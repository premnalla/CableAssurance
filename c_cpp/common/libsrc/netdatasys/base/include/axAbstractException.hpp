
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractException_hpp_
#define _axAbstractException_hpp_

//********************************************************************
// include files
//********************************************************************
#include <string>
#include "axObject.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/**
 * This class is used as the base for all 'Exception' classes.
 *
 *
 * file/class: axAbstractException.hpp
 *
 * Design Document:
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

class axAbstractException : public axObject
{
public:

  /// default constructor
  axAbstractException();

  /// destructor
  virtual ~axAbstractException();

  /**
   * Describe here ...
   *
   * @param p1 in parameter
   * @param p2 in-out parameter
   * @param p3 out parameter
   * @return
   *   \begin{itemize}
   *     \item AX_SUCCESS successfully executed
   *     \item AX_FAILED  unsuccessfully executed
   *   \end{itemize}
   * @see
   */
   const char * getErrorMsg(void) const;

protected:

  string    m_errorMsg;

private:

  // axAbstractException(const axAbstractException &);

};

#endif // _axAbstractException_hpp_
