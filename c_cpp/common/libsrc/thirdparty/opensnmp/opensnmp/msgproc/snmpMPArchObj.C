//
// snmpMPArchObj
//


#include "config.h"
#include "snmpMPArchObj.H"
#include "snmpV3Message.H"
#include "OctetString.H"
#include "asnDataType.H"
#include "asnParseError.H"
#include "snmpUSMArchObj.H"
#include "snmpConstants.H"
#include "snmpStats.H"
#include "snmpSNMPErrorObj.H"

#include "snmpDispReturnResponsePduASI.H"
#include "snmpDispSendPduASI.H"
#include "usmAddUserMsg.H"
#include "snmpEngine.H"

#include <stdio.h>
#include <iostream>
#include <string>
#include <typeinfo>

const string snmpMPArchObj::DEFAULT_CONTEXT_NAME = "";
const int    snmpMPArchObj::MAX_MESSAGE_SIZE     = snmpUSMArchObj::BUFFER_SIZE;
const string snmpMPArchObj::FAKE_USER            = "some_user";


snmpMPArchObj::snmpMPArchObj(snmpEngine *theEngine)  {
  this->enginePtr = theEngine;
  this->LOCAL_ENGINE_ID = string(*(this->enginePtr->get_snmpEngineId()));

  DEBUGCREATE(mpDebug, "MP3");

  this->fifoPtr  = NULL;
  this->dispFIFO = NULL;
  this->usmFIFO  = NULL;
  DEBUG9(mpDebug, "snmpMPArchObj Constructor");
} // snmpMPArchObj (constructor)

snmpMPArchObj::~snmpMPArchObj()  {
  DEBUG9(mpDebug, "snmpMPArchObj Destructor");
  DEBUGDESTROY(mpDebug);
} // ~snmpMPArchObj


void 
snmpMPArchObj::return_message(snmpMessageObj *theMessage) {
  snmpFIFOObj             *returnFIFO;

  // return message to its return FIFO, if it exists.
  if ( NULL != (returnFIFO = theMessage->get_returnFIFO()) ) {
    DEBUG9(mpDebug, "return_message: message being returned to retFIFO");
    returnFIFO->push(theMessage);
  }
  // no return FIFO, check if it is a defaulted message type...
  else if ( (typeid(*theMessage) == typeid(snmpMPPrepareOutgoingMsgASI)) ||
	    (typeid(*theMessage) == typeid(snmpMPPrepareResponseMsgASI)) ||
	    (typeid(*theMessage) == typeid(snmpMPPrepareDataElementsASI)) ) {

    if ( (NULL != this->dispFIFO) || 
	 (NULL != (this->dispFIFO = this->theReg.findArchFIFO("Disp"))) ) {
      DEBUG9(mpDebug, "return_message: message sent to dispatcher (default "
	     << "for message type))");
      this->dispFIFO->push(theMessage);
    }
  }

  // no known return FIFO.
  else {
    DEBUG0(mpDebug, "return_message: Unknown return fifo for the current "
	   << "message, message deleted");
    delete theMessage;
  }
} // return_message


void 
snmpMPArchObj::send_usm_message(snmpMessageObj *theMessage) {

  if ( (this->usmFIFO) || 
       (this->usmFIFO = this->theReg.findArchFIFO
	(SNMP_USM_ARCH_NAME)) ) {
    DEBUG9(mpDebug, "send_usm_message: message sent to USM");
    this->usmFIFO->push(theMessage);
  }
  // no known FIFO.
  else {
    DEBUG0(mpDebug, "send_usm_message: can't find USM FIFO to send "
	   << "message to..., deleting message");
    delete theMessage;
  }

} // send_usm_message


void 
snmpMPArchObj::send_sm_message(Integer32       secModel, 
			       snmpMessageObj *theMessage) {
  snmpFIFOObj  *smFIFO;
  // blech
  char buf[64];
  sprintf(buf, "%d", int(secModel));
  string sm_num = buf;
  string sm_model = "SM:" + sm_num;
  DEBUG5(mpDebug, "send_sm_message: sending to '" << sm_model);
  
  if (NULL != (smFIFO = this->theReg.findArchFIFO(sm_model))) {
    DEBUG9(mpDebug, "send_sm_message: message sent to '" << sm_model);
    smFIFO->push(theMessage);
  }
  // no known FIFO.
  else {
    DEBUG0(mpDebug, "send_sm_message: can't find '" << sm_model << 
	   "' FIFO to send message to..., deleting message");
    delete theMessage;
  }
  
} // send_sm_message


void 
snmpMPArchObj::send_disp_message(snmpMessageObj *theMessage) {

  if ( (this->dispFIFO) || 
       (this->dispFIFO = this->theReg.findArchFIFO("Disp")) ) {
    DEBUG9(mpDebug, "send_disp_message: message sent to DISP");
    this->dispFIFO->push(theMessage);
  }
  // no known FIFO.
  else {
    DEBUG0(mpDebug, "send_disp_message: can't find DISP FIFO to send "
	   << "message to..., deleting message");
    delete theMessage;
  }

} // send_disp_message


bool
snmpMPArchObj::handle_reports(snmpStateReference *ref,
 			      VarBindList        *theList,
 			      int                 oldMsgID,
 			      OctetString        *newEngID) {
  static const OID NOT_IN_TIME_WINDOW_OID = OID(snmpUSMArchObj::usmStatsOID,
						".2.0");
// XXX now doing engine ID discovery in snmpCGArch
//   static const OID UNKNOWN_ENGINE_OID     
//              = OID(snmpUSMArchObj::usmStatsOID, ".4.0");  
  OID *tempOID;
  VarBindList::const_iterator i = theList->begin();

  DEBUG9(mpDebug, "handle_reports");

  while (i != theList->end()) {
    tempOID = (*i)->get_OID();
    if ( *tempOID == NOT_IN_TIME_WINDOW_OID )  {
      // boots/time may have been incorrect, they should be updated 
      // already (from this msg) in USM, so resend the message.
      snmpDispSendPduASI *newMessage = 
	new snmpDispSendPduASI
	(new TransportDomain(*(ref->transportDomain)),
	 ref->transportAddress->clone(),
	 new Integer32(SNMP_CONSTANTS::SNMP_MP_MODEL_SNMPv3),
	 ref->securityModel->clone(),
	 ref->securityName->clone(),
	 ref->securityLevel->clone(),
	 ref->contextEngineID->clone(),
	 ref->contextName->clone(),
	 (PDU_Version *)NULL,            // PDU_Version
	 (ref->sPDU->get_pdu(false))->clone(), // PDU
	 (bool *)NULL,                   // expected response
	 this->fifoPtr,                  // FIFO
	 (snmpSynchDataObj *)NULL,       // synch data
	 (snmpMessageObj *)NULL,         // msg to copy from
	 oldMsgID);

      newMessage->set_sendPduHandle(ref->sendPDUHandle);
      DEBUG5(mpDebug, "handle_reports: OID '" << string(*tempOID) 
	     << "', indicate time synching, resending message");
      DEBUG9(mpDebug, "handle_reports: setting pduHandle to ref's '" 
	     << ref->sendPDUHandle << "'");
      send_disp_message(newMessage);
      i = theList->end();
      return(true);
    }
// now doing engine ID discovery in snmpCGArch
// XXX
//     else if ( (*tempOID == UNKNOWN_ENGINE_OID) &&
// 	      (ref->eng_ID_Discovery) )  {
//       // Add the new user/eng ID to the USM.
//       usmAddUserMsg *addNewUser = new usmAddUserMsg
// 	(newEngID->clone(), ref->securityName->clone(), this->fifoPtr);
//       send_usm_message(addNewUser);

//       // we're trying to do engine id discovery, resend with new engine ID
//       snmpDispSendPduASI *newMessage = 
// 	new snmpDispSendPduASI
// 	(new TransportDomain(*(ref->transportDomain)),
// 	 ref->transportAddress->clone(),
// 	 new Integer32(SNMP_CONSTANTS::SNMP_MP_MODEL_SNMPv3),
// 	 ref->securityModel->clone(),
// 	 ref->securityName->clone(),
// 	 ref->securityLevel->clone(),
// 	 newEngID->clone(),              // NEW ENGINE ID !!!
// 	 ref->contextName->clone(),
// 	 (PDU_Version *)NULL,            // PDU_Version
// 	 (ref->sPDU->get_pdu(false))->clone(), // PDU
// 	 (bool *)NULL,                   // expected response
// 	 this->fifoPtr,                  // FIFO
// 	 (snmpSynchDataObj *)NULL,       // synch data
// 	 (snmpMessageObj *)NULL,         // msg to copy from
// 	 oldMsgID);

//       DEBUG5(mpDebug, "handle_reports: OID '" << string(*tempOID) 
// 	     << "', indicates engine ID discovery, resending message");
//       DEBUG9(mpDebug, "handle_reports: setting pduHandle to ref's '" 
// 	     << ref->sendPDUHandle << "'");
//       newMessage->set_sendPduHandle(ref->sendPDUHandle);
//       send_disp_message(newMessage);
//       i = theList->end();
//       return(true);
//     }
    else {
      i++;
      DEBUG5(mpDebug, "handle_reports: OID '" << string(*tempOID)
	     << "' report received, not handled by engine.");
      //      return(false);
    }
  }
  return(false);
} // handle_reports


