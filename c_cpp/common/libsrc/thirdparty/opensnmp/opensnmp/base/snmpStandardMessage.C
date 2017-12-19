//
//  snmpStandardMessage
//  This object represents the stardard message for SNMP v3 messages
//  passed through the engine. It is generally used by the SNMP engine
//  for all of the engine messags involving PDU's passed to/from other
//  SNMP engines on the network. It uses an enum to differentiate
//  between types of messagse being passed (i.e. which parts of the
//  engine that the messages are being passed between, for example,
//  dispatcher <-> Message processor, etc...).
//

#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>
#include "snmpStandardMessage.H"

using  SNMP_CONSTANTS::TransportDomain;


//
// ******** DEFINE snmpStandardMessage_internalData DEFINE ********
//
snmpStandardMessage_internalData::snmpStandardMessage_internalData() {
  this->transportDomain        = 0;
  this->transportAddress       = 0;
  // this->messageProcessingModel = 0; // v3Message - msgVersion
  // this->securityModel          = 0; // HeaderData
  this->securityName           = 0;
  this->securityLevel          = 0;
  this->securityEngineID       = 0;
  // ---------------------------------
  //  this->contextEngineID        = 0; // scoped PDU
  //  this->contextName            = 0; // scoped PDU
  this->pduVersion             = 0;
  this->pduType                = 0;
  //   this->msgPDU                 = 0; // scoped PDU
  // scopedPDU, securityParameters, globalData coverd by v3Message.
  //  this->scopedPDU              = 0;
  this->expectResponse         = 0;
  // ---------------------------------
  this->maxSizeResponsePDU     = 0;
  this->sendPduHandle          = 0;
  this->outMsg                 = 0;
  this->outMsgLength           = 0;
  // scopedPDU, securityParameters, globalData coverd by v3Message.
  //  this->securityParameters     = 0;
  // ---------------------------------
  this->stateReference         = 0;
  this->securityStateReference = 0;
  // this->maxMsgSize             = 0; // HeaderData
  this->statusInformation      = 0;
  // scopedPDU, securityParameters, globalData coverd by v3Message.
  //  this->globalData             = 0;
} 

snmpStandardMessage_internalData::~snmpStandardMessage_internalData() {
  if (this->transportDomain        != 0) delete this->transportDomain;
  if (this->transportAddress       != 0) delete this->transportAddress;
  //  v3Message
  //  if (messageProcessingModel != 0) delete this->messageProcessingModel;
  //  HeaderData
  //  if (this->securityModel          != 0) delete this->securityModel;
  if (this->securityName           != 0) delete this->securityName;
  if (this->securityLevel          != 0) delete this->securityLevel;
  if (this->securityEngineID       != 0) delete this->securityEngineID;
  // ---------------------------------
  // Scoped PDU
  //   if (this->contextEngineID        != 0) delete this->contextEngineID;
  //   if (this->contextName            != 0) delete this->contextName;
  if (this->pduVersion             != 0) delete this->pduVersion;
  if (this->pduType                != 0) delete this->pduType; 
  // Scoped PDU
  //  if (this->msgPDU                 != 0) delete this->msgPDU;
  // v3Message.
  //  if (this->scopedPDU              != 0) delete this->scopedPDU;
  if (this->expectResponse         != 0) delete this->expectResponse;
  // ---------------------------------
  if (this->maxSizeResponsePDU     != 0) delete this->maxSizeResponsePDU;

  if (this->outMsg                 != 0) delete this->outMsg;
  if (this->outMsgLength           != 0) delete this->outMsgLength;
  // v3Message.
  //  if (this->securityParameters     != 0) delete this->securityParameters;
  // ---------------------------------
  if (this->stateReference         != 0) delete this->stateReference;
  if (this->securityStateReference != 0) delete this->securityStateReference;
  // HeaderData
  // if (this->maxMsgSize             != 0) delete this->maxMsgSize;
  if (this->statusInformation      != 0) delete this->statusInformation;
  // v3Message.
  //  if (this->globalData             != 0) delete this->globalData;
}


