
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmPerfUtils_hpp_
#define _axCmPerfUtils_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"
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
 * file/class: axCmPerfUtils.hpp
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

class axCmPerfUtils 
{
public:

  /// destructor
  virtual ~axCmPerfUtils();

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
  static bool IsCmThresholdCrossed(AX_INT16, AX_INT16, AX_INT16);

  /**
   *
   */
  static bool NewTcaUpdateWork(bool, time_t, axIntCmNonkey_t *);

  /**
   *
   */
  static bool LogTcaWork(time_t, axIntCmNonkey_t *);

  /**
   *
   */
  static bool LogStatusWork(time_t, axIntCmNonkey_t *);

protected:

  /**
   * default constructor
   */
  axCmPerfUtils();


private:

  /// not allowed
  axCmPerfUtils(const axCmPerfUtils &);

};

#endif // _axCmPerfUtils_hpp_
