//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <string.h> // memset()
#include "axAbstractMultiStates.hpp"

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
axAbstractMultiStates::axAbstractMultiStates()
{
  memset(&m_states, 0, sizeof(m_states));
  pthread_mutex_init(&m_states.state.stateLock, NULL);
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractMultiStates::~axAbstractMultiStates()
{
  pthread_mutex_destroy(&m_states.state.stateLock);
}

//********************************************************************
// data constructor:
//********************************************************************
// axAbstractMultiStates::axAbstractMultiStates(const char * appName)
// {
// }


//********************************************************************
// method :
//********************************************************************
bool 
axAbstractMultiStates::getState(void)
{
  pthread_mutex_lock(&m_states.state.stateLock);
  bool ret = m_states.state.state;
  pthread_mutex_unlock(&m_states.state.stateLock);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
void
axAbstractMultiStates::setState(bool b)
{
  pthread_mutex_lock(&m_states.state.stateLock);
  m_states.state.state = b;
  pthread_mutex_unlock(&m_states.state.stateLock);
}


//********************************************************************
// method :
//********************************************************************
void
axAbstractMultiStates::setInitializing(bool b)
{
  pthread_mutex_lock(&m_states.state.stateLock);
  m_states.initializing = b;
  pthread_mutex_unlock(&m_states.state.stateLock);
}


//********************************************************************
// method :
//********************************************************************
bool
axAbstractMultiStates::getInitializing(void)
{
  pthread_mutex_lock(&m_states.state.stateLock);
  bool ret = m_states.initializing;
  pthread_mutex_unlock(&m_states.state.stateLock);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
void
axAbstractMultiStates::setReinitRequested(bool b)
{
  pthread_mutex_lock(&m_states.state.stateLock);
  m_states.reInitRequested = b;
  pthread_mutex_unlock(&m_states.state.stateLock);
}


//********************************************************************
// method :
//********************************************************************
bool
axAbstractMultiStates::getReinitRequested(void)
{
  pthread_mutex_lock(&m_states.state.stateLock);
  bool ret = m_states.reInitRequested;
  pthread_mutex_unlock(&m_states.state.stateLock);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
void
axAbstractMultiStates::setReinitializing(bool b)
{
  pthread_mutex_lock(&m_states.state.stateLock);
  m_states.reInitializing = b;
  pthread_mutex_unlock(&m_states.state.stateLock);
}


//********************************************************************
// method :
//********************************************************************
bool
axAbstractMultiStates::getReinitializing(void)
{
  pthread_mutex_lock(&m_states.state.stateLock);
  bool ret = m_states.reInitializing;
  pthread_mutex_unlock(&m_states.state.stateLock);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
void
axAbstractMultiStates::setInitState(bool b)
{
  pthread_mutex_lock(&m_states.state.stateLock);
  m_states.initState = b;
  pthread_mutex_unlock(&m_states.state.stateLock);
}


//********************************************************************
// method :
//********************************************************************
bool
axAbstractMultiStates::getInitState(void)
{
  pthread_mutex_lock(&m_states.state.stateLock);
  bool ret = m_states.initState;
  pthread_mutex_unlock(&m_states.state.stateLock);

  return (ret);
}


//********************************************************************
// method :
//********************************************************************
void
axAbstractMultiStates::lock(void)
{
  pthread_mutex_lock(&m_states.state.stateLock);
}


//********************************************************************
// method :
//********************************************************************
void
axAbstractMultiStates::unlock(void)
{
  pthread_mutex_unlock(&m_states.state.stateLock);
}


//********************************************************************
// method :
//********************************************************************
bool
axAbstractMultiStates::getStateUnlocked(void)
{
  return (m_states.state.state);
}


//********************************************************************
// method :
//********************************************************************
void
axAbstractMultiStates::setStateUnlocked(bool b)
{
  m_states.state.state = b;
}


