
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHighRateCmtsCmStatusTblWalk_hpp_
#define _axHighRateCmtsCmStatusTblWalk_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSnmpMultipleGetWork.hpp"
#include "axSnmpCmtsCmStatusReqType.hpp"
#include "axInternalCmts.hpp"

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
 * file/class: axHighRateCmtsCmStatusTblWalk.hpp
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

class axHighRateCmtsCmStatusTblWalk : public axAbstractSnmpMultipleGetWork
{
public:

  /// data constructor
  axHighRateCmtsCmStatusTblWalk(axInternalCmts *);

  /// destructor
  virtual ~axHighRateCmtsCmStatusTblWalk();

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
  virtual bool createAndSendNextRequests(axSnmpAsyncCbReqObj *);

  /**
   * value setters
   */
  void setModemIndex(AX_INT32);

  /**
   * value getters
   */
  AX_INT8 * getMac(void);
  AX_INT8 * getIpAddress(void);
  AX_INT32  getDownstreamChannelIndex(void);
  AX_INT32  getUpstreamChannelIndex(void);
  AX_INT32  getModemIndex(void);
  AX_UINT16 getUpstreamSNR(void);
  AX_UINT16 getUpstreamPower(void);
  AX_INT8   getDocsisState(void);
  // AX_INT8   getIpAddressType(void);

protected:

  /// default constructor
  axHighRateCmtsCmStatusTblWalk();

  /**
   *
   */
  virtual bool createAndSendInitialRequests(void);

  /**
   *
   */
  virtual axSnmpOidsBase_s  * getInitialOids(void);

  /**
   *
   */
  virtual void decodeResponse(netsnmp_pdu *, axSnmpRespDecodeRC_s &);

  /**
   *
   */
  virtual void useResponse(axSnmpAsyncCbReqObj *);

private:

  axHighRateCmtsCmStatusTblWalk(const axHighRateCmtsCmStatusTblWalk &);

  void getValue(AX_INT32, AX_INT32, netsnmp_variable_list *);

  bool compareAndUpdate(axInternalCm *, axInternalCm *);

  axSnmpCmtsCmOids_s             m_nextOids;
  axSnmpCmtsCmResultValues_s     m_decodedResponse;
  axSnmpCmtsCmStatusReqType    * m_reqType;
  axInternalCmts               * m_intCmts;
  AX_INT32                       m_modemIndex;
};

#endif // _axHighRateCmtsCmStatusTblWalk_hpp_
