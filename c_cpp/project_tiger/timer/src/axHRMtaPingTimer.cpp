
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axHRMtaPingTimer.hpp"
#include "axMtaPinger.hpp"
#include "axHRMtaPingRunnableCollection.hpp"

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
axHRMtaPingTimer::axHRMtaPingTimer() :
  m_intCmts(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axHRMtaPingTimer::~axHRMtaPingTimer()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHRMtaPingTimer::axHRMtaPingTimer(axInternalCmts * intCmts, time_t & popTime) :
  axAbstractTimerObject(popTime),
  m_intCmts(intCmts)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axHRMtaPingTimer::timerCallback(void)
{
  static const char * myName="mtaPingTimerCb:";

  bool ret = false;

  ACE_DEBUG((LM_TIMER_DEBUG, "%s entry\n", myName));

  axMtaPinger * mtaHRPinger = axMtaPinger::getInstance();

  axHRMtaPingRunnableCollection tmpMtaPingRc(m_intCmts->getResId());

  axHRMtaPingRunnableCollection * mtaPingRc = 
    dynamic_cast<axHRMtaPingRunnableCollection *> (mtaHRPinger->find(&tmpMtaPingRc));

  if (!mtaPingRc) {
    goto EXIT_LABEL;
  }

  mtaPingRc->scheduleRunnables();

  // lastly but not least ...
  ret = true;

EXIT_LABEL:

  ACE_DEBUG((LM_TIMER_DEBUG, "%s exit\n", myName));

  return (ret);
}


