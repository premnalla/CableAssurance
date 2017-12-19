// snmpDispArchRegObj.C
//

#include "config.h"
#include "snmpMPArchRegObj.H"
#include <iostream>


snmpMPArchRegObj::snmpMPArchRegObj(snmpEngine *theEngine) 
  : snmpArchRegObj(theEngine)  {
  this->enginePtr = theEngine;
}

snmpMPArchRegObj::~snmpMPArchRegObj() {
}

void
snmpMPArchRegObj::init() {
  snmpMPArchObj   *newMP;

  newMP = new snmpMPArchObj(this->enginePtr);

  newMP->fifoPtr   = this->fifo;

  newMP->main_loop();

  delete newMP;

} // init
