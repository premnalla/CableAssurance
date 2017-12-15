
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCsrCmPoll_hpp_
#define _axCsrCmPoll_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axCmPerfCm.hpp"

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
 * file/class: axCsrCmPoll.hpp
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

class axCsrCmPoll : public axCmPerfCm
{
public:

  /// data constructor
  axCsrCmPoll(axInternalCmts *);

  /// destructor
  virtual ~axCsrCmPoll();

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
  axCsrCmPoll();

  // virtual bool createAndSendInitialRequests(void);

  // virtual axSnmpOidsBase_s  * getInitialOids(void);

  // virtual void decodeResponse(netsnmp_pdu *, axSnmpRespDecodeRC_s &);

  /**
   *
   */
  virtual void useResponse(axSnmpAsyncCbReqObj *);

private:

  axCsrCmPoll(const axCsrCmPoll &);

};

#endif // _axCsrCmPoll_hpp_
