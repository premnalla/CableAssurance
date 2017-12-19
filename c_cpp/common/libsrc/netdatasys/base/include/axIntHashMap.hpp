
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axIntHashMap_hpp_
#define _axIntHashMap_hpp_

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
 * file/class: axIntHashMap.hpp
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

class axIntHashMap : public axObject, public axAbstractCollection
{
public:

  /// typedefs
  // typedef hash_map<int, axObject *, hash<int>, axObject> intHashMap_t;
  typedef hash_map<AX_INT32, axObject *, hash<AX_INT32>, axObjectEqualsCompare> intHashMap_t;

  /// default constructor
  axIntHashMap();

  /// destructor
  virtual ~axIntHashMap();

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

  ///
  size_t        m_size;

  intHashMap_t  m_hashMap;

  ///
  axIntHashMap(const axIntHashMap &);

};

#endif // _axIntHashMap_hpp_
