
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpOneRowOneVarReqType_hpp_
#define _axSnmpOneRowOneVarReqType_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSnmpOneRowRequestType.hpp"

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
 * file/class: axSnmpOneRowOneVarReqType.hpp
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

class axSnmpOneRowOneVarReqType : public axSnmpOneRowRequestType
{
public:

  /// destructor
  virtual ~axSnmpOneRowOneVarReqType();

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
  axSnmpOneRowOneVarReqType();


private:

  axSnmpOneRowOneVarReqType(const axSnmpOneRowOneVarReqType &);

};

#endif // _axSnmpOneRowOneVarReqType_hpp_
