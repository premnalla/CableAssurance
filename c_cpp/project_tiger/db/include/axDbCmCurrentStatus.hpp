
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbCmCurrentStatus_hpp_
#define _axDbCmCurrentStatus_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractCurrentStatus.hpp"
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
 * file/class: axDbCmCurrentStatus.hpp
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

class axDbCmCurrentStatus : public axDbAbstractCurrentStatus
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * Insert;
  static AX_INT8 * Update;

  /// default constructor
  axDbCmCurrentStatus();

  /**
   * IN: (e)MTA Res ID
   */
  axDbCmCurrentStatus(DB_RESID_t);

  /**
   * IN: (e)MTA Res ID; Internal (e)MTA;
   */
  axDbCmCurrentStatus(DB_RESID_t, time_t, axIntCmNonkey_t *);

  /// destructor
  virtual ~axDbCmCurrentStatus();

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

  // AX_UINT32   m_onlineTime;
  // AX_UINT32   m_offlineTime;
  // AX_UINT8    m_docsisState;

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

private:

  // Copy disallowed
  axDbCmCurrentStatus(const axDbCmCurrentStatus &);

};

#endif // _axDbCmCurrentStatus_hpp_
