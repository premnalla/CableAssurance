
//********************************************************************
// OBSOLETE
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAlarmTrackingMgr_hpp_
#define _axAlarmTrackingMgr_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axCAAlarmCollection.hpp"
#include "axHfcAlarm.hpp"
#include "axMtaAlarm.hpp"

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
 * file/class: axAlarmTrackingMgr.hpp
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

class axAlarmTrackingMgr 
{
public:

  /// default constructor
  axAlarmTrackingMgr();

  /// destructor
  virtual ~axAlarmTrackingMgr();

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
  static axAlarmTrackingMgr * getInstance();

#if 0
  ///
  bool          isCurrentHfcAlarm(axHfcAlarm *);
  axHfcAlarm * addCurrentHfcAlarm(axHfcAlarm *);
  axHfcAlarm * findCurrentHfcAlarm(axHfcAlarm *);
  axHfcAlarm * removeCurrentHfcAlarm(axHfcAlarm *);

  ///
  bool          isCurrentMtaAlarm(axMtaAlarm *);
  axMtaAlarm * addCurrentMtaAlarm(axMtaAlarm *);
  axMtaAlarm * findCurrentMtaAlarm(axMtaAlarm *);
  axMtaAlarm * removeCurrentMtaAlarm(axMtaAlarm *);
#endif

  /*
   * for finer grained control
   */
  axCAAlarmCollection * getHfcAlarmTable(void);
  axCAAlarmCollection * getMtaAlarmTable(void);
  
protected:

  axHfcAlarm * findCurrentHfcAlarm_u(axHfcAlarm *);
  axMtaAlarm * findCurrentMtaAlarm_u(axMtaAlarm *);

private:

  axAlarmTrackingMgr(const axAlarmTrackingMgr &);

  /*
   *
   */
  static axAlarmTrackingMgr  * m_instance;
  bool                         m_initialized;

  /*
   *
   */
  axCAAlarmCollection          m_currentHfcAlarmTable;
  axCAAlarmCollection          m_currentMtaAlarmTable;
};

#endif // _axAlarmTrackingMgr_hpp_
