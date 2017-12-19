
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axUInt32_hpp_
#define _axUInt32_hpp_

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
 * file/class: axUInt32.hpp
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

class axUInt32 : public axObject
{
public:

  /// data constructor
  axUInt32(AX_UINT32);

  /// destructor
  virtual ~axUInt32();

  /**
   * Describe here ...
   *
   * @return string
   * @see string
   */
  virtual auto_ptr<string> toString(void);

  ///
  virtual AX_INT64  hashCode(void);
  virtual AX_UINT32 hashUInt32(void);
  // virtual AX_INT32  hashInt32(void);

  AX_UINT32 getValue(void);

protected:

private:

  ///
  const AX_UINT32 m_data;

  /// default constructor not allowed
  axUInt32();

};

#endif // _axUInt32_hpp_
