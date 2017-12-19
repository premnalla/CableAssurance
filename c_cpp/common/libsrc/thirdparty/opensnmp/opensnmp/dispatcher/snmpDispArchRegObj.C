// snmpDispArchRegObj.C
//

#include "config.h"
#include <iostream>
#include <pthread.h>

#include "snmpDispArchRegObj.H"
#include "snmpDispNetObj.H"

snmpDispArchRegObj::snmpDispArchRegObj(snmpEngine *enginePtr) 
  : snmpArchRegObj(enginePtr){
  this->enginePtr = enginePtr;
}

snmpDispArchRegObj::~snmpDispArchRegObj() {
}

void
snmpDispArchRegObj::init() {
  // why should this have to be on the heap, if on stack...crash!!
  snmpDispArchObj  *newDispatcher = new snmpDispArchObj(this->enginePtr); 
  snmpDispNetObj   *newNet        = new snmpDispNetObj(this->enginePtr);

  pthread_t sep_thread;
  int errNum;

  newDispatcher->fifoPtr = this->fifo;
  newDispatcher->netObj  = newNet;
  newNet->dispFIFO       = this->fifo;

  errNum = pthread_create(&sep_thread, 
			  (pthread_attr_t *) NULL, 
                          (void *(*) (void *))
			  snmpDispNetObj::start_net_recv_loop, 
			  newNet);

  newDispatcher->main_loop();
  delete newDispatcher;
  delete newNet;
}
