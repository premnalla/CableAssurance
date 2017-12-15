
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMtaAvailabilitySummary_hpp_
#define _axMtaAvailabilitySummary_hpp_

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
 * file/class: axMtaAvailabilitySummary.hpp
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

class axMtaAvailabilitySummary : public axAbstractStatusSummary
{
public:

  /**
   * default constructor
   */
  axMtaAvailabilitySummary(struct tm *, time_t);

  /// destructor
  virtual ~axMtaAvailabilitySummary();

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
  axMtaAvailabilitySummary();

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
  axMtaAvailabilitySummary(const axMtaAvailabilitySummary &);

};

#endif // _axMtaAvailabilitySummary_hpp_
