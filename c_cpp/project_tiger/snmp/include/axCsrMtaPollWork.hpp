
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCsrMtaPollWork_hpp_
#define _axCsrMtaPollWork_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axHRMtaPollWork.hpp"


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
 * file/class: axCsrMtaPollWork.hpp
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

class axCsrMtaPollWork : public axHRMtaPollWork
{
public:

  /// data constructor
  axCsrMtaPollWork(axInternalCmts *);

  /// destructor
  virtual ~axCsrMtaPollWork();

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

protected:

  /// default constructor
  axCsrMtaPollWork();

  // virtual bool createAndSendInitialRequests(void);

  // virtual axSnmpOidsBase_s  * getInitialOids(void);

  // virtual void decodeResponse(netsnmp_pdu *, axSnmpRespDecodeRC_s &);

  virtual void useResponse(axSnmpAsyncCbReqObj *);

private:

  axCsrMtaPollWork(const axCsrMtaPollWork &);

  // axSnmpHRMtaPollResultValues_s  m_decodedResponse;

  // void getValue(int, netsnmp_variable_list *);

};

#endif // _axCsrMtaPollWork_hpp_
