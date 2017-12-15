
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCountsAndAlarmMsgQ.hpp"
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
axCountsAndAlarmMsgQ * axCountsAndAlarmMsgQ::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCountsAndAlarmMsgQ::axCountsAndAlarmMsgQ() :
  axCAMessageQueue(AX_MSGQ_TOPIC_COUNTS_ALARMS,AX_SS_TYPE_COUNTS_ALARMS)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCountsAndAlarmMsgQ::~axCountsAndAlarmMsgQ()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCountsAndAlarmMsgQ::axCountsAndAlarmMsgQ()
// {
// }


//********************************************************************
// method:
//********************************************************************
axCountsAndAlarmMsgQ *
axCountsAndAlarmMsgQ::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axCountsAndAlarmMsgQ();
    axMessageManager * mgr = axMessageManager::getInstance();
    mgr->registerMessageTopic(AX_MSGQ_TYPE_PUB_SUB, 
             AX_MSGQ_TOPIC_COUNTS_ALARMS, AX_SS_TYPE_COUNTS_ALARMS);
    mgr->registerMessageQueue(m_instance);
  }

  return (m_instance);
}


