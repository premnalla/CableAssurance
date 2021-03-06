
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <sys/socket.h>
#include "axSocketHelper.hpp"
#include "axSnmpDataTypes.hpp"

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
axSocketHelper::axSocketHelper()
{
}


//********************************************************************
// destructor:
//********************************************************************
axSocketHelper::~axSocketHelper()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axSocketHelper::axSocketHelper()
// {
// }


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axSocketHelper::SnmpAddressTypeToAddressFamily(AX_UINT32 snmpAddrTyp)
{
  AX_UINT8 ret;

  switch (snmpAddrTyp) {
    case AX_SNMP_IPADDR_TYPE_IPv4:
      ret = AF_INET;
      break;

    case AX_SNMP_IPADDR_TYPE_IPv6:
      ret = AF_INET6;
      break;

    default:
      ret = 0;
      break;
  }

  return (ret);
}


