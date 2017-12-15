
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stddef.h>
#include "axCmtsPoller.hpp"
#include "axGlobalData.hpp"
#include "axGlobalCmtsTable.hpp"
#include "axReadLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axIteratorHolder.hpp"
#include "axAvlIterator.hpp"
#include "axInternalCmts.hpp"
#include "axCmtsPollRunnableCollection.hpp"
#include "axCmtsPollRunnable.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axCmtsPoller * axCmtsPoller::m_instance = NULL;
bool           axCmtsPoller::m_initialized = false;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCmtsPoller::axCmtsPoller() :
  axListExtLock(new axMultipleReaderLock())
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmtsPoller::~axCmtsPoller()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCmtsPoller::axCmtsPoller()
// {
// }


//********************************************************************
// method:
//********************************************************************
axCmtsPoller *
axCmtsPoller::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axCmtsPoller();
    m_instance->initialize();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axCmtsPoller::initialize(void)
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
axCmtsPoller::loadRunnableCollection(void)
{
  bool ret = true;

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  {
    axReadLockOperator rLock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> 
                                                (syncCmtsTbl->table);
    axIteratorHolder iH(cmtsTbl->createIterator());
    axAvlIterator * iter = dynamic_cast<axAvlIterator *> 
                                                  (iH.getIterator());

    axInternalCmts * cmts = (axInternalCmts *) iter->getFirst();

    while (cmts) {

      axCmtsPollRunnableCollection * coll = 
        new axCmtsPollRunnableCollection(cmts->getResId());

      axCmtsPollRunnable * rbl = new axCmtsPollRunnable(coll, cmts);
      coll->add(rbl);

      // no need to lock since this happens once at startup
      add(coll);

      cmts = (axInternalCmts *) iter->getNext();

    } // while

  }

  return (ret);
}


