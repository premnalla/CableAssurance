
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCmtsCmPerfPollRunnableCollection.hpp"
#include "axWriteLockOperator.hpp"
#include "axAbstractCARunnable.hpp"
#include "axGlobalData.hpp"
#include "axGlobalCmtsTable.hpp"
#include "axReadLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axInternalCmts.hpp"
// #include "axCmtsPollTimer.hpp"
#include "axGlobalTimer.hpp"
#include "axCmtsCmPerfPollTimer.hpp"
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
axCmtsCmPerfPollRunnableCollection::axCmtsCmPerfPollRunnableCollection()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmtsCmPerfPollRunnableCollection::~axCmtsCmPerfPollRunnableCollection()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmtsCmPerfPollRunnableCollection::axCmtsCmPerfPollRunnableCollection(
    AX_UINT32 cmtsResId) :
  m_cmtsResId(cmtsResId)
{
}


//********************************************************************
// method : hashCode
//********************************************************************
AX_INT64
axCmtsCmPerfPollRunnableCollection::hashCode(void)
{
  return ((AX_INT64) m_cmtsResId);
}


//********************************************************************
// method : hashUInt32
//********************************************************************
AX_UINT32
axCmtsCmPerfPollRunnableCollection::hashUInt32(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method :
//********************************************************************
axAbstractCARunnable *
axCmtsCmPerfPollRunnableCollection::createNewRunnable(void)
{
  axAbstractCARunnable * ret = NULL;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axCmtsCmPerfPollRunnableCollection::canScheduleRunnables(void)
{
  // static const char * myName="canSchedCmtsPollRbls:";

  bool ret = true;

#if 0
  axCATaskCoordHelper * tc = axCATaskCoordHelper::getInstance();

  axWriteLockOperator tcWLock(tc->getLock());

  if (tc->canDoCmtsPoll()) {
    tc->setCmtsPoll();
  } else {
    ret = false;
  }
#endif

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axCmtsCmPerfPollRunnableCollection::postponeTask(void)
{
  // static const char * myName="postponeCmtsPoll:";

#if 0
  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  struct timeval currTm;
  gettimeofday(&currTm, NULL);

  axIntCmtsKey_t cmtsKey;
  cmtsKey.resId = m_cmtsResId;

  axInternalCmts * intCmts =
                       axGlobalData::getInstance()->findCmts(cmtsKey);

  time_t timeToStartPolling = currTm.tv_sec +
                                 sysCfg->get_task_postpone_interval();

  axCmtsCmPerfPollTimer * nextPoll =
               new axCmtsCmPerfPollTimer(intCmts, timeToStartPolling);
  axGlobalTimer * timer = axGlobalTimer::getInstance();
  timer->add(nextPoll);
#endif

}


//********************************************************************
// method: processRunnableComplete
//********************************************************************
void
axCmtsCmPerfPollRunnableCollection::processRunnableComplete(
                                              axAbstractRunnable * in)
{

  if (areAllRunnablesComplete()) {

    axWriteLockOperator wLockCollection(getLock());

    struct timeval tm;
    gettimeofday(&tm, NULL);
    setWorkEndTime(tm.tv_sec);

    clearInProgress();

    // schedule next cycle...
    if (!isNextCycleScheduled()) {

      axIntCmtsKey_t cmtsKey;
      cmtsKey.resId = m_cmtsResId;

      axInternalCmts * intCmts = 
                      axGlobalData::getInstance()->findCmts(cmtsKey);

      if (intCmts) {

        // printf("Found intCmts\n");

        axReadLockOperator cmtsRlock(intCmts->getLock());

        if (!intCmts->isDeleted()) {

          time_t timeToStartPolling = getKickoffTime() + (rand() %10);
          if ((timeToStartPolling-tm.tv_sec) < 0) {
            timeToStartPolling = tm.tv_sec + (2*60);
            printf("Poll took longer than cycle time\n");
          }

          axCmtsCmPerfPollTimer * nextPoll = 
             new axCmtsCmPerfPollTimer(intCmts, timeToStartPolling);
          axGlobalTimer * timer = axGlobalTimer::getInstance();
          timer->add(nextPoll);

          setNextCycleScheduled();

        }

      } else {
        printf("Couldn't find intCmts\n");
      }

    } else {
      // printf("Next cycle already scheduled \n");
    }

  }

}



