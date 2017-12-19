/* Unsigned64.C */
//
// Unsigned64:
//
//   BER unsigned integers are an application specific encoding scheme
//   encoded with the application specific tag ored with 0x06.  It's
//   encoded with straight data, with the first non-zero MSB byte
//   first.  A leading bit set_ to 1 must be encoded with zero padding
//   to the left as needed.
//
//   Examples:
//        127 -> 46 01 7f
//        128 -> 46 02 00 80            // note the needed 00 padding
// 4294967295 -> 46 05 00 ff ff ff ff
//

#include "config.h"
#include <stdlib.h>
#include <stdio.h>
#include "Unsigned64.H"
#include "asnParseError.H"

Unsigned64::Unsigned64()
{
  value = 0;
}

Unsigned64::Unsigned64(unsigned long long init_val, berTag asn_tag) 
  : asnDataType(asn_tag)
{
  value = init_val;
}

Unsigned64::Unsigned64(char *str, berTag asn_tag)
  : asnDataType(asn_tag)
{
  value = strtoul(str,0,0);
}

Unsigned64::~Unsigned64()
{
}

void
Unsigned64::EraseData()
{
  // doesn't actually do much for an uint.
  value = 0;
}

//
// Encode the data into a buffer, but in reverse.
//
char *
Unsigned64::EncodeData(char *cp) const
{

  unsigned long long tmpvalue = value;

  *cp-- = tmpvalue & 0xff;
  tmpvalue >>= 8;
  while (tmpvalue != 0) {
    *cp-- = tmpvalue & 0xff;
    tmpvalue >>= 8;
  }
  if (*(cp+1) & 0x80) {
    // extra padding if the left most bit is not equal to all the bits
    // to the fully qualified integer to the left of it.
    // IE:
    //   0x80 should be encoded as 00 80, not just 80.
    *cp-- = 0x00;
  }
  return cp;
}

void
Unsigned64::DecodeData(char *cp, int len)
{
  if (len > 9 || len == 0)
    throw asnParseError("Unsigned64 data is too large");
  
  value = 0;

  while(len-- != 0) {
    value = (value << 8);
    value = (value & 0xffffff00) | (*cp++ & 0xff);
  }
}

//
//   print it: turn our object into a textual representation...
//      buf:      buffer to write to.
//      prefix:   print this prefix before all outgoing print statements.
//      maxlines: maximum number of lines we can output.
//
string Unsigned64::toString() const
{
  char buf[1024];
  string s;
  
  sprintf(buf, "UInt64: %llu (0x%llx)\n", value, value);
  s = buf;
  return s;
}


void Unsigned64::operator=(Unsigned64 fromNum)
{
  this->value = fromNum.value;
}

Unsigned64::operator unsigned long long() const
{
  return value;
}

Unsigned64 &
Unsigned64::change_value(const asnDataType &right) {
    this->value = (dynamic_cast<const Unsigned64 &>(right)).value;
    return *this;
}

