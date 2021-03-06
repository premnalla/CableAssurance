//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axStrHashMap_mts.hpp"

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
axStrHashMap_mts::axStrHashMap_mts()
{
  pthread_mutex_init(&m_collectionLock, NULL);
}

//********************************************************************
// destructor:
//********************************************************************
axStrHashMap_mts::~axStrHashMap_mts()
{
  pthread_mutex_destroy(&m_collectionLock);
}

//********************************************************************
// data constructor:
//********************************************************************
// axStrHashMap_mts::axStrHashMap_mts()
// {
// }

//********************************************************************
// method: lock
//********************************************************************
void
axStrHashMap_mts::lock(void)
{
  pthread_mutex_lock(&m_collectionLock);
}


//********************************************************************
// method: unlock
//********************************************************************
void
axStrHashMap_mts::unlock(void)
{
  pthread_mutex_unlock(&m_collectionLock);
}


