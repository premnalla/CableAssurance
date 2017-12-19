
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axInt64_hpp_
#define _axInt64_hpp_

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
 * file/class: axInt64.hpp
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

class axInt64 : public axObject
{
public:

  /// data constructor
  axInt64(AX_INT64);

  /// destructor
  virtual ~axInt64();

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

  /**
   * Describe here ...
   *
   * @return 
   * @see 
   */
  AX_INT64 getValue(void);

protected:

private:

  ///
  const AX_INT64 m_data;

  /// default constructor not allowed
  axInt64();

};

#endif // _axInt64_hpp_
