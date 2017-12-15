
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHighRateCmtsChannelTblWalk_hpp_
#define _axHighRateCmtsChannelTblWalk_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSnmpMultipleGetWork.hpp"
#include "axSnmpCmtsChannelReqType.hpp"
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
 * file/class: axHighRateCmtsChannelTblWalk.hpp
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

class axHighRateCmtsChannelTblWalk : public axAbstractSnmpMultipleGetWork
{
public:

  /// data constructor
  axHighRateCmtsChannelTblWalk(axInternalCmts *);

  /// destructor
  virtual ~axHighRateCmtsChannelTblWalk();

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
  axHighRateCmtsChannelTblWalk();

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

#if 0
  /**
   *
   */
  virtual axSnmpValueBase_s * getDecodedResponse(void);
  virtual axSnmpOidsBase_s  * getInitialOids(void);
  virtual netsnmp_pdu       * createRequestPdu(void);
  virtual void                addOids(netsnmp_pdu *, axSnmpOidsBase_s *);
  virtual void                decodeResponse(netsnmp_pdu *, axSnmpRespDecodeRC_s &);
  virtual void                useResponse(void);
  virtual axSnmpOidsBase_s  * getNextOids(void);
#endif

private:

  ///
  axHighRateCmtsChannelTblWalk(const axHighRateCmtsChannelTblWalk &);

  ///
  void getValue(AX_INT32, AX_INT32, netsnmp_variable_list *);

  ///
  bool compareAndUpdate(axInternalChannel *, axInternalChannel *);

  axSnmpCmtsChannelOids_s           m_nextOids;
  axSnmpCmtsChannelResultValues_s   m_decodedResponse;
  axSnmpCmtsChannelReqType        * m_reqType;
  axInternalCmts                  * m_intCmts;
};

#endif // _axHighRateCmtsChannelTblWalk_hpp_
