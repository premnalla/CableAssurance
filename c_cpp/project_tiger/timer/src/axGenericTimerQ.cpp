
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <assert.h>
#include <stddef.h>
#include "axGenericTimerQ.hpp"
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
axGenericTimerQ::axGenericTimerQ()
{
}


//********************************************************************
// destructor:
//********************************************************************
axGenericTimerQ::~axGenericTimerQ()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axGenericTimerQ::axGenericTimerQ()
// {
// }


//********************************************************************
// method: add
//********************************************************************
// void
axObject *
axGenericTimerQ::add(axObject * inObj)
{
  axObject * ret = NULL;

  lock();


  axListBase::listType_t::iterator start = begin();
  axListBase::listType_t::iterator finish = end();

  axAbstractTimerObject * inTmObj;
  axAbstractTimerObject * lastTimerObj;
  time_t inPopTime;
  time_t lastPopTime;

  if (isEmpty_u()) {
    ret = add_u(inObj);
    goto EXIT_LABEL;
  }

  inTmObj = dynamic_cast<axAbstractTimerObject *> (inObj);
  assert (inTmObj != NULL);
  inPopTime = inTmObj->getPopTime();

  lastTimerObj = dynamic_cast<axAbstractTimerObject *> (getLast_u());
  lastPopTime = lastTimerObj->getPopTime();

  if (inPopTime >= lastPopTime) {
    ret = add_u(inObj);
    goto EXIT_LABEL;
  }

  for (; start != finish; start++) {
    axAbstractTimerObject * timerObj =
      dynamic_cast<axAbstractTimerObject *> (*start);
    time_t popTime = timerObj->getPopTime();
    if (inPopTime < popTime) {
      ret = add_u(inObj, start);
      goto EXIT_LABEL;
    }
  }

EXIT_LABEL:

  notify();

  unlock();

  return (ret);
}


