
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcOutageDetectionRunnableCollection.hpp"
#include "axWriteLockOperator.hpp"
#include "axGlobalData.hpp"
#include "axInternalCmts.hpp"

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
axHfcOutageDetectionRunnableCollection::axHfcOutageDetectionRunnableCollection()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcOutageDetectionRunnableCollection::~axHfcOutageDetectionRunnableCollection()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcOutageDetectionRunnableCollection::axHfcOutageDetectionRunnableCollection(
    INTDS_RESID_t cmtsResId) :
  m_cmtsResId(cmtsResId)
{
}


//********************************************************************
// method : hashCode
//********************************************************************
AX_INT64
axHfcOutageDetectionRunnableCollection::hashCode(void)
{
  return ((AX_INT64) m_cmtsResId);
}


//********************************************************************
// method : hashUInt32
//********************************************************************
AX_UINT32
axHfcOutageDetectionRunnableCollection::hashUInt32(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method: getNextRunnableId
//********************************************************************
void
axHfcOutageDetectionRunnableCollection::processRunnableComplete(
                                              axAbstractRunnable * in)
{

  if (areAllRunnablesComplete()) {

    axWriteLockOperator rwCmtsPollRc(getLock());

    struct timeval tm;
    gettimeofday(&tm, NULL);

    setWorkEndTime(tm.tv_sec);
    clearInProgress();

    axIntCmtsKey_t cmtsKey;
    cmtsKey.resId = m_cmtsResId;

    axInternalCmts * intCmts = axGlobalData::getInstance()->findCmts(cmtsKey);

    if (intCmts) {
      // printf("Found intCmts\n");

      axWriteLockOperator wLockCmts(intCmts->getLock());
      intCmts->clearHfcOutageDetectionInProgress();

      setNextCycleScheduled();

    } else {
      printf("Couldn't find intCmts\n");
    }

  }

}



