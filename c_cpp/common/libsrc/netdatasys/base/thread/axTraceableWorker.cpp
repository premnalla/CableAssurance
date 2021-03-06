//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axTraceableWorker.hpp"

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
axTraceableWorker::axTraceableWorker() :
  m_traceInstance(0)
{
}

//********************************************************************
// destructor:
//********************************************************************
axTraceableWorker::~axTraceableWorker()
{
}

//********************************************************************
// data constructor:
//********************************************************************
axTraceableWorker::axTraceableWorker(axControllerProxy * p, 
    int traceInstance) :
  axWorker(p),
  m_traceInstance(traceInstance)
{
}



