
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcAlarmPower.hpp"
#include "axAlarmDataTypes.hpp"
#include "axHfcCurrentAlarmTable.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axSortedList.hpp"
#include "axHfcAlarmDetection.hpp"

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
axHfcAlarmPower::axHfcAlarmPower()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcAlarmPower::~axHfcAlarmPower()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcAlarmPower::axHfcAlarmPower(INTDS_RESID_t cmtsResId,
                                             axInternalHfc * intHfc) :
  axHfcAlarm(cmtsResId, intHfc)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcAlarmPower::axHfcAlarmPower(INTDS_RESID_t cmtsResId, 
                                                  AX_INT8 * hfcName) :
  axHfcAlarm(cmtsResId, hfcName)
{
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axHfcAlarmPower::getAlarmSubType(void)
{
  return (AX_HFC_ALARM_POWER);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarmPower::addAlarm(void)
{
  bool ret = false;

  axHfcAlarmPower * hfcPowerAlmPtr;

  axHfcCurrentAlarmTable * alarmTbl = 
                                axHfcCurrentAlarmTable::getInstance();
  axWriteLockOperator tblWlock(alarmTbl->getLock());

  axInternalHfc * intHfc = getInternalHfc();
  axWriteLockOperator hfcWLock(intHfc->getLock());

  if ( (hfcPowerAlmPtr = (axHfcAlarmPower *) alarmTbl->find(this)) ) {
    /*
     * This really should not happen!
     */
    axWriteLockOperator wLock(hfcPowerAlmPtr->getLock());
    hfcPowerAlmPtr->
             preSoakCheckAllAndAddCoincidentals(preSoakGetChildren());
    goto EXIT_LABEL;
  }

  if (!addAlarmToDb()) {
    goto EXIT_LABEL;
  }

  if (!alarmTbl->add(this)) {
    goto EXIT_LABEL;
  }

  movePowerAlarmStartTime();

  createSoakTimerEntry();

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarmPower::postSoakCheckEscalate(void)
{
  bool ret = true;

  axSortedList sortedBadMtaList(true);
  axIntMtaCounts_t mtaCounts;
  axListBase problemCausingDevList;
  axListBase postSoakBadCoincidentals;
  bool alarmExists;
  axListBase * preSoakCoincidentals = preSoakGetCoincidentals();

  axInternalHfc * intHfc = getInternalHfc();
  axReadLockOperator intHfcRlock(intHfc->getLock());

  axWriteLockOperator thisAlmWlock(getLock());

  updateDbBasicAlarm(DB_ALARM_STATE_SOAK_COMPLETE);

  axHfcAlarmDetection hfcAlmDetect(intHfc);

  hfcAlmDetect.setAlarmDetectionStartTime(getAlarmDetectionTime());
  hfcAlmDetect.setAlarmThreshold(getAlarmThreshold());
  hfcAlmDetect.setAlarmDetectionWindow(getAlarmDetectionWindow());

  /* just need the following for db updates */
  hfcAlmDetect.determineMtaCounts(sortedBadMtaList, mtaCounts);

  alarmExists = hfcAlmDetect.determineMtaPowerAlarm(
                                                problemCausingDevList);

  determinePostSoakBadCoincidentals(preSoakCoincidentals,
                                          postSoakBadCoincidentals);

  if (!alarmExists) {
    clearAlarm();
    generatePowerCoincidentalAlarms(postSoakBadCoincidentals);
    goto EXIT_LABEL;
  }

  updatePostSoakDevices(problemCausingDevList);

  updatePostSoakCoincidentals(postSoakBadCoincidentals);

  updateAlarmSecondary(mtaCounts.total, problemCausingDevList.size());

  ticketAlarm(problemCausingDevList, postSoakBadCoincidentals);

EXIT_LABEL:

  return (ret);
}