void
snmpMPArchObj::PrepareOutgoingMessage(snmpMPPrepareOutgoingMsgASI  *prepOutMsg)
{
// XXX    bool eng_ID_discovery = false;
  DEBUG9(mpDebug, "PrepareOutgoingMessage");

  // Elements of procedure, 7.1
  // EoP: 7.1,1b: generate unique msgID
  int currMsgID = prepOutMsg->get_ID();

  // EoP: 7.1,4: determine contextEngineID
  // Get context engine ID from message. If it doesn't exist or is  
  // the null string, assume engine ID discovery.
  OctetString *contextEngineID_ptr = prepOutMsg->get_contextEngineID();

  //  if ( !contextEngineID_ptr) {
    // should return error here
  //  }
// now doing engine ID discovery in snmpCGArch
// xxx  if ( !(contextEngineID_ptr) || (contextEngineID_ptr->size() <= 0) ) {
//     // this looks like we should do engine ID discovery, 
//     contextEngineID_ptr = 
//       new OctetString(SNMP_CONSTANTS::ENG_ID_DISCOVERY_ENG_ID);
//     prepOutMsg->set_contextEngineID(contextEngineID_ptr);

//     DEBUG5(mpDebug, "This message requires engine ID discovery");
//     eng_ID_discovery = true;
//   }

  // EoP: 7.1,5: If context name is undetermined, set to default context.
  // Get context name from message. If context name is null, 
  // set to default context (i.e. empty string).
  OctetString *contextName_ptr = prepOutMsg->get_contextName();
  if (!(contextName_ptr)) {
    contextName_ptr = new OctetString(this->DEFAULT_CONTEXT_NAME);
    prepOutMsg->set_contextName(contextName_ptr);
  }

  // EoP: 7.1,6: Scoped PDU is prepared from the contextEngineID, contextName, 
  // and PDU.

  // if the incoming PDU is null, create a new one for now
  PDU *pdu_ptr = prepOutMsg->get_pdu();
  int reqID;
  if (!(pdu_ptr)) {
    DEBUG0(mpDebug, "PrepareOutgoingMessageASI: no pdu, failing"
	   << "**should return error in the future**");
    reqID = currMsgID;
    pdu_ptr = new PDU(BER_TAG_PDU_GET, reqID,0,0,NULL,PDU_VERSION_2);
    prepOutMsg->set_pdu(pdu_ptr);
  }
  else {
    reqID = pdu_ptr->get_requestID();
    if (reqID == 0) pdu_ptr->set_requestID( (reqID = currMsgID) ); 
  }

  // EoP: 7.1,7: construct msgGlobalData
  // message processing madel...
  // (a) version is set to snmpv3
  prepOutMsg->set_messageProcessingModel
    (new Integer32(SNMP_CONSTANTS::SNMP_VERSION_3));
  // (b) already have msgID
  // (c) max message size is a constant
  // (d) msgFlags:

  // getting security levels for the message flags and secLevel for
  // the USM message.
  Integer32 *securityLevel_ptr = prepOutMsg->get_securityLevel();
  char tempFlag = 0x00;  // no auth, no priv
  if (securityLevel_ptr) {
    switch (int(*securityLevel_ptr)) {
    case SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV:
      tempFlag = SNMP_CONSTANTS::SNMP_MSG_FLAG_AUTH_BIT;
      break;
    case SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV:
      tempFlag = SNMP_CONSTANTS::SNMP_MSG_FLAG_AUTH_BIT | 
 	SNMP_CONSTANTS::SNMP_MSG_FLAG_PRIV_BIT;
      break;
    default: 
      // This is either noauth/nopriv or illegal, so default to noauth/nopriv
      break;
    }
  }
  else  { // return error in future, for now default to noauth/nopriv.
    securityLevel_ptr = new Integer32(SNMP_CONSTANTS::SNMP_SEC_LEVEL_NOAUTH);
    prepOutMsg->set_securityLevel(securityLevel_ptr);
  }

  // set reportable flag appropriately
  // is this a confirmed type?
  bool is_confirmed = false;
  if (SNMP_CONSTANTS::is_confirmed_class(pdu_ptr->get_tag())) {
    tempFlag = tempFlag | SNMP_CONSTANTS::SNMP_MSG_FLAG_RPRT_BIT;
    is_confirmed = true;
  }

  OctetString *msgFlags = new OctetString(string(1,tempFlag));

  // (e) set security model
  Integer32 *securitytModel_ptr = prepOutMsg->get_securityModel();
  if (!(securitytModel_ptr)) {
    securitytModel_ptr = new Integer32(SNMP_CONSTANTS::SNMP_SEC_MODEL_USM);
    prepOutMsg->set_securityModel(securitytModel_ptr);
  }
  // NOTE: setting security model, also makes sure globalData exists
  // in prepOutMsg (it's created if it doesn't)
  
  // Finally, set up all of global data.
  HeaderData *globalData_ptr = prepOutMsg->get_globalData();
  globalData_ptr->set_msgID(new Integer32(currMsgID));
  globalData_ptr->set_maxMsgSize(new Integer32(this->MAX_MESSAGE_SIZE));
  globalData_ptr->set_msgFlags(msgFlags);
  // securityModel already set.

  // EoP: 7.1,9: If the PDU is from the confirmed class, set 
  //             security Engine ID to the receiving entities 
  //             SNMP Engine ID. Otherwise, set it to this 
  //             entities SNMP Engine ID.
  OctetString *securityEngineID_ptr;
  if (is_confirmed) {    
    // set security engine ID from context engine ID.
    // for now, at least, this is just a straight copy.
    securityEngineID_ptr = contextEngineID_ptr->clone();
  }
  else {
    // set security engine ID to this engine's SNMP Engine ID.
    // unknown right no, so...
    securityEngineID_ptr = new OctetString(this->LOCAL_ENGINE_ID);
  }
  prepOutMsg->set_securityEngineID(securityEngineID_ptr);

// now doing engine ID discovery in snmpCGArch
//  XXX  // security name checks.
//  OctetString *engIDDiscovery_secName = NULL;
//
//   if (eng_ID_discovery) {
//     engIDDiscovery_secName = prepOutMsg->get_securityName(true);
//     prepOutMsg->set_securityName
//       (new OctetString(SNMP_CONSTANTS::ENG_ID_DISCOVERY_SEC_NAME));
    
//   }
//   // check that a value exists, create a blank if it does not, for now XXX.
//   else if ( !(prepOutMsg->get_securityName(false)) ) {
//     prepOutMsg->set_securityName(new OctetString());
//   }

  // create message to send to USM, putting in the synch data with a pointer 
  // to the current message (prepOutMsg), and the MP FIFO as the return FIFO.
  // move the internal data from prepOutMsg to the new request message.
  snmpUSMGenerateRequestMsgASI *newReqMsg = 
    new snmpUSMGenerateRequestMsgASI
      (prepOutMsg->get_intData(true),    // move message data over
       new snmpSynchDataObj(prepOutMsg), //synchDataObj 
// XXX       new snmpSynchDataObj(prepOutMsg,engIDDiscovery_secName), //synchDataObj 
       this->fifoPtr);                   // retFIFOptr

  // send to USM for processing.
  this->send_sm_message(*securitytModel_ptr, newReqMsg);

  // secModel

}  // snmpMPArchObj::PrepareOutgoingMessage


