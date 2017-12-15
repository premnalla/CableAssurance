
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <sys/time.h>
#include "axOutageTrackingMgr.hpp"
#include "axMultipleReaderLock.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axHfcOutageTimerEntry.hpp"
#include "axSoakTimerQ.hpp"
#include "axDbCACurrentOutage.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axOutageTrackingMgr * axOutageTrackingMgr::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axOutageTrackingMgr::axOutageTrackingMgr() :
  m_initialized(false),
  m_currentHfcOutageTable(new axMultipleReaderLock()),
  m_currentMtaOutageTable(new axMultipleReaderLock())
{
}


//********************************************************************
// destructor:
//********************************************************************
axOutageTrackingMgr::~axOutageTrackingMgr()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axOutageTrackingMgr::axOutageTrackingMgr()
// {
// }


//********************************************************************
// method:
//********************************************************************
axOutageTrackingMgr *
axOutageTrackingMgr::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axOutageTrackingMgr();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
bool
axOutageTrackingMgr::isCurrentHfcOutage(axHfcOutage * o)
{
  bool ret;

  ret = (findCurrentHfcOutage(o) != NULL);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axHfcOutage *
axOutageTrackingMgr::addCurrentHfcOutage(axHfcOutage * o)
{
  axHfcOutage * ret;

  ret = findCurrentHfcOutage(o);

  if (!ret) {

    axWriteLockOperator wL(m_currentHfcOutageTable.getLock());

    /*
     * Add to DB first
     */
    axDbCACurrentOutage dbOut(o);
    if (dbOut.insertRow()) {

      /*
       * Add to internal structures
       */

      ret = (axHfcOutage *) m_currentHfcOutageTable.add(o);

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
    axHfcOutageTimerEntry * soakTmEntry = 
                        new axHfcOutageTimerEntry(ret, currTm.tv_sec);
    axSoakTimerQ::getInstance()->add(soakTmEntry);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axHfcOutage *
axOutageTrackingMgr::findCurrentHfcOutage(axHfcOutage * o)
{
  axHfcOutage * ret;

  axReadLockOperator rL(m_currentHfcOutageTable.getLock());

  ret = (axHfcOutage *) m_currentHfcOutageTable.find(o);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axHfcOutage *
axOutageTrackingMgr::removeCurrentHfcOutage(axHfcOutage * o)
{
  axHfcOutage * ret;

  axWriteLockOperator wL(m_currentHfcOutageTable.getLock());

  ret = (axHfcOutage *) m_currentHfcOutageTable.remove(o);

  return (ret);
}



