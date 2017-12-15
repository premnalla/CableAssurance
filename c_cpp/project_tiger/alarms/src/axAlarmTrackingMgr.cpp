
//********************************************************************
// OBSOLETE
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <sys/time.h>
#include "axAlarmTrackingMgr.hpp"
#include "axMultipleReaderLock.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axHfcAlarmTimerEntry.hpp"
#include "axMtaAlarmTimerEntry.hpp"
#include "axSoakTimerQ.hpp"
#include "axDbCARootAlarm.hpp"
#include "axDbCACurrentAlarm.hpp"
#include "axDbCAHistoricalAlarms.hpp"
#include "axDbEmta.hpp"
#include "axDbMta.hpp"
#include "axDbHfc.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axAlarmTrackingMgr * axAlarmTrackingMgr::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axAlarmTrackingMgr::axAlarmTrackingMgr() :
  m_initialized(false),
  m_currentHfcAlarmTable(new axMultipleReaderLock()),
  m_currentMtaAlarmTable(new axMultipleReaderLock())
{
}


//********************************************************************
// destructor:
//********************************************************************
axAlarmTrackingMgr::~axAlarmTrackingMgr()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axAlarmTrackingMgr::axAlarmTrackingMgr()
// {
// }


//********************************************************************
// method:
//********************************************************************
axAlarmTrackingMgr *
axAlarmTrackingMgr::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axAlarmTrackingMgr();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
axCAAlarmCollection *
axAlarmTrackingMgr::getHfcAlarmTable(void)
{
  return (&m_currentHfcAlarmTable);
}


//********************************************************************
// method:
//********************************************************************
axCAAlarmCollection *
axAlarmTrackingMgr::getMtaAlarmTable(void)
{
  return (&m_currentMtaAlarmTable);
}


//********************************************************************
// method:
//********************************************************************
bool
axAlarmTrackingMgr::isCurrentHfcAlarm(axHfcAlarm * o)
{
  bool ret;

  ret = (findCurrentHfcAlarm(o) != NULL);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axHfcAlarm *
axAlarmTrackingMgr::addCurrentHfcAlarm(axHfcAlarm * o)
{
  axHfcAlarm * ret;

  axWriteLockOperator wL(m_currentHfcAlarmTable.getLock());

  ret = findCurrentHfcAlarm_u(o);

  if (!ret) {

    /*
     * Add to DB first
     */
    axDbCARootAlarm rootAlarm(o);
    axDbCACurrentAlarm currAlarm(o);
    axDbCAHistoricalAlarm histAlarm(o);
    if (dbOut.insertRow() && currAlarm.insertRow() && 
                                             histAlarm.insertRow()) {

      /*
       * Add to internal structures
       */

      ret = (axHfcAlarm *) m_currentHfcAlarmTable.add(o);

    }

  } else {
    ret = NULL;
  }

  /*
   * Add entry to the Soak Timer Q so that when the soaking period expires
   * We can take action
   */

  if (ret) {
    int soakWindow = 60 * 20;
    struct timeval currTm;
    gettimeofday(&currTm, NULL);
    currTm.tv_sec += soakWindow;
    axHfcAlarmTimerEntry * soakTmEntry = 
                        new axHfcAlarmTimerEntry(ret, currTm.tv_sec);
    axSoakTimerQ::getInstance()->add(soakTmEntry);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axHfcAlarm *
axAlarmTrackingMgr::findCurrentHfcAlarm(axHfcAlarm * o)
{
  axHfcAlarm * ret;

  axReadLockOperator rL(m_currentHfcAlarmTable.getLock());

  ret = findCurrentHfcAlarm_u(o);

  return (ret);
}


//********************************************************************
// method:
// assumption: either a read or write lock is held by caller
//********************************************************************
axHfcAlarm *
axAlarmTrackingMgr::findCurrentHfcAlarm_u(axHfcAlarm * o)
{
  axHfcAlarm * ret;

  ret = (axHfcAlarm *) m_currentHfcAlarmTable.find(o);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axHfcAlarm *
axAlarmTrackingMgr::removeCurrentHfcAlarm(axHfcAlarm * o)
{
  axHfcAlarm * ret;

  axWriteLockOperator wL(m_currentHfcAlarmTable.getLock());

  ret = (axHfcAlarm *) m_currentHfcAlarmTable.remove(o);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axAlarmTrackingMgr::isCurrentMtaAlarm(axMtaAlarm * o)
{
  bool ret;

  ret = (findCurrentMtaAlarm(o) != NULL);

  return (ret);
}


//********************************************************************
// method:
// NOTE: cant do it here. Have to do in the mtaAlarm->addAlarm() method
//********************************************************************
axMtaAlarm *
axAlarmTrackingMgr::addCurrentMtaAlarm(axMtaAlarm * o)
{
  axMtaAlarm * ret;

  axWriteLockOperator wL(m_currentMtaAlarmTable.getLock());

  ret = findCurrentMtaAlarm_u(o);

  if (!ret) {

    /*
     * Add to DB first
     */
    axDbCARootAlarm rootAlarm(o);
    axDbCACurrentAlarm currAlarm(o);
    axDbCAHistoricalAlarm histAlarm(o);
    if (dbOut.insertRow() && currAlarm.insertRow() &&
                                             histAlarm.insertRow()) {
      /*
       * Add to internal structures
       */

      ret = (axMtaAlarm *) m_currentMtaAlarmTable.add(o);

    }

  } else {
    ret = NULL;
  }

  /*
   * Add entry to the Soak Timer Q so that when the soaking period expires
   * We can take action
   */

  if (ret) {
    int soakWindow = 60 * 20;
    struct timeval currTm;
    gettimeofday(&currTm, NULL);
    currTm.tv_sec += soakWindow;
    axMtaAlarmTimerEntry * soakTmEntry =
                        new axMtaAlarmTimerEntry(ret, currTm.tv_sec);
    axSoakTimerQ::getInstance()->add(soakTmEntry);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axMtaAlarm *
axAlarmTrackingMgr::findCurrentMtaAlarm(axMtaAlarm * o)
{
  axMtaAlarm * ret;

  axReadLockOperator rL(m_currentMtaAlarmTable.getLock());

  ret = findCurrentMtaAlarm_u(o);

  return (ret);
}


//********************************************************************
// method:
// assumption: either a read or write lock is held by caller
//********************************************************************
axMtaAlarm *
axAlarmTrackingMgr::findCurrentMtaAlarm_u(axMtaAlarm * o)
{
  axMtaAlarm * ret;

  ret = (axMtaAlarm *) m_currentMtaAlarmTable.find(o);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axMtaAlarm *
axAlarmTrackingMgr::removeCurrentMtaAlarm(axMtaAlarm * o)
{
  axMtaAlarm * ret;

  axWriteLockOperator wL(m_currentMtaAlarmTable.getLock());

  ret = (axMtaAlarm *) m_currentMtaAlarmTable.remove(o);

  return (ret);
}