void
snmpMPArchObj::PrepareResponseMessage(snmpMPPrepareResponseMsgASI *prepRespMsg) {
  DEBUG9(mpDebug, "PrepareResponseMessage");
  // Elements of Procedure: 7.1.2 (b), retrieve cached information.
  snmpStateReference *theRef;
  if (!(theRef = dynamic_cast<snmpStateReference *>
	(prepRespMsg->get_stateReference()))) {
    DEBUG0(mpDebug, "PrepareRepsonseMessage: no state reference!, failing");
    snmpStatusInfo *theStatus = new snmpStatusInfo(false);
    theStatus->pushErrorObj(new snmpErrorObj
			    (snmpErrorObj::NO_STATE_REFERENCE));
    prepRespMsg->set_statusInformation(theStatus);
    this->return_message(prepRespMsg);
    return;
  }

  // these are defined in the following if-else loop.
  Integer32   *secLevel;
  OctetString *contEngineID;
  OctetString *contName;
  PDU         *theNewPDU;

  // EoP: 7.1.3: if statusInformation contains values for an OID/value
  //             combination, then process the report:
  snmpStatusInfo *theStatus = prepRespMsg->get_statusInformation();
  if ( (theStatus) && !(theStatus->get_success()) ) {

    // look for snmpSNMPErrorObj in snmpStatusInfo that has a varBindList,
    // using the FIRST one found.
    snmpSNMPErrorObj *pError;
    snmpErrorObj     *tempError;
    bool found = false;
    VarBindList *vList;
    
    while ( !(found) && (0 != (tempError = theStatus->popErrorObj())) ) {
      if ( (pError = dynamic_cast<snmpSNMPErrorObj*>(tempError))
	   && (0 != (vList = pError->get_varBindList(true))) ) {
	found = true;
      }
      else {
	delete tempError;
      }
    }
    if (!(found)) {
      DEBUG0(mpDebug, "PrepareResponseMessage: status info is error, "
	    << "but no varbind found, failing");
      // we already know that prepRespMsg has a status and that its
      // success value is false, so just send it back.
      this->return_message(prepRespMsg);
      return;
    }
      
    // EoP:(a) if reportable flag is 0, no further processing is done.
    char repChar;
    theRef->msgFlags->get_string((char *)&repChar, 1, false);
    if (!(repChar & SNMP_CONSTANTS::SNMP_MSG_FLAG_RPRT_BIT)) {
      // we already know that prepRespMsg has a status and that its
      // success value is false, so just send it back.
      this->return_message(prepRespMsg);
      return;
    }

    // EoP:(b) If a PDU is provided, it is from the original request,
    // extract the request ID. Otherwise it equals zero.
    PDU *thePDU;
    int request_id;
    if ( 0 != (thePDU = prepRespMsg->get_pdu()) ) {
      request_id = thePDU->get_requestID();
    }
    else {
      request_id = 0;
    }

    // EoP:(c) prepare report PDU, error-status and error-index is zero
    theNewPDU = new PDU(BER_TAG_PDU_REPORT,
			request_id,
			int(0),            // error_index
			int(0),            // error_status
			vList,             // VarBindList
			PDU_VERSION_2);
    // add to prepRespMsg so app gets what we are sending.
    prepRespMsg->set_pdu(theNewPDU);

    // EoP:(d) set security level, context engine ID, and context name
    //         depending on values available from the status info 
    //         (i.e. pError)
    if (0 == (secLevel = pError->get_securityLevel(true))) {
      secLevel = new Integer32(SNMP_CONSTANTS::SNMP_SEC_LEVEL_NOAUTH);
    }
    if (0 == (contEngineID = pError->get_contextEngineID(true))) {
      contEngineID = new OctetString(this->enginePtr->get_snmpEngineIdOS());
    }
    if (0 == (contName = pError->get_contextName(true))) {
      contName = new OctetString(this->DEFAULT_CONTEXT_NAME);
    }

  } // if this was an error indication.
  else {  // else this isn't an error indication

    // from EoP:7.1.2(b), Cached data is not allowed to be overwritten,
    //        so the following data is pulled from the state reference
    
    // EoP:7.1.4: if context engine ID is undetermined, determine
    // in an implementation-independent manner.
    // for now, default to this engine's ID.
    if (0 == (contEngineID = theRef->contextEngineID)) {
         contEngineID = new OctetString(this->LOCAL_ENGINE_ID);
    }
    else theRef->contextEngineID = 0;

    // EoP:7.1.5: if context name is unknown, set to default context 
    //            (i.e. null string)
    if (0 == (contName = theRef->contextName)) {
      contName = new OctetString(this->DEFAULT_CONTEXT_NAME);
    }      
    else theRef->contextName = 0;

    // In order to match 'if' above, get secLevel and PDU.
    if (0 == (secLevel = theRef->securityLevel)) {
      DEBUG0(mpDebug, "PrepareResponseMessage: Unknown security level!, failing");
      theStatus = new snmpStatusInfo(false);
      theStatus->pushErrorObj
	(new snmpErrorObj(snmpErrorObj::UNKNOWN_SECURITY_LEVEL));
      prepRespMsg->set_statusInformation(theStatus);
      return_message(prepRespMsg);
      return;
    }
    else theRef->securityLevel = 0;
    
    // leave pdu in prepRespMsg so app gets back what we are sending.
    if(0 == (theNewPDU = prepRespMsg->get_pdu())) {
      DEBUG0(mpDebug, "PrepareResponseMessage: no pdu, failing");
      theStatus = new snmpStatusInfo(false);
      theStatus->pushErrorObj
	(new snmpErrorObj(snmpErrorObj::NO_PDU));
      prepRespMsg->set_statusInformation(theStatus);
      return_message(prepRespMsg);
      return;
    }
  }  // else this isn't an error endication

  // EoP: 7.1,6: Scoped PDU is prepared from the contextEngineID, contextName, 
  // and PDU.

  // by setting contextEngineID, and contextName in the message, the 
  // scoped PDU will exist (the PDU already exists or was added above)
  // I'll add security level while I'm at it.

  prepRespMsg->set_contextEngineID(contEngineID);
  prepRespMsg->set_contextName(contName);
  prepRespMsg->set_securityLevel(secLevel);

  // EoP: 7.1,7: construct msgGlobalData
  // message processing madel...
  // (a) version is set to snmpv3
  Integer32* version = new Integer32(SNMP_CONSTANTS::SNMP_VERSION_3);
  prepRespMsg->set_messageProcessingModel(version);
  // (b) msgID from stateReference
  // (c) max message size is a constant
  // (d) msgFlags from stateReference

  // secLevel, from above

  // (e) set security model
  Integer32 *secModel = theRef->securityModel;
  if (!(secModel)) {
    secModel = new Integer32(SNMP_CONSTANTS::SNMP_SEC_MODEL_USM);
  }

  // EoP: 7.1.7 construct global data.
  // set up global data.
  // EoP:(d) msgFlags, according to security level.
  char newMsgFlag = 0x00; // no auth, no priv
  switch (int(*secLevel)) {
  case SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV:
    newMsgFlag = SNMP_CONSTANTS::SNMP_MSG_FLAG_AUTH_BIT;
    break;
  case SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV:
    newMsgFlag = SNMP_CONSTANTS::SNMP_MSG_FLAG_AUTH_BIT | 
      SNMP_CONSTANTS::SNMP_MSG_FLAG_PRIV_BIT;
    break;
  default: 
    // defaults to noauth/nopriv
    break;
  }
  // if the pdu is from the confirmed class set report bit to 1
  if (SNMP_CONSTANTS::is_confirmed_class(theNewPDU->get_tag())) {
    newMsgFlag = newMsgFlag | SNMP_CONSTANTS::SNMP_MSG_FLAG_RPRT_BIT;
  }

  HeaderData *globalData = new HeaderData
    ( theRef->msgID,                           // message ID
      (new Integer32(this->MAX_MESSAGE_SIZE)), // max message size
      new OctetString(&newMsgFlag, 1),         // message flags.
      secModel );                     // security model

  // set global data into message. This will overwrite the inputs
  // from the app, but since we're suppose to use the reference info 
  // anyway, why does the app give have to input it to us? who knows.
  prepRespMsg->set_globalData(globalData);
  
  // more optimism, add transDomain/transAddress to prepRespMsg here
  // so that the reference can be deleted.
  prepRespMsg->set_transportDomain(theRef->transportDomain);
  prepRespMsg->set_transportAddress(theRef->transportAddress);

  // set other refereence values
  prepRespMsg->set_securityName(theRef->securityName);
  prepRespMsg->set_securityEngineID(theRef->securityEngineID);
  prepRespMsg->set_securityStateReference(theRef->securityStateReference);

  // clean up and delete unused reference data.
  // msgID, msgFlags, and secModel were assigned farther above, the rest 
  // were used in the message creation directly above.
  theRef->msgID                  = 0;
  theRef->securityModel          = 0;
  theRef->securityName           = 0;
  theRef->securityEngineID       = 0;
  theRef->transportDomain        = 0;
  theRef->transportAddress       = 0;
  theRef->securityStateReference = 0;
  // remove state reference from message going back to dispatcher 
  // (and possibly to app!). Would have done this sooner, but if 
  // this failed earlier, the app should have been left with a valid reference.

  // leave the stateReference in the message for disp to use (just the pointer
  // value), but erase the structure. 
  // XXX - this should be handled differently!
  prepRespMsg->get_stateReference(true);
  delete theRef;

  // create message to send to USM, putting in the synch data with a pointer 
  // to the current message (prepRespMsg), and the MP FIFO as the return FIFO.
  snmpUSMGenerateResponseMsgASI *newRespMsg = 
    new snmpUSMGenerateResponseMsgASI
    (prepRespMsg->get_intData(true),       // pass prepRespMsg data
     new snmpSynchDataObj(prepRespMsg),    // synchDataObj
     this->fifoPtr);                       // retFIFOptr

  // send to USM for processing.
  this->send_sm_message(*secModel, newRespMsg);
  
};  // snmpMPArchObj::PrepareResponseMessage



