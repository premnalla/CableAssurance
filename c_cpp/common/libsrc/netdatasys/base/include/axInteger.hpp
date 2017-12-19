
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axInteger_hpp_
#define _axInteger_hpp_

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
 * file/class: axInteger.hpp
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

class axInteger : public axObject
{
public:

  /// data constructor
  axInteger(AX_INT32);

  /// destructor
  virtual ~axInteger();

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
  const AX_INT32 m_data;

  /// default constructor not allowed
  axInteger();

};

#endif // _axInteger_hpp_
