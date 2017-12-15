
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axSubSystemCommon.hpp"
#include "axMtaAlarmOnBattery.hpp"
#include "axAlarmDataTypes.hpp"
#include "axHfcCurrentAlarmTable.hpp"
#include "axMtaCurrentAlarmTable.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axDbCAAlarmSecondary.hpp"
#include "axDbCAAlarmHistory.hpp"
#include "axDbCASoakingAlarm.hpp"
#include "axDbHfc.hpp"
#include "axHfcAlarmPower.hpp"
#include "axInternalObjList.hpp"
#include "axGlobalData.hpp"
#include "axHfcAlarmDetection.hpp"
#include "axAlarmProcessingMsg.hpp"
#include "axMessageManager.hpp"
#include "axSortedList.hpp"
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
axMtaAlarmOnBattery::axMtaAlarmOnBattery() :
  axMtaAlarm((INTDS_RESID_t)0, (AX_INT8 *)NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axMtaAlarmOnBattery::~axMtaAlarmOnBattery()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axMtaAlarmOnBattery::axMtaAlarmOnBattery(INTDS_RESID_t cmtsResId,
                                            axInternalGenMta * gMta) :
  axMtaAlarm(cmtsResId, gMta)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axMtaAlarmOnBattery::axMtaAlarmOnBattery(INTDS_RESID_t cmtsResId, 
                                                   AX_INT8 * mtaMac) :
  axMtaAlarm(cmtsResId, mtaMac)
{
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axMtaAlarmOnBattery::getAlarmSubType(void)
{
  return (AX_MTA_ALARM_ON_BATTERY);
}


//********************************************************************
// method:
// NOTES: Be really careful. There is plenty of opportunity for deadlock
//********************************************************************
bool
axMtaAlarmOnBattery::addAlarm(void)
{
  bool    ret = false;
  axDbHfc dbHfc;

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  axIntCmtsKey_t cmtsKey(getCmtsResId());
  axInternalCmts * intCmts = 
                       axGlobalData::getInstance()->findCmts(cmtsKey);

  axInternalHfc * intHfc = NULL;
  bool foundDbHfc = findDbHfc(dbHfc);
  if (foundDbHfc && intCmts) {
    axIntHfcKey_t hfcKey(dbHfc.m_hfcName);
    intHfc  = (axInternalHfc *) intCmts->findHfc(hfcKey);
  }

  axHfcCurrentAlarmTable * hfcAlarmTbl = 
                                axHfcCurrentAlarmTable::getInstance();
  axMtaCurrentAlarmTable * mtaAlarmTbl = 
                                axMtaCurrentAlarmTable::getInstance();
  axInternalGenMta * intMta = getInternalMta();

  {
    axReadLockOperator mtaTblWlock(mtaAlarmTbl->getLock());

    if (mtaAlarmTbl->find(this)) {
      goto EXIT_LABEL;
    }
  }

  /*
   * Check if there is a higher level alarm
   */
  if (foundDbHfc) {
    axHfcAlarmPower   hfcPowerAlm(getCmtsResId(), dbHfc.m_hfcName);
    axHfcAlarmPower * hfcPowerAlmPtr;

    axReadLockOperator hfcTblWlock(hfcAlarmTbl->getLock());

    if ( (hfcPowerAlmPtr = (axHfcAlarmPower *) 
                                   hfcAlarmTbl->find(&hfcPowerAlm)) ) {
      axWriteLockOperator wLock(hfcPowerAlmPtr->getLock());
      axInternalObjList lst;
      lst.add(getInternalMta());
      hfcPowerAlmPtr->preSoakCheckAllAndAddCoincidentals(&lst);
      goto EXIT_LABEL;
    }
  }

  /*
   * Set internal MTA fields for Higher HFC power alarm detection
   */

  {
    axWriteLockOperator mtaWlock(intMta->getLock());

    if (!intMta->hasData()) {
      goto EXIT_LABEL;
    }

    axIntGenMtaNonkey_t * gMtaNk = (axIntGenMtaNonkey_t *) 
                                                    intMta->getNonKey();

    gMtaNk->powerAlarmChangeTime = getAlarmDetectionTime();
    gMtaNk->onBatteryAlarm = 1;
  }

  /*
   * Check if we have a HFC Power alarm situation
   */

  if (intHfc) {

    axReadLockOperator intHfcRlock(intHfc->getLock());

    if (!intHfc->hasData()) {
      goto SINGLE_ALARM;
    }

    {
      axListBase badDevices;
      bool isAlarm;
      // int hfcPowerAlarmMtaThreshold = 3;
      // int hfcPowerAlarmDetectWin = 60*10;
      // int hfcPowerAlarmSoakWin = 60*20;
      axHfcAlarmDetection hfcAlmDetect(intHfc);
      axIntHfcNonkey_t * hfcNk = 
                         (axIntHfcNonkey_t *) intHfc->getNonKey();
      // hfcAlmDetect.setAlarmDetectionStartTime(
                         // intHfc->getPowerAlarmDetectStartTime());
      //                                hfcNk->nextPowerAlmStartTm);
      hfcAlmDetect.setAlarmThreshold(
                           sysCfg->get_hfc_power_alarm_threshold());
      hfcAlmDetect.setAlarmDetectionWindow(
                    sysCfg->get_hfc_power_alarm_detection_window());
      isAlarm = hfcAlmDetect.determineMtaPowerAlarm(badDevices);

      if (isAlarm) {
        axHfcAlarmPower * alm = new 
                       axHfcAlarmPower(intCmts->getResId(), intHfc);
        alm->preSoakAddChildren(&badDevices);
        alm->setAlarmThreshold(
                           sysCfg->get_hfc_power_alarm_threshold());
        alm->setAlarmDetectionWindow(
                    sysCfg->get_hfc_power_alarm_detection_window());
        alm->setAlarmSoakWindow(
          sysCfg->get_hfc_power_soak_win_duration(
                                     alm->getAlarmDetectionTime()));
        alm->setPreSoakDeviceTotal(hfcNk->currentCounts.mta.total);
        alm->setPreSoakDeviceChange(badDevices.size());

        axAlarmProcessingMsg * msg = new axAlarmProcessingMsg(
                                 AX_SS_TYPE_ALARM_PROCESSING, alm);
        axMessageManager::getInstance()->sendMessage(msg);
        goto EXIT_LABEL;
      }

    }

  }

SINGLE_ALARM:

  /*
   * Finally add alarm to system to soaked, etc
   */

  if (!addAlarmToDb()) {
    goto EXIT_LABEL;
  }

  {
    axWriteLockOperator mtaTblWlock(mtaAlarmTbl->getLock());
    if (!mtaAlarmTbl->add(this)) {
      goto EXIT_LABEL;
    }
  }

  createSoakTimerEntry();

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axMtaAlarmOnBattery::postSoakCheckEscalate(void)
{
  bool ret = true;

  axIntGenMtaNonkey_t * mtaNk;

  axDbHfc dbHfc;

  axHfcCurrentAlarmTable * hfcAlarmTbl =
                                axHfcCurrentAlarmTable::getInstance();
  axReadLockOperator hfcTblWlock(hfcAlarmTbl->getLock());

  axInternalGenMta * intMta = getInternalMta();
  axReadLockOperator mtaRlock(intMta->getLock());

  if (!intMta->hasData()) {
    goto EXIT_LABEL;
  }

  mtaNk = (axIntGenMtaNonkey_t *) intMta->getNonKey();

  if (!mtaNk->onBatteryAlarm) {
    clearAlarm();
    goto EXIT_LABEL;
  }

  if (findDbHfc(dbHfc)) {

    axHfcAlarmPower   hfcPowerAlm(getCmtsResId(), dbHfc.m_hfcName);
    axHfcAlarmPower * hfcPowerAlmPtr;

    if ( (hfcPowerAlmPtr = (axHfcAlarmPower *)
                                    hfcAlarmTbl->find(&hfcPowerAlm)) ) {
      axWriteLockOperator wLock(hfcPowerAlmPtr->getLock());
      axInternalObjList lst;
      lst.add(intMta);
      hfcPowerAlmPtr-> preSoakCheckAndAddCoincidentals(&lst);
      updateDbBasicAlarm(DB_ALARM_STATE_COINCIDENTAL);
      addDbAlarmHistory(DB_ALARM_STATE_COINCIDENTAL);
      goto EXIT_LABEL;
    }
  }

  ticketAlarm();

EXIT_LABEL:

  return (ret);
}


