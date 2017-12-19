#include <config.h>

#include <stdio.h>
#include <iostream>
#include <typeinfo>
#include <vector>
#include <unistd.h>
#include <pthread.h>
#include <list>
#include <signal.h>

#include "CG.H"
#include "snmpAppProcessResponsePduASI.H"
#include "snmpDispSendPduASI.H"
#include "snmpDispUnregisterEngIdASI.H"
#include "snmpEngine.H"
#include "asnDataTypes.H"
#include "debug.H"
#include "sendPduArgs.H"
#include "cfgSendPduArgs.H"
#include "cfgVarBind.H"
#include "cfgSnmpEngine.H"
#include "cfgInteger.H"
#include "cfgHelp.H"
#include "cfgDebug.H"

// global var
namespace CGMain {
  DEBUGDECLARE(Debug);

  int     port = 161;

  sigset_t blocked_sigs;
};


void sigsetup() {

  sigemptyset(&CGMain::blocked_sigs);
  sigaddset(&CGMain::blocked_sigs, SIGHUP);
  sigaddset(&CGMain::blocked_sigs, SIGINT);
  sigaddset(&CGMain::blocked_sigs, SIGQUIT);
  sigaddset(&CGMain::blocked_sigs, SIGABRT);
  sigaddset(&CGMain::blocked_sigs, SIGTERM);
  sigaddset(&CGMain::blocked_sigs, SIGSTOP);

  //  pthread_sigmask(SIG_BLOCK, &CGMain::blocked_sigs, NULL);
  sigprocmask(SIG_BLOCK, &CGMain::blocked_sigs, NULL);
} // sigsetup


// checks for signals and returns true if an exit signal is
// detected.  (e.g. SIGINT).
bool 
sigexit() {
  sigset_t waiting;
  int      caught;

  sigpending(&waiting);

  if ( sigismember(&waiting, SIGHUP) ) {
    cerr << "\ncaught SIGHUP\n\n";
  }
  else if ( sigismember(&waiting, SIGINT) ) {
    cerr << "\ncaught SIGINT, exiting\n\n";
    return true;
  }
  else if ( sigismember(&waiting, SIGQUIT) ) {
    cerr << "\ncaught SIGQUIT, exiting\n\n";
    return true;
  }
  else if ( sigismember(&waiting, SIGABRT) ) {
    cerr << "\ncaught SIGABRT, exiting\n\n";
    return true;
  }
  else if ( sigismember(&waiting, SIGTERM) ) {
    cerr << "\ncaught SIGTERM, exiting\n\n";
    return true;
  }
  else if ( sigismember(&waiting, SIGSTOP) ) {
    cerr << "\ncaught SIGSTOP, exiting\n\n";
    return true;
  }

  return false;
} // sigexit


// ownership of vlist are given to this procedure!
snmpDispSendPduASI *createDispSendPdu(sendPduArgs &sargs,
				      snmpFIFOObj *cgFIFO) {

  return (new snmpDispSendPduASI
	  (sargs.get_transportDomain(true),
	   sargs.get_transportAddr(true),
	   new Integer32(SNMP_CONSTANTS::SNMP_MP_MODEL_SNMPv3),
	   new Integer32(SNMP_CONSTANTS::SNMP_SEC_MODEL_USM),
	   sargs.get_userName(true),
	   sargs.get_secLevel(true),
	   sargs.get_contextEngId(true),
	   sargs.get_contextName(true),
	   new PDU_Version(PDU_VERSION_2),
	   sargs.get_msgPDU(true),
	   new bool(true),
	   cgFIFO)) ;

}


bool findEndOrError(PDU *pdu) {

  // if it is a response keep checking
  if ( (pdu) && (pdu->get_pduType() ==  PDU_Type(BER_TAG_PDU_RESPONSE)) ) {
    VarBindList *varList = pdu->get_varBindList();

    if (varList) {
      VarBindList::const_iterator v_it = varList->begin();

      while (v_it != varList->end()) {
	berTag  tag = ((*v_it)->get_value())->get_tag();
	// if it is any of the below, stop walking.
	if ( (tag ==  BER_TAG_ENDOFMIBVIEW) || 
	     (tag ==  BER_TAG_NOSUCHOBJECT) ||
	     (tag ==  BER_TAG_NOSUCHINSTANCE) ) {
	  return true;
	}
	v_it++;
      }
    }
    else  return true; // error, no varbind list.

  }
  else  return true; // error, no pdu or it's not a response.

  // end or error not found.
  return false;
}


