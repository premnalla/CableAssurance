//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include "axVector.hpp"

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
axVector::axVector()
{
}

//********************************************************************
// destructor:
//********************************************************************
axVector::~axVector()
{
  this->clear();
}

//********************************************************************
// data constructor:
//********************************************************************
// axVector::axVector()
// {
// }


//********************************************************************
// method: isEmpty
//********************************************************************
bool
axVector::isEmpty(void)
{
  lock();
  bool b = m_vec.empty();
  unlock();

  return (b);
}


//********************************************************************
// method: add
//********************************************************************
// void
axObject *
axVector::add(axObject * o)
{
  lock();

  m_vec.push_back(o);

  unlock();

  return (o);
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axVector::remove(void)
{
  axObject * ret = NULL;

  lock();

  if (!m_vec.empty()) {
    vectorType_t::iterator item = m_vec.begin();
    ret = *item;
    m_vec.erase(item);
  }

  unlock();

  return (ret);
}


//********************************************************************
// method: clear
//********************************************************************
axObject *
axVector::operator[] (int pos)
{
  axObject * ret = NULL;

  lock();

  ret = m_vec[pos];

  unlock();

  return (ret);
}


//********************************************************************
// method: clear
//********************************************************************
void
axVector::clear(void)
{
  lock();

  m_vec.clear();

  unlock();
}


//********************************************************************
// method: clearAndFreeEntries
//********************************************************************
void
axVector::clearAndFreeEntries(void)
{
  lock();

  vectorType_t::iterator item = m_vec.begin();
  vectorType_t::iterator end = m_vec.end();
  for(; item != end; item++) {
    axObject * oI = *item;
    delete oI;
  }

  m_vec.clear();

  unlock();
}


//********************************************************************
// method: size
//********************************************************************
size_t
axVector::size(void)
{
  size_t ret = 0;

  lock();
  ret = m_vec.size();
  unlock();

  return (ret);
}


//********************************************************************
// method: find
//********************************************************************
axObject *
axVector::find(axObject * o)
{
  axObject * ret = NULL;

  lock();

  bool found = false;
  for (unsigned int i=0; !found && i<m_vec.size(); i++) {
    axObject * oI = m_vec[i];
    if (oI->isKeyEqual(o)) {
      found = true;
      ret = oI;
    }
  }

  unlock();

  return (ret);
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axVector::remove(axObject * o)
{
  axObject * ret = NULL;

  lock();

  vectorType_t::iterator item = m_vec.begin();
  vectorType_t::iterator end = m_vec.end();
  for(bool found=false; !found && item != end; item++) {
    axObject * oI = *item;
    if (oI->isKeyEqual(o)) {
      found = true;
      ret = oI;
      m_vec.erase(item);
    }
  }

  unlock();

  return (ret);
}


#if 0

//********************************************************************
// method: add
//********************************************************************
void
axVector::add(axObject * o, slist<axObject *>::iterator & inIter)
{
  lock();

  if (m_vec.empty()) {
    m_vec.push_front(o);
    inIter = m_vec.begin();
  } else {
    inIter = m_vec.previous(m_vec.end());
    inIter = m_vec.insert_after(inIter, o);
  }

  unlock();
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axVector::remove(vectorType_t::iterator & iter)
{
  axObject * ret = NULL;

  lock();

  if (iter != m_vec.end()) {
    m_vec.erase(iter);
    ret = *iter;
  }

  unlock();

  return (ret);
}


//********************************************************************
// method: getObject
//********************************************************************
axObject *
axVector::getObject(int elem)
{
  axObject * ret = NULL;

  if (elem < 0 || elem >= size()) {
    return (ret);
  }

  lock();

  vectorType_t::iterator start = begin();
  vectorType_t::iterator finish = end();

  int i=0;

  for (; i != elem && start != finish; i++, start++) {
  }

  if (i==elem) {
    ret = *start;
  }

  unlock();

  return (ret);
}

#endif


#if 0
//********************************************************************
// method: lock
//********************************************************************
void
axVector::lock(void)
{
}


//********************************************************************
// method: unlock
//********************************************************************
void
axVector::unlock(void)
{
}
#endif


//********************************************************************
// method: begin
//********************************************************************
axVector::vectorType_t::iterator
axVector::begin(void)
{
  axVector::vectorType_t::iterator iter = m_vec.begin();

  return (iter);
}


//********************************************************************
// method: end
//********************************************************************
axVector::vectorType_t::iterator
axVector::end(void)
{
  axVector::vectorType_t::iterator iter = m_vec.end();

  return (iter);
}


//********************************************************************
// method: createIterator
//********************************************************************
axAbstractIterator *
axVector::createIterator(void)
{
  axAbstractIterator * ret = NULL;

  return (ret);
}


//********************************************************************
// method: freeIterator
//********************************************************************
void
axVector::freeIterator(axAbstractIterator * iter)
{
  delete iter;
}

