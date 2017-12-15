
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCmtsCmPerfPollRunnable.hpp"
#include "axCAScheduler.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axInternalUtils.hpp"
#include "axHighRateCmtsCmStatusTblWalk.hpp"

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
axCmtsCmPerfPollRunnable::axCmtsCmPerfPollRunnable() :
  axAbstractCARunnable(NULL),
  m_intCmts(NULL)
{
  setPriority(CM_PERF_POLL_PRIORITY);
}


//********************************************************************
// destructor:
//********************************************************************
axCmtsCmPerfPollRunnable::~axCmtsCmPerfPollRunnable()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmtsCmPerfPollRunnable::axCmtsCmPerfPollRunnable(axAbstractCARunnableCollection * rc, 
    axInternalCmts * inCmts) :
  axAbstractCARunnable(rc),
  m_intCmts(inCmts)
{
  setPriority(CM_PERF_POLL_PRIORITY);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axCmtsCmPerfPollRunnable::run(void)
{
  AX_INT32 ret = 0;

  printf("In axCmtsCmPerfPollRunnable::run() entry...\n");

  {
    // AX_INT8 ipAddress[INT_IP_ADDRESS_SIZE];

    /* TODO: need to add code here... */
#if 0
    {
      axReadLockOperator cmtsLock(m_intCmts->getLock());
      axIntCmtsNonkey_t * cmtsNonkeyPart = (axIntCmtsNonkey_t *) 
                                              m_intCmts->getNonKey();
    }


    {
      axHighRateCmtsCmStatusTblWalk cmtsCmStatusTableWalk(sess, m_intCmts);
      cmtsCmStatusTableWalk.run();
    }
#endif

  }


// EXIT_LABEL:

  printf("In axCmtsCmPerfPollRunnable::run() exit...\n");

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axCmtsCmPerfPollRunnable::nextAction(void)
{
  axAbstractCARunnableCollection * rc = getRunnableCollection();

  rc->processRunnableComplete(this);
}


//********************************************************************
// method:
//********************************************************************
void
axCmtsCmPerfPollRunnable::addSubject(axObject * o)
{
}


