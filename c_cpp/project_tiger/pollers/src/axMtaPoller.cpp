
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stddef.h>
#include "axCALog.hpp"
#include "axCASystemConfig.hpp"
#include "axMtaPoller.hpp"
#include "axGlobalData.hpp"
#include "axGlobalCmtsTable.hpp"
#include "axMultipleReaderLock.hpp"
#include "axReadLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axIteratorHolder.hpp"
#include "axAvlIterator.hpp"
#include "axInternalCmts.hpp"
#include "axInternalGenMta.hpp"
#include "axHRMtaPollRunnableCollection.hpp"
#include "axHighRateMtaPollRunnable.hpp"
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
axMtaPoller * axMtaPoller::m_instance = NULL;
bool          axMtaPoller::m_initialized = false;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axMtaPoller::axMtaPoller() :
  axListExtLock(new axMultipleReaderLock())
{
}


//********************************************************************
// destructor:
//********************************************************************
axMtaPoller::~axMtaPoller()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axMtaPoller::axMtaPoller()
// {
// }


//********************************************************************
// method:
//********************************************************************
axMtaPoller *
axMtaPoller::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axMtaPoller();
    m_instance->initialize();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axMtaPoller::initialize(void)
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
axMtaPoller::loadRunnableCollection(void)
{
  bool ret = true;

  axCASystemConfig * sc = axCASystemConfig::getInstance();

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  int numMtasPerRunnable = sc->getMtaPerPollRunnable();

  {
    axReadLockOperator rLock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> 
                                                 (syncCmtsTbl->table);
    axIteratorHolder iH(cmtsTbl->createIterator());
    axAvlIterator * iter = dynamic_cast<axAvlIterator *> 
                                                   (iH.getIterator());
    axInternalCmts * cmts;
    axHRMtaPollRunnableCollection * coll = NULL;

    for (cmts=dynamic_cast<axInternalCmts *>(iter->getFirst());
         cmts;
         cmts=dynamic_cast<axInternalCmts *>(iter->getNext())) {

      int i = 0;

      coll = new axHRMtaPollRunnableCollection(cmts->getResId());

      // don't need to do any locking here...this happens at startup
      add(coll);

      synchronizedTable_t * mtaTableStruct = cmts->getMtaTable();
      axAvlTree * mtaTbl = dynamic_cast<axAvlTree *> 
                                              (mtaTableStruct->table);
      axIteratorHolder iMtaH(mtaTbl->createIterator());
      axAvlIterator * mtaIter = dynamic_cast<axAvlIterator *> 
                                                (iMtaH.getIterator());
      axInternalGenMta * gMta = dynamic_cast<axInternalGenMta *> 
                                                (mtaIter->getFirst());

      /*
       * Breakdown MTA's into batches
       */

      if (!mtaTbl->size()) {
        continue;
      }

      axHighRateMtaPollRunnable * r = 
                            new axHighRateMtaPollRunnable(coll, cmts);
      coll->add(r);

      while (gMta) {

        // printf("MTA: %s\n", gMta->getMtaMacAddress());

        if (i < numMtasPerRunnable) {
          r->addSubject(gMta);
        } else {
          i = 0;
          r = new axHighRateMtaPollRunnable(coll, cmts);
          r->addSubject(gMta);
          coll->add(r);
        }

        ++i;
        gMta = dynamic_cast<axInternalGenMta *> (mtaIter->getNext());
      } // for all MTA's in a CMTS

      {
        axAbstractIterator * iter;
        axIteratorHolder iH(iter=coll->createIterator());
        axHighRateMtaPollRunnable * r;
        for (r=(axHighRateMtaPollRunnable*)iter->getFirst();
             r;
             r=(axHighRateMtaPollRunnable*)iter->getNext()) {
          ACE_DEBUG((LM_POLL_DEBUG, "id=%d\n", r->hashUInt32()));
        }
      }

    } // for all CMTS's

  }

  return (ret);
}


