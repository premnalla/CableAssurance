
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCASystemConfig.hpp"
#include "axCAScheduler.hpp"
#include "axTaskScheduler.hpp"
#include "axSchedTaskExecutor.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axCAScheduler * axCAScheduler::m_instance = NULL;
// bool axCAScheduler::m_initialized = false;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCAScheduler::axCAScheduler() :
  m_initialized(false)
{
}


//********************************************************************
// default constructor:
//********************************************************************
void
axCAScheduler::initialize(void)
{
  axCASystemConfig * sc = axCASystemConfig::getInstance();

  if (!m_initialized) {
    for (int i=0; i<sc->getNumRunnableExecutors(); i++) {
      axSchedTaskExecutor * t = new axSchedTaskExecutor();
      t->start();
      ACE_DEBUG((LM_SCHED_DEBUG, "Started axSchedTaskExecutor id=%d\n", 
                                            (int) t->getWorkerId()));
    }

    m_initialized = true;
  }
}


//********************************************************************
// destructor:
//********************************************************************
axCAScheduler::~axCAScheduler()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCAScheduler::axCAScheduler()
// {
// }


//********************************************************************
// method:
//********************************************************************
axCAScheduler *
axCAScheduler::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axCAScheduler();
    m_instance->initialize();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
bool
axCAScheduler::enQRunnable(axAbstractRunnable * r)
{
  // static const char * myName="enQRunnable";
  bool ret = true;

  // ACE_TRACE(ACE_TEXT ("%s", myName));

  m_queue.add(r);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axAbstractRunnable *
axCAScheduler::deQNextRunnable(void)
{
  // static const char * myName="deQNextRunnable";
  axAbstractRunnable * ret;

  // ACE_TRACE(ACE_TEXT ("%s", myName));

  ret = (axAbstractRunnable *) m_queue.remove();

  return (ret);
}


