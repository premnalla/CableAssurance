
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbCASoakingAlarm_hpp_
#define _axDbCASoakingAlarm_hpp_

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
 * file/class: axDbCASoakingAlarm.hpp
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

class axDbCASoakingAlarm : public axDbCAAlarm
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Insert;
  static AX_INT8 * Query;
  static AX_INT8 * Delete;

  /**
   * IN: Alarm ID
   */
  axDbCASoakingAlarm(AX_UINT64);

  /**
   * Copy Constructor : apparently needed. Why?
   */
  axDbCASoakingAlarm(const axDbCASoakingAlarm &);

  /// default constructor
  axDbCASoakingAlarm();

  /// destructor
  virtual ~axDbCASoakingAlarm();

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

#endif // _axDbCASoakingAlarm_hpp_
