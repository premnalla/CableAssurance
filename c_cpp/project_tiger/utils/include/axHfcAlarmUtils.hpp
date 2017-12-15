
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcAlarmUtils_hpp_
#define _axHfcAlarmUtils_hpp_

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
 * file/class: axHfcAlarmUtils.hpp
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

class axHfcAlarmUtils 
{
public:

  /// destructor
  virtual ~axHfcAlarmUtils();

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
  static bool NewAlarmUpdateWork(bool, time_t, axIntHfcNonkey_t *);

  /**
   * 
   */
  static bool LogAlarmWork(time_t, axIntHfcNonkey_t *);


protected:

  /**
   * default constructor
   */
  axHfcAlarmUtils();


private:

  /// not allowed
  axHfcAlarmUtils(const axHfcAlarmUtils &);

};

#endif // _axHfcAlarmUtils_hpp_
