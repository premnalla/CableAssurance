/* snmpV3Message.C */
//
// snmpV3Message
//
// snmpV3Message is the representation of a ber sequence of a
// SNMP version 3 message. This is a sequence of version number (integer32),
// Header Data, security parameters (Octet String), and scoped PDU 
// respectively. It is able to encode itself and decode itself into 
// a ber serialization.

//
// Encoding:
// TAG LEN <INTEGER32 ENCODING> <HeaderData ENCODING> <OCTET STRING ENCODING>
// <ScopedPDU ENCODING>
//
// TAG=0x30 (sequence tag)

#include "config.h"
#include <stdio.h>
#include "snmpV3Message.H"

//
// snmpV3Message
//

snmpV3Message::snmpV3Message(void) : asnDataType(BER_TAG_SEQUENCE)
{
  this->msgVersion            = 0;
  this->msgGlobalData         = 0;
  this->msgSecurityParameters = 0;
  this->scopedPDU             = 0;
  this->scopedPDU_string      = 0;

  ZeroLocations();
}

snmpV3Message::~snmpV3Message()
{
  EraseData();
}

snmpV3Message::snmpV3Message(const snmpV3Message &ref) : asnDataType(ref.get_tag())
{
  if (ref.msgVersion)     this->msgVersion = ref.msgVersion->clone();
  if (ref.msgGlobalData)  this->msgGlobalData = ref.msgGlobalData->clone();
  if (ref.msgSecurityParameters)  
    this->msgSecurityParameters = ref.msgSecurityParameters->clone();
  if (ref.scopedPDU)      this->scopedPDU = ref.scopedPDU->clone();
  if(ref.scopedPDU_string)
    this->scopedPDU_string  = new string( *(ref.scopedPDU_string) );

  ZeroLocations();
}

//
// Add variables the class.
//
snmpV3Message::snmpV3Message(Integer32   *newMsgVersion, 
			     HeaderData  *newMsgGlobalData, 
			     asnDataType *newMsgSecurityParameters, 
			     ScopedPDU   *newScopedPDU) 
  : asnDataType(BER_TAG_SEQUENCE)
{
  this->msgVersion            = newMsgVersion;
  this->msgGlobalData         = newMsgGlobalData;
  this->msgSecurityParameters = newMsgSecurityParameters;
  this->scopedPDU             = newScopedPDU;
  this->scopedPDU_string      = 0;
  
  ZeroLocations();
}

void
snmpV3Message::ZeroLocations() {
  this->serialMsg                  = 0;
  this->msgVersion_loc             = 0;
  this->msgGlobalData_loc          = 0;
  this->msgSecurityParameters_loc  = 0;
  this->scopedPDU_loc              = 0;
}


void
snmpV3Message::EraseData()
{
  if (this->msgVersion)
    delete this->msgVersion;
  this->msgVersion = 0;
  if (this->msgGlobalData)
    delete this->msgGlobalData;
  this->msgGlobalData = 0;
  if (this->msgSecurityParameters)
    delete this->msgSecurityParameters;
  this->msgSecurityParameters = 0;
  if (this->scopedPDU)
    delete this->scopedPDU;
  this->scopedPDU = 0;
  if (this->scopedPDU_string)
    delete this->scopedPDU_string;
  this->scopedPDU_string = 0;
}


//
// Encode the data into a buffer, but in reverse.
//
char *
snmpV3Message::EncodeData(char *cp) const
{
  cp = this->scopedPDU->asnEncode(cp);
  cp = this->msgSecurityParameters->asnEncode(cp);
  cp = this->msgGlobalData->asnEncode(cp);
  cp = this->msgVersion->asnEncode(cp);
  return cp;
}


//
// Decode the data from a char * buffer.
//
void
snmpV3Message::DecodeData(char *cp, int length)
{
  char *tcp = cp;
  EraseData();
  
  this->msgVersion            = new Integer32;
  this->msgGlobalData         = new HeaderData;
  this->msgSecurityParameters = new OctetString;
  this->scopedPDU               = new ScopedPDU;

  this->msgVersion_loc            = tcp;
  tcp = this->msgVersion->asnDecode(tcp, length);

  this->msgGlobalData_loc         = tcp;
  tcp = this->msgGlobalData->asnDecode(tcp, (length - (tcp - cp)));

  this->msgSecurityParameters_loc = tcp;
  tcp = this->msgSecurityParameters->asnDecode(tcp, (length - (tcp - cp)));

  this->scopedPDU_loc               = tcp;
  tcp = this->scopedPDU->asnDecode(tcp, (length - (tcp - cp)));
}


