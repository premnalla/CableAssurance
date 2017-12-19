
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractAvlTreeEntry_hpp_
#define _axAbstractAvlTreeEntry_hpp_

//********************************************************************
// include files
//********************************************************************
// extern "C" {
// #include "avl.h"
// }
#include "avl.h"
#include "axObject.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/**
 * Description ...
 *
 *
 * file/class: axAbstractAvlTreeEntry.hpp
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

class axAbstractAvlTreeEntry : public axObject
{
public:

  /// destructor
  virtual ~axAbstractAvlTreeEntry();

  /**
   * Describe here ...
   *
   * @return string
   * @see string
   */

  // int (* compare_function) (void * a, void * b, void * c);
  // virtual avl_comparison_func_ptr getCompareFunction(void) = 0;

protected:

  /// default constructor not allowed
  axAbstractAvlTreeEntry();

private:

  ///
  axAbstractAvlTreeEntry(const axAbstractAvlTreeEntry &);

};

#endif // _axAbstractAvlTreeEntry_hpp_
