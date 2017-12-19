#include "config.h"
#include "snmpStatusInfo.H"


snmpStatusInfo::snmpStatusInfo(bool          is_success) {
			       // unsigned long handle) {
  this->success = is_success;
  //  this->handle = handle;
}

snmpStatusInfo::snmpStatusInfo(bool          is_success, 
			       snmpErrorObj* errObj) {
  this->success = is_success;
  if (NULL != errObj)
    this->pushErrorObj(errObj);
}

snmpStatusInfo::~snmpStatusInfo()  {
}

snmpStatusInfo *
snmpStatusInfo::clone() const {
  return(new snmpStatusInfo(*this));
}

// returns null if empty.
snmpErrorObj
*snmpStatusInfo::popErrorObj() {
  if (!error_q.empty()) {
    snmpErrorObj *retError = error_q.front();
    error_q.pop();
    return(retError);
  }
  return(NULL);
}

// returns null if empty.
snmpErrorObj
*snmpStatusInfo::frontErrorObj() {
  if (!error_q.empty()) {
    return(error_q.front());
  }
  return(NULL);
}

void 
snmpStatusInfo::pushErrorObj(snmpErrorObj *newError) {
  error_q.push(newError);
  success = false;
}

// returns number of error objects currently in queue
int
snmpStatusInfo::num_of_errors() {
  return(int(error_q.size()));
}

string
snmpStatusInfo::toString() {
  string retString;
  std::queue<snmpErrorObj *>  temp_q;
  snmpErrorObj *obj;

  if (success) retString = "Status Info: Succes 'True' :";
  else         retString = "Status Info: Succes 'False' :";

  while ( !error_q.empty() ) {
    obj = error_q.front();
    error_q.pop();
    temp_q.push(obj);
    retString = retString + obj->toString();
  }
  while ( !temp_q.empty() ) {
    obj = temp_q.front();
    temp_q.pop();
    error_q.push(obj);
  }

  return retString;
}
