
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axListIterator_hpp_
#define _axListIterator_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractIterator.hpp"
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
 * file/class: axListIterator.hpp
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

class axListIterator : public axAbstractIterator
{
public:

  /// data constructor
  axListIterator(axListBase *);

  /// destructor
  virtual ~axListIterator();

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
    * Extended from base class
    */
   virtual axObject * getFirst(void);
   virtual axObject * getNext(void);
   virtual axObject * getLast(void);
   virtual axObject * getPrevious(void);

protected:

  /// default constructor
  axListIterator();

private:

  axListIterator(const axListIterator &);

  axListBase::listType_t::iterator m_start;
  axListBase::listType_t::iterator m_curr;
  axListBase::listType_t::iterator m_end;

  axListBase * m_list;
};

#endif // _axListIterator_hpp_
