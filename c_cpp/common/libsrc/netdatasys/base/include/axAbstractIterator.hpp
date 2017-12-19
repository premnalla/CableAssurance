
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractIterator_hpp_
#define _axAbstractIterator_hpp_

//********************************************************************
// include files
//********************************************************************
#include <stddef.h>
#include "axAll.h"
#include "axObject.hpp"

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
 * file/class: axAbstractIterator.hpp
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

class axAbstractIterator 
{
public:

  /// destructor
  virtual ~axAbstractIterator();

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

   // virtual void       init(void)=0;
   virtual axObject * getFirst(void)=0;
   virtual axObject * getNext(void)=0;
   // virtual axObject * getCurrent(void)=0;
   virtual axObject * getLast(void)=0;
   virtual axObject * getPrevious(void)=0;
   
protected:

  /// default constructor
  axAbstractIterator();

private:

  axAbstractIterator(const axAbstractIterator &);

};

#endif // _axAbstractIterator_hpp_
