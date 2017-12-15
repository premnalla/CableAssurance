
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDailySummary_hpp_
#define _axDailySummary_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSummary.hpp"
#include "axDbSummaryFlags.hpp"
#include "axDbLastCompSummary.hpp"

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
 * file/class: axDailySummary.hpp
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

class axDailySummary : public axAbstractSummary
{
public:

  /**
   * default constructor
   */
  axDailySummary();

  /// destructor
  virtual ~axDailySummary();

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

  /**
   * 
   */
  bool summarize(axDbSummaryFlags &, axDbLastCompSummary &);

protected:


private:

  ///
  static AX_INT8 * SQL_CREATE_SCHEMA;
  static AX_INT8 * CM_PERF_LOG;
  static AX_INT8 * CMTS_COUNTS_LOG;
  static AX_INT8 * HFC_COUNTS_LOG;
  static AX_INT8 * CHANNEL_COUNTS_LOG;
  static AX_INT8 * CM_CURR_STATUS_UPD;
  static AX_INT8 * MTA_AVAIL_UPD;

  /// not allowed
  axDailySummary(const axDailySummary &);

  bool previousCycleCompleted(axDbSummaryFlags &, axDbLastCompSummary &);
  void continuePerviousCycle(axDbSummaryFlags &, axDbLastCompSummary &);
  void summarizeData(axDbSummaryFlags &, axDbLastCompSummary &);

  bool copyUpdateDatabases(axDbSummaryFlags &, axDbLastCompSummary &);
  void doCmtsCountsSummary(axDbSummaryFlags &, axDbLastCompSummary &);
  void doHfcCountsSummary(axDbSummaryFlags &, axDbLastCompSummary &);
  void doChannelCountsSummary(axDbSummaryFlags &, axDbLastCompSummary &);
  void doCmPerfSummary(axDbSummaryFlags &, axDbLastCompSummary &);
  void doCmStatusSummary(axDbSummaryFlags &, axDbLastCompSummary &);
  void doCmPerfThresholdSummary(axDbSummaryFlags &, axDbLastCompSummary &);
  void doMtaAvailSummary(axDbSummaryFlags &, axDbLastCompSummary &);
  void doAlarmSummary(axDbSummaryFlags &, axDbLastCompSummary &);
  void doHfcStatusSummary(axDbSummaryFlags &, axDbLastCompSummary &);
  void doHfcTcaSummary(axDbSummaryFlags &, axDbLastCompSummary &);

  bool createSchema(void);
  bool copyData(axDbLastCompSummary &);
  bool executeMultipleStatements(AX_INT8 * sql);
  bool executeSingleStatement(AX_INT8 * sql);
  bool updateTables(void);
  bool updateInternalStructures(void);

  time_t      m_startTm;
  struct tm   m_todaysDate;
};

#endif // _axDailySummary_hpp_
