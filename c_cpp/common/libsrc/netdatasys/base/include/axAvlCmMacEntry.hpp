
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axAvlCmMacEntry_hpp_
#define _axAvlCmMacEntry_hpp_

//********************************************************************
// include files
//********************************************************************
// extern "C" {
// #include "axDataTypes.h"
// #include "axNetworkDataTypes.h"
// #include "avl.h"
// }
#include "axAll.h"
#include "avl.h"
#include "axAbstractAvlTreeEntry.hpp"

//********************************************************************
// definitions/macros
//********************************************************************
// #define MAC_ADDRESS_SIZE 18

//********************************************************************
// forward declerations
//********************************************************************

/**
 * Description ...
 *
 *
 * file/class: axAvlCmMacEntry.hpp
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

class axAvlCmMacEntry : public axAbstractAvlTreeEntry
{
public:

  /// data constructor
  axAvlCmMacEntry(AX_INT8 *);

  /// destructor
  virtual ~axAvlCmMacEntry();

  /**
   * Describe here ...
   *
   * @return string
   * @see string
   */

  ///
  static int CompareFunction(const void *, const void *, void *);

  ///
  const AX_INT8 * getCmMac(void) const;

protected:

  /// default constructor
  axAvlCmMacEntry();

private:

  ///
  axAvlCmMacEntry(const axAvlCmMacEntry &);

  ///
  // char m_cmMac[MAX_MAC_ADDRESS_CHARS];
  AX_MAC_ADDRESS m_cmMac;
};

#endif // _axAvlCmMacEntry_hpp_
