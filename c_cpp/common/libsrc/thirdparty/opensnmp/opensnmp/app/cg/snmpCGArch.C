#include <config.h>

#include "snmpCGArch.H"

#include <limits>

#include <sys/time.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#include "opensnmpUtilities.H"
#include "snmpAppProcessResponsePduASI.H"
#include "snmpDispRegisterEngIdASI.H"
#include "snmpDispUnregisterEngIdASI.H"

#include "snmpUSMData.H"

using namespace OPENSNMP_UTILITIES;

// STATIC ---------------------

snmpEngine   *snmpCGArch::theEngine    = NULL;
bool          snmpCGArch::cgArchInited = false;
const int     snmpCGArch::MAX_INT      = std::numeric_limits<int>::max();

snmpCGArch::snmpCGArch(snmpEngine *ourEngine) {
#ifndef NODEBUGGING
  cgDebug      = NULL;
#endif
  theEngine    = ourEngine;

  cgArchInited = false;
  theDatabase  = NULL;
  
  cgRegName    = SNMP_CG_ARCH_NAME;      // name of the CG arch in the registry
  dispRegName  = SNMP_DISP_ARCH_NAME;
  theReg       = NULL;
  dispFifo     = NULL;
  cgArchFifo   = NULL;

  // XXX If multiple Command Generators are allowed (not done yet)
  // This will have to be changed so that each one has a different name
  // See init as well, the context name will have to be an input.
  cgRegName    = SNMP_CG_ARCH_NAME;      // name of the CG arch in the registry

  OID::set_displayType(MODULE_AND_NODE);
};

snmpCGArch::~snmpCGArch() {
  // unregistering doesn't really work here
  //  unregister_with_dispatcher();
  DEBUGDESTROY(cgDebug);
}

void
snmpCGArch::init() {

  // only allow one initialization, there should not be more than one
  // cgArch (and its thread) existing/running at a time
  if (cgArchInited)   return;

  cgArchInited = true;

  // need to construct the debug object after command line initialization!
  // XXX for multi CG's, should probably use context name here!
  DEBUGCREATE(cgDebug, "CGArch");

  theReg = theEngine->get_archRegistry(false);         // get the registry

  string tempDbName = string("");
  open_db( tempDbName ); // use default or command line

  // find the Dispatcher fifo so we can register for 
  // PDUs and send requests
  this->dispFifo = theReg->findArchFIFO(dispRegName);
  
  if (this->dispFifo) {
    DEBUG9(cgDebug, "found Disp fifo 0x" + intToString(int(this->dispFifo)));
  } else  {
    DEBUG0(cgDebug, "ERROR: failed to find Dispatcher fifo!");
    return;
  }

  // XXX to allow for multiple CG's (not done yet), a different
  // context name will have to be passed into register_with_dispatcher
  if (!register_with_dispatcher()) {
      DEBUG0(cgDebug, "ERROR: unable to register with dispatcher");
  }

  this->main_loop();
} // snmpCGArch::init


void
snmpCGArch::shutdown() {

  // shutdown engine threads.
  theEngine->shutdown();

} // snmpCGArch::shutdown


bool
snmpCGArch::open_db(string path) {

  try {
    this->theDatabase = snmpDatabaseObj::openSnmpDatabaseObj( path );
    if( this->theDatabase == NULL ) {
      DEBUG0(cgDebug, "open_db: Could not open database.");
      return(false);
    }
  }
  catch(exception &except) {
    DEBUG0(cgDebug, string("open_db: Exception, '") + 
	   typeid(except).name() + "', caught opening database");
    return(false);
  }
  catch(...) {
    DEBUG0(cgDebug, string("open_db: Unknown Exception thrown by ") 
	   + "openSnmpDatabaseObj");
    return(false);
  }

  return(true);
} // open_db


long
snmpCGArch::get_time() {
  timeval currTime;
  gettimeofday(&currTime, NULL);
  return ( long(currTime.tv_sec) );
}


// deleteFromStateMap erases the entry and the reference (including
// ctcMsg if it is non-NULL) from the state map.
// It returns true if the index was found and deleted and false if
// the index was not found.
bool
snmpCGArch::deleteFromStateMap(stateMap_Type &sMap, unsigned long index) {
  DEBUGN(15, cgDebug, "deleteFromStateMap:");  
    
  stateMap_iterator searchItr;

  // find in map
  if ( sMap.end() != (searchItr = sMap.find(index)) ) {
    if (searchItr->second->ctcMsg) delete searchItr->second->ctcMsg;
    delete searchItr->second;
    sMap.erase(searchItr);
    return true;
  }
  else {
    return false;
  }
} // deleteFromStateMap


