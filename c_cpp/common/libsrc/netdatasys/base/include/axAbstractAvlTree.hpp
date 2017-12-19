//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractAvlTree_hpp_
#define _axAbstractAvlTree_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCollection.hpp"

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
 * file/class: axAbstractAvlTree.hpp
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

class axAbstractAvlTree : public axObject, public axAbstractCollection
{
public:

  /// destructor
  virtual ~axAbstractAvlTree();

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
   // axAbstractIterator * createIterator(void);

protected:

  /// default constructor
  axAbstractAvlTree();

private:

  axAbstractAvlTree(const axAbstractAvlTree &);

};

#endif // _axAbstractAvlTree_hpp_
