
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stddef.h>
#include "axCASystemConfig.hpp"
#include "axCmPerfPoller.hpp"
#include "axGlobalData.hpp"
#include "axGlobalCmtsTable.hpp"
#include "axReadLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axIteratorHolder.hpp"
#include "axAvlIterator.hpp"
#include "axInternalCmts.hpp"
#include "axInternalCm.hpp"
#include "axCmPerfPollRunnableCollection.hpp"
#include "axCmPerfPollRunnable.hpp"
#include "axPollerUtils.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axCmPerfPoller * axCmPerfPoller::m_instance = NULL;
bool             axCmPerfPoller::m_initialized = false;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCmPerfPoller::axCmPerfPoller() :
  axListExtLock(new axMultipleReaderLock())
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmPerfPoller::~axCmPerfPoller()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCmPerfPoller::axCmPerfPoller()
// {
// }


//********************************************************************
// method:
//********************************************************************
axCmPerfPoller *
axCmPerfPoller::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axCmPerfPoller();
    m_instance->initialize();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axCmPerfPoller::initialize(void)
{
  AX_INT32 ret = 0;

  if (!m_initialized) {

    // Load runnable collection and it's runnables
    loadRunnableCollection();


    // finally
    m_initialized = true;
  }


  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axCmPerfPoller::loadRunnableCollection(void)
{
  bool ret = true;

  axCASystemConfig * sc = axCASystemConfig::getInstance();

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  const int numRunnablesPerRC = sc->getCmPerfRunnablesPerRC();
  const int numCMsPerRunnable = sc->getCmPerPerfRunnable();

  {
    axReadLockOperator rLock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> 
                                                (syncCmtsTbl->table);
    axIteratorHolder iH(cmtsTbl->createIterator());
    axAvlIterator * iter = dynamic_cast<axAvlIterator *> 
                                                  (iH.getIterator());

    axInternalCmts * cmts = (axInternalCmts *) iter->getFirst();

    while (cmts) {

      axCmPerfPollRunnableCollection * coll = 
        new axCmPerfPollRunnableCollection(cmts->getResId());

      // no need to lock since takes place once at startup
      add(coll);

      synchronizedTable_t * cmTableStruct = cmts->getCmTable();
      axAvlTree * cmTbl = dynamic_cast<axAvlTree *> 
                                             (cmTableStruct->table);

      /*
       * Breakdown CM's into batches
       */

#if 0
      int numInitialRunnables = axPollerUtils::GetNumRunnables(
                                  cmTbl->size(), numRunnablesPerRC);
#endif

      // if (!numInitialRunnables) {
      if (!cmTbl->size()) {
        // continue;
        goto GET_NEXT_CMTS;
      }

      {
        axIteratorHolder iCmH(cmTbl->createIterator());
        axAvlIterator * cmIter = 
                  dynamic_cast<axAvlIterator *> (iCmH.getIterator());

        axInternalCm * intCm = (axInternalCm *) cmIter->getFirst();

#if 0
        int numCMsPerRunnable = 
               axPollerUtils::GetNumElementsPerRunnable(cmTbl->size(), 
                                        numInitialRunnables);
#endif

        int i = 0;
        axCmPerfPollRunnable * r = new axCmPerfPollRunnable(coll, cmts);
        coll->add(r);

        while (intCm) {

          if (i < numCMsPerRunnable) {
            r->addSubject(intCm);
          } else {
            i = 0;
            r = new axCmPerfPollRunnable(coll, cmts);
            r->addSubject(intCm);
            coll->add(r);
          }

          ++i;
          intCm = (axInternalCm *) cmIter->getNext();
        }

      }

GET_NEXT_CMTS:

      cmts = (axInternalCmts *) iter->getNext();

    } // while

  }

  return (ret);
}