void 
snmpCGArch::discover_engine_id(snmpCGtoCGArchMsg *ctcMsg,
			       unsigned long      pduHandle,
			       int                retryAttempt) {
  sendPduArgs  *sendArgs = ctcMsg->get_args(false);

  DEBUG9(cgDebug, string("discover_engine_id: sending an engine ID ")
	 + "discovery message");

  // generate a PDU with dummy varbind values...
  PDU *thePDU = new PDU(BER_TAG_PDU_GET,
			0, 0, 0,   // request_id, error_index, error_status
			new VarBindList(new VarBind(new OID(".1.3.6"))));

  snmpDispSendPduASI     *ppmsg = 
    new snmpDispSendPduASI
    (new TransportDomain( *(sendArgs->get_transportDomain()) ),
     sendArgs->get_transportAddr()->clone(),
     new Integer32(SNMP_CONSTANTS::SNMP_MP_MODEL_SNMPv3),
     new Integer32(SNMP_CONSTANTS::SNMP_SEC_MODEL_USM),
     //     sendArgs->get_userName()->clone(),
     new OctetString(SNMP_CONSTANTS::ENG_ID_DISCOVERY_SEC_NAME), 
     new Integer32(SNMP_CONSTANTS::SNMP_SEC_LEVEL_NOAUTH),
     new OctetString(SNMP_CONSTANTS::ENG_ID_DISCOVERY_ENG_ID),
     sendArgs->get_contextName()->clone(),
     new PDU_Version(PDU_VERSION_2),
     thePDU,
     new bool(true),
     cgArchFifo);

  // set send time and
  // put ctcMsg into discovery state map
  ctcMsg->get_args()->set_sendTime(get_time());
  internalSentMsgStateMap[ppmsg->get_ID()] = 
              new intRef(true, ctcMsg, pduHandle, retryAttempt);

  DEBUG9(cgDebug, "discover_engine_id: sending out message with req id '" +
	 intToString(thePDU->get_requestID()) + "'");

  dispFifo->push(ppmsg);  // pass discovery PDU to Dispatcher.

} // discover_engine_id 

void 
snmpCGArch::delete_user(sendPduArgs& sendArgs) {
    string usmName = SNMP_USM_ARCH_NAME;
    DEBUG9( cgDebug, string("deleting user: id=") + 
	    string(*(sendArgs.get_contextEngId())) + 
	    ", secname=" + string(*(sendArgs.get_userName())) );
    usmDelUserMsg *deleteuser =
        new usmDelUserMsg(sendArgs.get_contextEngId()->clone(),
                          sendArgs.get_userName()->clone(), NULL);
    snmpFIFOObj *usmFifo = theReg->findArchFIFO(usmName);
    usmFifo->push(deleteuser);
} // delete_user

    
void 
snmpCGArch::add_user_to_usm(snmpCGtoCGArchMsg *ctcMsg,
			    bool               persistant,
			    bool               overwrite) {
  // should already be checkd as not NULL
  sendPduArgs *args = ctcMsg->get_args(false);

  string usmName = SNMP_USM_ARCH_NAME;
  snmpFIFOObj *usmFifo = theReg->findArchFIFO(usmName);

  if (usmFifo) {
    DEBUG9(cgDebug, "add_user_to_usm: found USM fifo");
  } else  {
    DEBUG0(cgDebug, "Error: add_user_to_usm: failed to find USM fifo, dropping snmpCGtoCGArchMsg");
    // XXX return error to CG class??
    delete ctcMsg;
    return;
  }
  
  // setup add method
  usmAddUserMsg::AddUserMethod_Enum  add;
  if (overwrite)  add = usmAddUserMsg::OVER_WRITE_USER;
  else            add = usmAddUserMsg::DO_NOT_OVER_WRITE_USER;
  
  // setup storage type
  int storage_type;
  if (persistant)  storage_type = SNMP_CONSTANTS::SNMP_STORAGE_PERMANENT;
  else             storage_type = SNMP_CONSTANTS::SNMP_STORAGE_VOLATILE;


  DEBUG9(cgDebug, 
	 string("add_user_to_usm: sending usmAddUserMsg with Eng ID = '") 
	 + string(*(args->get_contextEngId())) + "', secname = '" 
	 + string(*(args->get_userName())) + "'");

  // create the message
  usmAddUserMsg *theMsg = new usmAddUserMsg
    (args->get_contextEngId()->clone(),
     args->get_userName()->clone(),
     cgArchFifo,
     args->get_authOID()->clone(),
     args->get_authKey()->clone(),
     args->get_privOID()->clone(),
     args->get_privKey()->clone(),
     add,
     storage_type);

  // set send time and
  // store snmpCGtoCGArchMsg in add user state map by snmpMessageObj ID
  ctcMsg->get_args()->set_sendTime(get_time());
  addUserStateMap[theMsg->get_ID()] = new intRef(false, ctcMsg);
  DEBUG9(cgDebug, "add_user_to_usm: sending out message with id '" +
	 intToString(theMsg->get_ID()) + "'");
  
  // send the message
  usmFifo->push(theMsg);

} // add_user_to_usm 
 

