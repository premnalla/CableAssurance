
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpRequestObj_hpp_
#define _axSnmpRequestObj_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"
#include "axAbstractInternalObject.hpp"
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
 * file/class: axSnmpRequestObj.hpp
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

class axSnmpRequestObj : public axObject
{
public:

  /**
   * default constructor
   */
  axSnmpRequestObj(axAbstractInternalObject *, axSnmpSession *);

  /**
   * default constructor
   */
  axSnmpRequestObj(axAbstractInternalObject *);

  /// destructor
  virtual ~axSnmpRequestObj();

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
   * overloaded from base axObject class
   */
  virtual bool isKeyEqual(axObject *);

  ///
  void setStateInit(void);
  void setStateReqSent(void);
  void setStateReplyReceived(void);
  void setStateTimeout(void);
  bool isReplyReceived(void);
  bool isTimeout(void);
  bool isReqSent(void);

  /**
   * 
   */
  axObject * getObject(void);

  /**
   * 
   */
  axSnmpSession * getSession(void);

protected:

  /**
   * default constructor
   */
  axSnmpRequestObj();

private:

  enum axSnmpRequestObjStates {
    UNKNOWN,
    INIT,
    REQ_SENT,
    REPLY_RCV,
    TIMEOUT,
    LAST
  };

  /// not allowed
  axSnmpRequestObj(const axSnmpRequestObj &);

  AX_INT8           m_state;
  axObject        * m_intObj;
  axSnmpSession   * m_session;
};

#endif // _axSnmpRequestObj_hpp_
