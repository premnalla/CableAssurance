
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbMySqlConnFactory.hpp"
#include "axDbMySqlConnection.hpp"
#include "axCASystemConfig.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axDbMySqlConnFactory * axDbMySqlConnFactory::m_instance = NULL;
bool                   axDbMySqlConnFactory::m_initialized = false;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbMySqlConnFactory::axDbMySqlConnFactory()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbMySqlConnFactory::~axDbMySqlConnFactory()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axDbMySqlConnFactory::axDbMySqlConnFactory()
// {
// }


//********************************************************************
// method:
//********************************************************************
axDbMySqlConnFactory *
axDbMySqlConnFactory::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axDbMySqlConnFactory();
    m_instance->initialize();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlConnFactory::initialize(void)
{
  if (!m_initialized) {

  axCASystemConfig * sc = axCASystemConfig::getInstance();

    int readConns = sc->get_max_db_read_conns();
    int writeConns = sc->get_max_db_write_conns();

    for (int i=0; i<readConns; i++) {
      axDbMySqlConnection * c = new axDbMySqlConnection();
      if (c->openConnection()) {
        addReadOnlyConnection(c);
      } else {
        delete c;
      }
    }

    for (int i=0; i<writeConns; i++) {
      axDbMySqlConnection * c = new axDbMySqlConnection();
      if (c->openConnection()) {
        addWriteConnection(c);
      } else {
        delete c;
      }
    }

    m_initialized = true;
  }
}


