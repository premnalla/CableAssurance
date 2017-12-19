
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axSortedList.hpp"

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
axSortedList::axSortedList()
{
}


//********************************************************************
// destructor:
//********************************************************************
axSortedList::~axSortedList()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axSortedList::axSortedList(bool isFreeEntries) :
  axListBase(isFreeEntries)
{
}


//********************************************************************
// method: add
//********************************************************************
axObject *
axSortedList::add(axObject * inO)
{
  axObject * ret = NULL;
  bool       inserted;
  axListBase::listType_t::iterator start;
  axListBase::listType_t::iterator finish;

  axObject * o = getLast();

  if (!o) {
    ret = axListBase::add(inO);
    goto EXIT_LABEL;
  }

  if (inO->keyCompare(o) >= 0) {
    ret = axListBase::add(inO);
    goto EXIT_LABEL;
  }

  inserted = false;
  start = begin();
  finish = end();
  for (; start != finish && !inserted; start++) {
    o = *start;
    if (inO->keyCompare(o) < 0) {
      ret = axListBase::add(inO, start);
      inserted = true;
    }
  } // for

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method: add
// comments: this method doesn't make sense for this class
//********************************************************************
axObject *
axSortedList::add(axObject * o, listType_t::iterator & inIter)
{
  return (NULL);
}


