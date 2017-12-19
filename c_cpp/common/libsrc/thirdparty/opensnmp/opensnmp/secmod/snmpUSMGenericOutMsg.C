//
//  snmpUSMGenerateResponseMsgASI
//  This object represents the Prepare Response Message call
//  in the SNMPV3 architecture description (ietf RFC draft). It is 
//  generally used by the dispatcher to prepare a response message 
//  via the message proccesor.    
//

#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>
#include "snmpUSMGenericOutMsg.H"


snmpUSMGenericOutMsg::snmpUSMGenericOutMsg
( snmpMessageObj   *copyFromMsg, //= NULL
  int               newID,       //= 0
  snmpSynchDataObj *newData,     //= NULL
  snmpFIFOObj      *theFIFO)     //= NULL
  : snmpMessageObj(copyFromMsg, newID, newData, theFIFO)
{


  this->messageProcessingModel = 0;
  this->securityModel          = 0;
  this->securityName           = 0;
  this->securityLevel          = 0;
  this->securityEngineID       = 0;
  // ---------------------------------





  this->scopedPDU              = 0;

  // ---------------------------------

  this->outMsg                 = 0;
  this->outMsgLength           = 0;
  this->securityParameters     = 0;
  // ---------------------------------

  this->securityStateReference = 0;
  this->maxMsgSize             = 0;
  this->statusInformation      = 0;
  this->globalData             = 0;

  cout << "snmpUSMGenericOutMsg constructor\n";
}

snmpUSMGenericOutMsg::snmpUSMGenericOutMsg
(

  Integer32         *newMessageProcessingModel,
  Integer32         *newSecurityModel,
  OctetString       *newSecurityName,
  Integer32         *newSecurityLevel,
  OctetString       *newSecurityEngineID,
  // -----------------------------------------





  ScopedPDU         *newScopedPDU,

  // -----------------------------------------

  BufferClass       *newOutMsg,
  Integer32         *newOutMsgLength,
  asnDataType       *newSecurityParameters,
  // -----------------------------------------

  snmpObj           *newSecurityStateReference,
  Integer32         *newMaxMsgSize,
  snmpStatusInfo    *newStatusInformation,
  HeaderData    *newGlobalData,
  snmpSynchDataObj  *newData,     //= NULL
  snmpFIFOObj       *theFIFO)     //= NULL
  : snmpMessageObj((snmpMessageObj *)(NULL), int(0), newData, theFIFO)  
{

  this->messageProcessingModel = newMessageProcessingModel;
  this->securityModel          = newSecurityModel;
  this->securityName           = newSecurityName;
  this->securityLevel          = newSecurityLevel;
  this->securityEngineID       = newSecurityEngineID;
  // ---------------------------------





  this->scopedPDU              = newScopedPDU;

  // ---------------------------------

  this->outMsg                 = newOutMsg;
  this->outMsgLength           = newOutMsgLength;
  this->securityParameters     = newSecurityParameters;
  // ---------------------------------

  this->securityStateReference = newSecurityStateReference;
  this->maxMsgSize             = newMaxMsgSize;
  this->statusInformation      = newStatusInformation;
  this->globalData             = newGlobalData;
}



snmpUSMGenericOutMsg::~snmpUSMGenericOutMsg()  {


  if (this->messageProcessingModel != 0) delete this->messageProcessingModel;
  if (this->securityModel          != 0) delete this->securityModel;
  if (this->securityName           != 0) delete this->securityName;
  if (this->securityLevel          != 0) delete this->securityLevel;
  if (this->securityEngineID       != 0) delete this->securityEngineID;
  // ---------------------------------





  if (this->scopedPDU              != 0) delete this->scopedPDU;

  // ---------------------------------

  if (this->outMsg                 != 0) delete this->outMsg;
  if (this->outMsgLength           != 0) delete this->outMsgLength;
  if (this->securityParameters     != 0) delete this->securityParameters;
  // ---------------------------------

  if (this->securityStateReference != 0) delete this->securityStateReference;
  if (this->maxMsgSize             != 0) delete this->maxMsgSize;
  if (this->statusInformation      != 0) delete this->statusInformation;
  if (this->globalData             != 0) delete this->globalData;
}


