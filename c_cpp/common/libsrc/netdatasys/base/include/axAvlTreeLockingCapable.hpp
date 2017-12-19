//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axAvlTreeLockingCapable_hpp_
#define _axAvlTreeLockingCapable_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAvlTree.hpp"

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
 * file/class: axAvlTreeLockingCapable.hpp
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

class axAvlTreeLockingCapable : public axAvlTree
{
public:

  /// default constructor
  axAvlTreeLockingCapable();

  /// destructor
  virtual ~axAvlTreeLockingCapable();

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

  virtual bool isEmpty(void);

  ///
  virtual axObject * add(axObject *);
  // virtual void add(axObject *);

  ///
  virtual axObject * find(axObject *);

  ///
  virtual axObject * remove(axObject *);

  ///
  virtual size_t size(void);

  ///
  virtual void clear(void);

  ///
  virtual void clearAndFreeEntries(void);

protected:


private:

  /// copy not allowed
  axAvlTreeLockingCapable(const axAvlTreeLockingCapable &);

};

#endif // _axAvlTreeLockingCapable_hpp_
