
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axConsumerSupplierQueue_hpp_
#define _axConsumerSupplierQueue_hpp_

//********************************************************************
// include files
//********************************************************************
#include <pthread.h>
#include "axObject.hpp"
#include "axAbstractCollection.hpp"
#include "axListBase.hpp"

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
 * file/class: axConsumerSupplierQueue.hpp
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

class axConsumerSupplierQueue : public axObject, public axAbstractCollection
{
public:

  /// default constructor 
  axConsumerSupplierQueue();

  /// destructor not allowed
  virtual ~axConsumerSupplierQueue();

  ///
  virtual bool isEmpty(void);

  ///
  virtual axObject * add(axObject *);
  // virtual void add(axObject *);

  ///
  virtual axObject * add(axObject *, axListBase::listType_t::iterator &);
  // virtual void add(axObject *, axListBase::listType_t::iterator &);

  ///
  virtual axObject * getFirst(void);

  ///
  virtual axObject * getFirstWaiting(void);

  ///
  virtual axObject * getLast(void);

  ///
  virtual axObject * find(axObject *);

  ///
  virtual axObject * remove(axObject *);

  ///
  virtual axObject * remove(axListBase::listType_t::iterator &);

  ///
  virtual axObject * remove(void);

  ///
  virtual size_t size(void);

  ///
  virtual void clear(void);

  ///
  virtual void clearAndFreeEntries(void);

  ///
  virtual int timedWait(time_t);

protected:

  ///
  virtual void lock();

  ///
  virtual void unlock();

  /**
   * Does not lock queue. Usually called by sub classes that override
   * the corresponding (withouth the "_u") method.
   */
  bool isEmpty_u(void);
  axObject * add_u(axObject *);
  // void add_u(axObject *);
  axObject * add_u(axObject *, axListBase::listType_t::iterator &);
  // void add_u(axObject *, axListBase::listType_t::iterator &);
  axObject * getFirst_u(void);
  axObject * getFirstWaiting_u(void);
  axObject * getLast_u(void);
  axObject * find_u(axObject *);
  axObject * remove_u(axObject *);
  axObject * remove_u(axListBase::listType_t::iterator &);
  axObject * remove_u(void);
  size_t size_u(void);
  void clear_u(void);
  void clearAndFreeEntries_u(void);

  /**
   * Does not lock queue. Usually called by sub classes that override
   * the corresponding (withouth the "_u") method.
   */
  void notify(void);
  int wait(struct timespec *);
  int wait(void);

  /// MT-Unsafe !!!
  virtual axListBase::listType_t::iterator begin(void);

  /// MT-Unsafe !!!
  virtual axListBase::listType_t::iterator end(void);

  virtual axAbstractIterator * createIterator(void);
  virtual void freeIterator(axAbstractIterator *);

private:

  struct consumerSupplierProtectionS {
    axListBase       list;
    pthread_mutex_t  queueLock;
    pthread_cond_t   cond;
  };

  struct consumerSupplierProtectionS  m_queue;

  /// copy not allowed
  axConsumerSupplierQueue(const axConsumerSupplierQueue &);

};

#endif // _axConsumerSupplierQueue_hpp_