bool
snmpCGArch::register_with_dispatcher(set<PDU_Type> *pduType) {
  bool              retVal = false;
  set<PDU_Type> *spduTypes = new set<PDU_Type>;

  DEBUG9(cgDebug, string("register_with_dispatcher:'"));
  // pduType is NULL, default to registering for repsonses and reports
  if (pduType == NULL) {
    spduTypes->insert(PDU_Type(BER_TAG_PDU_RESPONSE));
    spduTypes->insert(PDU_Type(BER_TAG_PDU_REPORT));
  }
  else {
    *spduTypes = *pduType;
  }

  // register for Responses from all engineIds: zero length engId == wildcard
  snmpDispRegisterEngIdASI *regEngMsg = 
    new snmpDispRegisterEngIdASI(new OctetString(), spduTypes, cgArchFifo);
  DEBUG9(cgDebug, string("pushing RegisterEngId pduType to Disp:") + 
	 intToString(int(regEngMsg)) + "\n");
  
  dispFifo->push(regEngMsg);

  // wait until we here back since we can't do anything until we're registered
  queue<snmpMessageObj *>   reg_wait_Q;
  snmpMessageObj           *newMsg = NULL;
  snmpDispRegisterEngIdASI *theMsg = NULL;
  bool                        done = false;

  do {
    newMsg = cgArchFifo->pop(5);
    if (NULL == newMsg) {
      done   = true;
      retVal = false;
      DEBUG5(cgDebug, string("register_with_dispatcher: Warning could not register with desptacher'"));
    }
    else {
      if ((theMsg = dynamic_cast<snmpDispRegisterEngIdASI *>(newMsg))) {
	done   = true;
	retVal = theMsg->get_statusInformation(false)->get_success();
	delete newMsg;
      }
      else {
	DEBUG5(cgDebug, string("register_with_dispatcher: received '")
	       + typeid(*newMsg).name() + string("' message from FIFO while ")
	       + string("waiting for dispatcher registration,\n")
	       + string("saving until registered."));
	// save the message until we're registered.
	reg_wait_Q.push(newMsg);
      }
    }
  }
  while (!done);

  // if the wait queue isn't empty, throw all the messages back into
  // our FIFO.
  while (!(reg_wait_Q.empty())) {
    snmpMessageObj *wMsg = reg_wait_Q.front();
    reg_wait_Q.pop();
    cgArchFifo->push(wMsg);
  }    
  
  return(retVal);
} // register_with_dispatcher 


// unregister with dispatcher for pduType's
// Currently, we don't wait for a response.
bool
snmpCGArch::unregister_with_dispatcher(set<PDU_Type> *pduType) {
  bool retVal = true;
  set<PDU_Type> *spduTypes = new set<PDU_Type>;

  // pduType is NULL, default to unregistering for repsonses and reports
  if (pduType == NULL) {
    spduTypes->insert(PDU_Type(BER_TAG_PDU_RESPONSE));
    spduTypes->insert(PDU_Type(BER_TAG_PDU_REPORT));
  }
  else {
    *spduTypes = *pduType;
  }

  // notice, currently we're not waiting for a response, this could 
  // be a good way to nicely end all threads.... But this isn't being
  // very polite. Just sending a "see ya" to the dispatcher and not 
  // waiting for an "I'm done".
  snmpDispUnregisterEngIdASI *unregEngMsg = new 
   snmpDispUnregisterEngIdASI(new OctetString(theEngine->get_snmpEngineIdOS()),
			      spduTypes,
			      cgArchFifo);
  DEBUG9(cgDebug, "unregister_with_dispatcher: pushing UnregisterEngId msg(s) to Disp");
  dispFifo->push(unregEngMsg);      
  
  return(retVal);
} // unregister_with_dispatcher 


