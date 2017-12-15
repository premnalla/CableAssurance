
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcOutageTimerEntry.hpp"
#include "axWriteLockOperator.hpp"
#include "axHfcOutagePercent.hpp"
#include "axOutageTrackingMgr.hpp"
#include "axInternalUtils.hpp"
// #include "axGlobalData.hpp"
// #include "axCAOutageCollection.hpp"

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
axHfcOutageTimerEntry::axHfcOutageTimerEntry() :
  m_cmtsResId(0),
  m_alarmType(0),
  m_alarmSubType(0)
{
  m_hfcName[0] = '\0';
}


//********************************************************************
// destructor:
//********************************************************************
axHfcOutageTimerEntry::~axHfcOutageTimerEntry()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcOutageTimerEntry::axHfcOutageTimerEntry(
    axHfcOutage * hfcOutage, time_t & popTime) :
  axOutageTimerEntry(hfcOutage, popTime),
  m_cmtsResId(hfcOutage->getCmtsResId()),
  m_alarmType(hfcOutage->getAlarmType()),
  m_alarmSubType(hfcOutage->getAlarmSubType())
{
  copyIntHfcName(m_hfcName, hfcOutage->getHfcName());
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcOutageTimerEntry::timerCallback(void)
{
  bool ret = true;

  printf("In axHfcOutageTimerEntry::timerCallback() entry...\n");

  bool found = false;
  axHfcOutage * hPtr;
  axHfcOutagePercent hP(m_cmtsResId, m_hfcName);

  axOutageTrackingMgr * mgr = axOutageTrackingMgr::getInstance();

  hPtr = mgr->findCurrentHfcOutage(&hP);
  if ( hPtr && (hPtr->getOutageId()==getOutageId()) ) {
    found = true;
    hPtr = mgr->removeCurrentHfcOutage(hPtr);
    if (!hPtr) {
      goto EXIT_LABEL;
    }
  }

  if (!found) {
    /*
     * Check for other types of HFC outages (e.g. HFC Power)
     */
  }

  if (!found) {
    goto EXIT_LABEL;
  }

  /*
   * Do post soak check for the outage
   */


  /*
   * If condition still exists, excalate to ticketing etc.
   */


  /*
   * If condition does not exist anymore, clear
   */


  /*
   * Updat DB appropriately (Current, History, etc)
   */


  /*
   * Anything else ...
   */


  /*
   * cleanup
   */



  // lastly but not least ...
  // ret = true;

EXIT_LABEL:

  printf("In axHfcOutageTimerEntry::timerCallback() exit...\n");

  return (ret);
}


#if 0
//********************************************************************
// method:
//********************************************************************
INTDS_RESID_t
axHfcOutageTimerEntry::getCmtsResId(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcOutageTimerEntry::getHfcName(void)
{
  return (m_hfcName);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcOutageTimerEntry::isKeyEqual(axObject * o)
{
  bool ret = false;

  axHfcOutageTimerEntry * o2 = dynamic_cast<axHfcOutageTimerEntry *> (o);

  // if ()


  ret = true;

EXIT_LABEL:

  return (ret);
}
#endif


