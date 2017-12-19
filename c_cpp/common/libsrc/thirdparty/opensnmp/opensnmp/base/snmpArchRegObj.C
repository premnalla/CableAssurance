// snmpArchRegObj.C
//

#include "config.h"
#include "snmpArchRegObj.H"


snmpArchRegObj::snmpArchRegObj() {
  fifo          = NULL;
  enginePtr     = NULL;
  archName      = string("");
  exitMutex     = new snmpMutexObj;
  exitCondition = new snmpConditionObj;
}

snmpArchRegObj::snmpArchRegObj(snmpEngine *enginePtr) {
  enginePtr     = enginePtr;
  fifo          = NULL;
  archName      = string("");
  exitMutex     = new snmpMutexObj;
  exitCondition = new snmpConditionObj;
}

snmpArchRegObj::~snmpArchRegObj() {
  if (exitMutex)     delete exitMutex;
  if (exitCondition) delete exitCondition;
}

snmpFIFOObj *
snmpArchRegObj::create_fifo() {
  this->fifo = new snmpFIFOObj();
  return (this->fifo);
}
