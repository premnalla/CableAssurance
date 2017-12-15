
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axSubSystemCommon.hpp"
#include "axHfcAlarmDetectionRunnable.hpp"
#include "axCAScheduler.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axHfcCounts.hpp"
#include "axCmtsCounts.hpp"
#include "axHfcMtaCounts.hpp"
#include "axCmtsMtaCounts.hpp"
#include "axCountsDataTypes.hpp"
#include "axAvlTree.hpp"
#include "axIteratorHolder.hpp"
#include "axInternalGenMta.hpp"
#include "axMtaAlarmUnavail.hpp"
#include "axAlarmProcessingMsg.hpp"
#include "axMessageManager.hpp"

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
axHfcAlarmDetectionRunnable::axHfcAlarmDetectionRunnable()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcAlarmDetectionRunnable::~axHfcAlarmDetectionRunnable()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcAlarmDetectionRunnable::axHfcAlarmDetectionRunnable(
      axAbstractCARunnableCollection * rc, axInternalCmts * intCmts) :
  axAbstractHfcAlarmDetectionRun(rc, intCmts)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axHfcAlarmDetectionRunnable::run(void)
{
  static const char * myName="hfcAlarmDetectRblRun:";

  AX_INT32 ret = 0;

  pthread_t tid = pthread_self();

  axInternalCmts * intCmts = getInternalCmts();

  ACE_DEBUG((LM_MISC_DEBUG, "%s entry. tid=%d, cmts=%d...\n", myName,
                                          tid, intCmts->getResId()));

  axCmtsCounts cmtsCounts(intCmts);
  cmtsCounts.run();

  axHfcCounts hfcCounts(intCmts);
  hfcCounts.preSoakAlarmDetermination();

  /*
   * Have to do another pass of MTA's belonging to this CMTS to
   * figure out if individual MTA alarms need to be generated based
   * on the 'justBecameUnavailable' flag
   */

  postCheckForMtaAlarms();

  ACE_DEBUG((LM_MISC_DEBUG, "%s entry. tid=%d, cmts=%d...\n", myName,
                                          tid, intCmts->getResId()));

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarmDetectionRunnable::nextAction(void)
{
  axAbstractCARunnableCollection * rc = getRunnableCollection();

  rc->processRunnableComplete(this);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarmDetectionRunnable::addSubject(axObject * o)
{
}


