
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCmtsPollTimer.hpp"
#include "axCmtsPoller.hpp"
#include "axCmtsPollRunnableCollection.hpp"

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
axCmtsPollTimer::axCmtsPollTimer() :
  m_intCmts(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmtsPollTimer::~axCmtsPollTimer()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmtsPollTimer::axCmtsPollTimer(axInternalCmts * intCmts, time_t & popTime) :
  axAbstractTimerObject(popTime),
  m_intCmts(intCmts)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axCmtsPollTimer::timerCallback(void)
{
  static const char * myName="cmtsTimerCb:";

  bool ret = false;

  ACE_DEBUG((LM_TIMER_DEBUG, "%s entry\n", myName));

  axCmtsPoller * cmtsPoller = axCmtsPoller::getInstance();

  axCmtsPollRunnableCollection tmpCmtsPollRc(m_intCmts->getResId());

  axCmtsPollRunnableCollection * cmtsPollRc = 
    dynamic_cast<axCmtsPollRunnableCollection *> (cmtsPoller->find(&tmpCmtsPollRc));

  if (!cmtsPollRc) {
    goto EXIT_LABEL;
  }

  cmtsPollRc->scheduleRunnables();

  // lastly but not least ...
  ret = true;

EXIT_LABEL:

  ACE_DEBUG((LM_TIMER_DEBUG, "%s exit\n", myName));

  return (ret);
}


