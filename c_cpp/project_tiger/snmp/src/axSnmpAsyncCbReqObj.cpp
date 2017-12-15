
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axSnmpAsyncCbReqObj.hpp"
#include "axAbsSnmpAsyncPollWork.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axSnmpAsyncCbReqObj::axSnmpAsyncCbReqObj() :
  m_state(UNKNOWN), 
  m_intObj(NULL), 
  m_processingClass(NULL), 
  m_snmpSession(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axSnmpAsyncCbReqObj::~axSnmpAsyncCbReqObj()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axSnmpAsyncCbReqObj::axSnmpAsyncCbReqObj(
       axAbstractInternalObject * intO, axAbsSnmpAsyncPollWork * pC) :
  m_state(UNKNOWN),
  m_intObj(intO),
  m_processingClass(pC),
  m_snmpSession(NULL)
{
  pC->addCallBackReqObj(this);
}


//********************************************************************
// method:
//********************************************************************
axAbstractInternalObject *
axSnmpAsyncCbReqObj::getInternalObject(void)
{
  return (m_intObj);
}


//********************************************************************
// method:
//********************************************************************
axAbsSnmpAsyncPollWork *
axSnmpAsyncCbReqObj::getProcessingClass(void)
{
  return (m_processingClass);
}


//********************************************************************
// method:
//********************************************************************
axAbstractSnmpSession *
axSnmpAsyncCbReqObj::getSnmpSession(void)
{
  return (m_snmpSession);
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpAsyncCbReqObj::setSnmpSession(axAbstractSnmpSession * s)
{
  m_snmpSession = s;
  s->getInternalSession()->callback_magic = this;
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpAsyncCbReqObj::setStateInit(void)
{
  m_state = INIT;
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpAsyncCbReqObj::setStateReqSent(void)
{
  m_state = REQ_SENT;
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpAsyncCbReqObj::setStateSendReqFailed(void)
{
  m_state = SEND_REQ_FAILED;
}

//********************************************************************
// method:
//********************************************************************
void
axSnmpAsyncCbReqObj::setStateReplyReceived(void)
{
  m_state = REPLY_RCV;
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpAsyncCbReqObj::setStateTimeout(void)
{
  m_state = TIMEOUT;
}


//********************************************************************
// method:
//********************************************************************
bool
axSnmpAsyncCbReqObj::isReplyReceived(void)
{
  return ((m_state == REPLY_RCV ? true : false));
}


//********************************************************************
// method:
//********************************************************************
bool
axSnmpAsyncCbReqObj::isReqSent(void)
{
  return ((m_state == REQ_SENT ? true : false));
}


//********************************************************************
// method:
//********************************************************************
bool
axSnmpAsyncCbReqObj::isSendReqFailed(void)
{
  return ((m_state == SEND_REQ_FAILED ? true : false));
}


//********************************************************************
// method:
//********************************************************************
bool
axSnmpAsyncCbReqObj::isTimeout(void)
{
  return ((m_state == TIMEOUT ? true : false));
}


