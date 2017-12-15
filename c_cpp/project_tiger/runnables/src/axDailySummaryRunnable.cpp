
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCountsDataTypes.hpp"
#include "axCALog.hpp"
#include "axDailySummaryRunnable.hpp"
#include "axCAScheduler.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axDbSummaryFlags.hpp"
#include "axDbLastCompSummary.hpp"
#include "axDailySummary.hpp"

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
axDailySummaryRunnable::axDailySummaryRunnable() :
  axAbstractCARunnable(NULL)
{
  setPriority(DAILY_SUMMARY_PRIORITY);
}


//********************************************************************
// destructor:
//********************************************************************
axDailySummaryRunnable::~axDailySummaryRunnable()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDailySummaryRunnable::axDailySummaryRunnable(
                                axAbstractCARunnableCollection * rc) :
  axAbstractCARunnable(rc)
{
  setPriority(DAILY_SUMMARY_PRIORITY);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axDailySummaryRunnable::run(void)
{
  static const char * myName="dailySummaryRblRun:";

  AX_INT32 ret = 0;

  ACE_DEBUG((LM_MISC_DEBUG, "%s entry...\n", myName));

  axDbSummaryFlags     summFlags;
  axDbLastCompSummary  lastSumm;
  axDailySummary       summ;

  if (summFlags.getRow() && lastSumm.getRow()) {
    summ.summarize(summFlags, lastSumm);
  }

  ACE_DEBUG((LM_MISC_DEBUG, "%s exit...\n", myName));

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDailySummaryRunnable::nextAction(void)
{
  axAbstractCARunnableCollection * rc = getRunnableCollection();

  rc->processRunnableComplete(this);
}


//********************************************************************
// method:
//********************************************************************
void
axDailySummaryRunnable::addSubject(axObject * o)
{
}


