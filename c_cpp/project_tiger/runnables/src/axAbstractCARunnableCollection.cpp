
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCARunnableCollection.hpp"

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
axAbstractCARunnableCollection::axAbstractCARunnableCollection()
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractCARunnableCollection::~axAbstractCARunnableCollection()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axAbstractCARunnableCollection::axAbstractCARunnableCollection()
// {
// }


//********************************************************************
// method:
//********************************************************************
time_t
axAbstractCARunnableCollection::getKickoffTime(void)
{
  return (m_myData.kickoffTime);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCARunnableCollection::setKickoffTime(time_t in)
{
  m_myData.kickoffTime = in;
}


//********************************************************************
// method:
//********************************************************************
time_t
axAbstractCARunnableCollection::getWorkStartTime(void)
{
  return (m_myData.workStartTime);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCARunnableCollection::setWorkStartTime(time_t in)
{
  m_myData.workStartTime = in;
}


//********************************************************************
// method:
//********************************************************************
time_t
axAbstractCARunnableCollection::getWorkEndTime(void)
{
  return (m_myData.workEndTime);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCARunnableCollection::setWorkEndTime(time_t in)
{
  m_myData.workEndTime = in;
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractCARunnableCollection::isInProgress(void)
{
  return (m_myData.inProgress);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCARunnableCollection::setInProgress(void)
{
  m_myData.inProgress = true;
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCARunnableCollection::clearInProgress(void)
{
  m_myData.inProgress = false;
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axAbstractCARunnableCollection::getBatchNumber(void)
{
  return (m_myData.batchNumber);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCARunnableCollection::setBatchNumber(AX_INT32 in)
{
  m_myData.batchNumber = in;
}


//********************************************************************
// method:
//********************************************************************
axAbstractLock *
axAbstractCARunnableCollection::getLock(void)
{
  return (m_myData.lock);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractCARunnableCollection::isNextCycleScheduled(void)
{
  return (m_myData.nextCycleScheduled);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCARunnableCollection::setNextCycleScheduled(void)
{
  m_myData.nextCycleScheduled = true;
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCARunnableCollection::clearNextCycleScheduled(void)
{
  m_myData.nextCycleScheduled = false;
}


