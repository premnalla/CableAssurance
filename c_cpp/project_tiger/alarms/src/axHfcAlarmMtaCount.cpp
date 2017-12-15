
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcAlarmMtaCount.hpp"
#include "axAlarmDataTypes.hpp"
#include "axHfcAlarmPercent.hpp"
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
axHfcAlarmMtaCount::axHfcAlarmMtaCount()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcAlarmMtaCount::~axHfcAlarmMtaCount()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcAlarmMtaCount::axHfcAlarmMtaCount(INTDS_RESID_t cmtsResId,
                                             axInternalHfc * intHfc) :
  axHfcAlarm(cmtsResId, intHfc)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcAlarmMtaCount::axHfcAlarmMtaCount(INTDS_RESID_t cmtsResId, 
                                                  AX_INT8 * hfcName) :
  axHfcAlarm(cmtsResId, hfcName)
{
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axHfcAlarmMtaCount::getAlarmSubType(void)
{
  return (AX_HFC_ALARM_MTA_COUNT);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarmMtaCount::addAlarm(void)
{
  bool ret = false;

  axHfcAlarmMtaCount * hfcMtaCountAlmPtr;

  axHfcCurrentAlarmTable * alarmTbl = 
                                axHfcCurrentAlarmTable::getInstance();
  axWriteLockOperator tblWlock(alarmTbl->getLock());

  axInternalHfc * intHfc = getInternalHfc();
  axWriteLockOperator hfcWLock(intHfc->getLock());

  axHfcAlarmPercent cmHfcAlm(getCmtsResId(), getHfcName());
  axHfcAlarmPercent * cmHfcAlmPtr;
  if ( (cmHfcAlmPtr = (axHfcAlarmPercent *) 
                                        alarmTbl->find(&cmHfcAlm)) ) {
    axWriteLockOperator wLock(cmHfcAlmPtr->getLock());
    cmHfcAlmPtr->preSoakCheckAndAddCoincidentals(
                                               preSoakGetChildren());
    goto EXIT_LABEL;
  }

  if ( (hfcMtaCountAlmPtr = (axHfcAlarmMtaCount *) 
                                            alarmTbl->find(this)) ) {
    /*
     * This really should not happen!
     */
    axWriteLockOperator wLock(hfcMtaCountAlmPtr->getLock());
    hfcMtaCountAlmPtr->
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
axHfcAlarmMtaCount::postSoakCheckEscalate(void)
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

  hfcAlmDetect.determineMtaCounts(sortedBadMtaList, mtaCounts);
  alarmExists = hfcAlmDetect.determineMtaCountAlarm(sortedBadMtaList,
                                  mtaCounts, problemCausingDevList);

  determinePostSoakBadCoincidentals(preSoakCoincidentals,
                                          postSoakBadCoincidentals);

  if (!alarmExists) {
    clearAlarm();
    generateCountCoincidentalAlarms(postSoakBadCoincidentals);
    goto EXIT_LABEL;
  }

  updatePostSoakDevices(problemCausingDevList);

  updatePostSoakCoincidentals(postSoakBadCoincidentals);

  updateAlarmSecondary(mtaCounts.total, problemCausingDevList.size());

  ticketAlarm(problemCausingDevList, postSoakBadCoincidentals);

EXIT_LABEL:

  return (ret);
}


