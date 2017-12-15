
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <time.h>
#include "axCALog.hpp"
#include "axDate.hpp"
#include "axCblAssureConstants.hpp"

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
axDate::axDate()
{
  init();
  time_t timeT;
  time(&timeT);
  m_sec = timeT;
  struct tm bdTime;
  // memset(&bdTime, 0, sizeof(bdTime));
  gmtime_r(&timeT, &bdTime);
  m_month = (AX_UINT8 ) bdTime.tm_mon;
  m_day = (AX_UINT8 ) bdTime.tm_mday;
  m_year = (AX_UINT16 ) (1900 + bdTime.tm_year);
  m_hour = (AX_UINT8 ) bdTime.tm_hour;
  m_minute = (AX_UINT8 ) bdTime.tm_min;
  // ACE_DEBUG((LM_DEBUG, "axDate: %d-%d-%d", m_month,m_day,m_year));
}


//********************************************************************
// destructor:
//********************************************************************
axDate::~axDate()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDate::axDate(AX_INT32 m, AX_INT32 d, AX_INT32 y)
{
  init();
  m_month = m;
  m_day = d;
  m_year = y - 1900;
  struct tm bdTime;
  memset(&bdTime, 0, sizeof(bdTime));
  bdTime.tm_mon = m;
  bdTime.tm_mday = d;
  bdTime.tm_year = m_year;
  time_t timeT = mktime(&bdTime);
  m_sec = timeT;
}


//********************************************************************
// data constructor:
//********************************************************************
axDate::axDate(AX_INT32 m, AX_INT32 d, AX_INT32 y, AX_INT32 h, 
                                                         AX_INT32 min)
{
  init();
  m_month = m;
  m_day = d;
  m_year = y - 1900;
  m_hour = h;
  m_minute = min;
  struct tm bdTime;
  memset(&bdTime, 0, sizeof(bdTime));
  bdTime.tm_mon = m;
  bdTime.tm_mday = d;
  bdTime.tm_year = m_year;
  bdTime.tm_hour = h;
  bdTime.tm_min = min;
  time_t timeT = mktime(&bdTime);
  m_sec = timeT;
}


//********************************************************************
// data constructor:
//********************************************************************
axDate::axDate(AX_UINT32 s)
{
  init();
  m_sec = s;
}


//********************************************************************
// method:
//********************************************************************
void
axDate::init(void)
{
  m_month = 1;
  m_day = 1;
  m_year = 1970;
  m_hour = 0;
  m_minute = 0;
  m_sec = 0;
}


//********************************************************************
// method:
//********************************************************************
void
axDate::rollDays(AX_INT32 nDays)
{
  static const char * myName="rollDays:";

  // ACE_DEBUG((LM_DEBUG, "%s sec=%u\n", myName, m_sec));
  m_sec += (nDays * AX_SECONDS_IN_A_DAY);
  // ACE_DEBUG((LM_DEBUG, "%s sec=%u\n", myName, m_sec));

  struct tm bdTime;
  gmtime_r((time_t *)&m_sec, &bdTime);
  m_month = (AX_UINT8 ) bdTime.tm_mon;
  m_day = (AX_UINT8 ) bdTime.tm_mday;
  m_year = (AX_UINT16 ) (1900 + bdTime.tm_year);
  m_hour = (AX_UINT8 ) bdTime.tm_hour;
  m_minute = (AX_UINT8 ) bdTime.tm_min;
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axDate::getMonth(void)
{
  return (m_month);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axDate::getDayOfMonth(void)
{
  return (m_day);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT16
axDate::getYear(void)
{
  return (m_year);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT8
axDate::getHour(void)
{
  return (m_hour);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT8
axDate::getMinute(void)
{
  return (m_minute);
}

//********************************************************************
// method:
//********************************************************************
AX_UINT32
axDate::getTimeSeconds(void)
{
  return (m_sec);
}


