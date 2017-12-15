
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axInternalGenMta.hpp"
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
axInternalGenMta::axInternalGenMta()
{
}


//********************************************************************
// destructor:
//********************************************************************
axInternalGenMta::~axInternalGenMta()
{
}


//********************************************************************
// data constructor:
//********************************************************************
#if 0
axInternalGenMta::axInternalGenMta(AXINT8 * mtaMac)
{
}
#endif


#if 1
//********************************************************************
// method: 
//********************************************************************
int
axInternalGenMta::keyCompare(axObject * o)
{
  int ret;

  axInternalGenMta * b = dynamic_cast<axInternalGenMta *> (o);

  ret = strcmp(getMtaMacAddress(),b->getMtaMacAddress());

  return (ret);
}
#endif


//********************************************************************
// method: 
//********************************************************************
bool
axInternalGenMta::isEmta(void)
{
  return (false);
}


//********************************************************************
// method: 
//********************************************************************
AX_UINT8
axInternalGenMta::isProvStatePass(AX_UINT8 ps)
{
  AX_UINT8 ret;

  switch (ps) {
    case AX_PKTCBL_PROV_ST_PASS:
    case AX_PKTCBL_PROV_ST_PASS_W_WARN:
    case AX_PKTCBL_PROV_ST_PASS_W_INCMPL_PARS:
      ret = 1;
      break;

    default:
      ret = 0;
      break;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axInternalGenMta::isPingStateFailure(AX_UINT8 ps)
{
  AX_UINT8 ret;

  if (ps == AX_PING_ST_HOST_UNREACHABLE) {
    ret = 1;
  } else {
    ret = 0;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axInternalGenMta::isPingStatePass(AX_UINT8 ps)
{
  AX_UINT8 ret;

  if (ps == AX_PING_ST_HOST_ALIVE) {
    ret = 1;
  } else {
    ret = 0;
  }

  return (ret);
}


//********************************************************************
//
//********************************************************************
bool
axInternalGenMta::isAddable(void)
{
  return ((isValidMacLen(getMtaMacAddress()) ? true : false));
}


//********************************************************************
// method :
//********************************************************************
bool
axInternalGenMta::isAvailable(void)
{
  axIntGenMtaNonkey_t * mtaNk = (axIntGenMtaNonkey_t *) getNonKey();
  return ((mtaNk->available ? true : false));
}


