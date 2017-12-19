
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axListBase_hpp_
#define _axListBase_hpp_

//********************************************************************
// include files
//********************************************************************
#include <ext/slist>
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
 * file/class: axListBase.hpp
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

class axListBase : public axObject, public axAbstractCollection
{
public:

  typedef slist<axObject *> listType_t;

  /// data constructor
  axListBase(bool);

  /// default constructor
  axListBase();

  /// destructor
  virtual ~axListBase();

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
  virtual axObject * add(axObject *, listType_t::iterator &);

  ///
  virtual axObject * getFirst(void);

  ///
  virtual axObject * getLast(void);

  /**
   * Returns a list containing a copy of all the elems in this List
   * NOTE: this only does a SHALLOW COPY of the elements in the List.
   */
  virtual axListBase   * cloneList(void);

  ///
  virtual axObject * find(axObject *);

  ///
  virtual axObject * remove(axObject *);

  ///
  virtual axObject * remove(listType_t::iterator &);

  ///
  virtual axObject * remove(void);

  ///
  virtual size_t size(void);

  ///
  virtual axObject * getObject(int);

  ///
  virtual void clear(void);

  ///
  virtual void clearAndFreeEntries(void);

  ///
  virtual listType_t::iterator begin(void);

  ///
  virtual listType_t::iterator end(void);

  ///
  virtual listType_t::iterator previous(listType_t::iterator &);

  virtual axAbstractIterator * createIterator(void);
  virtual void freeIterator(axAbstractIterator *);

protected:

  /**
   * Returns a list containing a copy of all the elems in this List
   * NOTE: this only does a SHALLOW COPY of the elements in the List.
   * Also NOTE that this does not provide mutual exlusivity. It assumes
   * that the caller does the necessary locking.
   */
  axListBase * cloneList_u(void);

  ///
  size_t      m_size;

  ///
  listType_t  m_list;

  ///
  void setIsFreeEntries(bool);
  bool getIsFreeEntries(void);

private:

  ///
  axListBase(const axListBase &);

  ///
  bool m_isFreeEntries;
};

#endif // _axListBase_hpp_
