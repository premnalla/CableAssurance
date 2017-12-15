
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbConnectionFactory.hpp"

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
axDbConnectionFactory::axDbConnectionFactory()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbConnectionFactory::~axDbConnectionFactory()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axDbConnectionFactory::axDbConnectionFactory()
// {
// }


//********************************************************************
// method:
//********************************************************************
axDbConnection *
axDbConnectionFactory::getReadOnlyConnection(void)
{
  axDbConnection * ret = (axDbConnection *)
     m_readOnlyConns.remove();

  // printf("Available ro connections: %d\n", m_readOnlyConns.size());

#if 0
  switch (ct) {
    case axDbConnectionFactory::READ:
      break;

    case axDbConnectionFactory::WRITE:
      break;

    default:
      break;

  }
#endif

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDbConnectionFactory::releaseReadOnlyConnection(axDbConnection * c)
{
  addReadOnlyConnection(c);
}


//********************************************************************
// method:
//********************************************************************
axDbConnection *
axDbConnectionFactory::getWriteConnection(void)
{
  axDbConnection * ret = (axDbConnection *)
     m_readWriteConns.remove();

  // printf("Available rw connections: %d\n", m_readWriteConns.size());

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDbConnectionFactory::releaseWriteConnection(axDbConnection * c)
{
  addWriteConnection(c);
}


//********************************************************************
// method:
//********************************************************************
void
axDbConnectionFactory::addReadOnlyConnection(axDbConnection * c)
{
  m_readOnlyConns.add(c);
  // printf("Available ro connections: %d\n", m_readOnlyConns.size());
}


//********************************************************************
// method:
//********************************************************************
void
axDbConnectionFactory::addWriteConnection(axDbConnection * c)
{
  m_readWriteConns.add(c);
  // printf("Available rw connections: %d\n", m_readWriteConns.size());
}


