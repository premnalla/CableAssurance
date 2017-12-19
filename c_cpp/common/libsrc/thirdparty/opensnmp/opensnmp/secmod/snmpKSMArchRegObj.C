// snmpKSMArchRegObj.C
//

#include "config.h"
#include "snmpKSMArchRegObj.H"
#include <iostream>

snmpKSMArchRegObj::snmpKSMArchRegObj(snmpEngine *enginePtr) 
  : snmpArchRegObj(enginePtr) {
}

snmpKSMArchRegObj::~snmpKSMArchRegObj() {
}

void
snmpKSMArchRegObj::init() {
  snmpKSMArchObj   *newKSM;

  //  cout << "top of init - for KSM Arch\n";
  newKSM = new snmpKSMArchObj(this->enginePtr);

  newKSM->fifoPtr   = this->fifo;
  //  newKSM->enginePtr = this->enginePtr;

  newKSM->main_loop();

  delete newKSM;

} // init
