//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axAvlTree.hpp"
#include "axAvlIterator.hpp"
#include "axListBase.hpp"

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
axAvlTree::axAvlTree()
{
  m_avlTree = avl_create(axObject::CompareFunction, NULL, NULL);
}


//********************************************************************
// destructor:
//********************************************************************
axAvlTree::~axAvlTree()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAvlTree::axAvlTree(avl_comparison_func * cmpFunc)
{
  m_avlTree = avl_create(cmpFunc, NULL, NULL);
}


//********************************************************************
// method:
//********************************************************************
bool 
axAvlTree::isEmpty(void)
{
  return ((size()==0 ? true : false));
}


//********************************************************************
// method:
//********************************************************************
// void
axObject *
axAvlTree::add(axObject * o)
{
  // printf("axAvlTree::add()\n");

  // printf("axAvlTree::add(): o = 0x%x\n", o);

  axObject * ret = (axObject *) avl_insert(m_avlTree, (void *) o);

  // printf("axAvlTree::add(): ret = 0x%x\n", ret);

  if (!ret) {
    /* Successful or Memory allocation Error */
    ret = o;
  } else {
    /* 
     * Item already exists in table and therefore did not insert.
     * 'ret' points to it
     */
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axAvlTree::find(axObject * o)
{
  axObject * ret;

  ret = (axObject *) avl_find(m_avlTree, (void *) o);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axAvlTree::remove(axObject * o)
{
  axObject * ret;

  ret = (axObject *) avl_delete(m_avlTree, (void *) o);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
size_t
axAvlTree::size(void)
{
  return (avl_count(m_avlTree));
}


//********************************************************************
// method:
//********************************************************************
void
axAvlTree::clear(void)
{
  axListBase l;
  axAbstractIterator * iter = createIterator();
  axObject * o = iter->getFirst();
  while (o) {
    l.add(o);
    o = iter->getNext();
  }
  delete iter;

  while((o = l.remove())) {
    o = remove(o);
  }
  
}


//********************************************************************
// method:
//********************************************************************
void
axAvlTree::clearAndFreeEntries(void)
{
  axListBase l;
  axAbstractIterator * iter = createIterator();
  axObject * o = iter->getFirst();
  while (o) {
    delete o;
    o = iter->getNext();
  }
  delete iter;

  while((o = l.remove())) {
    o = remove(o);

    // the actual free
    delete o;
  }
}


//********************************************************************
// method: createIterator
//********************************************************************
axAbstractIterator *
axAvlTree::createIterator(void)
{
  axAbstractIterator * ret;

  ret = new axAvlIterator(m_avlTree);

  return (ret);
}


//********************************************************************
// method: freeIterator
//********************************************************************
void
axAvlTree::freeIterator(axAbstractIterator * iter)
{
  delete iter;
}

