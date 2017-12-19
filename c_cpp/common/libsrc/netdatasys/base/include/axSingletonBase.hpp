
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axSingletonBase_hpp_
#define _axSingletonBase_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/**
 * This class is used to ...
 *
 *
 * file/class: axSingletonBase.hpp
 *
 * Design Document:
 *
 * Description:
 *
 * System:
 *
 * Sub-system:
 *
 * History:
 *
 * @version 1.0
 * @author Prem Nallasivampillai
 * @see
 *
 */

class axSingletonBase : public axObject
{
public:

protected:

  /// default constructor
  axSingletonBase();

  /// destructor
  virtual ~axSingletonBase();

private:

  /// copy not allowed
  axSingletonBase(const axSingletonBase &);

};

#endif // _axSingletonBase_hpp_
