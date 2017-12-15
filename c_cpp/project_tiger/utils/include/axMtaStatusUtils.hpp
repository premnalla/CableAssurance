
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMtaStatusUtils_hpp_
#define _axMtaStatusUtils_hpp_

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
 * file/class: axMtaStatusUtils.hpp
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

class axMtaStatusUtils 
{
public:

  /// destructor
  virtual ~axMtaStatusUtils();

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
  static bool NewStatusUpdateWork(bool, time_t, axIntGenMtaNonkey_t *);

  /**
   * 
   */
  static bool LogStatusWork(time_t, axIntGenMtaNonkey_t *);


protected:

  /**
   * default constructor
   */
  axMtaStatusUtils();


private:

  /// not allowed
  axMtaStatusUtils(const axMtaStatusUtils &);

};

#endif // _axMtaStatusUtils_hpp_
