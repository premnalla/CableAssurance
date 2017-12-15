
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCmPerfPollTimer.hpp"
#include "axCmPerfPoller.hpp"
#include "axCmPerfPollRunnableCollection.hpp"

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
axCmPerfPollTimer::axCmPerfPollTimer() :
  m_intCmts(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmPerfPollTimer::~axCmPerfPollTimer()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmPerfPollTimer::axCmPerfPollTimer(axInternalCmts * intCmts, time_t & popTime) :
  axAbstractTimerObject(popTime),
  m_intCmts(intCmts)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axCmPerfPollTimer::timerCallback(void)
{
  static const char * myName="cmPerfTimerCb:";

  bool ret = false;

  ACE_DEBUG((LM_TIMER_DEBUG, "%s entry\n", myName));

  axCmPerfPoller * cmPerfPoller = axCmPerfPoller::getInstance();

  axCmPerfPollRunnableCollection tmpCmPollRc(m_intCmts->getResId());

  axCmPerfPollRunnableCollection * cmPollRc = 
    dynamic_cast<axCmPerfPollRunnableCollection *> (cmPerfPoller->find(&tmpCmPollRc));

  if (!cmPollRc) {
    goto EXIT_LABEL;
  }

  cmPollRc->scheduleRunnables();

  // lastly but not least ...
  ret = true;

EXIT_LABEL:

  ACE_DEBUG((LM_TIMER_DEBUG, "%s exit\n", myName));

  return (ret);
}


