
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcAlarmPercent.hpp"
#include "axHfcCurrentAlarmTable.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axAlarmDataTypes.hpp"
#include "axHfcAlarmMtaCount.hpp"
#include "axAbstractInternalObject.hpp"
#include "axInternalObjList.hpp"
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
axHfcAlarmPercent::axHfcAlarmPercent()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcAlarmPercent::~axHfcAlarmPercent()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcAlarmPercent::axHfcAlarmPercent(INTDS_RESID_t cmtsResId,
                                             axInternalHfc * intHfc) :
  axHfcAlarm(cmtsResId, intHfc)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcAlarmPercent::axHfcAlarmPercent(INTDS_RESID_t cmtsResId, 
                                                  AX_INT8 * hfcName) :
  axHfcAlarm(cmtsResId, hfcName)
{
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axHfcAlarmPercent::getAlarmSubType(void)
{
  return (AX_HFC_ALARM_PERCENT);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarmPercent::addAlarm(void)
{
  bool ret = false;

  axHfcAlarmPercent * hfcPercentAlmPtr;

  axHfcCurrentAlarmTable * alarmTbl = 
                                axHfcCurrentAlarmTable::getInstance();
  axWriteLockOperator tblWlock(alarmTbl->getLock());

  axInternalHfc * intHfc = getInternalHfc();
  axWriteLockOperator hfcWLock(intHfc->getLock());

  axHfcAlarmMtaCount cmHfcAlm(getCmtsResId(), getHfcName());
  axHfcAlarmMtaCount * cmHfcAlmPtr;
  if ( (cmHfcAlmPtr = (axHfcAlarmMtaCount *) 
                                       alarmTbl->find(&cmHfcAlm)) ) {
    axWriteLockOperator wLock(cmHfcAlmPtr->getLock());
    cmHfcAlmPtr->preSoakCheckAndAddCoincidentals(
                                               preSoakGetChildren());
    goto EXIT_LABEL;
  }

  if ( (hfcPercentAlmPtr = (axHfcAlarmPercent *) 
                                           alarmTbl->find(this)) ) {
    /*
     * This really should not happen!
     */
    axWriteLockOperator wLock(hfcPercentAlmPtr->getLock());
    hfcPercentAlmPtr->
           preSoakCheckAllAndAddCoincidentals(preSoakGetChildren());
    goto EXIT_LABEL;
  }

  if (!addAlarmToDb()) {
    goto EXIT_LABEL;
  }

  if (!alarmTbl->add(this)) {
    goto EXIT_LABEL;
  }

  movePercentAlarmStartTime();

  createSoakTimerEntry();

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarmPercent::postSoakCheckEscalate(void)
{
  bool ret = true;
  axSortedList sortedBadCmList(true);
  axIntCmCounts_t cmCounts;
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

  hfcAlmDetect.determineCmCounts(sortedBadCmList, cmCounts);
  alarmExists = hfcAlmDetect.determineCmPecentAlarm(sortedBadCmList, 
                                  cmCounts, problemCausingDevList);

  determinePostSoakBadCoincidentals(preSoakCoincidentals, 
                                          postSoakBadCoincidentals);

  if (!alarmExists) {
    clearAlarm();
    generatePercentCoincidentalAlarms(postSoakBadCoincidentals);
    goto EXIT_LABEL;
  }

  updatePostSoakDevices(problemCausingDevList);

  updatePostSoakCoincidentals(postSoakBadCoincidentals);

  updateAlarmSecondary(cmCounts.total, problemCausingDevList.size());

  ticketAlarm(problemCausingDevList, postSoakBadCoincidentals);

EXIT_LABEL:

  return (ret);
}


