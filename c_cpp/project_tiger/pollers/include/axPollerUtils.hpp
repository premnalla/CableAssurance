
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axPollerUtils_hpp_
#define _axPollerUtils_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"

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
 * file/class: axPollerUtils.hpp
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

class axPollerUtils 
{
public:

  /// destructor
  virtual ~axPollerUtils();

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
   * IN:
   *  1) Total number of elements
   *  2) Upper bound of Runnables (absolute max number of runnables)
   *
   */
  static AX_INT32 GetNumRunnables(AX_INT32, AX_INT32);

  /**
   *
   * IN:
   *  1) Total number of elements
   *  2) Actual Number of Runnables
   *
   */
  static AX_INT32 GetNumElementsPerRunnable(AX_INT32, AX_INT32);

protected:


private:

  /// default constructor
  axPollerUtils();

  axPollerUtils(const axPollerUtils &);

};

#endif // _axPollerUtils_hpp_