// Prepare Data Elements is the ASI call to process an incoming, off the net,
// message. Strip some data and then send the security module
// a 'snmpUSMProcessIncomingASI' message to process.
void
snmpMPArchObj::PrepareDataElements(snmpMPPrepareDataElementsASI  *prepDataElem) {
  DEBUG9(mpDebug, "PrepareDataElements");
  // Elements of Procedure 7.2  
  // EoP: 7.2,2: check if the received message is serialized correctly
  snmpV3Message &theMsg = prepDataElem->get_v3Message_ref();
  try {
    theMsg.asnDecodeMsgHeader((char *)(*(prepDataElem->get_outMsg())), 
			      (int)(*(prepDataElem->get_outMsgLength())));
  }
  catch (snmpStringException &except) {
    this->enginePtr->stats.increment(SNMP_STATS::SNMP_IN_ASN_PARSE_ERRORS);
    DEBUG0(mpDebug, "PrepareDataElements: while asn decoding message header " 
	   << "caught exception '" << except.get_errorMessage() 
	   << "', failure returned");
    prepDataElem->set_statusInformation(new snmpStatusInfo(false));
    return_message(prepDataElem);
    return;
  }
  catch (...) {
    DEBUG0(mpDebug, "PrepareDataElements: while asn decoding message header " 
	   << "caught UNKNOWN exception, failure returned");
    prepDataElem->set_statusInformation(new snmpStatusInfo(false));
    return_message(prepDataElem);
    return;
  }
    
  // EoP: 7.2,3: extract msgId, msgMaxSize, msgFlags, msgSecurityModel,
  // msgSecurityParameters, msgData (this is in theHeader, theMsg, and 
  // SecurityLevel)
  HeaderData    *theHeader = theMsg.get_msgGlobalData();

  // EoP: 7.2,4: if the value of security model doesn't match a supported
  // model, update stats and return failure...
  
  // We will assume USM for now, but later...
//   if (int(*(theHeader->get_msgSecurityModel())) != 
//       SNMP_CONSTANTS::SNMP_SEC_MODEL_USM) {
//     this->enginePtr->stats.increment(SNMP_STATS::SNMP_UNKNOWN_SECURITY_MODELS);
//     DEBUG(mpDebug, "PrepareDataElements: unsupported security model!,");
//     DEBUG(mpDebug, " failure returned\n");
//     prepDataElem->set_statusInformation(new snmpStatusInfo(false));
//     return_message(prepDataElem);
//     delete theMsg;
//     return;
//   }
  
  // EoP: 7.2,5: determine security level
  // get the security level out of the Headers msgFlags
  // i.e. OctetString to char, to int, dump all but the last two bits,
  // to Integer32
  char SecChar;
  (theHeader->get_msgFlags())->get_string((char *)&SecChar, 1, false);
  Integer32 *SecurityLevel;
  if (SecChar & SNMP_CONSTANTS::SNMP_MSG_FLAG_AUTH_BIT) {
    if (SecChar & SNMP_CONSTANTS::SNMP_MSG_FLAG_PRIV_BIT) {
      SecurityLevel = new 
	Integer32(int(SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV));
    }
    else {
      SecurityLevel = new 
	Integer32(int(SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV));
    }
  }
  else {
    // solely for EoP, check if someone messed up enought to...
    if (SecChar & SNMP_CONSTANTS::SNMP_MSG_FLAG_PRIV_BIT) {
      this->enginePtr->stats.increment(SNMP_STATS::SNMP_INVALID_MSGS);
      DEBUG0(mpDebug, "PrepareDataElements: msgFlags have noauth WITH" 
	     << "priv, failure returned");
      prepDataElem->set_statusInformation(new snmpStatusInfo(false));
      return_message(prepDataElem);
      return;
    }
    else {
      SecurityLevel = new 
	Integer32(int(SNMP_CONSTANTS::SNMP_SEC_LEVEL_NOAUTH));
    }
  }
    
  // get a pointer to the secModel (and ownership, it's passed to the message)
  // secmodel in message now!
  Integer32 *secModel = theHeader->get_msgSecurityModel();

  // Put new data into Message
  prepDataElem->set_securityLevel(SecurityLevel);

  // create message to send to USM, putting in the synch data with a pointer 
  // to the current message (prepRespMsg), and the MP FIFO as the return FIFO.

  snmpUSMProcessIncomingASI *newProcIncoming = 
     new snmpUSMProcessIncomingASI
       (prepDataElem->get_intData(true),    // move message data over
	new snmpSynchDataObj(prepDataElem), // synchDataObj
	this->fifoPtr);                     // retFIFOptr

  // send to USM for processing.
  this->send_sm_message(*secModel, newProcIncoming);

}  // snmpMPArchObj::PrepareDataElements


