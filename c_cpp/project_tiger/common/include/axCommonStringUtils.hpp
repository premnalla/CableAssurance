
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCommonStringUtils_hpp_
#define _axCommonStringUtils_hpp_

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
 * file/class: axCommonStringUtils.hpp
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

class axCommonStringUtils 
{
public:

  /// destructor
  virtual ~axCommonStringUtils();

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
   * IN: dest, src, destLength, srcLength
   */
  static void CopyString(AX_INT8 *, AX_INT8 *, AX_INT32, AX_INT32);

  /**
   * IN: dest, src, destLength
   */
  static void CopyString(AX_INT8 *, AX_INT8 *, AX_INT32);

  /**
   * IN: dest, src
   */
  static void CopyString(AX_INT8 *, AX_INT8 *);

protected:


private:

  /// default constructor
  axCommonStringUtils();

  axCommonStringUtils(const axCommonStringUtils &);

};

#endif // _axCommonStringUtils_hpp_
