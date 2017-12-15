
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axSnmpSession.hpp"

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
axSnmpSession::axSnmpSession() :
  m_openSession(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axSnmpSession::~axSnmpSession()
{
  if (m_openSession) {
    snmp_sess_close(m_openSession);
  }
}


//********************************************************************
// data constructor:
//********************************************************************
axSnmpSession::axSnmpSession(netsnmp_session_list * s) :
  m_openSession(s)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axSnmpSession::open(void)
{
  bool ret;

  ret = (m_openSession ? true : false);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpSession::close(void)
{
  snmp_sess_close(m_openSession);
  m_openSession = NULL;
}


//********************************************************************
// method:
//********************************************************************
bool
axSnmpSession::isOpen(void)
{
  bool ret;

  ret = (m_openSession ? true : false);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
netsnmp_session *
axSnmpSession::getInternalSession(void)
{
  return (m_openSession->session);
}


//********************************************************************
// method:
//********************************************************************
netsnmp_session_list *
axSnmpSession::getInternalSessionList(void)
{
  return (m_openSession);
}