int main(int argc, char *argv[]) {
  // .1.3.6.1.2.1.1
  // .1.3.6.1.2.1.1.9.1
  // .1.3.6.1.2.1.1.1.0
  int                    count = 1;
  snmpMessageObj        *newMsg;
  sendPduArgs            sendArgs;
  CG                    *theCG;
  snmpFIFOObj           *cgFIFO = NULL, *dispFIFO = NULL;
  VarBindList           *vbargs = new VarBindList();

  sigsetup();
  theCG = new CG();
  
  ConfigMgr config( true ); // register default options
  config.register_option( new SendPduArgsOptions( sendArgs ) );
  config.register_option( new IntegerOption( "-p", "--port", CGMain::port,
					     "port number to send to" ) );
  config.register_option( new SnmpEngineOptions( theCG->get_engine() ) );
  config.register_option( new VarBindOptions( vbargs ) );
  config.register_option( new cfgHelp() );

  DEBUGCREATE(CGMain::Debug, "CGMain");

  int argcount;
  if( config.parse_command_line(argc, argv, argcount) ) {
    theCG->shutdown();
    delete theCG;
    cerr << "ERROR: incorrect command line arguments\n";
    exit(1);
  }

  try {
    // set hostname and port
    if( argc <= argcount ) {
      cerr << "no hostname specified, using 'localhost'" << endl;
      sendArgs.set_transportAddr(new TransportAddress(short(CGMain::port)));
    }
    else
      sendArgs.set_transportAddr(new TransportAddress(CGMain::port,
						      argv[argcount]));
  } catch (snmpStringException &e) {
      cerr << "Error: " << e.what() << ", exiting\n";
      theCG->shutdown();
      exit(1);
  } 

  //  DEBUGCREATE(CGMain::Debug, "CGMain");

  // get the pdu to for the CG parameter.
  PDU *loc_pdu = sendArgs.get_msgPDU(true);
  // place vbargs and timeout in sendPduArgs
  loc_pdu->set_varBindList( vbargs );

  PDU *pdu = loc_pdu->clone();

  bool walking = sendArgs.get_isWalk();
  bool    done = false;
  bool signalled_exit = false;

  PDU                    *retPDU = NULL;
  snmpStatusInfo         *status = NULL;
  OctetString         *contEngID = NULL;
  CG::messageTypeEnum    msgType = CG::noMessage;

  // send the pdu
  theCG->send_dont_wait(pdu, sendArgs);

  do {

    // polling every second in order to handle signalling
    msgType = theCG->get_message(retPDU,    status,
				 contEngID, 1);

    if (msgType == CG::response) {
      done = true;
      if (!retPDU) {
	cerr << "Main: Error: reesponse PDU is NULL!\n";
      }
      else  {
	DEBUG5(CGMain::Debug, "retPDU: '" + retPDU->toString() + "'");
	theCG->print_incoming_pdu(retPDU);

	// if we're walking check for error or end of mib.
	if ( walking && !findEndOrError(retPDU) ) {
	  // no error or end, send next message.
	  // copy original PDU, set new varbindlist
	  pdu = loc_pdu->clone();
	  pdu->create_new_requestID();
	  pdu->set_varBindList(retPDU->get_varBindList(true));
	  done = false;
	  // send the next message
	  theCG->send_dont_wait(pdu, sendArgs);
	}
	delete retPDU;
      }
    }
    // internal error
    else if (msgType == CG::internal_error) {
      done = true;

      // create error text
      string errString = "";
      snmpErrorObj *err = NULL;
      errString = string("Local Error Report ");
      if (status) {
	errString = errString + status->toString();
      }
      else {
	errString = errString + ": Unknown Error";
      }
      cout << "Internal Engine Error: " << errString << "\n";
    }

    // external SNMP error, report or pdu error
    else if (msgType == CG::report) {
      done = true;

      string errStr = "";
      // get error varbind, if there is one.
      VarBind *errVB = NULL;
      // default vbIndex to 1, this assumes a REPORT PDU
      int vbIndex = 1;
      
      if ( NULL != (errVB = retPDU->get_varBind(vbIndex)) )  { 
	errStr = (errVB->get_OID())->toDisplayString(SINGLE_NODE);
      }
      else errStr = "unable to get error OID";

      DEBUG5(CGMain::Debug, "Main: retPDU: '" + retPDU->toString() + "'");
      cout << "Error: SNMP Report received: '" << errStr << "'\n";

    }
    // error status in pdu
    else if ( retPDU && (0 != retPDU->get_errorStatus()) ) {
      done = true;

      int errorStatus = 0;

      // for pertinent oid, if there is one and error text, if available.
      string oidStr = string(""),  errStr = string("");

      errorStatus     = retPDU->get_errorStatus();
      int errorIndex  = retPDU->get_errorIndex();

      // get error varbind, if there is one.
      VarBind *errVB = NULL;

      if ( (errorIndex > 0) && 
	   (NULL != (errVB = retPDU->get_varBind(errorIndex))) ) { 
	// from the varbind, get a string of the numeric value of the OID
	oidStr = "Error: OID '" + 
	  (errVB->get_OID())->toDisplayString(NUMERIC) + "'\n";
      }

      // get the error string, from enum if it's not a report, 
      // otherwise from the oid.
      if ( (errorStatus >= 0) && 
	   (errorStatus <= SNMP_CONSTANTS::MAX_SNMP_ERR) ) {
	errStr = SNMP_CONSTANTS::SNMP_ERR_STRING_ARRAY[errorStatus];
      }
      cout << "Error: SNMP PDU with error status received: '" 
	   << retPDU->toString() << "'\n"
	   << "Error: '" << errStr << "'\n" << oidStr;
    }
    // no message
    else if (msgType == CG::noMessage) {
      // just cycle
      done = false;
    }
    // unknown response
    else {
      cout << "Error: Unknown response back from engine\n";
      done = true;
    }

  } while ( !done && !sigexit() );

  theCG->shutdown();
  delete theCG;
  DEBUG9(CGMain::Debug, "Main: cg finished!");
  
  
  exit(0);  // in order to stop the all threads.
}
