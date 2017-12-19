//
// snmpMessageObj
//

#include "config.h"
#include "snmpMessageObj.H"
#include "debug.H"

// Define static objects.
snmpMutexObj  *snmpMessageObj::msgCountMutex   = new snmpMutexObj;
int            snmpMessageObj::currentMsgCount = 1;


// you can pass in the value for the internalMsgID during construction.
// Otherwise, the internalMsgID of the copyFromMsg will be used. If this
// is NULL, a unique ID will be generated.
// The synch data object can also be passed in at construction time.
snmpMessageObj::snmpMessageObj(snmpMessageObj   *copyFromMsg, // = NULL,
			       int               newID,       // = 0,
			       snmpSynchDataObj *newData,     // = NULL,
			       snmpFIFOObj      *theFIFO)  {  // = NULL
  DEBUGNEWTAG_L(msgDebugObj, "snmpMessageObj");
  if (newID == 0) {
    if (copyFromMsg == NULL) {
      this->msgCountMutex->lock("snmpMessageObj:constructor");
      this->internalMsgID = this->currentMsgCount++;
      this->msgCountMutex->unlock("snmpMessageObj:constructor");
    }
    else this->internalMsgID = copyFromMsg->internalMsgID;
  }
  else this->internalMsgID = newID;
  
  this->synchData = newData;
  this->returnFIFO   = theFIFO;
  DEBUG9_L(msgDebugObj, "snmpMessageObj constructor, ID = " << this->internalMsgID);
}

snmpMessageObj::snmpMessageObj(const snmpMessageObj & ref)  {
  DEBUGNEWTAG_L(msgDebugObj, "snmpMessageObj");
  this->internalMsgID = ref.internalMsgID;
  
  if (ref.synchData)
      this->synchData = ref.synchData->clone();
  else
      this->synchData = NULL;
  this->returnFIFO   = ref.returnFIFO;
  DEBUG9_L(msgDebugObj, "snmpMessageObj constructor, ID = " << this->internalMsgID);
}

snmpMessageObj::~snmpMessageObj()  {
  //  DEBUGCREATE_L(smoDestDebObj, "destructMessage");
  if (NULL != this->synchData)  delete this->synchData;
  //  DEBUG9_L(msgDebugObj, "snmpMessageObj destructor");
  //  DEBUG5_L(smoDestDebObj, "Destroying id '" << internalMsgID << "'");
  //  DEBUGDESTROY(msgDebugObj);
}


// clone returns a copy of the current (this) message object.
// Notice that while synchData and internalID (not a pointer anyway) are 
// new memory copies, returnFIFO is the same pointer. 
snmpMessageObj *
snmpMessageObj::clone() const {
  snmpMessageObj *retObj = new snmpMessageObj(*this);

  if (retObj->synchData != 0) {
    retObj->synchData = retObj->synchData->clone();
  }

  return(retObj);
}


int
snmpMessageObj::get_ID() {
  return(this->internalMsgID);
}


bool
snmpMessageObj::addSynchData(snmpSynchDataObj *newData) {
  if (NULL != this->synchData)  delete this->synchData;

  this->synchData = newData;
  return(true);
}

snmpSynchDataObj
*snmpMessageObj::get_synchData(bool extract)  {
  snmpSynchDataObj  *retObj = this->synchData;
  if (extract) this->synchData = NULL;

  return(retObj);
}


bool
snmpMessageObj::set_returnFIFO(snmpFIFOObj *FIFO) {
  // never delete a FIFO!?
  //  if (NULL != this->returnFIFO) 
  //    delete this->returnFIFO;
 
  this->returnFIFO = FIFO;
  return(true);
}

snmpFIFOObj *
snmpMessageObj::get_returnFIFO(bool extract)  {
  snmpFIFOObj      *retObj = this->returnFIFO;
  if (extract) this->returnFIFO = NULL;
  
  return(retObj);
}
  
