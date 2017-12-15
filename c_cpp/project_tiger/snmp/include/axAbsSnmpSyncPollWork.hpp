
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbsSnmpSyncPollWork_hpp_
#define _axAbsSnmpSyncPollWork_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSnmpPollWork.hpp"
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
 * file/class: axAbsSnmpSyncPollWork.hpp
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

class axAbsSnmpSyncPollWork : public axAbstractSnmpPollWork
{
public:

  /// destructor
  virtual ~axAbsSnmpSyncPollWork();

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
  axSnmpSession * getSnmpSession(void);

protected:

  /// data constructor
  axAbsSnmpSyncPollWork(axSnmpSession *);

  /// default constructor
  axAbsSnmpSyncPollWork();

  /**
   *
   */
  virtual axSnmpValueBase_s * getDecodedResponse(void)=0;
  virtual axSnmpOidsBase_s  * getInitialOids(void)=0;
  virtual netsnmp_pdu       * createRequestPdu(void)=0;
  virtual void                addOids(netsnmp_pdu *, axSnmpOidsBase_s *)=0;
  virtual void                decodeResponse(netsnmp_pdu *, axSnmpRespDecodeRC_s &)=0;
  virtual void                useResponse(void)=0;

  virtual void startWork(void);
  virtual void endWork(void);
  bool sendRequestAndGetResponse(netsnmp_session *, netsnmp_pdu *, netsnmp_pdu **);

  void setSnmpSession(axSnmpSession *);

private:

  axAbsSnmpSyncPollWork(const axAbsSnmpSyncPollWork &);

  axSnmpSession * m_snmpSession;
};

#endif // _axAbsSnmpSyncPollWork_hpp_
