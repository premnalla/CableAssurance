
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmStatusSummary_hpp_
#define _axCmStatusSummary_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractStatusSummary.hpp"

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
 * file/class: axCmStatusSummary.hpp
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

class axCmStatusSummary : public axAbstractStatusSummary
{
public:

  /**
   * data constructor
   */
  axCmStatusSummary(struct tm *, time_t);

  /// destructor
  virtual ~axCmStatusSummary();

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
  axCmStatusSummary();

  /**
   * 
   */
  virtual AX_INT8 * getResIdSql(void);
  virtual AX_INT8 * getCurrentStatusSql(void);
  virtual AX_INT8 * getInsertSql(void);
  virtual bool      isDeviceStateGood(AX_UINT8);

private:

  static AX_INT8 * SQL_RES_ID;
  static AX_INT8 * SQL_CURRENT_STATUS;
  static AX_INT8 * SQL_INSERT_SUMMARY;

  /// not allowed
  axCmStatusSummary(const axCmStatusSummary &);

};

#endif // _axCmStatusSummary_hpp_