void 
snmpMPArchObj::GenerateRequestMessage(snmpUSMGenerateRequestMsgASI   *genReqMsg) {
  snmpSynchDataObj                  *theSynchData;
  snmpMPPrepareOutgoingMsgASI       *prepOutMsg;
  snmpStatusInfo                    *theStatus;
  snmpStandardMessage_internalData  *intData;
  DEBUG9(mpDebug, "GenerateRequestMessage");

  // returned message from USM, check for synch data. If successful,
  // do processing and return to sender.
  if ((NULL == (theSynchData = genReqMsg->get_synchData(true)))
      ||
      !(prepOutMsg = 
	dynamic_cast<snmpMPPrepareOutgoingMsgASI *>(theSynchData->get_msgObject(true)))
      ||
      (NULL == (intData = genReqMsg->get_intData(true)))
      ) {
    // couldn't get synch data
    DEBUG0(mpDebug, "GenerateRequestMessage: unable to extract "
	   << "synch data from returned USM message, throwing away.");
    // clean up any loose memory transDomain, transAddress maybe?
    if (theSynchData) delete theSynchData;
    delete genReqMsg;
    delete intData;
    return;
  }

  // The old message and the internal data exit, lets get them together
  // for a fun procedure of SNMP protocoling by lcd light.
  prepOutMsg->set_intData(intData);
  // clean up
  // need synchData for possible Eng ID Discovery;// delete theSynchData;
  delete genReqMsg;

  // OK, we will now at least pass something back to the dispatcher.

  if (NULL == 
      (theStatus = prepOutMsg->get_statusInformation())) {
    // no status info from USM, fail
    DEBUG0(mpDebug, "GenerateRequestmessage: no status info from USM, "
	  << "sending failure to dispatcher");
    prepOutMsg->set_statusInformation(new snmpStatusInfo(false));
  }

  // EoP: 7.1, 9(b): If the security model returns an error, an error 
  //   indication is returned and no further proccessing is done.
  else if (!(theStatus->get_success())) {
    // do any USM error stuff here...
    DEBUG1(mpDebug, string("GenerateRequestMessage: Warning: Message ") +
	   string("failed at USM with error: ") + theStatus->toString()); 
  }

  // the message should have processed successfully through USM
  else {
    // get some values needed for state reference
    Integer32  *secModel = prepOutMsg->get_securityModel(false);
    OctetString *secName = prepOutMsg->get_securityName(false);
    Integer32  *secLevel = prepOutMsg->get_securityLevel(false);
    ScopedPDU      *sPDU = prepOutMsg->get_scopedPDU(false);

    // EoP: 7.1, 9(c): if the pdu is of confirmed class, information about
    //   the outgoing message is cached.
    // if this is a confirm class pdu, save state info...
    if (SNMP_CONSTANTS::is_confirmed_class( 
                           (prepOutMsg->get_pdu())->get_tag() )) {
      // are we doing engine ID Discovery?
// XXX
// now doing engine ID discovery in snmpCGArch
//       bool eng_ID_Discovery = 
// 	( (*secName == OctetString(SNMP_CONSTANTS::ENG_ID_DISCOVERY_SEC_NAME)) 
// 	  &&
// 	  (*(sPDU->get_contextEngineID()) == 
// 	   OctetString(SNMP_CONSTANTS::ENG_ID_DISCOVERY_ENG_ID)) );
      // globalData itself is saved, so its extracted.
      HeaderData  *globalData    = prepOutMsg->get_globalData(false);

      snmpStateReference *newRef = new snmpStateReference;
      // in case we need to resend, save next four objects...
      newRef->sPDU             = sPDU->clone();
      newRef->globalData       = globalData->clone();
      newRef->transportDomain  = new 
	TransportDomain(*(prepOutMsg->get_transportDomain()));
      newRef->transportAddress = prepOutMsg->get_transportAddress()->clone();

      newRef->msgID            = (globalData->get_msgID())->clone();
      newRef->contextEngineID  = (prepOutMsg->get_contextEngineID())->clone();
      newRef->contextName      = (prepOutMsg->get_contextName())->clone();
      newRef->securityModel    = secModel->clone();

      // Save prepOutMsg's secName because if we're doing engine
      // ID discovery, this will save the original security name.
// XXX, erase if not needed
// now doing engine ID discovery in snmpCGArch
//       if (eng_ID_Discovery) {
// 	OctetString *refSecName = 
// 	  dynamic_cast<OctetString*>(theSynchData->get_extraData1(true));
// 	if (NULL != refSecName) newRef->securityName = refSecName;
// 	else newRef->securityName = new OctetString();
//       }
//       else {
//       	newRef->securityName = (prepOutMsg->get_securityName(false))->clone();
//       }
//       newRef->eng_ID_Discovery = eng_ID_Discovery;
      newRef->securityName = (prepOutMsg->get_securityName(false))->clone();

      newRef->securityLevel    = secLevel->clone();
      newRef->securityEngineID = (prepOutMsg->get_securityEngineID())->clone();
      newRef->sendPDUHandle    = prepOutMsg->get_sendPduHandle();
      DEBUG9(mpDebug, "GenerateRequestMessage: saving pduhandle '" 
	     << newRef->sendPDUHandle << "'");
      // save info in map, check if it already exists
      snmpMPArchObj::map_iterator_type searchItr;
      searchItr = this->outgoingRequestMap.find(int(*(newRef->msgID)));
      if (searchItr != this->outgoingRequestMap.end()) {
	DEBUG0(mpDebug, "GenerateRequestMessage: msgID already exists "
	       << "in state reference map, failing");
	delete newRef;
	theStatus->set_success(false);
	theStatus->pushErrorObj(new snmpErrorObj(snmpErrorObj::BAD_MSGID));
      }
      else {
	this->outgoingRequestMap[int(*(newRef->msgID))] = newRef;
      }
    }
  }
  // clean up synchMsg, had been needed for possible engine ID discovery
  delete theSynchData;

  this->return_message(prepOutMsg);
} // snmpMPArchObj::GenerateRequestMessage


