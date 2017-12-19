
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axUnsignedInteger_hpp_
#define _axUnsignedInteger_hpp_

//********************************************************************
// include files
//********************************************************************
#include <string>
#include <memory>
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
 * file/class: axUnsignedInteger.hpp
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

class axUnsignedInteger : public axObject
{
public:

  /// data constructor
  axUnsignedInteger(AX_UINT32);

  /// destructor
  virtual ~axUnsignedInteger();

  /**
   * Describe here ...
   *
   * @return string
   * @see string
   */
  virtual auto_ptr<string> toString(void);

  ///
  virtual AX_INT32 hashInt32(void);
  virtual AX_UINT32 hashUInt32(void);

protected:

private:

  ///
  const AX_UINT32 m_data;

  /// default constructor not allowed
  axUnsignedInteger();

};

#endif // _axUnsignedInteger_hpp_
