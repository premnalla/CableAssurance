
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpV3SessionHelper_hpp_
#define _axSnmpV3SessionHelper_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSnmpSessionHelper.hpp"
#include "axSnmpSessionFactory.hpp"

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
 * file/class: axSnmpV3SessionHelper.hpp
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

class axSnmpV3SessionHelper : public axSnmpSessionHelper
{
public:

  /// data constructor
  axSnmpV3SessionHelper(axSnmpSessionFactory *);

  /// destructor
  virtual ~axSnmpV3SessionHelper();

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

protected:

  /// default constructor
  axSnmpV3SessionHelper();


private:

  axSnmpV3SessionHelper(const axSnmpV3SessionHelper &);

};

#endif // _axSnmpV3SessionHelper_hpp_
