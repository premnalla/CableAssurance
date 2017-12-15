
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmtsCountsSummary_hpp_
#define _axCmtsCountsSummary_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCountsSummary.hpp"

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
 * file/class: axCmtsCountsSummary.hpp
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

class axCmtsCountsSummary : public axAbstractCountsSummary
{
public:

  /**
   * default constructor
   */
  axCmtsCountsSummary(struct tm *);

  /// destructor
  virtual ~axCmtsCountsSummary();

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

protected:

  /**
   * default constructor
   */
  axCmtsCountsSummary();

  virtual AX_INT8 * getResIdSql(void);
  virtual AX_INT8 * getCountsLog(void);
  virtual AX_INT8 * getInsertSql(void);

private:

  static AX_INT8 * SQL_CMTS_RES_ID;
  static AX_INT8 * SQL_CMTS_COUNTS_LOG;
  static AX_INT8 * SQL_INSERT_SUMMARY;

  /// not allowed
  axCmtsCountsSummary(const axCmtsCountsSummary &);

};

#endif // _axCmtsCountsSummary_hpp_
