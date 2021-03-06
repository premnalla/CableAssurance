
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAlarmSubSystem.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axAlarmSubSystem * axAlarmSubSystem::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axAlarmSubSystem::axAlarmSubSystem() :
  m_nextId(1)
{
  pthread_mutex_init(&m_lock, NULL);
}


//********************************************************************
// destructor:
//********************************************************************
axAlarmSubSystem::~axAlarmSubSystem()
{
  pthread_mutex_destroy(&m_lock);
}


//********************************************************************
// data constructor:
//********************************************************************
// axAlarmSubSystem::axAlarmSubSystem()
// {
// }


//********************************************************************
// method:
//********************************************************************
axAlarmSubSystem *
axAlarmSubSystem::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axAlarmSubSystem();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axAlarmSubSystem::getNextAlarmId(void)
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


