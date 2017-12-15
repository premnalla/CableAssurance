
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stddef.h>
#include "axGlobalData.hpp"
#include "axMultipleReaderLock.hpp"
#include "axHfcAlarm.hpp"
#include "axMtaAlarm.hpp"
#include "axReadLockOperator.hpp"
#include "axAvlTree.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axGlobalData * axGlobalData::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axGlobalData::axGlobalData()
// axGlobalData::axGlobalData() :
  // m_currentHfcAlarmTable(new axMultipleReaderLock),
  // m_currentMtaAlarmTable(new axMultipleReaderLock)
{
}


//********************************************************************
// destructor:
//********************************************************************
axGlobalData::~axGlobalData()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axGlobalData::axGlobalData()
// {
// }


//********************************************************************
// method: getInstance
//********************************************************************
axGlobalData *
axGlobalData::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axGlobalData();
  }

  return (m_instance);
}


//********************************************************************
// method: getInstance
//********************************************************************
axGlobalCmtsTable *
axGlobalData::getCmtsTable(void)
{
  return (&m_cmtsTable);
}


//********************************************************************
// method:
//********************************************************************
axInternalCmts *
axGlobalData::findCmtsExtLock(axIntCmtsKey_t & key)
{
  axGlobalCmtsTable * gCmtsTable = getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  // axReadLockOperator rLock(syncCmtsTbl->lock);
  axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> (syncCmtsTbl->table);
  axInternalCmts tmpIntCmts(key);
  axInternalCmts * cmts = dynamic_cast<axInternalCmts *>
                                          (cmtsTbl->find(&tmpIntCmts));
  return (cmts);
}


//********************************************************************
// method:
//********************************************************************
axInternalCmts *
axGlobalData::findCmts(axIntCmtsKey_t & key)
{
  axGlobalCmtsTable * gCmtsTable = getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  axReadLockOperator rLock(syncCmtsTbl->lock);
  axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *> (syncCmtsTbl->table);
  axInternalCmts tmpIntCmts(key);
  axInternalCmts * cmts = dynamic_cast<axInternalCmts *>
                                          (cmtsTbl->find(&tmpIntCmts));
  return (cmts);
}


#if 0
//********************************************************************
// method: 
//********************************************************************
axCAAlarmCollection * 
axGlobalData::getCurrentHfcAlarmTable(void)
{
  return (&m_currentHfcAlarmTable);
}


//********************************************************************
// method:
//********************************************************************
axCAAlarmCollection *
axGlobalData::getCurrentMtaAlarmTable(void)
{
  return (&m_currentMtaAlarmTable);
}
#endif


