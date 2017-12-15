
//********************************************************************
// OBSOLETED !!!
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpCmtsCmStatusTblWalk_hpp_
#define _axSnmpCmtsCmStatusTblWalk_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axSnmpDataTypes.hpp"
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
 * file/class: axSnmpCmtsCmStatusTblWalk.hpp
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

class axSnmpCmtsCmStatusTblWalk 
{
public:

  /// default constructor
  axSnmpCmtsCmStatusTblWalk(axSnmpSession *);

  /// destructor
  virtual ~axSnmpCmtsCmStatusTblWalk();

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

  void run(void);

protected:

  /// default constructor
  axSnmpCmtsCmStatusTblWalk();


private:

  axSnmpCmtsCmStatusTblWalk(const axSnmpCmtsCmStatusTblWalk &);

  void init(void);
  bool createPdu(void);
  bool addOidsToPdu(void);
  bool sendRequestAndProcessResponse(netsnmp_session *, netsnmp_pdu *, netsnmp_pdu **, axSnmpCmtsCmOids_s &);
  void getValue(AX_INT32, AX_INT32, axSnmpCmtsCmResultValues_s &, netsnmp_variable_list *);

  axSnmpCmtsCmOids_s  m_initialCmtsOids;

  axSnmpSession   * m_snmpSession;
};

#endif // _axSnmpCmtsCmStatusTblWalk_hpp_
