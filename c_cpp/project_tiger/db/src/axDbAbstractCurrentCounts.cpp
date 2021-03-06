//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractCurrentCounts.hpp"

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
axDbAbstractCurrentCounts::axDbAbstractCurrentCounts()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbAbstractCurrentCounts::~axDbAbstractCurrentCounts()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbAbstractCurrentCounts::axDbAbstractCurrentCounts(DB_RESID_t resId) :
  axDbAbstractCounts(resId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbAbstractCurrentCounts::axDbAbstractCurrentCounts(
            DB_RESID_t resId, time_t tv_sec, axIntCounts_t * counts) :
  axDbAbstractCounts(resId, tv_sec, counts)
{
}


