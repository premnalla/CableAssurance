
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axHfcMtaAlarmDetectionRC.hpp"
#include "axWriteLockOperator.hpp"
#include "axGlobalData.hpp"
#include "axInternalCmts.hpp"
#include "axCAMessage.hpp"
#include "axMessageManager.hpp"
#include "axCATaskCoordHelper.hpp"

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
axHfcMtaAlarmDetectionRC::axHfcMtaAlarmDetectionRC() :
  m_cmtsResId(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcMtaAlarmDetectionRC::~axHfcMtaAlarmDetectionRC()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcMtaAlarmDetectionRC::axHfcMtaAlarmDetectionRC(
                                            INTDS_RESID_t cmtsResId) :
  m_cmtsResId(cmtsResId)
{
}


//********************************************************************
// method : hashCode
//********************************************************************
AX_INT64
axHfcMtaAlarmDetectionRC::hashCode(void)
{
  return ((AX_INT64) m_cmtsResId);
}


//********************************************************************
// method : hashUInt32
//********************************************************************
AX_UINT32
axHfcMtaAlarmDetectionRC::hashUInt32(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method : 
//********************************************************************
axAbstractCARunnable *
axHfcMtaAlarmDetectionRC::createNewRunnable(void)
{
  return (NULL);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcMtaAlarmDetectionRC::canScheduleRunnables(void)
{
  return (true);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcMtaAlarmDetectionRC::postponeTask(void)
{
}


//********************************************************************
// method: 
//********************************************************************
void
axHfcMtaAlarmDetectionRC::processRunnableComplete(
                                              axAbstractRunnable * in)
{
  static const char * myName="hfcMtaAlmDetectRcComplete:";

  axWriteLockOperator rwCmtsPollRc(getLock());

  struct timeval tm;
  gettimeofday(&tm, NULL);

  setWorkEndTime(tm.tv_sec);
  clearInProgress();

  axIntCmtsKey_t cmtsKey;
  cmtsKey.resId = m_cmtsResId;

  axInternalCmts * intCmts = 
                      axGlobalData::getInstance()->findCmts(cmtsKey);

  if (intCmts) {

    axWriteLockOperator wLockCmts(intCmts->getLock());

    intCmts->clearHfcAlarmDetectionInProgress();

    axCAMessage * outstandingMsg = (axCAMessage *)
                                      intCmts->removeCountsMessage();
    if (outstandingMsg) {
      axMessageManager::getInstance()->sendMessage(outstandingMsg);
    }

    setNextCycleScheduled();

  } else {
    ACE_DEBUG((LM_WARNING, "%s Couldn't find intCmts\n", myName));
  }

}


