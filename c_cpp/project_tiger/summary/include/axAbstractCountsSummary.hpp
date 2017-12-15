
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractCountsSummary_hpp_
#define _axAbstractCountsSummary_hpp_

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
 * file/class: axAbstractCountsSummary.hpp
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

class axAbstractCountsSummary : public axAbstractSummary
{
public:

  /// destructor
  virtual ~axAbstractCountsSummary();

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
  axAbstractCountsSummary(struct tm *);

  /**
   * default constructor
   */
  axAbstractCountsSummary();

  virtual AX_INT8 * getResIdSql(void)=0;
  virtual AX_INT8 * getCountsLog(void)=0;
  virtual AX_INT8 * getInsertSql(void)=0;

  struct tm * m_todaysDate;

private:

  /// not allowed
  axAbstractCountsSummary(const axAbstractCountsSummary &);

  bool summarizeForResource(MYSQL *, AX_UINT32);

};

#endif // _axAbstractCountsSummary_hpp_
