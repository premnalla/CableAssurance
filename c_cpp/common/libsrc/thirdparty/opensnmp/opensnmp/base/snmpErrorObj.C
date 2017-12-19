#include "config.h"
#include "snmpErrorObj.H"


snmpErrorObj::snmpErrorObj(int newError, string newErrorText)
{
  if (newError == int(NO_ERROR)) { this->success = true; }
  else { this->success = false;}

  this->errorValue = newError;
  this->errorText  = newErrorText;
}

snmpErrorObj::snmpErrorObj(const snmpErrorObj & ref)
{
  this->success    = ref.success;
  this->errorValue = ref.errorValue;
  this->errorText  = ref.errorText;
}

snmpErrorObj::~snmpErrorObj()  {
}

bool
snmpErrorObj::get_success() {
  return(this->success);
}

void
snmpErrorObj::set_success(bool is_success) {
  this->success = is_success;
}

int
snmpErrorObj::get_error() {
  return(this->errorValue);
}

void
snmpErrorObj::set_error(int newError) {
  if (newError != NO_ERROR) { this->success = false; }
  this->errorValue = newError;;
}

std::string
snmpErrorObj::toString() {
  using namespace std;

  switch(this->errorValue) {
  case NO_ERROR:
    return string("NO ERROR");
  case UNKNOWN_ERROR:
    return string("UNKNOWN ERROR: " + errorText);
  case BAD_MSGID:
    return string("BAD MSGID: " + errorText);
  case NO_STATE_REFERENCE:
    return string("NO STATE REFERENCE: " + errorText);
  case UNKNOWN_SECURITY_LEVEL:
    return string("UNKNOWN SECURITY LEVEL: " + errorText);
  case NO_PDU:
    return string("NO PDU: " + errorText);
  case BAD_MSG_DATA:
    return string("BAD MSG DATA: " + errorText);
  case ENCRYPTION_ERROR:
    return string("ENCRYPTION ERROR: " + errorText);
  case AUTHENTICATION_ERROR:
    return string("AUTHENTICATION ERROR: " + errorText);
  case ASN_ENCODE_ERROR:
    return string("ASN ENCODE ERROR: " + errorText);
  case ASN_PARSE_ERROR:
    return string("ASN PARSE ERROR: " + errorText);
  case ADD_USM_USER_ERROR:
    return string("Failed to add USM user: " + errorText);
  case USM_USER_EXISTS_ERROR:
    return string("USM user exists");
  case DISP_INTERNAL_MESSAGE_ERROR:
    return string("Disptacher had an internal message error");
  case TIMEOUT_ERROR:
    return string("Timed out");
  case DISCOVERY_ERROR:
    return string("Error during engine ID discovery");
  case SNMP_ERROR:
    return string("SNMP ERROR: " + errorText);
  defalt:
    return string("Unrecognized error value: " + errorText);
  };

  return(string("Unrecognized error value: " + errorText));
}


