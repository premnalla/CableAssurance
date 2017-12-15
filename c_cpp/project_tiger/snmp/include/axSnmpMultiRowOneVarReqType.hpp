
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpMultiRowOneVarReqType_hpp_
#define _axSnmpMultiRowOneVarReqType_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSnmpMultipleRowsRequestType.hpp"

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
 * file/class: axSnmpMultiRowOneVarReqType.hpp
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

class axSnmpMultiRowOneVarReqType : public axSnmpMultipleRowsRequestType
{
public:

  /// destructor
  virtual ~axSnmpMultiRowOneVarReqType();

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
  axSnmpMultiRowOneVarReqType();


private:

  axSnmpMultiRowOneVarReqType(const axSnmpMultiRowOneVarReqType &);

};

#endif // _axSnmpMultiRowOneVarReqType_hpp_
