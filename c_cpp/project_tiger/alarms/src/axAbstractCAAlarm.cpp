
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCAAlarm.hpp"
#include "axReaderWriterLock.hpp"
#include "axDbCARootAlarm.hpp"
#include "axDbCACurrentAlarm.hpp"
#include "axDbCAHistoricalAlarm.hpp"
#include "axDbCAAlarmHistory.hpp"
#include "axDbCASoakingAlarm.hpp"

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
axAbstractCAAlarm::axAbstractCAAlarm() :
  m_intAlarmId(0),
  m_alarmId((AX_UINT64)0),
  m_alarmResId(0),
  m_alarmSoakWindow(0),
  m_lock(new axReaderWriterLock())
{
  instantiationInit();
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractCAAlarm::~axAbstractCAAlarm()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAbstractCAAlarm::axAbstractCAAlarm(axAbstractLock * lock, 
                                           INTDS_RESID_t alarmResId) :
  m_intAlarmId(0),
  m_alarmId((AX_UINT64)0),
  m_alarmResId(alarmResId),
  m_alarmSoakWindow(0),
  m_lock(lock)
{
  instantiationInit();
}


//********************************************************************
// data constructor:
//********************************************************************
axAbstractCAAlarm::axAbstractCAAlarm(INTDS_RESID_t alarmResId) :
  m_intAlarmId(0),
  m_alarmId((AX_UINT64)0),
  m_alarmResId(alarmResId),
  m_alarmSoakWindow(0),
  m_lock(new axReaderWriterLock())
{
  instantiationInit();
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAAlarm::instantiationInit(void)
{
  struct timeval tm;
  gettimeofday(&tm, NULL);
  m_alarmDetectionTime = tm;
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAAlarm::setInternalAlarmId(AX_UINT32 id)
{
  m_intAlarmId = id;
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axAbstractCAAlarm::getInternalAlarmId(void)
{
  return (m_intAlarmId);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAAlarm::setResId(INTDS_RESID_t resId)
{
  m_alarmResId = resId;
}


//********************************************************************
// method:
//********************************************************************
INTDS_RESID_t
axAbstractCAAlarm::getResId(void)
{
  return (m_alarmResId);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAAlarm::setAlarmId(AX_UINT64 id)
{
  m_alarmId = id;
}


//********************************************************************
// method:
//********************************************************************
AX_UINT64
axAbstractCAAlarm::getAlarmId(void)
{
  return (m_alarmId);
}


//********************************************************************
// method:
//********************************************************************
axAbstractLock *
axAbstractCAAlarm::getLock(void)
{
  return (m_lock);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAAlarm::setAlarmDetectionTime(time_t in)
{
  m_alarmDetectionTime.tv_sec = in;
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAAlarm::setAlarmDetectionTimeUSec(time_t in)
{
  m_alarmDetectionTime.tv_usec = in;
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAAlarm::setAlarmDetectionTime(struct timeval & in)
{
  m_alarmDetectionTime = in;
}


//********************************************************************
// method:
//********************************************************************
time_t
axAbstractCAAlarm::getAlarmDetectionTime(void)
{
  return (m_alarmDetectionTime.tv_sec);
}


//********************************************************************
// method:
//********************************************************************
time_t
axAbstractCAAlarm::getAlarmDetectionTimeUSec(void)
{
  return (m_alarmDetectionTime.tv_usec);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAAlarm::setAlarmSoakWindow(AX_UINT16 in)
{
  m_alarmSoakWindow = in;
}


//********************************************************************
// method:
//********************************************************************
AX_UINT16
axAbstractCAAlarm::getAlarmSoakWindow(void)
{
  return (m_alarmSoakWindow);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractCAAlarm::addAlarmToDb(void)
{
  bool ret = false;

  axDbCARootAlarm rootAlm(this);
  rootAlm.m_detectionTime = getAlarmDetectionTime();
  rootAlm.m_detectionTimeUSec = getAlarmDetectionTimeUSec();

  if (!rootAlm.insertRow()) {
    goto EXIT_LABEL;
  }

  //
  setAlarmId(rootAlm.m_rootAlarmId);

  {
    axDbCACurrentAlarm currAlm(this);
    axDbCAHistoricalAlarm histAlm(this);

    currAlm.insertRow();
    histAlm.insertRow();

    axDbCAAlarmHistory almStateHist(currAlm);
    almStateHist.insertRow();

    axDbCASoakingAlarm soakingAlm(getAlarmId());
    soakingAlm.insertRow();
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractCAAlarm::updateDbBasicAlarm(AX_INT8 newAlmState)
{
  bool ret;

  axDbCACurrentAlarm currAlarm(getAlarmId());
  axDbCAHistoricalAlarm histAlarm(getAlarmId());

  bool histUpdate = false;
  bool currUpdate = false;

  if (histAlarm.getRow()) {
    histAlarm.m_alarmState = newAlmState;
    histUpdate = histAlarm.updateRow();
  }

  if (currAlarm.getRow()) {
    currAlarm.m_alarmState = newAlmState;
    currUpdate = currAlarm.updateRow();
  }

  ret = (histUpdate && currUpdate);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractCAAlarm::addDbAlarmHistory(AX_INT8 newAlmState)
{
  bool ret;

  axDbCAAlarmHistory almHist(getAlarmId());
  almHist.m_alarmState = newAlmState;
  ret = almHist.insertRow();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractCAAlarm::clearAlarm(void)
{
  bool ret = true;

  bool rc;

  rc = updateDbBasicAlarm(DB_ALARM_STATE_CLEARED);

  rc = addDbAlarmHistory(DB_ALARM_STATE_CLEARED);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractCAAlarm::ticketAlarm(void)
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


#if 0
//********************************************************************
// method:
//********************************************************************
bool
axAbstractCAAlarm::postSoakAnalysis(void)
{
  bool ret = false;

  /*
   * TODO: If condition still exists, excalate to ticketing etc.
   */

  /*
   * TODO: If condition does not exist anymore, clear
   */


  /*
   * TODO: Updat DB appropriately (Current, History, etc)
   */


  /*
   * TODO: Anything else ...
   */


  /*
   * TODO: cleanup
   */

  return (ret);
}
#endif


