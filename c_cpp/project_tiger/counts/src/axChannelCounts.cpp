
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axChannelCounts.hpp"
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
// #include "axHfcAlarmPercent.hpp"
// #include "axHfcAlarmMtaCount.hpp"
#include "axDbChannelCurrentCounts.hpp"
#include "axDbChannelCountsLog.hpp"
#include "axAlarmProcessingMsg.hpp"
#include "axMessageManager.hpp"
#include "axSubSystemCommon.hpp"
#include "axChannelAlarmDetection.hpp"
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
axChannelCounts::axChannelCounts()
{
}


//********************************************************************
// destructor:
//********************************************************************
axChannelCounts::~axChannelCounts()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axChannelCounts::axChannelCounts(axInternalCmts * c) :
  axCorrelatedCounts(c)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axChannelCounts::preSoakAlarmDetermination(void)
{
  bool ret = true;

  time_t  cmPercentAlarmDetStartTm;
  // time_t  cmPercentAlarmWindow;
  // time_t  cmPercentAlarmSoakWindow;
  // int     cmPercentAlarmThreshold;

  time_t  mtaCountAlarmDetStartTm;
  // time_t  mtaCountAlarmWindow;
  // time_t  mtaCountAlarmSoakWindow;
  // int     mtaCountAlarmThreshold;

  cmPercentAlarmDetStartTm = 0;
  // cmPercentAlarmWindow = 60 * 10; // 10 minutes
  // cmPercentAlarmSoakWindow = 60 * 15;  // 15 minutes
  // cmPercentAlarmThreshold = 30;  // 30%

  mtaCountAlarmDetStartTm = 0;
  // mtaCountAlarmWindow = 60 * 10; // 10 minutes;
  // mtaCountAlarmSoakWindow = 60 * 15;  // 15 minutes;
  // mtaCountAlarmThreshold = 3;   // 3 unavailable MTA's in 10 mimutes

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
    synchronizedTable_t * syncChnlTbl = intCmts->getChannelTable();

    axReadLockOperator cmtsChnlRlock(syncChnlTbl->lock);

    axAvlTree * chnlTbl = dynamic_cast<axAvlTree *>
                                            (syncChnlTbl->table);

    axIteratorHolder iH(chnlTbl->createIterator());
    axAbstractIterator * iter = iH.getIterator();
    axInternalChannel * intChnl = dynamic_cast<axInternalChannel *>
                                                    (iter->getFirst());

    while (intChnl) {

      axWriteLockOperator chnlRLock(intChnl->getLock());

      if (intChnl->hasData()) {

        axIntCmCounts_t  cmCounts;
        axIntMtaCounts_t mtaCounts;
        axSortedList     allBadCms(true);
        axSortedList     allBadMtas(true);
        axListBase       alarmCausingCmDevs;
        axListBase       alarmCausingMtaDevs;

        axIntChannelNonkey_t * chnlNk = (axIntChannelNonkey_t *)
                                                  intChnl->getNonKey();

        /* Yes, it is the Same! */
        // cmPercentAlarmDetStartTm = chnlNk->nextPercentAlmStartTm;
        // mtaCountAlarmDetStartTm = chnlNk->nextPercentAlmStartTm;

        axChannelAlarmDetection chnlAlarmDetect(intChnl);

        chnlAlarmDetect.determineCmCounts(allBadCms, cmCounts);
        chnlAlarmDetect.determineMtaCounts(allBadMtas, mtaCounts);

        bool updateCounts = false;

        if (chnlNk->currentCounts.mta != mtaCounts) {
          updateCounts = true;
          chnlNk->previousCounts.mta = chnlNk->currentCounts.mta;
          chnlNk->currentCounts.mta = mtaCounts;
          chnlNk->currentCounts.lastLogTime = tm.tv_sec;
        }

        if (chnlNk->currentCounts.cm != cmCounts) {
          updateCounts = true;
          chnlNk->previousCounts.cm = chnlNk->currentCounts.cm;
          chnlNk->currentCounts.cm = cmCounts;
          chnlNk->currentCounts.lastLogTime = tm.tv_sec;
        }

        if (!updateCounts) {
          updateCounts = needToUpdate(&tm, &chnlNk->currentCounts);
        }

        if (updateCounts) {

          axDbChannelCurrentCounts currCounts(intCmts->getResId(),
                                      tm.tv_sec, &chnlNk->currentCounts);
          axDbChannelCountsLog countsLog(intCmts->getResId(),
                                      tm.tv_sec, &chnlNk->currentCounts);

          // if (!currCounts.updateRow()) {
          //   currCounts.insertRow();
          // }

          currCounts.insertOrUpdateRow();
          countsLog.insertRow();

          bool isMtaCountAlarm, isCmPercentAlarm;

          chnlAlarmDetect.setAlarmDetectionStartTime(
                                            cmPercentAlarmDetStartTm);
          chnlAlarmDetect.setAlarmThreshold(
            sysCfg->get_hfc_cm_offline_alarm_threshold(cmCounts.total));
             // sysCfg->get_cm_offline_alarm_threshold(cmCounts.total));
          chnlAlarmDetect.setAlarmDetectionWindow(
                   sysCfg->get_hfc_cm_offline_alarm_detection_window());
                            // sysCfg->get_cm_offline_alarm_window_1());
          isCmPercentAlarm = chnlAlarmDetect.determineCmPecentAlarm(
                           allBadCms, cmCounts, alarmCausingCmDevs);

          chnlAlarmDetect.setAlarmDetectionStartTime(
                                              mtaCountAlarmDetStartTm);
          chnlAlarmDetect.setAlarmThreshold(
            sysCfg->get_hfc_mta_unavail_alarm_threshold(
                                                      mtaCounts.total));
                    // sysCfg->get_mta_unavailable_alarm_threshold_1());
          chnlAlarmDetect.setAlarmDetectionWindow(
                  sysCfg->get_hfc_mta_unavail_alarm_detection_window());
                       // sysCfg->get_mta_unavailable_alarm_window_1());
          isMtaCountAlarm = chnlAlarmDetect.determineMtaCountAlarm(
                        allBadMtas, mtaCounts, alarmCausingMtaDevs);

          if (isCmPercentAlarm) {

#if 0
            axHfcAlarmPercent * alarm = new axHfcAlarmPercent(
                                        intCmts->getResId(), intChnl);

            alarm->preSoakAddChildren(&alarmCausingCmDevs);
            alarm->setAlarmThreshold(
              sysCfg->get_cm_offline_alarm_threshold(cmCounts.total));
            alarm->setAlarmDetectionWindow(cmPercentAlarmWindow);
            alarm->setAlarmSoakWindow(cmPercentAlarmSoakWindow);
            alarm->setPreSoakDeviceTotal(cmCounts.total);
            alarm->setPreSoakDeviceChange(alarmCausingCmDevs.size());

            axAlarmProcessingMsg * msg = new axAlarmProcessingMsg(
                                      AX_SS_TYPE_COUNTS_ALARMS, alarm);

            axMessageManager::getInstance()->sendMessage(msg);
#endif

          }

          if (isMtaCountAlarm) {

#if 0
            axHfcAlarmMtaCount * alarm = new axHfcAlarmMtaCount(
                                           intCmts->getResId(), intChnl);

            alarm->preSoakAddChildren(&alarmCausingMtaDevs);
            alarm->setAlarmThreshold(
                        sysCfg->get_mta_unavailable_alarm_threshold_1());
            alarm->setAlarmDetectionWindow(mtaCountAlarmWindow);
            alarm->setAlarmSoakWindow(mtaCountAlarmSoakWindow);
            alarm->setPreSoakDeviceTotal(mtaCounts.total);
            alarm->setPreSoakDeviceChange(alarmCausingMtaDevs.size());

            axAlarmProcessingMsg * msg = new axAlarmProcessingMsg(
                                      AX_SS_TYPE_COUNTS_ALARMS, alarm);
            axMessageManager::getInstance()->sendMessage(msg);
#endif

          }

        }

      }

      // advance
      intChnl = dynamic_cast<axInternalChannel *> (iter->getNext());
    }

  }

EXIT_LABEL:

  return (ret);
}



