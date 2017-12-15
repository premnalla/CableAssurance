
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMessageManager_hpp_
#define _axMessageManager_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSubSystem.hpp"
#include "axCAMessageQueue.hpp"
#include "axAbstractCAMessage.hpp"
#include "axAbstractLock.hpp"
#include "axMessageQCollection.hpp"

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
 * file/class: axMessageManager.hpp
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

class axMessageManager : public axAbstractSubSystem
{
public:

  /// destructor
  virtual ~axMessageManager();

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
  static axMessageManager * getInstance(void);


  /**
   *
   * IN: Message Q Type, Message Topic, Owner
   */
  bool registerMessageTopic(AX_UINT8, AX_UINT8, AX_UINT8);

  /**
   *
   * IN: Message Topic, Calling sub-system ID
   */
  bool unregisterMessageTopic(AX_UINT8, AX_UINT8);


  /**
   *
   * IN: Message Topic, Sub-System ID, Queue
   * IN: Queue
   */
  // bool registerMessageQueue(AX_UINT8, AX_UINT8, axCAMessageQueue *);
  bool registerMessageQueue(axCAMessageQueue *);

  /**
   *
   * IN: Message Topic, Sub-System ID
   * IN: Queue
   *
   */
  // bool unregisterMessageQueue(AX_UINT8, AX_UINT8);
  bool unregisterMessageQueue(axCAMessageQueue *);


  /**
   *
   * IN: Message Topic, Message
   *
   */
  bool sendMessage(AX_UINT8, axAbstractCAMessage *);

  /**
   *
   * IN: Message
   *
   */
  bool sendMessage(axAbstractCAMessage *);


#if 0
  /**
   *
   *
   */
  void registerSubscriber(AX_UINT8);

  /**
   *
   *
   */
  void unregisterSubscriber(AX_UINT8);

  /**
   *
   *
   */
  void registerPublisher(AX_UINT8);

  /**
   *
   *
   */
  void unregisterPublisher(AX_UINT8);
#endif


protected:

  /// default constructor
  axMessageManager();


private:

  axMessageManager(const axMessageManager &);

  static axMessageManager * m_instance;

  axAbstractLock          * m_lock;

  // message Q collections, one for each of 255 possible topics
  axMessageQCollection    * m_msgQCollections[255];
};

#endif // _axMessageManager_hpp_
