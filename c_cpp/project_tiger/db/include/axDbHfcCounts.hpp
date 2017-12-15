
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbHfcCounts_hpp_
#define _axDbHfcCounts_hpp_

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
 * file/class: axDbHfcCounts.hpp
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

class axDbHfcCounts : public axDbAbstractCounts
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * UpdateCurrentCounts;
  static AX_INT8 * InsertCurrentCounts;
  static AX_INT8 * InsertCountsLog;

  /**
   * IN: CMTS Res ID
   */
  axDbHfcCounts(DB_RESID_t);

  /**
   * IN: CMTS Res ID; CM Counts; MTA counts
   */
  axDbHfcCounts(DB_RESID_t, axIntCounts_t *);

  /// destructor
  virtual ~axDbHfcCounts();

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
  // virtual bool getRow(void);
  // virtual bool getRow(AX_INT8 *);
  // virtual bool getRows(axListBase &);
  // virtual bool getRows(axListBase &, AX_INT8 *);
  // virtual bool insertRow(void);
  // virtual bool updateRow(void);
  // virtual bool deleteRow(void);

protected:

  /// default constructor
  axDbHfcCounts();

  ///
  virtual void getInsertCurrentSql(AX_INT8 *);
  virtual void getUpdateCurrentSql(AX_INT8 *);
  virtual void getInsertLogSql(AX_INT8 *);

private:

  // Copy disallowed
  axDbHfcCounts(const axDbHfcCounts &);

};

#endif // _axDbHfcCounts_hpp_
