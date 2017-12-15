
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axSchedPriRunnableQ_hpp_
#define _axSchedPriRunnableQ_hpp_

//********************************************************************
// include files
//********************************************************************
#include <pthread.h>
#include "axObject.hpp"
#include "axAbstractCollection.hpp"
#include "axListBase.hpp"
#include "axSchedDataTypes.hpp"

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
 * file/class: axSchedPriRunnableQ.hpp
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

class axSchedPriRunnableQ : public axObject, public axAbstractCollection
{
public:

  /// default constructor 
  axSchedPriRunnableQ();

  /// destructor not allowed
  virtual ~axSchedPriRunnableQ();

  ///
  virtual bool isEmpty(void);

  ///
  virtual axObject * add(axObject *);

  ///
  virtual axObject * find(axObject *);

  ///
  virtual axObject * remove(axObject *);

  ///
  virtual axObject * remove(void);

  ///
  virtual size_t size(void);

  ///
  virtual void clear(void);

  ///
  virtual void clearAndFreeEntries(void);

protected:

  ///
  virtual void lock();

  ///
  virtual void unlock();

  /**
   * Does not lock queue. Usually called by sub classes that override
   * the corresponding (withouth the "_u") method.
   */
  axObject * find_u(axObject *);
  axObject * remove_u(axObject *);
  axObject * remove_u(void);

  /**
   * Does not lock queue. Usually called by sub classes that override
   * the corresponding (withouth the "_u") method.
   */
  void   notify(void);
  int    wait(struct timespec *);
  int    wait(void);

  virtual axAbstractIterator * createIterator(void);
  virtual void freeIterator(axAbstractIterator *);

private:

  struct consumerSupplierProtectionS {
    axListBase       list[SCHED_NUM_PRIORITIES];
    pthread_mutex_t  queueLock;
    pthread_cond_t   cond;
  };

  struct consumerSupplierProtectionS  m_queue;

  /// copy not allowed
  axSchedPriRunnableQ(const axSchedPriRunnableQ &);

};

#endif // _axSchedPriRunnableQ_hpp_
