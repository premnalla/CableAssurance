
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbCAAlarmSecondary_hpp_
#define _axDbCAAlarmSecondary_hpp_

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
 * file/class: axDbCAAlarmSecondary.hpp
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

class axDbCAAlarmSecondary : public axDbCAAlarm
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Insert;
  static AX_INT8 * Query;
  static AX_INT8 * Delete;
  static AX_INT8 * Update;

  /**
   * IN: Alarm ID
   */
  axDbCAAlarmSecondary(AX_UINT64);

  /**
   * Copy Constructor : apparently needed. Why?
   */
  axDbCAAlarmSecondary(const axDbCAAlarmSecondary &);

  /// default constructor
  axDbCAAlarmSecondary();

  /// destructor
  virtual ~axDbCAAlarmSecondary();

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
  AX_UINT32        m_preSoakTotal;
  AX_UINT32        m_preSoakCount;
  AX_UINT32        m_postSoakTotal;
  AX_UINT32        m_postSoakCount;
  AX_UINT32        m_alarmDetectionWindow;
  AX_UINT32        m_alarmThreshold;

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


private:


};

#endif // _axDbCAAlarmSecondary_hpp_