snmpStandardMessage_internalData *
snmpStandardMessage_internalData::clone() {
  snmpStandardMessage_internalData *retObj = 
    new snmpStandardMessage_internalData();

  if (this->transportDomain        != 0) 
    retObj->transportDomain = new TransportDomain(*(this->transportDomain));
  if (this->transportAddress       != 0) 
    retObj->transportAddress = this->transportAddress->clone();
  // v3Message
  //   if (this->messageProcessingModel != 0) 
  //     retObj->messageProcessingModel = this->messageProcessingModel->clone();
  // HeaderData
  //   if (this->securityModel          != 0) 
  //     retObj->securityModel = this->securityModel->clone();
  if (this->securityName           != 0) 
    retObj->securityName = this->securityName->clone();
  if (this->securityLevel          != 0) 
    retObj->securityLevel = this->securityLevel->clone();
  if (this->securityEngineID       != 0) 
    retObj->securityEngineID = this->securityEngineID->clone();
  // ---------------------------------
  // Scoped PDU
  //   if (this->contextEngineID        != 0) 
  //     retObj->contextEngineID = this->contextEngineID->clone();
  //   if (this->contextName            != 0) 
  //     retObj->contextName = this->contextName->clone();
  if (this->pduVersion             != 0) 
    retObj->pduVersion = new PDU_Version(*(this->pduVersion));
  if (this->pduType                != 0) 
    retObj->pduType = new PDU_Type(*(this->pduType));
  // Scoped PDU
  //   if (this->msgPDU                 != 0) 
  //     retObj->msgPDU = this->msgPDU->clone();
  // v3Message.
  //  if (this->scopedPDU              != 0) 
  //    retObj->scopedPDU = this->scopedPDU->clone();
  if (this->expectResponse         != 0) 
    retObj->expectResponse = new bool(*(this->expectResponse));
  // ---------------------------------
  if (this->maxSizeResponsePDU     != 0) 
    retObj->maxSizeResponsePDU = this->maxSizeResponsePDU->clone();
  if (this->sendPduHandle          != 0) 
    retObj->sendPduHandle = this->sendPduHandle;
  if (this->outMsg                 != 0)
    retObj->outMsg = this->outMsg->clone();
  if (this->outMsgLength           != 0) 
    retObj->outMsgLength = this->outMsgLength->clone();
  // v3Message.
  //  if (this->securityParameters     != 0) 
  //    retObj->securityParameters = this->securityParameters->clone();
  // ---------------------------------
  if (this->stateReference         != 0) 
    retObj->stateReference = this->stateReference->clone();
  if (this->securityStateReference != 0) 
    retObj->securityStateReference = this->securityStateReference->clone();
  // HeaderData
  // if (this->maxMsgSize             != 0) 
  //   retObj->maxMsgSize = this->maxMsgSize->clone();
  if (this->statusInformation      != 0) 
    retObj->statusInformation = this->statusInformation->clone();
  // v3Message.
  //  if (this->globalData             != 0) 
  //    retObj->globalData = this->globalData->clone();
  retObj->v3Message = snmpV3Message(this->v3Message);
  
  return(retObj);
}


snmpV3Message &
snmpStandardMessage_internalData::get_v3Message_ref() {
  return   this->v3Message;
}


//
// ******** DEFINE snmpStandardMessage DEFINE ********
//

snmpStandardMessage::snmpStandardMessage
(snmpMessageObj   *copyFromMsg, // = NULL
 int               newID,       // = 0
 snmpSynchDataObj *newData,     // = NULL
 snmpFIFOObj      *theFIFO )    // = NULL  )
  : snmpMessageObj(copyFromMsg, newID, newData, theFIFO)
{
  DEBUGCREATE(this->mDebug, "message");
  DEBUG9(this->mDebug, "constructor, 4 params");
  this->intData = NULL;
}

snmpStandardMessage::snmpStandardMessage
( TransportDomain   *newTransportDomain,
  TransportAddress  *newTransportAddress,
  Integer32         *newMessageProcessingModel,
  Integer32         *newSecurityModel,
  OctetString       *newSecurityName,
  Integer32         *newSecurityLevel,
  OctetString       *newSecurityEngineID,
  // -----------------------------------------
  OctetString       *newContextEngineID,
  OctetString       *newContextName,
  PDU_Version       *newPDUVersion,
  PDU_Type          *newPDUType, 
  PDU               *newMsgPDU,
  ScopedPDU         *newScopedPDU,
  bool              *newExpectResponse,
  // -----------------------------------------
  Integer32         *newMaxSizeResponsePDU,
  unsigned long     newSendPduHandle,
  BufferClass       *newOutMsg,
  Integer32         *newOutMsgLength,
  asnDataType       *newSecurityParameters,
  // -----------------------------------------
  snmpObj           *newStateReference,
  snmpObj           *newSecurityStateReference,
  Integer32         *newMaxMsgSize,
  snmpStatusInfo    *newStatusInformation,
  HeaderData        *newGlobalData,
  snmpMessageObj    *copyFromMsg, // = NULL,
  int                newID,       // = 0,
  snmpSynchDataObj  *newData,     // = NULL,
  snmpFIFOObj       *theFIFO)     // = NULL 
  : snmpMessageObj( copyFromMsg, newID, newData, theFIFO)  
{
  DEBUGCREATE(this->mDebug, "message");
  DEBUG9(this->mDebug, "constructor, full params");
  this->intData = new snmpStandardMessage_internalData();

  // these two need to be set first so that they don't over-ride
  // data later (i.e. data inside them can be overridden)
  this->intData->v3Message.set_scopedPDU(newScopedPDU);
  this->intData->v3Message.set_msgGlobalData(newGlobalData);

  this->intData->transportDomain        = newTransportDomain;
  this->intData->transportAddress       = newTransportAddress;
  this->set_messageProcessingModel(newMessageProcessingModel);
  this->set_securityModel(newSecurityModel);
  this->intData->securityName           = newSecurityName;
  this->intData->securityLevel          = newSecurityLevel;
  this->intData->securityEngineID       = newSecurityEngineID;
  // ---------------------------------
  this->set_contextEngineID(newContextEngineID);
  this->set_contextName(newContextName);
  this->intData->pduVersion             = newPDUVersion;
  this->intData->pduType                = newPDUType; 
  this->set_pdu(newMsgPDU);
  //  this->intData->v3Message.set_scopedPDU(newScopedPDU);
  this->intData->expectResponse         = newExpectResponse;
  // ---------------------------------
  this->intData->maxSizeResponsePDU     = newMaxSizeResponsePDU;
  this->intData->sendPduHandle          = newSendPduHandle;
  this->intData->outMsg                 = newOutMsg;
  this->intData->outMsgLength           = newOutMsgLength;
  this->intData->v3Message.set_msgSecurityParameters(newSecurityParameters);
  // ---------------------------------
  this->intData->stateReference         = newStateReference;
  this->intData->securityStateReference = newSecurityStateReference;
  this->set_maxMsgSize(newMaxMsgSize);
  this->intData->statusInformation      = newStatusInformation;
  //  this->intData->v3Message.set_msgGlobalData(newGlobalData);
}


