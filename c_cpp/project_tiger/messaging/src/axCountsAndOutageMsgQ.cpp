
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCountsAndOutageMsgQ.hpp"
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
axCountsAndOutageMsgQ * axCountsAndOutageMsgQ::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCountsAndOutageMsgQ::axCountsAndOutageMsgQ() :
  axCAMessageQueue(AX_MSGQ_TOPIC_COUNTS_OUTAGES,AX_SS_TYPE_COUNTS_OUTAGES)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCountsAndOutageMsgQ::~axCountsAndOutageMsgQ()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCountsAndOutageMsgQ::axCountsAndOutageMsgQ()
// {
// }


//********************************************************************
// method:
//********************************************************************
axCountsAndOutageMsgQ *
axCountsAndOutageMsgQ::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axCountsAndOutageMsgQ();
    axMessageManager * mgr = axMessageManager::getInstance();
    mgr->registerMessageTopic(AX_MSGQ_TYPE_PUB_SUB, 
             AX_MSGQ_TOPIC_COUNTS_OUTAGES, AX_SS_TYPE_COUNTS_OUTAGES);
    mgr->registerMessageQueue(m_instance);
  }

  return (m_instance);
}


