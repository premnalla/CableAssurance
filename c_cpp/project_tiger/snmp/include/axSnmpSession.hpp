
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpSession_hpp_
#define _axSnmpSession_hpp_

//********************************************************************
// include files
//********************************************************************
#include <net-snmp/net-snmp-config.h>
#include <net-snmp/net-snmp-includes.h>
#include "axAbstractSnmpSession.hpp"

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
 * file/class: axSnmpSession.hpp
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

class axSnmpSession : public axAbstractSnmpSession
{
public:

  /**
   * 
   */
  axSnmpSession(netsnmp_session_list *);

  /// destructor
  virtual ~axSnmpSession();

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
  virtual bool open(void);

  /**
   *
   */
  virtual void close(void);

  /**
   *
   */
  virtual bool isOpen(void);

  /**
   * 
   */
  virtual netsnmp_session * getInternalSession(void);

  /**
   *
   */
  virtual netsnmp_session_list * getInternalSessionList(void);

protected:

  /// default constructor
  axSnmpSession();

private:

  axSnmpSession(const axSnmpSession &);

  netsnmp_session_list * m_openSession;
};

#endif // _axSnmpSession_hpp_
