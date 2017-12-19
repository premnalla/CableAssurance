
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <unistd.h> // sleep()
#include <stdio.h> // sleep()
#include "axMultipleReaderLock.hpp"

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
axMultipleReaderLock::axMultipleReaderLock() :
  m_readCounter(0)
{
  pthread_mutex_init(&m_readLock, NULL);
  pthread_mutex_init(&m_writeLock, NULL);
}


//********************************************************************
// destructor:
//********************************************************************
axMultipleReaderLock::~axMultipleReaderLock()
{
  pthread_mutex_destroy(&m_readLock);
  pthread_mutex_destroy(&m_writeLock);
}


//********************************************************************
// data constructor:
//********************************************************************
// axMultipleReaderLock::axMultipleReaderLock()
// {
// }


//********************************************************************
// method:
//********************************************************************
void
axMultipleReaderLock::readPreAccess(void)
{
  pthread_mutex_lock(&m_writeLock);
  pthread_mutex_lock(&m_readLock);

  ++m_readCounter;

  pthread_mutex_unlock(&m_writeLock);
  pthread_mutex_unlock(&m_readLock);
}

//********************************************************************
// method:
//********************************************************************
void
axMultipleReaderLock::readPostAccess(void)
{
  pthread_mutex_lock(&m_readLock);

  --m_readCounter;
  if (m_readCounter < 0) {
    m_readCounter = 0;
  }

  pthread_mutex_unlock(&m_readLock);
}

//********************************************************************
// method:
//********************************************************************
void
axMultipleReaderLock::writePreAccess(void)
{
  pthread_mutex_lock(&m_writeLock);

  bool gotAccess = false;
  while (!gotAccess) {
    pthread_mutex_lock(&m_readLock);

    /**
     *  CAREFUL: need explicit unlocks in both parts of if !!!
     */
    if (m_readCounter == 0) {
      gotAccess = true;
      pthread_mutex_unlock(&m_readLock);
    } else {
      pthread_mutex_unlock(&m_readLock);
      printf("Some readers still exist, sleeping....\n");
      sleep(1);
    }

  }

  // cant release before completing access/walk
  // pthread_mutex_unlock(&m_writeLock);
}

//********************************************************************
// method:
//********************************************************************
void
axMultipleReaderLock::writePostAccess(void)
{
  pthread_mutex_unlock(&m_writeLock);
}

