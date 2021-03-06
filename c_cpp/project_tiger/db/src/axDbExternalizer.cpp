
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbExternalizer.hpp"
#include "axDbMySqlConnFactory.hpp"
#include "axDbMySqlQuery.hpp"
#include "axDbMySqlConnection.hpp"

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
axDbExternalizer::axDbExternalizer()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbExternalizer::~axDbExternalizer()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axDbExternalizer::axDbExternalizer()
// {
// }


//********************************************************************
// method:
//********************************************************************
axDbConnectionFactory *
axDbExternalizer::getConnectionFactory(void)
{
  return (axDbMySqlConnFactory::getInstance());
}


//********************************************************************
// method:
//********************************************************************
axDbGenericQuery *
axDbExternalizer::getQuery(axDbConnection * c)
{
  axDbMySqlConnection * mc = dynamic_cast<axDbMySqlConnection *> (c);
  return (new axDbMySqlQuery(mc));
}


