
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcAlarmDetection.hpp"

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
axHfcAlarmDetection::axHfcAlarmDetection() :
  m_intHfc(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcAlarmDetection::~axHfcAlarmDetection()
{
}


//********************************************************************
// data constructor:
// NOTE: that a HFC lock is held before entering this method
//********************************************************************
axHfcAlarmDetection::axHfcAlarmDetection(axInternalHfc * c) :
  m_intHfc(c)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcAlarmDetection::hasData(void)
{
  return (m_intHfc->hasData());
}


//********************************************************************
// method:
//********************************************************************
axAbstractIterator *
axHfcAlarmDetection::getCableModems(void)
{
  axAbstractIterator * ret;

  ret = m_intHfc->getCableModems();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axAbstractIterator *
axHfcAlarmDetection::getMTAs(void)
{
  axAbstractIterator * ret;

  ret = m_intHfc->getMTAs();

  return (ret);
}


