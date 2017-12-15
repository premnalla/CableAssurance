
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractCAMessage_hpp_
#define _axAbstractCAMessage_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"
#include "axAbstractMessage.hpp"
#include "axMessageDataTypes.hpp"

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
 * file/class: axAbstractCAMessage.hpp
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

class axAbstractCAMessage : public axObject, public axAbstractMessage
{
public:

  /// destructor
  virtual ~axAbstractCAMessage();

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

  ///
  void setMessageId(AX_MSG_MSGID_TYPE);
  void setMessageTopic(AX_UINT8);
  void setMessageSourceSubSystem(AX_UINT8);
  void setMessagePayload(axMessagePayload_s *);

  ///
  AX_MSG_MSGID_TYPE    getMessageId(void);
  AX_UINT8             getMessageTopic(void);
  AX_UINT8             getMessageSourceSubSystem(void);
  axMessagePayload_s * getMessagePayload(void);

protected:

  /// default constructor
  axAbstractCAMessage();


private:

  axAbstractCAMessage(const axAbstractCAMessage &);

  axMessage_s    m_msg;
};

#endif // _axAbstractCAMessage_hpp_
