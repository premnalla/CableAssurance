
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stddef.h>
#include <stdio.h>
#include "axReadLockOperator.hpp"

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
axReadLockOperator::axReadLockOperator() :
  m_lock(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axReadLockOperator::~axReadLockOperator()
{
#if 1
  if (m_lock) {
    m_lock->readPostAccess();
    // printf("ReadPostAccess...\n");
  }
#endif
}


//********************************************************************
// data constructor:
//********************************************************************
axReadLockOperator::axReadLockOperator(axAbstractLock * lock) :
  m_lock(lock)
{
#if 1
  m_lock->readPreAccess();
  // printf("ReadPreAccess...\n");
#endif
}


