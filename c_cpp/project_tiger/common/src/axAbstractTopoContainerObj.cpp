
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAbstractTopoContainerObj.hpp"

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
axAbstractTopoContainerObj::axAbstractTopoContainerObj() :
  m_containerFd(0), m_containerId(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractTopoContainerObj::~axAbstractTopoContainerObj()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAbstractTopoContainerObj::axAbstractTopoContainerObj(
  AX_INT32 clientFd, AX_UINT16 containerId, AX_INT8 * containerName, 
                                            AX_INT8 * containerHost) :
  m_containerFd(clientFd), m_containerId(containerId), 
  m_containerName(containerName), m_containerHost(containerHost)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAbstractTopoContainerObj::axAbstractTopoContainerObj(
               AX_INT32 clientFd, axDbAbstractTopoContainer & dbObj) :
  m_containerFd(clientFd), m_containerId(dbObj.m_id), 
  m_containerName(dbObj.m_name), m_containerHost(dbObj.m_host)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAbstractTopoContainerObj::axAbstractTopoContainerObj(
                                              AX_UINT16 containerId) :
  m_containerFd(0), m_containerId(containerId)
{
}


//********************************************************************
// method:
//********************************************************************
const AX_INT8 *
axAbstractTopoContainerObj::getContainerName(void)
{
  return (m_containerName.c_str());
}


//********************************************************************
// method:
//********************************************************************
AX_UINT16
axAbstractTopoContainerObj::getContainerId(void)
{
  return (m_containerId);
}


//********************************************************************
// method:
//********************************************************************
const AX_INT8 *
axAbstractTopoContainerObj::getContainerHost(void)
{
  return (m_containerHost.c_str());
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axAbstractTopoContainerObj::getContainerFd(void)
{
  return (m_containerFd);
}

//********************************************************************
// method:
//********************************************************************
void
axAbstractTopoContainerObj::setContainerFd(AX_INT32 fd)
{
  m_containerFd = fd;
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractTopoContainerObj::clearContainerFd(void)
{
  m_containerFd = 0;
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractTopoContainerObj::isKeyEqual(axObject * o)
{
  bool ret;

  axAbstractTopoContainerObj * other = 
                       dynamic_cast<axAbstractTopoContainerObj *> (o);
  if (m_containerId == other->getContainerId()) {
    ret = true;
  } else {
    ret = false;
  }

  return (ret);
}



