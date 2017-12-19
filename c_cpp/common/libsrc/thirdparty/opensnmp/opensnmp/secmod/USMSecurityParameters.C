/* USMSecurityParameters.C */
//
// USMSecurityParameters
//
// USMSecurityParameters is the representation of a ber sequence of a
// the USM Security Parameters.  
// This is a sequence of Authoritative Engine ID (Octet String),
// Authoritative Engine Boots (Integer32), Authoritative Engine Time
// (Integer32), User Name (Octet String), Authentication Parameters 
// (Octet String), and Privacy Parameters (Octet String) respectively.
// It is able to encode itself and decode itself into ber serialization.

//
// Encoding:
// TAG LEN <OCTET STRING ENCODING> <INTEGER32 ENCODING>  <INTEGER32 ENCODING>
// <OCTET STRING ENCODING> <OCTET STRING ENCODING> <OCTET STRING ENCODING>
//
// TAG=0x30 (sequence tag)

#include "config.h"
#include <stdio.h>
#include "USMSecurityParameters.H"

//
// NOTE:
// We are using the following structure to cheat a little. EncodeData() is a const method,
// but we want to store data. We can't change any of our own member variables, but if a
// member variable points to another object, we can change that object. So the loc pointer
// keeps the method const while allowing us to store pointers. It's ugly, but it should work.
//
struct loc_t {
  char* serialSecParams_loc;
  char* msgAuthoritativeEngineID_loc;
  char* msgAuthoritativeEngineBoots_loc;
  char* msgAuthoritativeEngineTime_loc;
  char* msgUserName_loc;
  char* msgAuthenticationParameters_loc;
  char* msgPrivacyParameters_loc;
};


//
// USMSecurityParameters
//

USMSecurityParameters::USMSecurityParameters() : asnDataType(BER_TAG_SEQUENCE)
{
  this->msgAuthoritativeEngineID    = 0;
  this->msgAuthoritativeEngineBoots = 0;
  this->msgAuthoritativeEngineTime  = 0;
  this->msgUserName                 = 0;
  this->msgAuthenticationParameters = 0;
  this->msgPrivacyParameters        = 0;
  loc = new loc_t();

  ZeroLocations();
}

USMSecurityParameters::~USMSecurityParameters()
{
  EraseData();
  delete loc;
}

USMSecurityParameters::USMSecurityParameters(const USMSecurityParameters &ref) 
  : asnDataType(ref.get_tag())
{
  this->msgAuthoritativeEngineID    = ref.msgAuthoritativeEngineID->clone();
  this->msgAuthoritativeEngineBoots = ref.msgAuthoritativeEngineBoots->clone();
  this->msgAuthoritativeEngineTime  = ref.msgAuthoritativeEngineTime->clone();
  this->msgUserName                 = ref.msgUserName->clone();
  this->msgAuthenticationParameters = ref.msgAuthenticationParameters->clone();
  this->msgPrivacyParameters        = ref.msgPrivacyParameters->clone();
  loc = new loc_t();

  ZeroLocations();
}

//
// Add variables the class.
//
USMSecurityParameters::USMSecurityParameters
  (OctetString  *new_msgAuthoritativeEngineID,
   Integer32    *new_msgAuthoritativeEngineBoots,
   Integer32    *new_msgAuthoritativeEngineTime,
   OctetString  *new_msgUserName,
   OctetString  *new_msgAuthenticationParameters,
   OctetString  *new_msgPrivacyParameters)
    : asnDataType(BER_TAG_SEQUENCE)
{
  this->msgAuthoritativeEngineID    = new_msgAuthoritativeEngineID;
  this->msgAuthoritativeEngineBoots = new_msgAuthoritativeEngineBoots;
  this->msgAuthoritativeEngineTime  = new_msgAuthoritativeEngineTime;
  this->msgUserName                 = new_msgUserName;
  this->msgAuthenticationParameters = new_msgAuthenticationParameters;
  this->msgPrivacyParameters        = new_msgPrivacyParameters;
  loc = new loc_t();
  
  ZeroLocations();
}

