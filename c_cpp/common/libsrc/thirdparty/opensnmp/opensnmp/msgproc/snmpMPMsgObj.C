//
// generic snmpMessageProcessingObj
//

#include "config.h"
#include "snmpMPMsgObj.H"
#include <stdio.h>
#include <iostream>
#include <string>


snmpMessageProcessingMsgObj::snmpMessageProcessingMsgObj
( snmpMessageObj   *copyFromMsg, //= NULL
  int               newID,       //= 0
  snmpSynchDataObj *newData,     //= NULL
  snmpFIFOObj      *theFIFO)     //= NULL
  : snmpMessageObj(copyFromMsg, newID, newData, theFIFO)  
{
  this->transportDomain        = 0;
  this->transportAddress       = 0;
  this->messageProcessingModel = 0;
  this->securityModel          = 0;
  this->securityName           = 0;
  this->securityLevel          = 0;
  this->securityEngineID       = 0;
  // ---------------------------------
  this->contextEngineID        = 0;
  this->contextName            = 0;
  this->pduVersion             = 0;
  this->pduType                = 0;
  this->msgPDU                 = 0;
  this->scopedPDU              = 0;
  this->expectedResponse       = 0;
  // ---------------------------------
  this->maxSizeRespPDU         = 0;
  this->outMsg                 = 0;
  this->outMsgLength           = 0;
  this->securityParameters     = 0;
  // ---------------------------------
  this->stateReference         = 0;
  this->securityStateReference = 0;
  this->maxMsgSize             = 0;
  this->statusInformation      = 0;
  this->globalData             = 0;
}


snmpMessageProcessingMsgObj::snmpMessageProcessingMsgObj
( TransportDomain   *newTransportDomain,
  OctetString       *newTransportAddress,
  Integer32         *newMessageProcessingModel,
  Integer32         *newSecurityModel,
  OctetString       *newSecurityName,
  Integer32         *newSecurityLevel,
  OctetString       *newSecurityEngineID,
  // -----------------------------------------
  OctetString       *newContextEngineID,
  OctetString       *newContextName,
  Integer32         *newPDUVersion,
  PDU_Type          *newPDUType, 
  PDU               *newMsgPDU,
  ScopedPDU         *newScopedPDU,
  bool              *newExpectedResponse,
  // -----------------------------------------
  Integer32         *newMaxSizeRespPDU,
  char              *newOutMsg,
  Integer32         *newOutMsgLength,
  OctetString       *newSecurityParameters,
  // -----------------------------------------
  snmpObj           *newStateReference,
  snmpObj           *newSecurityStateReference,
  Integer32         *newMaxMsgSize,
  snmpStatusInfo    *newStatusInformation,
  snmpGlobalData    *newGlobalData,
  snmpSynchDataObj  *newData,     //= NULL
  snmpFIFOObj       *theFIFO)     //= NULL
  : snmpMessageObj((snmpMessageObj *)(NULL), int(0), newData, theFIFO)  
{
  this->transportDomain        = newTransportDomain;
  this->transportAddress       = newTransportAddress;
  this->messageProcessingModel = newMessageProcessingModel;
  this->securityModel          = newSecurityModel;
  this->securityName           = newSecurityName;
  this->securityLevel          = newSecurityLevel;
  this->securityEngineID       = newSecurityEngineID;
  // ---------------------------------
  this->contextEngineID        = newContextEngineID;
  this->contextName            = newContextName;
  this->pduVersion             = newPDUVersion;
  this->pduType                = newPDUType;
  this->msgPDU                 = newMsgPDU;
  this->scopedPDU              = newScopedPDU;
  this->expectedResponse       = newExpectedResponse;
  // ---------------------------------
  this->maxSizeRespPDU         = newMaxSizeRespPDU;
  this->outMsg                 = newOutMsg;
  this->outMsgLength           = newOutMsgLength;
  this->securityParameters     = newSecurityParameters;
  // ---------------------------------
  this->stateReference         = newStateReference;
  this->securityStateReference = newSecurityStateReference;
  this->maxMsgSize             = newMaxMsgSize;
  this->statusInformation      = newStatusInformation;
  this->globalData             = newGlobalData;
}


