
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axAltString_hpp_
#define _axAltString_hpp_

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
 * file/class: axAltString.hpp
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

class axAltString : public axObject
{
public:

  /// data constructor
  axAltString(const char *);

  /// destructor
  virtual ~axAltString();

  /**
   * Describe here ...
   *
   * @return string
   * @see string
   */
  virtual auto_ptr<string> toString(void);

  ///
  virtual auto_ptr<string> hashString(void);

  /// equality check
  virtual bool isEqual(axObject *);
  virtual bool isKeyEqual(axObject *);

protected:

private:

  ///
  auto_ptr<string> m_data;

  /// default constructor not allowed
  axAltString();

};

#endif // _axAltString_hpp_
