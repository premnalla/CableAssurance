
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axIcmpSocketHolder.hpp"

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
axIcmpSocketHolder::axIcmpSocketHolder() :
  m_sock(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axIcmpSocketHolder::~axIcmpSocketHolder()
{
  if (m_sock) {
    // done by base class of socket
    // m_sock->closeSocket();
    delete m_sock;
  }
}


//********************************************************************
// data constructor:
//********************************************************************
axIcmpSocketHolder::axIcmpSocketHolder(axIcmpCASocket * s) :
  m_sock(s)
{
}


//********************************************************************
// method:
//********************************************************************
axIcmpCASocket *
axIcmpSocketHolder::getSocket(void)
{
  return (m_sock);
}

