
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSortedList_hpp_
#define _axSortedList_hpp_

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
 * file/class: axSortedList.hpp
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

class axSortedList : public axListBase
{
public:

  /// data constructor
  axSortedList(bool);

  /// default constructor
  axSortedList();

  /// destructor
  virtual ~axSortedList();

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


  /**
   * Overrides from base class: axListBase
   *
   * @return
   * @see
   */
  virtual axObject * add(axObject *);
  virtual axObject * add(axObject *, listType_t::iterator &);

protected:


private:

  axSortedList(const axSortedList &);

};

#endif // _axSortedList_hpp_
