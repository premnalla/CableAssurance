
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcMtaCounts.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axAvlIterator.hpp"
#include "axAbstractIterator.hpp"
#include "axInternalCm.hpp"
#include "axInternalGenMta.hpp"
#include "axGenericIntegerHolder.hpp"
#include "axListIterator.hpp"
#include "axIteratorHolder.hpp"
#include "axHfcAlarmPercent.hpp"
#include "axHfcAlarmMtaCount.hpp"
#include "axDbHfcCurrentCounts.hpp"
#include "axDbHfcCountsLog.hpp"
#include "axAlarmProcessingMsg.hpp"
#include "axMessageManager.hpp"
#include "axSubSystemCommon.hpp"
#include "axHfcAlarmDetection.hpp"
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
axHfcMtaCounts::axHfcMtaCounts() :
  axHfcCounts(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcMtaCounts::~axHfcMtaCounts()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcMtaCounts::axHfcMtaCounts(axInternalCmts * c) :
  axHfcCounts(c)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcMtaCounts::preSoakAlarmDetermination(void)
{
  bool ret = true;

  time_t  mtaCountAlarmDetStartTm;

  mtaCountAlarmDetStartTm = 0;

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  axInternalCmts * intCmts = getInternalCmts();

  struct timeval tm;
  gettimeofday(&tm, NULL);

  {
    axReadLockOperator cmtsWlock(intCmts->getLock());

    if (!intCmts->canDoCounts()) {
      goto EXIT_LABEL;
    }

  }

  {
    synchronizedTable_t * syncHfcTable = intCmts->getHfcTable();

    axReadLockOperator cmtsHfcRlock(syncHfcTable->lock);

    axAvlTree * hfcTbl = dynamic_cast<axAvlTree *> (syncHfcTable->table);

    axIteratorHolder iH(hfcTbl->createIterator());
    axAbstractIterator * iter = iH.getIterator();
    axInternalHfc * intHfc = dynamic_cast<axInternalHfc *> 
                                                    (iter->getFirst());

    while (intHfc) {

      axWriteLockOperator hfcRLock(intHfc->getLock());

      if (intHfc->hasData()) {

        axIntMtaCounts_t mtaCounts;
        axSortedList     allBadMtas(true);
        axListBase       alarmCausingMtaDevs;

        axIntHfcNonkey_t * hfcNonkey = (axIntHfcNonkey_t *)
                                                  intHfc->getNonKey();

        // mtaCountAlarmDetStartTm = hfcNonkey->nextPercentAlmStartTm;

        axHfcAlarmDetection hfcAlarmDetect(intHfc);

        hfcAlarmDetect.determineMtaCounts(allBadMtas, mtaCounts);

        bool updateCounts = false;

        if (hfcNonkey->currentCounts.mta != mtaCounts) {
          updateCounts = true;
          hfcNonkey->previousCounts.mta = hfcNonkey->currentCounts.mta;
          hfcNonkey->currentCounts.mta = mtaCounts;
          hfcNonkey->currentCounts.lastLogTime = tm.tv_sec;
        }

        if (!updateCounts) {
          updateCounts = needToUpdate(&tm, &hfcNonkey->currentCounts);
        }

        if (updateCounts) {

          axDbHfcCurrentCounts currCounts(intCmts->getResId(),
            tm.tv_sec, &hfcNonkey->currentCounts);
          axDbHfcCountsLog countsLog(intCmts->getResId(),
            tm.tv_sec, &hfcNonkey->currentCounts);

          if (!currCounts.updateRow()) {
            currCounts.insertRow();
          }

          countsLog.insertRow();

          bool isMtaCountAlarm;

          hfcAlarmDetect.setAlarmDetectionStartTime(
                                               mtaCountAlarmDetStartTm);
          hfcAlarmDetect.setAlarmThreshold(
            sysCfg->get_hfc_mta_unavail_alarm_threshold(mtaCounts.total));
          hfcAlarmDetect.setAlarmDetectionWindow(
                  sysCfg->get_hfc_mta_unavail_alarm_detection_window());
          isMtaCountAlarm = hfcAlarmDetect.determineMtaCountAlarm(
                        allBadMtas, mtaCounts, alarmCausingMtaDevs);
         
          if (isMtaCountAlarm) {

            axHfcAlarmMtaCount * alarm = new axHfcAlarmMtaCount(
                                           intCmts->getResId(), intHfc);

            alarm->preSoakAddChildren(&alarmCausingMtaDevs);
            alarm->setAlarmThreshold(
              sysCfg->get_hfc_mta_unavail_alarm_threshold(mtaCounts.total));
            alarm->setAlarmDetectionWindow(
                  sysCfg->get_hfc_mta_unavail_alarm_detection_window());
            alarm->setAlarmSoakWindow(
              sysCfg->get_hfc_mta_unavail_soak_win_duration(
                                       alarm->getAlarmDetectionTime()));
            alarm->setPreSoakDeviceTotal(mtaCounts.total);
            alarm->setPreSoakDeviceChange(alarmCausingMtaDevs.size());

            axAlarmProcessingMsg * msg = new axAlarmProcessingMsg(
                                      AX_SS_TYPE_COUNTS_ALARMS, alarm);
            axMessageManager::getInstance()->sendMessage(msg);

          }

        }

      }

      // advance
      intHfc = dynamic_cast<axInternalHfc *> (iter->getNext());
    }

  }

EXIT_LABEL:

  return (ret);
}

