
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbMtaPingStatusLog_hpp_
#define _axDbMtaPingStatusLog_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractStatus.hpp"
#include "axListBase.hpp"
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
 * file/class: axDbMtaPingStatusLog.hpp
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

class axDbMtaPingStatusLog : public axDbAbstractStatus
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * Insert;

  /**
   * IN: (e)MTA Res ID
   */
  axDbMtaPingStatusLog(DB_RESID_t);

  /**
   * IN: (e)MTA Res ID;
   * IN: time_t (time in seconds since 01/1/70
   * IN: Internal (e)MTA;
   */
  axDbMtaPingStatusLog(DB_RESID_t, time_t, axIntGenMtaNonkey_t *);

  /// destructor
  virtual ~axDbMtaPingStatusLog();

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

  AX_UINT8    m_pingState;

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
  axDbMtaPingStatusLog();

private:

  // Copy disallowed
  axDbMtaPingStatusLog(const axDbMtaPingStatusLog &);

};

#endif // _axDbMtaPingStatusLog_hpp_
