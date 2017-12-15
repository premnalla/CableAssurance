
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stddef.h>
#include "axCASystemConfig.hpp"
#include "axMtaPinger.hpp"
#include "axGlobalData.hpp"
#include "axGlobalCmtsTable.hpp"
#include "axMultipleReaderLock.hpp"
#include "axReadLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axIteratorHolder.hpp"
#include "axAvlIterator.hpp"
#include "axInternalCmts.hpp"
#include "axInternalGenMta.hpp"
#include "axHRMtaPingRunnableCollection.hpp"
#include "axHighRateMtaPingRunnable.hpp"
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
axMtaPinger * axMtaPinger::m_instance = NULL;
bool          axMtaPinger::m_initialized = false;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axMtaPinger::axMtaPinger() :
  axListExtLock(new axMultipleReaderLock())
{
}


//********************************************************************
// destructor:
//********************************************************************
axMtaPinger::~axMtaPinger()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axMtaPinger::axMtaPinger()
// {
// }


//********************************************************************
// method:
//********************************************************************
axMtaPinger *
axMtaPinger::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axMtaPinger();
    m_instance->initialize();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axMtaPinger::initialize(void)
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
axMtaPinger::loadRunnableCollection(void)
{
  bool ret = true;

  axCASystemConfig * sc = axCASystemConfig::getInstance();

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

#if 0
  static const int maxRunnablesPerRC = sc->getMtaPingRunnablesPerRc();
#endif

  int numMtasPerRunnable = sc->getMtaPerPingRunnable();

  {
    axReadLockOperator rLock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> 
                                                (syncCmtsTbl->table);
    axIteratorHolder iH(cmtsTbl->createIterator());
    axAvlIterator * iter = dynamic_cast<axAvlIterator *> 
                                                  (iH.getIterator());

    axInternalCmts * cmts = (axInternalCmts *) iter->getFirst();

    while (cmts) {

      axHRMtaPingRunnableCollection * coll = 
                new axHRMtaPingRunnableCollection(cmts->getResId());

      // don't need to do any locking here...this happens at startup
      add(coll);

      synchronizedTable_t * mtaTableStruct = cmts->getMtaTable();
      axAvlTree * mtaTbl = dynamic_cast<axAvlTree *> 
                                            (mtaTableStruct->table);

      /* 
       * NOTE: don't need to lock the MTA table since this takes 
       * place at startup 
       */

      /*
       * Breakdown MTA's into batches
       */

#if 0
      int numInitialRunnables = axPollerUtils::GetNumRunnables(
                                 mtaTbl->size(), maxRunnablesPerRC);
#endif

      // if (!numInitialRunnables) {
      if (!mtaTbl->size()) {
        goto GET_NEXT_CMTS;
      }

      {
        axIteratorHolder iMtaH(mtaTbl->createIterator());
        axAvlIterator * mtaIter = dynamic_cast<axAvlIterator *> 
                                             (iMtaH.getIterator());
        axInternalGenMta * gMta = (axInternalGenMta *) 
                                               mtaIter->getFirst();

#if 0
        int numMtasPerRunnable =
           axPollerUtils::GetNumElementsPerRunnable(mtaTbl->size(),
                                              numInitialRunnables);
#endif

        int i = 0;
        axHighRateMtaPingRunnable * r = new 
                             axHighRateMtaPingRunnable(coll, cmts);
        coll->add(r);

        while (gMta) {

          if (i < numMtasPerRunnable) {
            r->addSubject(gMta);
          } else {
            i = 0;
            r = new axHighRateMtaPingRunnable(coll, cmts);
            r->addSubject(gMta);
            coll->add(r);
          }

          ++i;
          gMta = (axInternalGenMta *) mtaIter->getNext();
        }

      }

GET_NEXT_CMTS:

      // advance to next cmts
      cmts = (axInternalCmts *) iter->getNext();

    } // while

  }

  return (ret);
}