void
snmpCGArch::handleCGtoCGArchMsg(snmpCGtoCGArchMsg *ctcMsg) {
  DEBUG9(cgDebug, "handleCGtoCGArchMsg:");

  sendPduArgs *args = ctcMsg->get_args(false);
  PDU         *pdu  = ctcMsg->get_pdu(false);

  // check that the pointer's aren't NULL
  if ( (NULL == args) || (NULL == pdu) || 
       (NULL == ctcMsg->get_returnFIFO())  ) {
    // XXX return error to CG class??
    DEBUG0(cgDebug, "ERROR: handleCGtoCGArchMsg: The PDU, sendPduArgs adn/or return FIFO are missing from the snmpCGtoCGArchMsg, I'm dropping it.");
    delete ctcMsg;
    return;
  }

  // if we need to do engineID discovery
  if ( (*(args->get_contextEngId()) == OctetString(""))
       &&
       (*(args->get_secLevel()) != 
	  SNMP_CONSTANTS::SNMP_SEC_LEVEL_NOAUTH) ) {

    discover_engine_id(ctcMsg);
  }
  else if (args->get_addToUSM()) {
    // add user, not persistent, overwrite.
    add_user_to_usm(ctcMsg, false, true);
  }
  // else we know the engine ID, and sendPduArgs says the user is
  // known, send the pdu to the agent.
  else  {
    // clone the pdu from ctcMsg in case of error (e.g. timeout)
    pdu = ctcMsg->get_pdu(false)->clone();

    snmpDispSendPduASI     *ppmsg = new snmpDispSendPduASI
      (new SNMP_CONSTANTS::TransportDomain(*(args->get_transportDomain())),
       args->get_transportAddr()->clone(),
       new Integer32(SNMP_CONSTANTS::SNMP_MP_MODEL_SNMPv3),
       new Integer32(SNMP_CONSTANTS::SNMP_SEC_MODEL_USM),
       args->get_userName()->clone(),
       args->get_secLevel()->clone(),
       args->get_contextEngId()->clone(),
       args->get_contextName()->clone(),
       new PDU_Version(PDU_VERSION_2),
       pdu,
       new bool(true),
       cgArchFifo);

    // set sentd time and 
    // place ctcMsg in internal sent message state map
    ctcMsg->get_args()->set_sendTime(get_time());
    internalSentMsgStateMap[ppmsg->get_ID()] = new intRef(false, ctcMsg);
    DEBUG9(cgDebug, "handleCGtoCGArchMsg: sending out message with req id '" +
	 intToString(pdu->get_requestID()) + "'");
    
    // send
    dispFifo->push(ppmsg);
  }

} // handleCGtoCGArchMsg


// Find message in sent state map.
// for discovery if error return to calling CG object
//               if success re-send original mesasge
// for non-discovery return to calling CG object.
//
// Note: it is acceptable for status parameter to be NULL.
void
snmpCGArch::handleAppProcessResponseMsg(snmpAppProcessResponsePduASI *msg)
{
  PDU                      *pdu = msg->get_pdu(true);
  snmpStatusInfo        *status = msg->get_statusInformation(true);
  OctetString  *contextEngineID = msg->get_contextEngineID(true);

  struct local_error {};
  intRef                *iRef = NULL;
  snmpCGtoCGArchMsg   *ctcMsg = NULL;
  sendPduArgs        *pduArgs = NULL;
  stateMap_iterator  searchItr;

  try {
    if ( !(pdu) || !(status) || !(contextEngineID) ) {
      DEBUG0(cgDebug, "Error: handleAppProcessResponseMsg: empty pdu, contextEngineID and/or status, dropping message");
      throw local_error();
    }
    DEBUG9(cgDebug, "handleAppProcessResponseMsg: looking for pdu handle '" +
	   intToString(int(msg->get_sendPduHandle())) + "'");

    // find in state map
    if ( sentMsgStateMap.end() == 
	 (searchItr = sentMsgStateMap.find(msg->get_sendPduHandle())) ) {
      DEBUG0(cgDebug, "Error: handleAppProcessResponseMsg: could not find incoming message in state map, dropping message");
      throw local_error();
    }
    else {     // found in state map.
      DEBUG9(cgDebug, "handleAppProcessResponseMsg: found message in state map");
      iRef    = searchItr->second;
      ctcMsg  = iRef->ctcMsg;
      pduArgs = ctcMsg->get_args();
      // erase from state map
      sentMsgStateMap.erase(searchItr);

      // if it's a discovery failure or it's not discovery, send back
      // to calling cg
      if ( (iRef->discovery && 
	    (!status->get_success() || (*contextEngineID == OctetString(""))))
	   ||
	   !(iRef->discovery) )  {

	// if it's discovery with error, add a discovery error object.
	if (iRef->discovery) {
	  status->pushErrorObj
	    (new snmpErrorObj(snmpErrorObj::DISCOVERY_ERROR));
	  DEBUG5(cgDebug, "handleAppProcessResponseMsg: error returned during DISCOVERY, returning to CG");
	}
	else {
	  if (status->get_success()) {
	    DEBUG5(cgDebug, "handleAppProcessResponseMsg: successful message returning to CG");
	  }
	  else {
	    DEBUG5(cgDebug, "handleAppProcessResponseMsg: error message returning to CG");
	  }
	}

	DEBUG9(cgDebug, "handelAppProcessResponseMsg: incoming request ID '" +
	       intToString(pdu->get_requestID()) + 
	       "', setting to reqeust ID '" + 
	       intToString(ctcMsg->get_pdu()->get_requestID()) + "'");
	// set up return values set incoming pdu to initial request
	// ID's so the caller can match normal returns, errors
	// and/or SNMP reports.
	pdu->set_requestID(ctcMsg->get_pdu()->get_requestID());
	ctcMsg->set_pdu(pdu);
	ctcMsg->set_status(status);
	ctcMsg->get_args()->set_contextEngId(contextEngineID);
	ctcMsg->get_returnFIFO()->push(ctcMsg);
	// cleanup
	delete iRef;
      } 
      else {  // it's a successful discovery message, check if user
	      // needs to be added to the USM or if original message
	      // should now be sent.
	// status and pdu aren't needed anymore.
	delete status; status = NULL;
	delete pdu;       pdu = NULL;

	iRef->discovery = false;
	// place engineID into sendPduArgs
	pduArgs->set_contextEngId(contextEngineID);
	contextEngineID = NULL;
	
	// do we need to add the user to USM?
	if ( pduArgs->get_addToUSM() ) {
	  // add user, not persistent, overwrite.
	  DEBUG9(cgDebug, "handleAppProcessResponseMsg: ID discovered, adding user");
	  add_user_to_usm(ctcMsg, false, true);
	  delete iRef;
	} 
	else { // don't need to add user to USM, send out original
	       // pdu with obtained engine ID.
	  // send out old pdu (clone the pdu from ctcMsg in case of
	  // error, e.g. timeout).
	  pdu = ctcMsg->get_pdu(false)->clone();
	  
	  snmpDispSendPduASI  *ppmsg = new snmpDispSendPduASI
	    (new SNMP_CONSTANTS::TransportDomain
	     (*(pduArgs->get_transportDomain())),
	     pduArgs->get_transportAddr()->clone(),
	     new Integer32(SNMP_CONSTANTS::SNMP_MP_MODEL_SNMPv3),
	     new Integer32(SNMP_CONSTANTS::SNMP_SEC_MODEL_USM),
	     pduArgs->get_userName()->clone(),
	     pduArgs->get_secLevel()->clone(),
	     pduArgs->get_contextEngId()->clone(),
	     pduArgs->get_contextName()->clone(),
	     new PDU_Version(PDU_VERSION_2),
	     pdu,
	     new bool(true),
	     cgArchFifo);

	  // set sent time and 
	  // place iRef in sent internal sent message state map
	  ctcMsg->get_args()->set_sendTime(get_time());
	  internalSentMsgStateMap[ppmsg->get_ID()] = iRef;
	  DEBUG9(cgDebug, "handleAppProcessResponseMsg: no need to add user, sending out mesasge with message ID '" + 
		 intToString(int(ppmsg->get_ID())) + "'");

	  dispFifo->push(ppmsg);
	}
      } // else it was a successful discovery message.
    } // else found in state map

  } catch (local_error) { // catch local problems and cleanup parameters
    if (pdu)             delete pdu;                         pdu = NULL;
    if (status)          delete status;                   status = NULL;
    if (contextEngineID) delete contextEngineID; contextEngineID = NULL;
  }

} // handleAppProcessResponseMsg


