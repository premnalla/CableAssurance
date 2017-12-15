
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcStatusSummary_hpp_
#define _axHfcStatusSummary_hpp_

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
 * file/class: axHfcStatusSummary.hpp
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

class axHfcStatusSummary : public axAbstractStatusSummary
{
public:

  /**
   * data constructor
   */
  axHfcStatusSummary(struct tm *, time_t);

  /// destructor
  virtual ~axHfcStatusSummary();

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
  axHfcStatusSummary();

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
  axHfcStatusSummary(const axHfcStatusSummary &);

};

#endif // _axHfcStatusSummary_hpp_
