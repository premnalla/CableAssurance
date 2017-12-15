
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbHfcCurrentStatus_hpp_
#define _axDbHfcCurrentStatus_hpp_

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
 * file/class: axDbHfcCurrentStatus.hpp
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

class axDbHfcCurrentStatus : public axDbAbstractCurrentStatus
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * Insert;
  static AX_INT8 * Update;

  /// default constructor
  axDbHfcCurrentStatus();

  /**
   * IN: HFC Res ID
   */
  axDbHfcCurrentStatus(DB_RESID_t);

  /**
   * IN: HFC Res ID; Internal HFC;
   */
  axDbHfcCurrentStatus(DB_RESID_t, time_t, axIntHfcNonkey_t *);

  /// destructor
  virtual ~axDbHfcCurrentStatus();

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

private:

  // Copy disallowed
  axDbHfcCurrentStatus(const axDbHfcCurrentStatus &);

};

#endif // _axDbHfcCurrentStatus_hpp_
