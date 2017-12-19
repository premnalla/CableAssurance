
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axStrNVPair_hpp_
#define _axStrNVPair_hpp_

//********************************************************************
// include files
//********************************************************************
#include <string>
#include "axString.hpp"

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
 * file/class: axStrNVPair.hpp
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

class axStrNVPair : public axString
{
public:

  /// data constructor
  axStrNVPair(const char *, const char *);

  /// destructor
  virtual ~axStrNVPair();

  /// 
  auto_ptr<string> getName(void);

  /// 
  auto_ptr<string> getValue(void);

protected:

private:

  ///
  const string m_value;

  /// default constructor not allowed
  axStrNVPair();

};

#endif // _axStrNVPair_hpp_
