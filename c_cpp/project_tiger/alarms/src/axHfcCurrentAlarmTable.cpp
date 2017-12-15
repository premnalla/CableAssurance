
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <sys/time.h>
#include "axHfcCurrentAlarmTable.hpp"
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
axHfcCurrentAlarmTable * axHfcCurrentAlarmTable::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axHfcCurrentAlarmTable::axHfcCurrentAlarmTable()
  // axCAAlarmCollection(new axMultipleReaderLock())
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcCurrentAlarmTable::~axHfcCurrentAlarmTable()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axHfcCurrentAlarmTable::axHfcCurrentAlarmTable()
// {
// }


//********************************************************************
// method:
//********************************************************************
axHfcCurrentAlarmTable *
axHfcCurrentAlarmTable::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axHfcCurrentAlarmTable();
  }

  return (m_instance);
}


