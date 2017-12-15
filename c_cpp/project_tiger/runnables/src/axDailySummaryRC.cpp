
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCblAssureConstants.hpp"
#include "axDailySummaryRC.hpp"
#include "axDailySummaryTimer.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axGlobalTimer.hpp"
#include "axCATaskCoordHelper.hpp"
#include "axCASystemConfig.hpp"
#include "axDailySummaryRunnable.hpp"
// #include "axAbstractCARunnable.hpp"
// #include "axGlobalData.hpp"
// #include "axGlobalCmtsTable.hpp"
// #include "axAvlTree.hpp"
// #include "axInternalCmts.hpp"
// #include "axCmtsPollTimer.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axDailySummaryRC * axDailySummaryRC::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDailySummaryRC::axDailySummaryRC()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDailySummaryRC::~axDailySummaryRC()
{
}


//********************************************************************
// method : hashCode
//********************************************************************
axDailySummaryRC *
axDailySummaryRC::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axDailySummaryRC();
    m_instance->add(new axDailySummaryRunnable(m_instance));
  }

  return (m_instance);
}


#if 0
//********************************************************************
// method : hashCode
//********************************************************************
AX_INT64
axDailySummaryRC::hashCode(void)
{
  return ((AX_INT64) m_cmtsResId);
}


//********************************************************************
// method : hashUInt32
//********************************************************************
AX_UINT32
axDailySummaryRC::hashUInt32(void)
{
  return (m_cmtsResId);
}
#endif


//********************************************************************
// method:
//********************************************************************
bool
axDailySummaryRC::canScheduleRunnables(void)
{
  // static const char * myName="canSchedCmtsPollRbls:";

  bool ret = true;

  axCATaskCoordHelper * tc = axCATaskCoordHelper::getInstance();

  axWriteLockOperator tcWLock(tc->getLock());

  tc->setSummary();

  if (!tc->canDoSummary()) {
    ret = false;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDailySummaryRC::postponeTask(void)
{
  // static const char * myName="postponeCmtsPoll:";

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  struct timeval currTm;
  gettimeofday(&currTm, NULL);

  time_t timeToStartPolling = currTm.tv_sec +
                                 sysCfg->get_task_postpone_interval();

  axDailySummaryTimer * nextPoll = new
                         axDailySummaryTimer(timeToStartPolling);
  axGlobalTimer * timer = axGlobalTimer::getInstance();
  timer->add(nextPoll);
}


//********************************************************************
// method :
//********************************************************************
axAbstractCARunnable *
axDailySummaryRC::createNewRunnable(void)
{
  axAbstractCARunnable * ret = NULL;

  return (ret);
}


//********************************************************************
// method: processRunnableComplete
// Assumption: there is only ONE runnable in this collection
//********************************************************************
void
axDailySummaryRC::processRunnableComplete(axAbstractRunnable * in)
{
  static const char * myName="cmtsPollComplete:";

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  struct timeval currTm;
  gettimeofday(&currTm, NULL);

  setWorkEndTime(currTm.tv_sec);

  clearInProgress();

  time_t stTime = getWorkStartTime();
  /* skip to next day */
  time_t nextStTime = stTime + AX_SECONDS_IN_A_DAY;
  struct tm result;
  gmtime_r(&nextStTime, &result);
  /* midnight next day */
  result.tm_sec=1;
  result.tm_min=0;
  result.tm_hour=0;
  time_t timeToStartPolling = mktime(&result);

  if ((timeToStartPolling - currTm.tv_sec) < 0) {
    ACE_DEBUG((LM_WARNING, "%s Took longer than a day\n", myName));
    timeToStartPolling = currTm.tv_sec + (1*60);
  }

  axDailySummaryTimer * nextPoll = new
                              axDailySummaryTimer(timeToStartPolling);
  axGlobalTimer * timer = axGlobalTimer::getInstance();
  timer->add(nextPoll);

  setNextCycleScheduled();

  ACE_DEBUG((LM_MISC_DEBUG, "%s start=%d, stop=%d\n",
                                     myName, (int)getWorkStartTime(),
                                             (int)getWorkEndTime()));
}



