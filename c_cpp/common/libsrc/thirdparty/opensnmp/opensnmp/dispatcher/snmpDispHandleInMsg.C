//
// snmpDispHandleInMsg implememntation
//

#include "config.h"
#include <stdio.h>
#include <iostream>
#include <string>

#include "snmpDispHandleInMsg.H"
#include "debug.H"


snmpDispHandleInMsg::snmpDispHandleInMsg()  {
  DEBUGCREATE(debugObj, "snmpDispHandleInMsg");
  DEBUG9(debugObj, "Disp:snmpDispHandleInMsg contructor" << endl);
}

snmpDispHandleInMsg::snmpDispHandleInMsg(TransportDomain  *domain, 
					 TransportAddress *address, 
					 TransportAddress *localAddress, 
					 BufferClass      *data, 
					 Integer32        *length) :
  transportDomain(domain), transportAddress(address), 
  localTransportAddress(localAddress),
  msgData(data), msgLength(length)
{
  DEBUGCREATE(debugObj, "snmpDispHandleInMsg");
  DEBUG9(debugObj, "Disp:snmpDispHandleInMsg contructor 2" << endl);
}


snmpDispHandleInMsg::~snmpDispHandleInMsg()  {
  DEBUG9(debugObj, "Disp:~snmpDispHandleInMsg destructor" << endl);
  
  if (this->transportDomain       != 0) delete this->transportDomain;
  if (this->transportAddress      != 0) delete this->transportAddress;
  if (this->localTransportAddress != 0) delete this->localTransportAddress;
  if (this->msgData               != 0) delete this->msgData;
  if (this->msgLength             != 0) delete this->msgLength;

  DEBUGDESTROY(debugObj);
}


snmpDispHandleInMsg *
snmpDispHandleInMsg::clone() const {
  snmpDispHandleInMsg *retObj = new snmpDispHandleInMsg(*this);
  if (retObj->transportDomain != 0) {
    retObj->transportDomain = new TransportDomain(*this->transportDomain);
    //    *(retObj->transportDomain) = *(this->transportDomain);
  }
  if (retObj->transportAddress != 0) {
    retObj->transportAddress = retObj->transportAddress->clone();
  }
  if (retObj->localTransportAddress != 0) {
    retObj->localTransportAddress = retObj->localTransportAddress->clone();
  }
  if (retObj->msgData != 0) {
    retObj->msgData = this->msgData->clone();
  }
  if (retObj->msgLength != 0) {
    retObj->msgLength = this->msgLength->clone();
  }

  return(retObj);
}


void
snmpDispHandleInMsg::set_localTransportAddress(TransportAddress  *localAddress)
{
  if (this->localTransportAddress)
    delete this->localTransportAddress;
  this->localTransportAddress = localAddress;
}

TransportAddress  *
snmpDispHandleInMsg::get_localTransportAddress(bool extract) {
  TransportAddress  *ret = localTransportAddress;
  if (extract)
    localTransportAddress = NULL;
  return ret;
}

void
snmpDispHandleInMsg::set_transportDomain(TransportDomain  *transportDomain) {
  if (this->transportDomain)
    delete this->transportDomain;
  this->transportDomain = transportDomain;
}

TransportDomain  *
snmpDispHandleInMsg::get_transportDomain(bool extract) {
  TransportDomain  *ret = transportDomain;
  if (extract)
    transportDomain = NULL;
  return ret;
}

void
snmpDispHandleInMsg::set_transportAddress(TransportAddress  *transportAddress)
{
  if (this->transportAddress)
    delete this->transportAddress;
  this->transportAddress = transportAddress;
}

TransportAddress  *
snmpDispHandleInMsg::get_transportAddress(bool extract) {
  TransportAddress  *ret = transportAddress;
  if (extract)
    transportAddress = NULL;
  return ret;
}

void
snmpDispHandleInMsg::set_msgData(BufferClass  *msgData) {
  if (this->msgData)
    delete this->msgData;
  this->msgData = msgData;
}

BufferClass  *
snmpDispHandleInMsg::get_msgData(bool extract) {
  BufferClass  *ret = msgData;
  if (extract)
    msgData = NULL;
  return ret;
}

void
snmpDispHandleInMsg::set_msgLength(Integer32  *msgLength) {
  if (this->msgLength)
    delete this->msgLength;
  this->msgLength = msgLength;
}

Integer32  *
snmpDispHandleInMsg::get_msgLength(bool extract) {
  Integer32  *ret = msgLength;
  if (extract)
    msgLength = NULL;
  return ret;
}

