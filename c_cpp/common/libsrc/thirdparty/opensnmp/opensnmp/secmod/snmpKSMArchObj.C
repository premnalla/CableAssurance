//
// snmpKSMArchObj
//


#include "config.h"
#include "snmpKSMArchObj.H"
#include "snmpRegObj.H"
#include "snmpV3Message.H"
#include "snmpConstants.H"
#include "snmpStats.H"
#include "snmpCRRegisterTable.H"
#include "snmpCRRegisterScalarSet.H"
#include "snmpStorageType.H"
#include "snmpRowStatus.H"
#include "snmpRowStatusColumnAllocator.H"
#include "snmpRowStorageColumnAllocator.H"
#include "snmpPersistentRowManager.H"
#include "snmpRowStatusCheckColsExist.H"
#include "snmpKeyChangeColumnAllocator.H"

#include <stdio.h>
#include <iostream>
#include <string>
#include <typeinfo>

const int snmpKSMArchObj::BUFFER_SIZE       = 4096;
const int snmpKSMArchObj::MESSAGE_OVERHEAD  = 160;
const OID snmpKSMArchObj::ksmStatsOID       = OID(".1.3.6.1.6.3.15.1.1");

snmpKSMArchObj::snmpKSMArchObj()  {
  this->fifoPtr = NULL;
  this->crFIFO  = NULL;
  this->mpFIFO  = NULL;
  this->crFIFO  = NULL;

  DEBUGCREATE(ksmDebug, "KSM");
  DEBUG9(ksmDebug, "snmpKSMArchObj Constructor");
} // snmpKSMArchObj (constructor)

snmpKSMArchObj::snmpKSMArchObj(snmpEngine *engPtr)  {
  this->fifoPtr   = NULL;
  this->mpFIFO    = NULL;
  this->crFIFO    = NULL;
  this->enginePtr = engPtr;
  this->kcontext  == NULL;

  DEBUGCREATE(ksmDebug, "KSM");
  DEBUG9(ksmDebug, "snmpKSMArchObj Constructor");

  //imported
  krb5_error_code retval;
  struct snmp_secmod_def *def;
  int i;
  
  //get kcontext
  retval = krb5_init_context(&kcontext);
    
  if (retval) {
    DEBUG0(ksmDebug,"krb5_init_context failed (" << error_message(retval) <<
	   "), not registering KSM\n");
    return;
  }
  
  for (i = 0; i < HASHSIZE; i++)
    ksm_hash_table[i] = NULL;
  
  def = SNMP_MALLOC_STRUCT(snmp_secmod_def);
  
  if (!def) {
    DEBUGMSGTL(("ksm", "Unable to malloc snmp_secmod struct, not "
		"registering KSM\n"));
    return;
  }
  
  def->encode_reverse = ksm_rgenerate_out_msg;
  def->decode = ksm_process_in_msg;
  def->session_open = ksm_session_init;
  def->pdu_free_state_ref = ksm_free_state_ref;
  def->pdu_free = ksm_free_pdu;
  def->pdu_clone = ksm_clone_pdu;
  
  register_sec_mod(2066432, "ksm", def);
  
} // snmpKSMArchObj (constructor)

snmpKSMArchObj::~snmpKSMArchObj()  {
  DEBUG9(ksmDebug, "snmpKSMArchObj Deconstructor");
  DEBUGDESTROY(ksmDebug);
} // ~snmpKSMArchObj


