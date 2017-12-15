
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAlarmProcessingMsgQ.hpp"
#include "axMessageDataTypes.hpp"
#include "axSubSystemCommon.hpp"
#include "axMessageManager.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axAlarmProcessingMsgQ * axAlarmProcessingMsgQ::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axAlarmProcessingMsgQ::axAlarmProcessingMsgQ() :
  axCAMessageQueue(AX_MSGQ_TOPIC_ALARM_PROCESSING,
                                           AX_SS_TYPE_ALARM_PROCESSING)
{
}


//********************************************************************
// destructor:
//********************************************************************
axAlarmProcessingMsgQ::~axAlarmProcessingMsgQ()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axAlarmProcessingMsgQ::axAlarmProcessingMsgQ()
// {
// }


//********************************************************************
// method:
//********************************************************************
axAlarmProcessingMsgQ *
axAlarmProcessingMsgQ::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axAlarmProcessingMsgQ();
    axMessageManager * mgr = axMessageManager::getInstance();
    mgr->registerMessageTopic(AX_MSGQ_TYPE_PUB_SUB, 
         AX_MSGQ_TOPIC_ALARM_PROCESSING, AX_SS_TYPE_ALARM_PROCESSING);
    mgr->registerMessageQueue(m_instance);
  }

  return (m_instance);
}


