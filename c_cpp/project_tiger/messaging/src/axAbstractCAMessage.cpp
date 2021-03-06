
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCAMessage.hpp"

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
axAbstractCAMessage::axAbstractCAMessage()
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractCAMessage::~axAbstractCAMessage()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axAbstractCAMessage::axAbstractCAMessage()
// {
// }


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAMessage::setMessageId(AX_MSG_MSGID_TYPE id)
{
  m_msg.msgHeader.msgId = id;
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAMessage::setMessageTopic(AX_UINT8 t)
{
  m_msg.msgHeader.msgTopic = t;
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAMessage::setMessageSourceSubSystem(AX_UINT8 s)
{
  m_msg.msgHeader.msgSourceSubSystemId = s;
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAMessage::setMessagePayload(axMessagePayload_s * p)
{
  if (m_msg.msgPayload) {
    delete m_msg.msgPayload;
  }
  m_msg.msgPayload = p;
}


//********************************************************************
// method:
//********************************************************************
AX_MSG_MSGID_TYPE
axAbstractCAMessage::getMessageId(void)
{
  return (m_msg.msgHeader.msgId);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axAbstractCAMessage::getMessageTopic(void)
{
  return (m_msg.msgHeader.msgTopic);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axAbstractCAMessage::getMessageSourceSubSystem(void)
{
  return (m_msg.msgHeader.msgSourceSubSystemId);
}


//********************************************************************
// method:
//********************************************************************
axMessagePayload_s *
axAbstractCAMessage::getMessagePayload(void)
{
  return (m_msg.msgPayload);
}


