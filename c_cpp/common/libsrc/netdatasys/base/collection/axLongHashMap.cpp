//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axLongHashMap.hpp"

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
axLongHashMap::axLongHashMap() :
  m_size(0)
{
}

//********************************************************************
// destructor:
//********************************************************************
axLongHashMap::~axLongHashMap()
{
  this->clear();
}

//********************************************************************
// data constructor:
//********************************************************************
// axLongHashMap::axLongHashMap()
// {
// }

//********************************************************************
// method: isEmpty
//********************************************************************
bool
axLongHashMap::isEmpty(void)
{
  lock();
  bool b = m_hashMap.empty();
  unlock();

  return (b);
}


//********************************************************************
// method: add
//********************************************************************
void
axLongHashMap::add(axObject * o)
{
  lock();
  m_hashMap[o->hashLong()] = o;
  ++m_size;
  unlock();
}


//********************************************************************
// method: find
//********************************************************************
axObject *
axLongHashMap::find(axObject * o)
{
  axObject * ret = NULL;

  lock();
  longHashMap_t::iterator elem = m_hashMap.find(o->hashLong());
  if (elem != m_hashMap.end()) {
    ret = elem->second;
  }
  unlock();

  return (ret);
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axLongHashMap::remove(axObject * o)
{
  axObject * ret = NULL;

  lock();
  longHashMap_t::iterator elem = m_hashMap.find(o->hashLong());
  if (elem != m_hashMap.end()) {
    ret = elem->second;
    m_hashMap.erase(elem);
    --m_size;
  }
  unlock();

  return (ret);
}


//********************************************************************
// method: size
//********************************************************************
size_t
axLongHashMap::size(void)
{
  size_t ret = 0;

  lock();
  ret = m_size;
  unlock();

  return (ret);
}


//********************************************************************
// method: clear
//********************************************************************
void
axLongHashMap::clear(void)
{
  lock();
  m_hashMap.clear();
  m_size = 0;
  unlock();
}


//********************************************************************
// method: clearAndFreeEntries
//********************************************************************
void
axLongHashMap::clearAndFreeEntries(void)
{
  lock();

  longHashMap_t::iterator elemI = m_hashMap.begin();
  longHashMap_t::iterator end = m_hashMap.end();
  for(; elemI != end; elemI++) {
     axObject * o = elemI->second;
     delete o;
  }

  m_hashMap.clear();

  m_size = 0;

  unlock();
}


#if 0
//********************************************************************
// method: lock/unlock
//********************************************************************
void
axLongHashMap::lock(void)
{
}


//********************************************************************
// method: lock/unlock
//********************************************************************
void
axLongHashMap::unlock(void)
{
}
#endif

//********************************************************************
// method: begin
//********************************************************************
axLongHashMap::longHashMap_t::iterator
axLongHashMap::begin(void)
{
  axLongHashMap::longHashMap_t::iterator iter = m_hashMap.begin();

  return (iter);
}


//********************************************************************
// method: end
//********************************************************************
axLongHashMap::longHashMap_t::iterator
axLongHashMap::end(void)
{
  axLongHashMap::longHashMap_t::iterator iter = m_hashMap.end();

  return (iter);
}


//********************************************************************
// method: createIterator
//********************************************************************
axAbstractIterator *
axLongHashMap::createIterator(void)
{
  axAbstractIterator * ret = NULL;

  return (ret);
}


//********************************************************************
// method: freeIterator
//********************************************************************
void
axLongHashMap::freeIterator(axAbstractIterator * iter)
{
  delete iter;
}

