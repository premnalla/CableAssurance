
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbTopoLookupGenMta_hpp_
#define _axDbTopoLookupGenMta_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbAbstractTopoLookup.hpp"

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
 * file/class: axDbTopoLookupGenMta.hpp
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

class axDbTopoLookupGenMta : public axDbAbstractTopoLookup
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * Update;
  static AX_INT8 * Insert;

  /**
   * IN: Topo container ID, Res ID
   */
  axDbTopoLookupGenMta(AX_UINT16, DB_RESID_t);

  /**
   * IN: String ID
   */
  axDbTopoLookupGenMta(const AX_INT8 *);

  /// default constructor
  axDbTopoLookupGenMta();

  /// destructor
  virtual ~axDbTopoLookupGenMta();

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
  axDbTopoLookupGenMta(const axDbTopoLookupGenMta &);

};

#endif // _axDbTopoLookupGenMta_hpp_
