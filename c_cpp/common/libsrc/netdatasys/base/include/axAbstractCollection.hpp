
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractCollection_hpp_
#define _axAbstractCollection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"
#include "axAbstractIterator.hpp"

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
 * file/class: axAbstractCollection.hpp
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

class axAbstractCollection
{
public:

  /// default constructor
  axAbstractCollection();

  /// destructor
  virtual ~axAbstractCollection();

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
  virtual bool isEmpty(void)=0;

  ///
  virtual axObject * add(axObject *)=0;
  // virtual void add(axObject *)=0;

  ///
  virtual axObject * find(axObject *)=0;

  ///
  virtual axObject * remove(axObject *)=0;

  ///
  virtual axAbstractIterator * createIterator(void)=0;
  virtual void freeIterator(axAbstractIterator *)=0;

  ///
  virtual size_t size(void)=0;

  ///
  virtual void clear(void)=0;

  ///
  virtual void clearAndFreeEntries(void)=0;

protected:

  ///
  virtual void lock();

  ///
  virtual void unlock();

private:

  axAbstractCollection(const axAbstractCollection &);

};

#endif // _axAbstractCollection_hpp_
