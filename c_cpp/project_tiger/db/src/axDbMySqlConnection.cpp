
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
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
axDbMySqlConnection::axDbMySqlConnection() :
  m_connected(false)
{
  memset(&m_dbHandle, '\0', sizeof(m_dbHandle));
  mysql_init(&m_dbHandle);
}


//********************************************************************
// destructor:
//********************************************************************
axDbMySqlConnection::~axDbMySqlConnection()
{
  closeConnection();
}


//********************************************************************
// data constructor:
//********************************************************************
// axDbMySqlConnection::axDbMySqlConnection()
// {
// }


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlConnection::initialize(void)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlConnection::openConnection(void)
{
  bool ret = true;

  char * host = NULL;
  char * user = "root";
  char * passwd = "manager";
  char * db = "canet";
  unsigned int port = 0;
  char * socket_name = NULL;
  long unsigned int client_flag = 0;

  if (m_connected) {
    goto EXIT_LABEL;
  }

  // Establish connection
  if (mysql_real_connect(&m_dbHandle, host, user, passwd, db, port,
                         socket_name, client_flag)) {
    m_connected = true;
    // apply_pending_options();

#if 0
    if (db && db[0]) {
      // Also attach to given database
      mysql_select_db(&m_dbHandle, db);
    }
#endif

  } else {
    ret = false;
  }

EXIT_LABEL:
  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlConnection::closeConnection(void)
{
  bool ret = true;

  if (m_connected) {
    mysql_close(&m_dbHandle);
    m_connected = false;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlConnection::testConnection(void)
{
  bool ret = true;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlConnection::isConnected(void)
{
  return (m_connected);
}


//********************************************************************
// method:
//********************************************************************
void *
axDbMySqlConnection::getConnectionHandle(void)
{
  return ((void *) &m_dbHandle);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlConnection::setAutoCommitOn(void)
{
  bool ret = false;



  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlConnection::setAutoCommitOff(void)
{
  bool ret = false;



  return (ret);
}


