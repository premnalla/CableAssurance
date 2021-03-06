
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpV2cSessionHelper_hpp_
#define _axSnmpV2cSessionHelper_hpp_

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
 * file/class: axSnmpV2cSessionHelper.hpp
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

class axSnmpV2cSessionHelper : public axSnmpSessionHelper
{
public:

  /// data constructor
  axSnmpV2cSessionHelper(axSnmpSessionFactory *);

  /// destructor
  virtual ~axSnmpV2cSessionHelper();

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
  axSnmpV2cSessionHelper();


private:

  axSnmpV2cSessionHelper(const axSnmpV2cSessionHelper &);

};

#endif // _axSnmpV2cSessionHelper_hpp_