//
// Decodes the first three sequences of a message.
//
char *
snmpV3Message::asnDecodeMsgHeader(char *cp, int length)
{
  
  this->serialMsg = cp;
  cp = DecodeHeader(cp, &length);
  DecodeMsgHeader(cp, length);
  return cp+length;
}


//
// used by asnDecodeMsgHeader to decode the first three sequences 
// of data from a char * buffer.
//
char *
snmpV3Message::DecodeMsgHeader(char *cp, int length)
{
  char* tcp = cp;
  EraseData();
  
  this->msgVersion            = new Integer32;
  this->msgGlobalData         = new HeaderData;
  this->msgSecurityParameters = new OctetString;

  this->msgVersion_loc            = tcp;
  tcp = this->msgVersion->asnDecode(tcp, length);

  this->msgGlobalData_loc         = tcp;
  tcp = this->msgGlobalData->asnDecode(tcp, (length - (tcp - cp)));

  this->msgSecurityParameters_loc = tcp;
  tcp = this->msgSecurityParameters->asnDecode(tcp, (length - (tcp - cp)));

  this->scopedPDU_loc               = tcp;

  return(tcp);
}


//
//   print it: turn our object into a textual representation...
//
string snmpV3Message::toString() const
{

  string s = "snmpV3Message:\n   msgVersion = ";
  if (this->msgVersion == 0)
    s = s + "   NULL\n";
  else
    s = s + string(*this->msgVersion) + "\n";
  
  s = s + "\n";
  if (this->msgGlobalData == 0)
    s = s + "   NULL\n";
  else
    s = s + this->msgGlobalData->toString();

  s = s + "\n";
  if (this->msgSecurityParameters == 0)
    s = s + "   NULL\n";
  else
    s = s + this->msgSecurityParameters->toString();

  s = s + "\n";
  if (this->scopedPDU == 0)
    s = s + "   NULL\n\n";
  else
    s = s + this->scopedPDU->toString() + "\n";

  if (this->scopedPDU_string == 0)
    s = s + "scoped PDU string:   NULL\n\n";
  else
    s = s + "scoped PDU string: " + *(this->scopedPDU_string) + "\n";

  return s;
}



char *snmpV3Message::get_serialLocation()  {
  return(this->serialMsg);
}

char *snmpV3Message::get_scopedPDUSerialLocation()  {
  return (this->scopedPDU_loc);
}

char *snmpV3Message::get_msgSecurityParametersSerialLocation()  {
  return (this->msgSecurityParameters_loc);
}

snmpV3Message &
snmpV3Message::change_value(const asnDataType &right) {
    const snmpV3Message &ref = dynamic_cast<const snmpV3Message &>(right);
    
    if (this->msgVersion && ref.msgVersion)
        *(this->msgVersion) = *(ref.msgVersion);
    else {
        if (this->msgVersion)
            delete this->msgVersion;
        if (ref.msgVersion)
            this->msgVersion = ref.msgVersion->clone();
        else
            this->msgVersion = 0;
    }
    
    
    if (this->msgGlobalData && ref.msgGlobalData)
        *(this->msgGlobalData) = *(ref.msgGlobalData);
    else {
        if (this->msgGlobalData)
            delete this->msgGlobalData;
        if (ref.msgGlobalData)
            this->msgGlobalData = ref.msgGlobalData->clone();
        else
            this->msgGlobalData = 0;
    }
    
    if (this->msgSecurityParameters && ref.msgSecurityParameters)
        *(this->msgSecurityParameters) = *(ref.msgSecurityParameters);
    else {
        if (this->msgSecurityParameters)
            delete this->msgSecurityParameters;
        if (ref.msgSecurityParameters)
            this->msgSecurityParameters = ref.msgSecurityParameters->clone();
        else
            this->msgSecurityParameters = 0;
    }
    
    if (this->scopedPDU && ref.scopedPDU)
        *(this->scopedPDU) = *(ref.scopedPDU);
    else {
        if (this->scopedPDU)
            delete this->scopedPDU;
        if (ref.scopedPDU)
            this->scopedPDU = ref.scopedPDU->clone();
        else
            this->scopedPDU = 0;
    }

    if (ref.scopedPDU_string) {
      if (!this->scopedPDU_string) this->scopedPDU_string = new string();
      *(this->scopedPDU_string) = *(ref.scopedPDU_string);
    }
    else if (this->scopedPDU_string) {
      delete this->scopedPDU_string;
      this->scopedPDU_string = 0;
    }

    // XXX: buffers

    return *this;
}
