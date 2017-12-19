
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axStrHashMultiMap_hpp_
#define _axStrHashMultiMap_hpp_

//********************************************************************
// include files
//********************************************************************
#include <ext/hash_map>
#include <memory>
#include "axObject.hpp"
#include "axListBase.hpp"
#include "axAbstractCollection.hpp"
#include "axAbstractIterator.hpp"

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
 * file/class: axStrHashMultiMap.hpp
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

class axStrHashMultiMap : public axObject, public axAbstractCollection
{
public:

  /// 
  typedef hash_multimap<const char *, axObject *, hash<const char *>, axObjectEqualsCompare> strHashMultiMap_t;

  /// default constructor
  axStrHashMultiMap();

  /// destructor
  virtual ~axStrHashMultiMap();

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

  ///
  virtual auto_ptr<axListBase> getElements(axObject *);

  ///
  virtual auto_ptr<axListBase> removeElements(axObject *);

  ///
  virtual void printElements(axObject *);

  virtual axAbstractIterator * createIterator(void);
  virtual void freeIterator(axAbstractIterator *);

protected:


private:

  ///
  size_t               m_size;

  ///
  strHashMultiMap_t    m_hashMap;

  ///
  axStrHashMultiMap(const axStrHashMultiMap &);

};

#endif // _axStrHashMultiMap_hpp_
