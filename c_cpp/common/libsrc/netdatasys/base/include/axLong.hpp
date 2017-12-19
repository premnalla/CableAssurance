
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axLong_hpp_
#define _axLong_hpp_

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
 * file/class: axLong.hpp
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

class axLong : public axObject
{
public:

  /// data constructor
  axLong(AX_LONG);

  /// destructor
  virtual ~axLong();

  /**
   * Describe here ...
   *
   * @return string
   * @see string
   */
  virtual auto_ptr<string>  toString(void);

  ///
  virtual AX_INT32 hashInt32(void);
  virtual AX_UINT32 hashUInt32(void);

protected:

private:

  ///
  const long m_data;

  /// default constructor not allowed
  axLong();

};

#endif // _axLong_hpp_
