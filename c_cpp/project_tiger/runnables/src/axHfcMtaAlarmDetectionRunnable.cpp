
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axSubSystemCommon.hpp"
#include "axHfcMtaAlarmDetectionRunnable.hpp"
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
axHfcMtaAlarmDetectionRunnable::axHfcMtaAlarmDetectionRunnable()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcMtaAlarmDetectionRunnable::~axHfcMtaAlarmDetectionRunnable()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcMtaAlarmDetectionRunnable::axHfcMtaAlarmDetectionRunnable(
      axAbstractCARunnableCollection * rc, axInternalCmts * intCmts) :
  axAbstractHfcAlarmDetectionRun(rc, intCmts)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axHfcMtaAlarmDetectionRunnable::run(void)
{
  AX_INT32 ret = 0;

  ACE_DEBUG((LM_MISC_DEBUG, "axHfcMtaAlarmDetectionRunnable::run() entry...\n"));

  axInternalCmts * intCmts = getInternalCmts();

  axCmtsMtaCounts cmtsCounts(intCmts);
  cmtsCounts.run();

  axHfcMtaCounts hfcCounts(intCmts);
  hfcCounts.preSoakAlarmDetermination();

  /*
   * Have to do another pass of MTA's belonging to this CMTS to
   * figure out if individual MTA alarms need to be generated based
   * on the 'justBecameUnavailable' flag
   */

  postCheckForMtaAlarms();

  ACE_DEBUG((LM_MISC_DEBUG, "axHfcMtaAlarmDetectionRunnable::run() exit...\n"));

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcMtaAlarmDetectionRunnable::nextAction(void)
{
  axAbstractCARunnableCollection * rc = getRunnableCollection();

  rc->processRunnableComplete(this);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcMtaAlarmDetectionRunnable::addSubject(axObject * o)
{
}

