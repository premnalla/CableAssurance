
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractHfcAlarmDetectionRun_hpp_
#define _axAbstractHfcAlarmDetectionRun_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCARunnable.hpp"
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
 * file/class: axAbstractHfcAlarmDetectionRun.hpp
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

class axAbstractHfcAlarmDetectionRun : public axAbstractCARunnable
{
public:

  /// destructor
  virtual ~axAbstractHfcAlarmDetectionRun();

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

  /// data constructor
  axAbstractHfcAlarmDetectionRun(axAbstractCARunnableCollection *, 
                                                   axInternalCmts *);

  /// default constructor
  axAbstractHfcAlarmDetectionRun();

  ///
  axInternalCmts * getInternalCmts(void);

  ///
  void postCheckForMtaAlarms(void);

private:

  axAbstractHfcAlarmDetectionRun(
                            const axAbstractHfcAlarmDetectionRun &);

  axInternalCmts * m_intCmts;
};

#endif // _axAbstractHfcAlarmDetectionRun_hpp_
