
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHRMtaPollWork_hpp_
#define _axHRMtaPollWork_hpp_

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
 * file/class: axHRMtaPollWork.hpp
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

class axHRMtaPollWork : public axAbstractSnmpFloodGetWork
{
public:

  /// data constructor
  axHRMtaPollWork(axInternalCmts *);

  /// destructor
  virtual ~axHRMtaPollWork();

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
   *
   */
  AX_UINT8 getProvStatus(void);

protected:

  /// default constructor
  axHRMtaPollWork();

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

  axHRMtaPollWork(const axHRMtaPollWork &);

  axSnmpHRMtaPollResultValues_s  m_decodedResponse;

  /**
   *
   */
  void getValue(int, netsnmp_variable_list *);

};

#endif // _axHRMtaPollWork_hpp_
