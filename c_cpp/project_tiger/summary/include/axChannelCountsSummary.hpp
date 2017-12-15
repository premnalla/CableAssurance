
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axChannelCountsSummary_hpp_
#define _axChannelCountsSummary_hpp_

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
 * file/class: axChannelCountsSummary.hpp
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

class axChannelCountsSummary : public axAbstractCountsSummary
{
public:

  /**
   * default constructor
   */
  axChannelCountsSummary(struct tm *);

  /// destructor
  virtual ~axChannelCountsSummary();

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
  axChannelCountsSummary();

  virtual AX_INT8 * getResIdSql(void);
  virtual AX_INT8 * getCountsLog(void);
  virtual AX_INT8 * getInsertSql(void);

private:

  static AX_INT8 * SQL_CHANNEL_RES_ID;
  static AX_INT8 * SQL_CHANNEL_COUNTS_LOG;
  static AX_INT8 * SQL_INSERT_SUMMARY;

  /// not allowed
  axChannelCountsSummary(const axChannelCountsSummary &);

};

#endif // _axChannelCountsSummary_hpp_
