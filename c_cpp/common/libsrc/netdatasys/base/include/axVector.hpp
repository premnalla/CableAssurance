
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axVector_hpp_
#define _axVector_hpp_

//********************************************************************
// include files
//********************************************************************
#include <vector>
#include <memory>
#include "axAbstractCollection.hpp"
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
 * 
 * file/class: axVector.hpp
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

class axVector : public axObject, public axAbstractCollection
{
public:

  typedef vector<axObject *> vectorType_t;

  /// default constructor
  axVector();

  /// destructor
  virtual ~axVector();

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
  virtual axObject * remove(void);

  ///
  virtual size_t size(void);

  ///
  virtual axObject * operator[] (int);

  ///
  virtual void clear(void);

  ///
  virtual void clearAndFreeEntries(void);

  ///
  virtual axObject * find(axObject *);

  ///
  virtual axObject * remove(axObject *);

  /// MT-Unsafe !!!
  virtual vectorType_t::iterator begin(void);

  /// MT-Unsafe !!!
  virtual vectorType_t::iterator end(void);

  virtual axAbstractIterator * createIterator(void);
  virtual void freeIterator(axAbstractIterator *);

protected:

private:

  ///
  vectorType_t  m_vec;

  ///
  axVector(const axVector &);

};

#endif // _axVector_hpp_
