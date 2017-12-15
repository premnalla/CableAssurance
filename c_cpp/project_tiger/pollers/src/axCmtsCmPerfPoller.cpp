
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stddef.h>
#include "axCmtsCmPerfPoller.hpp"
#include "axGlobalData.hpp"
#include "axGlobalCmtsTable.hpp"
#include "axReadLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axAvlIterator.hpp"
#include "axInternalCmts.hpp"
#include "axCmtsCmPerfPollRunnableCollection.hpp"
#include "axCmtsCmPerfPollRunnable.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axCmtsCmPerfPoller * axCmtsCmPerfPoller::m_instance = NULL;
bool           axCmtsCmPerfPoller::m_initialized = false;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCmtsCmPerfPoller::axCmtsCmPerfPoller() :
  axListExtLock(new axMultipleReaderLock())
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmtsCmPerfPoller::~axCmtsCmPerfPoller()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCmtsCmPerfPoller::axCmtsCmPerfPoller()
// {
// }


//********************************************************************
// method:
//********************************************************************
axCmtsCmPerfPoller *
axCmtsCmPerfPoller::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axCmtsCmPerfPoller();
    m_instance->initialize();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axCmtsCmPerfPoller::initialize(void)
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
axCmtsCmPerfPoller::loadRunnableCollection(void)
{
  bool ret = true;

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  {
    axReadLockOperator rLock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> (syncCmtsTbl->table);
    axAvlIterator * iter = dynamic_cast<axAvlIterator *> (cmtsTbl->createIterator());

    axInternalCmts * cmts = (axInternalCmts *) iter->getFirst();

    while (cmts) {

      axCmtsCmPerfPollRunnableCollection * coll = 
        new axCmtsCmPerfPollRunnableCollection(cmts->getResId());

      axCmtsCmPerfPollRunnable * rbl = new axCmtsCmPerfPollRunnable(coll, cmts);
      coll->add(rbl);

      // no need to lock since takes place once at startup
      add(coll);

      // printf("loadCableModems: cmts-res-id = %d\n", cmts->hashUInt32());

      cmts = (axInternalCmts *) iter->getNext();
    } // while

    // finally
    delete iter;
  }

  return (ret);
}


