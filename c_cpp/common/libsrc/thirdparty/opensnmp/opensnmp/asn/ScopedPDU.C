/* ScopedPDU.C */
//
// ScopedPDU
//
// ScopedPDUs is a sequence of variable bindings, which are in turn
// sequences of OID and VALUE.
//

#include "config.h"
#include <stdio.h>
#include "ScopedPDU.H"

//
// ScopedPDU
//

ScopedPDU::ScopedPDU(const ScopedPDU &copyFrom) : asnDataType(copyFrom.get_tag())
{
  if (copyFrom.contextEngineID) 
    this->contextEngineID = copyFrom.contextEngineID->clone();
  else this->contextEngineID = 0;

  if (copyFrom.contextName)
    this->contextName = copyFrom.contextName->clone();
  else this->contextName = 0;

  if (copyFrom.pdu)
    this->pdu = copyFrom.pdu->clone();
  else this->pdu = 0;

  this->serialScopedPDU = 0;

  if (copyFrom.encryptedData) 
    this->encryptedData = copyFrom.encryptedData->clone();
  else this->encryptedData= 0;  
}

ScopedPDU::ScopedPDU(OctetString *contextEngineID, 
		     OctetString *contextName, 
		     PDU         *pdu) : asnDataType(BER_TAG_SEQUENCE)
{
  this->contextEngineID = contextEngineID;
  this->contextName = contextName;
  this->pdu = pdu;
  this->serialScopedPDU = 0;
  this->encryptedData   = 0;
}

ScopedPDU::ScopedPDU(char *buffer)
{
  this->contextEngineID = new OctetString();
  this->contextName = new OctetString();
  // XXX: broken complier needs full declaration.
  this->pdu = new PDU(BER_TAG_PDU_GET,0,0,0, new VarBindList, PDU_VERSION_2);
  this->serialScopedPDU = buffer;
  this->encryptedData = 0;
}

ScopedPDU::~ScopedPDU()
{
  EraseData();
  if (contextEngineID) delete contextEngineID;
  if (contextName)     delete contextName;
  if (pdu)             delete pdu;
  //  if (this->serialScopedPDU)  delete this->serialScopedPDU;
  if (this->encryptedData)    delete this->encryptedData;
}

void
ScopedPDU::EraseData()
{
  if (this->contextEngineID) this->contextEngineID->EraseData();
  if (this->contextName)     this->contextName->EraseData();
  if (this->pdu)             this->pdu->EraseData();
}

//
// Add a variable to the binding list.
//
void
ScopedPDU::Add(VarBind *vb)
{
  pdu->Add(vb);
}


// 
// asnEncode asnEncodes either the encrypted sPDU or just
// the scopedPDU
char*
ScopedPDU::asnEncode(char *cp) const {
  // if there is encrypted data
  if (this->encryptedData != 0) {
    return(this->encryptedData->asnEncode(cp));
  }
  else {
    return(this->asnDataType::asnEncode(cp));
  }
}


//
// Encode the data into a buffer, but in reverse.
//
char *
ScopedPDU::EncodeData(char *cp) const
{ 
  cp = pdu->asnEncode(cp);
  cp = contextName->asnEncode(cp);
  cp = contextEngineID->asnEncode(cp);
  
  return cp;
}

//
// Decode the data from a char * buffer.
//
void
ScopedPDU::DecodeData(char *cp, int length)
{
  char *tcp = cp;
  EraseData();

  tcp = contextEngineID->asnDecode(tcp, length);
  tcp = contextName->asnDecode(tcp, (length - (tcp - cp)));
  tcp = pdu->asnDecode(tcp, (length - (tcp - cp)));
}


//
//   print it: turn our object into a textual representation...
//      buf:      buffer to write to.
//      prefix:   print this prefix before all outgoing print statements.
//      maxlines: maximum number of lines we can output.
//
string ScopedPDU::toString() const
{
  string s;

  s = "ScopedPDU: \n";
  s = s + "  contextEngineID: " + contextEngineID->toString() + 
    "  contextName: " + contextName->toString() + 
    "  " + pdu->toString() + "\n";
  return s;
}

ScopedPDU::operator string() const
{
  string s;
  s = "ScopedPDU: contextEngineID: " + string(*contextEngineID) + 
    "contextName: " + string(*contextName) +  "  " + string(*pdu);
  return s;
}

char *
ScopedPDU::get_serialScopedPDU(bool extract)
{
  char *retVal = this->serialScopedPDU;
  if (extract)
    this->serialScopedPDU = 0;
  return retVal;
}

void ScopedPDU::set_serialScopedPDU(char *newVal)
{
  if (this->serialScopedPDU)
    delete this->serialScopedPDU;
  this->serialScopedPDU = newVal;
}

OctetString *
ScopedPDU::get_encryptedData(bool extract) {
  OctetString *retVal = this->encryptedData;
  if (extract)
    this->encryptedData = 0;
  return retVal;
}
  
void 
ScopedPDU::set_encryptedData(OctetString *newEncryptData) {
  if (this->encryptedData)
    delete this->encryptedData;
  this->encryptedData = newEncryptData;
}


ScopedPDU &
ScopedPDU::change_value(const asnDataType &right) {
    const ScopedPDU &ref = dynamic_cast<const ScopedPDU &>(right);
    this->contextEngineID = ref.contextEngineID;
    this->contextName = ref.contextName;
    if (this->pdu && ref.pdu)
        *(this->pdu) = *(ref.pdu);
    else {
        if (this->pdu)
            delete this->pdu;
        if (ref.pdu)
            this->pdu = ref.pdu->clone();
        else
            this->pdu = 0;
    }
    // XXX: clear buffers
    return *this;
}

