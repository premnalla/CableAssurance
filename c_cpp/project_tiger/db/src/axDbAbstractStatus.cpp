
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractStatus.hpp"

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
axDbAbstractStatus::axDbAbstractStatus() :
  m_id((AX_UINT64)0),
  m_resId(0),
  m_lastLogTime(0)
{
  // initAtInstantiation();
}


//********************************************************************
// destructor:
//********************************************************************
axDbAbstractStatus::~axDbAbstractStatus()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbAbstractStatus::axDbAbstractStatus(DB_RESID_t resId) :
  m_id((AX_UINT64)0),
  m_resId(resId),
  m_lastLogTime(0)
{
  // initAtInstantiation();
}


//********************************************************************
// data constructor:
//********************************************************************
axDbAbstractStatus::axDbAbstractStatus(DB_RESID_t resId, 
                                                      time_t tv_sec) :
  m_id((AX_UINT64)0),
  m_resId(resId),
  m_lastLogTime((AX_UINT32)tv_sec)
{
  // initAtInstantiation();
}

//********************************************************************
// method:
//********************************************************************
void
axDbAbstractStatus::initAtInstantiation(void)
{
  struct timeval tm;
  gettimeofday(&tm, NULL);
  m_lastLogTime = tm.tv_sec;
}


