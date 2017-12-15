
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpAsyncCbReqObj_hpp_
#define _axSnmpAsyncCbReqObj_hpp_

//********************************************************************
// include files
//********************************************************************

//********************************************************************
// definitions/macros
//********************************************************************
#include "axObject.hpp"
#include "axAbstractInternalObject.hpp"
#include "axAbstractSnmpSession.hpp"
// #include "axAbsSnmpAsyncPollWork.hpp"

//********************************************************************
// forward declerations
//********************************************************************
class axAbsSnmpAsyncPollWork;

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axSnmpAsyncCbReqObj.hpp
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

class axSnmpAsyncCbReqObj : public axObject
{
public:

  /**
   * data constructor
   */
  axSnmpAsyncCbReqObj(axAbstractInternalObject *,
                                            axAbsSnmpAsyncPollWork *);

  /// destructor
  virtual ~axSnmpAsyncCbReqObj();

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
  axAbstractInternalObject * getInternalObject(void);

  /**
   *
   */
  axAbsSnmpAsyncPollWork   * getProcessingClass(void);

  /**
   *
   */
  void setSnmpSession(axAbstractSnmpSession *);
  axAbstractSnmpSession * getSnmpSession(void);

  /**
   *
   */
  void setStateInit(void);
  void setStateReqSent(void);
  void setStateSendReqFailed(void);
  void setStateReplyReceived(void);
  void setStateTimeout(void);
  bool isReplyReceived(void);
  bool isTimeout(void);
  bool isReqSent(void);
  bool isSendReqFailed(void);

protected:

  /**
   * default constructor
   */
  axSnmpAsyncCbReqObj();


private:

  enum axSnmpRequestObjStates {
    UNKNOWN,
    INIT,
    REQ_SENT,
    SEND_REQ_FAILED,
    REPLY_RCV,
    TIMEOUT,
    LAST
  };

  /// not allowed
  axSnmpAsyncCbReqObj(const axSnmpAsyncCbReqObj &);

  AX_INT8                     m_state;
  axAbstractInternalObject  * m_intObj;
  axAbsSnmpAsyncPollWork    * m_processingClass;
  axAbstractSnmpSession     * m_snmpSession;

};

#endif // _axSnmpAsyncCbReqObj_hpp_
