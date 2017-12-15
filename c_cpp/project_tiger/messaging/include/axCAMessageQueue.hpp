
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCAMessageQueue_hpp_
#define _axCAMessageQueue_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axConsumerSupplierQueue.hpp"
#include "axAbstractMessageQueue.hpp"

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
 * file/class: axCAMessageQueue.hpp
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

class axCAMessageQueue : public axAbstractMessageQueue, public axConsumerSupplierQueue
{
public:

  /**
   *
   * IN: Message Topic, Subscriber Sub-System ID
   *
   */
  axCAMessageQueue(AX_UINT8, AX_UINT8);

  /// destructor
  virtual ~axCAMessageQueue();

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
   * Override from axObject
   *
   * @return
   * @see
   */
  virtual bool isKeyEqual(axObject *);

  AX_UINT8    getTopic(void);
  AX_UINT8    getSubscriberSubSystem(void);

protected:

  /// default constructor
  axCAMessageQueue();


private:

  axCAMessageQueue(const axCAMessageQueue &);

  AX_UINT8    m_topic;
  AX_UINT8    m_subscriberSubSystem;

};

#endif // _axCAMessageQueue_hpp_
