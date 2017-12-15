
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <assert.h>
#include "axMessageManager.hpp"
#include "axMultipleReaderLock.hpp"
#include "axReaderWriterLock.hpp"
#include "axWriteLockOperator.hpp"
#include "axReadLockOperator.hpp"
#include "axAbstractIterator.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axMessageManager * axMessageManager::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axMessageManager::axMessageManager() :
  m_lock(new axReaderWriterLock())
{
  memset(&m_msgQCollections, 0, sizeof(m_msgQCollections));
}


//********************************************************************
// destructor:
//********************************************************************
axMessageManager::~axMessageManager()
{
  delete m_lock;
}


//********************************************************************
// data constructor:
//********************************************************************
// axMessageManager::axMessageManager()
// {
// }


//********************************************************************
// method:
//********************************************************************
axMessageManager *
axMessageManager::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axMessageManager();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
bool 
axMessageManager::registerMessageTopic(AX_UINT8 qType, AX_UINT8 topic, 
  AX_UINT8 creatingSysId)
{
  bool ret = true;

  axWriteLockOperator wLock(m_lock);

  if (!m_msgQCollections[topic]) {
    m_msgQCollections[topic] = new axMessageQCollection(qType, 
                                                       creatingSysId);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axMessageManager::unregisterMessageTopic(AX_UINT8 topic, 
                                                AX_UINT8 callingSysId)
{
  bool ret = true;

  axWriteLockOperator wLock(m_lock);

  if (m_msgQCollections[topic] && 
      m_msgQCollections[topic]->getCreatorId() == callingSysId) {
    delete m_msgQCollections[topic];
    m_msgQCollections[topic] = NULL;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axMessageManager::registerMessageQueue(axCAMessageQueue * msgQ)
{
  bool ret;

  assert(msgQ != NULL);

  axWriteLockOperator wLock(m_lock);

  if (m_msgQCollections[msgQ->getTopic()]) {
    m_msgQCollections[msgQ->getTopic()]->add(msgQ);
    ret = true;
  } else {
    ret = false;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axMessageManager::unregisterMessageQueue(axCAMessageQueue * msgQ)
{
  bool ret = true;

  assert(msgQ != NULL);

  axWriteLockOperator wLock(m_lock);

  if (m_msgQCollections[msgQ->getTopic()]) {
    m_msgQCollections[msgQ->getTopic()]->remove(msgQ);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axMessageManager::sendMessage(AX_UINT8 topic, axAbstractCAMessage * msg)
{
  bool ret = false;

  axCAMessageQueue * msgQ;
  axAbstractIterator * iter;

  axWriteLockOperator wLock(m_lock);

  if (!m_msgQCollections[topic]) {
    goto EXIT_LABEL;
  }

  iter = m_msgQCollections[topic]->createIterator();
  
  msgQ = (axCAMessageQueue *) iter->getFirst();

  while (msgQ) {

    msgQ->add(msg);

    // advance to next Q
    msgQ = (axCAMessageQueue *) iter->getNext();
  }

  delete iter;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axMessageManager::sendMessage(axAbstractCAMessage * msg)
{
  return ( sendMessage(msg->getMessageTopic(), msg) );
}







