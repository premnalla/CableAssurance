
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCASystemConfig.hpp"
#include "axCmPerfPollRunnableCollection.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axGlobalData.hpp"
#include "axInternalCmts.hpp"
#include "axCmPerfPollTimer.hpp"
#include "axGlobalTimer.hpp"
#include "axCmPerfPollRunnable.hpp"
#include "axCASystemConfig.hpp"
#include "axCAScheduler.hpp"
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

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCmPerfPollRunnableCollection::axCmPerfPollRunnableCollection()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmPerfPollRunnableCollection::~axCmPerfPollRunnableCollection()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmPerfPollRunnableCollection::axCmPerfPollRunnableCollection(
    AX_UINT32 cmtsResId) :
  m_cmtsResId(cmtsResId)
{
}


//********************************************************************
// method : hashCode
//********************************************************************
AX_INT64
axCmPerfPollRunnableCollection::hashCode(void)
{
  return ((AX_INT64) m_cmtsResId);
}


//********************************************************************
// method : hashUInt32
//********************************************************************
AX_UINT32
axCmPerfPollRunnableCollection::hashUInt32(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method : 
//********************************************************************
axAbstractCARunnable *
axCmPerfPollRunnableCollection::createNewRunnable(void)
{
  axAbstractCARunnable * ret;

  axIntCmtsKey_t cmtsKey(m_cmtsResId);
  axInternalCmts * intCmts =
                      axGlobalData::getInstance()->findCmts(cmtsKey);

  if (intCmts) {
    ret = new axCmPerfPollRunnable(this, intCmts);
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axCmPerfPollRunnableCollection::canScheduleRunnables(void)
{
  // static const char * myName="canSchedCmtsPollRbls:";

  bool ret = true;

  axCATaskCoordHelper * tc = axCATaskCoordHelper::getInstance();

  axWriteLockOperator tcWLock(tc->getLock());

  if (tc->canDoCmPoll()) {
    tc->setCmPoll();
  } else {
    ret = false;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axCmPerfPollRunnableCollection::postponeTask(void)
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

  axCmPerfPollTimer * nextPoll =
                   new axCmPerfPollTimer(intCmts, timeToStartPolling);
  axGlobalTimer * timer = axGlobalTimer::getInstance();
  timer->add(nextPoll);
}


//********************************************************************
// method:
//********************************************************************
bool
axCmPerfPollRunnableCollection::scheduleRunnables(void)
{
  static const char * myName="cmPerfRCSched:";

  bool ret;

  ACE_DEBUG((LM_POLL_DEBUG, "%s begin cmts=%d entry\n", 
                                                myName, m_cmtsResId));

  ret = axSimpleRunnableCollection::scheduleRunnables();

  return (ret);
}


//********************************************************************
// method: 
//********************************************************************
void
axCmPerfPollRunnableCollection::processRunnableComplete(
                                              axAbstractRunnable * in)
{
  static const char * myName="cmPerfRblComplete:";

  ACE_DEBUG((LM_POLL_DEBUG, "%s cmts=%d entry\n", myName, m_cmtsResId));

  axCAScheduler * scheduler = axCAScheduler::getInstance();

  axAbstractCARunnable * caR = 
                            dynamic_cast<axAbstractCARunnable *> (in);
  axAbstractCARunnable * nextRbl = getNextRunnable(caR);

  if (!nextRbl) {

    axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

    axWriteLockOperator rwCmtsPollRc(getLock());

    struct timeval currTm;
    gettimeofday(&currTm, NULL);
    setWorkEndTime(currTm.tv_sec);

    clearInProgress();

    axIntCmtsKey_t cmtsKey;
    cmtsKey.resId = m_cmtsResId;

    axInternalCmts * intCmts =
                      axGlobalData::getInstance()->findCmts(cmtsKey);

    if (intCmts) {

        axReadLockOperator cmtsRlock(intCmts->getLock());

        if (!intCmts->isDeleted()) {

          time_t timeToStartPolling = getWorkStartTime() +
                                      sysCfg->get_cm_poll_interval();

          if ((timeToStartPolling - currTm.tv_sec) < 0) {
            timeToStartPolling = currTm.tv_sec + (1*60);
            ACE_DEBUG((LM_WARNING, "%s poll overrun\n", myName));
          }

          axCmPerfPollTimer * nextPoll =
                 new axCmPerfPollTimer(intCmts, timeToStartPolling);
          axGlobalTimer * timer = axGlobalTimer::getInstance();
          timer->add(nextPoll);

          setNextCycleScheduled();

        }

    } else {
      ACE_DEBUG((LM_WARNING, "%s Couldn't find intCmts\n", myName));
    }

    ACE_DEBUG((LM_POLL_DEBUG, "%s end cmts=%d, start=%d, stop=%d\n",
                        myName, m_cmtsResId, (int)getWorkStartTime(),
                                             (int)getWorkEndTime()));
  } else {

    ACE_DEBUG((LM_POLL_DEBUG,
                 "%s scheduling next rbl; cmts=%d, id=%d\n",
                          myName, m_cmtsResId, nextRbl->hashUInt32()));

    scheduler->enQRunnable(nextRbl);

  }

  ACE_DEBUG((LM_POLL_DEBUG, "%s exit\n", myName));
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axCmPerfPollRunnableCollection::getMaxDevicesPerRunnable(void)
{
  return (axCASystemConfig::getInstance()->getCmPerPerfRunnable());
}


