
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmPerfCm_hpp_
#define _axCmPerfCm_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSnmpFloodGetWork.hpp"

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
 * file/class: axCmPerfCm.hpp
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

class axCmPerfCm : public axAbstractSnmpFloodGetWork
{
public:

  /// data constructor
  axCmPerfCm(axInternalCmts *);

  /// destructor
  virtual ~axCmPerfCm();

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

  AX_UINT16  getDownstreamSNR(void);
  AX_UINT16  getDownstreamPower(void);
  AX_UINT16  getUpstreamPower(void);
  AX_UINT32  getT1Timeouts(void);
  AX_UINT32  getT2Timeouts(void);
  AX_UINT32  getT3Timeouts(void);
  AX_UINT32  getT4Timeouts(void);

protected:

  /// default constructor
  axCmPerfCm();

  /**
   * 
   */
  virtual bool createAndSendInitialRequests(void);

#if 0 // moved to the direct parent class
  /**
   * 
   * OUT:
   *  int * - numFD's
   *  fd_set * - read fd set
   *  struct timeval * - timeout value for select() sys call
   *  int * - block (net-snmp'ism)
   */
  virtual bool setFdSet(AX_INT32 *, fd_set *, struct timeval *, AX_INT32 *);

  /**
   * 
   */
  virtual bool handleTimeout(void);
#endif

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

  axCmPerfCm(const axCmPerfCm &);

  /**
   *
   */
  void getValue(int, netsnmp_variable_list *);

  axSnmpCmPerfCmResultValues_s  m_decodedResponse;

};

#endif // _axCmPerfCm_hpp_
