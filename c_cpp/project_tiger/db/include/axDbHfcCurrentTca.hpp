
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbHfcCurrentTca_hpp_
#define _axDbHfcCurrentTca_hpp_

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
 * file/class: axDbHfcCurrentTca.hpp
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

class axDbHfcCurrentTca : public axDbAbstractCurrentStatus
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * Insert;
  static AX_INT8 * Update;

  /// default constructor
  axDbHfcCurrentTca();

  /**
   * IN: HFC Res ID
   */
  axDbHfcCurrentTca(DB_RESID_t);

  /**
   * IN: HFC Res ID; Internal HFC;
   */
  axDbHfcCurrentTca(DB_RESID_t, time_t, axIntHfcNonkey_t *);

  /// destructor
  virtual ~axDbHfcCurrentTca();

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
  axDbHfcCurrentTca(const axDbHfcCurrentTca &);

};

#endif // _axDbHfcCurrentTca_hpp_
