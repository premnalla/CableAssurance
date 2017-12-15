
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractCounts.hpp"

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
axDbAbstractCounts::axDbAbstractCounts() :
  m_id((AX_UINT64)0),
  m_resId(0),
  m_totalCm(0),
  m_onlineCm(0),
  m_totalMta(0),
  m_availableMta(0),
  m_timeSec(0)
{
  // initAtInstantiation();
}


//********************************************************************
// destructor:
//********************************************************************
axDbAbstractCounts::~axDbAbstractCounts()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbAbstractCounts::axDbAbstractCounts(DB_RESID_t resId) :
  m_id((AX_UINT64)0),
  m_resId(resId),
  m_totalCm(0),
  m_onlineCm(0),
  m_totalMta(0),
  m_availableMta(0),
  m_timeSec(0)
{
  // initAtInstantiation();
}


//********************************************************************
// data constructor:
//********************************************************************
axDbAbstractCounts::axDbAbstractCounts(DB_RESID_t resId,
                              time_t tv_sec, axIntCounts_t * counts) :
  m_id((AX_UINT64)0),
  m_resId(resId),
  m_totalCm(counts->cm.total),
  m_onlineCm(counts->cm.online),
  m_totalMta(counts->mta.total),
  m_availableMta(counts->mta.available),
  m_timeSec(tv_sec)
{
  // initAtInstantiation();
}


//********************************************************************
// method:
//********************************************************************
void
axDbAbstractCounts::initAtInstantiation(void)
{
  struct timeval tm;
  gettimeofday(&tm, NULL);
  m_timeSec = tm.tv_sec;
}


