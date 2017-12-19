//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axIntHashMap.hpp"

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
axIntHashMap::axIntHashMap() :
  m_size(0)
{
}

//********************************************************************
// destructor:
//********************************************************************
axIntHashMap::~axIntHashMap()
{
  this->clear();
}

//********************************************************************
// data constructor:
//********************************************************************
// axIntHashMap::axIntHashMap()
// {
// }

//********************************************************************
// method: isEmpty
//********************************************************************
bool
axIntHashMap::isEmpty(void)
{
  lock();
  bool b = m_hashMap.empty();
  unlock();

  return (b);
}


//********************************************************************
// method: add
//********************************************************************
// void
axObject *
axIntHashMap::add(axObject * o)
{
  lock();
  m_hashMap[o->hashInt32()] = o;
  ++m_size;
  unlock();

  return (o);
}


//********************************************************************
// method: find
//********************************************************************
axObject *
axIntHashMap::find(axObject * o)
{
  axObject * ret = NULL;

  lock();
  intHashMap_t::iterator elem = m_hashMap.find(o->hashInt32());
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
axIntHashMap::remove(axObject * o)
{
  axObject * ret = NULL;

  lock();
  intHashMap_t::iterator elem = m_hashMap.find(o->hashInt32());
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
axIntHashMap::size(void)
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
axIntHashMap::clear(void)
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
axIntHashMap::clearAndFreeEntries(void)
{
  lock();

  intHashMap_t::iterator elemI = m_hashMap.begin();
  intHashMap_t::iterator end = m_hashMap.end();
  for(; elemI != end; elemI++) {
     axObject * o = elemI->second;
     delete o;
  }

  m_hashMap.clear();

  m_size = 0;

  unlock();
}


//********************************************************************
// method: createIterator
//********************************************************************
axAbstractIterator *
axIntHashMap::createIterator(void)
{
  axAbstractIterator * ret = NULL;

  return (ret);
}


//********************************************************************
// method: freeIterator
//********************************************************************
void
axIntHashMap::freeIterator(axAbstractIterator * iter)
{
  delete iter;
}

