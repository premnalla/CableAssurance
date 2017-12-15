
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbResult.hpp"

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
axDbResult::axDbResult() :
  axSqlResult((axAbstractConnection *)NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbResult::~axDbResult()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbResult::axDbResult(axDbConnection * c) :
  axSqlResult(c)
{
}

