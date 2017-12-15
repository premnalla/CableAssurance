
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbCAHistoricalAlarm_hpp_
#define _axDbCAHistoricalAlarm_hpp_

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
 * file/class: axDbCAHistoricalAlarm.hpp
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

class axDbCAHistoricalAlarm : public axDbCAAlarm
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
  axDbCAHistoricalAlarm(AX_UINT64);

  /**
   * IN: HFC Alarm
   */
  axDbCAHistoricalAlarm(axAbstractCAAlarm *);

  /**
   * Copy Constructor : apparently needed. Why?
   */
  axDbCAHistoricalAlarm(const axDbCAHistoricalAlarm &);

  /// default constructor
  axDbCAHistoricalAlarm();

  /// destructor
  virtual ~axDbCAHistoricalAlarm();

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
  AX_UINT32        m_clearedUserId;
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

#endif // _axDbCAHistoricalAlarm_hpp_
