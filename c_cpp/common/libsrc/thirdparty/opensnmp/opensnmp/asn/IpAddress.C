/* IpAddress.C */
//
// Octet String
//
// Octet strings are simply encoded as TAG LEN DATA, where data is
// straight data from the octet string.  Need not be null terminated.

#include "config.h"
#include <string>
#include <iostream>

#include <stdio.h>
#include <strings.h>

using namespace std;

#include "IpAddress.H"

IpAddress::IpAddress() 
  : RawData(BER_TAG_IPADDRESS)
{
}

IpAddress::IpAddress(const IpAddress &thecopy) 
  : RawData(thecopy, BER_TAG_IPADDRESS)
{
}

IpAddress::~IpAddress()
{
}

IpAddress::IpAddress(char *init) 
  : RawData(BER_TAG_IPADDRESS)
{
  string ns = init;

  EraseData();
  char *tmp_string = new char[4];
  *tmp_string = 0x00000000;

  int index, oldindex = 0; 
  int i = 0;

  while ( (string::npos != (index = ns.find_first_of(".",oldindex)))  &&
	  (i < 4) )  {
    tmp_string[i] = (char)atoi((ns.substr(oldindex,(oldindex-index))).c_str());
    oldindex = ++index; 
    i++;
  } 
  if ((i < 4) && (string::npos != oldindex)) {
    tmp_string[i] = (char)atoi((ns.substr(oldindex,(oldindex-index))).c_str());
  }

  data_string.assign(tmp_string,4);
}

//
//   print it: turn our object into a textual representation...
//      buf:      buffer to write to.
//      prefix:   print this prefix before all outgoing print statements.
//      maxlines: maximum number of lines we can output.
//
string IpAddress::toString() const
{
  char buf[168]; // 3 * 4 + 3 + 1;
  string s = "IPADDR: ";

  sprintf(buf, "%u.%u.%u.%u", (unsigned int) data_string[0] & 0xff, 
          (unsigned int) data_string[1] & 0xff, 
          (unsigned int) data_string[2] & 0xff, 
          (unsigned int) data_string[3] & 0xff);
  s = s + buf;

  return s;
}
