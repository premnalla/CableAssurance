
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcMtaAlarmDetectionRunnable_hpp_
#define _axHfcMtaAlarmDetectionRunnable_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractHfcAlarmDetectionRun.hpp"
#include "axInternalCmts.hpp"

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
 * file/class: axHfcMtaAlarmDetectionRunnable.hpp
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

class axHfcMtaAlarmDetectionRunnable : 
                                public axAbstractHfcAlarmDetectionRun
{
public:

  /// data constructor
  axHfcMtaAlarmDetectionRunnable(axAbstractCARunnableCollection *, 
                                                   axInternalCmts *);

  /// destructor
  virtual ~axHfcMtaAlarmDetectionRunnable();

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
  axHfcMtaAlarmDetectionRunnable();

private:

  axHfcMtaAlarmDetectionRunnable(
                            const axHfcMtaAlarmDetectionRunnable &);
};

#endif // _axHfcMtaAlarmDetectionRunnable_hpp_
