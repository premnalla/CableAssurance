
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axIcmpSocketHelper.hpp"

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
axIcmpSocketHelper::axIcmpSocketHelper() :
  m_socket(NULL), m_socketFactory(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axIcmpSocketHelper::~axIcmpSocketHelper()
{
  if (m_socket) {
    m_socket->closeSocket();
    m_socketFactory->releaseV4Socket(m_socket);
  }
}


//********************************************************************
// data constructor:
//********************************************************************
axIcmpSocketHelper::axIcmpSocketHelper(axIcmpSocketFactory * factory,
    AX_UINT8 ipAddressType) :
  m_socket(NULL), m_socketFactory(factory)
{
  m_socket = m_socketFactory->getV4Socket();
}


#if 0
//********************************************************************
// method:
//********************************************************************
void
axIcmpSocketHelper::setSession(axSnmpSession * c)
{
  m_socket  = c;
}


//********************************************************************
// method:
//********************************************************************
void
axIcmpSocketHelper::setSessionFactory(axSnmpSessionFactory * cf)
{
  m_socketFactory  = cf;
}


//********************************************************************
// method:
//********************************************************************
axSnmpSessionFactory *
axIcmpSocketHelper::getSessionFactory(void)
{
  return (m_socketFactory);
}
#endif


//********************************************************************
// method:
//********************************************************************
axIcmpCASocket *
axIcmpSocketHelper::getSocket(void)
{
  return (m_socket);
}



