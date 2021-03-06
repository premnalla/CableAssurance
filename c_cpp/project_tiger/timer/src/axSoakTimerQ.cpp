
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
// #include <stddef.h>
#include "axSoakTimerQ.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axSoakTimerQ * axSoakTimerQ::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axSoakTimerQ::axSoakTimerQ()
{
}


//********************************************************************
// destructor:
//********************************************************************
axSoakTimerQ::~axSoakTimerQ()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axSoakTimerQ::axSoakTimerQ()
// {
// }


//********************************************************************
// method:
//********************************************************************
axSoakTimerQ *
axSoakTimerQ::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axSoakTimerQ();
  }

  return (m_instance);
}


