
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbHfcCurrentCounts_hpp_
#define _axDbHfcCurrentCounts_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractCurrentCounts.hpp"

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
 * file/class: axDbHfcCurrentCounts.hpp
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

class axDbHfcCurrentCounts : public axDbAbstractCurrentCounts
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * QueryCurrentCounts;
  static AX_INT8 * UpdateCurrentCounts;
  static AX_INT8 * InsertCurrentCounts;

  /// default constructor
  axDbHfcCurrentCounts();

  /**
   * IN: CMTS Res ID
   */
  axDbHfcCurrentCounts(DB_RESID_t);

  /**
   * IN: CMTS Res ID; CM Counts; MTA counts
   */
  axDbHfcCurrentCounts(DB_RESID_t, time_t, axIntCounts_t *);

  /// destructor
  virtual ~axDbHfcCurrentCounts();

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
  virtual bool insertOrUpdateRow(void);
  virtual bool updateRow(void);
  virtual bool deleteRow(void);

protected:

private:

  // Copy disallowed
  axDbHfcCurrentCounts(const axDbHfcCurrentCounts &);

};

#endif // _axDbHfcCurrentCounts_hpp_
