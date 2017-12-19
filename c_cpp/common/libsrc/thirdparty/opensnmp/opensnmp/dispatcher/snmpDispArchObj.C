//
// snmpDispaArchObj
//

#include <config.h>

#include <stdio.h>
#include <string>
#include <typeinfo>
#include <iostream>
#include <errno.h>

#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#include "asnDataTypes.H"
#include "snmpFIFOObj.H"
#include "snmpRegObj.H"
#include "snmpMessageObj.H"
#include "snmpDispRegisterEngIdASI.H"
#include "snmpDispUnregisterEngIdASI.H"
#include "snmpDispSendPduASI.H"
#include "snmpDispReturnResponsePduASI.H"
#include "snmpMPPrepareOutgoingMsgASI.H"
#include "snmpMPPrepareResponseMsgASI.H"
#include "snmpMPPrepareDataElementsASI.H"
#include "snmpAppProcessPduASI.H"
#include "snmpAppProcessResponsePduASI.H"
#include "snmpDispHandleInMsg.H" 
#include "snmpDispArchObj.H"
#include "BufferClass.H"
#include "opensnmpUtilities.H"

#define SNMP_SUCCESS 0
#define SNMP_FUNCTION_PARAMS_ERR 1
#define SNMP_MSG_MISSING_SYNCH_DATA_ERR 2
#define SNMP_MSG_MISSING_SYNCH_MSG_ERR 3
#define SNMP_MSG_MISSING_RET_FIFO_ERR 4
#define SNMP_MSG_MISSING_STATUS_ERR 5
#define SNMP_UNKNOWN_PDU_HANDLER_ERR 6

#define MSG_PASS_ARG(msgDst, msgSrc, arg) \
           msgDst->set_##arg(msgSrc->get_##arg(true))

#define MSG_PASS_ARG2(msgDst, msgSrc, argDst, argSrc) \
           msgDst->set_##argDst(msgSrc->get_##argSrc(true))

#define MSG_PASS_ARG_REF(msgDst, msgSrc, arg) \
           msgDst->set_##arg(msgSrc->get_##arg(false))

const static OctetString wildcardEngId;

int 
snmpDispArchObj::dispatch_incoming_msg(snmpMPPrepareDataElementsASI *msg) {
  snmpFIFOObj  *appFifo = NULL;
  //  snmpDispHandleInMsg *handleInMsg;
  DEBUG9(dispDebugObj,  string("DISP:dispatch_incoming_msg"));

  PDU_Type *pduType = msg->get_pduType(false); 
  const OctetString *contextEngId = msg->get_contextEngineID(false);
 retry:  
  // find entries for this engine id
  multimap<OctetString, snmpPduTypeReg *>::iterator eit 
    = this->engIdPduRegistry.lower_bound(*contextEngId);

  // search for registration record for this contextEngId/pduType
  for(; eit != this->engIdPduRegistry.upper_bound(*contextEngId); eit++) {
    snmpPduTypeReg *entry = eit->second;
    if (*(entry->pduTypeSet->find(*pduType))) {
      appFifo = entry->fifoPtr;
      break;
    }
  }
  if (appFifo == NULL) {
    if (contextEngId->size()) {
      // try zero length wilcard engineId
      contextEngId = const_cast<OctetString*>(&wildcardEngId);
      goto retry;
    } else {
      DEBUG9(dispDebugObj, string("no pduHandler found: PDU type '") + \
	     OPENSNMP_UTILITIES::intToString((int)*pduType) + \
	     string("'\n") );
      // increment unknownPduHandler
      return SNMP_UNKNOWN_PDU_HANDLER_ERR;
    }
  }
  if (!SNMP_CONSTANTS::is_response_class(*pduType)) {

    snmpAppProcessPduASI *processPduMsg = 
      new snmpAppProcessPduASI(msg->get_intData(true));

    // send the CR/NR/PROXY app processPdu message
    appFifo->push(processPduMsg);
  } else {
    snmpAppProcessResponsePduASI *processResponsePduMsg = 
      new snmpAppProcessResponsePduASI(msg->get_intData(true));

    appFifo->push(processResponsePduMsg);
  }
  
  return SNMP_SUCCESS;
} // dispatch_incoming_msg


