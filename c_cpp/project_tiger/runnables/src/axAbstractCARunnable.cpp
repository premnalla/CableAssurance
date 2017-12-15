
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axAbstractCARunnable.hpp"
#include "axWriteLockOperator.hpp"

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
axAbstractCARunnable::axAbstractCARunnable() :
  m_rc(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractCARunnable::~axAbstractCARunnable()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAbstractCARunnable::axAbstractCARunnable(axAbstractCARunnableCollection * rc) :
  m_rc(rc)
{
}


//********************************************************************
// method:
//********************************************************************
axAbstractCARunnableCollection *
axAbstractCARunnable::getRunnableCollection(void)
{
  return (m_rc);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axAbstractCARunnable::preRun(void)
{
  AX_INT32 ret = 0;

  struct timeval tm;
  gettimeofday(&tm, NULL);

  {
    axWriteLockOperator rwLock(getLock());
    setRunning();
    setWorkStartTime(tm.tv_sec);
  }

  {
    axWriteLockOperator rwLock(m_rc->getLock());
    if (!m_rc->getWorkStartTime()) {
      m_rc->setWorkStartTime(tm.tv_sec);
    }
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axAbstractCARunnable::postRun(void)
{
  AX_INT32 ret = 0;

  axWriteLockOperator rwLock(getLock());
  clearRunning();

  struct timeval tm;
  gettimeofday(&tm, NULL);
  setWorkEndTime(tm.tv_sec);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axAbstractLock *
axAbstractCARunnable::getLock(void)
{
  return (m_myData.lock);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractCARunnable::isRunning(void)
{
  return (m_myData.running);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCARunnable::setRunning(void)
{
  m_myData.running = true;
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCARunnable::clearRunning(void)
{
  m_myData.running = false;
}


//********************************************************************
// method:
//********************************************************************
time_t
axAbstractCARunnable::getWorkStartTime(void)
{
  return (m_myData.workStartTime);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCARunnable::setWorkStartTime(time_t in)
{
  m_myData.workStartTime = in;
}


//********************************************************************
// method:
//********************************************************************
time_t
axAbstractCARunnable::getWorkEndTime(void)
{
  return (m_myData.workEndTime);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCARunnable::setWorkEndTime(time_t in)
{
  m_myData.workEndTime = in;
}


//********************************************************************
// method:
//********************************************************************
size_t
axAbstractCARunnable::size(void)
{
  size_t ret = 0;
  return (ret);
}


