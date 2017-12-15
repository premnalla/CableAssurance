
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCsrCmtsCmPoll_hpp_
#define _axCsrCmtsCmPoll_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axHighRateCmtsCmStatusTblWalk.hpp"

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
 * file/class: axCsrCmtsCmPoll.hpp
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

class axCsrCmtsCmPoll : public axHighRateCmtsCmStatusTblWalk
{
public:

  /// data constructor
  axCsrCmtsCmPoll(axInternalCmts *);

  /// destructor
  virtual ~axCsrCmtsCmPoll();

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
  axCsrCmtsCmPoll();

  // virtual bool createAndSendInitialRequests(void);

  // virtual axSnmpOidsBase_s  * getInitialOids(void);

  // virtual void decodeResponse(netsnmp_pdu *, axSnmpRespDecodeRC_s &);

  /**
   *
   */
  virtual void useResponse(axSnmpAsyncCbReqObj *);

private:

  axCsrCmtsCmPoll(const axCsrCmtsCmPoll &);

};

#endif // _axCsrCmtsCmPoll_hpp_
