
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbHfcCurrentOutage_hpp_
#define _axDbHfcCurrentOutage_hpp_

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
 * file/class: axDbHfcCurrentOutage.hpp
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

class axDbHfcCurrentOutage : public axDbAbstractCurrentStatus
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * Insert;
  static AX_INT8 * Update;

  /// default constructor
  axDbHfcCurrentOutage();

  /**
   * IN: HFC Res ID
   */
  axDbHfcCurrentOutage(DB_RESID_t);

  /**
   * IN: HFC Res ID; Internal HFC;
   */
  axDbHfcCurrentOutage(DB_RESID_t, axIntHfcNonkey_t *);

  /// destructor
  virtual ~axDbHfcCurrentOutage();

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
  axDbHfcCurrentOutage(const axDbHfcCurrentOutage &);

};

#endif // _axDbHfcCurrentOutage_hpp_
