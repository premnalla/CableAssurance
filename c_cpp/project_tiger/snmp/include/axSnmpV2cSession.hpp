
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpV2cSession_hpp_
#define _axSnmpV2cSession_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSnmpSession.hpp"

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
 * file/class: axSnmpV2cSession.hpp
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

class axSnmpV2cSession : public axSnmpSession
{
public:

  /// default constructor
  axSnmpV2cSession();

  /// destructor
  virtual ~axSnmpV2cSession();

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

   void setSessionV2cCommunity(AX_INT8 *);

protected:


private:

  axSnmpV2cSession(const axSnmpV2cSession &);

};

#endif // _axSnmpV2cSession_hpp_
