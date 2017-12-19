//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axAvlTree_hpp_
#define _axAvlTree_hpp_

//********************************************************************
// include files
//********************************************************************
// extern "C" {
// #include "avl.h"
// }
#include "avl.h"
#include "axAbstractAvlTree.hpp"
#include "axAbstractAvlTreeEntry.hpp"

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
 * file/class: axAvlTree.hpp
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

class axAvlTree : public axAbstractAvlTree
{
public:

  /// default constructor
  axAvlTree();

  /// destructor
  virtual ~axAvlTree();

  /// data constructor
  axAvlTree(avl_comparison_func *);

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

  virtual axAbstractIterator * createIterator(void);
  virtual void freeIterator(axAbstractIterator *);

protected:


private:

  /// copy not allowed
  axAvlTree(const axAvlTree &);

  ///
  struct avl_table * m_avlTree;

  size_t             m_size;
};

#endif // _axAvlTree_hpp_