void 
snmpKSMArchObj::return_message(snmpMessageObj *theMessage) {
  snmpFIFOObj             *returnFIFO;

  DEBUG9(ksmDebug, "return_message");
  // return message to its return FIFO, if it exists.
  if ( NULL != (returnFIFO = theMessage->get_returnFIFO()) ) {
    DEBUG9(ksmDebug, "return_message: message being returned to retFIFO");
    returnFIFO->push(theMessage);
  }
  // no return FIFO, check if it is a defaulted message type...
  else if ( (typeid(*theMessage) == typeid(snmpUSMGenerateRequestMsgASI)) ||
	    (typeid(*theMessage) == typeid(snmpUSMGenerateResponseMsgASI)) ||
	    (typeid(*theMessage) == typeid(snmpUSMProcessIncomingASI)) ) {

    if ( (NULL != this->mpFIFO) || 
	 (NULL != (this->mpFIFO = this->theReg.findArchFIFO("MP3"))) ) {
      DEBUG5(ksmDebug, "return_message: msg sent to MP3 (def. for msg type)");
      this->mpFIFO->push(theMessage);
    }

  }

  // no known return FIFO.
  else {
    DEBUG0(ksmDebug, "return_message: Unknown return fifo for the current message, message deleted");
    delete theMessage;
  }

} // return_message


snmpSNMPErrorObj*
snmpKSMArchObj::processError() {
  // get the statistics OID XXXX
  OID *errorOID  = new OID(.1.1.1.0);
  
  // increment and get the statistics new value XXX ?
  Integer32 *errorValue = new Integer32(0);
  
  // create the varbind list of one varbind representing the error.
  VarBindList *errVBList  = new VarBindList(new VarBind(errorOID, errorValue));

  // return the child object of snmpErrorObj with the VarBindList
  return(new snmpSNMPErrorObj(errVBList, snmpErrorObj::SNMP_ERROR));
} // processStatsError


void
snmpKSMArchObj::GenerateRequestMessage(snmpUSMGenerateRequestMsgASI   *genReqMsg) {
  DEBUG9(ksmDebug, "GenerateRequestMessage");

  snmpStatusInfo  *theStatus = new snmpStatusInfo(true);
  genReqMsg->set_statusInformation(theStatus);

  OctetString      *locSecName = genReqMsg->get_securityName();
  OctetString     *locSecEngID = genReqMsg->get_securityEngineID();
  Integer32       *locSecLevel = genReqMsg->get_securityLevel();

  //  snmpSecurityStateReference *userData = 
  //    new snmpKSMSecurityStateReference();

  // If all the data doesn't exist in the incoming message, fail.
  if ( (locSecName == 0) || (locSecEngID == 0) || (locSecLevel == 0) ) {
    DEBUG0(ksmDebug, "GenerateRequestMessage: security name or security "
	   << "engine ID does not exist in message, failing");
    theStatus->set_success(false);
    theStatus->pushErrorObj(new snmpErrorObj(snmpErrorObj::BAD_MSG_DATA));
  }
  else { 
    // do all the shared request/response work...
    this->GenerateOutMessage(genReqMsg);
  }
  
  return_message(genReqMsg);
} // GenerateRequestMessage


void
snmpKSMArchObj::GenerateResponseMessage(snmpUSMGenerateResponseMsgASI *genRespMsg) {
  DEBUG9(ksmDebug, "GenerateResponseMessage");

  // default status to true, change it later if neccesary.
  snmpStatusInfo  *theStatus = new snmpStatusInfo(true);
  genRespMsg->set_statusInformation(theStatus);

  // convert statereference to something usable
  //  if (!(userData = dynamic_cast<snmpSecurityStateReference *>
  //                     (genRespMsg->get_securityStateReference()))) {
  //    DEBUG0(ksmDebug, "GenerateResponseMessage: security state reference "
  //	   << "casting failed, KSM failing");
  //    theStatus->set_success(false);
  //    theStatus->pushErrorObj(new snmpErrorObj(snmpErrorObj::BAD_MSG_DATA));
  //  }
  //  else {
    // do all the shared request/response work...
  this->GenerateOutMessage(genRespMsg);
    //  }
  
  return_message(genRespMsg);
}  // GenerateResponseMsg



