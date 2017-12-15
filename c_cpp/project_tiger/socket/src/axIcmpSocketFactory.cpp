
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <unistd.h>
#include "axIcmpSocketFactory.hpp"
#include "axIcmpV4CASocket.hpp"
#include "axIcmpV6CASocket.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axIcmpSocketFactory * axIcmpSocketFactory::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axIcmpSocketFactory::axIcmpSocketFactory()
{
}


//********************************************************************
// destructor:
//********************************************************************
axIcmpSocketFactory::~axIcmpSocketFactory()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axIcmpSocketFactory::axIcmpSocketFactory()
// {
// }


//********************************************************************
// method:
//********************************************************************
axIcmpSocketFactory *
axIcmpSocketFactory::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axIcmpSocketFactory();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
axIcmpCASocket *
axIcmpSocketFactory::createSocket(axIcmpTemplateSocket * tmplSock)
{
  axIcmpCASocket * sock = NULL;

  if (tmplSock->getType() == SOCK_RAW && 
      tmplSock->getProtocol() == IPPROTO_ICMP) {

    switch (tmplSock->getDomain()) {
      case AF_INET:
        sock = new axIcmpV4CASocket();
        break;

      case AF_INET6:
        sock = new axIcmpV6CASocket();
        break;

      default:
        break;
    }
  }

  return (sock);
}