// handleAddUserMsg handles returned message from the USM attempting
// to add users to the USM
void
snmpCGArch::handleAddUserMsg(usmAddUserMsg *uauMsg) {
  stateMap_iterator  searchItr;

  // find the message in the add user state map
  if ( addUserStateMap.end() == 
       (searchItr = addUserStateMap.find(uauMsg->get_ID())) ) {
    DEBUG0(cgDebug, "Error: handleAddUserMsg: couldn't find usmAddUserMsg in addUserStateMap, dropping!");
    delete uauMsg;
    return;
  }
  // we found the message

  // get the CGtoCGArchMsg and erase from map
  intRef            *iRef   = searchItr->second;
  snmpCGtoCGArchMsg *ctcMsg = searchItr->second->ctcMsg;
  addUserStateMap.erase(searchItr);
  
  // if the user was added successfully, send the message
  if ( uauMsg->get_statusInformation(false)->get_success() ) {

    // copy pdu for possible timeout later
    PDU          *pdu = ctcMsg->get_pdu(false)->clone();
    sendPduArgs *args = ctcMsg->get_args(false);
    args->set_addToUSM(false);

    snmpDispSendPduASI     *ppmsg = new snmpDispSendPduASI
      (new SNMP_CONSTANTS::TransportDomain(*(args->get_transportDomain())),
       args->get_transportAddr()->clone(),
       new Integer32(SNMP_CONSTANTS::SNMP_MP_MODEL_SNMPv3),
       new Integer32(SNMP_CONSTANTS::SNMP_SEC_MODEL_USM),
       args->get_userName()->clone(),
       args->get_secLevel()->clone(),
       args->get_contextEngId()->clone(),
       args->get_contextName()->clone(),
       new PDU_Version(PDU_VERSION_2),
       pdu,
       new bool(true),
       cgArchFifo);

    // set send time
    // place ctcMsg in sent message state map
    ctcMsg->get_args()->set_sendTime(get_time());
    internalSentMsgStateMap[ppmsg->get_ID()] = iRef;
    DEBUG9(cgDebug, "handleAddUserMsg: adding user succeeded, sending message and adding to internal send state map with  message ID '" 
	   + intToString(int(ppmsg->get_ID())) + "'");
    // send
    dispFifo->push(ppmsg);
  }
  else { // adding user failed.
    snmpStatusInfo *status  = uauMsg->get_statusInformation(true);
    snmpFIFOObj    *retFIFO = ctcMsg->get_returnFIFO(false);
    ctcMsg->set_status(status);
    // return the error message to CG object
    retFIFO->push(ctcMsg);
    DEBUG5(cgDebug, "Error: handleAddUserMsg: adding user failed, returning to CG object");
    // cleanup reference
    delete iRef;
  }
  
  delete uauMsg;
} // handleAddUserMsg


