
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbHfcTcaLog_hpp_
#define _axDbHfcTcaLog_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractStatus.hpp"
#include "axInternalDsTypes.hpp"

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
 * file/class: axDbHfcTcaLog.hpp
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

class axDbHfcTcaLog : public axDbAbstractStatus
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * Insert;

  /**
   * IN: HFC Res ID
   */
  axDbHfcTcaLog(DB_RESID_t);

  /**
   * IN: HFC Res ID;
   * IN: time_t (time in seconds since 01/1/70
   * IN: Internal HFC;
   */
  axDbHfcTcaLog(DB_RESID_t, time_t, axIntHfcNonkey_t *);

  /// destructor
  virtual ~axDbHfcTcaLog();

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

  AX_UINT8    m_tca;

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
  axDbHfcTcaLog();

private:

  // Copy disallowed
  axDbHfcTcaLog(const axDbHfcTcaLog &);

};

#endif // _axDbHfcTcaLog_hpp_
