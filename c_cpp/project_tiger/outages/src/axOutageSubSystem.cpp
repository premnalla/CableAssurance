
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axOutageSubSystem.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axOutageSubSystem * axOutageSubSystem::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axOutageSubSystem::axOutageSubSystem() :
  m_nextId(1)
{
  pthread_mutex_init(&m_lock, NULL);
}


//********************************************************************
// destructor:
//********************************************************************
axOutageSubSystem::~axOutageSubSystem()
{
  pthread_mutex_destroy(&m_lock);
}


//********************************************************************
// data constructor:
//********************************************************************
// axOutageSubSystem::axOutageSubSystem()
// {
// }


//********************************************************************
// method:
//********************************************************************
axOutageSubSystem *
axOutageSubSystem::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axOutageSubSystem();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axOutageSubSystem::getNextOutageId(void)
{
  AX_UINT32 ret;

  pthread_mutex_lock(&m_lock);

  ret = m_nextId++;

  if (!m_nextId) {
    m_nextId = 1;
  }

  pthread_mutex_unlock(&m_lock);

  return (ret);
}

