
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axHfcAlarmUtils.hpp"
#include "axCASystemConfig.hpp"
#include "axDbHfcCurrentStatus.hpp"
#include "axDbHfcStatusLog.hpp"

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
axHfcAlarmUtils::axHfcAlarmUtils()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcAlarmUtils::~axHfcAlarmUtils()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axHfcAlarmUtils::axHfcAlarmUtils()
// {
// }


//********************************************************************
// method:
// Assumptions: HFC lock is held by caller
//********************************************************************
bool
axHfcAlarmUtils::NewAlarmUpdateWork(bool isAlarm, time_t tv_sec, 
                                         axIntHfcNonkey_t * hfcNonkey)
{
  static const char * myName = "HFC:NewAlarmUpdateWork:";

  bool ret = true;

  // axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  hfcNonkey->percentAlarmChanges++;

  AX_UINT8 alarm;
  if (isAlarm) {
    alarm = 1;
  } else {
    alarm = 0;
  }

  axDbHfcCurrentStatus currAlarm(hfcNonkey->resId, tv_sec, hfcNonkey);
  if (currAlarm.getRow()) {

    if (isAlarm) {
      // alarm-free-time
      currAlarm.m_time1 += tv_sec - hfcNonkey->percentAlarmChangeTime;
    } else {
      // alarm-time
      currAlarm.m_time2 += tv_sec - hfcNonkey->percentAlarmChangeTime;
    }

    currAlarm.m_lastLogTime = tv_sec;
    currAlarm.m_lastStateChangeTime = tv_sec;
    currAlarm.m_currentValue = alarm;
    currAlarm.m_stateChanges = hfcNonkey->percentAlarmChanges;;
    if (currAlarm.updateRow()) {
      hfcNonkey->percentAlarm = alarm;
      hfcNonkey->percentAlarmChangeTime = tv_sec;
    } else {
      ACE_DEBUG((LM_WARNING, "%s update failed", myName));
    }

  } else {
    ACE_DEBUG((LM_WARNING, "%s get failed", myName));
    ret = false;
  }

  axDbHfcStatusLog log(hfcNonkey->resId, tv_sec, hfcNonkey);
  log.m_lastLogTime = tv_sec;
  if (log.insertRow()) {
    hfcNonkey->lastAlarmLogTime = tv_sec;
  } else {
    ACE_DEBUG((LM_WARNING, "%s log failed", myName));
    ret = false;
  }

  return (ret);
}


//********************************************************************
// method:
// Assumptions: HFC lock is held by caller
//********************************************************************
bool
axHfcAlarmUtils::LogAlarmWork(time_t tv_sec,
                                         axIntHfcNonkey_t * hfcNonkey)
{
  static const char * myName = "HFC:LogAlarmWork:";

  bool ret = true;

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  if ((tv_sec - hfcNonkey->lastAlarmLogTime) > 
                        (sysCfg->get_unchanged_log_interval() * 60)) {
    axDbHfcStatusLog log(hfcNonkey->resId, tv_sec, hfcNonkey);
    log.m_lastLogTime = tv_sec;
    if (log.insertRow()) {
      hfcNonkey->lastAlarmLogTime = tv_sec;
    } else {
      ACE_DEBUG((LM_WARNING, "%s log failed", myName));
      ret = false;
    }
  }

  return (ret);
}


