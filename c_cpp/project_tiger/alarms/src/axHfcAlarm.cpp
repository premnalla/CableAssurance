
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcAlarm.hpp"
#include "axAlarmDataTypes.hpp"
#include "axAlarmTrackingMgr.hpp"
#include "axGlobalData.hpp"
#include "axDbCAAlarmHistory.hpp"
#include "axDbCAAlarmSecondary.hpp"
#include "axDbCAAlarmDeviceSoaking.hpp"
#include "axIteratorHolder.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axHfcAlarmTimerEntry.hpp"
#include "axSoakTimerQ.hpp"
#include "axDbCAAlarmDevicePostSoak.hpp"
#include "axDbCAAlarmCoincidentalPostSoak.hpp"
#include "axInternalObjList.hpp"
#include "axInternalUtils.hpp"

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
axHfcAlarm::axHfcAlarm() :
  m_cmtsResId(0),
  m_alarmThreshold(0),
  m_alarmDetectionWindow(0),
  m_preSoakDeviceTotal(0),
  m_preSoakDeviceChange(0),
  m_postSoakDeviceTotal(0),
  m_postSoakDeviceChange(0),
  m_intHfc(NULL)
{
  m_hfcName[0] = '\0';
}


//********************************************************************
// destructor:
//********************************************************************
axHfcAlarm::~axHfcAlarm()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcAlarm::axHfcAlarm(INTDS_RESID_t cmtsResId, axInternalHfc * intHfc) :
  axAbstractCAAlarm(intHfc->getResId()),
  m_cmtsResId(cmtsResId),
  m_alarmThreshold(0),
  m_alarmDetectionWindow(0),
  m_preSoakDeviceTotal(0),
  m_preSoakDeviceChange(0),
  m_postSoakDeviceTotal(0),
  m_postSoakDeviceChange(0),
  m_intHfc(intHfc)
{
  copyIntHfcName(m_hfcName, intHfc->getHfcName());
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcAlarm::axHfcAlarm(INTDS_RESID_t cmtsResId, AX_INT8 * hfcName) :
  m_cmtsResId(cmtsResId),
  m_alarmThreshold(0),
  m_alarmDetectionWindow(0),
  m_preSoakDeviceTotal(0),
  m_preSoakDeviceChange(0),
  m_postSoakDeviceTotal(0),
  m_postSoakDeviceChange(0),
  m_intHfc(NULL)
{
  copyIntHfcName(m_hfcName, hfcName);
}


//********************************************************************
// method:
//********************************************************************
INTDS_RESID_t
axHfcAlarm::getCmtsResId(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcAlarm::getHfcName(void)
{
  return (m_hfcName);
}


//********************************************************************
// method:
//********************************************************************
int
axHfcAlarm::keyCompare(axObject * o)
{
  int ret;

  axHfcAlarm * b = dynamic_cast<axHfcAlarm *> (o);

  ret = getCmtsResId() - b->getCmtsResId();

  if (ret) {
    goto EXIT_LABEL;
  }

  ret = strcmp(getHfcName(), b->getHfcName());

  if (ret) {
    goto EXIT_LABEL;
  }

  ret = getAlarmSubType() - b->getAlarmSubType();

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axHfcAlarm::getAlarmType(void)
{
  return (AX_ALARM_TYPE_HFC);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::preSoakAddChildren(axListBase * l)
{
  axObject * o;
  while ( (o = l->remove()) ) {
    m_preSoakList.add(o);
  }
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarm::preSoakHasChild(axObject * o)
{
  bool ret;

  ret = (m_preSoakList.find(o) ? true : false);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
size_t
axHfcAlarm::preSoakNumChildren(void)
{
  return ((size_t)m_preSoakList.size());
}


//********************************************************************
// method:
//********************************************************************
axListBase *
axHfcAlarm::preSoakGetChildren(void)
{
  return (&m_preSoakList);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::preSoakAddCoincidentals(axListBase * l)
{
  axObject * o;
  while ( (o = l->remove()) ) {
    m_preSoakCoincidentalList.add(o);
  }
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarm::preSoakHasCoincidental(axObject * o)
{
  bool ret;

  ret = (m_preSoakCoincidentalList.find(o) ? true : false);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
size_t
axHfcAlarm::preSoakNumCoincidentals(void)
{
  return ((size_t)m_preSoakCoincidentalList.size());
}


//********************************************************************
// method:
//********************************************************************
axListBase *
axHfcAlarm::preSoakGetCoincidentals(void)
{
  return (&m_preSoakCoincidentalList);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::postSoakAddChildren(axListBase * l)
{
  axObject * o;
  while ( (o = l->remove()) ) {
    m_postSoakList.add(o);
  }
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarm::postSoakHasChild(axObject * o)
{
  bool ret;

  ret = (m_postSoakList.find(o) ? true : false);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
size_t
axHfcAlarm::postSoakNumChildren(void)
{
  return ((size_t)m_postSoakList.size());
}


//********************************************************************
// method:
//********************************************************************
axListBase *
axHfcAlarm::postSoakGetChildren(void)
{
  return (&m_postSoakList);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::postSoakAddCoincidentals(axListBase * l)
{
  axObject * o;
  while ( (o = l->remove()) ) {
    m_postSoakCoincidentalList.add(o);
  }
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarm::postSoakHasCoincidental(axObject * o)
{
  bool ret;

  ret = (m_postSoakCoincidentalList.find(o) ? true : false);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
size_t
axHfcAlarm::postSoakNumCoincidentals(void)
{
  return ((size_t)m_postSoakCoincidentalList.size());
}


//********************************************************************
// method:
//********************************************************************
axListBase *
axHfcAlarm::postSoakGetCoincidentals(void)
{
  return (&m_postSoakCoincidentalList);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::setAlarmThreshold(AX_UINT16 in)
{
  m_alarmThreshold = in;
}


//********************************************************************
// method:
//********************************************************************
AX_UINT16
axHfcAlarm::getAlarmThreshold(void)
{
  return (m_alarmThreshold);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::setAlarmDetectionWindow(time_t in)
{
  m_alarmDetectionWindow = in;
}


//********************************************************************
// method:
//********************************************************************
time_t
axHfcAlarm::getAlarmDetectionWindow(void)
{
  return (m_alarmDetectionWindow);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::setPreSoakDeviceTotal(AX_UINT16 in)
{
  m_preSoakDeviceTotal = in;
}


//********************************************************************
// method:
//********************************************************************
AX_UINT16
axHfcAlarm::getPreSoakDeviceTotal(void)
{
  return (m_preSoakDeviceTotal);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::setPreSoakDeviceChange(AX_UINT16 in)
{
  m_preSoakDeviceChange = in;
}


//********************************************************************
// method:
//********************************************************************
AX_UINT16
axHfcAlarm::getPreSoakDeviceChange(void)
{
  return (m_preSoakDeviceChange);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::setPostSoakDeviceTotal(AX_UINT16 in)
{
  m_postSoakDeviceTotal = in;
}


//********************************************************************
// method:
//********************************************************************
AX_UINT16
axHfcAlarm::getPostSoakDeviceTotal(void)
{
  return (m_postSoakDeviceTotal);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::setPostSoakDeviceChange(AX_UINT16 in)
{
  m_postSoakDeviceChange = in;
}


//********************************************************************
// method:
//********************************************************************
AX_UINT16
axHfcAlarm::getPostSoakDeviceChange(void)
{
  return (m_postSoakDeviceChange);
}


//********************************************************************
// method:
//********************************************************************
axInternalHfc *
axHfcAlarm::getInternalHfc(void)
{
  return (m_intHfc);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::movePercentAlarmStartTime(void)
{
#if 0
  if (m_intHfc->hasData()) {
    axIntHfcNonkey_t * hfcNonkey = 
                           (axIntHfcNonkey_t *) m_intHfc->getNonKey();

    hfcNonkey->nextPercentAlmStartTm = getAlarmDetectionTime() +
                                                 getAlarmSoakWindow();
  }
#endif
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::movePowerAlarmStartTime(void)
{
#if 0
  if (m_intHfc->hasData()) {
    axIntHfcNonkey_t * hfcNonkey =
                           (axIntHfcNonkey_t *) m_intHfc->getNonKey();

    hfcNonkey->nextPowerAlmStartTm = getAlarmDetectionTime() +
                                                 getAlarmSoakWindow();
  }
#endif
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::createSoakTimerEntry(void)
{
  time_t soakCompletionTm = getAlarmDetectionTime() +
                                                 getAlarmSoakWindow();
  axHfcAlarmTimerEntry * soakTmEntry =
                      new axHfcAlarmTimerEntry(this, soakCompletionTm);
  axSoakTimerQ::getInstance()->add(soakTmEntry);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::preSoakCheckAndAddCoincidentals(axListBase * inList)
{
  axIteratorHolder iH(inList->createIterator());
  axAbstractIterator * iter = iH.getIterator();
  axListBase * currList = preSoakGetCoincidentals();
  axAbstractInternalObject * inObj = (axAbstractInternalObject *)
                                                     iter->getFirst();
  while (inObj) {
    if (!currList->find(inObj)) {
      currList->add(inObj);
    }
    inObj = (axAbstractInternalObject *) iter->getNext();
  }

}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarm::preSoakCheckAllAndAddCoincidentals(axListBase * inList)
{
  axIteratorHolder iH(inList->createIterator());
  axAbstractIterator * iter = iH.getIterator();
  axListBase * currList1 = preSoakGetCoincidentals();
  axListBase * currList2 = preSoakGetChildren();
  axAbstractInternalObject * inObj = (axAbstractInternalObject *)
                                                     iter->getFirst();
  while (inObj) {
    if ( !currList1->find(inObj) && !currList2->find(inObj) ) {
      currList1->add(inObj);
    }
    inObj = (axAbstractInternalObject *) iter->getNext();
  }

}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarm::addAlarmToDb(void)
{
  bool ret = axAbstractCAAlarm::addAlarmToDb();

  if (!ret) {
    goto EXIT_LABEL;
  }

  {
    //
    axDbCAAlarmSecondary secondaryAlm(getAlarmId());
    secondaryAlm.m_preSoakTotal = getPreSoakDeviceTotal();
    secondaryAlm.m_preSoakCount = getPreSoakDeviceChange();
    secondaryAlm.m_alarmThreshold = getAlarmThreshold();
    secondaryAlm.m_alarmDetectionWindow = getAlarmDetectionWindow();
    // secondaryAlm.m_postSoakTotal = ;
    // secondaryAlm.m_postSoakCount = ;
    secondaryAlm.insertRow();
  }

  {
    //
    axIteratorHolder iH(preSoakGetChildren()->createIterator());
    axAbstractIterator * iter = iH.getIterator();
    axAbstractInternalObject * intObj = 
                         (axAbstractInternalObject *) iter->getFirst();
    while (intObj) {
      axDbCAAlarmDeviceSoaking almDevice(getAlarmId(),
                                                   intObj->getResId());
      almDevice.insertRow();
      intObj = (axAbstractInternalObject *) iter->getNext();
    }
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarm::updatePostSoakDevices(axListBase & l)
{
  bool ret = true;

  axAbstractInternalObject * intO;

  while ( (intO = (axAbstractInternalObject *) l.remove()) ) {
    axReadLockOperator rLock(intO->getLock());
    axDbCAAlarmDevicePostSoak almRec(getAlarmId(), intO->getResId());
    almRec.insertRow();
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarm::updatePostSoakCoincidentals(axListBase & l)
{
  bool ret = true;

  axAbstractInternalObject * intO;

  while ( (intO = (axAbstractInternalObject *) l.remove()) ) {
    axReadLockOperator rLock(intO->getLock());
    axDbCAAlarmCoincidentalPostSoak almRec(getAlarmId(), 
                                                    intO->getResId());
    almRec.insertRow();
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarm::updateAlarmSecondary(AX_UINT32 total, AX_UINT32 numOos)
{
  bool ret = false;

  axDbCAAlarmSecondary alm(getAlarmId());
  if (!alm.getRow()) {
    goto EXIT_LABEL;
  }

  alm.m_postSoakTotal = total;
  alm.m_postSoakCount = numOos;

  ret = alm.updateRow();

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarm::generatePercentCoincidentalAlarms(
                                axListBase & postSoakBadCoincidentals)
{
  bool ret = false;

  axAbstractInternalObject * intO;

  struct timeval tm;
  gettimeofday(&tm, NULL);

  while ( (intO = (axAbstractInternalObject *)
                               postSoakBadCoincidentals.remove()) ) {
    axReadLockOperator rLock(intO->getLock());
    if (intO->isAlarmable()) {
      intO->generateAlarm(AX_CORRELATED_ALARM_PERCENT, 
                                             m_cmtsResId, tm.tv_sec);
    }
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarm::generateCountCoincidentalAlarms(
                                axListBase & postSoakBadCoincidentals)
{
  bool ret = false;

  axAbstractInternalObject * intO;

  struct timeval tm;
  gettimeofday(&tm, NULL);

  while ( (intO = (axAbstractInternalObject *)
                               postSoakBadCoincidentals.remove()) ) {
    axReadLockOperator rLock(intO->getLock());
    if (intO->isAlarmable()) {
      intO->generateAlarm(AX_CORRELATED_ALARM_COUNT,
                                             m_cmtsResId, tm.tv_sec);
    }
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarm::generatePowerCoincidentalAlarms(
                                axListBase & postSoakBadCoincidentals)
{
  bool ret = false;

  axAbstractInternalObject * intO;

  struct timeval tm;
  gettimeofday(&tm, NULL);

  while ( (intO = (axAbstractInternalObject *)
                               postSoakBadCoincidentals.remove()) ) {
    axReadLockOperator rLock(intO->getLock());
    if (intO->isAlarmable()) {
      intO->generateAlarm(AX_CORRELATED_ALARM_POWER,
                                             m_cmtsResId, tm.tv_sec);
    }
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarm::determinePostSoakBadCoincidentals(
                                    axListBase * preSoakCoincidentals,
                                axListBase & postSoakBadCoincidentals) 
{
  bool ret = true;

  axAbstractInternalObject * intO;

  while ( (intO = (axAbstractInternalObject *)
                                  preSoakCoincidentals->remove()) ) {
    axReadLockOperator rLock(intO->getLock());
    if (!intO->isStatusOperational()) {
      postSoakBadCoincidentals.add(intO);
    }
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarm::ticketAlarm(axListBase & problemCausingDevList,
                                axListBase & postSoakBadCoincidentals)
{
  bool ret = true;

  bool ticketed = false;

  bool rc;

  rc = updateDbBasicAlarm(DB_ALARM_STATE_TICKETING);
  rc = addDbAlarmHistory(DB_ALARM_STATE_TICKETING);

  /*
   * Interface with 3rd party systems for ticeting
   */

  if (ticketed) {
    rc = updateDbBasicAlarm(DB_ALARM_STATE_TICKETED);
    rc = addDbAlarmHistory(DB_ALARM_STATE_TICKETED);
  } else {
    rc = updateDbBasicAlarm(DB_ALARM_STATE_TICKETING_FAILED);
    rc = addDbAlarmHistory(DB_ALARM_STATE_TICKETING_FAILED);
  }

  return (ret);
}


