//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axStrHashMultiMap.hpp"

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
axStrHashMultiMap::axStrHashMultiMap() :
  m_size(0)
{
}

//********************************************************************
// destructor:
//********************************************************************
axStrHashMultiMap::~axStrHashMultiMap()
{
  this->clear();
}

//********************************************************************
// data constructor:
//********************************************************************
// axStrHashMultiMap::axStrHashMultiMap()
// {
// }

//********************************************************************
// method: isEmpty
//********************************************************************
bool
axStrHashMultiMap::isEmpty(void)
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
axStrHashMultiMap::add(axObject * o)
{
  lock();
  auto_ptr<string> str = o->hashString();
  m_hashMap.insert(strHashMultiMap_t::value_type(str->c_str(), o));
  ++m_size;
  unlock();

  return (o);
}


//********************************************************************
// method: find
//********************************************************************
axObject *
axStrHashMultiMap::find(axObject * o)
{
  axObject * ret = NULL;

  lock();
  auto_ptr<string> str = o->hashString();
  strHashMultiMap_t::iterator elem = m_hashMap.find(str->c_str());
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
axStrHashMultiMap::remove(axObject * o)
{
  axObject * ret = NULL;

  lock();
  auto_ptr<string> str = o->hashString();
  strHashMultiMap_t::iterator elem = m_hashMap.find(str->c_str());
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
axStrHashMultiMap::clear(void)
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
axStrHashMultiMap::clearAndFreeEntries(void)
{
  lock();

  strHashMultiMap_t::iterator elemI = m_hashMap.begin();
  strHashMultiMap_t::iterator end = m_hashMap.end();
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
axStrHashMultiMap::size(void)
{
  size_t ret = 0;

  lock();
  ret = m_size;
  unlock();

  return (ret);
}


//********************************************************************
// method: getElements
//********************************************************************
#if 1
auto_ptr<axListBase>
axStrHashMultiMap::getElements(axObject * o)
{
  auto_ptr<axListBase> p(new axListBase());

  lock();

  auto_ptr<string> str = o->hashString();
  pair<strHashMultiMap_t::const_iterator, strHashMultiMap_t::const_iterator> 
    iter = m_hashMap.equal_range(str->c_str());

  for (strHashMultiMap_t::const_iterator i = iter.first; i != iter.second; 
       i++) {
    p->add(((*i).second));
  }

  unlock();

  return (p);
}
#else
axListBase *
axStrHashMultiMap::getElements(axObject * o)
{
  axListBase * p = new axListBase();

  if (!p) {
    return (p);
  }

  lock();

  pair<strHashMultiMap_t::const_iterator, strHashMultiMap_t::const_iterator>
    iter = m_hashMap.equal_range(o->hashString());

  for (strHashMultiMap_t::const_iterator i = iter.first; i != iter.second;
       i++) {
    p->add(((*i).second));
  }

  unlock();

  return (p);
}
#endif


//********************************************************************
// method: removeElements
//********************************************************************
#if 1
auto_ptr<axListBase>
axStrHashMultiMap::removeElements(axObject * o)
{
  auto_ptr<axListBase> p(new axListBase());

  lock();

  auto_ptr<string> str = o->hashString();
  pair<strHashMultiMap_t::iterator, strHashMultiMap_t::iterator>
    iter = m_hashMap.equal_range(str->c_str());

  for (strHashMultiMap_t::iterator i = iter.first; i != iter.second;) {
    strHashMultiMap_t::iterator tempIter = i++;
    axObject * listO = (*tempIter).second;
    if (listO->isKeyEqual(o)) {
      p->add(listO);
      m_hashMap.erase(tempIter);
      --m_size;
    }
  }

  unlock();

  return (p);
}
#else
axListBase *
axStrHashMultiMap::removeElements(axObject * o)
{
  axListBase * p = new axListBase();

  if (!p) {
    return (p);
  }

  lock();

  pair<strHashMultiMap_t::iterator, strHashMultiMap_t::iterator>
    iter = m_hashMap.equal_range(o->hashString());

  for (strHashMultiMap_t::iterator i = iter.first; i != iter.second;) {
    strHashMultiMap_t::iterator tempIter = i++;
    axObject * listO = (*tempIter).second;
    if (listO->isKeyEqual(o)) {
      p->add(listO);
      m_hashMap.erase(tempIter);
      --m_size;
    }
  }

  unlock();

  return (p);
}
#endif


//********************************************************************
// method: printElements
//********************************************************************
void
axStrHashMultiMap::printElements(axObject *)
{
}

//********************************************************************
// method: createIterator
//********************************************************************
axAbstractIterator *
axStrHashMultiMap::createIterator(void)
{
  axAbstractIterator * ret = NULL;

  return (ret);
}


//********************************************************************
// method: freeIterator
//********************************************************************
void
axStrHashMultiMap::freeIterator(axAbstractIterator * iter)
{
  delete iter;
}


