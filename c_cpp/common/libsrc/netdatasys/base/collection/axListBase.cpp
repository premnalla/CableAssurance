//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include "axListBase.hpp"
#include "axListIterator.hpp"

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
axListBase::axListBase() :
  m_size(0), m_isFreeEntries(false)
{
}

//********************************************************************
// destructor:
//********************************************************************
axListBase::~axListBase()
{
  if (m_isFreeEntries) {
    this->clearAndFreeEntries();
  } else {
    this->clear();
  }
}

//********************************************************************
// data constructor:
//********************************************************************
axListBase::axListBase(bool freeEntries) :
  m_size(0), m_isFreeEntries(freeEntries)
{
}

//********************************************************************
// method: isEmpty
//********************************************************************
bool
axListBase::isEmpty(void)
{
  return (m_list.empty());
}


//********************************************************************
// method: add
//********************************************************************
// void
axObject *
axListBase::add(axObject * o)
{
  if (m_list.empty()) {
    m_list.push_front(o);
  } else {
    listType_t::iterator lastItem = 
      m_list.previous(m_list.end());
    lastItem = m_list.insert_after(lastItem, o);
  }

  ++m_size;

  return (o);
}


//********************************************************************
// method: add
//********************************************************************
// void
axObject *
axListBase::add(axObject * o, listType_t::iterator & inIter)
{

  if (m_list.empty()) {
    /* SHOULD NOT HAPPEN ... */
    m_list.push_front(o);
    // inIter = m_list.begin();
  } else {
    // inIter = m_list.previous(m_list.end());
    // inIter = m_list.insert_after(inIter, o);
    // listType_t::iterator iter = m_list.insert_after(inIter, o);
    listType_t::iterator iter = m_list.insert(inIter, o);
  }

  ++m_size;

  return (o);
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axListBase::getFirst(void)
{
  axObject * ret;

  if (!m_list.empty()) {
    ret = m_list.front();
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axListBase::getLast(void)
{
  axObject * ret;

  if (!m_list.empty()) {
    listType_t::iterator lastItem =
      m_list.previous(m_list.end());
    ret = *lastItem;
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method: find
//********************************************************************
axObject *
axListBase::find(axObject * o)
{
  axObject * ret = NULL;

  listType_t::iterator item = m_list.begin();
  listType_t::iterator end = m_list.end();
  for(bool found=false; !found && item != end; item++) {
    axObject * oI = *item;
    if (oI->isKeyEqual(o)) {
      found = true;
      ret = oI;
    }
  }

  return (ret);
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axListBase::remove(axObject * o)
{
  axObject * ret = NULL;

  listType_t::iterator item = m_list.begin();
  listType_t::iterator end = m_list.end();
  for(bool found=false; !found && item != end; item++) {
    axObject * oI = *item;
    if (oI->isKeyEqual(o)) {
      found = true;
      ret = oI;
      m_list.erase(item);
      --m_size;
    }
  }

  return (ret);
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axListBase::remove(listType_t::iterator & iter)
{
  axObject * ret;

  if (iter != m_list.end()) {
    m_list.erase(iter);
    --m_size;
    ret = *iter;
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method: remove
//********************************************************************
axObject *
axListBase::remove(void)
{
  axObject * ret;

  if (!m_list.empty()) {
    ret = m_list.front();
    m_list.pop_front();
    --m_size;
  } else {
    ret = NULL;
  }

  return (ret);
}


//********************************************************************
// method: clear
//********************************************************************
void
axListBase::clear(void)
{
  m_list.clear();
  m_size = 0;
}


//********************************************************************
// method: clearAndFreeEntries
//********************************************************************
void
axListBase::clearAndFreeEntries(void)
{
  listType_t::iterator item = m_list.begin();
  listType_t::iterator end = m_list.end();
  for(; item != end; item++) {
    axObject * oI = *item;
    delete oI;
  }

  m_list.clear();

  m_size = 0;
}


//********************************************************************
// method: size
//********************************************************************
size_t
axListBase::size(void)
{
  return (m_size);
}


//********************************************************************
// method: getObject
//********************************************************************
axObject *
axListBase::getObject(int elem)
{
  axObject * ret = NULL;

  if (elem < 0 || elem >= size()) {
    return (ret);
  }

  listType_t::iterator start = begin();
  listType_t::iterator finish = end();

  int i=0;

  for (; i != elem && start != finish; i++, start++) {
  }

  if (i==elem) {
    ret = *start;
  }

  return (ret);
}


//********************************************************************
// method: begin
//********************************************************************
axListBase::listType_t::iterator
axListBase::begin(void)
{
  return (m_list.begin());
}


//********************************************************************
// method: end
//********************************************************************
axListBase::listType_t::iterator
axListBase::end(void)
{
  return (m_list.end());
}


//********************************************************************
// method: end
//********************************************************************
axListBase::listType_t::iterator
axListBase::previous(axListBase::listType_t::iterator & pos)
{
  return (m_list.previous(pos));
}


//********************************************************************
// method: createIterator
//********************************************************************
axAbstractIterator *
axListBase::createIterator(void)
{
  return (new axListIterator(this));
}


//********************************************************************
// method: freeIterator
//********************************************************************
void
axListBase::freeIterator(axAbstractIterator * iter)
{
  delete iter;
}


//********************************************************************
// method: cloneList_u
//********************************************************************
axListBase *
axListBase::cloneList_u(void)
{
  axListBase * ret = new axListBase();

  listType_t::iterator item = m_list.begin();
  listType_t::iterator end = m_list.end();
  for(; item != end; item++) {
    axObject * oI = *item;
    // delete oI;
    ret->add(oI);
  }

  return (ret);
}


//********************************************************************
// method: cloneList
//********************************************************************
axListBase *
axListBase::cloneList(void)
{
  return (this->cloneList_u());
}


//********************************************************************
// method: 
//********************************************************************
void
axListBase::setIsFreeEntries(bool in)
{
  m_isFreeEntries = in;
}


//********************************************************************
// method:
//********************************************************************
bool
axListBase::getIsFreeEntries(void)
{
  return (m_isFreeEntries);
}


