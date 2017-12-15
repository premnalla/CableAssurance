
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAbstractTimerData.hpp"

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
axAbstractTimerData::axAbstractTimerData() :
  m_timerHeader()
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractTimerData::~axAbstractTimerData()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAbstractTimerData::axAbstractTimerData(time_t popTime) :
  m_timerHeader(popTime)
{
}


//********************************************************************
// method:
//********************************************************************
time_t
axAbstractTimerData::getPopTime(void)
{
  return (m_timerHeader.timerPop);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axAbstractTimerData::getCorrelator(void)
{
  return (m_timerHeader.timerId);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractTimerData::setCorrelator(AX_UINT32 in)
{
  m_timerHeader.timerId = in;
}


