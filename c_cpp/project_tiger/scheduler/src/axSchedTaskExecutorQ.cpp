
//********************************************************************
// OBSOLETE
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axSchedTaskExecutorQ.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axSchedTaskExecutorQ * axSchedTaskExecutorQ::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axSchedTaskExecutorQ::axSchedTaskExecutorQ()
{
}


//********************************************************************
// destructor:
//********************************************************************
axSchedTaskExecutorQ::~axSchedTaskExecutorQ()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axSchedTaskExecutorQ::axSchedTaskExecutorQ()
// {
// }


//********************************************************************
// method:
//********************************************************************
axSchedTaskExecutorQ *
axSchedTaskExecutorQ::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axSchedTaskExecutorQ();
  }

  return (m_instance);
}


