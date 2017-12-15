
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcMtaAlarmCollectionOfRCs.hpp"
#include "axGlobalData.hpp"
#include "axMultipleReaderLock.hpp"
#include "axReadLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axAvlIterator.hpp"
#include "axInternalCmts.hpp"
#include "axHfcMtaAlarmDetectionRC.hpp"
#include "axHfcMtaAlarmDetectionRunnable.hpp"


//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axHfcMtaAlarmCollectionOfRCs * 
                      axHfcMtaAlarmCollectionOfRCs::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axHfcMtaAlarmCollectionOfRCs::axHfcMtaAlarmCollectionOfRCs() :
  axListExtLock(new axMultipleReaderLock()),
  m_initialized(false)
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcMtaAlarmCollectionOfRCs::~axHfcMtaAlarmCollectionOfRCs()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axHfcMtaAlarmCollectionOfRCs::axHfcMtaAlarmCollectionOfRCs()
// {
// }


//********************************************************************
// method:
//********************************************************************
axHfcMtaAlarmCollectionOfRCs *
axHfcMtaAlarmCollectionOfRCs::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axHfcMtaAlarmCollectionOfRCs();
    m_instance->initialize();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axHfcMtaAlarmCollectionOfRCs::initialize(void)
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
axHfcMtaAlarmCollectionOfRCs::loadRunnableCollection(void)
{
  bool ret = true;

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  {
    axReadLockOperator rLock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> (syncCmtsTbl->table);
    axAvlIterator * iter = dynamic_cast<axAvlIterator *> 
                                           (cmtsTbl->createIterator());

    axInternalCmts * cmts = (axInternalCmts *) iter->getFirst();

    while (cmts) {

      axHfcMtaAlarmDetectionRC * coll =
          new axHfcMtaAlarmDetectionRC(cmts->getResId());

      axHfcMtaAlarmDetectionRunnable * rbl =
                        new axHfcMtaAlarmDetectionRunnable(coll, cmts);
      coll->add(rbl);

      // no need to lock since this happens once at startup
      add(coll);

      // printf("axHfcCountsAlarmStarter::loadRunnableCollection: "
      //        "cmts-res-id = %d\n", cmts->hashUInt32());

      // advance to next cmts
      cmts = (axInternalCmts *) iter->getNext();

    } // while

    // finally
    delete iter;
  }

  return (ret);
}


