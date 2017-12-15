
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCorrelatedAlarmDetection.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axAvlIterator.hpp"
#include "axAbstractIterator.hpp"
#include "axInternalCm.hpp"
#include "axInternalGenMta.hpp"
#include "axGenericIntegerHolder.hpp"
#include "axListIterator.hpp"
#include "axIteratorHolder.hpp"
#include "axHfcAlarmPercent.hpp"
#include "axHfcAlarmMtaCount.hpp"
#include "axDbHfcCurrentCounts.hpp"
#include "axDbHfcCountsLog.hpp"
#include "axAlarmProcessingMsg.hpp"
#include "axMessageManager.hpp"
#include "axSubSystemCommon.hpp"

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
axCorrelatedAlarmDetection::axCorrelatedAlarmDetection() :
  m_alarmDetectStartTime(0),
  m_alarmThreshold(0),
  m_alarmDetectWindow(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCorrelatedAlarmDetection::~axCorrelatedAlarmDetection()
{
}


//********************************************************************
// method:
//********************************************************************
void
axCorrelatedAlarmDetection::setAlarmDetectionStartTime(time_t in)
{
  m_alarmDetectStartTime = in;
}


//********************************************************************
// method:
//********************************************************************
void
axCorrelatedAlarmDetection::setAlarmThreshold(AX_UINT16 in)
{
  m_alarmThreshold = in;
}


//********************************************************************
// method:
//********************************************************************
void
axCorrelatedAlarmDetection::setAlarmDetectionWindow(AX_UINT16 in)
{
  m_alarmDetectWindow = in;
}


//********************************************************************
// method:
// NOTE: that a HFC/Channel lock is held before entering this method
//********************************************************************
void
axCorrelatedAlarmDetection::determineCmCounts(
                axSortedList & sortedList, axIntCmCounts_t & cmCounts)
{
  struct timeval currTime;

  cmCounts.total = 0;
  cmCounts.online = 0;

  axIteratorHolder iH(getCableModems());
  axAbstractIterator * cmIter = iH.getIterator();

  gettimeofday(&currTime, NULL);

  axInternalCm * cm;
  for (cm = dynamic_cast<axInternalCm *> (cmIter->getFirst()); cm;
       cm = dynamic_cast<axInternalCm *> (cmIter->getNext())) {

    axReadLockOperator cmRLock(cm->getLock());

    if (!cm->hasData() || !cm->isCmEndUserDevice()) {
      continue;
    }

    axIntCmNonkey_t * cmNonkey = (axIntCmNonkey_t *) cm->getNonKey();

    ++cmCounts.total;

    if (axInternalCm::isCmOnline(cmNonkey->docsisState)) {
      cmCounts.online++;
    } else {
      if (cmNonkey->onlineChangeTime > m_alarmDetectStartTime) {
        axGenericIntegerHolder * iH = new
          axGenericIntegerHolder(cm, cmNonkey->onlineChangeTime);
        sortedList.add(iH);
      }
    }

  }

  return;
}


//********************************************************************
// method:
// NOTE: that a HFC/Channel lock is held before entering this method
//********************************************************************
void
axCorrelatedAlarmDetection::determineMtaCounts(
              axSortedList & sortedList, axIntMtaCounts_t & mtaCounts)
{
  struct timeval currTime;

  mtaCounts.total = 0;
  mtaCounts.available = 0;

  axIteratorHolder iH(getMTAs());
  axAbstractIterator * mtaIter = iH.getIterator();

  gettimeofday(&currTime, NULL);

  axInternalGenMta * gMta;
  for (gMta = dynamic_cast<axInternalGenMta *>(mtaIter->getFirst());
       gMta;
       gMta = dynamic_cast<axInternalGenMta *> (mtaIter->getNext())) {

    axReadLockOperator mtaRLock(gMta->getLock());

    if (!gMta->hasData()) {
      continue;
    }

    axIntGenMtaNonkey_t * mtaNonkey = (axIntGenMtaNonkey_t *)
                                                    gMta->getNonKey();

    ++mtaCounts.total;

    if (mtaNonkey->available) {
      ++mtaCounts.available;
    } else {
      if (mtaNonkey->availChangeTime > m_alarmDetectStartTime) {
        axGenericIntegerHolder * iH = new
          axGenericIntegerHolder(gMta, mtaNonkey->availChangeTime);
        sortedList.add(iH);
      }
    }

  }

  return;
}


//********************************************************************
// method:
// NOTE: that a HFC/Channel lock is held before entering this method
//********************************************************************
bool
axCorrelatedAlarmDetection::determineMtaCountAlarm(
              axSortedList & sortedList, axIntMtaCounts_t & mtaCounts, 
                                     axListBase & offendingDeviceList)
{
  axGenericIntegerHolder * aMtaHolder;
  int                      numOfflineInWindow;
  bool                     alarmDetected = false;

  while ( !alarmDetected && 
      (aMtaHolder = (axGenericIntegerHolder *) sortedList.remove()) ) {

    axInternalGenMta * aGMta = 
                          (axInternalGenMta *) aMtaHolder->getObject();
    time_t aMtaTime;
    {
      axReadLockOperator aGmtaRLock(aGMta->getLock());
      if (aGMta->hasData()) {
        axIntGenMtaNonkey_t * aMtaNonkey = 
                            (axIntGenMtaNonkey_t *) aGMta->getNonKey();
        aMtaTime = aMtaNonkey->availChangeTime;
      } else {
        aMtaTime = 0;
      }
    }

    if (!aMtaTime || aMtaTime < m_alarmDetectStartTime) {
      delete aMtaHolder;
      continue;
    }

    axIteratorHolder sortedIH(sortedList.createIterator());
    axAbstractIterator * sortedListIter = sortedIH.getIterator();

    numOfflineInWindow = 1;

    offendingDeviceList.clear();
    offendingDeviceList.add(aGMta);

    axGenericIntegerHolder * bMtaHolder;
    for (bMtaHolder = (axGenericIntegerHolder *) sortedListIter->getFirst();
         bMtaHolder;
         bMtaHolder=(axGenericIntegerHolder *) sortedListIter->getNext()) {

      axInternalGenMta * bGMta = (axInternalGenMta *) 
                                               bMtaHolder->getObject();
      time_t bMtaTime;
      time_t diffTm;

      {
        axReadLockOperator bGmtaRLock(bGMta->getLock());
        axIntGenMtaNonkey_t * bMtaNonkey = 
                                   (axIntGenMtaNonkey_t *) bGMta->getNonKey();
        if (bGMta->hasData()) {
          bMtaTime = bMtaNonkey->availChangeTime;
        } else {
          bMtaTime = 0;
        }
      }

      if (!bMtaTime) {
        continue;
      }

      diffTm = bMtaTime - aMtaTime;

      if (diffTm <= m_alarmDetectWindow) {
        ++numOfflineInWindow;
        offendingDeviceList.add(bGMta);
      }

    } // for

    /*
     * Alarm condition
     */

    if (numOfflineInWindow >= m_alarmThreshold) {
      alarmDetected = true;
    }

    // finally
    delete aMtaHolder;

  } // for

  /*
   * clear justBecameUnavailable so that we don't bother alarming
   */
  if (alarmDetected) {

    axIteratorHolder sortedIH(sortedList.createIterator());
    axAbstractIterator * sortedListIter = sortedIH.getIterator();

    axGenericIntegerHolder * bMtaHolder;
    for (bMtaHolder=(axGenericIntegerHolder *) sortedListIter->getFirst();
         bMtaHolder;
         bMtaHolder=(axGenericIntegerHolder *) sortedListIter->getNext()) {

      axInternalGenMta * bGMta = (axInternalGenMta *)
                                               bMtaHolder->getObject();

      axWriteLockOperator bGmtaRLock(bGMta->getLock());

      axIntGenMtaNonkey_t * nk =
                            (axIntGenMtaNonkey_t *) bGMta->getNonKey();
      if (bGMta->hasData()) {
        nk->justBecameUnavailable = 0;
      }

    } // for

  }

  // free sortedList
  // sortedList.clearAndFreeEntries();

  return (alarmDetected);
}


//********************************************************************
// method:
// NOTE: that a HFC/Channel lock is held before entering this method
//********************************************************************
bool
axCorrelatedAlarmDetection::determineCmPecentAlarm(
                axSortedList & sortedList, axIntCmCounts_t & cmCounts, 
                                     axListBase & offendingDeviceList)
{
  axGenericIntegerHolder * aCmHolder;
  int                      numOfflineInWindow;
  bool                     alarmDetected = false;

  while ( !alarmDetected &&
       (aCmHolder = (axGenericIntegerHolder *) sortedList.remove()) ) {

    axInternalCm * aCm = (axInternalCm *) aCmHolder->getObject();
    time_t aCmTime;
    {
      axReadLockOperator aCmRLock(aCm->getLock());
      if (aCm->hasData()) {
        axIntCmNonkey_t * aCmNonkey = (axIntCmNonkey_t *) 
                                                      aCm->getNonKey();
        aCmTime = aCmNonkey->onlineChangeTime;
      } else {
        aCmTime = 0;
      }
    }

    if (!aCmTime || aCmTime < m_alarmDetectStartTime) {
      delete aCmHolder;
      continue;
    }

    axIteratorHolder sortedIH(sortedList.createIterator());
    axAbstractIterator * sortedListIter = sortedIH.getIterator();

    numOfflineInWindow = 1;

    offendingDeviceList.clear();
    offendingDeviceList.add(aCm);

    axGenericIntegerHolder * bCmHolder;
    for (bCmHolder=(axGenericIntegerHolder *) sortedListIter->getFirst();
         bCmHolder;
         bCmHolder=(axGenericIntegerHolder *) sortedListIter->getNext()) {

      axInternalCm * bCm = (axInternalCm *) bCmHolder->getObject();
      time_t bCmTime;
      time_t diffTm;

      {
        axReadLockOperator bCmRLock(bCm->getLock());
        axIntCmNonkey_t * bCmNonkey = 
                                  (axIntCmNonkey_t *) bCm->getNonKey();
        if (aCm->hasData()) {
          bCmTime = bCmNonkey->onlineChangeTime;
        } else {
          bCmTime = 0;
        }
      }

      if (!bCmTime) {
        continue;
      }

      diffTm = bCmTime - aCmTime;

      if (diffTm <= m_alarmDetectWindow) {
        ++numOfflineInWindow;
        offendingDeviceList.add(bCm);
      }

    } // for

    /*
     * Alarm condition
     */
    int onlineDropPercent = 
                          (numOfflineInWindow * 100) / cmCounts.total;

    if (onlineDropPercent >= m_alarmThreshold) {
      alarmDetected = true;
    }

    // finally
    delete aCmHolder;

  } // for

  // free sortedList
  // sortedList.clearAndFreeEntries();

  return (alarmDetected);
}


//********************************************************************
// method:
// NOTE: that a HFC/Channel lock is held before entering this method
// out: offendingDeviceList - list of devices causing the alarm
// return: true if there is an alarm condition; false otherwise
//********************************************************************
bool
axCorrelatedAlarmDetection::determineMtaPowerAlarm(
                                     axListBase & offendingDeviceList)
{
  int   numOfflineInWindow;
  bool  alarmDetected = false;

  if (!hasData()) {
    goto EXIT_LABEL;
  }

  {
    axIteratorHolder iH(getMTAs());
    axAbstractIterator * mtaIter = iH.getIterator();

    numOfflineInWindow = 0;

    axInternalGenMta * gMta;
    for (gMta=dynamic_cast<axInternalGenMta *>(mtaIter->getFirst());
         gMta;
         gMta=dynamic_cast<axInternalGenMta *>(mtaIter->getNext())) {

      axReadLockOperator mtaRlock(gMta->getLock());

      if (!gMta->hasData()) {
        continue;
      }

      axIntGenMtaNonkey_t * mtaNonkey = (axIntGenMtaNonkey_t *)
                                                    gMta->getNonKey();

      time_t futureTime = m_alarmDetectStartTime + m_alarmDetectWindow;

      if (mtaNonkey->onBatteryAlarm &&
          mtaNonkey->powerAlarmChangeTime > m_alarmDetectStartTime &&
          mtaNonkey->powerAlarmChangeTime <= futureTime) {
        ++numOfflineInWindow;
        offendingDeviceList.add(gMta);
      }

    } // for

    if (numOfflineInWindow >= m_alarmThreshold) {
      alarmDetected = true;
    }
  }

EXIT_LABEL:

  return (alarmDetected);
}


