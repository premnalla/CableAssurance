//
// snmpSynchDataObj
//

#include "config.h"
#include "snmpSynchDataObj.H"

snmpSynchDataObj::snmpSynchDataObj(snmpMessageObj *newMsgObj,
				   asnDataType    *extraData1)  {
  this->msgObject  = newMsgObj;
  this->extraData1 = extraData1;
}

snmpSynchDataObj::snmpSynchDataObj(snmpSynchDataObj const &synchObj) {
  if (synchObj.msgObject) this->msgObject = (synchObj.msgObject)->clone();
  else this->msgObject = 0;
  if (synchObj.extraData1) this->extraData1 = (synchObj.extraData1)->clone();
  else this->extraData1 = 0;
}

snmpSynchDataObj::~snmpSynchDataObj()  {
  if (NULL != this->msgObject)  delete this->msgObject;
  if (NULL != this->extraData1) delete this->extraData1;
}

snmpMessageObj*
snmpSynchDataObj::get_msgObject(bool extract) {
  snmpMessageObj *ret = this->msgObject;
  if (extract)
    this->msgObject = 0;
  return ret;
}

void 
snmpSynchDataObj::set_msgObject(snmpMessageObj *msgObject) {
  if (this->msgObject) 
    delete this->msgObject;
  this->msgObject = msgObject;
}
