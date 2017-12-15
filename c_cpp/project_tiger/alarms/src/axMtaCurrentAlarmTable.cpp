
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <sys/time.h>
#include "axMtaCurrentAlarmTable.hpp"
#include "axMultipleReaderLock.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axMtaCurrentAlarmTable * axMtaCurrentAlarmTable::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axMtaCurrentAlarmTable::axMtaCurrentAlarmTable()
  // axCAAlarmCollection(new axMultipleReaderLock())
{
}


//********************************************************************
// destructor:
//********************************************************************
axMtaCurrentAlarmTable::~axMtaCurrentAlarmTable()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axMtaCurrentAlarmTable::axMtaCurrentAlarmTable()
// {
// }


//********************************************************************
// method:
//********************************************************************
axMtaCurrentAlarmTable *
axMtaCurrentAlarmTable::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axMtaCurrentAlarmTable();
  }

  return (m_instance);
}


