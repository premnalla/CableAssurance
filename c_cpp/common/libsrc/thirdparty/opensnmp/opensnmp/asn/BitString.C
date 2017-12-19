/* BitString.C */
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

#include "BitString.H"

BitString::BitString() 
 : OctetString()
{
}

BitString::BitString(const BitString &thecopy) 
  : OctetString(thecopy)
{
}

BitString::~BitString()
{
}

BitString::BitString(char *init) 
  : OctetString(init)
{
}

string
BitString::bitString() const
{
    char buf[10];
    string s;

    for(int i=0; i < size(); ++i) {

        if(data_string[i] & 0x80) {
            sprintf(buf,"(%d) ",0+(i*8));
            s = s + buf;
        }
        if(data_string[i] & 0x40) {
            sprintf(buf,"(%d) ",1+(i*8));
            s = s + buf;
        }
        if(data_string[i] & 0x20) {
            sprintf(buf,"(%d) ",2+(i*8));
            s = s + buf;
        }
        if(data_string[i] & 0x10) {
            sprintf(buf,"(%d) ",3+(i*8));
            s = s + buf;
        }
        if(data_string[i] & 0x08) {
            sprintf(buf,"(%d) ",4+(i*8));
            s = s + buf;
        }
        if(data_string[i] & 0x04) {
            sprintf(buf,"(%d) ",5+(i*8));
            s = s + buf;
        }
        if(data_string[i] & 0x02) {
            sprintf(buf,"(%d) ",6+(i*8));
            s = s + buf;
        }
        if(data_string[i] & 0x01) {
            sprintf(buf,"(%d) ",7+(i*8));
            s = s + buf;
        }
    }
    return s;
}

BitString::operator string() const
{
    return bitString();
}

//
//   print it: turn our object into a textual representation...
//      buf:      buffer to write to.
//      prefix:   print this prefix before all outgoing print statements.
//      maxlines: maximum number of lines we can output.
//
string BitString::toString() const
{
    string s = "BitString: ";

    s = s + bitString();
    
  return s;
}
