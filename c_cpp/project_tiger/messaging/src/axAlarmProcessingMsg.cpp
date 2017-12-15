
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAlarmProcessingMsg.hpp"
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
axAlarmProcessingMsg::axAlarmProcessingMsg()
{
  setMessageId(axMessageSubSystem::getInstance()->getNewMessageId());
  setMessageTopic(AX_MSGQ_TOPIC_ALARM_PROCESSING);
  setMessageSourceSubSystem(AX_SS_TYPE_NULL);
  setMessagePayload(NULL);
}


//********************************************************************
// destructor:
//********************************************************************
axAlarmProcessingMsg::~axAlarmProcessingMsg()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAlarmProcessingMsg::axAlarmProcessingMsg(AX_UINT8 srcSS, 
                                            axAbstractCAAlarm * alarm)
{
  setMessageId(axMessageSubSystem::getInstance()->getNewMessageId());
  setMessageTopic(AX_MSGQ_TOPIC_ALARM_PROCESSING);
  setMessageSourceSubSystem(srcSS);

  axAlarmProcessingPayload_s * p1 = new axAlarmProcessingPayload_s();
  p1->alarm = alarm;
  axMessagePayload_s * p = new axMessagePayload_s();
  p->specificPayloadData = p1;
  setMessagePayload(p);
}


