
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axLongHashMap_hpp_
#define _axLongHashMap_hpp_

//********************************************************************
// include files
//********************************************************************
#include <ext/hash_map>
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

/**
 * This class is used to ...
 *
 *
 * file/class: axLongHashMap.hpp
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

class axLongHashMap : public axObject, public axAbstractCollection
{
public:

  /// typedefs
  // typedef hash_map<long, axObject *, hash<long>, axObject> longHashMap_t;
  typedef hash_map<AX_LONG, axObject *, hash<AX_LONG>, axObjectEqualsCompare> longHashMap_t;

  /// default constructor
  axLongHashMap();

  /// destructor
  virtual ~axLongHashMap();

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
  virtual void add(axObject *);

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

  /// MT-Unsafe !!!
  virtual longHashMap_t::iterator begin(void);

  /// MT-Unsafe !!!
  virtual longHashMap_t::iterator end(void);

  virtual axAbstractIterator * createIterator(void);
  virtual void freeIterator(axAbstractIterator *);

protected:

  ///
  // virtual void lock();

  ///
  // virtual void unlock();

private:

  ///
  size_t         m_size;

  ///
  longHashMap_t  m_hashMap;

  ///
  axLongHashMap(const axLongHashMap &);

};

#endif // _axLongHashMap_hpp_
