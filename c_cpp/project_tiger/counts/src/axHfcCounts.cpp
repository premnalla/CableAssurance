
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axHfcCounts.hpp"
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
#include "axHfcAlarmUtils.hpp"

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
axHfcCounts::axHfcCounts()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcCounts::~axHfcCounts()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcCounts::axHfcCounts(axInternalCmts * c) :
  axCorrelatedCounts(c)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcCounts::preSoakAlarmDetermination(void)
{
  bool ret = true;

  time_t  cmPercentAlarmDetStartTm;
  time_t  mtaCountAlarmDetStartTm;

  cmPercentAlarmDetStartTm = 0;
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

    axAvlTree * hfcTbl = dynamic_cast<axAvlTree *> 
                                            (syncHfcTable->table);

    axIteratorHolder iH(hfcTbl->createIterator());
    axAbstractIterator * iter = iH.getIterator();

    axInternalHfc * intHfc;
    for (intHfc=dynamic_cast<axInternalHfc *>(iter->getFirst());
         intHfc;
         intHfc=dynamic_cast<axInternalHfc *>(iter->getNext())) {

      axWriteLockOperator hfcRLock(intHfc->getLock());

      if (intHfc->hasData()) {

        axIntCmCounts_t  cmCounts;
        axIntMtaCounts_t mtaCounts;
        axSortedList     allBadCms(true);
        axSortedList     allBadMtas(true);
        axListBase       alarmCausingCmDevs;
        axListBase       alarmCausingMtaDevs;

        axIntHfcNonkey_t * hfcNonkey = (axIntHfcNonkey_t *)
                                                  intHfc->getNonKey();

        /* Yes, it is the Same! */
        // cmPercentAlarmDetStartTm = hfcNonkey->nextPercentAlmStartTm;
        // mtaCountAlarmDetStartTm = hfcNonkey->nextPercentAlmStartTm;

        axHfcAlarmDetection hfcAlarmDetect(intHfc);

        hfcAlarmDetect.determineCmCounts(allBadCms, cmCounts);
        hfcAlarmDetect.determineMtaCounts(allBadMtas, mtaCounts);

        bool updateCounts = false;

        if (hfcNonkey->currentCounts.mta != mtaCounts) {
          updateCounts = true;
          hfcNonkey->previousCounts.mta = hfcNonkey->currentCounts.mta;
          hfcNonkey->currentCounts.mta = mtaCounts;
          hfcNonkey->currentCounts.lastLogTime = tm.tv_sec;
        }

        if (hfcNonkey->currentCounts.cm != cmCounts) {
          updateCounts = true;
          hfcNonkey->previousCounts.cm = hfcNonkey->currentCounts.cm;
          hfcNonkey->currentCounts.cm = cmCounts;
          hfcNonkey->currentCounts.lastLogTime = tm.tv_sec;
        }

        if (!updateCounts) {
          updateCounts = needToUpdate(&tm, &hfcNonkey->currentCounts);
        }

        bool dbLogged = false;

        if (updateCounts) {

          axDbHfcCurrentCounts currCounts(intCmts->getResId(),
            tm.tv_sec, &hfcNonkey->currentCounts);
          axDbHfcCountsLog countsLog(intCmts->getResId(),
            tm.tv_sec, &hfcNonkey->currentCounts);

          currCounts.insertOrUpdateRow();
          countsLog.insertRow();

          bool isMtaCountAlarm, isCmPercentAlarm;

          /*
           * Setup and Determine CM % offline Alarms
           */
          hfcAlarmDetect.setAlarmDetectionStartTime(
                                            cmPercentAlarmDetStartTm);
          hfcAlarmDetect.setAlarmThreshold(
               sysCfg->get_hfc_cm_offline_alarm_threshold(
                                                      cmCounts.total));
          hfcAlarmDetect.setAlarmDetectionWindow(
                  sysCfg->get_hfc_cm_offline_alarm_detection_window());
          isCmPercentAlarm = hfcAlarmDetect.determineCmPecentAlarm(
                           allBadCms, cmCounts, alarmCausingCmDevs);

          /*
           * Setup and Determine MTA unavailable counts Alarms
           */
          hfcAlarmDetect.setAlarmDetectionStartTime(
                                              mtaCountAlarmDetStartTm);
          hfcAlarmDetect.setAlarmThreshold(
            sysCfg->get_hfc_mta_unavail_alarm_threshold(
                                                     mtaCounts.total));
          hfcAlarmDetect.setAlarmDetectionWindow(
                 sysCfg->get_hfc_mta_unavail_alarm_detection_window());
          isMtaCountAlarm = hfcAlarmDetect.determineMtaCountAlarm(
                        allBadMtas, mtaCounts, alarmCausingMtaDevs);
         
          if (isCmPercentAlarm && !hfcNonkey->percentAlarm) {

            /*
             * DB Log and Update Internal mem
             */
            axHfcAlarmUtils::NewAlarmUpdateWork(true, tm.tv_sec, 
                                                            hfcNonkey);

            dbLogged = true;

            axHfcAlarmPercent * alarm = new axHfcAlarmPercent(
                                        intCmts->getResId(), intHfc);

            alarm->preSoakAddChildren(&alarmCausingCmDevs);
            alarm->setAlarmThreshold(
                sysCfg->get_hfc_cm_offline_alarm_threshold(
                                                    cmCounts.total));
            alarm->setAlarmDetectionWindow(
                sysCfg->get_hfc_cm_offline_alarm_detection_window());
            alarm->setAlarmSoakWindow(
                sysCfg->get_hfc_cm_offline_soak_win_duration(
                                    alarm->getAlarmDetectionTime()));
            alarm->setPreSoakDeviceTotal(cmCounts.total);
            alarm->setPreSoakDeviceChange(alarmCausingCmDevs.size());

            axAlarmProcessingMsg * msg = new axAlarmProcessingMsg(
                                      AX_SS_TYPE_COUNTS_ALARMS, alarm);

            axMessageManager::getInstance()->sendMessage(msg);

          } // if (isCmPercentAlarm)

          if (isMtaCountAlarm && !hfcNonkey->percentAlarm) {

            /*
             * DB Log and Update Internal mem
             */
            axHfcAlarmUtils::NewAlarmUpdateWork(true, tm.tv_sec,
                                                            hfcNonkey);

            dbLogged = true;

            axHfcAlarmMtaCount * alarm = new axHfcAlarmMtaCount(
                                         intCmts->getResId(), intHfc);

            alarm->preSoakAddChildren(&alarmCausingMtaDevs);
            alarm->setAlarmThreshold(
                sysCfg->get_hfc_mta_unavail_alarm_threshold(
                                                    mtaCounts.total));
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

          } // if (isMtaCountAlarm)

          /*
           * CLEAR alarm Updates to DB and internal mem
           */
          if (!isMtaCountAlarm && !isCmPercentAlarm &&
              hfcNonkey->percentAlarm) {
            /*
             * DB Log and Update Internal mem
             */
            axHfcAlarmUtils::NewAlarmUpdateWork(false, tm.tv_sec,
                                                            hfcNonkey);
            dbLogged = true;
          }

          /*
           * Every so often, log to db
           */
          if (!dbLogged) {
            /*
             * DB Log and Update Internal mem
             */
            axHfcAlarmUtils::LogAlarmWork(tm.tv_sec, hfcNonkey);
          }

        } // if (updateCounts)

      } // if (hasData)

    } // foreach HFC

  }

EXIT_LABEL:

  return (ret);
}