void
USMSecurityParameters::ZeroLocations() {
  
  loc->serialSecParams_loc             = 0;
  loc->msgAuthoritativeEngineID_loc    = 0;
  loc->msgAuthoritativeEngineBoots_loc = 0;
  loc->msgAuthoritativeEngineTime_loc  = 0;
  loc->msgUserName_loc                 = 0;
  loc->msgAuthenticationParameters_loc = 0;
  loc->msgPrivacyParameters_loc        = 0;
}


void
USMSecurityParameters::EraseData()
{
  if (this->msgAuthoritativeEngineID)
    delete this->msgAuthoritativeEngineID;
  this->msgAuthoritativeEngineID = 0;

  if (this->msgAuthoritativeEngineBoots)
    delete this->msgAuthoritativeEngineBoots;
  this->msgAuthoritativeEngineBoots = 0;

  if (this->msgAuthoritativeEngineTime)
    delete this->msgAuthoritativeEngineTime;
  this->msgAuthoritativeEngineTime = 0;

  if (this->msgUserName)
    delete this->msgUserName;
  this->msgUserName = 0;

  if (this->msgAuthenticationParameters)
    delete this->msgAuthenticationParameters;
  this->msgAuthenticationParameters = 0;

  if (this->msgPrivacyParameters)
    delete this->msgPrivacyParameters;
  this->msgPrivacyParameters = 0;
}


//
// Encode the data into a buffer, but in reverse.
// Save the location of the serialized variables,
// particularly the msgAuthenticationParameters.
//
char *
USMSecurityParameters::EncodeData(char *cp) const
{
  cp = this->msgPrivacyParameters->asnEncode(cp);
  loc->msgPrivacyParameters_loc        = cp + 1;
  
  cp = this->msgAuthenticationParameters->asnEncode(cp);
  loc->msgAuthenticationParameters_loc = cp + 1;
    
  cp = this->msgUserName->asnEncode(cp);
  loc->msgUserName_loc                 = cp + 1;
  
  cp = this->msgAuthoritativeEngineTime->asnEncode(cp);
  loc->msgAuthoritativeEngineTime_loc  = cp + 1;
  
  cp = this->msgAuthoritativeEngineBoots->asnEncode(cp);
  loc->msgAuthoritativeEngineBoots_loc = cp + 1;
  
  cp = this->msgAuthoritativeEngineID->asnEncode(cp);
  loc->msgAuthoritativeEngineID_loc    = cp + 1;

  return cp;
}


//
// Decode the data from a char * buffer.
// Save the location of the serialized variables,
// particularly the msgAuthenticationParameters.
//
void
USMSecurityParameters::DecodeData(char *cp, int length)
{
  char *tcp = cp;
  EraseData();

  this->msgAuthoritativeEngineID    = new OctetString;
  this->msgAuthoritativeEngineBoots = new Integer32;
  this->msgAuthoritativeEngineTime  = new Integer32;
  this->msgUserName                 = new OctetString;
  this->msgAuthenticationParameters = new OctetString;
  this->msgPrivacyParameters        = new OctetString;


  loc->msgAuthoritativeEngineID_loc    = tcp;
  tcp = this->msgAuthoritativeEngineID->asnDecode(tcp, length);

  loc->msgAuthoritativeEngineBoots_loc = tcp;
  tcp = this->msgAuthoritativeEngineBoots->asnDecode(tcp, (length-(tcp-cp) ));

  loc->msgAuthoritativeEngineTime_loc  = tcp;
  tcp = this->msgAuthoritativeEngineTime->asnDecode(tcp, (length-(tcp-cp) ));

  loc->msgUserName_loc                 = tcp;
  tcp = this->msgUserName->asnDecode(tcp, (length-(tcp-cp) ));

  loc->msgAuthenticationParameters_loc = tcp;
  tcp = this->msgAuthenticationParameters->asnDecode(tcp, (length-(tcp-cp) ));

  loc->msgPrivacyParameters_loc        = tcp;
  tcp = this->msgPrivacyParameters->asnDecode(tcp, (length-(tcp-cp) ));
}


