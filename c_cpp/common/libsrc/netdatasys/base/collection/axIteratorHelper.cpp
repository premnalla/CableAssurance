
//********************************************************************
// OBSOLETE !!! USE axIteratorHolder instead
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axIteratorHelper.hpp"

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
axIteratorHelper::axIteratorHelper() :
  m_iter(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axIteratorHelper::~axIteratorHelper()
{
  if (m_iter) {
    delete m_iter;
  }
}


//********************************************************************
// data constructor:
//********************************************************************
axIteratorHelper::axIteratorHelper(axAbstractIterator * iter) :
  m_iter(iter)
{
}


//********************************************************************
// method:
//********************************************************************
axAbstractIterator *
axIteratorHelper::getIterator(void)
{
  return (m_iter);
}


