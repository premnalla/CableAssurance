
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCountsAndAlarmMsg.hpp"
#include "axMessageSubSystem.hpp"
#include "axSubSystemCommon.hpp"

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
axCountsAndAlarmMsg::axCountsAndAlarmMsg()
{
  setMessageId(axMessageSubSystem::getInstance()->getNewMessageId());
  setMessageTopic(AX_MSGQ_TOPIC_COUNTS_ALARMS);
  setMessageSourceSubSystem(AX_SS_TYPE_NULL);
  setMessagePayload(NULL);
}


//********************************************************************
// destructor:
//********************************************************************
axCountsAndAlarmMsg::~axCountsAndAlarmMsg()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCountsAndAlarmMsg::axCountsAndAlarmMsg(AX_UINT8 srcSS, 
                                       axMessagePayload_s * payload)
{
  setMessageId(axMessageSubSystem::getInstance()->getNewMessageId());
  setMessageTopic(AX_MSGQ_TOPIC_COUNTS_ALARMS);
  setMessageSourceSubSystem(srcSS);
  setMessagePayload(payload);
}


