
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axList_hpp_
#define _axList_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axListBase.hpp"

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
 * file/class: axList.hpp
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

class axList : public axListBase
{
public:

  /// data constructor
  axList(bool);

  /// default constructor
  axList();

  /// destructor
  virtual ~axList();

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
  virtual axObject * add(axObject *, axListBase::listType_t::iterator &);
  // virtual void add(axObject *, axListBase::listType_t::iterator &);

  ///
  virtual axObject * getFirst(void);

  ///
  virtual axObject * getLast(void);

  /**
   * Returns a list containing a copy of all the elems in this List
   * NOTE: this only does a SHALLOW COPY of the elements in the List.
   */
  virtual axListBase * cloneList(void);

  ///
  virtual axObject * find(axObject *);

  ///
  virtual axObject * remove(axObject *);

  ///
  virtual axObject * remove(axListBase::listType_t::iterator &);

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

protected:

private:

  ///
  axList(const axList &);

};

#endif // _axList_hpp_
