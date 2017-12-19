//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axAvlTreeExtLock_hpp_
#define _axAvlTreeExtLock_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAvlTree.hpp"
#include "axAbstractLock.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/**
 * This class is used as a Collection Interface.
 *
 *
 * file/class: axAvlTreeExtLock.hpp
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

class axAvlTreeExtLock : public axAvlTree
{
public:

  /// data constructor
  axAvlTreeExtLock(axAbstractLock *);

  /// default constructor 
  axAvlTreeExtLock();

  /// destructor
  virtual ~axAvlTreeExtLock();

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
  virtual axAbstractLock * getLock(void);

protected:


private:

  /// copy not allowed
  axAvlTreeExtLock(const axAvlTreeExtLock &);

  axAbstractLock * m_lock;
};

#endif // _axAvlTreeExtLock_hpp_
