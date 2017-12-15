
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axMtaAlarmUnavail.hpp"
#include "axAlarmDataTypes.hpp"
#include "axHfcCurrentAlarmTable.hpp"
#include "axMtaCurrentAlarmTable.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axDbCAAlarmSecondary.hpp"
#include "axDbCAAlarmHistory.hpp"
#include "axDbCASoakingAlarm.hpp"
#include "axDbHfc.hpp"
#include "axHfcAlarmMtaCount.hpp"
#include "axHfcAlarmPercent.hpp"
#include "axInternalObjList.hpp"

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
axMtaAlarmUnavail::axMtaAlarmUnavail() :
  axMtaAlarm((INTDS_RESID_t)0, (AX_INT8 *)NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axMtaAlarmUnavail::~axMtaAlarmUnavail()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axMtaAlarmUnavail::axMtaAlarmUnavail(INTDS_RESID_t cmtsResId,
                                            axInternalGenMta * gMta) :
  axMtaAlarm(cmtsResId, gMta)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axMtaAlarmUnavail::axMtaAlarmUnavail(INTDS_RESID_t cmtsResId, 
                                                   AX_INT8 * mtaMac) :
  axMtaAlarm(cmtsResId, mtaMac)
{
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axMtaAlarmUnavail::getAlarmSubType(void)
{
  return (AX_MTA_ALARM_UNAVAILABLE);
}


//********************************************************************
// method:
//********************************************************************
bool
axMtaAlarmUnavail::addAlarm(void)
{
  bool      ret = false;
  axDbHfc   dbHfc;

  axHfcCurrentAlarmTable * hfcAlarmTbl = 
                                axHfcCurrentAlarmTable::getInstance();
  axMtaCurrentAlarmTable * mtaAlarmTbl = 
                                axMtaCurrentAlarmTable::getInstance();

  {
    axReadLockOperator mtaTblWlock(mtaAlarmTbl->getLock());

    if (mtaAlarmTbl->find(this)) {
      goto EXIT_LABEL;
    }
  }

  /*
   * Check if there is a higher level alarm
   */

  if (findDbHfc(dbHfc)) {

    axReadLockOperator hfcTblWlock(hfcAlarmTbl->getLock());

    axHfcAlarmMtaCount hfcMtaAlm(getCmtsResId(), dbHfc.m_hfcName);
    axHfcAlarmMtaCount * hfcMtaAlmPtr;

    axHfcAlarmPercent hfcPercentAlm(getCmtsResId(), dbHfc.m_hfcName);
    axHfcAlarmPercent * hfcPercentAlmPtr;

    if ( (hfcMtaAlmPtr = (axHfcAlarmMtaCount *) 
                                      hfcAlarmTbl->find(&hfcMtaAlm)) ) {
      axWriteLockOperator wLock(hfcMtaAlmPtr->getLock());
      axInternalObjList lst;
      lst.add(getInternalMta());
      hfcMtaAlmPtr-> preSoakCheckAndAddCoincidentals(&lst);
      goto EXIT_LABEL;
    }

    if ( (hfcPercentAlmPtr = (axHfcAlarmPercent *) 
                                  hfcAlarmTbl->find(&hfcPercentAlm)) ) {
      axWriteLockOperator wLock(hfcPercentAlmPtr->getLock());
      axInternalObjList lst;
      lst.add(getInternalMta());
      hfcPercentAlmPtr-> preSoakCheckAndAddCoincidentals(&lst);
      goto EXIT_LABEL;
    }
  }

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
axMtaAlarmUnavail::postSoakCheckEscalate(void)
{
  bool ret = true;

  axDbHfc dbHfc;

  axHfcCurrentAlarmTable * hfcAlarmTbl =
                                axHfcCurrentAlarmTable::getInstance();

  axInternalGenMta * intMta = getInternalMta();

  {
    axReadLockOperator mtaRlock(intMta->getLock());
    if (!intMta->hasData()) {
      goto EXIT_LABEL;
    }

    axIntGenMtaNonkey_t * mtaNk = 
                          (axIntGenMtaNonkey_t *) intMta->getNonKey();

    if (mtaNk->available) {
      clearAlarm();
      goto EXIT_LABEL;
    }
  }

  if (findDbHfc(dbHfc)) {

    axReadLockOperator hfcTblWlock(hfcAlarmTbl->getLock());

    axHfcAlarmMtaCount hfcMtaAlm(getCmtsResId(), dbHfc.m_hfcName);
    axHfcAlarmMtaCount * hfcMtaAlmPtr;

    axHfcAlarmPercent hfcPercentAlm(getCmtsResId(), dbHfc.m_hfcName);
    axHfcAlarmPercent * hfcPercentAlmPtr;

    axHfcAlarm        * genericHfcAlmPtr = NULL;

    if ( (hfcMtaAlmPtr = (axHfcAlarmMtaCount *)
                                      hfcAlarmTbl->find(&hfcMtaAlm)) ) {
      genericHfcAlmPtr = hfcMtaAlmPtr;
    }

    if ( !genericHfcAlmPtr && (hfcPercentAlmPtr = (axHfcAlarmPercent *)
                                  hfcAlarmTbl->find(&hfcPercentAlm)) ) {
      genericHfcAlmPtr = hfcPercentAlmPtr;
    }

    if (genericHfcAlmPtr) {
      axWriteLockOperator wLock(genericHfcAlmPtr->getLock());
      axInternalObjList lst;
      lst.add(intMta);
      hfcMtaAlmPtr-> preSoakCheckAndAddCoincidentals(&lst);
      updateDbBasicAlarm(DB_ALARM_STATE_COINCIDENTAL);
      addDbAlarmHistory(DB_ALARM_STATE_COINCIDENTAL);
      goto EXIT_LABEL;
    }

  }

  ticketAlarm();

EXIT_LABEL:

  return (ret);
}


