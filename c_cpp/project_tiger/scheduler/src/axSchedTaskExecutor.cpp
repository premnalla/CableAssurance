
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axSchedTaskExecutor.hpp"
#include "axCAScheduler.hpp"
// #include "axCAScheduler.hpp"
// #include "axSchedTaskExecutorQ.hpp"

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
axSchedTaskExecutor::axSchedTaskExecutor()
{
}


//********************************************************************
// destructor:
//********************************************************************
axSchedTaskExecutor::~axSchedTaskExecutor()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axSchedTaskExecutor::axSchedTaskExecutor()
// {
// }


//********************************************************************
// method: main loop
//********************************************************************
AX_INT32
axSchedTaskExecutor::run(void)
{
  bool exitCondition = false;

  axAbstractRunnable * r;

  pthread_t thrId = getWorkerId();

  if (thrId != pthread_self()) {
    ACE_ERROR((LM_ERROR, "ACE_thread_t != pthread_t\n"));
  }

  // Consumer-Supplier Runnable Q
  axCAScheduler * sched = axCAScheduler::getInstance();

  while(!exitCondition) {

    while((r=sched->deQNextRunnable()) != NULL) {

      ACE_DEBUG((LM_SCHED_DEBUG, "axSchedTaskExecutor::run(): thrId=%d\n", 
                                                 (int)thrId));

      r->preRun();
      r->run();
      r->postRun();
      r->nextAction();
      if (r->isDeletable()) {
        delete r;
      }

    }

  }

  return (0);
}


#if 0
//********************************************************************
// method:
//********************************************************************
void
axSchedTaskExecutor::enQWorkItem(axAbstractRunnable * r)
{
  m_workItemQ.add(r);
}
#endif


