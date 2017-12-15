
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axMtaOutageTimerEntry.hpp"
#include "axInternalUtils.hpp"

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
axMtaOutageTimerEntry::axMtaOutageTimerEntry() :
  m_cmtsResId(0)
{
  m_mtaMacAddress[0] = '\0';
}


//********************************************************************
// destructor:
//********************************************************************
axMtaOutageTimerEntry::~axMtaOutageTimerEntry()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axMtaOutageTimerEntry::axMtaOutageTimerEntry(
    axMtaOutage * mtaOutage, time_t & popTime) :
  axOutageTimerEntry(mtaOutage, popTime),
  m_cmtsResId(mtaOutage->getCmtsResId())
{
  copyIntMac(m_mtaMacAddress, mtaOutage->getMtaMacAddress());
}


//********************************************************************
// method:
//********************************************************************
bool
axMtaOutageTimerEntry::timerCallback(void)
{
  bool ret = false;

  printf("In axMtaOutageTimerEntry::timerCallback() entry...\n");



  // lastly but not least ...
  ret = true;

EXIT_LABEL:

  printf("In axMtaOutageTimerEntry::timerCallback() exit...\n");

  return (ret);
}


#if 0
//********************************************************************
// method:
//********************************************************************
INTDS_RESID_t
axMtaOutageTimerEntry::getCmtsResId(void)
{
  return (m_cmtsResId);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axMtaOutageTimerEntry::getHfcName(void)
{
  return (m_hfcName);
}


//********************************************************************
// method:
//********************************************************************
bool
axMtaOutageTimerEntry::isKeyEqual(axObject * o)
{
  bool ret = false;

  axMtaOutageTimerEntry * o2 = dynamic_cast<axMtaOutageTimerEntry *> (o);

  // if ()


  ret = true;

EXIT_LABEL:

  return (ret);
}
#endif


