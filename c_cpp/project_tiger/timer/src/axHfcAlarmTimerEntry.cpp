
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axHfcAlarmTimerEntry.hpp"
#include "axHfcCurrentAlarmTable.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axHfcAlarmPercent.hpp"
#include "axHfcAlarmPower.hpp"
#include "axHfcAlarmMtaCount.hpp"
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
axHfcAlarmTimerEntry::axHfcAlarmTimerEntry() :
  m_cmtsResId(0),
  m_alarmType(0),
  m_alarmSubType(0),
  m_intHfc(NULL)
{
  m_hfcName[0] = '\0';
}


//********************************************************************
// destructor:
//********************************************************************
axHfcAlarmTimerEntry::~axHfcAlarmTimerEntry()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcAlarmTimerEntry::axHfcAlarmTimerEntry(
    axHfcAlarm * hfcAlarm, time_t & popTime) :
  axAlarmTimerEntry(hfcAlarm, popTime),
  m_cmtsResId(hfcAlarm->getCmtsResId()),
  m_alarmType(hfcAlarm->getAlarmType()),
  m_alarmSubType(hfcAlarm->getAlarmSubType()),
  m_intHfc(hfcAlarm->getInternalHfc())
{
  copyIntHfcName(m_hfcName, hfcAlarm->getHfcName());
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarmTimerEntry::timerCallback(void)
{
  static const char * myName="hfcAlmTimerCb:";

  bool ret = true;

  ACE_DEBUG((LM_TIMER_DEBUG, "%s entry\n", myName));

  axHfcAlarm * genericHfcAlm = NULL;

  axHfcAlarmPercent hPercent(m_cmtsResId, m_hfcName);
  axHfcAlarm * hPercentPtr;

  axHfcAlarmPower hPower(m_cmtsResId, m_hfcName);
  axHfcAlarm * hPowerPtr;

  axHfcAlarmMtaCount hMtaCount(m_cmtsResId, m_hfcName);
  axHfcAlarm * hMtaCountPtr;

  axHfcCurrentAlarmTable * mgr = axHfcCurrentAlarmTable::getInstance();

  {
    axWriteLockOperator tblWlock(mgr->getLock());

    /*
     * Find alarm in internal structures
     */
    hPercentPtr = (axHfcAlarmPercent *) mgr->find(&hPercent);
    hPowerPtr = (axHfcAlarmPower *) mgr->find(&hPower);
    hMtaCountPtr = (axHfcAlarmMtaCount *) mgr->find(&hMtaCount);

    if ( hPercentPtr && (hPercentPtr->getInternalAlarmId()
                                           ==getInternalAlarmId()) ) {
      hPercentPtr = (axHfcAlarmPercent *) mgr->remove(hPercentPtr);
      genericHfcAlm = hPercentPtr;
    }

    if ( !genericHfcAlm && hPowerPtr && 
          (hPowerPtr->getInternalAlarmId()==getInternalAlarmId()) ) {
      hPowerPtr = (axHfcAlarmPower *) mgr->remove(hPowerPtr);
      genericHfcAlm = hPowerPtr;
    }

    if ( !genericHfcAlm && hMtaCountPtr && 
       (hMtaCountPtr->getInternalAlarmId()==getInternalAlarmId()) ) {
      hMtaCountPtr = (axHfcAlarmMtaCount *) mgr->remove(hMtaCountPtr);
      genericHfcAlm = hMtaCountPtr;
    }

  }

  if (!genericHfcAlm) {
    goto EXIT_LABEL;
  }

  /*
   * Do post soak check for the alarm
   */

  genericHfcAlm->postSoakCheckEscalate();

  delete genericHfcAlm;

EXIT_LABEL:

  ACE_DEBUG((LM_TIMER_DEBUG, "%s exit\n", myName));

  return (ret);
}


#if 0
//********************************************************************
// method:
//********************************************************************
INTDS_RESID_t
axHfcAlarmTimerEntry::getCmtsResId(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcAlarmTimerEntry::getHfcName(void)
{
  return (m_hfcName);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarmTimerEntry::isKeyEqual(axObject * o)
{
  bool ret = false;

  axHfcAlarmTimerEntry * o2 = dynamic_cast<axHfcAlarmTimerEntry *> (o);

  // if ()


  ret = true;

EXIT_LABEL:

  return (ret);
}
#endif


