//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axStrHashMap.hpp"

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
axStrHashMap::axStrHashMap() :
  m_size(0)
{
}

//********************************************************************
// destructor:
//********************************************************************
axStrHashMap::~axStrHashMap()
{
  this->clear();
}

//********************************************************************
// data constructor:
//********************************************************************
// axStrHashMap::axStrHashMap()
// {
// }

//********************************************************************
// method: isEmpty
//********************************************************************
bool
axStrHashMap::isEmpty(void)
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
axStrHashMap::add(axObject * o)
{
  lock();
  auto_ptr<string> str = o->hashString();
  m_hashMap[str->c_str()] = o;
  ++m_size;
  unlock();

  return (o);
}


//********************************************************************
// method: find
//********************************************************************
axObject *
axStrHashMap::find(axObject * o)
{
  axObject * ret = NULL;

  lock();
  auto_ptr<string> str = o->hashString();
  strHashMap_t::iterator elem = m_hashMap.find(str->c_str());
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
axStrHashMap::remove(axObject * o)
{
  axObject * ret = NULL;

  lock();
  auto_ptr<string> str = o->hashString();
  strHashMap_t::iterator elem = m_hashMap.find(str->c_str());
  if (elem != m_hashMap.end()) {
    ret = elem->second;
    m_hashMap.erase(elem);
    --m_size;
  }
  unlock();

  return (ret);
}


//********************************************************************
// method: clear
//********************************************************************
void
axStrHashMap::clear(void)
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
axStrHashMap::clearAndFreeEntries(void)
{
  lock();

  strHashMap_t::iterator elemI = m_hashMap.begin();
  strHashMap_t::iterator end = m_hashMap.end();
  for(; elemI != end; elemI++) {
     axObject * o = elemI->second;
     delete o;
  }

  m_hashMap.clear();

  m_size = 0;

  unlock();
}


//********************************************************************
// method: size
//********************************************************************
size_t
axStrHashMap::size(void)
{
  size_t ret = 0;

  lock();
  ret = m_size;
  unlock();

  return (ret);
}


//********************************************************************
// method: createIterator
//********************************************************************
axAbstractIterator *
axStrHashMap::createIterator(void)
{
  axAbstractIterator * ret = NULL;

  return (ret);
}


//********************************************************************
// method: freeIterator
//********************************************************************
void
axStrHashMap::freeIterator(axAbstractIterator * iter)
{
  delete iter;
}
