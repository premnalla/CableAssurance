
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractTopoLookup.hpp"

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
axDbAbstractTopoLookup::axDbAbstractTopoLookup() :
  m_id(0),
  m_resId(0),
  m_strId(""),
  m_topoContainerId(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbAbstractTopoLookup::~axDbAbstractTopoLookup()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbAbstractTopoLookup::axDbAbstractTopoLookup(AX_UINT16 containerId, 
                                                   DB_RESID_t resId) :
  m_id(0),
  m_resId(resId),
  m_strId(""),
  m_topoContainerId(containerId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbAbstractTopoLookup::axDbAbstractTopoLookup(const AX_INT8 * strId):
  m_id(0),
  m_resId(0),
  m_strId(strId),
  m_topoContainerId(0)
{
}

