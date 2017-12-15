
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axHfcAlarmDetectionRunnableCollection.hpp"
#include "axWriteLockOperator.hpp"
#include "axGlobalData.hpp"
#include "axInternalCmts.hpp"
#include "axCAMessage.hpp"
#include "axMessageManager.hpp"
#include "axCASystemConfig.hpp"
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
axHfcAlarmDetectionRunnableCollection::
                             axHfcAlarmDetectionRunnableCollection() :
  m_cmtsResId(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcAlarmDetectionRunnableCollection::
                              ~axHfcAlarmDetectionRunnableCollection()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcAlarmDetectionRunnableCollection::
      axHfcAlarmDetectionRunnableCollection(INTDS_RESID_t cmtsResId) :
  m_cmtsResId(cmtsResId)
{
}


//********************************************************************
// method : hashCode
//********************************************************************
AX_INT64
axHfcAlarmDetectionRunnableCollection::hashCode(void)
{
  return ((AX_INT64) m_cmtsResId);
}


//********************************************************************
// method : hashUInt32
//********************************************************************
AX_UINT32
axHfcAlarmDetectionRunnableCollection::hashUInt32(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method : 
//********************************************************************
axAbstractCARunnable *
axHfcAlarmDetectionRunnableCollection::createNewRunnable(void)
{
  return (NULL);
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarmDetectionRunnableCollection::canScheduleRunnables(void)
{
  return (true);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcAlarmDetectionRunnableCollection::postponeTask(void)
{
}


//********************************************************************
// method: 
//********************************************************************
void
axHfcAlarmDetectionRunnableCollection::processRunnableComplete(
                                              axAbstractRunnable * in)
{
  static const char * myName="hfcAlarmDetectRblComplete:";

  // axCASystemConfig * sysCfg = axCASystemConfig::getInstance();

  struct timeval currTm;
  gettimeofday(&currTm, NULL);
  setWorkEndTime(currTm.tv_sec);

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
    ACE_DEBUG((LM_ERROR, "%s Couldn't find intCmts\n", myName));
  }

  ACE_DEBUG((LM_MISC_DEBUG, "%s cmts=%d, start=%d, stop=%d\n",
                        myName, m_cmtsResId, (int)getWorkStartTime(),
                                             (int)getWorkEndTime()));

}


