
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <assert.h>
#include "axInternalObjList.hpp"
#include "axAbstractInternalObject.hpp"

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
axInternalObjList::axInternalObjList()
{
}


//********************************************************************
// destructor:
//********************************************************************
axInternalObjList::~axInternalObjList()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axInternalObjList::axInternalObjList()
// {
// }


//********************************************************************
// method:
//********************************************************************
axObject *
axInternalObjList::find(axObject * in)
{
  axObject * ret = NULL;

  axAbstractInternalObject * inO = 
    dynamic_cast<axAbstractInternalObject *> (in);
  assert(inO != NULL);

  listType_t::iterator item = m_list.begin();
  listType_t::iterator end = m_list.end();
  for(bool found=false; !found && item != end; item++) {
    axAbstractInternalObject * oI = (axAbstractInternalObject *) *item;
    if (oI->getResId()==inO->getResId()) {
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
axInternalObjList::remove(axObject * in)
{
  axObject * ret = NULL;

  axAbstractInternalObject * inO =
    dynamic_cast<axAbstractInternalObject *> (in);
  assert(inO != NULL);

  listType_t::iterator item = m_list.begin();
  listType_t::iterator end = m_list.end();
  for(bool found=false; !found && item != end; item++) {
    axAbstractInternalObject * oI = (axAbstractInternalObject *) *item;
    if (oI->getResId()==inO->getResId()) {
      found = true;
      ret = oI;
      m_list.erase(item);
      --m_size;
    }
  }

  return (ret);
}

