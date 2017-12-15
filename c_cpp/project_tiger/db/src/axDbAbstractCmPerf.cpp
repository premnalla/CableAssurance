
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractCmPerf.hpp"

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
axDbAbstractCmPerf::axDbAbstractCmPerf() :
  m_id((AX_UINT64)0),
  m_resId(0),
  m_lastLogTime(0),
  m_downstreamSnr(0),
  m_downstreamPower(0),
  m_upstreamPower(0),
  m_t1Timeouts(0),
  m_t2Timeouts(0),
  m_t3Timeouts(0),
  m_t4Timeouts(0)
{
  // initAtInstantiation();
}


//********************************************************************
// destructor:
//********************************************************************
axDbAbstractCmPerf::~axDbAbstractCmPerf()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbAbstractCmPerf::axDbAbstractCmPerf(DB_RESID_t cmResId) :
  m_id((AX_UINT64)0),
  m_resId(cmResId),
  m_lastLogTime(0),
  m_downstreamSnr(0),
  m_downstreamPower(0),
  m_upstreamPower(0),
  m_t1Timeouts(0),
  m_t2Timeouts(0),
  m_t3Timeouts(0),
  m_t4Timeouts(0)
{
  // initAtInstantiation();
}

//********************************************************************
// data constructor:
//********************************************************************
axDbAbstractCmPerf::axDbAbstractCmPerf(DB_RESID_t cmResId, 
                              time_t tv_sec, axIntCmNonkey_t * cmNk) :
  m_id((AX_UINT64)0),
  m_resId(cmResId),
  // m_lastLogTime(cmNk->lastPerfLogTime),
  m_lastLogTime((AX_UINT32)tv_sec),
  m_downstreamSnr(cmNk->downstreamSNR),
  m_downstreamPower(cmNk->downstreamPower),
  m_upstreamPower(cmNk->upstreamPower),
  m_t1Timeouts(cmNk->t1Timeouts),
  m_t2Timeouts(cmNk->t2Timeouts),
  m_t3Timeouts(cmNk->t3Timeouts),
  m_t4Timeouts(cmNk->t4Timeouts)
{
  // initAtInstantiation();
}


//********************************************************************
// method:
//********************************************************************
void
axDbAbstractCmPerf::initAtInstantiation(void)
{
  struct timeval tm;
  gettimeofday(&tm, NULL);
  m_lastLogTime = tm.tv_sec;
}



