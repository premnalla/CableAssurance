
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCASystemConfig.hpp"
#include "axHRMtaPollRunnableCollection.hpp"
#include "axWriteLockOperator.hpp"
#include "axGlobalData.hpp"
#include "axInternalCmts.hpp"
#include "axHRMtaPollTimer.hpp"
#include "axGlobalTimer.hpp"
#include "axMessageManager.hpp"
#include "axCountsAndAlarmMsg.hpp"
#include "axSubSystemCommon.hpp"
#include "axMessageDataTypes.hpp"
#include "axCountsDataTypes.hpp"
#include "axHighRateMtaPollRunnable.hpp"
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
axHRMtaPollRunnableCollection::axHRMtaPollRunnableCollection()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHRMtaPollRunnableCollection::~axHRMtaPollRunnableCollection()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHRMtaPollRunnableCollection::axHRMtaPollRunnableCollection(
    AX_UINT32 cmtsResId) :
  m_cmtsResId(cmtsResId)
{
}


//********************************************************************
// method : hashCode
//********************************************************************
AX_INT64
axHRMtaPollRunnableCollection::hashCode(void)
{
  return ((AX_INT64) m_cmtsResId);
}


//********************************************************************
// method : hashUInt32
//********************************************************************
AX_UINT32
axHRMtaPollRunnableCollection::hashUInt32(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method :
//********************************************************************
axAbstractCARunnable *
axHRMtaPollRunnableCollection::createNewRunnable(void)
{
  axAbstractCARunnable * ret;

  axIntCmtsKey_t cmtsKey(m_cmtsResId);
  axInternalCmts * intCmts =
                      axGlobalData::getInstance()->findCmts(cmtsKey);

  if (intCmts) {
    ret = new axHighRateMtaPollRunnable(this, intCmts);
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axHRMtaPollRunnableCollection::canScheduleRunnables(void)
{
  // static const char * myName="canSchedCmtsPollRbls:";

  bool ret = true;

  axCATaskCoordHelper * tc = axCATaskCoordHelper::getInstance();

  axWriteLockOperator tcWLock(tc->getLock());

  if (tc->canDoMtaPoll()) {
    tc->setMtaPoll();
  } else {
    ret = false;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axHRMtaPollRunnableCollection::postponeTask(void)
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

  axHRMtaPollTimer * nextPoll = new
                        axHRMtaPollTimer(intCmts, timeToStartPolling);
  axGlobalTimer * timer = axGlobalTimer::getInstance();
  timer->add(nextPoll);
}


//********************************************************************
// method:
//********************************************************************
bool
axHRMtaPollRunnableCollection::scheduleRunnables(void)
{
  static const char * myName="mtaPollRCSched:";

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
axHRMtaPollRunnableCollection::processRunnableComplete(
                                              axAbstractRunnable * in)
{
  static const char * myName="mtaPollRblComplete:";

  ACE_DEBUG((LM_POLL_DEBUG, "%s entry, id=%d\n", myName,
                                                   in->hashUInt32()));

  axCAScheduler * scheduler = axCAScheduler::getInstance();

  axAbstractCARunnable * caR = dynamic_cast<axAbstractCARunnable *> 
                                                                 (in);
  axAbstractCARunnable * nextRbl = getNextRunnable(caR);

  if (!nextRbl) {

    axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

    axWriteLockOperator rwCmtsPollRc(getLock());

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
                       new axCountsAndAlarmMsg(AX_SS_TYPE_MTA_POLL, p);
    axMessageManager::getInstance()->sendMessage(msg);

    axIntCmtsKey_t cmtsKey;
    cmtsKey.resId = m_cmtsResId;

    axInternalCmts * intCmts = 
                       axGlobalData::getInstance()->findCmts(cmtsKey);

    if (intCmts) {

      axWriteLockOperator cmtsWlock(intCmts->getLock());

      axIntCmtsNonkey_t * nk = (axIntCmtsNonkey_t *)
                                                 intCmts->getNonKey();
      nk->mtaPoll = 1;

      if (!intCmts->isDeleted()) {

        time_t timeToStartPolling = getWorkStartTime() + 
                                      sysCfg->get_mta_poll_interval();

        if ((timeToStartPolling-tm.tv_sec) < 0) {
          timeToStartPolling = tm.tv_sec + (1*60);
          ACE_DEBUG((LM_WARNING, "%s poll overrun\n", myName));
        }

        axHRMtaPollTimer * nextPoll = new 
                        axHRMtaPollTimer(intCmts, timeToStartPolling);
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

  ACE_DEBUG((LM_POLL_DEBUG, "%s exit, id=%d\n", myName,
                                                   in->hashUInt32()));
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axHRMtaPollRunnableCollection::getMaxDevicesPerRunnable(void)
{
  return (axCASystemConfig::getInstance()->getMtaPerPollRunnable());
}


