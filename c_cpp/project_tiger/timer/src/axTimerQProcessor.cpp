
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <assert.h>
#include <stdio.h>
#include "axCALog.hpp"
#include "axTimerQProcessor.hpp"
#include "axGlobalTimer.hpp"
#include "axPoppedTimerQ.hpp"
#include "axAbstractTimerObject.hpp"

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
axTimerQProcessor::axTimerQProcessor()
{
}


//********************************************************************
// destructor:
//********************************************************************
axTimerQProcessor::~axTimerQProcessor()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axTimerQProcessor::axTimerQProcessor()
// {
// }


//********************************************************************
// method:
//********************************************************************
AX_INT32
axTimerQProcessor::run(void)
{
  AX_INT32 ret = 0;

  axGlobalTimer * timer = axGlobalTimer::getInstance();
  axPoppedTimerQ * poppedQ = axPoppedTimerQ::getInstance();
  struct timeval currTm;

  axAbstractTimerObject * timerQObj;
  axObject              * o;
  
  while ((o=timer->getFirstWaiting()) != NULL) {

    // printf("axTimerQProcessor::run(): got an item...\n");

    timerQObj = dynamic_cast<axAbstractTimerObject *> (o);
    assert (timerQObj != NULL);

    gettimeofday(&currTm, NULL);

    time_t popTimeT = timerQObj->getPopTime();

    if (popTimeT <= currTm.tv_sec) {

      // remove from one Q
      timerQObj = dynamic_cast<axAbstractTimerObject *> (timer->remove());

      // add to second Q
      poppedQ->add(timerQObj);

    } else {

      // printf("axTimerQProcessor::run(): time not yet up for item ...\n");

      // wait until another object is added to Q or it is time to proces
      // the first item in Q
      timer->timedWait(popTimeT);

    }

  }

  return (ret);
}


