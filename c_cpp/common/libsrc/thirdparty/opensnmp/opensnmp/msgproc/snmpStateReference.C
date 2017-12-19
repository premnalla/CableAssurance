//
//  snmpStateReference
//  This object is used by the message proccessor to keep track of
//  pertinent information about an SNMPV3 incoming message.
//

#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>
#include "snmpStateReference.H"


snmpStateReference::snmpStateReference() {
  //  cout << "snmpStateReference constructor\n";
  this->msgID                  = 0; 
  this->securityEngineID       = 0;
  this->contextEngineID        = 0;
  this->contextName            = 0;
  this->securityModel          = 0;
  this->securityName           = 0;
  this->securityLevel          = 0;

  this->sPDU                   = 0;
  this->pdu                    = 0;
  this->globalData             = 0;

  this->reportable             = false;
  this->eng_ID_Discovery       = false;
  
  this->sendPDUHandle          = 0;

  this->transportDomain        = 0;
  this->transportAddress       = 0;

  this->msgFlags               = 0;
  this->msgMaxSize             = 0;
  this->maxSizeResponsePDU     = 0;
  this->securityStateReference = 0;
}

snmpStateReference::~snmpStateReference() {
  //  cout << "snmpStateReference: deconstructor\n";
  if (this->msgID                  != 0)
    delete this->msgID;
  if (this->securityEngineID       != 0)
    delete this->securityEngineID;
  if (this->contextEngineID        != 0)
    delete this->contextEngineID;
  if (this->contextName            != 0)
    delete this->contextName;
  if (this->securityModel          != 0)
    delete this->securityModel;
  if (this->securityName           != 0)
    delete this->securityName;
  if (this->securityLevel          != 0)
    delete this->securityLevel;

  if (this->sPDU                   != 0)
    delete this->sPDU;
  if (this->pdu                    != 0)
    delete this->pdu;
  if (this->globalData             != 0)
    delete this->globalData;

  //  this->sendPDUHandle = 0; // treat as reference

  if (this->transportDomain   != 0)
    delete this->transportDomain;
  if (this->transportAddress   != 0)
    delete this->transportAddress;

  if (this->msgFlags               != 0)
    delete this->msgFlags;
  if (this->msgMaxSize             != 0)
    delete this->msgMaxSize;
  if (this->maxSizeResponsePDU     != 0)
    delete this->maxSizeResponsePDU;
  //  this->securityStateReference = 0; // treat as reference

}


// clone

snmpObj *
snmpStateReference::clone() const {
  snmpStateReference *retObj = new snmpStateReference();

  if (this->msgID                  != 0)
    retObj->msgID                   = this->msgID->clone();
  if (this->securityEngineID       != 0)
    retObj->securityEngineID        = this->securityEngineID->clone();
  if (this->contextEngineID        != 0)
    retObj->contextEngineID         = this->contextEngineID->clone();
  if (this->contextName            != 0)
    retObj->contextName             = this->contextName->clone();
  if (this->securityModel          != 0)
    retObj->securityModel           = this->securityModel->clone();
  if (this->securityName           != 0)
    retObj->securityName            = this->securityName->clone();
  if (this->securityLevel          != 0)
    retObj->securityLevel           = this->securityLevel->clone();
  
  if (this->sPDU                   != 0)
    retObj->sPDU                    = this->sPDU->clone();
  if (this->pdu                    != 0)
    retObj->pdu                     = this->pdu->clone();
  if (this->globalData             != 0)
    retObj->globalData              = this->globalData->clone();

  retObj->reportable                = this->reportable;
  retObj->eng_ID_Discovery          = this->eng_ID_Discovery;

  retObj->sendPDUHandle = this->sendPDUHandle;

  if (this->transportDomain   != 0)
    retObj->transportDomain = new TransportDomain(*(this->transportDomain));
  if (this->transportAddress       != 0)
    retObj->transportAddress        = this->transportAddress->clone();

  if (this->msgFlags               != 0)
    retObj->msgFlags                = this->msgFlags->clone();
  if (this->msgMaxSize             != 0)
    retObj->msgMaxSize              = this->msgMaxSize->clone();
  if (this->maxSizeResponsePDU     != 0)
    retObj->maxSizeResponsePDU      = this->maxSizeResponsePDU->clone();

  if (this->securityStateReference != 0)
    retObj->securityStateReference  = this->securityStateReference->clone();

  return(retObj);
}

