
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axMessageSubSystem.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axMessageSubSystem * axMessageSubSystem::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axMessageSubSystem::axMessageSubSystem() :
  m_nextId(1)
{
  pthread_mutex_init(&m_lock, NULL);
}


//********************************************************************
// destructor:
//********************************************************************
axMessageSubSystem::~axMessageSubSystem()
{
  pthread_mutex_destroy(&m_lock);
}


//********************************************************************
// data constructor:
//********************************************************************
// axMessageSubSystem::axMessageSubSystem()
// {
// }


//********************************************************************
// method:
//********************************************************************
axMessageSubSystem *
axMessageSubSystem::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axMessageSubSystem();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axMessageSubSystem::getNewMessageId(void)
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


