
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpHRMtaReqType_hpp_
#define _axSnmpHRMtaReqType_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSnmpOneRowMultiVarReqType.hpp"
#include "axSnmpDataTypes.hpp"

//********************************************************************
// definitions/macros
//********************************************************************
#define SNMP_MTA_PROV_STATUS_OID "1.3.6.1.4.1.4491.2.2.1.1.1.9"

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axSnmpHRMtaReqType.hpp
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

class axSnmpHRMtaReqType : public axSnmpOneRowMultiVarReqType
{
public:

  /// destructor
  virtual ~axSnmpHRMtaReqType();

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

  static axSnmpHRMtaReqType * getInstance(void);

  axSnmpOidsBase_s * getOids(void);

protected:

  /// default constructor
  axSnmpHRMtaReqType();


private:

  axSnmpHRMtaReqType(const axSnmpHRMtaReqType &);

  static axSnmpHRMtaReqType * m_instance;

  axSnmpHRMtaPollOids_s  m_mtaOids;
};

#endif // _axSnmpHRMtaReqType_hpp_
