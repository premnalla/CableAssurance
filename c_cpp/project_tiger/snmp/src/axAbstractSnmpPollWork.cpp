
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSnmpPollWork.hpp"

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
axAbstractSnmpPollWork::axAbstractSnmpPollWork()
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractSnmpPollWork::~axAbstractSnmpPollWork()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axAbstractSnmpPollWork::axAbstractSnmpPollWork(axSnmpSession * s) :
// {
// }

//********************************************************************
// method:
//********************************************************************
void
axAbstractSnmpPollWork::freeResponse(netsnmp_pdu ** resp)
{
  if (*resp) {
    snmp_free_pdu(*resp);
    *resp = NULL;
  }
}


