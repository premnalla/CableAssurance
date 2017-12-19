
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAvlIterator_hpp_
#define _axAvlIterator_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractIterator.hpp"
#include "avl.h"

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
 * file/class: axAvlIterator.hpp
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

class axAvlIterator : public axAbstractIterator
{
public:

  /// data constructor
  axAvlIterator(avl_table *);

  /// destructor
  virtual ~axAvlIterator();

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
    *
    */
   virtual axObject * getFirst(void);
   virtual axObject * getNext(void);
   virtual axObject * getLast(void);
   virtual axObject * getPrevious(void);

protected:

  /// default constructor
  axAvlIterator();


private:

  axAvlIterator(const axAvlIterator &);

  avl_table * m_table;
  avl_traverser * m_traverser;

};

#endif // _axAvlIterator_hpp_
