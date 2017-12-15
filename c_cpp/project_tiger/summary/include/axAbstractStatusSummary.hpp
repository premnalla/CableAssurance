
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractStatusSummary_hpp_
#define _axAbstractStatusSummary_hpp_

//********************************************************************
// include files
//********************************************************************
#include <time.h>
#include "axAbstractSummary.hpp"
#include "axDbMySqlConnection.hpp"

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
 * file/class: axAbstractStatusSummary.hpp
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

class axAbstractStatusSummary : public axAbstractSummary
{
public:

  /// destructor
  virtual ~axAbstractStatusSummary();

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
  virtual bool summarize(void);

protected:

  /**
   * default constructor
   */
  axAbstractStatusSummary(struct tm *, time_t);

  /**
   * default constructor
   */
  axAbstractStatusSummary();

  virtual AX_INT8 * getResIdSql(void)=0;
  virtual AX_INT8 * getCurrentStatusSql(void)=0;
  virtual AX_INT8 * getInsertSql(void)=0;
  virtual bool      isDeviceStateGood(AX_UINT8)=0;

  struct tm * m_todaysDate;
  time_t      m_startTime;

private:

  /// not allowed
  axAbstractStatusSummary(const axAbstractStatusSummary &);

  bool summarizeForResource(MYSQL *, AX_UINT32);

};

#endif // _axAbstractStatusSummary_hpp_
