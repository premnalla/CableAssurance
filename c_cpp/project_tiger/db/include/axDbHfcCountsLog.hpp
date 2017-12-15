
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbHfcCountsLog_hpp_
#define _axDbHfcCountsLog_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractCounts.hpp"

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
 * file/class: axDbHfcCountsLog.hpp
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

class axDbHfcCountsLog : public axDbAbstractCounts
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * QueryCountsLog;
  static AX_INT8 * InsertCountsLog;

  /**
   * IN: HFC Res ID
   */
  axDbHfcCountsLog(DB_RESID_t);

  /**
   * IN: HFC Res ID; CM Counts; MTA counts
   */
  axDbHfcCountsLog(DB_RESID_t, time_t, axIntCounts_t *);

  /// destructor
  virtual ~axDbHfcCountsLog();

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

  // ***********************************
  // data members begin ...
  // ***********************************

  // ***********************************
  // data members end ...
  // ***********************************

  /**
   *
   */
  virtual bool getRow(void);
  virtual bool getRow(AX_INT8 *);
  virtual bool getRows(axListBase &);
  virtual bool getRows(axListBase &, AX_INT8 *);
  virtual bool insertRow(void);
  virtual bool updateRow(void);
  virtual bool deleteRow(void);

protected:

  /// default constructor
  axDbHfcCountsLog();

private:

  // Copy disallowed
  axDbHfcCountsLog(const axDbHfcCountsLog &);

};

#endif // _axDbHfcCountsLog_hpp_