
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axSqlResultSet.hpp"

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
axSqlResultSet::axSqlResultSet() :
  axAbstractSqlOperReturn((axAbstractConnection *) NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axSqlResultSet::~axSqlResultSet()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axSqlResultSet::axSqlResultSet(axAbstractConnection * c) :
  axAbstractSqlOperReturn(c)
{
}


//********************************************************************
// method:
//********************************************************************