snmpMessageProcessingMsgObj::~snmpMessageProcessingMsgObj()  {
  if (this->transportDomain        != 0) delete this->transportDomain;
  if (this->transportAddress       != 0) delete this->transportAddress;
  if (this->messageProcessingModel != 0) delete this->messageProcessingModel;
  if (this->securityModel          != 0) delete this->securityModel;
  if (this->securityName           != 0) delete this->securityName;
  if (this->securityLevel          != 0) delete this->securityLevel;
  if (this->securityEngineID       != 0) delete this->securityEngineID;
  // ---------------------------------
  if (this->contextEngineID        != 0) delete this->contextEngineID;
  if (this->contextName            != 0) delete this->contextName;
  if (this->pduVersion             != 0) delete this->pduVersion;
  if (this->pduType                != 0) delete this->pduType;
  if (this->msgPDU                 != 0) delete this->msgPDU;
  if (this->scopedPDU              != 0) delete this->scopedPDU;
  if (this->expectedResponse       != 0) delete this->expectedResponse;
  // ---------------------------------
  if (this->maxSizeRespPDU         != 0) delete this->maxSizeRespPDU;
  if (this->outMsg                 != 0) delete this->outMsg;
  if (this->outMsgLength           != 0) delete this->outMsgLength;
  if (this->securityParameters     != 0) delete this->securityParameters;
  // ---------------------------------
  if (this->stateReference         != 0) delete this->stateReference;
  if (this->securityStateReference != 0) delete this->securityStateReference;
  if (this->maxMsgSize             != 0) delete this->maxMsgSize;
  if (this->statusInformation      != 0) delete this->statusInformation;
  if (this->globalData             != 0) delete this->globalData;
}


//  clone
snmpMessageProcessingMsgObj *
snmpMessageProcessingMsgObj::clone() {
  snmpMessageProcessingMsgObj *retObj = new snmpMessageProcessingMsgObj(*this);

  if (this->transportDomain        != 0) 
    retObj->transportDomain = new TransportDomain(*(this->transportDomain));
  if (this->transportAddress       != 0) 
    retObj->transportAddress = this->transportAddress->clone();
  if (this->messageProcessingModel != 0) 
    retObj->messageProcessingModel = this->messageProcessingModel->clone();
  if (this->securityModel          != 0) 
    retObj->securityModel = this->securityModel->clone();
  if (this->securityName           != 0) 
    retObj->securityName = this->securityName->clone();
  if (this->securityLevel          != 0) 
    retObj->securityLevel = this->securityLevel->clone();
  if (this->securityEngineID       != 0) 
    retObj->securityEngineID = this->securityEngineID->clone();
  // ---------------------------------
  if (this->contextEngineID        != 0) 
    retObj->contextEngineID = this->contextEngineID->clone();
  if (this->contextName            != 0) 
    retObj->contextName = this->contextName->clone();
  if (this->pduVersion             != 0) 
    retObj->pduVersion = this->pduVersion->clone();
  if (this->pduType                != 0) 
    retObj->pduType = new PDU_Type(*(this->pduType));
  if (this->msgPDU                 != 0) 
    retObj->msgPDU = this->msgPDU->clone();
  if (this->scopedPDU              != 0) 
    retObj->scopedPDU = this->scopedPDU->clone();
  if (this->expectedResponse       != 0) 
    retObj->expectedResponse = new bool(*(this->expectedResponse));
  // ---------------------------------
  if (this->maxSizeRespPDU         != 0) 
    retObj->maxSizeRespPDU = this->maxSizeRespPDU->clone();
  if (this->outMsg                 != 0) {
    retObj->outMsg = new char[int(this->outMsgLength)];
    retObj->outMsg = this->outMsg;
  }
  if (this->outMsgLength           != 0) 
    retObj->outMsgLength = this->outMsgLength->clone();
  if (this->securityParameters     != 0) 
    retObj->securityParameters = this->securityParameters->clone();
  // ---------------------------------
  if (this->stateReference         != 0) 
    retObj->stateReference = this->stateReference->clone();
  if (this->securityStateReference      != 0) 
    retObj->securityStateReference = this->securityStateReference->clone();
  if (this->maxMsgSize             != 0) 
    retObj->maxMsgSize = this->maxMsgSize->clone();
  if (this->statusInformation      != 0) 
    retObj->statusInformation = this->statusInformation->clone();
  if (this->globalData             != 0) 
    retObj->globalData = this->globalData->clone();
  
  return(retObj);
}


// Transport Domain

