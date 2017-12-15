
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpOneRowRequestType_hpp_
#define _axSnmpOneRowRequestType_hpp_

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
 * file/class: axSnmpOneRowRequestType.hpp
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

class axSnmpOneRowRequestType : public axAbstractSnmpRequestType
{
public:

  /// destructor
  virtual ~axSnmpOneRowRequestType();

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
  axSnmpOneRowRequestType();


private:

  axSnmpOneRowRequestType(const axSnmpOneRowRequestType &);

};

#endif // _axSnmpOneRowRequestType_hpp_
