
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpCmtsChannelReqType_hpp_
#define _axSnmpCmtsChannelReqType_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSnmpMultiRowMultiVarReqType.hpp"
#include "axSnmpDataTypes.hpp"

//********************************************************************
// definitions/macros
//********************************************************************
#define SNMP_CMTS_CHNL_IF_INDEX_OID "1.3.6.1.2.1.2.2.1.1"
#define SNMP_CMTS_CHNL_IF_DESCR_OID "1.3.6.1.2.1.2.2.1.2"
#define SNMP_CMTS_CHNL_IF_TYPE_OID  "1.3.6.1.2.1.2.2.1.3"

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axSnmpCmtsChannelReqType.hpp
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

class axSnmpCmtsChannelReqType : public axSnmpMultiRowMultiVarReqType
{
public:

  /// destructor
  virtual ~axSnmpCmtsChannelReqType();

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

  static axSnmpCmtsChannelReqType * getInstance(void);

  void getOids(axSnmpCmtsChannelOids_s &);
  axSnmpOidsBase_s * getOids(void);

protected:

  /// default constructor
  axSnmpCmtsChannelReqType();


private:

  axSnmpCmtsChannelReqType(const axSnmpCmtsChannelReqType &);

  static axSnmpCmtsChannelReqType * m_instance;

  axSnmpCmtsChannelOids_s  m_cmtsOids;
};

#endif // _axSnmpCmtsChannelReqType_hpp_
