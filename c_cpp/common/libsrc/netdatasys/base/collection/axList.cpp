//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include "axList.hpp"
// #include "axListIterator.hpp"

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
axList::axList()
{
}

//********************************************************************
// destructor:
//********************************************************************
axList::~axList()
{
}

//********************************************************************
// data constructor:
//********************************************************************
axList::axList(bool isFreeEntries) :
  axListBase(isFreeEntries)
{
}

//********************************************************************
// method: isEmpty
//********************************************************************
bool
axList::isEmpty(void)
{
  bool b;

  lock();

  b = axListBase::isEmpty();

  unlock();

  return (b);
}


//********************************************************************
// method: add
//********************************************************************
// void
axObject *
axList::add(axObject * o)
{
  axObject * ret;

  lock();

  ret = axListBase::add(o);

  unlock();

  return (ret);
}


//********************************************************************
// method: add
//********************************************************************
// void
axObject *
axList::add(axObject * o, axListBase::listType_t::iterator & iter)
{
  axObject * ret;

  lock();

  ret = axListBase::add(o, iter);

  unlock();

  return (ret);
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axList::getFirst(void)
{
  axObject * ret;

  lock();

  ret = axListBase::getFirst();

  unlock();

  return (ret);
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axList::getLast(void)
{
  axObject * ret;

  lock();

  ret = axListBase::getLast();

  unlock();

  return (ret);
}


//********************************************************************
// method: find
//********************************************************************
axObject *
axList::find(axObject * o)
{
  axObject * ret;

  lock();

  ret = axListBase::find(o);

  unlock();

  return (ret);
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axList::remove(axObject * o)
{
  axObject * ret;

  lock();

  ret = axListBase::remove(o);

  unlock();

  return (ret);
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axList::remove(axListBase::listType_t::iterator & iter)
{
  axObject * ret;

  lock();

  ret = axListBase::remove(iter);

  unlock();

  return (ret);
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axList::remove(void)
{
  axObject * ret;

  lock();

  ret = axListBase::remove();

  unlock();

  return (ret);
}


//********************************************************************
// method: clear
//********************************************************************
void
axList::clear(void)
{
  lock();

  axListBase::clear();

  unlock();
}


//********************************************************************
// method: clearAndFreeEntries
//********************************************************************
void
axList::clearAndFreeEntries(void)
{
  lock();

  axListBase::clearAndFreeEntries();

  unlock();
}


//********************************************************************
// method: size
//********************************************************************
size_t
axList::size(void)
{
  size_t ret;

  lock();

  ret = axListBase::size();

  unlock();

  return (ret);
}


//********************************************************************
// method: getObject
//********************************************************************
axObject *
axList::getObject(int elem)
{
  axObject * ret;

  lock();

  ret = axListBase::getObject(elem);

  unlock();

  return (ret);
}


//********************************************************************
// method: cloneList
//********************************************************************
axListBase *
axList::cloneList(void)
{
  axListBase * ret;

  lock();

  ret = axListBase::cloneList();

  unlock();

  return (ret);
}


