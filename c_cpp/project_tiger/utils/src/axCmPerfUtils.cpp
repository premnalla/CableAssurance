
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCmPerfUtils.hpp"
#include "axCASystemConfig.hpp"
#include "axDbCurrentCmPerf.hpp"
#include "axDbCmPerfLog.hpp"
#include "axDbCmStatusLog.hpp"

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
axCmPerfUtils::axCmPerfUtils()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmPerfUtils::~axCmPerfUtils()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCmPerfUtils::axCmPerfUtils()
// {
// }


//********************************************************************
// method:
//********************************************************************
bool
axCmPerfUtils::IsCmThresholdCrossed(
                       AX_INT16 snr, AX_INT16 downPwr, AX_INT16 upPwr)
{
  bool ret;

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  if (snr <= sysCfg->get_cm_snr_threshold()
      || downPwr <= sysCfg->get_cm_dwn_pwr_low_threshold() ||
         downPwr >= sysCfg->get_cm_dwn_pwr_high_threshold()
      || upPwr <= sysCfg->get_cm_up_pwr_low_threshold() ||
         upPwr >= sysCfg->get_cm_up_pwr_high_threshold())
  {
    ret = true;
  }
  else
  {
    ret = false;
  }

  return (ret);
}

//********************************************************************
// method:
// Assumptions: CM lock is held by caller
//********************************************************************
bool
axCmPerfUtils::NewTcaUpdateWork(bool isAlarm, time_t tv_sec,
                                               axIntCmNonkey_t * cmNk)
{
  static const char * myName = "Cm:NewTcaUpdateWork:";

  bool ret = true;

  // axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  cmNk->tcaStateChanges++;

  AX_UINT8 tca;

  if (isAlarm) {
    tca = 1;
  } else {
    tca = 0;
  }

  axDbCurrentCmPerf currPerf(cmNk->resId);
  if (currPerf.getRow()) {

    if (isAlarm) {
      // tcafree-time
      currPerf.m_sumNonTcaTime += tv_sec - cmNk->tcaChangeTime;
    } else {
      // alarm-time
      currPerf.m_sumTcaTime += tv_sec - cmNk->tcaChangeTime;
    }

    currPerf.m_lastLogTime = tv_sec;

    currPerf.m_lastTcaChangeTime = tv_sec;
    currPerf.m_stateChanges = cmNk->tcaStateChanges;

    currPerf.m_downstreamSnr=cmNk->downstreamSNR;
    currPerf.m_downstreamPower=cmNk->downstreamPower;
    currPerf.m_upstreamPower=cmNk->upstreamPower;
    currPerf.m_t1Timeouts=cmNk->t1Timeouts;
    currPerf.m_t2Timeouts=cmNk->t2Timeouts;
    currPerf.m_t3Timeouts=cmNk->t3Timeouts;
    currPerf.m_t4Timeouts=cmNk->t4Timeouts;

    if (currPerf.updateRow()) {
      cmNk->tca = tca;
      cmNk->tcaChangeTime = tv_sec;
    } else {
      ACE_DEBUG((LM_WARNING, "%s update failed", myName));
    }

  } else {
    ACE_DEBUG((LM_WARNING, "%s get failed", myName));
    ret = false;
  }

  axDbCmPerfLog log(cmNk->resId, tv_sec, cmNk);
  log.m_lastLogTime = tv_sec;
  if (log.insertRow()) {
    cmNk->lastPerfLogTime = tv_sec;
  } else {
    ACE_DEBUG((LM_WARNING, "%s log failed", myName));
    ret = false;
  }

  return (ret);
}


//********************************************************************
// method:
// Assumptions: CM lock is held by caller
//********************************************************************
bool
axCmPerfUtils::LogTcaWork(time_t tv_sec, axIntCmNonkey_t * cmNk)
{
  static const char * myName = "Cm:LogTcaWork:";

  bool ret = true;

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  if ((tv_sec - cmNk->lastPerfLogTime) >
                        (sysCfg->get_unchanged_log_interval() * 60)) {
    axDbCmPerfLog log(cmNk->resId, tv_sec, cmNk);
    log.m_lastLogTime = tv_sec;
    if (log.insertRow()) {
      cmNk->lastPerfLogTime = tv_sec;
    } else {
      ACE_DEBUG((LM_WARNING, "%s log failed", myName));
      ret = false;
    }
  }

  return (ret);
}


//********************************************************************
// method:
// Assumptions: CM lock is held by caller
//********************************************************************
bool
axCmPerfUtils::LogStatusWork(time_t tv_sec, axIntCmNonkey_t * cmNk)
{
  static const char * myName = "Cm:LogStatusWork:";

  bool ret = true;

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  if ((tv_sec - cmNk->lastStateLogTime) >
                        (sysCfg->get_unchanged_log_interval() * 60)) {
    axDbCmStatusLog log(cmNk->resId, tv_sec, cmNk);
    if (log.insertRow()) {
      cmNk->lastStateLogTime = tv_sec;
    } else {
      ACE_DEBUG((LM_WARNING, "%s log failed", myName));
      ret = false;
    }
  }

  return (ret);
}


