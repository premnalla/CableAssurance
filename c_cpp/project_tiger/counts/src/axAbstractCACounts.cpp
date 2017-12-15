
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCACounts.hpp"
#include "axCASystemConfig.hpp"

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
axAbstractCACounts::axAbstractCACounts()
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractCACounts::~axAbstractCACounts()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axAbstractCACounts::axAbstractCACounts()
// {
// }


//********************************************************************
// method:
//********************************************************************
bool
axAbstractCACounts::needToUpdate(struct timeval * tm, axIntCounts_t * c)
{
  bool ret;

  if ((tm->tv_sec - c->lastLogTime) >
      (axCASystemConfig::getInstance()->get_unchanged_log_interval()
       * 60)) {
     ret = true;
     c->lastLogTime = tm->tv_sec;
  } else {
     ret = false;
  }

  return (ret);
}
