
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractCACounts_hpp_
#define _axAbstractCACounts_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axInternalDsTypes.hpp"

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
 * file/class: axAbstractCACounts.hpp
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

class axAbstractCACounts 
{
public:

  /// destructor
  virtual ~axAbstractCACounts();

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


protected:

  /// default constructor
  axAbstractCACounts();

  /**
   *
   *
   */
  bool needToUpdate(struct timeval *, axIntCounts_t *);

private:

  axAbstractCACounts(const axAbstractCACounts &);

};

#endif // _axAbstractCACounts_hpp_
