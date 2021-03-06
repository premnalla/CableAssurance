
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCAOutage.hpp"

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
axAbstractCAOutage::axAbstractCAOutage() :
  m_outageId(0),
  m_alarmId(0),
  m_alarmResId(0)
{
  struct timeval tm;
  gettimeofday(&tm, NULL);
  m_detectionTime = tm.tv_sec;
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractCAOutage::~axAbstractCAOutage()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAbstractCAOutage::axAbstractCAOutage(time_t detectTm) :
  m_outageId(0),
  m_alarmId(0),
  m_alarmResId(0),
  m_detectionTime(detectTm)
{
}


//********************************************************************
// method:
//********************************************************************
time_t
axAbstractCAOutage::getDetectionTime(void)
{
  return (m_detectionTime);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAOutage::setOutageId(AX_UINT32 id)
{
  m_outageId = id;
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axAbstractCAOutage::getOutageId(void)
{
  return (m_outageId);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAOutage::setResId(INTDS_RESID_t resId)
{
  m_alarmResId = resId;
}


//********************************************************************
// method:
//********************************************************************
INTDS_RESID_t
axAbstractCAOutage::getResId(void)
{
  return (m_alarmResId);
}


//********************************************************************
// method:
//********************************************************************
void
axAbstractCAOutage::setAlarmId(AX_UINT32 id)
{
  m_alarmId = id;
}


//********************************************************************
// method:
//********************************************************************
AX_UINT32
axAbstractCAOutage::getAlarmId(void)
{
  return (m_alarmId);
}


