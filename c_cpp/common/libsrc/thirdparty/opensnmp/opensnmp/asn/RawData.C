/* RawData.C */
//
// Raw Data
//
// rawdatas are simply encoded as TAG LEN DATA, where data is
// straight data from the rawdata.  Need not be null terminated.

#ifndef RAWDATA_C
#define RAWDATA_C

#include "config.h"
#include <stdio.h>
#include <strings.h>
#include <sys/types.h>

#include "RawData.H"

static const string nullstr = "";

RawData::RawData(berTag asn_tag) 
  : asnIndex(asn_tag)
{

  // initialize to empty string
  data_string.assign("");
}

RawData::RawData(const string &init, berTag asn_tag)
  : asnIndex(asn_tag)
{

  // string to use has been passed in.
  data_string = init;
}

RawData::RawData(char *str, unsigned int len, berTag asn_tag)
    : asnIndex(asn_tag)
{
    if (len)
        data_string = string(str, len);
    else
        data_string = string(str);
}

RawData::RawData(const RawData &ref) : asnIndex(ref.get_tag()) {
    data_string = ref.data_string;
}


RawData::~RawData()
{
  EraseData();
}

void
RawData::EraseData()
{
  data_string = "";
}

//
// Encode the data into a buffer, but in reverse.
//
char *
RawData::EncodeData(char *cp) const
{
  cp -= get_value().size()-1;
  memcpy(cp, get_value().data(), get_value().size());
  return cp-1;
}

//
// Decode the data from a buffer.
//
void
RawData::DecodeData(char *cp, int len)
{
  EraseData();
  data_string.assign(cp,len);
}

//
//   print it: turn our object into a textual representation...
//      buf:      buffer to write to.
//      prefix:   print this prefix before all outgoing print statements.
//      maxlines: maximum number of lines we can output.
//
string
RawData::toString() const
{
  string s = "RawData: ";
  s = s + get_value() + "\n";
  return s;
}

// fromString intreprets a c_str (i.e. '\0' terminated), as a regular
// string or as a hex string if prepended with 0x (e.g. 0xff07)
void 
RawData::fromString(const char* Str) {
    if ( Str && (*Str == '0') && 
	 ((*(Str+1) == 'x') || (*(Str+1) == 'X')) ) {
      this->fromHexString(Str);
    }
    else {
      this->assign(Str, strlen(Str));
    }
}


void
RawData::fromHexString(const char* hexStr)
{
  int len, itmp;
  u_char buffer[1024];
  u_char *bufp = buffer;

  if ( hexStr && (*hexStr == '0') && 
       ((*(hexStr+1) == 'x') || (*(hexStr+1) == 'X')) ) hexStr += 2;
  for (len = 0; *hexStr; hexStr++) {
    if (isspace(*hexStr)) continue;
    if (!isxdigit(*hexStr)) { len = 0; break; }
    len++;
    if (sscanf(hexStr++, "%2x", &itmp) == 0) { len = 0; break; };
    *bufp++ = itmp;
    if (!*hexStr) { len = 0; break; }; /* odd number of chars is an error */
  }
  this->data_string = string((char*)buffer,len);
}

string
RawData::toHexString() const
{
  int len, stringLen = this->get_value().size();
  char  buffer[stringLen*3+4];
  char *bufp = buffer;

  memcpy(buffer, "0x ", 3);
  bufp += 3;

  for (len = 0; len < stringLen; len++) {
    sprintf(bufp, "%02x ", this->get_value()[len]);
    bufp += 3;
  }
  *(bufp) = '\0';

  // send back the string without the last space...
  return string(buffer);
}

// virtual functions from base class, copies a 'c' string, i.e. with '\0' 
// added, truncating the OctetString if neccessary!

char *
RawData::get_string(char *buf, unsigned int maxLen, bool null_terminated)
{
  unsigned int len = 0;
  if (null_terminated) {
    len = ((get_value().size() > (unsigned int)(maxLen-1)) 
	   ? maxLen-1 : get_value().size());
    memcpy(buf, get_value().data(), len);
    *(buf+len) = '\0';
  }
  else  {
    len = ((get_value().size() > (maxLen)) 
	   ? maxLen : get_value().size());
    memcpy(buf, get_value().data(), len);
  }

  return buf;
}

char *
RawData::get_string(char *buf, unsigned int *maxLen, bool null_terminated)
{
  unsigned int len = 0;
  if (null_terminated) {
    len = ((get_value().size() > (*maxLen-1)) 
	   ? *maxLen-1 : get_value().size());
    memcpy(buf, get_value().data(), len);
    *(buf+len) = '\0';
  }
  else  {
    len = ((get_value().size() > (unsigned int)(*maxLen)) 
	   ? *maxLen : get_value().size());
    memcpy(buf, get_value().data(), len);
  }

  *maxLen = len;
  return buf;
}

// copies up to maxLen, starting from location start_location
// doing the appropriate checks. location '0' is first char
char *
RawData::get_string(char *buf,   unsigned int  start_location,
		    unsigned int *maxLen, bool null_terminated) {
  unsigned int len = 0;
  if (start_location < get_value().size()) {
    unsigned int cpSize = get_value().size() - start_location;
    if (null_terminated) {
      len = ((cpSize > (*maxLen-1)) 
	     ? *maxLen-1 : cpSize);
      memcpy(buf, get_value().data() + start_location, len);
      *(buf+len) = '\0';
    }
    else  {
      len = ((cpSize > (unsigned int)(*maxLen)) 
	     ? *maxLen : cpSize);
      memcpy(buf, get_value().data() + start_location, len);
    }
  } // Notice, if this block wasn't entered, len = 0

  *maxLen = len;
  return buf;
}

void
RawData::assign(const char *str, int len) {
  data_string.assign(str, len);
}

void
RawData::assign(int len, const char theChar) {
  data_string.assign(len, theChar);
}


int
RawData::size() const
{
  return get_value().size();
}

RawData::operator string() const
{
  return get_value();
}

void
RawData::operator =(string newValue) {
  this->data_string = newValue;
}

RawData &
RawData::change_value(const asnDataType &newValue) {
  this->data_string = (dynamic_cast<const RawData &>(newValue)).get_value();
  return *this;
}

void
RawData::operator =(const RawData & fromData) {
  this->data_string = fromData.get_value();
}

bool
RawData::operator ==(RawData fromData) const {
  return(this->get_value() == fromData.get_value());
}

bool
RawData::operator <(RawData fromData) const {
  return(this->get_value() < fromData.get_value());
}

OID& RawData::appendOID(OID& to, bool implied) {
    // Convert the stored string into a OID
    unsigned int i;
    const unsigned char *val = (const unsigned char *)get_value().data();
    
    if (!implied)
        to.append(get_value().size());

    for(i=0; i< get_value().size(); i++) {
        to.append(val[i]);
    }

    return to;
}

void RawData::append(string str) {
    data_string = data_string + str;
}
    
void
RawData::append(char *cp, int len)
{
  data_string.append(cp,len);
}

const string &
RawData::get_value() const {
    return data_string;
}


#endif /* RAWDATA_C */
