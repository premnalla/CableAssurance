
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbCmtsCounts_hpp_
#define _axDbCmtsCounts_hpp_

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
 * file/class: axDbCmtsCounts.hpp
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

class axDbCmtsCounts : public axDbAbstractCounts
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
  axDbCmtsCounts(DB_RESID_t);

  /**
   * IN: CMTS Res ID; CM Counts; MTA counts
   */
  axDbCmtsCounts(DB_RESID_t, axIntCounts_t *);

  /// destructor
  virtual ~axDbCmtsCounts();

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
  axDbCmtsCounts();

  ///
  virtual void getInsertCurrentSql(AX_INT8 *);
  virtual void getUpdateCurrentSql(AX_INT8 *);
  virtual void getInsertLogSql(AX_INT8 *);

private:

  // Copy disallowed
  axDbCmtsCounts(const axDbCmtsCounts &);

};

#endif // _axDbCmtsCounts_hpp_
