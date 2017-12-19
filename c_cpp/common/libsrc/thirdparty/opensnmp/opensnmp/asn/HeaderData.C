/* HeaderData */
//
// Header data represents the header data for a SNMP V3 message
//

#include "config.h"
#include "HeaderData.H"

// initiate class with nothing.
HeaderData::HeaderData() : asnDataType(BER_TAG_SEQUENCE)  {
  this->msgID            = 0;
  this->maxMsgSize       = 0;
  this->msgFlags         = 0;
  this->msgSecurityModel = 0;
}

// initiate class from another object of the same class
HeaderData::HeaderData(const HeaderData &ref) 
  : asnDataType(BER_TAG_SEQUENCE)  {

  this->msgID            = ref.msgID->clone();
  this->maxMsgSize       = ref.maxMsgSize->clone();
  this->msgFlags         = ref.msgFlags->clone();
  this->msgSecurityModel = ref.msgSecurityModel->clone();
}

// initiate class from a list of new members
HeaderData::HeaderData(Integer32   *new_msgID, 
		       Integer32   *new_maxMsgSize,
		       OctetString *new_msgFlags,
		       Integer32   *new_msgSecurityModel) 
  : asnDataType(BER_TAG_SEQUENCE)  {

  this->msgID            = new_msgID;
  this->maxMsgSize       = new_maxMsgSize;
  this->msgFlags         = new_msgFlags;
  this->msgSecurityModel = new_msgSecurityModel;
}

// deallocate class
HeaderData::~HeaderData() {
  EraseData();
}


// deallocate data
void
HeaderData::EraseData() {
  if (this->msgID)
    delete this->msgID;
  this->msgID = 0;
  if (this->maxMsgSize)
    delete this->maxMsgSize;
  this->maxMsgSize = 0;
  if (this->msgFlags)
    delete this->msgFlags;
  this->msgFlags = 0;
  if (this->msgSecurityModel)
    delete this->msgSecurityModel;
  this->msgSecurityModel = 0;
}


//
// Encode the data into a buffer, but in reverse.
//
char *
HeaderData::EncodeData(char *cp) const
{
  cp = this->msgSecurityModel->asnEncode(cp);
  cp = this->msgFlags->asnEncode(cp);
  cp = this->maxMsgSize->asnEncode(cp);
  cp = this->msgID->asnEncode(cp);
  return cp;
}


//
// Decode the data from a char * buffer.
//
void
HeaderData::DecodeData(char *cp, int length)
{
  char *tcp = cp;
  EraseData();

  this->msgID            = new Integer32;
  this->maxMsgSize       = new Integer32;
  this->msgFlags         = new OctetString;
  this->msgSecurityModel = new Integer32;

  tcp = this->msgID->asnDecode(tcp, length);
  tcp = this->maxMsgSize->asnDecode(tcp, (length - (tcp - cp)));
  tcp = this->msgFlags->asnDecode(tcp, (length - (tcp - cp)));
  tcp = this->msgSecurityModel->asnDecode(tcp, (length - (tcp - cp)));
}


//
//   print it: turn our object into a textual representation...
//   Of coures, there is nothing here yet...
//
string HeaderData::toString() const
{

  string s = "Header Data:\n           msgID = ";
  if (this->msgID == 0)
    s = s + "NULL\n";
  else
    s = s + string(*this->msgID) + "\n";

  s = s + "      maxMsgSize = ";
  if (this->maxMsgSize == 0)
    s = s + "NULL\n";
  else
    s = s + string(*this->maxMsgSize) + "\n";

  s = s + "        msgFlags = ";
  if (this->msgFlags == 0)
    s = s + "NULL\n";
  else
    s = s + this->msgFlags->toHexString() + "\n";

  s = s + "msgSecurityModel = ";
  if (this->msgSecurityModel == 0)
    s = s + "NULL\n";
  else
    s = s + string(*this->msgSecurityModel) + "\n";
  
  return s;
}

HeaderData::operator string() const
{

  string s = "Header Data: msgID = ";
  if (this->msgID == 0)
    s = s + "NULL";
  else
    s = s + string(*this->msgID);

  s = s + ", maxMsgSize = ";
  if (this->maxMsgSize == 0)
    s = s + "NULL";
  else
    s = s + string(*this->maxMsgSize);

  s = s + ", msgFlags = ";
  if (this->msgFlags == 0)
    s = s + "NULL";
  else
    s = s + this->msgFlags->toHexString();

  s = s + ", msgSecurityModel = ";
  if (this->msgSecurityModel == 0)
    s = s + "NULL";
  else
    s = s + string(*this->msgSecurityModel);
  
  return s;
}


HeaderData &
HeaderData::change_value(const asnDataType &right) {

    const HeaderData &ref = dynamic_cast<const HeaderData &>(right);

    if (this->msgID && ref.msgID)
        *(this->msgID) = *(ref.msgID);
    else {
        if (this->msgID)
            delete this->msgID;
        if (ref.msgID)
            this->msgID = ref.msgID->clone();
        else
            this->msgID = 0;
    }
    
    if (this->maxMsgSize && ref.maxMsgSize)
        *(this->maxMsgSize) = *(ref.maxMsgSize);
    else {
        if (this->maxMsgSize)
            delete this->maxMsgSize;
        if (ref.maxMsgSize)
            this->maxMsgSize = ref.maxMsgSize->clone();
        else
            this->maxMsgSize = 0;
    }
    
    if (this->msgFlags && ref.msgFlags)
        *(this->msgFlags) = *(ref.msgFlags);
    else {
        if (this->msgFlags)
            delete this->msgFlags;
        if (ref.msgFlags)
            this->msgFlags = ref.msgFlags->clone();
        else
            this->msgFlags = 0;
    }
    
    if (this->msgSecurityModel && ref.msgSecurityModel)
        *(this->msgSecurityModel) = *(ref.msgSecurityModel);
    else {
        if (this->msgSecurityModel)
            delete this->msgSecurityModel;
        if (ref.msgSecurityModel)
            this->msgSecurityModel = ref.msgSecurityModel->clone();
        else
            this->msgSecurityModel = 0;
    }
    

    return *this;
}
