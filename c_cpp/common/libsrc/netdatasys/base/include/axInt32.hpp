
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axInt32_hpp_
#define _axInt32_hpp_

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
 * file/class: axInt32.hpp
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

class axInt32 : public axObject
{
public:

  /// data constructor
  axInt32(AX_INT32);

  /// destructor
  virtual ~axInt32();

  /**
   * Describe here ...
   *
   * @return string
   * @see string
   */
  virtual auto_ptr<string> toString(void);

  /**
   * Describe here ...
   *
   * @return 
   * @see 
   */
  virtual AX_INT64  hashCode(void);
  virtual AX_INT32  hashInt32(void);
  // virtual AX_UINT32  hashUInt32(void);

  /**
   * Describe here ...
   *
   * @return 
   * @see 
   */
  AX_INT32 getValue(void);

protected:

private:

  ///
  const AX_INT32 m_data;

  /// default constructor not allowed
  axInt32();

};

#endif // _axInt32_hpp_
