
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractSnmpSession_hpp_
#define _axAbstractSnmpSession_hpp_

//********************************************************************
// include files
//********************************************************************
#include <net-snmp/net-snmp-config.h>
#include <net-snmp/net-snmp-includes.h>
#include "axNetSnmpDataTypes.h"
#include "axAbstractSession.hpp"

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
 * file/class: axAbstractSnmpSession.hpp
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

class axAbstractSnmpSession : public axAbstractSession
{
public:

  /// destructor
  virtual ~axAbstractSnmpSession();

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

  /**
   * 
   */
  virtual netsnmp_session * getInternalSession(void)=0;

  /**
   *
   */
  virtual netsnmp_session_list * getInternalSessionList(void)=0;

protected:

  /// default constructor
  axAbstractSnmpSession();

private:

  axAbstractSnmpSession(const axAbstractSnmpSession &);

};

#endif // _axAbstractSnmpSession_hpp_
