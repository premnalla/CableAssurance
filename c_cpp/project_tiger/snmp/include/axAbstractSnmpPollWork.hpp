
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractSnmpPollWork_hpp_
#define _axAbstractSnmpPollWork_hpp_

//********************************************************************
// include files
//********************************************************************
#include <net-snmp/net-snmp-config.h>
#include <net-snmp/net-snmp-includes.h>
#include "axSnmpDataTypes.hpp"
#include "axAll.h"

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
 * file/class: axAbstractSnmpPollWork.hpp
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

class axAbstractSnmpPollWork 
{
public:

  /// destructor
  virtual ~axAbstractSnmpPollWork();

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
  virtual bool run(void)=0;

  /**
   * 
   */
  void freeResponse(netsnmp_pdu **);

protected:

  /// default constructor
  axAbstractSnmpPollWork();

private:

  axAbstractSnmpPollWork(const axAbstractSnmpPollWork &);

};

#endif // _axAbstractSnmpPollWork_hpp_