// a special version need because this is a sequence within a octet
// string.
// encode the object in reverse into a character buffer: data, header
//
char *
USMSecurityParameters::asnEncode(char *cp) const
{
  char *cp2 = cp;
  
  cp = EncodeData(cp);

  cp = asnDataType::EncodeHeader(cp, cp2 - cp, BER_TAG_SEQUENCE);

  cp = asnDataType::EncodeHeader(cp, cp2 - cp, BER_TAG_OCTET_STRING);

  loc->serialSecParams_loc = cp;
  return cp;
}

char *
USMSecurityParameters::asnDecode(char *cp, int length)
{
  loc->serialSecParams_loc = cp;

  this->tag = BER_TAG_OCTET_STRING;
  cp = DecodeHeader(cp, &length);

  this->tag = BER_TAG_SEQUENCE;
  cp = DecodeHeader(cp, &length);

  DecodeData(cp, length);
  return cp+length;
}


//
//   print it: turn our object into a textual representation...
//
string USMSecurityParameters::toString() const
{
  string s = "USMSecurityParameters:\n";
  s = s + "    msgAuthoritativeEngineID = ";
  if (this->msgAuthoritativeEngineID == 0)
    s = s + "   NULL\n";
  else
    s = s + "   " + string(*this->msgAuthoritativeEngineID) + "\n";
  
  s = s + " msgAuthoritativeEngineBoots = ";
  if (this->msgAuthoritativeEngineBoots == 0)
  s = s + "   NULL\n";
  else
  s = s + "   " + string(*this->msgAuthoritativeEngineBoots) + "\n";
  
  s = s + "  msgAuthoritativeEngineTime = ";
  if (this->msgAuthoritativeEngineTime == 0)
    s = s + "   NULL\n";
  else
    s = s + "   " + string(*this->msgAuthoritativeEngineTime) + "\n";

  s = s + "                 msgUserName = ";
  if (this->msgUserName == 0)
    s = s + "   NULL\n";
  else
    s = s + "   " + string(*this->msgUserName) + "\n";
  
  s = s + " msgAuthenticationParameters = ";
  if (this->msgAuthenticationParameters == 0)
    s = s + "   NULL\n";
  else
    s = s + "   " + this->msgAuthenticationParameters->toHexString() + "\n";
 
  s = s + "        msgPrivacyParameters = ";
  if (this->msgPrivacyParameters == 0)
    s = s + "   NULL\n";
  else
    s = s + "   " + this->msgPrivacyParameters->toHexString() + "\n";

  return s;
}

USMSecurityParameters::operator string() const
{
  string s = "USMSecurityParameters: msgAuthoritativeEngineID = ";
  if (this->msgAuthoritativeEngineID == 0)
    s = s + " NULL";
  else
    s = s + string(*this->msgAuthoritativeEngineID);
  
  s = s + ", msgAuthoritativeEngineBoots = ";
  if (this->msgAuthoritativeEngineBoots == 0)
  s = s + " NULL";
  else
  s = s + string(*this->msgAuthoritativeEngineBoots);
  
  s = s + ", msgAuthoritativeEngineTime = ";
  if (this->msgAuthoritativeEngineTime == 0)
    s = s + " NULL";
  else
    s = s + string(*this->msgAuthoritativeEngineTime);

  s = s + ", msgUserName = ";
  if (this->msgUserName == 0)
    s = s + " NULL";
  else
    s = s + string(*this->msgUserName);
  
  s = s + ", msgAuthenticationParameters = ";
  if (this->msgAuthenticationParameters == 0)
    s = s + " NULL";
  else
    s = s + this->msgAuthenticationParameters->toHexString();
 
  s = s + ", msgPrivacyParameters = ";
  if (this->msgPrivacyParameters == 0)
    s = s + " NULL";
  else
    s = s + this->msgPrivacyParameters->toHexString();

  return s;
}


OctetString *
USMSecurityParameters::get_msgAuthoritativeEngineID(bool extract)  {
  OctetString *ret = this->msgAuthoritativeEngineID;
  if (extract)
    this->msgAuthoritativeEngineID = 0;
  return ret;
}

