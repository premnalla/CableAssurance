
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpDownChannelPerfReqType_hpp_
#define _axSnmpDownChannelPerfReqType_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSnmpMultiRowMultiVarReqType.hpp"
#include "axSnmpDataTypes.hpp"

//********************************************************************
// definitions/macros
//********************************************************************
#define SNMP_DWN_CHNL_POWER_OID "1.3.6.1.2.1.10.127.1.1.1.1.6"

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axSnmpDownChannelPerfReqType.hpp
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

class axSnmpDownChannelPerfReqType : public axSnmpMultiRowMultiVarReqType
{
public:

  /// destructor
  virtual ~axSnmpDownChannelPerfReqType();

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

  static axSnmpDownChannelPerfReqType * getInstance(void);

  axSnmpOidsBase_s * getOids(void);

protected:

  /// default constructor
  axSnmpDownChannelPerfReqType();


private:

  axSnmpDownChannelPerfReqType(const axSnmpDownChannelPerfReqType &);

  static axSnmpDownChannelPerfReqType * m_instance;

  axSnmpDownChannelPerfOid_s  m_channelOids;
};

#endif // _axSnmpDownChannelPerfReqType_hpp_
