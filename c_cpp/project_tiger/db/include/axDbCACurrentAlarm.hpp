
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbCACurrentAlarm_hpp_
#define _axDbCACurrentAlarm_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbCAAlarm.hpp"
#include "axListBase.hpp"
#include "axAbstractCAAlarm.hpp"

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
 * file/class: axDbCACurrentAlarm.hpp
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

class axDbCACurrentAlarm : public axDbCAAlarm
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Insert;
  static AX_INT8 * Update;
  static AX_INT8 * Delete;
  static AX_INT8 * Query;
  static AX_INT8 * QueryByState;
  static AX_INT8 * QueryByResource;

  /**
   * IN: Root Alarm ID
   */
  axDbCACurrentAlarm(AX_UINT64);

  /**
   * IN: HFC Alarm
   */
  axDbCACurrentAlarm(axAbstractCAAlarm *);

  /**
   * Copy Constructor : apparently needed. Why?
   */
  axDbCACurrentAlarm(const axDbCACurrentAlarm &);

  /// default constructor
  axDbCACurrentAlarm();

  /// destructor
  virtual ~axDbCACurrentAlarm();

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

  AX_UINT64        m_id;
  AX_UINT64        m_rootAlarmId;
  AX_UINT32        m_soakDuration;
  AX_UINT16        m_alarmState;

  // ***********************************
  // data members end ...
  // ***********************************

  /**
   *
   */
  virtual bool getRow(void);
  virtual bool getRow(AX_INT8 *);
  virtual bool getRows(axListBase &);
  virtual bool getRows(axListBase &, AX_INT8 *);
  virtual bool insertRow(void);
  virtual bool updateRow(void);
  virtual bool deleteRow(void);

#if 0
  /**
   *
   */
  bool getSecondaryData(void);

  /**
   *
   */
  static bool GetCurrentAlarms(axAbstractCAAlarm *, axListBase &);
#endif

protected:


private:


};

#endif // _axDbCACurrentAlarm_hpp_
