
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCmtsCmPerfPollTimer.hpp"
#include "axCmtsCmPerfPoller.hpp"
#include "axCmtsCmPerfPollRunnableCollection.hpp"

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
axCmtsCmPerfPollTimer::axCmtsCmPerfPollTimer() :
  m_intCmts(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmtsCmPerfPollTimer::~axCmtsCmPerfPollTimer()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmtsCmPerfPollTimer::axCmtsCmPerfPollTimer(axInternalCmts * intCmts, time_t & popTime) :
  axAbstractTimerObject(popTime),
  m_intCmts(intCmts)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axCmtsCmPerfPollTimer::timerCallback(void)
{
  static const char * myName="CmtsCmPerf::tmCbk:";

  bool ret = false;

  ACE_DEBUG((LM_TIMER_DEBUG, "%s entry\n", myName));

  axCmtsCmPerfPoller * cmtsPoller = axCmtsCmPerfPoller::getInstance();

  axCmtsCmPerfPollRunnableCollection tmpCmtsPollRc(m_intCmts->getResId());

  axCmtsCmPerfPollRunnableCollection * cmtsPollRc = 
    dynamic_cast<axCmtsCmPerfPollRunnableCollection *> (cmtsPoller->find(&tmpCmtsPollRc));

  if (!cmtsPollRc) {
    goto EXIT_LABEL;
  }

  // printf("In axCmtsCmPerfPollTimer::timerCallback() found runnable collection \n");

  cmtsPollRc->scheduleRunnables();

  // lastly but not least ...
  ret = true;

EXIT_LABEL:

  ACE_DEBUG((LM_TIMER_DEBUG, "%s exit\n", myName));

  return (ret);
}


