/* Integer32.C */
//
// Interger32:
//
//   BER integers are encoded as straight data MSB first, in the
//   smallest amount of space possible by reducing the bit padding to
//   the left.  The first bit of the first byte indicates how the
//   remaining bits to the left of the data are to be padded.
//
//   Examples:
//      127 -> 02 01 7f
//      128 -> 02 02 00 80            // note the needed 00 padding
//     -128 -> 02 01 80
//     -129 -> 02 02 ff 7f            // note the needed ff padding
//

#include "config.h"
#include <stdlib.h>
#include <stdio.h>
#include "Integer32.H"
#include "asnParseError.H"

Integer32::Integer32() : asnIndex(BER_TAG_INTEGER32)
{
  value = 0;
}

Integer32::Integer32(int init)  : asnIndex(BER_TAG_INTEGER32)
{
  value = init;
}

Integer32::Integer32(char *str)  : asnIndex(BER_TAG_INTEGER32)
{
  value = atoi(str);
}

Integer32::Integer32(const string &str)  : asnIndex(BER_TAG_INTEGER32)
{
  value = atoi(str.c_str());
}

Integer32::~Integer32()
{
}

void
Integer32::EraseData()
{
  // doesn't actually do much for an int.
  value = 0;
}

//
// Encode the data into a buffer, but in reverse.
//
char *
Integer32::EncodeData(char *cp) const
{

  int tmpvalue = 0;
  tmpvalue = get_value();
  int testvalue = 0;
  if (tmpvalue < 0) testvalue = -1;
  else testvalue = 0;

  *cp-- = tmpvalue & 0xff;
  tmpvalue >>= 8;
  while (tmpvalue != testvalue) {
    *cp-- = tmpvalue & 0xff;
    tmpvalue >>= 8;
  }
  if ((*(cp+1) & 0x80) != (testvalue & 0x80)) {
    // extra padding if the left most bit is not equal to all the bits
    // to the fully qualified integer to the left of it.
    // IE:
    //   0x80 should be encoded as 00 80, not just 80.
    *cp-- = testvalue & 0xff;
  }
  return cp;
}

void
Integer32::DecodeData(char *cp, int len)
{
  if (len > 5 || len == 0) // a 32 bit int can be encoded in 5 bytes
                           // if the high bit needs masking.
    throw asnParseError("Length of Integer32 is too large");
  
  if (*cp & 0x80)
    value = -1;
  else
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
string Integer32::toString() const
{
  char buf[64];
  string s = "Int: ";
  sprintf(buf, "%d (0x%x)\n", get_value(), get_value());
  s = s + buf;
  return s;
}

// convert the value to a string
Integer32::operator string() const
{
  char buf[64];
  sprintf(buf, "%d", get_value());
  string s = buf;
  return s;
}


Integer32 &Integer32::change_value(const asnDataType &fromNum)
{
  this->value = (dynamic_cast<const Integer32 &>(fromNum)).get_value();
  return *this;
}

void Integer32::set_value(int fromNum)
{
  this->value = fromNum;
}

Integer32 Integer32::operator++()
{
  set_value(get_value()+1);
  return *this;
}


Integer32::Integer32(const Integer32 &thecopy) : asnIndex(thecopy.get_tag())
{
  this->value = thecopy.value;
}

Integer32::operator int() const
{
  return get_value();
}

OID&
Integer32::appendOID(OID &to, bool implied) {
    if (get_value() < 0)
        std::cerr << "Ack: illegally cast int value to OID\n";

    to.append(get_value());
    return to;
}
