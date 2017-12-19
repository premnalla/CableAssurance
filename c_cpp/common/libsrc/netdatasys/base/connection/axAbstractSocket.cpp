
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include "axAbstractSocket.hpp"

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
axAbstractSocket::axAbstractSocket() :
  m_socketDomain(0), m_socketType(0), m_socketProtocol(0), m_socketFd(0),
  m_opened(false)
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractSocket::~axAbstractSocket()
{
  closeSocket();
}


//********************************************************************
// data constructor:
//********************************************************************
axAbstractSocket::axAbstractSocket(AX_INT32 domain, AX_INT32 type, AX_INT32 protocol) :
  m_socketDomain(domain), m_socketType(type), m_socketProtocol(protocol), m_socketFd(0),
  m_opened(false)
{
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractSocket::initialize(void)
{
}

//********************************************************************
// method:
//********************************************************************
bool
axAbstractSocket::openSocket(void)
{
  bool ret = false;

  if (m_opened) {
    closeSocket();
  }

  m_socketFd = socket(m_socketDomain, m_socketType, m_socketProtocol);

  if (m_socketFd < 0) {
  } else {
    m_opened = true;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractSocket::closeSocket(void)
{
  bool ret = true;

  if (m_opened) {
    close(m_socketFd);
    m_opened = false;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractSocket::isOpen(void)
{
  return (m_opened);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractSocket::testSocket(void)
{
  return (false);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axAbstractSocket::getHandle(void)
{
  return (m_socketFd);
}


#if 0
//********************************************************************
// method:
//********************************************************************
void
axAbstractSocket::setHandle(AX_INT32 sockFd)
{
  m_socketFd = sockFd;
}
#endif

