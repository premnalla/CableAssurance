
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpCmtsCmStatusReqType_hpp_
#define _axSnmpCmtsCmStatusReqType_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSnmpMultiRowMultiVarReqType.hpp"
#include "axSnmpDataTypes.hpp"

//********************************************************************
// definitions/macros
//********************************************************************
#define SNMP_CMTS_CM_INDEX_OID   "1.3.6.1.2.1.10.127.1.3.3.1.1"
#define SNMP_CMTS_CM_MACADDR_OID "1.3.6.1.2.1.10.127.1.3.3.1.2"
#define SNMP_CMTS_CM_IPADDR_OID  "1.3.6.1.2.1.10.127.1.3.3.1.3"
#define SNMP_CMTS_CM_DWNCHNL_OID "1.3.6.1.2.1.10.127.1.3.3.1.4"
#define SNMP_CMTS_CM_UPCHNL_OID  "1.3.6.1.2.1.10.127.1.3.3.1.5"
#define SNMP_CMTS_CM_STATUS_OID  "1.3.6.1.2.1.10.127.1.3.3.1.9"
#define SNMP_CMTS_CM_UP_PWR_OID  "1.3.6.1.2.1.10.127.1.3.3.1.6"
#define SNMP_CMTS_CM_SNR_OID     "1.3.6.1.2.1.10.127.1.3.3.1.13"

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axSnmpCmtsCmStatusReqType.hpp
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

class axSnmpCmtsCmStatusReqType : public axSnmpMultiRowMultiVarReqType
{
public:

  /// destructor
  virtual ~axSnmpCmtsCmStatusReqType();

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

  static axSnmpCmtsCmStatusReqType * getInstance(void);

  void getOids(axSnmpCmtsCmOids_s &);

  /**
   * IN:
   *   Modem Index;
   * OUT:
   *  oids
   */
  void getOids(AX_INT32, axSnmpCmtsCmOids_s &);

  axSnmpOidsBase_s * getOids(void);

protected:

  /// default constructor
  axSnmpCmtsCmStatusReqType();


private:

  axSnmpCmtsCmStatusReqType(const axSnmpCmtsCmStatusReqType &);

  static axSnmpCmtsCmStatusReqType * m_instance;

  axSnmpCmtsCmOids_s  m_cmtsOids;
};

#endif // _axSnmpCmtsCmStatusReqType_hpp_
