
//********************************************************************
// OBSOLETE
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <unistd.h> // sleep
#include "axCALog.hpp"
#include "axTaskScheduler.hpp"
#include "axCAScheduler.hpp"
// #include "axSchedTaskExecutor.hpp"
#include "axSchedTaskExecutorQ.hpp"

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
axTaskScheduler::axTaskScheduler()
{
}


//********************************************************************
// destructor:
//********************************************************************
axTaskScheduler::~axTaskScheduler()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axTaskScheduler::axTaskScheduler()
// {
// }


//********************************************************************
// method: main loop
//********************************************************************
AX_INT32
axTaskScheduler::run(void)
{
  bool exitCondition = false;

  ACE_LOG_MSG;

  axCAScheduler * sched = axCAScheduler::getInstance();
  axSchedTaskExecutorQ * csRblQ = axSchedTaskExecutorQ::getInstance();
  bool sleepForABit;

  while(!exitCondition) {

    sleepForABit = true;

    if (csRblQ->isEmpty()) {
      axAbstractRunnable * r = sched->deQNextRunnable();
      if (r) {
        csRblQ->add(r);
        sleepForABit = false;
      }
    }

    if (sleepForABit) {
      sleep(1);
    }

  }

  return (0);
}


#if 0
//********************************************************************
// method:
//********************************************************************
void
axTaskScheduler::enQWorkItem(axAbstractRunnable * r)
{
  m_workItemQ.add(r);
}
#endif


#if 0
//********************************************************************
// method: 
//********************************************************************
void
axTaskScheduler::setPriorityQueue(axList_mts * priorityQ[])
{

}
#endif


