
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stddef.h>
#include "axPoppedTimerQ.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axPoppedTimerQ * axPoppedTimerQ::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axPoppedTimerQ::axPoppedTimerQ()
{
}


//********************************************************************
// destructor:
//********************************************************************
axPoppedTimerQ::~axPoppedTimerQ()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axPoppedTimerQ::axPoppedTimerQ()
// {
// }


//********************************************************************
// method:
//********************************************************************
axPoppedTimerQ *
axPoppedTimerQ::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axPoppedTimerQ();
  }

  return (m_instance);
}


