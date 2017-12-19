// snmpDispArchRegObj.C
//

#include "config.h"
#include "snmpUSMArchRegObj.H"
#include <iostream>

snmpUSMArchRegObj::snmpUSMArchRegObj(snmpEngine *enginePtr) 
  : snmpArchRegObj(enginePtr) {
  this->enginePtr = enginePtr;
}

snmpUSMArchRegObj::~snmpUSMArchRegObj() {
}

void
snmpUSMArchRegObj::init() {
  snmpUSMArchObj   *newUSM;

  //  cout << "top of init - for USM Arch\n";
  newUSM = new snmpUSMArchObj(this->enginePtr);

  newUSM->fifoPtr   = this->fifo;
  //  newUSM->enginePtr = this->enginePtr;

  newUSM->main_loop();

  delete newUSM;

} // init
