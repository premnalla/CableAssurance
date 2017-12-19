
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axMultiSet_hpp_
#define _axMultiSet_hpp_

//********************************************************************
// include files
//********************************************************************
#include <set>
#include <memory>
#include "axAbstractCollection.hpp"
#include "axObject.hpp"

using namespace __gnu_cxx;

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
// struct strictWeakOrderingCmp;

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axMultiSet.hpp
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

class axMultiSet : public axObject, public axAbstractCollection
{
public:

  typedef multiset<axObject *> multisetType_t;
  // typedef multiset<axObject *, strictWeakOrderingCmp> multisetType_t;

  /// default constructor
  axMultiSet();

  /// destructor
  virtual ~axMultiSet();

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

  ///
  virtual axObject * find(axObject *);

  ///
  virtual axObject * remove(axObject *);

  ///
  virtual axObject * remove(void);

  ///
  virtual size_t size(void);

  ///
  virtual void clear(void);

  ///
  virtual void clearAndFreeEntries(void);

  ///
  virtual multisetType_t::iterator begin(void);

  ///
  virtual multisetType_t::iterator end(void);

  ///
  virtual axAbstractIterator * createIterator(void);
  virtual void freeIterator(axAbstractIterator *);

protected:


private:

  ///
  multisetType_t  m_multiset;

  ///
  axMultiSet(const axMultiSet &);

};

#if 0
struct strictWeakOrderingCmp 
{
  bool operator() (const axObject * o1, const axObject * o2)
  {
    axObject * a = const_cast<axObject *> (o1);
    axObject * b = const_cast<axObject *> (o2);
    return ((a->hashCode() < b->hashCode() ? true : false));
  }
};
#endif


#endif // _axMultiSet_hpp_
