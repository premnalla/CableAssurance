#include "config.h"
#include <stdio.h>
#include <iostream>
#include <typeinfo>
#include <vector>
#include <unistd.h>
#include <pthread.h>
#include <signal.h>
#include <assert.h>

#include "snmpAppProcessPduASI.H"
#include "snmpRegObj.H"
#include "snmpCRArchRegObj.H"
#include "snmpDispArchRegObj.H"
#include "snmpEngine.H"
#include "snmpCRArchObj.H"
#include "snmpDatabaseObj.H"
#include "snmpCRRegisterTable.H"
#include "debug.H"
#include "configMgr.H"
#include "cfgInteger.H"
#include "cfgString.H"
#include "cfgHelp.H"
#include "TransportAddress.H"
#include "snmpMutexObj.H"


snmpEngine  *theEngine = NULL;
bool         shuttingdown = false;
snmpMutexObj exitMutex;

RETSIGTYPE
crShutdown(int a)  {
  cerr << "Segmentation Fault\n";
  abort(); // going down hard
}

void
register_cr_signals() {
  signal(SIGSEGV, crShutdown);
}


int main(int argc, char *argv[]) {
  string                  crRegName = string(""), dispName = string("");
  snmpFIFOObj            *searchPtr = NULL;
  snmpRegObj             *theReg = NULL;
  snmpCRArchRegObj       *CRArchRegPtr = NULL;
  int                    cr_port = 161;
  string                 net_add = "0.0.0.0";

  register_cr_signals();

  ConfigMgr config( true ); // register default options

  config.register_option( new IntegerOption( "-p", "--port", cr_port,
					     "port number to listen on" ) );
  config.register_option( new StringOption( "-n", "--net_address", net_add,
				  "address to listen on (e.g. 127.0.0.1)" ) );
  config.register_option( new cfgHelp() );

  config.parse_command_line(argc, argv);

  // must create crDebugObj after initing debug from the command line.
  DEBUGCREATE_L(crDebugObj, "crmain");

#ifndef NO_PERSISTENCE
  snmpDatabaseObj * theDatabase = NULL;
  try {
    theDatabase = snmpDatabaseObj::openSnmpDatabaseObj();
  }
  catch( ... ) {
    assert(theDatabase==NULL);
  }
  if( theDatabase == NULL ) {
    cerr << "Could not open database in '" << snmpDatabaseObj::get_dbPath()
	 << "'" << endl;
    exit( 99 );
  }
#endif

  /*
   * start the rest of the engine.
   */
  theEngine = new snmpEngine();
  if( cr_port ) {
    theEngine->set_engineDefaultCRPort(cr_port);
    theEngine->set_engineDefaultNRPort(cr_port + 1);
  }
  // set cg port
  theEngine->set_engineDefaultCGPort( 0 ); // any

  /*
   * start the CR
   */
  crRegName = SNMP_CR_ARCH_NAME; // name of the CR architecture in the registry

  // create CR registration object.
  set<TransportAddress> *tAdds = NULL;
  if (cr_port != 0) {
    tAdds = new set<TransportAddress>;
    tAdds->insert(TransportAddress(short(cr_port), net_add));
  }
  CRArchRegPtr   = new snmpCRArchRegObj(theEngine, tAdds);

  // theReg = new snmpRegObj();
  theReg = theEngine->get_archRegistry(false);

  // Register it.
  theReg->addNewArchInit( crRegName, CRArchRegPtr);

  // See if we can find the CR's FIFO in the registry.
  // Since it isn't started yet, this will start the CR
  // architecture object in a seperate thread.
  searchPtr = theReg->findArchFIFO(crRegName);  

  // have the engine register the rest of the modules, which must be
  // done after ours since we must be able to accept table
  // registrations.
  // done in snmpCRArchObj...
  //  theEngine->init();  // registers the disp, mp, sm, etc.
  
  DEBUG5_L(crDebugObj, "first map dump");

  // just to see what's in the registry
  theReg->dumpMap();

  if (searchPtr == NULL) {
    cerr << "ERROR: searchPtr is NULL, CR failed to start?\n";
    exit(1);
  }
  
  //  cerr << "\nCRMAIN, thread id '" << pthread_self() << "'\n";

  // wait for signal to exit
  int sigcaught;
  sigset_t handle_sig_set;

  sigemptyset(&handle_sig_set);
  sigaddset(&handle_sig_set, SIGTERM);
  sigaddset(&handle_sig_set, SIGINT);
  //  sigaddset(&handle_sig_set, SIGSEGV);
  sigaddset(&handle_sig_set, SIGABRT);

  sigwait(&handle_sig_set, &sigcaught);

  string sigName = string("unknown");
  if (sigcaught == SIGTERM) sigName = string("terminate");
  else if (sigcaught == SIGINT)  sigName = string("interrupt");
  else if (sigcaught == SIGABRT)  { 
    exit(1);
  }

  DEBUG0_L(crDebugObj,"caught " + sigName + " signal, exiting");
  theEngine->shutdownAll();  
  DEBUG5_L(crDebugObj,"Fin!\n");
  exit(0);

}

