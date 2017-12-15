
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpMultipleRowsRequestType_hpp_
#define _axSnmpMultipleRowsRequestType_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSnmpRequestType.hpp"

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
 * file/class: axSnmpMultipleRowsRequestType.hpp
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

class axSnmpMultipleRowsRequestType : public axAbstractSnmpRequestType
{
public:

  /// destructor
  virtual ~axSnmpMultipleRowsRequestType();

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
  axSnmpMultipleRowsRequestType();


private:

  axSnmpMultipleRowsRequestType(const axSnmpMultipleRowsRequestType &);

};

#endif // _axSnmpMultipleRowsRequestType_hpp_
