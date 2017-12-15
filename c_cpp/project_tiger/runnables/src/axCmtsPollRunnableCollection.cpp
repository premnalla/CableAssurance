
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCmtsPollRunnableCollection.hpp"
#include "axWriteLockOperator.hpp"
#include "axAbstractCARunnable.hpp"
#include "axGlobalData.hpp"
#include "axGlobalCmtsTable.hpp"
#include "axReadLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axInternalCmts.hpp"
#include "axCmtsPollTimer.hpp"
#include "axGlobalTimer.hpp"
#include "axCASystemConfig.hpp"
#include "axCATaskCoordHelper.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
// AX_UINT32 axCmtsPollRunnableCollection::m_nextRunnableId = 1;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCmtsPollRunnableCollection::axCmtsPollRunnableCollection()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmtsPollRunnableCollection::~axCmtsPollRunnableCollection()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmtsPollRunnableCollection::axCmtsPollRunnableCollection(
    AX_UINT32 cmtsResId) :
  m_cmtsResId(cmtsResId)
{
}


//********************************************************************
// method : hashCode
//********************************************************************
AX_INT64
axCmtsPollRunnableCollection::hashCode(void)
{
  return ((AX_INT64) m_cmtsResId);
}


//********************************************************************
// method : hashUInt32
//********************************************************************
AX_UINT32
axCmtsPollRunnableCollection::hashUInt32(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method:
//********************************************************************
bool
axCmtsPollRunnableCollection::canScheduleRunnables(void)
{
  // static const char * myName="canSchedCmtsPollRbls:";

  bool ret = true;

  axCATaskCoordHelper * tc = axCATaskCoordHelper::getInstance();

  axWriteLockOperator tcWLock(tc->getLock());

  if (tc->canDoCmtsPoll()) {
    tc->setCmtsPoll();
  } else {
    ret = false;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axCmtsPollRunnableCollection::postponeTask(void)
{
  // static const char * myName="postponeCmtsPoll:";

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  struct timeval currTm;
  gettimeofday(&currTm, NULL);

  axIntCmtsKey_t cmtsKey;
  cmtsKey.resId = m_cmtsResId;

  axInternalCmts * intCmts =
                       axGlobalData::getInstance()->findCmts(cmtsKey);

  time_t timeToStartPolling = currTm.tv_sec +
                                 sysCfg->get_task_postpone_interval();

  axCmtsPollTimer * nextPoll = new
                         axCmtsPollTimer(intCmts, timeToStartPolling);
  axGlobalTimer * timer = axGlobalTimer::getInstance();
  timer->add(nextPoll);
}


#if 0
//********************************************************************
// method:
//********************************************************************
bool
axCmtsPollRunnableCollection::scheduleRunnables(void)
{
  static const char * myName="CmtsPollSchedRbl:";

  bool ret = true;

  axCATaskCoordHelper * tc = axCATaskCoordHelper::getInstance();

  axWriteLockOperator tcWLock(tc->getLock());

  if (tc->canDoCmtsPoll()) {

    tc->setCmtsPoll();

    ret = axSimpleRunnableCollection::scheduleRunnables();

  } else {

    axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

    struct timeval currTm;
    gettimeofday(&currTm, NULL);

    axIntCmtsKey_t cmtsKey;
    cmtsKey.resId = m_cmtsResId;

    axInternalCmts * intCmts =
                       axGlobalData::getInstance()->findCmts(cmtsKey);

    time_t timeToStartPolling = currTm.tv_sec + 
                               sysCfg->get_task_reschedule_interval();

    axCmtsPollTimer * nextPoll = new
                         axCmtsPollTimer(intCmts, timeToStartPolling);
    axGlobalTimer * timer = axGlobalTimer::getInstance();
    timer->add(nextPoll);

  }

  return (ret);
}
#endif


//********************************************************************
// method :
//********************************************************************
axAbstractCARunnable *
axCmtsPollRunnableCollection::createNewRunnable(void)
{
  axAbstractCARunnable * ret = NULL;

  return (ret);
}


//********************************************************************
// method: processRunnableComplete
// Assumption: there is only ONE runnable in this collection
//********************************************************************
void
axCmtsPollRunnableCollection::processRunnableComplete(
                                              axAbstractRunnable * in)
{
  static const char * myName="cmtsPollComplete:";

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  axCATaskCoordHelper * tc = axCATaskCoordHelper::getInstance();

  {
    axWriteLockOperator tcWLock(tc->getLock());
    tc->clearCmtsPoll();
  }

  struct timeval currTm;
  gettimeofday(&currTm, NULL);
  setWorkEndTime(currTm.tv_sec);

  clearInProgress();

  axIntCmtsKey_t cmtsKey;
  cmtsKey.resId = m_cmtsResId;

  axInternalCmts * intCmts =
                       axGlobalData::getInstance()->findCmts(cmtsKey);

  if (intCmts) {

    axReadLockOperator cmtsWlock(intCmts->getLock());

    if (!intCmts->isDeleted()) {

      axIntCmtsNonkey_t * nk = (axIntCmtsNonkey_t *)
                                                 intCmts->getNonKey();
      nk->cmStatusPoll = 1;

      // TODO: remove later; TEMPORARY
      nk->mtaPoll = 1;
      nk->mtaPing = 1;

      time_t timeToStartPolling = getWorkStartTime() +
                                     sysCfg->get_cmts_poll_interval();

      if ((timeToStartPolling - currTm.tv_sec) < 0) {
        timeToStartPolling = currTm.tv_sec + (1*60);
      }

      axCmtsPollTimer * nextPoll = new
                         axCmtsPollTimer(intCmts, timeToStartPolling);
      axGlobalTimer * timer = axGlobalTimer::getInstance();
      timer->add(nextPoll);

      setNextCycleScheduled();

    }

  } else {
    ACE_DEBUG((LM_ERROR, "%s Couldn't find intCmts\n", myName));
  }

  ACE_DEBUG((LM_MISC_DEBUG, "%s cmts=%d, start=%d, stop=%d\n",
                        myName, m_cmtsResId, (int)getWorkStartTime(),
                                             (int)getWorkEndTime()));

}



