
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axListExtLock_hpp_
#define _axListExtLock_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axListBase.hpp"
#include "axAbstractLock.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axListExtLock.hpp
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

class axListExtLock : public axListBase
{
public:

  /// data constructor
  axListExtLock(axAbstractLock *);

  /// destructor
  virtual ~axListExtLock();

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

  ///
  axAbstractLock * getLock(void);

protected:

private:

  /// default constructor
  axListExtLock();

  ///
  axListExtLock(const axListExtLock &);

  axAbstractLock * m_lock;
};

#endif // _axListExtLock_hpp_
