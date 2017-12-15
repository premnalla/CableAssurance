
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbCARootAlarm_hpp_
#define _axDbCARootAlarm_hpp_

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
 * file/class: axDbCARootAlarm.hpp
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

class axDbCARootAlarm : public axDbCAAlarm
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
  axDbCARootAlarm(AX_UINT64);

  /**
   * IN: Resource ID
   */
  axDbCARootAlarm(DB_RESID_t);

  /**
   * IN: HFC Alarm
   */
  axDbCARootAlarm(axAbstractCAAlarm *);

  /**
   * Copy Constructor : apparently needed. Why?
   */
  axDbCARootAlarm(const axDbCARootAlarm &);

  /// default constructor
  axDbCARootAlarm();

  /// destructor
  virtual ~axDbCARootAlarm();

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

  AX_UINT64        m_rootAlarmId;
  DB_RESID_t       m_resId;
  AX_UINT32        m_detectionTime;
  AX_UINT32        m_detectionTimeUSec;
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


protected:

  ///
  // bool deleteChildren(void);

private:


};

#endif // _axDbCARootAlarm_hpp_
