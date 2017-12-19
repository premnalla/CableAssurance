
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axSqlModifyOperation.hpp"

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
axSqlModifyOperation::axSqlModifyOperation() :
  axAbstractSqlOperation((axAbstractConnection *) NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axSqlModifyOperation::~axSqlModifyOperation()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axSqlModifyOperation::axSqlModifyOperation(axAbstractConnection * c) :
  axAbstractSqlOperation(c)
{
}


