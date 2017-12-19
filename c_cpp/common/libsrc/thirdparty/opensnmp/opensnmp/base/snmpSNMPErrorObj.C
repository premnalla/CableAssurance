#include "config.h"
#include "snmpSNMPErrorObj.H"
#include "debug.H"


snmpSNMPErrorObj::snmpSNMPErrorObj(int newError)
  : snmpErrorObj(newError)
{
  if (newError != int(NO_ERROR)) { 
    this->set_error(int(SNMP_ERROR));
  }
  
  this->snmpErrorValue  = newError;
  this->snmpErrorList   = NULL;
  this->securityLevel   = NULL;
  this->contextName     = NULL;
  this->contextEngineID = NULL;
}

snmpSNMPErrorObj::snmpSNMPErrorObj(VarBindList *newList, int newError)
  : snmpErrorObj(int(SNMP_ERROR))
{
  this->snmpErrorList  = newList;
  this->snmpErrorValue = newError;
  this->securityLevel   = NULL;
  this->contextName     = NULL;
  this->contextEngineID = NULL;
}

snmpSNMPErrorObj::~snmpSNMPErrorObj()  {
  DEBUGCREATE_L(debugObj, "snmpSNMPErrorObj");
  DEBUG9_L(debugObj, "snmpSNMPErrorObj:~snmpSNMPErrorObj: deleting!\n");
  if (this->snmpErrorList   != NULL) { delete this->snmpErrorList; }
  if (this->securityLevel   != NULL) { delete this->securityLevel; }
  if (this->contextName     != NULL) { delete this->contextName; }
  if (this->contextEngineID != NULL) { delete this->contextEngineID; }
}

Integer32 *
snmpSNMPErrorObj::get_securityLevel(bool extract)  {
  Integer32 *retVal = this->securityLevel;
  if (extract) { this->securityLevel = NULL; }
  return(retVal);
}

OctetString *
snmpSNMPErrorObj::get_contextName(bool extract)  {
  OctetString *retVal = this->contextName;
  if (extract) { this->contextName = NULL; }
  return(retVal);
}

OctetString *
snmpSNMPErrorObj::get_contextEngineID(bool extract )  {
  OctetString *retVal = this->contextEngineID;
  if (extract) { this->contextEngineID = NULL; }
  return(retVal);
}

void
snmpSNMPErrorObj::set_securityLevel(Integer32 *newVal)  {
  if (this->securityLevel != NULL)  { delete this->securityLevel; }
  this->securityLevel = newVal;
}

void
snmpSNMPErrorObj::set_contextName(OctetString *newVal)  {
  if (this->contextName != NULL)  { delete this->contextName; }
  this->contextName = newVal;
}

void
snmpSNMPErrorObj::set_contextEngineID(OctetString *newVal)  {
  if (this->contextEngineID != NULL)  { delete this->contextEngineID; }
  this->contextEngineID = newVal;
}


VarBindList
*snmpSNMPErrorObj::get_varBindList(bool extract ) {
  VarBindList  *retList = this->snmpErrorList;

  if (extract) { this->snmpErrorList = NULL; }
  return(retList);
}

void
snmpSNMPErrorObj::set_varBindList(VarBindList *newList) {
  if (this->snmpErrorList != NULL) { delete this->snmpErrorList; }

  this->snmpErrorList = newList;
}

int
snmpSNMPErrorObj::get_SNMPError() {
  return(this->snmpErrorValue);
}

void
snmpSNMPErrorObj::set_SNMPError(int newError) {
  this->snmpErrorValue = newError;;

  this->set_success(false);
  this->set_error(SNMP_ERROR);
}

string
snmpSNMPErrorObj::toString() {
  string retString = "SNMP protocal error : ";
  
  if ( NULL != snmpErrorList ) {
    VarBind *vb;
    for (int i = 1; i <= snmpErrorList->size(); i++) {
      retString = retString + 
	snmpErrorList->get_varBindNum(1, false)->get_OID()->toDisplayString(SINGLE_NODE);
    }
  }
  else {
    retString = retString + string("Unknown Error!");
  }

  return(retString);
}

