
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbMySqlModify.hpp"

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
axDbMySqlModify::axDbMySqlModify() :
  axSqlModifyOperation((axAbstractConnection *) NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbMySqlModify::~axDbMySqlModify()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbMySqlModify::axDbMySqlModify(axAbstractConnection * c) :
  axSqlModifyOperation(c)
{
}


