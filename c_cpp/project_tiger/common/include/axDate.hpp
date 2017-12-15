
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axData_hpp_
#define _axData_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"

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
 * file/class: axDate.hpp
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

class axDate 
{
public:

  /**
   * default constructor
   */
  axDate();

  /**
   *
   * arg1: month
   * arg2: day-of-month
   * arg3: year
   *
   */
  axDate(AX_INT32, AX_INT32, AX_INT32);

  /**
   *
   * arg1: month
   * arg2: day-of-month
   * arg3: year
   * arg4: hour
   * arg5: minute
   *
   */
  axDate(AX_INT32, AX_INT32, AX_INT32, AX_INT32, AX_INT32);

  /**
   *
   * arg1: seconds
   *
   */
  axDate(AX_UINT32);

  /// destructor
  virtual ~axDate();

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
  AX_UINT8   getMonth(void);
  AX_UINT8   getDayOfMonth(void);
  AX_UINT16  getYear(void);
  AX_UINT8   getHour(void);
  AX_UINT8   getMinute(void);
  AX_UINT32  getTimeSeconds(void);

  /**
   *
   * arg1: roll (+/-) 'n' days
   */
  void rollDays(AX_INT32);

protected:


private:

  /// not allowed
  axDate(const axDate &);

  AX_UINT8    m_month;
  AX_UINT8    m_day;
  AX_UINT16   m_year;
  AX_UINT8    m_hour;
  AX_UINT8    m_minute;
  AX_UINT16   m_unused;
  AX_UINT32   m_sec;

  void init(void);
};

#endif // _axData_hpp_
