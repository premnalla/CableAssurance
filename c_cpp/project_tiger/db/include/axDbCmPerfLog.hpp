
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbCmPerfLog_hpp_
#define _axDbCmPerfLog_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractCmPerf.hpp"

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
 * file/class: axDbCmPerfLog.hpp
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

class axDbCmPerfLog : public axDbAbstractCmPerf
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * Insert;

  /**
   * IN: CM Res ID
   */
  axDbCmPerfLog(DB_RESID_t);

  /**
   * IN: CM Res ID;
   * IN: time_t (seconds since 01/1/70
   * IN: Internal CM;
   */
  axDbCmPerfLog(DB_RESID_t, time_t, axIntCmNonkey_t *);

  /// destructor
  virtual ~axDbCmPerfLog();

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
  axDbCmPerfLog();

private:

  // Copy disallowed
  axDbCmPerfLog(const axDbCmPerfLog &);

};

#endif // _axDbCmPerfLog_hpp_
