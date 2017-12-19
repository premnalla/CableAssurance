
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axStrHashMap_hpp_
#define _axStrHashMap_hpp_

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
 * file/class: axStrHashMap.hpp
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

class axStrHashMap : public axAbstractCollection
{
public:

  /// typedefs
  // typedef hash_map<const char *, axObject *, hash<const char *>, axObject> strHashMap_t;
  typedef hash_map<const char *, axObject *, hash<const char *>, axObjectEqualsCompare> strHashMap_t;

  /// default constructor
  axStrHashMap();

  /// destructor
  virtual ~axStrHashMap();

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

  ///
  strHashMap_t  m_hashMap;

  ///
  axStrHashMap(const axStrHashMap &);

};

#endif // _axStrHashMap_hpp_
