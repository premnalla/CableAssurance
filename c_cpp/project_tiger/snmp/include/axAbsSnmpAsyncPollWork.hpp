
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbsSnmpAsyncPollWork_hpp_
#define _axAbsSnmpAsyncPollWork_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axListFree.hpp"
#include "axAbstractInternalObject.hpp"
#include "axAbstractSnmpPollWork.hpp"
#include "axAbstractSnmpSession.hpp"
#include "axSnmpAsyncCbReqObj.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
// class axSnmpAsyncCbReqObj;

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axAbsSnmpAsyncPollWork.hpp
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

class axAbsSnmpAsyncPollWork : public axAbstractSnmpPollWork
{
public:

  /// destructor
  virtual ~axAbsSnmpAsyncPollWork();

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
   * IN:
   *   AX_INT32 - operation
   *   netsnmp_session - open session with peer
   *   AX_INT32 - request id
   *   netsnmp_pdu * - the response pdu
   *   void * - passback argument
   */
  static AX_INT32 CallbackHandler(AX_INT32,
                                  netsnmp_session *,
                                  AX_INT32,
                                  netsnmp_pdu *,
                                  void *);

  /**
   *
   */
  void setCallback(axAbstractSnmpSession *);

  /**
   *
   */
  void addCallBackReqObj(axSnmpAsyncCbReqObj *);

  /**
   *
   */
  virtual bool run(void);

  /**
   * 
   */
  void decodeAndUseResponse(netsnmp_pdu *, axSnmpAsyncCbReqObj *);

  /**
   * Specific to the leaf class
   */
  virtual bool createAndSendNextRequests(axSnmpAsyncCbReqObj *)=0;

protected:

  /// default constructor
  axAbsSnmpAsyncPollWork();

  /**
   * Specific to the leaf class
   */
  virtual bool createAndSendInitialRequests(void)=0;

  /**
   * 
   * OUT:
   *  int * - numFD's
   *  fd_set * - read fd set
   *  struct timeval * - timeout value for select() sys call
   *  int * - block (net-snmp'ism)
   */
  bool setFdSet(AX_INT32 *, fd_set *, struct timeval *, AX_INT32 *);

  /**
   * 
   */
  bool handleTimeout(void);

  /**
   * 
   */
  bool snmpRead(fd_set *);

  /**
   * Leaf class only
   */
  virtual axSnmpOidsBase_s  * getInitialOids(void)=0;

  /**
   *
   */
  bool isDoneGetting(void);
  void setDoneGetting(void);

  /**
   *
   */
  axListBase * getCbReqObjList(void);

  /**
   * Leaf class only
   */
  virtual void decodeResponse(netsnmp_pdu *, axSnmpRespDecodeRC_s &)=0;

  /**
   * Leaf class only
   */
  virtual void useResponse(axSnmpAsyncCbReqObj *)=0;

private:

  axAbsSnmpAsyncPollWork(const axAbsSnmpAsyncPollWork &);

  bool                  m_doneGetting;
  axListFree            m_cbReqObjList;
  axSnmpRespDecodeRC_s  m_responseCode;

};

#endif // _axAbsSnmpAsyncPollWork_hpp_
