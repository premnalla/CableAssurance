
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axControllerProxy_hpp_
#define _axControllerProxy_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/**
 * This class is used to ...
 *
 * file/class: axControllerProxy.hpp
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

class axControllerProxy : public axObject
{
public:

  /// default constructor
  axControllerProxy();

  /// destructor
  virtual ~axControllerProxy();

  /**
   * Used to determine if application has exited.
   *
   * @return
   *   \begin{itemize}
   *     \item true if application has exit state set
   *     \item false if application has exit state unset
   *   \end{itemize}
   */
  virtual bool isExit(void);

protected:

private:

  // copy not allowed
  axControllerProxy(const axControllerProxy &);

};

#endif // _axControllerProxy_hpp_
