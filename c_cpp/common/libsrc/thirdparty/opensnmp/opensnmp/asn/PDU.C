/* PDU.C */
//
// PDU
//
// PDUs is a sequence of variable bindings, which are in turn
// sequences of OID and VALUE.
//

#include "config.h"
#include <stdio.h>
#include "PDU.H"
#include "asnParseError.H"
#include "snmpConstants.H"

//
// PDU
//

// static ----

int           PDU::requestID_generator = 1;
snmpMutexObj  PDU::requestID_mutex;


PDU::PDU(berTag pdu_type, 
	 int new_request_id, 
         int error_index, 
	 int error_status, 
	 VarBindList *varbinds, 
	 PDU_Version version) 
  : asnDataType(pdu_type)
{
  if ( new_request_id == 0 ) {
    create_new_requestID();
  }
  else {
    this->request_id = new_request_id;
  }

  this->error_index = error_index;
  this->error_status = error_status;
  this->varbinds = varbinds;
  this->version = version;
}

PDU::PDU(const PDU &thePDU)
  : asnDataType(thePDU.get_tag())
{
  this->request_id   = thePDU.request_id;
  this->error_index  = thePDU.error_index;
  this->error_status = thePDU.error_status;
  if (thePDU.varbinds)
      this->varbinds     = thePDU.varbinds->clone();
  else 
      this->varbinds     = NULL;
  this->version      = thePDU.version;
}

PDU::~PDU()
{
  EraseData();
  delete varbinds;
}

void
PDU::EraseData()
{
  this->request_id.EraseData();
  this->error_index.EraseData();
  this->error_status.EraseData();
  if (this->varbinds)
    this->varbinds->EraseData();
}


int
PDU::create_new_requestID() {
  requestID_mutex.lock("Creating PDU request ID");
  this->request_id = requestID_generator++;
  requestID_mutex.unlock("Creating PDU request ID");

  return this->request_id;
}


//
// Add a variable to the binding list.
//
void
PDU::Add(VarBind *vb)
{
  varbinds->Add(vb);
}

//
// Encode the data into a buffer, but in reverse.
//
char *
PDU::EncodeData(char *cp) const
{
  cp = varbinds->asnEncode(cp);
  cp = error_index.asnEncode(cp);
  cp = error_status.asnEncode(cp);
  cp = request_id.asnEncode(cp);
  return cp;
}

//
// Decode the data from a char * buffer.
//
void
PDU::DecodeData(char *cp, int length)
{
  char *tcp = cp;
  EraseData();

  tcp = request_id.asnDecode(tcp, length);
  tcp = error_status.asnDecode(tcp, (length - (tcp - cp)));
  tcp = error_index.asnDecode(tcp, (length - (tcp - cp)));
  tcp = varbinds->asnDecode(tcp, (length - (tcp - cp)));
}

// DecodeHeader must be done seperately here, since we can be one of a
// few data types, which is different than the default asnDataType usage.
char *
PDU::DecodeHeader(char *cp, int *thelength)
{
  set_tag((berTag) (unsigned char) *cp++);
  if (get_tag() < BER_TAG_PDU_GET || get_tag() > BER_TAG_PDU_REPORT)
    throw asnUsageError("Asked to decode a PDU type we don't know about.");
  return DecodeLength(cp,thelength);
}


//
//   print it: turn our object into a textual representation...
//      buf:      buffer to write to.
//      prefix:   print this prefix before all outgoing print statements.
//      maxlines: maximum number of lines we can output.
//
string PDU::toString() const
{
  string s;

  s = "PDU: " + get_type() + "\n";
  s = s + "  requstID: " + string(request_id) + 
    ", error_index: " + string(error_index) + 
    ", error_status: " + string(error_status);

  if ( (error_status >= 0) && (error_status <= SNMP_CONSTANTS::MAX_SNMP_ERR) )
    s = s + " : " + SNMP_CONSTANTS::SNMP_ERR_STRING_ARRAY[error_status];

  s = s + "\n" + varbinds->toString() + "\n";
  return s;
}

PDU::operator string() const
{
  string s;

  s = "PDU: " + get_type() + "  requstID: " + string(request_id) + 
    ", error_index: " + string(error_index) + 
    ", error_status: " + string(error_status) + 
    ", " + string(*varbinds);
  return s;
}

int PDU::get_errorIndex()
{
  return error_index;
}

void PDU::set_errorIndex(int error_index)
{
  this->error_index = error_index;
}

int PDU::get_errorStatus()
{
  return error_status;
}

void PDU::set_errorStatus(int error_status)
{
  this->error_status = error_status;
}

int PDU::get_requestID()
{
  return request_id;
}

void PDU::set_requestID(int request_id)
{
  this->request_id = request_id;
}

VarBindList *PDU::get_varBindList(bool extract) {
  VarBindList *vbl = varbinds;
  if (extract)
    varbinds = 0;
  return vbl;
}

VarBind *PDU::get_varBind(int index, bool extract) {
  return varbinds->get_varBindNum(index, extract);
}

PDU_Version
PDU::get_version() {
  return version;
}

PDU_Type 
PDU::get_pduType() {
  return this->get_tag();
}

void
PDU::set_pduType(PDU_Type type) {
    set_tag(type);
}

void PDU::set_version(PDU_Version version) {
  this->version = version;
}

void
PDU::set_varBindList(VarBindList *newvbl) {
    if (varbinds != NULL)
        delete varbinds;
    varbinds = newvbl;
}

string
PDU::get_type() const {
    switch (get_tag()) {
      case BER_TAG_PDU_GET:
        return string("GET");
        
      case BER_TAG_PDU_GETNEXT:
        return string("GETNEXT");

      case BER_TAG_PDU_RESPONSE:
        return string("RESPONSE");

      case BER_TAG_PDU_SET:
        return string("SET");

      case BER_TAG_PDU_TRAP:
        return string("TRAP");

      case BER_TAG_PDU_GETBULK:
        return string("GETBULK");

      case BER_TAG_PDU_INFORM:
        return string("INFORM");

      case BER_TAG_PDU_TRAP2:
        return string("TRAP2");

      case BER_TAG_PDU_REPORT:
        return string("REPORT");

        default:        
            return string("UNKNOWN");
    }
}

PDU &
PDU::change_value(const asnDataType &right) {
    const PDU &ref = dynamic_cast<const PDU &>(right);
    this->version = ref.version;
    this->request_id = ref.request_id;
    this->error_index = ref.error_index;
    this->error_status = ref.error_status;
    if (varbinds)
        delete this->varbinds;
    if (ref.varbinds)
        this->varbinds = ref.varbinds->clone();
    else
        this->varbinds = 0;
    
    return *this;
}