// handles internal errors returned by this local snmp engine.
// This is an utility function for handleDispSendPduASI below.
void
snmpCGArch::internalSendError(intRef *iRef, snmpStatusInfo *status) 
{
  DEBUG9(cgDebug, "internalSendError:");

  if (iRef->discovery) {
    snmpErrorObj *errO = new snmpErrorObj(snmpErrorObj::DISCOVERY_ERROR);
    if (status) { status->pushErrorObj(errO); } 
    else        { status = new snmpStatusInfo(false, errO); }
  }
  iRef->ctcMsg->set_status(status);

  string pErrString = "";
  if (status->frontErrorObj())
    pErrString = status->frontErrorObj()->toString();
  DEBUG9(cgDebug, "internalSendError: error text '" + pErrString + "'");
  
  iRef->ctcMsg->get_returnFIFO()->push(iRef->ctcMsg);

  // cleanup
  delete iRef;
}


// handles returning snmpDispSendPduASI messages.  
// 
// These are messages returned when sending an SNMP message out to
// the net and they hold status info on the success or failure of
// sending the message.
void
snmpCGArch::handleDispSendPduASI(snmpDispSendPduASI *msg) {
  DEBUGN(15, cgDebug, "handleDispSendPduASI:");  

  stateMap_iterator ismItr, ssmItr;

  // if we can not find it in state map
  if ( internalSentMsgStateMap.end() == 
       (ismItr = internalSentMsgStateMap.find(msg->get_ID())) ) {
    DEBUG0(cgDebug, "Warning: handleDispSendPduASI: snmpDispSendPduASI recieved but unable to find in state map, dropping.");
  } 
  else {  // it was found in state map, update state and continue.
    if (msg->get_statusInformation(false)->get_success()) {
      DEBUG9(cgDebug, "handleDispSendPduASI: snmpDispSendPduASI succesful, message sent on net");
      // if it was a retry, just drop. 
      if (0 < ismItr->second->attemptedRetries) {
	DEBUG9(cgDebug, "handleDispSendPduASI: retry attempt, no need to update sent message state.");
	// clean up
	delete ismItr->second->ctcMsg;
	delete ismItr->second;
      }
      // not a retry, move reference info over to sentMsgStateMap
      else {
	DEBUG9(cgDebug, "handleDispSendPduASI: first attempt, updating sent message state.");
	sentMsgStateMap[msg->get_sendPduHandle()] = ismItr->second;
      }
    }
    else {  // ERROR sending message, return pdu and status info to
	    // application
      DEBUG5(cgDebug, "handleDispSendPduASI: snmpDispSendPduASI unsuccessful. sending message failed due to internal error, returning error information to CG object");
      // if this is a retry with an error, erase info from
      // sentMsgStatMap info.
      if (0 <= ismItr->second->attemptedRetries) {
	deleteFromStateMap(sentMsgStateMap, msg->get_sendPduHandle());
	DEBUG9(cgDebug, "handleDispSendPduASI: internal failure while sending a retry message, erasing sent message state information");
      }
      internalSendError( ismItr->second, 
			 msg->get_statusInformation(true) );
    }
    // clean up internal messages state map.
    internalSentMsgStateMap.erase(ismItr);
  }

} // handleDispSendPduASI


// handles setting up and sending a timeout error back to CG object.
void
snmpCGArch::processTimeout(intRef  *reference, bool internalMessage,
			   string   errorText) {
  DEBUGN(15, cgDebug, "processTimeout:");  

  snmpCGtoCGArchMsg  *ctcMsg = reference->ctcMsg;
  snmpFIFOObj       *retFIFO = ctcMsg->get_returnFIFO();
  snmpStatusInfo     *status = new snmpStatusInfo
    ( false,
      new snmpErrorObj(snmpErrorObj::TIMEOUT_ERROR, 
		       string("Timed out " + errorText)) );

  ctcMsg->set_status(status);

  // if this is an internal message error on a retry, erase info
  // from sent message state map.
  if ( internalMessage && (reference->pduHandle > 0) ) {
    deleteFromStateMap(sentMsgStateMap, reference->pduHandle);
  }

  // double check return FIFO.
  if (NULL == retFIFO)  {
    DEBUG0(cgDebug, "Error: processTimeout: timed out but return FIFO not found!, deleting message");
    if (ctcMsg) delete ctcMsg;
  }
  else  retFIFO->push(ctcMsg);

} // processTimeout