snmpStandardMessage::snmpStandardMessage
  (snmpStandardMessage_internalData *intData,
   snmpSynchDataObj                 *newData, // = NULL,
   snmpFIFOObj                      *theFIFO) // = NULL
    : snmpMessageObj( (snmpMessageObj *)NULL, int(0), newData, theFIFO) 
{
  DEBUGCREATE(this->mDebug, "message");
  DEBUG9(this->mDebug, "constructor, with intdata");
  this->intData = intData;
}


snmpStandardMessage::snmpStandardMessage(snmpStandardMessage &oldMsg)
{
  DEBUGCREATE(this->mDebug, "message");
  DEBUG9(this->mDebug, "constructor, from snmpStandardMessage reference");
  this->intData = oldMsg.intData->clone();
}


// ************** DECONSTRUCTOR ****************

snmpStandardMessage::~snmpStandardMessage()  {
  DEBUG9(this->mDebug, "Deconstructing an snmpStandardMessage," <<
	 " an SNMP dream if you will...");
  if (this->intData != 0) delete this->intData;

  DEBUGDESTROY(this->mDebug);
}


//  clone
snmpStandardMessage *
snmpStandardMessage::clone() {
  DEBUG9(this->mDebug, "clone");
  snmpStandardMessage *retObj = new snmpStandardMessage();
  
  if (this->intData) retObj->intData = this->intData->clone();
  
  return(retObj);
}



// special access to v3Message
snmpV3Message &
snmpStandardMessage::get_v3Message_ref() {
  if (!this->intData) this->intData = new snmpStandardMessage_internalData();
  return   this->intData->v3Message;
}

//    snmpStandardMessage((TransportDomain *)NULL,   // TransportDomain
// 		       (TransportAddress *)NULL,  // TransportAddress
// 		       (Integer32 *)NULL,         // MessageProcessingModel
// 		       (Integer32 *)NULL,         // SecurityModel
// 		       (OctetString *)NULL,       // SecurityName
// 		       (Integer32 *)NULL,         // SecurityLevel
// 		       (OctetString *)NULL,       // SecurityEngineID
// 		       // -----------------------------------------
// 		       (OctetString *)NULL,       // ContextEngineID
// 		       (OctetString *)NULL,       // ContextName
// 		       (PDU_Version *)NULL,       // PDUVersion
// 		       (PDU_Type *)NULL,          // PDUType 
// 		       (PDU *)NULL,               // MsgPDU
// 		       (ScopedPDU *)NULL,         // ScopedPDU
// 		       (bool *)NULL,              // ExpectResponse
// 		       // -----------------------------------------
// 		       (Integer32 *)NULL,         // MaxSizeResponsePDU
// 		       (snmpObj *)NULL,           // SendPduHandle
// 		       (BufferClass *)NULL,       // OutMsg
// 		       (Integer32 *)NULL,         // OutMsgLength
// 		       (asnDataType *)NULL,       // SecurityParameters
// 		       // -----------------------------------------
// 		       (snmpObj *)NULL,           // StateReference
// 		       (snmpObj *)NULL,           // SecurityStateReference
// 		       (Integer32 *)NULL,         // MaxMsgSize
// 		       (snmpStatusInfo *)NULL,    // StatusInformation
// 		       (HeaderData *)NULL,        // GlobalData
// 		       (snmpMessageObj *)NULL,    // copy From Msg
// 		       int(0),                    // Msg ID
// 		       (snmpSynchDataObj *)NULL,  // Synch Data
// 		       (snmpFIFOObj *)NULL )      // return FIFO
