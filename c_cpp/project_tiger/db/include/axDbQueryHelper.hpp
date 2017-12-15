
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbQueryHelper_hpp_
#define _axDbQueryHelper_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axDbGenericQuery.hpp"

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
 * file/class: axDbQueryHelper.hpp
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

class axDbQueryHelper 
{
public:

  /// default constructor
  axDbQueryHelper(axDbGenericQuery *);

  /// destructor
  virtual ~axDbQueryHelper();

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
  axDbGenericQuery * getQuery(void);

protected:

  /// default constructor
  axDbQueryHelper();


private:

  axDbQueryHelper(const axDbQueryHelper &);

  axDbGenericQuery * m_queryObj;
};

#endif // _axDbQueryHelper_hpp_
