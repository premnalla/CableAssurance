
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSnmpRequestType.hpp"

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
axAbstractSnmpRequestType::axAbstractSnmpRequestType() :
  m_maxRepetitions(0), m_nonRepeters(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractSnmpRequestType::~axAbstractSnmpRequestType()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axAbstractSnmpRequestType::axAbstractSnmpRequestType()
// {
// }


//********************************************************************
// method:
//********************************************************************
AX_INT32
axAbstractSnmpRequestType::getMaxRepetitions(void)
{
  return (m_maxRepetitions);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractSnmpRequestType::setMaxRepetitions(AX_INT32 in)
{
  m_maxRepetitions = in;
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axAbstractSnmpRequestType::getNonRepeters(void)
{
  return (m_nonRepeters);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractSnmpRequestType::setNonRepeters(AX_INT32 in)
{
  m_nonRepeters = in;
}

