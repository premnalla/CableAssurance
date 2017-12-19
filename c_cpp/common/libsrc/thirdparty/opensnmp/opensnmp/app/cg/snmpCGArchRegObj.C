// snmpCGArchRegObj.C
//

#include "config.h"
#include <iostream>
#include <pthread.h>

#include "snmpCGArchRegObj.H"

snmpCGArchRegObj::snmpCGArchRegObj(snmpEngine *theEngine) 
  : snmpArchRegObj(theEngine) {
  this->enginePtr = theEngine;
}

snmpCGArchRegObj::~snmpCGArchRegObj() {
}

void
snmpCGArchRegObj::init() {
  snmpCGArch  *newCGArch;

  newCGArch             = new snmpCGArch(this->enginePtr);
  newCGArch->cgArchFifo = this->fifo;

  newCGArch->init();

  delete newCGArch;

}