void 
snmpMPArchObj::GenerateResponseMessage(snmpUSMGenerateResponseMsgASI *genRespMsg) {
  snmpSynchDataObj                 *theSynchData;
  snmpMPPrepareResponseMsgASI      *prepRespMsg;
  snmpStatusInfo                   *theStatus;
  snmpStandardMessage_internalData *intData;
  DEBUG9(mpDebug, "GenerateResponseMessage");

  // returned message from USM, check for synch data. If successful,
  // do processing and return to sender, Address unknown?
  if ( 
      (NULL == (theSynchData = genRespMsg->get_synchData(true)))
      ||
      !(prepRespMsg = 
       dynamic_cast<snmpMPPrepareResponseMsgASI *>(theSynchData->get_msgObject(true))) 
      ||
      (NULL == (intData = genRespMsg->get_intData(true)))
      ) {
    DEBUG0(mpDebug, "GenerateResponseMessage: unable to extract synch "
	   << "data from incoming message, deleting message");
    // clean up any loose memory transDomain, transAddress maybe?
    if (theSynchData) delete theSynchData;
    delete genRespMsg;
    return;
  }

  // OK, we will now at least pass something back to the dispatcher.
  // The old message and the internal data exit, lets get them together
  // for a fun procedure of SNMP protocoling by lcd light.
  prepRespMsg->set_intData(intData);
  // clean up
  delete theSynchData;
  delete genRespMsg;

  if (NULL == 
      (theStatus = prepRespMsg->get_statusInformation())) {
    // no status info from USM, fail
    DEBUG0(mpDebug, "GenerateResponseMessage: no status info from USM, "
	  << "sending failure to dispatcher");
    prepRespMsg->set_statusInformation(new snmpStatusInfo(false));
  }
  else if (!(theStatus->get_success())) {
    // do any USM error stuff here...
  }
  // the message should have processed successfully through USM

  this->return_message(prepRespMsg);
    
} // snmpMPArchObj::GenerateResponseMessage


