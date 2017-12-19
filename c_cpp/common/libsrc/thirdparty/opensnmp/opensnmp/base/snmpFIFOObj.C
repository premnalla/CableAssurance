// snmpFIFOObj.c
//

#include "config.h"
#include "snmpFIFOObj.H"
#include <pthread.h>
#include "debug.H"


snmpFIFOObj::snmpFIFOObj(const snmpFIFOObj & ref)
{
  DEBUGCREATE(debugObj, "snmpFIFOObj");
  this->fifoMutex = new snmpMutexObj;

  this->fifoCond  = new snmpConditionObj;

  this->isWaiting = false;
}

snmpFIFOObj::snmpFIFOObj() {
  DEBUGCREATE(debugObj, "snmpFIFOObj");
  this->fifoMutex = new snmpMutexObj;

  this->fifoCond  = new snmpConditionObj;

  this->isWaiting = false;
};

snmpFIFOObj::~snmpFIFOObj() {
  delete this->fifoMutex;
  delete this->fifoCond;
  DEBUG9(debugObj, "snmpFIFOObj destructor...\n");
  DEBUGDESTROY(debugObj);
};

void snmpFIFOObj::push(snmpMessageObj *msg) {
  bool checkIsWaiting = false;

  if (this->fifoMutex->lock("snmpFIFOObj::push")) {
    this->fifoQueue.push(msg);
    if (this->isWaiting) {
      checkIsWaiting = true;
    }
    this->fifoMutex->unlock("snmpFIFOObj::push");
  }
  
  if (checkIsWaiting) {
    this->fifoCond->signal("snmpFIFOObj::push");;
  }
}


snmpMessageObj
*snmpFIFOObj::pop() {
  snmpMessageObj *retMsg;

  if (this->fifoMutex->lock("snmpFIFOObj::pop")) {
    while (this->fifoQueue.empty()) {
      isWaiting = true;
      this->fifoCond->wait(this->fifoMutex, "snmpFIFOObj::pop");
      isWaiting = false;
    }

    retMsg = this->fifoQueue.front();
    this->fifoQueue.pop();
    this->fifoMutex->unlock("snmpFIFOObj::pop");
  }
  return(retMsg);
}

snmpMessageObj
*snmpFIFOObj::pop(int seconds) {
  snmpMessageObj *retMsg;

  if (this->fifoMutex->lock("snmpFIFOObj::pop")) {
    if (this->fifoQueue.empty()) {
      isWaiting = true;
      this->fifoCond->wait(this->fifoMutex, seconds, "snmpFIFOObj::pop");
      isWaiting = false;
    }
    // check here and see if it is empty!, return NULL
    if (this->fifoQueue.empty()) {
      retMsg = NULL;
    }
    else {
      retMsg = this->fifoQueue.front();
      this->fifoQueue.pop();
    }
    this->fifoMutex->unlock("snmpFIFOObj::pop");
  }
  return(retMsg);
}


bool
snmpFIFOObj::messageWating() {
  return ( !this->fifoQueue.empty() );
}


void snmpFIFOObj::dump() {
  // DEBUGINIT(debugObj, "snmpFIFOObj");
  while(!this->fifoQueue.empty()) {
    DEBUG9(debugObj, "Dequeuing: " << this->fifoQueue.front() << endl);

    this->fifoQueue.pop();
  }
}
