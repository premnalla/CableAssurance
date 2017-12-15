
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcAlarmDetectionRunnable_hpp_
#define _axHfcAlarmDetectionRunnable_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractHfcAlarmDetectionRun.hpp"

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
 * file/class: axHfcAlarmDetectionRunnable.hpp
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

class axHfcAlarmDetectionRunnable : 
                               public axAbstractHfcAlarmDetectionRun
{
public:

  /// data constructor
  axHfcAlarmDetectionRunnable(axAbstractCARunnableCollection *, 
                                                  axInternalCmts *);

  /// destructor
  virtual ~axHfcAlarmDetectionRunnable();

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
  virtual AX_INT32 run(void);

  /**
   *
   */
  virtual void nextAction(void);

  /**
   *
   */
  virtual void addSubject(axObject *);

protected:

  /// default constructor
  axHfcAlarmDetectionRunnable();

private:

  axHfcAlarmDetectionRunnable(const axHfcAlarmDetectionRunnable &);
};

#endif // _axHfcAlarmDetectionRunnable_hpp_
