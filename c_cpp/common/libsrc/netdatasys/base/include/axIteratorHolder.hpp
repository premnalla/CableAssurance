
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axIteratorHolder_hpp_
#define _axIteratorHolder_hpp_

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
 * file/class: axIteratorHolder.hpp
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

class axIteratorHolder 
{
public:

  /// data constructor
  axIteratorHolder(axAbstractIterator *);

  /// destructor
  virtual ~axIteratorHolder();

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
  axIteratorHolder();


private:

  axIteratorHolder(const axIteratorHolder &);

  axAbstractIterator * m_iter;
};

#endif // _axIteratorHolder_hpp_
