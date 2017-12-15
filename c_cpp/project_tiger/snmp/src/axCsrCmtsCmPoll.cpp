
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCsrCmtsCmPoll.hpp"

#if 0
#include "axCASystemConfig.hpp"
#include "axSnmpUtils.hpp"
#include "axCAScheduler.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axAvlTree.hpp"
#include "axInternalCm.hpp"
#include "axList.hpp"
#include "axInternalChannel.hpp"
#include "axDbCmStatusLog.hpp"
#include "axCmPerfPoller.hpp"
#include "axCmPerfPollRunnableCollection.hpp"
#include "axIteratorHolder.hpp"
#include "axDbCmCurrentStatus.hpp"
#include "axDbCurrentCmPerf.hpp"
#include "axCmPerfUtils.hpp"
// #include "axDbCmStatusLog.hpp"
// #include "axDbCmPerfLog.hpp"
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
axCsrCmtsCmPoll::axCsrCmtsCmPoll()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCsrCmtsCmPoll::~axCsrCmtsCmPoll()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCsrCmtsCmPoll::axCsrCmtsCmPoll(axInternalCmts * intCmts) :
  axHighRateCmtsCmStatusTblWalk(intCmts)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axCsrCmtsCmPoll::createAndSendNextRequests(
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
axCsrCmtsCmPoll::useResponse(axSnmpAsyncCbReqObj * cbReqObj)
{
}