void
snmpKSMArchObj::GenerateOutMessage(snmpKSMGenericOutMsg  *genOutMsg) {
  DEBUG9(ksmDebug, "GenerateOutMsg");

  // get a ptr to the status object (added to msg, & =true, in prev. routine)
  snmpStatusInfo  *theStatus = genOutMsg->get_statusInformation();

  // Pull the data out of the  message.
  Integer32   *locMsgProcModel = genOutMsg->get_messageProcessingModel();
  Integer32       *locSecModel = genOutMsg->get_securityModel();
  OctetString      *locSecName = genOutMsg->get_securityName();
  Integer32       *locSecLevel = genOutMsg->get_securityLevel();
  OctetString     *locSecEngID = genOutMsg->get_securityEngineID();

  ScopedPDU  *locScopedPDU  = genOutMsg->get_scopedPDU();
  Integer32  *locMaxMsgSize = genOutMsg->get_maxMsgSize();
  HeaderData *locGlobalData = genOutMsg->get_globalData();

  // If all the data doesn't exist in the incoming message, fail.
  if ( (locMsgProcModel == 0) || (locSecModel == 0) || (locSecName == 0) || 
       (locSecLevel == 0)   || (locScopedPDU == 0) || (locSecEngID == 0) ||
       (locMaxMsgSize == 0) || (locGlobalData == 0) ) {

    DEBUG0(ksmDebug, "GenerateOutMsg: not all the data exists in incoming "
	   << "message, failing");
    theStatus->set_success(false);
    theStatus->pushErrorObj(new snmpErrorObj(snmpErrorObj::BAD_MSG_DATA));
  }

  // check the security level.
  // If it is auth-priv, fail if the user dosen't support both
  // privacy and authentication. XXX

  else if ( (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV == int(*locSecLevel)) 
	    &&
	    ( ( userData->authProtocol == this->ksmData.ksmNoAuthProtocolOID ) 
	      ||
	      ( userData->privProtocol == this->ksmData.ksmNoPrivProtocolOID ) 
	    )
	  )  {
    theStatus->set_success(false);
    theStatus->pushErrorObj(this->processError());
  }

  // check the security level.
  // If it is auth-noPriv, fail if the user dosen't support authentication.
  // XXX

  else if ( (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV == int(*locSecLevel)) &&
	    (userData->authProtocol == this->ksmData.ksmNoAuthProtocolOID) 
	  )  {
    theStatus->set_success(false);
    theStatus->pushErrorObj(this->processError());
  }

  // XXX **************** 
  // The user's info in userData (from cache data or ksmUserTable) should 
  // provide the neccessary info for any desired auth./encrypt.

  else {

    OctetString *privParams = new OctetString();

    // if the security level is AuthPriv, encrypt the scoped PDU
    if (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV == int(*locSecLevel)) { 
      // XXX      if (!(this->encryptData(userData, locScopedPDU, privParams))) {
	theStatus->set_success(false);
	theStatus->pushErrorObj(new snmpErrorObj
				      (snmpErrorObj::ENCRYPTION_ERROR));
	//      }
    }

    // it is time to serialize the message.

    
    //    KSMSecurityParameters *secParams = new KSMSecurityParameters()
    asnDataType *secParams = new asnDataType(); // XXX
    
    snmpV3Message *theMsg = new snmpV3Message
      ( locMsgProcModel,
	locGlobalData,
	(asnDataType *)secParams,
	locScopedPDU );
    
    char* buffer = new char[BUFFER_SIZE];
    char* outMsg;
    Integer32*  outMsgLength = 0;
  
    try {
      outMsg = theMsg->asnEncode(buffer + BUFFER_SIZE - 1);
      if (outMsg)
        outMsg++; // Get to the real data location, since encoding
                  // returns the next free byte.
      outMsgLength = new Integer32(buffer + BUFFER_SIZE - outMsg);
      
      BufferClass *theWholeOutMsg = 
	new BufferClass(buffer, BUFFER_SIZE,
			outMsg, (buffer + BUFFER_SIZE - outMsg));

      DEBUG9(ksmDebug, "The Outgoing message is: \n" << theMsg->toString());
 
      genOutMsg->set_wholeOutMsg(theWholeOutMsg);
      genOutMsg->set_wholeOutMsgLength(outMsgLength);
      genOutMsg->set_securityParameters(secParams);
    }
    
    catch (snmpStringException &except) {
      DEBUG0(ksmDebug, "GenerateOutMsg: while asn encoding V3 message " << 
	     "caught exception '" << except.get_errorMessage() 
	     << "', failing");
      delete []  buffer;
      theStatus->set_success(false);
      theStatus->pushErrorObj(new snmpErrorObj
			      (snmpErrorObj::ASN_ENCODE_ERROR));
    }
    catch (...) {
      DEBUG0(ksmDebug, "GenerateOutMsg: while asn encoding V3 message " << 
	     "caught UNKNOWN exception, failing");
      delete []  buffer;
      theStatus->set_success(false);
    }

    // if the security level specifies authenticatation,
    // authenticate 

    if ( (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV == int(*locSecLevel))
	 ||
	 (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHNOPRIV == int(*locSecLevel)) ) {

      //      if (!(this->authenticateMessage())) {
      // XXX
	DEBUG0(ksmDebug, "GenerateOutMsg: Error authenticating  message, "
	       << "failing");
	theStatus->set_success(false);
	theStatus->pushErrorObj(new snmpErrorObj
				(snmpErrorObj::AUTHENTICATION_ERROR));
	//      }
    }

  }
  
}  // generate out message


