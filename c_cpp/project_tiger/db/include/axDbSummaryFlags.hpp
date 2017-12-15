
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbSummaryFlags_hpp_
#define _axDbSummaryFlags_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractDbObject.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to retrieve and save the state of Summarization
 * processing. This is useful if the the app were to restart abruptly.
 * 
 * file/class: axDbSummaryFlags.hpp
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

class axDbSummaryFlags : public axAbstractDbObject
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * Update;

  /// default constructor
  axDbSummaryFlags();

  /// destructor
  virtual ~axDbSummaryFlags();

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

  AX_UINT8 m_overallSummStarted;
  AX_UINT8 m_overallSummEnded;
  AX_UINT8 m_dbCopyStarted;
  AX_UINT8 m_dbCopyEnded;
  AX_UINT8 m_cmtsCountsSummStarted;
  AX_UINT8 m_cmtsCountsSummEnded;
  AX_UINT8 m_hfcCountsSummStarted;
  AX_UINT8 m_hfcCountsSummEnded;
  AX_UINT8 m_channelCountsSummStarted;
  AX_UINT8 m_channelCountsSummEnded;
  AX_UINT8 m_cmPerfSummStarted;
  AX_UINT8 m_cmPerfSummEnded;
  AX_UINT8 m_cmStatusSummStarted;
  AX_UINT8 m_cmStatusSummEnded;
  AX_UINT8 m_mtaAvailSummStarted;
  AX_UINT8 m_mtaAvailSummEnded;
  AX_UINT8 m_alarmSummStarted;
  AX_UINT8 m_alarmSummEnded;
  AX_UINT8 m_cmPerfThreshSummStarted;
  AX_UINT8 m_cmPerfThreshSummEnded;
  AX_UINT8 m_hfcStatusSummStarted;
  AX_UINT8 m_hfcStatusSummEnded;
  AX_UINT8 m_hfcTcaSummStarted;
  AX_UINT8 m_hfcTcaSummEnded;

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

  // Copy disallowed
  axDbSummaryFlags(const axDbSummaryFlags &);

};

#endif // _axDbSummaryFlags_hpp_
