
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcOutagePercent.hpp"
#include "axOutageDataTypes.hpp"

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
axHfcOutagePercent::axHfcOutagePercent() :
  axHfcOutage(0, NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcOutagePercent::~axHfcOutagePercent()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcOutagePercent::axHfcOutagePercent(INTDS_RESID_t cmtsResId, 
    AX_INT8 * hfcName) :
  axHfcOutage(cmtsResId, hfcName)
{
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axHfcOutagePercent::getAlarmSubType(void)
{
  return (AX_HFC_OUTAGE_ST_PERCENT);
}