// sendRetry  sends a retry message.
void
snmpCGArch::sendRetry(intRef             *ref,
		      unsigned long       pduHandle) {
  DEBUGN(15, cgDebug, "sendRetry:");  

  // use a copy in the internal message state map in case of an
  // internal tiemout.
  // Note: the orinal ctcMsg (and thus the copy) should already have
  // been updated with the current send time and attemptedRetry
  // number before this routine was called.
  snmpCGtoCGArchMsg *ctcMsg = ref->ctcMsg->clone();

  if (ref->discovery) {
    DEBUG9(cgDebug, "sendRetry: retrying discovery message");
    discover_engine_id(ctcMsg, pduHandle, ref->attemptedRetries);
  }
  else {
    sendPduArgs          *args = ctcMsg->get_args();

    snmpDispSendPduASI  *ppmsg = new snmpDispSendPduASI
      (new SNMP_CONSTANTS::TransportDomain(*(args->get_transportDomain())),
       args->get_transportAddr()->clone(),
       new Integer32(SNMP_CONSTANTS::SNMP_MP_MODEL_SNMPv3),
       new Integer32(SNMP_CONSTANTS::SNMP_SEC_MODEL_USM),
       args->get_userName()->clone(),
       args->get_secLevel()->clone(),
       args->get_contextEngId()->clone(),
       args->get_contextName()->clone(),
       new PDU_Version(PDU_VERSION_2),
       ctcMsg->get_pdu(false)->clone(),
       new bool(true),
       cgArchFifo);

    // Use the current pduHandle because the dispatcher checks for and
    // uses an existing pdu handle in the message.  This will cause
    // the same pduHandle to be returned regardless of which retry
    // returns first.
    ppmsg->set_sendPduHandle(pduHandle);

    // note new sent time for this retry both in the internal message
    // state map and in the original state map.
    ctcMsg->get_args()->set_sendTime(get_time());

    // place ctcMsg in internal sent message state map
    internalSentMsgStateMap[ppmsg->get_ID()] = 
      new intRef(false, ctcMsg, pduHandle, ref->attemptedRetries);

    DEBUG9(cgDebug, "sendRetry: sending retry message to dispatcher");

    // send
    dispFifo->push(ppmsg);
  } 

}// sentRetry


// searches through a stateMap for timed out mesages and
// for the lowest timeout value.
//
// returns MAX_INT if no low (or if only '0' which indicates wait
// forever) time out values are found.
int 
snmpCGArch::stateMapTimeCheck(stateMap_Type  & sMap, bool internalMessage,
			      long currSec, string errorText) {
  DEBUGN(15, cgDebug, "stateMapTimeCheck:");  
  int    retVal = MAX_INT;
  int  timeLeft = 0;
  stateMap_iterator eraseMe;
 
  intRef            *currRef = NULL;
  stateMap_iterator  searchItr = sMap.begin();

  while (searchItr != sMap.end()) {
    DEBUGN(15, cgDebug, "stateMapTimeCheck: checking '" + errorText +
	   "', index: '" + intToString(searchItr->first) + "'");
    currRef = searchItr->second;
    // if the messages timeout value is not 0 (i.e. wait forever)
    if (0 < currRef->ctcMsg->get_args()->get_timeout()) {
      timeLeft   = currRef->ctcMsg->get_args()->get_timeout() - 
	int(currSec - currRef->ctcMsg->get_args()->get_sendTime());
      // if mesasge has timed out.
      if ( timeLeft <= 0) {
	DEBUG9(cgDebug, "stateMapTimeCheck: message Timed Out, pdu Handle: " 
	       + intToString(int(searchItr->first)));
	// if this is an internal message timeout, fail immediately.
	// Retries are not currently done for non-network timeouts.
	// Otherwis, check if the CG should send more or any
	// retries.
	if ( !internalMessage      &&
	     ( currRef->attemptedRetries < 
	       currRef->ctcMsg->get_args()->get_retries() )
	   ) {
	  // increment number of retry attempts and set new send time.
	  currRef->attemptedRetries++;
	  currRef->ctcMsg->get_args()->set_sendTime(get_time());

	  sendRetry(currRef, searchItr->first);

	  DEBUG9(cgDebug, "stateMapTimeCheck: sending retry # " +
		 intToString(currRef->attemptedRetries) +
		 " of " + 
		 intToString(currRef->ctcMsg->get_args()->get_retries()) +
		 ",  PDU Handle: '" + intToString(int(searchItr->first)) +
		 "',  request ID: '" + 
		 intToString(currRef->ctcMsg->get_pdu()->get_requestID()) + 
		 "'");
	}
	else {
	  DEBUG9(cgDebug, "stateMapTimeCheck: no retries left, sending error to application");
	  processTimeout(currRef, internalMessage, errorText);
	  eraseMe = searchItr;
	  searchItr++;
	  sMap.erase(eraseMe);
	}
      }
      // otherwise just look for the smallest value of time left to
      // return
      else {
	if ( timeLeft < retVal ) { 
	  retVal = timeLeft; 
	}
	searchItr++;
      }
    }
    else {
      searchItr++;
    }
  }

  return retVal;
}  // stateMapTimeCheck