void
snmpKSMArchObj::ProcessIncoming(snmpUSMProcessIncomingASI  *procIncoming) {
  DEBUG9(ksmDebug, "ProcessIncoming");
  // create a status, default it to true, change later if neccessary
  snmpStatusInfo *theStatus = new snmpStatusInfo(true);
  procIncoming->set_statusInformation(theStatus);

  snmpV3Message            *theMsg = procIncoming->get_wholeOutMsg();
  // XXX  KSMSecurityParameters *secParams = new KSMSecurityParameters();
  asnDataType *secParams = new asnDataType;

  // Elements of Procedure: 3.2,1: parse security parameters
  try  {
    secParams->asnDecode(theMsg->get_msgSecurityParametersSerialLocation());
  }
  catch (snmpStringException &except) {
    DEBUG5(ksmDebug, "ProcessIncoming: while asn decoding security " 
	   << "parameters caught exception '" << except.get_errorMessage() 
	   << "', failure returned");
    theStatus->set_success(false);
    theStatus->pushErrorObj(new snmpErrorObj
			    (snmpErrorObj::ASN_PARSE_ERROR));
    delete secParams;
    return_message(procIncoming);
    return;
  }
  catch (...) {
    DEBUG5(ksmDebug, "ProcessIncoming: while asn decoding security " 
	   << "parameters caught UNKNOWN exception, failure returned");
    theStatus->set_success(false);
    theStatus->pushErrorObj(new snmpErrorObj
			    (snmpErrorObj::ASN_PARSE_ERROR));
    delete secParams;
    return_message(procIncoming);
    return;
  }

  //  prepare security state reference
  OctetString *loc_secName = secParams->get_msgUserName(true);
  procIncoming->set_securityName(loc_secName);

  OctetString *loc_secEngineID = 
    secParams->get_msgAuthoritativeEngineID()->clone();
  procIncoming->set_securityEngineID(loc_secEngineID);

  Integer32 *loc_secLevel = procIncoming->get_securityLevel();

  // If all the data doesn't exist in the incoming message, fail.
  if ( (loc_secName == 0) || (loc_secLevel == 0) || 
       (loc_secEngineID == 0) ) {
    DEBUG5(ksmDebug, "ProcessIncomingMsg: not all the data exists in "
	   << "incoming message, failing");
    theStatus->set_success(false);
    theStatus->pushErrorObj(new snmpErrorObj(snmpErrorObj::BAD_MSG_DATA));
    delete secParams;
    return_message(procIncoming);
    return;
  }

  int secLevel = int(*loc_secLevel); // used for speed later.

  //  snmpKSMSecurityStateReference *setState = 
  //    new snmpKSMSecurityStateReference();
  //  procIncoming->set_securityStateReference(setState);

  // EoP: 3.2,9: calculate maxSizeResponseScopedPDU
  // This is a fairly arbitrary number at the moment...
  // Done here, because it may be needed un a report message even if 
  // processing fails, say in 3.2,3...
  procIncoming->set_maxSizeResponseScopedPDU(new Integer32
     ( int(*(procIncoming->get_maxMessageSize())) - this->MESSAGE_OVERHEAD) );

  // check if the requested 
  // security level is supported by the user's local data
  if (0) {
//   if ( XXX ) {
//     theStatus->set_success(false);
//     theStatus->pushErrorObj(this->processStatsError
// 			    (ksmStatsUnsupportedSecLevels));
  }
  else {

    // If the security level specifies authentication, 
    // check the messages authentication.
    if (SNMP_CONSTANTS::SNMP_SEC_LEVEL_NOAUTH != secLevel) {
      //      if (!(this->checkAuthentication(userData, secParams, theMsg)))  {
	DEBUG5(ksmDebug, "ProcessIncoming: Error, Message NOT Authenticated!");
	theStatus->set_success(false);
	theStatus->pushErrorObj(this->processStatsError
				(ksmStatsWrongDigests));
	//      }
    }
     
    char       *payload = theMsg->get_scopedPDUSerialLocation();
    int   payloadLength = asnDataType::asnLengthWithHeader(payload);
    string *sPDU_string = new string();
    // EoP: 3.2,8 a): if the securiyt level indicates the message is protected
    // from disclosure (i.e. encrypted), decrypt the message (if we are 
    // still valid from above).
    if ( (SNMP_CONSTANTS::SNMP_SEC_LEVEL_AUTHPRIV == secLevel) &&
	 (theStatus->get_success())
       )  {
      // XXX     if ( !(this->decryptData(userData, secParams, 
			       payload, payloadLength, 
			       sPDU_string)) ) {
	DEBUG5(ksmDebug, "ProcessIncoming: Error, decrypt failed!");
	theStatus->set_success(false);
	theStatus->pushErrorObj(this->processStatsError
				(ksmStatsDecryptionErrors));
	//      }
	// else DEBUG9(ksmDebug,"ProcessIncoming: Scoped PDU succesfully decrypted");
    }
    else  {  // no encrypton
      sPDU_string->assign(payload, payloadLength);      
    }
    procIncoming->set_scopedPDU(sPDU_string);
    
  }

  return_message(procIncoming);
}; // ProcessIncoming


