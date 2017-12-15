
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbHfcOutageLog_hpp_
#define _axDbHfcOutageLog_hpp_

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
 * file/class: axDbHfcOutageLog.hpp
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

class axDbHfcOutageLog : public axDbAbstractStatus
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
  axDbHfcOutageLog(DB_RESID_t);

  /**
   * IN: HFC Res ID; Internal HFC;
   */
  axDbHfcOutageLog(DB_RESID_t, axIntHfcNonkey_t *);

  /// destructor
  virtual ~axDbHfcOutageLog();

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

  AX_UINT8    m_outage;

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
  axDbHfcOutageLog();

private:

  // Copy disallowed
  axDbHfcOutageLog(const axDbHfcOutageLog &);

};

#endif // _axDbHfcOutageLog_hpp_
