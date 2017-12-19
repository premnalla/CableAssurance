//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include "axInt64.hpp"

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
axInt64::axInt64() :
  m_data((AX_INT64)0)
{
}

//********************************************************************
// destructor:
//********************************************************************
axInt64::~axInt64()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axInt64::axInt64(AX_INT64 val) : 
  m_data(val)
{
}


//********************************************************************
// method: toString
//********************************************************************
auto_ptr<string>
axInt64::toString(void) 
{
  char tmpS[64];
  sprintf(tmpS, "%lld", m_data);
  auto_ptr<string> ret(new string(tmpS));

  return (ret);
}


//********************************************************************
// method : hashCode
//********************************************************************
AX_INT64
axInt64::hashCode(void)
{
  return (m_data);
}


//********************************************************************
// method : getValue
//********************************************************************
AX_INT64
axInt64::getValue(void)
{
  return (m_data);
}


