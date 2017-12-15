
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbCABasicAlarm_hpp_
#define _axDbCABasicAlarm_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbCAAlarm.hpp"
#include "axListBase.hpp"
#include "axAbstractCAAlarm.hpp"
// #include "axHfcAlarm.hpp"

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
 * file/class: axDbCABasicAlarm.hpp
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

class axDbCABasicAlarm : public axDbCAAlarm
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Insert;
  static AX_INT8 * Query;
  static AX_INT8 * QueryByState;
  static AX_INT8 * Update;
  static AX_INT8 * Delete;

  /**
   * IN: Alarm ID
   */
  axDbCABasicAlarm(AX_UINT64);

  /**
   * IN: Resource ID
   */
  axDbCABasicAlarm(DB_RESID_t);

  /**
   * IN: HFC Alarm
   */
  // axDbCABasicAlarm(axHfcAlarm *);
  axDbCABasicAlarm(axAbstractCAAlarm *);

  /**
   * Copy Constructor : apparently needed. Why?
   */
  axDbCABasicAlarm(const axDbCABasicAlarm &);

  /// default constructor
  axDbCABasicAlarm();

  /// destructor
  virtual ~axDbCABasicAlarm();

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

  AX_UINT64        m_alarmId;
  DB_RESID_t       m_resId;
  AX_UINT32        m_detectionTime;
  AX_UINT32        m_detectionTimeUSec;
  AX_UINT32        m_soakDuration;
  AX_UINT8         m_alarmState;
  AX_UINT8         m_alarmType;
  AX_UINT8         m_alarmSubType;

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

  ///
  bool deleteChildren(void);

private:


};

#endif // _axDbCABasicAlarm_hpp_
