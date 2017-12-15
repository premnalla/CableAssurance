
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbCACurrentOutage_hpp_
#define _axDbCACurrentOutage_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbCAOutage.hpp"
#include "axListBase.hpp"
#include "axHfcOutage.hpp"

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
 * file/class: axDbCACurrentOutage.hpp
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

class axDbCACurrentOutage : public axDbCAOutage
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * BasicCurrAlarmInsert;
  static AX_INT8 * BasicCurrAlarmQuery;

  /**
   * IN: Outage ID
   */
  axDbCACurrentOutage(AX_UINT64);

  /**
   * IN: Resource ID
   */
  axDbCACurrentOutage(DB_RESID_t);

  /**
   * IN: HFC outage
   */
  axDbCACurrentOutage(axHfcOutage *);

  /**
   * Copy Constructor : apparently needed. Why?
   */
  axDbCACurrentOutage(const axDbCACurrentOutage &);

  /// default constructor
  axDbCACurrentOutage();

  /// destructor
  virtual ~axDbCACurrentOutage();

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

  /**
   *
   */
  bool getSecondaryData(void);

protected:


private:


};

#endif // _axDbCACurrentOutage_hpp_