//  clone
snmpUSMGenericOutMsg *
snmpUSMGenericOutMsg::clone() {
  snmpUSMGenericOutMsg *retObj = new snmpUSMGenericOutMsg(*this);



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





  if (this->scopedPDU              != 0) 
    retObj->scopedPDU = this->scopedPDU->clone();

  // ---------------------------------

  if (this->outMsg                 != 0)
    retObj->outMsg = this->outMsg->clone();
  if (this->outMsgLength           != 0) 
    retObj->outMsgLength = this->outMsgLength->clone();
  if (this->securityParameters     != 0) 
    retObj->securityParameters = this->securityParameters->clone();
  // ---------------------------------

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


// Message Processing Model 

Integer32  *
snmpUSMGenericOutMsg::get_messageProcessingModel(bool extract)  {
  Integer32 *retVal = this->messageProcessingModel;
  if (extract)  this->messageProcessingModel = 0;
  return(retVal);
}

bool  
snmpUSMGenericOutMsg::set_messageProcessingModel(Integer32 *newNum)  {
  if (this->messageProcessingModel != 0) delete this->messageProcessingModel;
  this->messageProcessingModel = newNum;
  return(true);
}


// Security Model

Integer32  *
snmpUSMGenericOutMsg::get_securityModel(bool extract)  {
  Integer32 *retVal = this->securityModel;
  if (extract)  this->securityModel = 0;
  return(retVal);
}

bool  
snmpUSMGenericOutMsg::set_securityModel(Integer32 *newNum)  {
  if (this->securityModel != 0) delete this->securityModel;
  this->securityModel = newNum;
  return(true);
}


// Security Name

OctetString  *
snmpUSMGenericOutMsg::get_securityName(bool extract)  {
  OctetString *retVal = this->securityName;
  if (extract)  this->securityName = 0;
  return(retVal);
}

bool  
snmpUSMGenericOutMsg::set_securityName(OctetString *newString)  {
  if (this->securityName != 0) delete this->securityName;
  this->securityName = newString;
  return(true);
}


// Security Level

Integer32  *
snmpUSMGenericOutMsg::get_securityLevel(bool extract)  {
  Integer32 *retVal = this->securityLevel;
  if (extract)  this->securityLevel = 0;
  return(retVal);
}

bool  
snmpUSMGenericOutMsg::set_securityLevel(Integer32 *newNum)  {
  if (this->securityLevel != 0) delete this->securityLevel;
  this->securityLevel = newNum;
  return(true);
}


// Security Engine ID

OctetString  *
snmpUSMGenericOutMsg::get_securityEngineID(bool extract)  {
  OctetString *retVal = this->securityEngineID;
  if (extract)  this->securityEngineID = 0;
  return(retVal);
}

bool  
snmpUSMGenericOutMsg::set_securityEngineID(OctetString *newString)  {
  if (this->securityEngineID != 0) delete this->securityEngineID;
  this->securityEngineID = newString;
  return(true);
}

// -----------------------------------------------------------------


// Scoped PDU 

ScopedPDU  *
snmpUSMGenericOutMsg::get_scopedPDU(bool extract)  {
  ScopedPDU *retVal = this->scopedPDU;
  if (extract)  this->scopedPDU= 0;
  return(retVal);
}

bool  
snmpUSMGenericOutMsg::set_scopedPDU(ScopedPDU *newScopedPDU)  {
  if (this->scopedPDU != 0) delete this->scopedPDU;
  this->scopedPDU = newScopedPDU;
  return(true);
}


// -----------------------------------------------------------------


// Whole Out Message

BufferClass *
snmpUSMGenericOutMsg::get_wholeOutMsg(bool extract)  {
  BufferClass *retVal = this->outMsg;
  if (extract)  this->outMsg = 0;
  return(retVal);
}

bool  
snmpUSMGenericOutMsg::set_wholeOutMsg(BufferClass *newWholeOutMsg)  {
  if (this->outMsg != 0) delete this->outMsg;
  this->outMsg = newWholeOutMsg;
  return(true);
}


// Whole Out Message Length

Integer32  *
snmpUSMGenericOutMsg::get_wholeOutMsgLength(bool extract)  {
  Integer32 *retVal = this->outMsgLength;
  if (extract)  this->outMsgLength = 0;
  return(retVal);
}

bool  
snmpUSMGenericOutMsg::set_wholeOutMsgLength(Integer32 *newOutMsgLength)  {
  if (this->outMsgLength != 0) delete this->outMsgLength;
  this->outMsgLength = newOutMsgLength;
  return(true);
}


// Security Parameters

asnDataType  
*snmpUSMGenericOutMsg::get_securityParameters(bool extract)  {
  asnDataType *retVal = this->securityParameters;
  if (extract)  this->securityParameters = 0;
  return(retVal);
}

bool  
snmpUSMGenericOutMsg::set_securityParameters
(asnDataType *newSecParams)  
{
  if (this->securityParameters != 0) delete this->securityParameters;
  this->securityParameters = newSecParams;
  return(true);
}


// -----------------------------------------------------------------


//  Security State Reference

snmpObj *
snmpUSMGenericOutMsg::get_securityStateReference(bool extract) {
  snmpObj *retVal = this->securityStateReference;
  if (extract)  this->securityStateReference = 0;
  return(retVal);
}

bool 
snmpUSMGenericOutMsg::set_securityStateReference
(snmpObj  *newSecStateRef) 
{
  if (this->securityStateReference != 0) delete this->securityStateReference;
  this->securityStateReference = newSecStateRef;
  return(true);
}


// Max Message Size

Integer32  *
snmpUSMGenericOutMsg::get_maxMsgSize(bool extract)  {
  Integer32 *retVal = this->maxMsgSize;
  if (extract)  this->maxMsgSize = 0;
  return(retVal);
}

bool  
snmpUSMGenericOutMsg::set_maxMsgSize(Integer32 *newMaxMsgSize)  {
  if (this->maxMsgSize != 0) delete this->maxMsgSize;
  this->maxMsgSize = newMaxMsgSize;
  return(true);
}


// status Information

snmpStatusInfo *
snmpUSMGenericOutMsg::get_statusInformation(bool extract)  {
  snmpStatusInfo *retStatus = this->statusInformation;
  
  if (extract) { this->statusInformation = NULL; }
  return(retStatus);
}

bool  snmpUSMGenericOutMsg::set_statusInformation
  (snmpStatusInfo *newStatus) 
{
  if (this->statusInformation != NULL) { delete this->statusInformation; }
  this->statusInformation = newStatus;
  return(true);
}


//  global data

HeaderData *
snmpUSMGenericOutMsg::get_globalData(bool extract) {
  HeaderData *retData = this->globalData;
  if (extract) { this->globalData = NULL; }
  return(retData);
}

bool 
snmpUSMGenericOutMsg::set_globalData(HeaderData  *newGlobalData) {
  if (this->globalData != NULL) { delete this->globalData; }
  this->globalData = newGlobalData;
  return(true);
}

