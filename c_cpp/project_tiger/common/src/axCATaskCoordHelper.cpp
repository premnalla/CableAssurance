
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCATaskCoordHelper.hpp"
#include "axReaderWriterLock.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axCATaskCoordHelper * axCATaskCoordHelper::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCATaskCoordHelper::axCATaskCoordHelper() :
  axLockableObject(new axReaderWriterLock())
{
}


//********************************************************************
// destructor:
//********************************************************************
axCATaskCoordHelper::~axCATaskCoordHelper()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axCATaskCoordHelper::axCATaskCoordHelper()
// {
// }


//********************************************************************
// method:
//********************************************************************
axCATaskCoordHelper *
axCATaskCoordHelper::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axCATaskCoordHelper();
  }
  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
bool
axCATaskCoordHelper::canDoCmtsPoll(void)
{
  return (m_data.summaryCounter==0?true:false);
}

//********************************************************************
// method:
//********************************************************************
bool
axCATaskCoordHelper::canDoCmPoll(void)
{
  return (m_data.summaryCounter==0?true:false);
}

//********************************************************************
// method:
//********************************************************************
bool
axCATaskCoordHelper::canDoMtaPing(void)
{
  return (m_data.summaryCounter==0?true:false);
}

//********************************************************************
// method:
//********************************************************************
bool
axCATaskCoordHelper::canDoMtaPoll(void)
{
  return (m_data.summaryCounter==0?true:false);
}

//********************************************************************
// method:
//********************************************************************
bool
axCATaskCoordHelper::canDoCounts(void)
{
  return (m_data.summaryCounter==0?true:false);
}

//********************************************************************
// method:
//********************************************************************
bool
axCATaskCoordHelper::canDoDeviceClassification(void)
{
  return (m_data.summaryCounter==0?true:false);
}

//********************************************************************
// method:
//********************************************************************
bool
axCATaskCoordHelper::canDoSummary(void)
{
  return ((m_data.cmtsPollCounter==0 &&
           m_data.cmPollCounter==0 &&
           m_data.mtaPingCounter==0 &&
           m_data.mtaPollCounter==0 &&
           m_data.countsCounter==0 &&
           m_data.deviceClassifyCounter==0
          )?true:false);
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::setCmtsPoll(void)
{
  m_data.cmtsPollCounter++;
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::setCmPoll(void)
{
  m_data.cmPollCounter++;
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::setMtaPing(void)
{
  m_data.mtaPingCounter++;
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::setMtaPoll(void)
{
  m_data.mtaPollCounter++;
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::setCounts(void)
{
  m_data.countsCounter++;
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::setDeviceClassification(void)
{
  m_data.deviceClassifyCounter++;
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::setSummary(void)
{
  m_data.summaryCounter = 1;
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::clearCmtsPoll(void)
{
  m_data.cmtsPollCounter--;
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::clearCmPoll(void)
{
  m_data.cmPollCounter--;
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::clearMtaPing(void)
{
  m_data.mtaPingCounter--;
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::clearMtaPoll(void)
{
  m_data.mtaPollCounter--;
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::clearCounts(void)
{
  m_data.countsCounter--;
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::clearDeviceClassification(void)
{
  m_data.deviceClassifyCounter--;
}

//********************************************************************
// method:
//********************************************************************
void
axCATaskCoordHelper::clearSummary(void)
{
  m_data.summaryCounter = 0;
}