// check_internal_message parses a message for it's synchdata
// and the message object and ret FIFO within the synch data.
int
snmpDispArchObj::check_internal_message(snmpMessageObj* inMsg, 
					snmpSynchDataObj** synchData,
					snmpMessageObj** synchMsg, 
					snmpFIFOObj** retFifo)
{
  DEBUGCREATE_L(dispDebugObj_L, string("DISP:check_internal_message"));
  DEBUG9_L(dispDebugObj_L, string(" "));

  if (inMsg == NULL || synchData == NULL || synchMsg == NULL || 
      retFifo == NULL) return SNMP_FUNCTION_PARAMS_ERR;

  *synchData = inMsg->get_synchData(true);
  if (*synchData == NULL) {
    DEBUG1_L(dispDebugObj_L, string("Warning: no synch data in message"));
    return SNMP_MSG_MISSING_SYNCH_DATA_ERR;
  }

  *synchMsg = (*synchData)->get_msgObject(true);
  if (*synchMsg == NULL) {
    DEBUG1_L(dispDebugObj_L, string("Warning: no synch message in message"));
    return SNMP_MSG_MISSING_SYNCH_MSG_ERR;
  }

  *retFifo = (*synchMsg)->get_returnFIFO(true);
  if (*retFifo == NULL) {
    DEBUG1_L(dispDebugObj_L, string("Warning: no return FIFO in message"));
    return SNMP_MSG_MISSING_RET_FIFO_ERR;
  }

  return SNMP_SUCCESS;
}

int
snmpDispArchObj::parse_version(char *data, int length) {
  RawData msgData(BER_TAG_SEQUENCE);
  Integer32 version;
  int asn_len = length;
  data = (char*)msgData.DecodeHeader((char*)data, &asn_len);
  data = (char*)version.asnDecode((char*)data, asn_len);
  // need to catch exceptions here and return parse error
  return ((int) version);
}  // parse_version


int
snmpDispArchObj::register_engid(OctetString           *engId, 
				set<PDU_Type>         *pduTypeSet,
				set<TransportAddress> *addSet,
				snmpFIFOObj           *retFifo) {
  struct snmpPduTypeReg *entry;
  DEBUG9(dispDebugObj, string("DISP:register_engid"));

  // find entries for this engine id
  multimap<OctetString, snmpPduTypeReg *>::iterator eit 
    = this->engIdPduRegistry.lower_bound(*engId);

  // error if any same pdu types are already registered for this engId
  for(; eit != this->engIdPduRegistry.upper_bound(*engId); eit++) {
    entry = eit->second;
    for (set<PDU_Type>::iterator pit = pduTypeSet->begin();
	 pit != pduTypeSet->end(); ++pit) {
      if (entry->pduTypeSet->count(*pit)) {
	DEBUG9(dispDebugObj, string("warning: ") + eit->first.toString() \
	       + " already registered for " + \
	       OPENSNMP_UTILITIES::intToString((int)*pit));
	return 0;
      }
    }
  }

  // We can register try to listen to addresses
  set<TransportAddress>::iterator tasi;
  for (tasi = addSet->begin(); tasi != addSet->end(); tasi++) {
    if (!this->netObj->listen_tAddress((TransportAddress)*tasi)) {
      return 0;
    }
  }

  // create registry and store in multimap
  entry = new struct snmpPduTypeReg;
  entry->pduTypeSet = pduTypeSet;
  entry->fifoPtr = retFifo;

  DEBUG9(dispDebugObj, string("registering pduTypes for ") + \
	 engId->toString() );
  this->engIdPduRegistry.insert(pair<OctetString, snmpPduTypeReg*>
				    (*engId,entry));
  return 1;
}  // register_engid


