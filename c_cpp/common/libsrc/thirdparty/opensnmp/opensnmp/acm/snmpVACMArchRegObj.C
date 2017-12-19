// snmpVACMArchRegObj.C
//

#include "config.h"
#include <iostream>
#include <pthread.h>

#include "snmpVACMArchRegObj.H"

snmpVACMArchRegObj::snmpVACMArchRegObj() {
}

snmpVACMArchRegObj::~snmpVACMArchRegObj() {
}

void
snmpVACMArchRegObj::init() {
  snmpVACMArchObj  *newVACM = new snmpVACMArchObj(this->fifo); 
  newVACM->main_loop();

  delete newVACM;
}
