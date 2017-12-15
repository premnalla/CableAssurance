
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbChannelCurrentCounts_hpp_
#define _axDbChannelCurrentCounts_hpp_

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
 * file/class: axDbChannelCurrentCounts.hpp
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

class axDbChannelCurrentCounts : public axDbAbstractCurrentCounts
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * QueryCurrentCounts;
  static AX_INT8 * UpdateCurrentCounts;
  static AX_INT8 * InsertCurrentCounts;

  /// default constructor
  axDbChannelCurrentCounts();

  /**
   * IN: CMTS Res ID
   */
  axDbChannelCurrentCounts(DB_RESID_t);

  /**
   * IN: CMTS Res ID; CM Counts; MTA counts
   */
  axDbChannelCurrentCounts(DB_RESID_t, time_t, axIntCounts_t *);

  /// destructor
  virtual ~axDbChannelCurrentCounts();

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
  axDbChannelCurrentCounts(const axDbChannelCurrentCounts &);

};

#endif // _axDbChannelCurrentCounts_hpp_
