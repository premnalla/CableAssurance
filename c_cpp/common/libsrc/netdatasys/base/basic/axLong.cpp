//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include "axLong.hpp"

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
axLong::axLong() :
  m_data(0L)
{
}

//********************************************************************
// destructor:
//********************************************************************
axLong::~axLong()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axLong::axLong(long val) : 
  m_data(val)
{
}


//********************************************************************
// method: toString
//********************************************************************
auto_ptr<string>
axLong::toString(void) 
{
  char tmpS[64];
  sprintf(tmpS, "%l", m_data);
  auto_ptr<string> ret(new string(tmpS));

  return (ret);
}


//********************************************************************
// method : hashCode
//********************************************************************
AX_INT32
axLong::hashInt32(void)
{
  return ((AX_INT32)m_data);
}


//********************************************************************
// method : hashInt
//********************************************************************
AX_UINT32
axLong::hashUInt32(void)
{
  return ((AX_UINT32) m_data);
}


