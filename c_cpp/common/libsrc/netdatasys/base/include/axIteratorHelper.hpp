
//********************************************************************
// OBSOLETE !!! USE axIteratorHolder instead
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axIteratorHelper_hpp_
#define _axIteratorHelper_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axAbstractIterator.hpp"

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
 * file/class: axIteratorHelper.hpp
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

class axIteratorHelper 
{
public:

  /// data constructor
  axIteratorHelper(axAbstractIterator *);

  /// destructor
  virtual ~axIteratorHelper();

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

  ///
  axAbstractIterator * getIterator(void);

protected:

  /// default constructor
  axIteratorHelper();


private:

  axIteratorHelper(const axIteratorHelper &);

  axAbstractIterator * m_iter;
};

#endif // _axIteratorHelper_hpp_
