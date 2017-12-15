
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbAbstractCurrentStatus_hpp_
#define _axDbAbstractCurrentStatus_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractStatus.hpp"
// #include "axInternalDsTypes.hpp"

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
 * file/class: axDbAbstractCurrentStatus.hpp
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

class axDbAbstractCurrentStatus : public axDbAbstractStatus
{
public:

  /// destructor
  virtual ~axDbAbstractCurrentStatus();

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

  // ***********************************
  // data members begin ...
  // ***********************************

  /**
   * Good value time (onlineTime, availableTime, etc)
   */
  AX_UINT32   m_time1;

  /**
   * Bad value time (offlineTime, unavailableTime, etc)
   */
  AX_UINT32   m_time2;

  /**
   * Last State Change Time
   */
  AX_UINT32  m_lastStateChangeTime;

  /**
   * Current status, perf-reading, etc., value
   */
  AX_UINT16   m_currentValue;

  /**
   * Number of of state changes
   */
  AX_UINT16   m_stateChanges;

  // ***********************************
  // data members end ...
  // ***********************************

protected:

  /// default constructor
  axDbAbstractCurrentStatus();

  /**
   * IN: Resource ID
   */
  axDbAbstractCurrentStatus(DB_RESID_t);

  /**
   * IN: Res ID(any one of: cmts, hfc, channel, etc)
   * IN: time_t (time in seconds since 01/1/70
   */
  axDbAbstractCurrentStatus(DB_RESID_t, time_t);

private:

  // Copy disallowed
  axDbAbstractCurrentStatus(const axDbAbstractCurrentStatus &);

};

#endif // _axDbAbstractCurrentStatus_hpp_
