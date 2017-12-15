
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcOutageDetectionRunnable.hpp"
#include "axCAScheduler.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axHfcCounts.hpp"
#include "axCmtsCounts.hpp"

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
axHfcOutageDetectionRunnable::axHfcOutageDetectionRunnable() :
  axAbstractCARunnable(NULL),
  m_intCmts(NULL)
{
  setPriority(HFC_OUTAGE_DETECT_PRIORITY);
}


//********************************************************************
// destructor:
//********************************************************************
axHfcOutageDetectionRunnable::~axHfcOutageDetectionRunnable()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcOutageDetectionRunnable::axHfcOutageDetectionRunnable(
    axAbstractCARunnableCollection * rc, axInternalCmts * inCmts) :
  axAbstractCARunnable(rc),
  m_intCmts(inCmts)
{
  setPriority(HFC_OUTAGE_DETECT_PRIORITY);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axHfcOutageDetectionRunnable::run(void)
{
  AX_INT32 ret = 0;

  printf("In axHfcOutageDetectionRunnable::run() entry...\n");

  axCmtsCounts cmtsCounts(m_intCmts);
  cmtsCounts.run();

  axHfcCounts hfcCounts(m_intCmts);
  hfcCounts.run();

#if 0
  {

    {
      axReadLockOperator cmtsLock(m_intCmts->getLock());
      axIntCmtsNonkey_t * cmtsNonkeyPart = (axIntCmtsNonkey_t *) 
                                              m_intCmts->getNonKey();
    }

  }
#endif


// EXIT_LABEL:

  printf("In axHfcOutageDetectionRunnable::run() exit...\n");

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axHfcOutageDetectionRunnable::nextAction(void)
{
  axAbstractCARunnableCollection * rc = getRunnableCollection();

  rc->processRunnableComplete(this);
}