void 
snmpKSMArchObj::main_loop()  {
  snmpMessageObj          *newMsg;
  int                      count = 0;
  
  // fifo->pop will block and wait for an snmpMessageObj to 
  // be placed on the fifo
  while (NULL != (newMsg = this->fifoPtr->pop())) {
    DEBUG9(ksmDebug, "top of main loop\nNew message is class '" 
	   << typeid(*newMsg).name() << "'");

    if (snmpUSMGenerateRequestMsgASI *genReqMsg = 
	dynamic_cast<snmpUSMGenerateRequestMsgASI *>(newMsg)) {
      DEBUG9(ksmDebug, "NewMsg is an snmpUSMGenerateRequestMsgASI");
      GenerateRequestMessage(genReqMsg);
    }
    else if (snmpUSMGenerateResponseMsgASI *genRespMsg = 
	     dynamic_cast<snmpUSMGenerateResponseMsgASI *>(newMsg)) {
      DEBUG9(ksmDebug, "NewMsg is an snmpUSMGenerateResponseMsgASI");
      GenerateResponseMessage(genRespMsg);
    }
    else if (snmpUSMProcessIncomingASI *procIncoming = 
	     dynamic_cast<snmpUSMProcessIncomingASI *>(newMsg)) {
      DEBUG9(ksmDebug, "NewMsg is an snmpUSMProcessIncomingASI");
      ProcessIncoming(procIncoming);
    }

    // or not an acceptable message type...
    else {
      DEBUG0(ksmDebug, "main_loop: Error, received unknown message type");
    }

    count++;
  } // while ( (NULL != (newMsg = this->fifoPtr->pop())) && (count < 10) )

}  // main_loop


