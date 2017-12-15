
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmPerfThreshSummary_hpp_
#define _axCmPerfThreshSummary_hpp_

//********************************************************************
// include files
//********************************************************************
#include <time.h>
#include "axDbMySqlConnection.hpp"
#include "axAbstractSummary.hpp"

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
 * file/class: axCmPerfThreshSummary.hpp
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

class axCmPerfThreshSummary : public axAbstractSummary
{
public:

  /**
   * data constructor
   */
  axCmPerfThreshSummary(struct tm *, time_t);

  /// destructor
  virtual ~axCmPerfThreshSummary();

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
  bool summarize(void);

protected:

  /**
   * default constructor
   */
  axCmPerfThreshSummary();

private:

  static AX_INT8 * SQL_RES_ID;
  static AX_INT8 * SQL_CURR_PERF;
  static AX_INT8 * SQL_INSERT_SUMMARY;

  /// not allowed
  axCmPerfThreshSummary(const axCmPerfThreshSummary &);

  bool summarizeForCm(MYSQL *, AX_UINT32);

  struct tm * m_todaysDate;
  time_t      m_startTime;

};

#endif // _axCmPerfThreshSummary_hpp_