void
USMSecurityParameters::set_msgAuthoritativeEngineID(OctetString *newVal)  {
  if (this->msgAuthoritativeEngineID)
    delete this->msgAuthoritativeEngineID;
  this->msgAuthoritativeEngineID = newVal;
}

Integer32 *
USMSecurityParameters::get_msgAuthoritativeEngineBoots(bool extract)  {
  Integer32 *ret = this->msgAuthoritativeEngineBoots;
  if (extract)
    this->msgAuthoritativeEngineBoots = 0;
  return ret;
}

void
USMSecurityParameters::set_msgAuthoritativeEngineBoots(Integer32 *newVal)  {
  if (this->msgAuthoritativeEngineBoots)
    delete this->msgAuthoritativeEngineBoots;
  this->msgAuthoritativeEngineBoots = newVal;
}

Integer32 *
USMSecurityParameters::get_msgAuthoritativeEngineTime(bool extract)  {
  Integer32 *ret = this->msgAuthoritativeEngineTime;
  if (extract)
    this->msgAuthoritativeEngineTime = 0;
  return ret;
}

void
USMSecurityParameters::set_msgAuthoritativeEngineTime(Integer32 *newVal)  {
  if (this->msgAuthoritativeEngineTime)
    delete this->msgAuthoritativeEngineTime;
  this->msgAuthoritativeEngineTime = newVal;
}

OctetString *
USMSecurityParameters::get_msgUserName(bool extract)  {
  OctetString *ret = this->msgUserName;
  if (extract)
    this->msgUserName = 0;
  return ret;
}

void
USMSecurityParameters::set_msgUserName(OctetString *newVal)  {
  if (this->msgUserName)
    delete this->msgUserName;
  this->msgUserName = newVal;
}

OctetString *
USMSecurityParameters::get_msgAuthenticationParameters(bool extract)  {
  OctetString *ret = this->msgAuthenticationParameters;
  if (extract)
    this->msgAuthenticationParameters = 0;
  return ret;
}

void
USMSecurityParameters::set_msgAuthenticationParameters(OctetString *newVal)  {
  if (this->msgAuthenticationParameters)
    delete this->msgAuthenticationParameters;
  this->msgAuthenticationParameters = newVal;
}

OctetString *
USMSecurityParameters::get_msgPrivacyParameters(bool extract)  {
  OctetString *ret = this->msgPrivacyParameters;
  if (extract)
    this->msgPrivacyParameters = 0;
  return ret;
}

void
USMSecurityParameters::set_msgPrivacyParameters(OctetString *newVal)  {
  if (this->msgPrivacyParameters)
    delete this->msgPrivacyParameters;
  this->msgPrivacyParameters = newVal;
}


// Returns serial location of entire block (i.e. the octet string,
// not the sequence.
// 
char *USMSecurityParameters::get_serialLocation()  {
  return(loc->serialSecParams_loc);
}

// checks for match of msgAuthoritativeEngineBoots or 
// msgAuthoritativeEngineTime.
char *USMSecurityParameters::get_serialLocation(Integer32    *findVal)  {

  if (findVal == this->msgAuthoritativeEngineBoots) 
    return(loc->msgAuthoritativeEngineBoots_loc);

  else if (findVal == this->msgAuthoritativeEngineTime)
    return(loc->msgAuthoritativeEngineTime_loc);
  
  return(0);
}

// checks for match of msgAuthoritativeEngineID, msgUserName,
// msgAuthenticationParameters, msgPrivacyParameters.
char *USMSecurityParameters::get_serialLocation(OctetString  *findVal) {
  // if you're wondering msgAuthenticationParameters is first
  // because it is the most likely to be called.
  if (findVal == this->msgAuthenticationParameters)
    return(loc->msgAuthenticationParameters_loc);

  else if (findVal == this->msgAuthoritativeEngineID)
    return(loc->msgAuthoritativeEngineID_loc);
  else if (findVal == this->msgUserName)
    return(loc->msgUserName_loc);
  else if (findVal == this->msgPrivacyParameters)
    return(loc->msgPrivacyParameters_loc);

 return(0);
}
