
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCASocket.hpp"

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
axAbstractCASocket::axAbstractCASocket() :
  axAbstractSocket(0,0,0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractCASocket::~axAbstractCASocket()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAbstractCASocket::axAbstractCASocket(
                  AX_INT32 domain, AX_INT32 type, AX_INT32 protocol) :
  axAbstractSocket(domain, type, protocol)
{
}


//********************************************************************
// method:
//********************************************************************
