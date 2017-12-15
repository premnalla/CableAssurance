
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axMtaAlarm.hpp"
#include "axAlarmDataTypes.hpp"
#include "axMtaCurrentAlarmTable.hpp"
#include "axWriteLockOperator.hpp"
#include "axDbCAAlarmSecondary.hpp"
#include "axMtaAlarmTimerEntry.hpp"
#include "axSoakTimerQ.hpp"
#include "axDbEmta.hpp"
#include "axDbMta.hpp"
#include "axDbHfc.hpp"
#include "axInternalUtils.hpp"
// #include "axHfcAlarmMtaCount.hpp"
// #include "axHfcAlarmPercent.hpp"

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
axMtaAlarm::axMtaAlarm() :
  m_cmtsResId(0),
  m_intMta(NULL)
{
  m_mtaMacAddress[0] = '\0';
}


//********************************************************************
// destructor:
//********************************************************************
axMtaAlarm::~axMtaAlarm()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axMtaAlarm::axMtaAlarm(INTDS_RESID_t cmtsResId, 
                                            axInternalGenMta * gMta) :
  axAbstractCAAlarm(gMta->getResId()),
  m_cmtsResId(cmtsResId),
  m_intMta(gMta)
{
  copyIntMac(m_mtaMacAddress, gMta->getMtaMacAddress());
}


//********************************************************************
// data constructor:
//********************************************************************
axMtaAlarm::axMtaAlarm(INTDS_RESID_t cmtsResId, AX_INT8 * mtaMac) :
  m_cmtsResId(cmtsResId),
  m_intMta(NULL)
{
  copyIntMac(m_mtaMacAddress, mtaMac);
}


//********************************************************************
// method:
//********************************************************************
INTDS_RESID_t
axMtaAlarm::getCmtsResId(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method:
//********************************************************************
axInternalGenMta *
axMtaAlarm::getInternalMta(void)
{
  return (m_intMta);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axMtaAlarm::getMtaMacAddress(void)
{
  return (m_mtaMacAddress);
}


//********************************************************************
// method:
//********************************************************************
int
axMtaAlarm::keyCompare(axObject * o)
{
  int ret;

  axMtaAlarm * b = dynamic_cast<axMtaAlarm *> (o);

  ret = getCmtsResId() - b->getCmtsResId();

  if (ret) {
    goto EXIT_LABEL;
  }

  ret = strcmp(getMtaMacAddress(), b->getMtaMacAddress());

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axMtaAlarm::getAlarmType(void)
{
  return (AX_ALARM_TYPE_MTA);
}


//********************************************************************
// method:
//********************************************************************
bool
axMtaAlarm::findDbHfc(axDbHfc & dbHfc)
{
  bool       ret      = false;
  DB_RESID_t hfcResId = 0;

  axDbEmta dbEmta(getResId());
  if (dbEmta.getRow()) {
    hfcResId = dbEmta.m_cm.m_hfcResId;
  }

  if (!hfcResId) {
#if 0
    /* TODO : add support for MTA (i.e., the other brother of eMTA) */
    axDbMta dbMta(getMtaMacAddress(), getCmtsResId());
    if (dbMta.getRow()) {
      hfcResId = dbEmta.m_hfcResId;
    }
#endif
  }

  if (hfcResId) {
    dbHfc.m_hfcResId = hfcResId;
    if (!dbHfc.getRow()) {
      goto EXIT_LABEL;
    }
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axMtaAlarm::createSoakTimerEntry(void)
{
  time_t soakCompletionTm = getAlarmDetectionTime() +
                                                  getAlarmSoakWindow();
  axMtaAlarmTimerEntry * soakTmEntry =
                      new axMtaAlarmTimerEntry(this, soakCompletionTm);
  axSoakTimerQ::getInstance()->add(soakTmEntry);
}


//********************************************************************
// method:
//********************************************************************
bool
axMtaAlarm::addAlarm(void)
{
  bool ret = false;

  axMtaCurrentAlarmTable * mgr = axMtaCurrentAlarmTable::getInstance();

  axWriteLockOperator tblWlock(mgr->getLock());

  if (mgr->find(this)) {
    goto EXIT_LABEL;
  }

  if (!addAlarmToDb()) {
    goto EXIT_LABEL;
  }

  if (!mgr->add(this)) {
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
axMtaAlarm::addAlarmToDb(void)
{
  bool ret = axAbstractCAAlarm::addAlarmToDb();

  if (!ret) {
    goto EXIT_LABEL;
  }

  {
    //
    axDbCAAlarmSecondary secondaryAlm(getAlarmId());
    // secondaryAlm.m_preSoakTotal = getPreSoakDeviceTotal();
    // secondaryAlm.m_preSoakCount = getPreSoakDeviceChange();
    // secondaryAlm.m_alarmThreshold = getAlarmThreshold();
    // secondaryAlm.m_alarmDetectionWindow = getAlarmDetectionWindow();
    // secondaryAlm.m_postSoakTotal = ;
    // secondaryAlm.m_postSoakCount = ;
    secondaryAlm.insertRow();
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axMtaAlarm::postSoakCheckEscalate(void)
{
  bool ret = false;


  return (ret);
}


