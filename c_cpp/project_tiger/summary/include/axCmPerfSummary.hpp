
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmPerfSummary_hpp_
#define _axCmPerfSummary_hpp_

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
 * file/class: axCmPerfSummary.hpp
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

class axCmPerfSummary : public axAbstractSummary
{
public:

  /**
   * default constructor
   */
  axCmPerfSummary(struct tm *);

  /// destructor
  virtual ~axCmPerfSummary();

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
  axCmPerfSummary();

private:

  static AX_INT8 * SQL_RES_ID;
  static AX_INT8 * SQL_PERF_LOG;
  static AX_INT8 * SQL_INSERT_SUMMARY;

  /// not allowed
  axCmPerfSummary(const axCmPerfSummary &);

  bool summarizeForCm(MYSQL *, AX_UINT32);

  struct tm * m_todaysDate;

};

#endif // _axCmPerfSummary_hpp_