// handle incoming messages after USM processing.
void 
snmpMPArchObj::ProcessIncoming(snmpUSMProcessIncomingASI *procIncoming) {
  snmpSynchDataObj                 *theSynchData = 0;
  snmpMPPrepareDataElementsASI     *prepDataElems = 0;
  snmpStatusInfo                   *theStatus_ptr;
  snmpStandardMessage_internalData *intData;
  snmpStateReference               *refData;

  DEBUG9(mpDebug, "ProcessIncoming");
  // returned message from USM, check for synch data. Extract orginal
  // message from dispatcher. If successful, do processing and return 
  // to sender, Address unknown?
  if ((NULL == (theSynchData = procIncoming->get_synchData(true)))
      || 
      !(prepDataElems = 
	dynamic_cast<snmpMPPrepareDataElementsASI *>(theSynchData->get_msgObject(true))) 
      ||
      (NULL == (intData = procIncoming->get_intData(true)))
      ) {
    // couldn't get synch data
    DEBUG0(mpDebug, "ProcessIncoming: unable to extract "
	   << "synch data from returned USM message, throwing away.");
    // clean up any loose memory transDomain, transAddress maybe?
    if (theSynchData) delete theSynchData;
    delete procIncoming;
    return;
  }

  prepDataElems->set_intData(intData);
  // clean up
  delete theSynchData;
  delete procIncoming;

  // used below for asn decoding decision.
  Integer32 * securityLevel_ptr = prepDataElems->get_securityLevel(false);

  if (NULL == 
	   (theStatus_ptr = prepDataElems->get_statusInformation(false))) {
    // no status info from USM, fail
    DEBUG0(mpDebug, "ProcessIncoming: no status info from USM, "
	   << "sending failure to dispatcher");
    prepDataElems->set_statusInformation(new snmpStatusInfo(false));
  }
  // the message should have processed through USM
  else {
    // add the scopedPDU data structure to the prepDataElems
    ScopedPDU *sPDU        = new ScopedPDU();
    prepDataElems->set_scopedPDU(sPDU);

    // Extract data from message
    // Whether or not the USM successfully processed the message,
    // try to get the pdu info.
    // EoP: 7.2,7,8,9: asn parse scoped pdu and determine pdu_type
    // get data from scoped pdu.
    string    *sPDU_string = prepDataElems->get_scopedPDU_string();
    bool       sPDU_parsed = false;
    // check that sPDU_string is available and that it is unencrypted:
    // i.e. either never encrypted or successfully decrypted.
    if ( ((sPDU_string) && (sPDU_string->size() > 0))  
	 &&
	 ((int(*securityLevel_ptr) != SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV)
	  || theStatus_ptr->get_success()) )  {
      try {
	sPDU->asnDecode((char *)sPDU_string->c_str(), sPDU_string->length());
	sPDU_parsed = true;
      }
      catch (snmpStringException &except) {
	this->enginePtr->stats.increment(SNMP_STATS::SNMP_IN_ASN_PARSE_ERRORS);
	theStatus_ptr->set_success(false);
	DEBUG0(mpDebug, "ProcessIncoming: while asnDecoding scoped PDU, "
	       << "caught exception '" << except.get_errorMessage() 
	       << "', failure returned");
      }
      catch (...) {
	theStatus_ptr->set_success(false);
	DEBUG0(mpDebug, "ProcessIncoming: while asnDecoding scoped PDU, "
	       << "caught an UNKOWN exception, failure returned");
      }
    }

    // before heading onward, get some local pointers to make reading
    // code easier. 
    // Note: ownership is not taken, we are just pointing to data that's 
    // owned by prepDataElems.
    HeaderData *globalData_ptr = prepDataElems->get_globalData(false);
    Integer32   *securityModel_ptr = prepDataElems->get_securityModel(false);
    OctetString *securityName_ptr = prepDataElems->get_securityName(false);
    //Integer32   *securityLevel_ptr = prepDataElems->get_securityLevel(false);
    OctetString *securityEngineID_ptr = 
      prepDataElems->get_securityEngineID(false);
    Integer32   *msgMaxSize_ptr = prepDataElems->get_maxMsgSize(false);
    Integer32   *maxSizeResponsePDU_ptr = 
      prepDataElems->get_maxSizeResponsePDU(false);
    PDU         *pdu_ptr = prepDataElems->get_pdu(false);
    OctetString *contextName_ptr = prepDataElems->get_contextName(false);
    OctetString *contextEngineID_ptr = 
      prepDataElems->get_contextEngineID(false);


    // version and type are enumerations...
    PDU_Version *thePDU_Version = new PDU_Version;
    *thePDU_Version = pdu_ptr->get_version();
    prepDataElems->set_pduVersion(thePDU_Version);
    
    PDU_Type *thePDU_Type = new PDU_Type;
    *thePDU_Type = pdu_ptr->get_pduType();
    prepDataElems->set_pduType(thePDU_Type);
    
    // EoP: 7.2, 6: If an error indication is returned and the status
    //   information includes an OID/value pair, attempt a report pdu.
    //   Otherwise, discard message without further processing.
    if (!(theStatus_ptr->get_success())) {
      // EoP: 7.2,6 see if we can send a report PDU
      // or just fail.
      // look for snmpSNMPErrorObj in snmpStatusInfo that has a varBindList,
      // using the FIRST errorObj w/varBindList found.
      snmpSNMPErrorObj *pError;
      snmpErrorObj     *tempError;
      bool found = false;
      VarBindList *vList;
      queue<snmpErrorObj *> temp_q;
     
      while (0 != (tempError = theStatus_ptr->popErrorObj())) {
	temp_q.push(tempError);
	if ( !found
	     && ( pError = dynamic_cast<snmpSNMPErrorObj*>(tempError) )
	     && (0 != (vList = pError->get_varBindList(false)))
	   ) {
	  found = true;
	}
      }
      while (!temp_q.empty()) {
	theStatus_ptr->pushErrorObj(temp_q.front());
	temp_q.pop();
      }

      if (!(found)) {
	DEBUG5(mpDebug, "ProcessIncoming: status info is error, "
	       << "no varbind found, failing");
	// we already know that has a status and that its
	// success value is false, so just send it back.
	// prepDataElems->set_statusInformation(theStatus_ptr);    
      }
      else {  // send a report pdu
	DEBUG5(mpDebug, "ProcessIncoming: status info is error, "
	       << "sending report");

	// create a state reference.
	snmpStateReference *reportRef = new snmpStateReference;

	reportRef->msgID              = (globalData_ptr->get_msgID())->clone();
	reportRef->securityModel      = securityModel_ptr->clone();
	reportRef->securityName       = securityName_ptr->clone();
	reportRef->securityLevel      = securityLevel_ptr->clone();
	reportRef->securityEngineID   = new OctetString
	  (this->enginePtr->get_snmpEngineIdOS());
	reportRef->contextEngineID    = contextEngineID_ptr->clone();
	reportRef->contextName        = contextName_ptr->clone();
	reportRef->msgMaxSize         = msgMaxSize_ptr->clone();
	reportRef->maxSizeResponsePDU = maxSizeResponsePDU_ptr->clone();
	reportRef->msgFlags = (globalData_ptr->get_msgFlags())->clone();
	reportRef->securityStateReference = 
	  prepDataElems->get_securityStateReference(true);
	reportRef->transportDomain    = new TransportDomain;
	*(reportRef->transportDomain) = 
	  *(prepDataElems->get_transportDomain(false));
	reportRef->transportAddress   = 
	  (prepDataElems->get_transportAddress(false))->clone();

	// create a report message.
	snmpDispReturnResponsePduASI  *retRespMsg = 
	  new snmpDispReturnResponsePduASI
	  (new Integer32(SNMP_CONSTANTS::SNMP_MP_MODEL_SNMPv3),
	   reportRef->securityModel->clone(),
	   reportRef->securityName->clone(),
	   reportRef->securityLevel->clone(),
	   reportRef->contextEngineID->clone(),
	   reportRef->contextName->clone(),
	   (PDU_Version *)NULL, // PDU Version unused by snmpV3
	   pdu_ptr->clone(),
	   (prepDataElems->get_maxSizeResponsePDU())->clone(),
	   reportRef,           // stateReference
	   prepDataElems->get_statusInformation(true),  // status from USM
	   this->fifoPtr,       // FIFO to return to (i.e. MP's)
	   (snmpSynchDataObj *)NULL,
	   (snmpMessageObj   *)NULL,
	   0);                  // message ID (0 = create a new one)

	// send report message to dispatcher, current message fails.
	send_disp_message(retRespMsg);
	prepDataElems->set_statusInformation(new snmpStatusInfo(false));
      }
    }
    else { // message from USM is valid! (no error indication from USM)
      // set status, to the status object from USM (which we now know is
      // valid). But notice, the status may be changed directly
      // (e.g.  'theStatus_ptr->set_success(true/false)' )
      // before this mesasge is returned to the dispatcher.
      
      // EoP: 7.2,7,8,9: asn parse scoped pdu and determine pdu_type
      // get data from scoped pdu. 
      // Parse already attempted, but time to check if it was parsed 
      // succesfully for a valid message.
      if (!(sPDU_parsed)) {
	theStatus_ptr->set_success(false);
      }

      // On to more Elements of Procedure...
       
      // EoP: 7.2,10: if the pdu is from the response class
      // find in cache.
      // set return values (extract some from prepDataElems)
      else if ( SNMP_CONSTANTS::
		is_response_class(pdu_ptr->get_tag()) ) {
	snmpMPArchObj::map_iterator_type searchItr;
	
	// msgID may be used on a resend.
	int msgID = int( *(globalData_ptr->get_msgID()) );
	searchItr = this->outgoingRequestMap.find(msgID);

	if (searchItr == this->outgoingRequestMap.end()) {
	  DEBUG0(mpDebug, "ProcessIncomingASI: unable to find "
		 << "response-class pdu's cache data, failing");
	  theStatus_ptr->set_success(false);
	}
	else {  // cache data found!
	  refData = searchItr->second;
	  
	  // EoP: 7.2,11 If the pdu is from the internal class,
 	  // Handle incoming reports. We currently resend on: 
 	  // not_in_time_window errors, for time synching and 
 	  // unknownEngineID errors, if we're doing eng ID discovery.
	  if (SNMP_CONSTANTS::is_internal_class(pdu_ptr->get_tag())) {
	    // If we handle this report, dump current message.
	    if ( this->handle_reports(refData, pdu_ptr->get_varBindList(), 
				      msgID, securityEngineID_ptr) )  {
	      // we're resending the orginal, so dump current message...
	      theStatus_ptr->set_success(false);
	    }
	    // sends back to application all otherwise unhandled reports!
	    // add set pdu handle to match original message to report!
	    else {
	      prepDataElems->set_sendPduHandle(refData->sendPDUHandle);
	      DEBUG5(mpDebug, "ProcessIncomingASI: un-handled report "
		     << "passing up to CG, changing pduHandle to '" 
		     << refData->sendPDUHandle << 
		     "' in order to match original message.");
	    }
	  }
	  // otherwise, we send the report back to the application.

	  // EoP: 7.2,12 If the pdu is from the response class (inc.reports),
	  // retrieve cache informaton: snmpEngineID, securityModel,
	  // securityName, securityLevel, contextEngineID, contextName.
	  // if reference data doesn't match message data, fail.
	  
	  // we already know this is a response class but the RFC's
	  // differentiate between a report and and a non-report in an
	  // awkward way.
	  else if (SNMP_CONSTANTS::is_response_class(pdu_ptr->get_tag())) {
	    if  ( (*(refData->securityEngineID) != *(securityEngineID_ptr)) ||
		  (*(refData->securityModel)    != *(securityModel_ptr))    ||
		  (*(refData->securityName)     != *(securityName_ptr))     ||
		  (*(refData->securityLevel)    != *(securityLevel_ptr))    ||
		  (*(refData->contextEngineID)  != 
		   *(prepDataElems->get_contextEngineID()))   ||
		  (*(refData->contextName)      != 
		   *(prepDataElems->get_contextName())) )  {
	      DEBUG0(mpDebug, "ProcessIncomingASI: reference data != "
		     << "message data, failing");
	      theStatus_ptr->set_success(false);
	    }
	  } // if it is a response class
	  // set pdu handle appropriately
	  prepDataElems->set_sendPduHandle(refData->sendPDUHandle);
	  // get rid of cached reference data.
	  // resent data will get a new reference during re-sending.
	  this->outgoingRequestMap.erase(searchItr);
	  delete refData;
	} // data found in cache.
      } // this is a response class pdu.
      
      // EoP: 7.2,13: If the pdu is from the confirm class
      // (a) Check that security engine ID is equal to this engine's snmp
      // engine ID. (b) If so, pertinent message info is cached and (d) 
      // success is returned. 'c' apparently doesn't get a chance.
      // is this a confirmed class pdu?
      else  if (SNMP_CONSTANTS::is_confirmed_class(pdu_ptr->get_tag())) {

	if (string(*securityEngineID_ptr) != string(this->LOCAL_ENGINE_ID)) {
	  theStatus_ptr->set_success(false);
	  DEBUG0(mpDebug, "ProcessIncoming: Confirmed class PDU, but PDU's "
		 << "security engine ID '" << string(*securityEngineID_ptr)
		 << "' doesn't match local engine ID '" 
		 << this->LOCAL_ENGINE_ID << "'");
	}
	else {
	  snmpStateReference *newRef = new snmpStateReference;
	  
	  newRef->msgID              = globalData_ptr->get_msgID(true);
	  newRef->contextEngineID    = 
	    (prepDataElems->get_contextEngineID())->clone();
	  newRef->contextName        = 
	    (prepDataElems->get_contextName())->clone();
	  newRef->securityEngineID   = securityEngineID_ptr->clone();
	  newRef->securityModel      = securityModel_ptr->clone();
	  newRef->securityName       = securityName_ptr->clone();
	  newRef->securityLevel      = securityLevel_ptr->clone();
	  newRef->msgFlags           = globalData_ptr->get_msgFlags(true);
	  newRef->msgMaxSize         = globalData_ptr->get_maxMsgSize(true);
	  newRef->maxSizeResponsePDU = maxSizeResponsePDU_ptr->clone();
	  newRef->securityStateReference = 
	    prepDataElems->get_securityStateReference(true);
	  
	  newRef->transportDomain    = new TransportDomain;
	  *(newRef->transportDomain) = 
	    *(prepDataElems->get_transportDomain(false));
	  newRef->transportAddress   = 
	    (prepDataElems->get_transportAddress(false))->clone();
	  
	  prepDataElems->set_stateReference(newRef);
	}
      }
      // EoP: 7.2,14: if the pdu is from the unconfirmed class,
      // return success.
      // is this an uncormfirmed class pdu?
      else if (SNMP_CONSTANTS::is_unconfirmed_class(pdu_ptr->get_tag())) {
      }
      // this is an unknown pdu type.
      else {
	DEBUG0(mpDebug, "ProcessIncomingASI: Unknown pdu type, failing");
	theStatus_ptr->set_success(false);
      }
    }

  }  // else, status was processed from USM

  this->return_message(prepDataElems);
}  // snmpMPArchObj::ProcessIncoming


