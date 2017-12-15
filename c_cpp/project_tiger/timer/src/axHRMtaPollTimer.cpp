
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axHRMtaPollTimer.hpp"
#include "axMtaPoller.hpp"
#include "axHRMtaPollRunnableCollection.hpp"

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
axHRMtaPollTimer::axHRMtaPollTimer() :
  m_intCmts(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axHRMtaPollTimer::~axHRMtaPollTimer()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHRMtaPollTimer::axHRMtaPollTimer(axInternalCmts * intCmts, time_t & popTime) :
  axAbstractTimerObject(popTime),
  m_intCmts(intCmts)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axHRMtaPollTimer::timerCallback(void)
{
  static const char * myName="mtaPollTimerCb:";

  bool ret = false;

  ACE_DEBUG((LM_TIMER_DEBUG, "%s entry\n", myName));

  axMtaPoller * mtaHRPoller = axMtaPoller::getInstance();

  axHRMtaPollRunnableCollection tmpMtaPollRc(m_intCmts->getResId());

  axHRMtaPollRunnableCollection * mtaPollRc = 
    dynamic_cast<axHRMtaPollRunnableCollection *> (mtaHRPoller->find(&tmpMtaPollRc));

  if (!mtaPollRc) {
    goto EXIT_LABEL;
  }

  mtaPollRc->scheduleRunnables();

  // lastly but not least ...
  ret = true;

EXIT_LABEL:

  ACE_DEBUG((LM_TIMER_DEBUG, "%s exit\n", myName));

  return (ret);
}


