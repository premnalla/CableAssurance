//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include "axString.hpp"

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
axString::axString() :
  m_data("")
{
}

//********************************************************************
// destructor:
//********************************************************************
axString::~axString()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axString::axString(const char * val) :
  m_data(val)
{
}


//********************************************************************
// method: toString
//********************************************************************
auto_ptr<string>
axString::toString(void) 
{
  auto_ptr<string> str(new string(m_data));
  return (str);
}


//********************************************************************
// method : hashString
//********************************************************************
auto_ptr<string>
axString::hashString(void)
{
  auto_ptr<string> str(new string(m_data));
  return (str);
}


//********************************************************************
// method: isEqual
//********************************************************************
bool
axString::isEqual(axObject * o)
{
  bool ret = isKeyEqual(o);

  return (ret);
}


//********************************************************************
// method: isKeyEqual
//********************************************************************
bool
axString::isKeyEqual(axObject * o)
{
  bool ret = false;

  auto_ptr<string> str1 = hashString();
  auto_ptr<string> str2 = o->hashString();

  if (!strcmp(str1->c_str(), str2->c_str())) {
    ret = true;
  }

  return (ret);
}