void 
snmpMPArchObj::main_loop()  {
  snmpMessageObj          *newMsg;
  int                      count = 0;
  
  // fifo->pop will block and wait for an snmpMessageObj to 
  // be placed on the fifo
  while (NULL != (newMsg = this->fifoPtr->pop())) {
    DEBUG9(mpDebug, "main_loop: New message is class '" 
	   << typeid(*newMsg).name() << "'");

    // incoming from dispatcher (most likely)
    if (snmpMPPrepareOutgoingMsgASI *prepOutMsg = 
	dynamic_cast<snmpMPPrepareOutgoingMsgASI *>(newMsg)) {
      DEBUG9(mpDebug, "NewMsg is an snmpMPPrepareareOutgoingMsgASI");
      this->PrepareOutgoingMessage(prepOutMsg);
    }
    else if (snmpMPPrepareResponseMsgASI *prepRespMsg = 
	     dynamic_cast<snmpMPPrepareResponseMsgASI *>(newMsg)) {
      this->PrepareResponseMessage(prepRespMsg);
      DEBUG9(mpDebug, "NewMsg is an snmpMPPrepareResponseMsgASI");
    }
    else if (snmpMPPrepareDataElementsASI *prepDataElem = 
	     dynamic_cast<snmpMPPrepareDataElementsASI *>(newMsg)) {
      DEBUG9(mpDebug, "NewMsg is an snmpMPPrepareDataElementsASI");
      this->PrepareDataElements(prepDataElem);
    }

    // incoming from usm
    else if (snmpUSMGenerateRequestMsgASI *genReqMsg = 
	dynamic_cast<snmpUSMGenerateRequestMsgASI *>(newMsg)) {
      DEBUG9(mpDebug, "NewMsg is an snmpUSMGeneratRequestMsgASI");
      this->GenerateRequestMessage(genReqMsg);
    }
    else if (snmpUSMGenerateResponseMsgASI *genRespMsg = 
	     dynamic_cast<snmpUSMGenerateResponseMsgASI *>(newMsg)) {
      DEBUG9(mpDebug, "NewMsg is an snmpUSMGenerateResponseMsgsASI");
      this->GenerateResponseMessage(genRespMsg);
    }
    else if (snmpUSMProcessIncomingASI *procIncoming = 
	     dynamic_cast<snmpUSMProcessIncomingASI *>(newMsg)) {
      DEBUG9(mpDebug, "NewMsg is an snmpUSMProcessIncomingASI");
      this->ProcessIncoming(procIncoming);
    }
    // returned status from a report we tried to send
    else if (snmpDispReturnResponsePduASI *retResponse = 
	     dynamic_cast<snmpDispReturnResponsePduASI *>(newMsg)) {
      DEBUG9(mpDebug, "NewMsg is an snmpDispReturnResponsePduASI");
      int reqID = 0;
      if (NULL != retResponse->get_pdu(false))
	reqID = retResponse->get_pdu(false)->get_requestID();
      if (retResponse->get_statusInformation(false)->get_success()) {
	DEBUG9(mpDebug, "Report, req ID: " << reqID	<< ", sent");
      }
      else {
	DEBUG9(mpDebug, "Report, req ID: " << reqID	
	       << ", Failed sending!");
      }
      delete retResponse;
    }
    // returned status from resent, post eng ID discovery, message.
    else if (snmpDispSendPduASI *retDiscovery = 
	     dynamic_cast<snmpDispSendPduASI *>(newMsg)) {
      DEBUG9(mpDebug, "NewMsg is an snmpDispSendPduASI");
      if (retDiscovery->get_statusInformation(false)->get_success()) {
	DEBUG5(mpDebug, "Succesfully resent message after discovery");
      }
      else {
	DEBUG5(mpDebug, "Failed sending message after discovery?");
      }
      delete retDiscovery;
    }
    // returned status from resent, post eng ID discovery, message.
    else if (usmAddUserMsg *retAddUser = 
	     dynamic_cast<usmAddUserMsg *>(newMsg)) {
      DEBUG9(mpDebug, "NewMsg is an usmAddUserMsg");
      if (retAddUser->get_statusInformation(false)->get_success()) {
	DEBUG5(mpDebug, "Succesfully added user after discovery");
      }
      else {
	DEBUG5(mpDebug, "Failed adding user after discovery??");
      }
      delete retAddUser;
    }
    else if (snmpExitMessage *xMsg = 
	     dynamic_cast<snmpExitMessage *>(newMsg)) {
      // doing any neccessary clean up here.
      DEBUG9(mpDebug, "NewMsg is an snmpExitMessage, exiting v3 " <<
	     "message processor thread");
      delete xMsg;
      return;
    }
    // or not an acceptable message type...
    else {
      DEBUG0(mpDebug, "Error, Message Processor received unknown "
	     << "message type");
      delete newMsg;
    }
    
    count++;
  } // while (NULL != (newMsg = this->fifoPtr->pop()))
  
}  // main_loop


void 
snmpMPArchObj::handleInFIFO(snmpMessageObj *message)  {
  DEBUG9(mpDebug, "handle in FIFO from MP Arch");
}

