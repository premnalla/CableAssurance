
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCountsAndOutageMsg.hpp"
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
axCountsAndOutageMsg::axCountsAndOutageMsg()
{
  setMessageId(axMessageSubSystem::getInstance()->getNewMessageId());
  setMessageTopic(AX_MSGQ_TOPIC_COUNTS_OUTAGES);
  setMessageSourceSubSystem(AX_SS_TYPE_NULL);
  setMessagePayload(NULL);
}


//********************************************************************
// destructor:
//********************************************************************
axCountsAndOutageMsg::~axCountsAndOutageMsg()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCountsAndOutageMsg::axCountsAndOutageMsg(AX_UINT8 srcSS, 
                                       axMessagePayload_s * payload)
{
  setMessageId(axMessageSubSystem::getInstance()->getNewMessageId());
  setMessageTopic(AX_MSGQ_TOPIC_COUNTS_OUTAGES);
  setMessageSourceSubSystem(srcSS);
  setMessagePayload(payload);
}


