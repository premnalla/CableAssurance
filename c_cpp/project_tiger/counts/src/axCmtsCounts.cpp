
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCASystemConfig.hpp"
#include "axCmtsCounts.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axIteratorHolder.hpp"
#include "axInternalCm.hpp"
#include "axInternalGenMta.hpp"
// #include "axAbstractIterator.hpp"
#include "axDbCmtsCurrentCounts.hpp"
#include "axDbCmtsCountsLog.hpp"
#include "axDbMtaAvailLog.hpp"
#include "axDbEmta.hpp"
#include "axDbMtaCurrentAvailability.hpp"
#include "axMtaStatusUtils.hpp"

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
axCmtsCounts::axCmtsCounts() :
  m_intCmts(NULL), m_totalCm(0), m_onlineCm(0), m_totalMta(0), 
  m_availableMta(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmtsCounts::~axCmtsCounts()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmtsCounts::axCmtsCounts(axInternalCmts * c) :
  m_intCmts(c), m_totalCm(0), m_onlineCm(0), m_totalMta(0), 
  m_availableMta(0)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axCmtsCounts::run(void)
{
  static const char * myName="cmtsCountsRun:";

  bool ret = true;

  struct timeval tm;
  gettimeofday(&tm, NULL);

  {
    axWriteLockOperator cmtsWlock(m_intCmts->getLock());

    if (!m_intCmts->canDoCounts()) {
      goto EXIT_LABEL;
    }

    axIntCmtsNonkey_t * cmtsNonkey = (axIntCmtsNonkey_t *) 
                                                m_intCmts->getNonKey();

    cmtsNonkey->previousCounts = cmtsNonkey->currentCounts;
  }

  cableModemCounts();
  mtaCounts();

  {
    bool updateRequired = false;

    axReadLockOperator cmtsWlock(m_intCmts->getLock());

    axIntCmtsNonkey_t * cmtsNonkey = (axIntCmtsNonkey_t *)
                                                m_intCmts->getNonKey();

    /*
     * Update DB if need be
     */

    if (cmtsNonkey->previousCounts != cmtsNonkey->currentCounts) {
      updateRequired = true;
      cmtsNonkey->currentCounts.lastLogTime = tm.tv_sec;
    } else {
      updateRequired = needToUpdate(&tm, &cmtsNonkey->currentCounts);
    }

    if (updateRequired) {
      axDbCmtsCurrentCounts currentCounts(m_intCmts->getResId(), 
         tm.tv_sec, &cmtsNonkey->currentCounts);
      axDbCmtsCountsLog countsLog(m_intCmts->getResId(), 
         tm.tv_sec, &cmtsNonkey->currentCounts);
      currentCounts.insertOrUpdateRow();
      countsLog.insertRow();
    }

  }


EXIT_LABEL:

  ACE_DEBUG((LM_ALARM_DEBUG, "%s cmTotal=%d; mtaTotal=%d; cmts=%d\n",
                myName, m_totalCm, m_totalMta, m_intCmts->getResId()));

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axCmtsCounts::cableModemCounts(void)
{
  synchronizedTable_t * syncCmTable = m_intCmts->getCmTable();

  axReadLockOperator cmTblRLock(syncCmTable->lock);

  axAvlTree * cmTbl = dynamic_cast<axAvlTree *> (syncCmTable->table);

  axIteratorHolder iH(cmTbl->createIterator());
  axAbstractIterator * iter = iH.getIterator();
  axInternalCm * intCm;

  for (intCm = dynamic_cast<axInternalCm *> (iter->getFirst());
       intCm;
       intCm = dynamic_cast<axInternalCm *> (iter->getNext())) {

    axReadLockOperator cmRlock(intCm->getLock());

    if (!intCm->hasData()) {
      continue;
    }

    axIntCmNonkey_t * cmNonkey = (axIntCmNonkey_t *) intCm->getNonKey();

    ++m_totalCm;

    if (axInternalCm::isCmOnline(cmNonkey->docsisState)) {
      ++m_onlineCm;
    }

  } // for

  {
    axWriteLockOperator cmtsWlock(m_intCmts->getLock());

    axIntCmtsNonkey_t * cmtsNonkey = (axIntCmtsNonkey_t *)
                                                m_intCmts->getNonKey();

    cmtsNonkey->currentCounts.cm.total = m_totalCm;
    cmtsNonkey->currentCounts.cm.online = m_onlineCm;
  }

// EXIT_LABEL:

  return;
}


//********************************************************************
// method:
//********************************************************************
void
axCmtsCounts::mtaCounts(void)
{
  bool isOnline;
  bool isProvPass;
  bool isPingPass;
  bool isPingFailure;

  synchronizedTable_t * syncMtaTable = m_intCmts->getMtaTable();

  axReadLockOperator mtaTblRLock(syncMtaTable->lock);

  axAvlTree * mtaTbl = dynamic_cast<axAvlTree *> (syncMtaTable->table);

  axIteratorHolder iH(mtaTbl->createIterator());
  axAbstractIterator * iter = iH.getIterator();
  axInternalGenMta * gMta;

  struct timeval tm;
  gettimeofday(&tm, NULL);

  for (gMta = dynamic_cast<axInternalGenMta *> (iter->getFirst());
       gMta;
       gMta = dynamic_cast<axInternalGenMta *> (iter->getNext())) {

    axReadLockOperator gMtaRlock(gMta->getLock());

    if (!gMta->hasData()) {
      continue;
    }

    axIntGenMtaNonkey_t * gMtaNonkey = (axIntGenMtaNonkey_t *) 
                                                    gMta->getNonKey();

    ++m_totalMta;

    if (gMta->isEmta()) {
      axIntEmtaNonkey_t * eMtaNonkey = (axIntEmtaNonkey_t *) 
                                                    gMta->getNonKey();
      axInternalCm * intCm = eMtaNonkey->cmPtr;
      {
        axReadLockOperator cmRlock(intCm->getLock());
        if (intCm->hasData()) {
          axIntCmNonkey_t * cmNonkey = (axIntCmNonkey_t *)
                                                   intCm->getNonKey();
          isOnline = axInternalCm::isCmOnline(cmNonkey->docsisState);
        } else {
          isOnline = false;
        }
      }
    } else {
      isOnline = true;
    }

    isProvPass = axInternalGenMta::isProvStatePass(
                                             gMtaNonkey->provState);
    isPingPass = axInternalGenMta::isPingStatePass(
                                             gMtaNonkey->pingState);
    isPingFailure = axInternalGenMta::isPingStateFailure(
                                             gMtaNonkey->pingState);

    if (gMtaNonkey->available && 
                      (!isOnline || !isProvPass || isPingFailure)) {

      axMtaStatusUtils::NewStatusUpdateWork(true, tm.tv_sec, 
                                                        gMtaNonkey);

    } else if (!gMtaNonkey->available &&
                           (isOnline && isProvPass && isPingPass)) {

      axMtaStatusUtils::NewStatusUpdateWork(false, tm.tv_sec, 
                                                        gMtaNonkey);
    } else {
      axMtaStatusUtils::LogStatusWork(tm.tv_sec, gMtaNonkey);
    }

    if (gMtaNonkey->available) {
      ++m_availableMta;
    }

  } // for all MTA's

  {
    axWriteLockOperator cmtsWlock(m_intCmts->getLock());

    axIntCmtsNonkey_t * cmtsNonkey = (axIntCmtsNonkey_t *)
                                                m_intCmts->getNonKey();

    cmtsNonkey->currentCounts.mta.total = m_totalMta;
    cmtsNonkey->currentCounts.mta.available = m_availableMta;
  }

// EXIT_LABEL:

  return;
}


