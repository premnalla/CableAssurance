
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axMtaAlarmBattMissing.hpp"
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
axMtaAlarmBattMissing::axMtaAlarmBattMissing() :
  axMtaAlarm((INTDS_RESID_t)0, (AX_INT8 *)NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axMtaAlarmBattMissing::~axMtaAlarmBattMissing()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axMtaAlarmBattMissing::axMtaAlarmBattMissing(INTDS_RESID_t cmtsResId,
                                            axInternalGenMta * gMta) :
  axMtaAlarm(cmtsResId, gMta)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axMtaAlarmBattMissing::axMtaAlarmBattMissing(INTDS_RESID_t cmtsResId, 
                                                   AX_INT8 * mtaMac) :
  axMtaAlarm(cmtsResId, mtaMac)
{
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axMtaAlarmBattMissing::getAlarmSubType(void)
{
  return (AX_MTA_ALARM_BATTERY_MISSING);
}


//********************************************************************
// method:
//********************************************************************
bool
axMtaAlarmBattMissing::addAlarm(void)
{
  bool    ret = false;
  axDbHfc dbHfc;

  axMtaCurrentAlarmTable * mtaAlarmTbl = 
                                axMtaCurrentAlarmTable::getInstance();

  axWriteLockOperator mtaTblWlock(mtaAlarmTbl->getLock());

  if (mtaAlarmTbl->find(this)) {
    goto EXIT_LABEL;
  }

  /*
   * Finally add alarm to system to soak, etc
   */

  if (!addAlarmToDb()) {
    goto EXIT_LABEL;
  }

  if (!mtaAlarmTbl->add(this)) {
    goto EXIT_LABEL;
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
axMtaAlarmBattMissing::postSoakCheckEscalate(void)
{
  bool ret = true;

  axIntGenMtaNonkey_t * mtaNk;

  axInternalGenMta * intMta = getInternalMta();

  axReadLockOperator mtaRlock(intMta->getLock());

  if (!intMta->hasData()) {
    goto EXIT_LABEL;
  }

  mtaNk = (axIntGenMtaNonkey_t *) intMta->getNonKey();

  /*
   * TODO: Have to send out an SNMP request to read battery status
   * Then have to process the SNMP reply to determine if batteries are
   * truly missing
   */




  // ticketAlarm();

EXIT_LABEL:

  return (ret);
}


