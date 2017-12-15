
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpCmPerfCmReqType_hpp_
#define _axSnmpCmPerfCmReqType_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSnmpOneRowMultiVarReqType.hpp"
#include "axSnmpDataTypes.hpp"

//********************************************************************
// definitions/macros
//********************************************************************
#define SNMP_CM_PERF_CM_DN_SNR_OID "1.3.6.1.2.1.10.127.1.1.4.1.5"
#define SNMP_CM_PERF_CM_DN_PWR_OID "1.3.6.1.2.1.10.127.1.1.1.1.6"
#define SNMP_CM_PERF_CM_UP_SNR_OID "1.3.6.1.2.1.10.127.1.2.2.1.3"
#define SNMP_CM_PERF_CM_T1_TO_OID  "1.3.6.1.2.1.10.127.1.2.2.1.10"
#define SNMP_CM_PERF_CM_T2_TO_OID  "1.3.6.1.2.1.10.127.1.2.2.1.11"
#define SNMP_CM_PERF_CM_T3_TO_OID  "1.3.6.1.2.1.10.127.1.2.2.1.12"
#define SNMP_CM_PERF_CM_T4_TO_OID  "1.3.6.1.2.1.10.127.1.2.2.1.13"

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axSnmpCmPerfCmReqType.hpp
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

class axSnmpCmPerfCmReqType : public axSnmpOneRowMultiVarReqType
{
public:

  /// destructor
  virtual ~axSnmpCmPerfCmReqType();

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

  static axSnmpCmPerfCmReqType * getInstance(void);

  axSnmpOidsBase_s * getOids(void);

protected:

  /// default constructor
  axSnmpCmPerfCmReqType();


private:

  axSnmpCmPerfCmReqType(const axSnmpCmPerfCmReqType &);

  static axSnmpCmPerfCmReqType * m_instance;

  axSnmpCmPerfCmGetNextOid_s  m_cmOids;
};

#endif // _axSnmpCmPerfCmReqType_hpp_