int
snmpDispArchObj::unregister_engid(OctetString *engId, 
				  set<PDU_Type>  *pduTypeSet) {
  struct snmpPduTypeReg *entry;
  int siz = 0;
  DEBUG9(dispDebugObj,  string("DISP:unregister_engid"));

 retry:
  // find entries for this engine id
  multimap<OctetString, snmpPduTypeReg *>::iterator eit 
    = this->engIdPduRegistry.lower_bound(*engId);

  for(; eit != this->engIdPduRegistry.upper_bound(*engId); eit++) {
    entry = eit->second;
    for (set<PDU_Type>::iterator pit = pduTypeSet->begin();
	 pit != pduTypeSet->end(); ++pit) {
      if ((siz = entry->pduTypeSet->erase(*pit))) {
	DEBUG9(dispDebugObj, string("removing: ") + eit->first.toString() \
	       + " : " + OPENSNMP_UTILITIES::intToString((int)*pit) +  \
	       " : " + OPENSNMP_UTILITIES::intToString(siz) );
      } else {
	DEBUG9(dispDebugObj, string("failed removing: ") + \
	       eit->first.toString() + " : " + \
	       OPENSNMP_UTILITIES::intToString((int)*pit) + " : " + \
	       OPENSNMP_UTILITIES::intToString(siz) );
      }
    }
  }
  if (engId->size() && siz == 0) {
    engId = const_cast<OctetString*>(&wildcardEngId);
    goto retry;
  }

  return 1;
}  // unregister_engid


snmpDispArchObj::snmpDispArchObj(const snmpDispArchObj &ref)
{
  cerr << "ERROR:snmpDispArchObj copy ctor not implemented!" << endl;
  throw;
}


snmpDispArchObj::snmpDispArchObj(snmpEngine *theEngine)  {
  snmpRegObj               theReg;
  DEBUGCREATE(dispDebugObj, string("DISP"));

  this->fifoPtr   = NULL;
  this->enginePtr = theEngine;
  this->mpFIFO    = theReg.findArchFIFO("MP3");
  this->pduHandleCounter = 1;

  DEBUG9(dispDebugObj, string("snmpDispArchObj constructor"));
}

snmpDispArchObj::~snmpDispArchObj()  {
  DEBUG9(dispDebugObj, string("destructor"));
  DEBUGDESTROY(dispDebugObj);
}


int 
snmpDispArchObj::send_MP_msg(snmpMessageObj *msg)  {
  snmpRegObj        theReg;
  DEBUG9(dispDebugObj, string("DISP:send_MP_msg"));

  if (NULL == this->mpFIFO) {
    if ( NULL == (this->mpFIFO = theReg.findArchFIFO("MP3")) ) {
      DEBUG9(dispDebugObj, 
	     string("ERROR: unable to find MP3 fifo in registry"));
      // raise exception.
    } else {
      DEBUG9(dispDebugObj, string("requested and found the MP3 fifo:") + \
	     OPENSNMP_UTILITIES::intToString(int(mpFIFO)) );
    }
  }
  this->mpFIFO->push(msg);
  return SNMP_SUCCESS;
} // send_MP_msg