// searches through all the state map's for timed out messages and
// the lowest current timeout value.  
// returns 0 if there are no low timeouts (or only '0' valued ones)
int
snmpCGArch::checkTimeout() {
  DEBUGN(15, cgDebug, "checkTimeout:");  
  int   retVal = MAX_INT;
  int  lowSMap = 0;
  // get current time
  long currSec = get_time();

  lowSMap = stateMapTimeCheck(sentMsgStateMap, false,
			      currSec,
			      "waiting for message");
  if (lowSMap < retVal) {
    retVal = lowSMap;
  }
  lowSMap = stateMapTimeCheck(addUserStateMap, true,
			      currSec,
			      "Adding User");
  if (lowSMap < retVal) {
    retVal = lowSMap;
  }
  lowSMap = stateMapTimeCheck(internalSentMsgStateMap, true,
			      currSec,
			      "waiting for internal response");
  if (lowSMap < retVal) {
    retVal = lowSMap;
  }
  
  // if retVal hasn't been updated return '0'
  if (MAX_INT == retVal)   retVal = 0;

  DEBUG9(cgDebug, "checkTimeout: timeout is " + intToString(retVal));
  return retVal;

}  // checkTimeout


void
snmpCGArch::main_loop(void) {

  snmpMessageObj  *newMsg;
  int              minTimeout = 0;

  while ( 1 )  {
    // the timeout is '0', wait indefinitely
    if (minTimeout == 0)  newMsg = cgArchFifo->pop();
    else                  newMsg = cgArchFifo->pop(minTimeout);

    if ( newMsg )  {

      // if it's an incoming message from CG class
      if ( snmpCGtoCGArchMsg *msg = 
	   dynamic_cast<snmpCGtoCGArchMsg *>(newMsg) ) {
	DEBUG5(cgDebug, "main_loop: snmpCGtoCGMsg received");
      
	handleCGtoCGArchMsg(msg);

      }
      // if it's an incoming SNMP message,
      else if (snmpAppProcessResponsePduASI *msg = 
	       dynamic_cast<snmpAppProcessResponsePduASI *>(newMsg)) {
	DEBUG5(cgDebug, "main_loop: snmpAppProcessResponsePduASI received");
	DEBUG5(cgDebug, "           with msg ID '" + 
	       intToString(msg->get_ID()) + "' and pduHandle '" + 
	       intToString(msg->get_sendPduHandle()) + "'");

	handleAppProcessResponseMsg(msg);
	delete msg;
      }
      // if it's a return for an SNMP message being sent out,
      else if (snmpDispSendPduASI *msg = 
	       dynamic_cast<snmpDispSendPduASI *>(newMsg)) {

	handleDispSendPduASI(msg);
	delete msg;
      }
      // if it's a message returning from the USM with from an add
      // usser attempt,
      else if (usmAddUserMsg *msg = 
	       dynamic_cast<usmAddUserMsg *>(newMsg)) {
	DEBUG9(cgDebug, "main_loop: usmAddUserMsg received");

	handleAddUserMsg(msg);
      }
      else if (snmpExitMessage *xMsg = 
	       dynamic_cast<snmpExitMessage *>(newMsg)) {
	DEBUG9(cgDebug, "NewMsg is an snmpExitMessage, exiting CGArch thread");
	// do any neccessary clean up here
	if (0 < internalSentMsgStateMap.size()) {
	  DEBUG0(cgDebug, "main_loop exiting with '" + 
		 intToString(internalSentMsgStateMap.size()) + 
		 "' entries in internal sent message state map");
	}
	if (0 < addUserStateMap.size()) {
	  DEBUG0(cgDebug, "main_loop exiting with '" + 
		 intToString(addUserStateMap.size()) + 
		 "' entries in add user state map");
	}
	if (0 < sentMsgStateMap.size()) {
	  DEBUG0(cgDebug, "main_loop exiting with '" + 
		 intToString(sentMsgStateMap.size()) + 
		 "' entries in sent message state map");
	}
	delete xMsg;
	return;
      }
      // unknown/unrequested messagen
      else {   
	DEBUG0(cgDebug, "ERROR: main_loop: unknown (i.e. not send or response PduASI) message type received. I'll drop and try to continue.");
	delete msg;
      }
    } // if we're not timed out

    // whether we timed out or not, handle any time outs and find
    // the shortest timeout left.
    minTimeout = checkTimeout();

  }  // infinite while loop

} // main_loop

