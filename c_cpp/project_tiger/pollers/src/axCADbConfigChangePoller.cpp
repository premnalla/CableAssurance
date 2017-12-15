
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include "axCALog.hpp"
#include "axCADbConfigChangePoller.hpp"
#include "axListFree.hpp"
#include "axAvlTree.hpp"
#include "axIteratorHolder.hpp"
#include "axAvlIterator.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axCAController.hpp"
#include "axGlobalData.hpp"
#include "axGlobalCmtsTable.hpp"
#include "axInternalCmts.hpp"
#include "axDbCmts.hpp"
#include "axCALoadDbConfig.hpp"

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
axCADbConfigChangePoller::axCADbConfigChangePoller()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCADbConfigChangePoller::~axCADbConfigChangePoller()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCADbConfigChangePoller::axCADbConfigChangePoller()
// {
// }


//********************************************************************
// method:
//********************************************************************
AX_INT32
axCADbConfigChangePoller::run(void)
{
  static const char * myName="dbCfgChgPoller:";

  AX_INT32 ret = 0;

  axCAController * ctlr = axCAController::getInstance();

  while (!ctlr->isExit()) {
    sleep(5*60);
    ACE_DEBUG((LM_TIMING_DEBUG, "%s start\n",myName));
    checkForCmtsChanges();
    checkDbBasedConfigChanges();
    ACE_DEBUG((LM_TIMING_DEBUG, "%s end\n",myName));
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axCADbConfigChangePoller::checkForCmtsChanges(void)
{
  axListFree cmtsList;
  axListBase newCmtsList;

  axGlobalData * gD;
  axDbCmts tmpDbCmts;
  if (!tmpDbCmts.getRows(cmtsList, axDbCmts::QueryAll)) {
    goto EXIT_LABEL;
  }

  gD = axGlobalData::getInstance();

  preMarkAllCmts();

  {
    axIteratorHolder iH(cmtsList.createIterator());
    axAbstractIterator * iter = iH.getIterator();
    axDbCmts * dbCmts;
    axInternalCmts * intCmts;

    for (dbCmts=(axDbCmts *)iter->getFirst();
         dbCmts;
         dbCmts=(axDbCmts *)iter->getNext()) {
      axIntCmtsKey_t cmtsKey(dbCmts->m_cmtsResId);
      intCmts = gD->findCmts(cmtsKey);
      if (!intCmts) {
        newCmtsList.add(dbCmts);
        continue;
      }
      {
        axWriteLockOperator wL(intCmts->getLock());
        intCmts->compareAndUpdate(dbCmts);
        intCmts->setReconciled(true);
      }
    }
  }

  deleteUnreconciledCmts();

  addAllNewCmts(newCmtsList);

EXIT_LABEL:
  return;
}


//********************************************************************
// method:
//********************************************************************
void
axCADbConfigChangePoller::preMarkAllCmts(void)
{
  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();
  axReadLockOperator rLock(syncCmtsTbl->lock);
                                                (syncCmtsTbl->table);
  axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *>
                                                (syncCmtsTbl->table);
  axIteratorHolder iH(cmtsTbl->createIterator());
  axAvlIterator * iter = dynamic_cast<axAvlIterator *>
                                                  (iH.getIterator());
  axInternalCmts * intCmts = (axInternalCmts *) iter->getFirst();
  while (intCmts) {
    axWriteLockOperator wL(intCmts->getLock());
    intCmts->setReconciled(false);
    intCmts = (axInternalCmts *) iter->getNext();
  } // while
}


//********************************************************************
// method:
//********************************************************************
void
axCADbConfigChangePoller::deleteUnreconciledCmts(void)
{
  // axListBase deletedCmtsList;
  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();
  axReadLockOperator rLock(syncCmtsTbl->lock);
                                                (syncCmtsTbl->table);
  axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *>
                                                (syncCmtsTbl->table);
  axAbstractIterator * iter;
  axIteratorHolder iH(iter=cmtsTbl->createIterator());
  axInternalCmts * intCmts;

  for (intCmts=(axInternalCmts *) iter->getFirst();
       intCmts;
       intCmts=(axInternalCmts *) iter->getNext()) {

    axWriteLockOperator wL(intCmts->getLock());
    if (!intCmts->isReconciled() && !intCmts->isDeleted()) {
      // deletedCmtsList.add(intCmts);
      intCmts->setDeleted(true);
    }

  } // for all cmts's in tree

#if 0
  {
    axAbstractIterator * iter;
    axIteratorHolder iH(iter=deletedCmtsList.createIterator());
    for (intCmts=(axInternalCmts *)iter->getFirst();
         intCmts;
         intCmts=(axInternalCmts *)iter->getNext()) {
      axWriteLockOperator wL(intCmts->getLock());
      intCmts->setDeleted(true);
    }
  }
#endif

}


//********************************************************************
// method:
//********************************************************************
void
axCADbConfigChangePoller::addAllNewCmts(axListBase & l)
{
  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();
  axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *>
                                                (syncCmtsTbl->table);
  if (l.size()) {
    axWriteLockOperator rLock(syncCmtsTbl->lock);
    axDbCmts * dbCmts;
    axAbstractIterator * iter;
    axIteratorHolder iH(iter=l.createIterator());
    for (dbCmts=(axDbCmts *)iter->getFirst();
         dbCmts;
         dbCmts=(axDbCmts *)iter->getNext()) {
      axInternalCmts * intCmts = new axInternalCmts(*dbCmts);
      if (!intCmts->isAddable() || !cmtsTbl->add(intCmts)) {
        delete intCmts;
      }
    }
  }
}


//********************************************************************
// method:
//********************************************************************
void
axCADbConfigChangePoller::checkDbBasedConfigChanges(void)
{
  axCALoadDbConfig dbCfg;
  dbCfg.loadConfig();
}


