
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCblAssureConstants.hpp"
#include "axMtaStatusUtils.hpp"
#include "axCASystemConfig.hpp"
#include "axDbMtaCurrentAvailability.hpp"
#include "axDbMtaAvailLog.hpp"
#include "axDbEmta.hpp"

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
axMtaStatusUtils::axMtaStatusUtils()
{
}


//********************************************************************
// destructor:
//********************************************************************
axMtaStatusUtils::~axMtaStatusUtils()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axMtaStatusUtils::axMtaStatusUtils()
// {
// }


//********************************************************************
// method:
// Assumptions: MTA lock is held by caller
//********************************************************************
bool
axMtaStatusUtils::NewStatusUpdateWork(bool isDown, time_t tv_sec, 
                                          axIntGenMtaNonkey_t * mtaNk)
{
  static const char * myName = "MTA:NewStatusUpdateWork:";

  bool ret = false;
  AX_UINT32 oldAvailChangeTime = 0;

  // axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  mtaNk->availChanges++;

  AX_UINT8 avail;

  if (isDown) {
    avail = AX_AVAIL_ST_UNAVAILABLE;
  } else {
    avail = AX_AVAIL_ST_AVAILABLE;
  }
  
  axDbEmta dbEmta(mtaNk->resId);
  if (!dbEmta.getRow()) {
    goto EXIT_LABEL;
  }

  dbEmta.m_available = avail;
  if (!dbEmta.updateRow()) {
    goto EXIT_LABEL;
  }

  mtaNk->available = avail;
  oldAvailChangeTime = mtaNk->availChangeTime;
  mtaNk->availChangeTime = tv_sec;

  if (isDown) {
    mtaNk->justBecameUnavailable = 1;
  }

  {
    axDbMtaAvailLog aLog(mtaNk->resId, tv_sec, mtaNk);
    aLog.m_lastLogTime = tv_sec;
    if (aLog.insertRow()) {
      mtaNk->lastAvailLogTime = tv_sec;
    } else {
      ACE_DEBUG((LM_WARNING, "%s log failed", myName));
    }
  }

  {
    axDbMtaCurrentAvailability currAvail(mtaNk->resId);
    if (!currAvail.getRow()) {
      goto EXIT_LABEL;
    }

    if (mtaNk->available) {
      // set sum-unavail-time
      currAvail.m_time2 += tv_sec - oldAvailChangeTime;
    } else {
      // set sum-avail-time
      currAvail.m_time1 += tv_sec - oldAvailChangeTime;
    }

    currAvail.m_lastLogTime = tv_sec;
    currAvail.m_lastStateChangeTime = tv_sec;
    if (!currAvail.updateRow()) {
      goto EXIT_LABEL;
    }

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
// Assumptions: MTA lock is held by caller
//********************************************************************
bool
axMtaStatusUtils::LogStatusWork(time_t tv_sec,
                                          axIntGenMtaNonkey_t * mtaNk)
{
  static const char * myName = "MTA:LogStatusWork:";

  bool ret = true;

  axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  if ((tv_sec - mtaNk->lastAvailLogTime) >
                        (sysCfg->get_unchanged_log_interval() * 60)) {
    axDbMtaAvailLog aLog(mtaNk->resId, tv_sec, mtaNk);
    aLog.m_lastLogTime = tv_sec;
    if (aLog.insertRow()) {
      mtaNk->lastAvailLogTime = tv_sec;
    } else {
      ACE_DEBUG((LM_WARNING, "%s log failed", myName));
      ret = false;
    }
  }

  return (ret);
}


