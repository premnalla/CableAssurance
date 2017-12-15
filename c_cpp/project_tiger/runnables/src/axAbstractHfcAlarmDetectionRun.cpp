
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axSubSystemCommon.hpp"
#include "axAbstractHfcAlarmDetectionRun.hpp"
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
#include "axCASystemConfig.hpp"

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
axAbstractHfcAlarmDetectionRun::axAbstractHfcAlarmDetectionRun() :
  axAbstractCARunnable(NULL),
  m_intCmts(NULL)
{
  setPriority(HFC_OUTAGE_DETECT_PRIORITY);
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractHfcAlarmDetectionRun::~axAbstractHfcAlarmDetectionRun()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAbstractHfcAlarmDetectionRun::axAbstractHfcAlarmDetectionRun(
    axAbstractCARunnableCollection * rc, axInternalCmts * inCmts) :
  axAbstractCARunnable(rc),
  m_intCmts(inCmts)
{
  setPriority(HFC_OUTAGE_DETECT_PRIORITY);
}


//********************************************************************
// method:
//********************************************************************
axInternalCmts *
axAbstractHfcAlarmDetectionRun::getInternalCmts(void)
{
  return (m_intCmts);
}

//********************************************************************
// method:
//********************************************************************
void
axAbstractHfcAlarmDetectionRun::postCheckForMtaAlarms(void)
{
  /*
   * Have to do another pass of MTA's belonging to this CMTS to
   * figure out if individual MTA alarms need to be generated based
   * on the 'justBecameUnavailable' flag
   */

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  synchronizedTable_t * syncMtaTable = m_intCmts->getMtaTable();
  axAvlTree * mtaTbl = (axAvlTree *) syncMtaTable->table;
  axReadLockOperator mtaTblRlock(syncMtaTable->lock);
  axIteratorHolder iH(mtaTbl->createIterator());
  axAbstractIterator * mtaIter = iH.getIterator();
  axInternalGenMta * intMta;

  for (intMta = (axInternalGenMta *) mtaIter->getFirst();
       intMta;
       intMta = (axInternalGenMta *) mtaIter->getNext()) {

    axWriteLockOperator mtaRLock(intMta->getLock());

    if (!intMta->hasData()) {
      continue;
    }

    axIntGenMtaNonkey_t * nk = (axIntGenMtaNonkey_t *) 
                                                  intMta->getNonKey();

    if (!nk->justBecameUnavailable) {
      continue;
    }

    nk->justBecameUnavailable = 0;

    {
      axMtaAlarmUnavail * alarm = new 
               axMtaAlarmUnavail(m_intCmts->getResId(), intMta);
      // alarm->preSoakAddChildren(&alarmCausingMtaDevs);
      // alarm->setAlarmThreshold(mtaCountAlarmThreshold);
      // alarm->setAlarmDetectionWindow(mtaCountAlarmWindow);
      alarm->setAlarmSoakWindow(
                           sysCfg->get_mta_unavail_soak_win_duration(
                                     alarm->getAlarmDetectionTime()));
                // sysCfg->get_mta_unavailable_alarm_soak_window_1());
      // alarm->setPreSoakDeviceTotal(mtaCounts.total);
      // alarm->setPreSoakDeviceChange(alarmCausingMtaDevs.size());

      axAlarmProcessingMsg * msg = new axAlarmProcessingMsg(
                                     AX_SS_TYPE_COUNTS_ALARMS, alarm);
      axMessageManager::getInstance()->sendMessage(msg);
    }

  }

}


