
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCsrMtaPollWork.hpp"

#if 0
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axInternalGenMta.hpp"
#include "axSnmpHRMtaReqType.hpp"
#include "axIteratorHolder.hpp"
#include "axDbEmta.hpp"
#include "axDbMtaProvStatusLog.hpp"
#endif


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
axCsrMtaPollWork::axCsrMtaPollWork()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCsrMtaPollWork::~axCsrMtaPollWork()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCsrMtaPollWork::axCsrMtaPollWork(axInternalCmts * cmts) :
  axHRMtaPollWork(cmts)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axCsrMtaPollWork::createAndSendNextRequests(
                                       axSnmpAsyncCbReqObj * cbReqObj)
{
  bool ret = true;

  setDoneGetting();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axCsrMtaPollWork::useResponse(axSnmpAsyncCbReqObj * cbReqObj)
{
}

