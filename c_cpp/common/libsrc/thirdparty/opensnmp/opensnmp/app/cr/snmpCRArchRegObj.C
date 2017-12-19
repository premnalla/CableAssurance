// snmpCRArchRegObj.C
//

#include "config.h"
#include <iostream>
#include <pthread.h>

#include "snmpCRArchRegObj.H"
#include "snmpEngine.H"
#include "snmpCRArchObj.H"

snmpCRArchRegObj::snmpCRArchRegObj(snmpEngine *theEngine,
		   std::set<TransportAddress> *listenTAddresses) { //= NULL
  this->theEngine        = theEngine;
  this->listenTAddresses = listenTAddresses;
}

snmpCRArchRegObj::~snmpCRArchRegObj() {
}

void
snmpCRArchRegObj::init() {
  snmpCRArchObj  *newCR = new snmpCRArchObj(theEngine, this->fifo, 
					    this->listenTAddresses);

  newCR->main_loop();
  delete newCR;
}