void 
snmpDispArchObj::main_loop()  {
  snmpMessageObj   *newMsg;
  int               count = 0;
  int               result;
  snmpSynchDataObj *synchData;
  snmpMessageObj   *synchMsg;
  snmpFIFOObj      *retFifo;

  DEBUG9(dispDebugObj, string("DISP:main_loop"));

  if (this->fifoPtr == NULL) {
    DEBUG0(dispDebugObj, string("ERROR: our fifo is NULL???"));
    exit(99);
  }
  // fifo->pop will block and wait for an snmpMessageObj to 
  // be placed on the fifo
  while (NULL != (newMsg = this->fifoPtr->pop())) {
    DEBUG9(dispDebugObj, string("recvd a new message in fifo: number = ") + \
	   OPENSNMP_UTILITIES::intToString(count) + ": newMsg = " + \
	   OPENSNMP_UTILITIES::intToString(int(newMsg)) );

    if (snmpDispRegisterEngIdASI *msg 
	       = dynamic_cast<snmpDispRegisterEngIdASI *>(newMsg)) {
      OctetString *engId = msg->get_contextEngineID(true);
      set<PDU_Type> *pduTypeSet = msg->get_pduTypeSet(true);
      set<TransportAddress> *lAddressSet = msg->get_lAddressSet(true);
      snmpFIFOObj *retFifo = msg->get_returnFIFO(true);

      DEBUG9(dispDebugObj, string("processing a RegisterEngId message") );
      int result = this->register_engid(engId,pduTypeSet,lAddressSet,retFifo);

      msg->set_statusInformation(new snmpStatusInfo((bool)result));
      retFifo->push(newMsg);

    } else if (snmpDispSendPduASI *msg 
	       = dynamic_cast<snmpDispSendPduASI *>(newMsg)) {
      DEBUG9(dispDebugObj, string("processing a snmpDispSendPduASI message!"));
      // Request from application (or other) to send PDU to the net
      snmpMPPrepareOutgoingMsgASI *mpMsg = 
	new snmpMPPrepareOutgoingMsgASI(msg->get_intData(true),
					new snmpSynchDataObj(newMsg),
					this->fifoPtr);
      if (0 == mpMsg->get_sendPduHandle()) {
	mpMsg->set_sendPduHandle(this->pduHandleCounter++);
	DEBUG9(dispDebugObj, string("main: PrepOutMsg is new, creating pduhandle '") \
	       + OPENSNMP_UTILITIES::intToString(mpMsg->get_sendPduHandle()) \
	       + "'" );
      }
      else {
	DEBUG9(dispDebugObj, string("main: PrepOutMsg already has a pduhandle of '") \
	       + OPENSNMP_UTILITIES::intToString(mpMsg->get_sendPduHandle()) \
	       + "'" );
      }

      DEBUG9(dispDebugObj, string("pushing PrepareOutgoingMsg to MP3")); 
      // need to save pointers calling ASI and and state data before pushing
      this->send_MP_msg(mpMsg);

    } else if (snmpDispReturnResponsePduASI *msg 
	       = dynamic_cast<snmpDispReturnResponsePduASI *>(newMsg)) {
      DEBUG9(dispDebugObj, 
	     string("processing a snmpDispReturnResponsePduASI message!"));
      // Request from CR (or other) to send Response PDU to the net
      snmpMPPrepareResponseMsgASI *mpMsg = 
	new snmpMPPrepareResponseMsgASI(msg->get_intData(true), 
					new snmpSynchDataObj(newMsg),
					this->fifoPtr);
      DEBUG9(dispDebugObj, string("pushing PrepareResponseMsg to MP3:") \
	     + OPENSNMP_UTILITIES::intToString(int(mpMsg)) );
      this->send_MP_msg(mpMsg);

    } else if (snmpDispHandleInMsg *msg 
	       = dynamic_cast<snmpDispHandleInMsg *>(newMsg)) {
      DEBUG9(dispDebugObj, 
	     string("processing an internal HandleInMsg message:") + \
	     OPENSNMP_UTILITIES::intToString(int(newMsg)) );
      // internal Dispatcher message from helper thread - we received a message
      // off the net
      this->dispStats.incr_snmpInPkts();
      BufferClass *data = msg->get_msgData(true);
      Integer32 *length = msg->get_msgLength(true);
      int version = this->parse_version((char *)(*data), (int)(*length));
      switch (version) {
      case SNMP_CONSTANTS::SNMP_VERSION_1:
      case SNMP_CONSTANTS::SNMP_VERSION_2c:
        enginePtr->increment_engineCounter(snmpEngine::snmpInBadVersions);
	DEBUG9(dispDebugObj, string("unimplemented message version received"));
	delete msg;
	break;

      case SNMP_CONSTANTS::SNMP_VERSION_3:
	{
	  snmpMPPrepareDataElementsASI *mpMsg = 
	    new snmpMPPrepareDataElementsASI((snmpMessageObj *)NULL, 
					     0, 
					     new snmpSynchDataObj(msg), 
					     this->fifoPtr);
	  MSG_PASS_ARG(mpMsg, msg, transportDomain);
	  MSG_PASS_ARG(mpMsg, msg, transportAddress);
	  mpMsg->set_outMsg(data);
	  mpMsg->set_outMsgLength(length);
	  DEBUG9(dispDebugObj, 
		 string("pushing PrepareDataElements to MP3:") + \
		 OPENSNMP_UTILITIES::intToString(int(mpMsg)) );
	  // need to save pointers calling ASI and state data before pushing
	  this->send_MP_msg(mpMsg);
	}
	break;
      default:
	DEBUG9(dispDebugObj, string("unsupported message version received"));
        enginePtr->increment_engineCounter(snmpEngine::snmpInBadVersions);
	delete msg;
      }

    } else if (snmpMPPrepareOutgoingMsgASI *msg 
	       = dynamic_cast<snmpMPPrepareOutgoingMsgASI *>(newMsg)) {
      // just got msg back from the MP3, processing a request to send PDU.
      // ready to send to the net and return sendPduHandle to calling app
      // along with reply snmpDispSendPduASI 
      result = this->check_internal_message(newMsg, &synchData, 
					    &synchMsg, &retFifo);

      if (result == SNMP_SUCCESS) {
	snmpStatusInfo *status;
	if (NULL == (status = msg->get_statusInformation(false))) {
	  status = new snmpStatusInfo(true);
	  msg->set_statusInformation(status);
	}

	// if message was succesfully processed, send it.
	if (status->get_success()) {
	  TransportDomain *transportDomain = msg->get_transportDomain(false);
	  TransportAddress *transportAddress = 
	    msg->get_transportAddress(false);

	  char *outMsg = (char*)(*(msg->get_outMsg(false)));
	  Integer32 *outMsgLength = msg->get_outMsgLength(false);
	  
	  DEBUG9(dispDebugObj, 
		 string("recvd PrepareOutgoingMsg result from MP3:result = ") \
		 + OPENSNMP_UTILITIES::intToString(result) + ":" + \
		 OPENSNMP_UTILITIES::intToString(int(msg)) );
	  result = this->netObj->send_net_msg(transportDomain,
					      transportAddress, 
					      (u_char*)outMsg, 
					      *outMsgLength);
	}

	snmpDispSendPduASI* sendPduMsg = NULL;
	if ( sendPduMsg = dynamic_cast<snmpDispSendPduASI*>(synchMsg) ) {
	  // send all created data back to CG, particularly pdu handle!
	  // intData includes statusInformation object
	  sendPduMsg->set_intData(msg->get_intData(true));
	  retFifo->push(sendPduMsg);
	}
	else {
	  DEBUG0(dispDebugObj, 
		 string("ERROR can not cast snmpDispSendPDUASI") + \
		 "message from MP or can not cast snmpPduHandle");
	}	  
	delete synchData;
      }
      else {
	DEBUG0(dispDebugObj, 
	       string("ERROR from internal_check_message for ") + \
	       "PrepareOutgoingMsg, unable to process");
      }
      delete msg;

    } else if (snmpMPPrepareResponseMsgASI *msg 
	       = dynamic_cast<snmpMPPrepareResponseMsgASI *>(newMsg)) {
      // just got msg back from the MP3, processing a request to send response.
      // ready to send to the net and return status reply returnResponsePdu 
      result = this->check_internal_message(newMsg, &synchData, 
					    &synchMsg, &retFifo);
      DEBUG9(dispDebugObj, string("recvd PrepareResponseMsg result from ") + \
	     "MP3:result = " + OPENSNMP_UTILITIES::intToString(result) + \
	     ":" + OPENSNMP_UTILITIES::intToString(int(msg)) );
      if (result == SNMP_SUCCESS) {
	snmpStatusInfo *statInfo = msg->get_statusInformation(false);
	if (statInfo && statInfo->get_success()) {

	  char *outMsg = (char*)(*(msg->get_outMsg(false)));

	  result = this->netObj->send_net_msg(msg->get_transportDomain(false),
					      msg->get_transportAddress(false),
					      (u_char*)outMsg, 
					     *(msg->get_outMsgLength(false)));
	}
	else  result = 0; // no status info or status was failure (false)
	if (snmpDispReturnResponsePduASI* returnResponsePduMsg = 
	    dynamic_cast<snmpDispReturnResponsePduASI*>(synchMsg)) {
	  returnResponsePduMsg->
	    set_statusInformation(new snmpStatusInfo(result));
	  retFifo->push(returnResponsePduMsg);
	}
	delete synchData;
      }
      else {
	DEBUG0(dispDebugObj, 
	       string("ERROR from internal_check_message for PrepareResponseMsg"));
      }
      delete msg;

    } else if (snmpMPPrepareDataElementsASI *msg 
	       = dynamic_cast<snmpMPPrepareDataElementsASI *>(newMsg)) {
      // just got this back from the MP3 after recv'ing off the net
      snmpStatusInfo *statInfo = msg->get_statusInformation(false);

      if (statInfo) {
	int result = statInfo->get_success();      
	DEBUG9(dispDebugObj, 
	       string("recvd PrepareDataElements back from MP3: result = " \
		      + OPENSNMP_UTILITIES::intToString(result) ));
	if (result) {
	  this->dispatch_incoming_msg(msg);
	} else {
	  DEBUG9(dispDebugObj, string("error: returned error") );
	}
      } else {
	DEBUG9(dispDebugObj, 
	       string("warning: PrepareDataElements has no statusInfo"));
      }
      DEBUG9(dispDebugObj, string("finished processing an inbound message"));
      delete msg;

    } else if (snmpDispUnregisterEngIdASI *msg 
	       = dynamic_cast<snmpDispUnregisterEngIdASI *>(newMsg)) {
      // leave the values in the messsage, but be sure were done with them
      // i.e. don't return the msg until after this->unregister_engid()
      OctetString *engId = msg->get_contextEngineID(false);
      set<PDU_Type> *pduTypeSet = msg->get_pduTypeSet(false);;
      snmpFIFOObj *retFifo = msg->get_returnFIFO();

      DEBUG9(dispDebugObj, string("processing a UnregisterEngId message"));
      int result = this->unregister_engid(engId,pduTypeSet);

      msg->set_statusInformation(new snmpStatusInfo((bool)result));
      retFifo->push(newMsg);
    } else if (snmpExitMessage *msg 
	       = dynamic_cast<snmpExitMessage *>(newMsg)) {
      // do any neccessary clean up here
      DEBUG9(dispDebugObj, 
	     string("processing a snmpExitMessage, exiting dispatcher thread"));
      // try to exit the Network listening thread:
      if (NULL != this->netObj) {
	netObj->netExitMutex.lock("Disp: exiting NET");
	netObj->exit_net_object = true;
	int retVal = netObj->netExitCondition.wait
	  (&(netObj->netExitMutex), 1, "Disp: waiting for NET");

	if (retVal == snmpConditionObj::signalled) {
	  DEBUG9(dispDebugObj, 
		 string("caught exit signal for NET, Dispatcher Exiting"));
	}
	else if (retVal == snmpConditionObj::sigtimeout) {
	  DEBUG9(dispDebugObj, 
		 string("timeout waiting for NET's exit, exiting anyway"));
	}
	else {
	  DEBUG0(dispDebugObj, 
		 string("Error waiting for NET's exit, exiting anyway"));
	}
	netObj->netExitMutex.unlock("exitAllArches: unlock");
      }
      delete msg;
      return;
    } else {
      DEBUG9(dispDebugObj, string("ERROR: unknown message type:") + \
	     OPENSNMP_UTILITIES::intToString(int(newMsg)) + ", " + \
	     typeid(*newMsg).name() );
      delete newMsg;
    }
    count++;
  } // while pop messages
  DEBUG9(dispDebugObj, string("exitting") );
} // main_loop


void 
snmpDispArchObj::handleMsg(snmpMessageObj *message) {
//   extern snmpFIFOObj  *gFIFOObj;
//   gFIFOObj->sendMPMsg(message);
}

