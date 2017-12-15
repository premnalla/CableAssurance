
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axMtaAlarmTimerEntry.hpp"
#include "axHfcCurrentAlarmTable.hpp"
#include "axMtaCurrentAlarmTable.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axHfcAlarmPower.hpp"
#include "axMtaAlarmOnBattery.hpp"
#include "axMtaAlarmUnavail.hpp"
#include "axMtaAlarmOnBattery.hpp"
#include "axMtaAlarmBattMissing.hpp"
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
axMtaAlarmTimerEntry::axMtaAlarmTimerEntry() :
  m_cmtsResId(0),
  m_alarmType(0),
  m_alarmSubType(0),
  m_intMta(NULL)
{
  m_mtaMacAddress[0] = '\0';
}


//********************************************************************
// destructor:
//********************************************************************
axMtaAlarmTimerEntry::~axMtaAlarmTimerEntry()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axMtaAlarmTimerEntry::axMtaAlarmTimerEntry(
    axMtaAlarm * mtaAlarm, time_t & popTime) :
  axAlarmTimerEntry(mtaAlarm, popTime),
  m_cmtsResId(mtaAlarm->getCmtsResId()),
  m_alarmType(mtaAlarm->getAlarmType()),
  m_alarmSubType(mtaAlarm->getAlarmSubType()),
  m_intMta(mtaAlarm->getInternalMta())
{
  copyIntMac(m_mtaMacAddress, mtaAlarm->getMtaMacAddress());
}


//********************************************************************
// method:
//********************************************************************
bool
axMtaAlarmTimerEntry::timerCallback(void)
{
  bool ret = true;

  printf("In axMtaAlarmTimerEntry::timerCallback() entry...\n");

  axMtaAlarm * genericMtaAlm = NULL;

  axMtaAlarmUnavail   mtaUnavailAlm(m_cmtsResId, m_mtaMacAddress);
  axMtaAlarmUnavail * mtaUnavailAlmPtr;

  axMtaAlarmOnBattery   mtaOnBattAlm(m_cmtsResId, m_mtaMacAddress);
  axMtaAlarmOnBattery * mtaOnBattAlmPtr;

  axMtaAlarmBattMissing   mtaBattMissAlm(m_cmtsResId, m_mtaMacAddress);
  axMtaAlarmBattMissing * mtaBattMissAlmPtr;

  axMtaCurrentAlarmTable * almTbl = 
                                axMtaCurrentAlarmTable::getInstance();

  {
    axWriteLockOperator tblWlock(almTbl->getLock());

    /*
     * Find alarm in internal structures
     */
    mtaUnavailAlmPtr = (axMtaAlarmUnavail *) 
                                        almTbl->find(&mtaUnavailAlm);
    mtaOnBattAlmPtr = (axMtaAlarmOnBattery *) 
                                         almTbl->find(&mtaOnBattAlm);
    mtaBattMissAlmPtr = (axMtaAlarmBattMissing *) 
                                       almTbl->find(&mtaBattMissAlm);

    if ( mtaUnavailAlmPtr && (mtaUnavailAlmPtr->getInternalAlarmId()
                                         ==getInternalAlarmId()) ) {
      mtaUnavailAlmPtr = (axMtaAlarmUnavail *) 
                                    almTbl->remove(mtaUnavailAlmPtr);
      genericMtaAlm = mtaUnavailAlmPtr;
    }

    if ( !genericMtaAlm && mtaOnBattAlmPtr &&
                           (mtaOnBattAlmPtr->getInternalAlarmId()==
                                            getInternalAlarmId()) ) {
      mtaOnBattAlmPtr = (axMtaAlarmOnBattery *) 
                                     almTbl->remove(mtaOnBattAlmPtr);
      genericMtaAlm = mtaOnBattAlmPtr;
    }

    if ( !genericMtaAlm && mtaBattMissAlmPtr &&
                          (mtaBattMissAlmPtr->getInternalAlarmId()==
                                            getInternalAlarmId()) ) {
      mtaBattMissAlmPtr = (axMtaAlarmBattMissing *) 
                                   almTbl->remove(mtaBattMissAlmPtr);
      genericMtaAlm = mtaBattMissAlmPtr;
    }

  }

  if (!genericMtaAlm) {
    goto EXIT_LABEL;
  }

  /*
   * Do post soak check for the alarm
   */

  genericMtaAlm->postSoakCheckEscalate();

  delete genericMtaAlm;

EXIT_LABEL:

  printf("In axMtaAlarmTimerEntry::timerCallback() exit...\n");

  return (ret);
}


#if 0
//********************************************************************
// method:
//********************************************************************
INTDS_RESID_t
axMtaAlarmTimerEntry::getCmtsResId(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axMtaAlarmTimerEntry::getHfcName(void)
{
  return (m_hfcName);
}


//********************************************************************
// method:
//********************************************************************
bool
axMtaAlarmTimerEntry::isKeyEqual(axObject * o)
{
  bool ret = false;

  axMtaAlarmTimerEntry * o2 = dynamic_cast<axMtaAlarmTimerEntry *> (o);

  // if ()


  ret = true;

EXIT_LABEL:

  return (ret);
}
#endif


