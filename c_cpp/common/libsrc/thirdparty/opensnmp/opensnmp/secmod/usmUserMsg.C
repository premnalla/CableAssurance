#include "config.h"
#include "usmUserMsg.H"
#include <stdio.h>

usmUserMsg::usmUserMsg
(OctetString    *engID,
 OctetString    *userName,
 snmpFIFOObj    *returnFIFO,
 OID            *authProtocolOID,
 opensnmpKey    *authKey,
 OID            *privProtocolOID,
 opensnmpKey    *privKey,

 snmpStatusInfo   *status,

 snmpMessageObj   *copyFromMsg,
 int               newID,
 snmpSynchDataObj *newData)

  : snmpMessageObj(copyFromMsg, newID, newData, returnFIFO)
{
  this->engID             = engID;
  this->userName          = userName;
  this->authProtocolOID   = authProtocolOID;
  this->privProtocolOID   = privProtocolOID;
  this->authKey           = authKey;
  this->privKey           = privKey;
  this->statusInformation = status;
  
  //  cout << "usmUserMsg:constructor\n";
}

usmUserMsg::usmUserMsg(usmUserMsg *copyFrom) 
{
  if (copyFrom->engID != 0)
    this->engID = copyFrom->engID->clone();
  else this->engID = NULL;

  if (copyFrom->userName != 0)
    this->userName = copyFrom->userName->clone();
  else this->userName = NULL;
  
  if (copyFrom->authProtocolOID != 0)
    this->authProtocolOID = copyFrom->authProtocolOID->clone();
  else this->authProtocolOID = NULL;
  
  if (copyFrom->privProtocolOID != 0)
    this->privProtocolOID = copyFrom->privProtocolOID->clone();
  else this->privProtocolOID = NULL;
  
  if (copyFrom->authKey != 0)
    this->authKey = copyFrom->authKey->clone();
  else this->authKey = NULL;
  
  if (copyFrom->privKey != 0)
    this->privKey = copyFrom->privKey->clone();
  else this->privKey = NULL;
  
  if (copyFrom->statusInformation != 0)
    this->statusInformation = copyFrom->statusInformation->clone();
  else this->statusInformation = NULL;
  
  cout << "usmUserMsg:constructor\n";
}


usmUserMsg::~usmUserMsg() {
  if (this->engID             != 0) delete this->engID;
  if (this->userName          != 0) delete this->userName;
  if (this->authProtocolOID   != 0) delete this->authProtocolOID;
  if (this->privProtocolOID   != 0) delete this->privProtocolOID;
  if (this->authKey           != 0) delete this->authKey;
  if (this->privKey           != 0) delete this->privKey;
  if (this->statusInformation != 0) delete this->statusInformation;
};


