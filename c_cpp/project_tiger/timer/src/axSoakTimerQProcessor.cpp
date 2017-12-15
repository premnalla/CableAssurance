
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <assert.h>
#include "axCALog.hpp"
#include "axSoakTimerQProcessor.hpp"
#include "axAbstractTimerObject.hpp"
#include "axSoakTimerQ.hpp"

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
axSoakTimerQProcessor::axSoakTimerQProcessor()
{
}


//********************************************************************
// destructor:
//********************************************************************
axSoakTimerQProcessor::~axSoakTimerQProcessor()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axSoakTimerQProcessor::axSoakTimerQProcessor()
// {
// }


//********************************************************************
// method:
//********************************************************************
AX_INT32
axSoakTimerQProcessor::run(void)
{
  AX_INT32 ret = 0;

  axSoakTimerQ * timer = axSoakTimerQ::getInstance();
  struct timeval currTm;

  axAbstractTimerObject * timerQObj;
  axObject              * o;
  
  while ((o=timer->getFirstWaiting()) != NULL) {

    // printf("axSoakTimerQProcessor::run(): got an item...\n");

    timerQObj = dynamic_cast<axAbstractTimerObject *> (o);
    assert (timerQObj != NULL);

    gettimeofday(&currTm, NULL);

    time_t popTimeT = timerQObj->getPopTime();

    if (popTimeT <= currTm.tv_sec) {

      // remove from one Q
      timerQObj = dynamic_cast<axAbstractTimerObject *> (timer->remove());

      // call callback
      // poppedQ->add(timerQObj);
      timerQObj->timerCallback();

      delete timerQObj;

    } else {

      // printf("axSoakTimerQProcessor::run(): time not yet up for item ...\n");

      // wait until another object is added to Q or it is time to proces
      // the first item in Q
      timer->timedWait(popTimeT);

    }

  }

  return (ret);
}