TransportDomain *
snmpMessageProcessingMsgObj::get_transportDomain(bool extract)  {
  TransportDomain *retVal = this->transportDomain;
  if (extract)  this->transportDomain = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_transportDomain(TransportDomain *newString) { 
  if (this->transportDomain != 0) delete this->transportDomain;
  this->transportDomain = newString;
  return(true);
}


// Transport Address

OctetString  *
snmpMessageProcessingMsgObj::get_transportAddress(bool extract)  { 
  OctetString *retVal = this->transportAddress;
  if (extract)  this->transportAddress = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_transportAddress(OctetString *newString)  {
  if (this->transportAddress != 0) delete this->transportAddress;
  this->transportAddress = newString;
  return(true);
}


// Message Processing Model 

Integer32  *
snmpMessageProcessingMsgObj::get_messageProcessingModel(bool extract)  {
  Integer32 *retVal = this->messageProcessingModel;
  if (extract)  this->messageProcessingModel = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_messageProcessingModel(Integer32 *newNum)  {
  if (this->messageProcessingModel != 0) delete this->messageProcessingModel;
  this->messageProcessingModel = newNum;
  return(true);
}


// Security Model

Integer32  *
snmpMessageProcessingMsgObj::get_securityModel(bool extract)  {
  Integer32 *retVal = this->securityModel;
  if (extract)  this->securityModel = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_securityModel(Integer32 *newNum)  {
  if (this->securityModel != 0) delete this->securityModel;
  this->securityModel = newNum;
  return(true);
}


// Security Name

OctetString  *
snmpMessageProcessingMsgObj::get_securityName(bool extract)  {
  OctetString *retVal = this->securityName;
  if (extract)  this->securityName = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_securityName(OctetString *newString)  {
  if (this->securityName != 0) delete this->securityName;
  this->securityName = newString;
  return(true);
}


// Security Level

Integer32  *
snmpMessageProcessingMsgObj::get_securityLevel(bool extract)  {
  Integer32 *retVal = this->securityLevel;
  if (extract)  this->securityLevel = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_securityLevel(Integer32 *newNum)  {
  if (this->securityLevel != 0) delete this->securityLevel;
  this->securityLevel = newNum;
  return(true);
}


// Security Engine ID

OctetString  *
snmpMessageProcessingMsgObj::get_securityEngineID(bool extract)  {
  OctetString *retVal = this->securityEngineID;
  if (extract)  this->securityEngineID = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_securityEngineID(OctetString *newString)  {
  if (this->securityEngineID != 0) delete this->securityEngineID;
  this->securityEngineID = newString;
  return(true);
}

// -----------------------------------------------------------------


// Context Engine ID

OctetString  *
snmpMessageProcessingMsgObj::get_contextEngineID(bool extract)  {
  OctetString *retVal = this->contextEngineID;
  if (extract)  this->contextEngineID = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_contextEngineID(OctetString *newString)  {
  if (this->contextEngineID != 0) delete this->contextEngineID;
  this->contextEngineID = newString;
  return(true);
}


// Context Name

OctetString  *
snmpMessageProcessingMsgObj::get_contextName(bool extract)  {
  OctetString *retVal = this->contextName;
  if (extract)  this->contextName = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_contextName(OctetString *newContextName)  {
  if (this->contextName != 0) delete this->contextName;
  this->contextName = newContextName;
  return(true);
}


// PDU version

Integer32  *
snmpMessageProcessingMsgObj::get_PDUVersion(bool extract)  {
  Integer32 *retVal = this->pduVersion;
  if (extract)  this->pduVersion = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_PDUVersion(Integer32 *newPDUVersion)  {
  if (this->pduVersion != 0) delete this->pduVersion;
  this->pduVersion = newPDUVersion;
  return(true);
}


// PDU Type

PDU_Type  *
snmpMessageProcessingMsgObj::get_PDUType(bool extract)  {
  PDU_Type *retVal = this->pduType;
  if (extract)  this->contextName = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_PDUType(PDU_Type *newPDUType)  {
  if (this->pduType != 0) delete this->pduType;
  this->pduType = newPDUType;
  return(true);
}


// PDU 

PDU  *
snmpMessageProcessingMsgObj::get_PDU(bool extract)  {
  PDU *retVal = this->msgPDU;
  if (extract)  this->msgPDU = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_PDU(PDU *newPDU)  {
  if (this->msgPDU != 0) delete this->msgPDU;
  this->msgPDU = newPDU;
  return(true);
}


// Scoped PDU 

ScopedPDU  *
snmpMessageProcessingMsgObj::get_scopedPDU(bool extract)  {
  ScopedPDU *retVal = this->scopedPDU;
  if (extract)  this->scopedPDU= 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_scopedPDU(ScopedPDU *newScopedPDU)  {
  if (this->scopedPDU != 0) delete this->scopedPDU;
  this->scopedPDU = newScopedPDU;
  return(true);
}


// Expected Response

bool  *
snmpMessageProcessingMsgObj::get_expectedResponse(bool extract)  {
  bool *retVal = this->expectedResponse;
  if (extract)  this->expectedResponse = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_expectedResponse(bool *expResponse)  {
  if (this->expectedResponse != 0) delete this->expectedResponse;
  this->expectedResponse = expResponse;
  return(true);
}


// -----------------------------------------------------------------


// max size response PDU


Integer32  *
snmpMessageProcessingMsgObj::get_maxSizeResponseScopedPDU(bool extract)  {
  Integer32 *retVal = this->maxSizeRespPDU;
  if (extract)  this->maxSizeRespPDU = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_maxSizeResponseScopedPDU(Integer32 *newMaxSizeRespPDU) {
  if (this->maxSizeRespPDU != 0) delete this->maxSizeRespPDU;
  this->maxSizeRespPDU = newMaxSizeRespPDU;
  return(true);
}


// Whole Out Message

char *
snmpMessageProcessingMsgObj::get_wholeOutMsg(bool extract)  {
  char *retVal = this->outMsg;
  if (extract)  this->outMsg = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_wholeOutMsg(char *newWholeOutMsg)  {
  if (this->outMsg != 0) delete this->outMsg;
  this->outMsg = newWholeOutMsg;
  return(true);
}


// Whole Out Message Length

Integer32  *
snmpMessageProcessingMsgObj::get_wholeOutMsgLength(bool extract)  {
  Integer32 *retVal = this->outMsgLength;
  if (extract)  this->outMsgLength = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_wholeOutMsgLength(Integer32 *newOutMsgLength)  {
  if (this->outMsgLength != 0) delete this->outMsgLength;
  this->outMsgLength = newOutMsgLength;
  return(true);
}


// Security Parameters

OctetString  
*snmpMessageProcessingMsgObj::get_securityParameters(bool extract)  {
  OctetString *retVal = this->securityParameters;
  if (extract)  this->securityParameters = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_securityParameters
(OctetString *newSecParams)  
{
  if (this->securityParameters != 0) delete this->securityParameters;
  this->securityParameters = newSecParams;
  return(true);
}


// -----------------------------------------------------------------


//  State Reference

snmpObj *
snmpMessageProcessingMsgObj::get_stateReference(bool extract) {
  snmpObj *retVal = this->stateReference;
  if (extract)  this->stateReference = 0;
  return(retVal);
}

bool
snmpMessageProcessingMsgObj::set_stateReference(snmpObj  *newStateReference) {
  if (this->stateReference != 0) delete this->stateReference;
  this->stateReference = newStateReference;
  return(true);
}

//  Security State Reference

snmpObj *
snmpMessageProcessingMsgObj::get_securityStateReference(bool extract) {
  snmpObj *retVal = this->securityStateReference;
  if (extract)  this->securityStateReference = 0;
  return(retVal);
}

bool 
snmpMessageProcessingMsgObj::set_securityStateReference
(snmpObj  *newSecStateRef) 
{
  if (this->securityStateReference != 0) delete this->securityStateReference;
  this->securityStateReference = newSecStateRef;
  return(true);
}


// Max Message Size

Integer32  *
snmpMessageProcessingMsgObj::get_maxMsgSize(bool extract)  {
  Integer32 *retVal = this->maxMsgSize;
  if (extract)  this->maxMsgSize = 0;
  return(retVal);
}

bool  
snmpMessageProcessingMsgObj::set_maxMsgSize(Integer32 *newMaxMsgSize)  {
  if (this->maxMsgSize != 0) delete this->maxMsgSize;
  this->maxMsgSize = newMaxMsgSize;
  return(true);
}


// status Information

snmpStatusInfo *
snmpMessageProcessingMsgObj::get_statusInformation(bool extract)  {
  snmpStatusInfo *retStatus = this->statusInformation;
  
  if (extract) { this->statusInformation = NULL; }
  return(retStatus);
}

bool  snmpMessageProcessingMsgObj::set_statusInformation
  (snmpStatusInfo *newStatus) 
{
  if (this->statusInformation != NULL) { delete this->statusInformation; }
  this->statusInformation = newStatus;
  return(true);
}


//  global data

snmpGlobalData *
snmpMessageProcessingMsgObj::get_globalData(bool extract) {
  snmpGlobalData *retData = this->globalData;
  if (extract) { this->globalData = NULL; }
  return(retData);
}

bool 
snmpMessageProcessingMsgObj::set_globalData(snmpGlobalData  *newGlobalData) {
  if (this->globalData != NULL) { delete this->globalData; }
  this->globalData = newGlobalData;
  return(true);
}



//
//    --------       snmpMPMsgObj       --------------
//


snmpMPMsgObj::snmpMPMsgObj()  {
}

snmpMPMsgObj::~snmpMPMsgObj()  {
}

//  Copy
void *
snmpMPMsgObj::copy() {
  return(new snmpMPMsgObj);
}



//
//    --------       snmpMPResponseMsgObj       --------------
//


snmpMPResponseMsgObj::snmpMPResponseMsgObj()  {
}

snmpMPResponseMsgObj::~snmpMPResponseMsgObj()  {
}

//  Copy
void *
snmpMPResponseMsgObj::copy() {
  return(new snmpMPResponseMsgObj);
}

