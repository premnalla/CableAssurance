
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCASystemConfig.hpp"
#include "axHRMtaPingRunnableCollection.hpp"
#include "axWriteLockOperator.hpp"
#include "axGlobalData.hpp"
#include "axInternalCmts.hpp"
#include "axHRMtaPingTimer.hpp"
#include "axGlobalTimer.hpp"
#include "axMessageManager.hpp"
#include "axCountsAndAlarmMsg.hpp"
#include "axSubSystemCommon.hpp"
#include "axMessageDataTypes.hpp"
#include "axCountsDataTypes.hpp"
#include "axHighRateMtaPingRunnable.hpp"
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
axHRMtaPingRunnableCollection::axHRMtaPingRunnableCollection()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHRMtaPingRunnableCollection::~axHRMtaPingRunnableCollection()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHRMtaPingRunnableCollection::axHRMtaPingRunnableCollection(
    AX_UINT32 cmtsResId) :
  m_cmtsResId(cmtsResId)
{
}


//********************************************************************
// method : hashCode
//********************************************************************
AX_INT64
axHRMtaPingRunnableCollection::hashCode(void)
{
  return ((AX_INT64) m_cmtsResId);
}


//********************************************************************
// method : hashUInt32
//********************************************************************
AX_UINT32
axHRMtaPingRunnableCollection::hashUInt32(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method :
//********************************************************************
axAbstractCARunnable *
axHRMtaPingRunnableCollection::createNewRunnable(void)
{
  axAbstractCARunnable * ret;

  axIntCmtsKey_t cmtsKey(m_cmtsResId);
  axInternalCmts * intCmts =
                      axGlobalData::getInstance()->findCmts(cmtsKey);

  if (intCmts) {
    ret = new axHighRateMtaPingRunnable(this, intCmts);
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axHRMtaPingRunnableCollection::canScheduleRunnables(void)
{
  // static const char * myName="canSchedMtaPingRbls:";

  bool ret = true;

  axCATaskCoordHelper * tc = axCATaskCoordHelper::getInstance();

  axWriteLockOperator tcWLock(tc->getLock());

  if (tc->canDoMtaPing()) {
    tc->setMtaPing();
  } else {
    ret = false;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axHRMtaPingRunnableCollection::postponeTask(void)
{
  // static const char * myName="postponeMtaPing:";

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  struct timeval currTm;
  gettimeofday(&currTm, NULL);

  axIntCmtsKey_t cmtsKey;
  cmtsKey.resId = m_cmtsResId;

  axInternalCmts * intCmts =
                       axGlobalData::getInstance()->findCmts(cmtsKey);

  time_t timeToStartPolling = currTm.tv_sec +
                                 sysCfg->get_task_postpone_interval();

  axHRMtaPingTimer * nextPoll = new
                        axHRMtaPingTimer(intCmts, timeToStartPolling);
  axGlobalTimer * timer = axGlobalTimer::getInstance();
  timer->add(nextPoll);
}


//********************************************************************
// method:
//********************************************************************
bool
axHRMtaPingRunnableCollection::scheduleRunnables(void)
{
  static const char * myName="mtaPingRCSched:";

  bool ret;

  ACE_DEBUG((LM_PING_DEBUG, "%s begin cmts=%d entry\n",
                                                myName, m_cmtsResId));

  ret = axSimpleRunnableCollection::scheduleRunnables();

  return (ret);
}


//********************************************************************
// method: 
//********************************************************************
void
axHRMtaPingRunnableCollection::processRunnableComplete(
                                              axAbstractRunnable * in)
{
  static const char * myName="mtaPingRblComplete:";

  ACE_DEBUG((LM_PING_DEBUG, "%s entry\n", myName));

  axCAScheduler * scheduler = axCAScheduler::getInstance();

  axAbstractCARunnable * caR = dynamic_cast<axAbstractCARunnable *> (in);
  axAbstractCARunnable * nextRbl = getNextRunnable(caR);

  if (!nextRbl) {

    axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

    axWriteLockOperator wLockCollection(getLock());

    struct timeval tm;
    gettimeofday(&tm, NULL);
    setWorkEndTime(tm.tv_sec);

    clearInProgress();

    /*
     * Send message to do counts and alarm detection
     */
    axCountsAndAlarmsPayload_s * p1 = 
                                     new axCountsAndAlarmsPayload_s();
    p1->cmtsResId = m_cmtsResId;
    p1->countsType = AX_COUNTS_TYPE_MTA;
    axMessagePayload_s * p = new axMessagePayload_s();
    p->specificPayloadData = p1;
    axCountsAndAlarmMsg * msg =
                       new axCountsAndAlarmMsg(AX_SS_TYPE_MTA_PING, p);
    axMessageManager::getInstance()->sendMessage(msg);

    axIntCmtsKey_t cmtsKey;
    cmtsKey.resId = m_cmtsResId;

    axInternalCmts * intCmts = 
                      axGlobalData::getInstance()->findCmts(cmtsKey);

    if (intCmts) {

      axWriteLockOperator cmtsWlock(intCmts->getLock());

      axIntCmtsNonkey_t * nk = (axIntCmtsNonkey_t *)
                                                 intCmts->getNonKey();
      nk->mtaPing = 1;

      if (!intCmts->isDeleted()) {

        time_t timeToStartPolling = getWorkStartTime() + 
                                      sysCfg->get_mta_ping_interval();

        if ((timeToStartPolling-tm.tv_sec) < 0) {
          timeToStartPolling = tm.tv_sec + (1*60);
          ACE_DEBUG((LM_WARNING, "%s ping overrun\n", myName));
        }

        axHRMtaPingTimer * nextPoll = new 
                        axHRMtaPingTimer(intCmts, timeToStartPolling);
        axGlobalTimer * timer = axGlobalTimer::getInstance();
        timer->add(nextPoll);

        setNextCycleScheduled();

      }

    } else {
      ACE_DEBUG((LM_WARNING, "%s Couldn't find intCmts\n", myName));
    }

    ACE_DEBUG((LM_PING_DEBUG, "%s end cmts=%d, start=%d, stop=%d\n",
                        myName, m_cmtsResId, (int)getWorkStartTime(),
                                             (int)getWorkEndTime()));
  } else {

    ACE_DEBUG((LM_PING_DEBUG, "%s scheduling next rbl; cmts=%d\n",
                                                   myName, m_cmtsResId));

    scheduler->enQRunnable(nextRbl);

  }

  ACE_DEBUG((LM_PING_DEBUG, "%s exit\n", myName));
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axHRMtaPingRunnableCollection::getMaxDevicesPerRunnable(void)
{
  return (axCASystemConfig::getInstance()->getMtaPerPingRunnable());
}


