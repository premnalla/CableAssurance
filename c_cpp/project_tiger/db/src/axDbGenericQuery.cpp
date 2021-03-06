
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbGenericQuery.hpp"

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
axDbGenericQuery::axDbGenericQuery() :
  axSqlQueryOperation((axAbstractConnection *) NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbGenericQuery::~axDbGenericQuery()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbGenericQuery::axDbGenericQuery(axDbConnection * c) :
  axSqlQueryOperation(c)
{
}


