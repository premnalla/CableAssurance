//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axConsumerSupplierQueue.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

//********************************************************************
// default constructor:
//********************************************************************
axConsumerSupplierQueue::axConsumerSupplierQueue()
{
  pthread_mutex_init(&m_queue.queueLock, NULL);
  pthread_cond_init(&m_queue.cond, NULL);
}

//********************************************************************
// destructor:
//********************************************************************
axConsumerSupplierQueue::~axConsumerSupplierQueue()
{
  clearAndFreeEntries();
  pthread_mutex_destroy(&m_queue.queueLock);
  pthread_cond_destroy(&m_queue.cond);
}

//********************************************************************
// data constructor:
//********************************************************************
// axConsumerSupplierQueue::axConsumerSupplierQueue()
// {
// }


//********************************************************************
// method:
//********************************************************************
void
axConsumerSupplierQueue::lock(void)
{
  pthread_mutex_lock(&m_queue.queueLock);
}


//********************************************************************
// method:
//********************************************************************
void
axConsumerSupplierQueue::unlock(void)
{
  pthread_mutex_unlock(&m_queue.queueLock);
}


//********************************************************************
// method:
//********************************************************************
int
axConsumerSupplierQueue::wait(void)
{
  // lock();

  int ret = pthread_cond_wait(&m_queue.cond, &m_queue.queueLock);

  // unlock();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
int
axConsumerSupplierQueue::wait(struct timespec * timeSpec)
{
  // lock();

  int ret = 
    pthread_cond_timedwait(&m_queue.cond, &m_queue.queueLock, timeSpec);

  // unlock();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axConsumerSupplierQueue::notify(void)
{
  // lock();

  pthread_cond_broadcast(&m_queue.cond);

  // unlock();
}


//********************************************************************
// method: add
//********************************************************************
// void
axObject *
axConsumerSupplierQueue::add(axObject * o)
{
  lock();

  add_u(o);

  notify();

  unlock();

  return (o);
}


//********************************************************************
// method: add
// description: Does not lock the queue. Usually called from sub classes
//              that override the "add(axObject *) method.
//********************************************************************
// void
axObject *
axConsumerSupplierQueue::add_u(axObject * o)
{
  return (m_queue.list.add(o));
}


//********************************************************************
// method: add
//********************************************************************
// void
axObject *
axConsumerSupplierQueue::add(axObject * o, axListBase::listType_t::iterator & iter)
{
  axObject * ret;

  lock();

  ret = add_u(o, iter);

  notify();

  unlock();

  return (ret);
}


//********************************************************************
// method: add
// description: Does not lock the queue. Usually called from sub classes
//              that override the "add(axObject *, listType_t::iterator &)" method.
//********************************************************************
// void
axObject *
axConsumerSupplierQueue::add_u(axObject * o, axListBase::listType_t::iterator & iter)
{
  return (m_queue.list.add(o, iter));
}


//********************************************************************
// method:
//********************************************************************
axObject *
axConsumerSupplierQueue::getFirst(void)
{
  axObject * ret;

  lock();

  ret = getFirst_u();

  unlock();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axConsumerSupplierQueue::getFirst_u(void)
{
  axObject * ret = m_queue.list.getFirst();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axConsumerSupplierQueue::getFirstWaiting(void)
{
  axObject * ret;

  lock();

  ret = getFirstWaiting_u();

  unlock();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axConsumerSupplierQueue::getFirstWaiting_u(void)
{
  axObject * ret = m_queue.list.getFirst();

  while(!ret) {
    wait();
    ret = m_queue.list.getFirst();
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axConsumerSupplierQueue::getLast(void)
{
  axObject * ret;

  lock();

  ret = getLast_u();

  unlock();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axConsumerSupplierQueue::getLast_u(void)
{
  axObject * ret = m_queue.list.getLast();

  return (ret);
}


//********************************************************************
// method: 
//********************************************************************
axObject *
axConsumerSupplierQueue::find(axObject * o)
{
  axObject * ret;

  lock();

  ret = find_u(o);

  unlock();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axConsumerSupplierQueue::find_u(axObject * o)
{
  axObject * ret = m_queue.list.find(o);

  return (ret);
}


//********************************************************************
// method: 
//********************************************************************
axObject *
axConsumerSupplierQueue::remove(axObject * o)
{
  axObject * ret;

  lock();

  ret = remove_u(o);

  unlock();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axConsumerSupplierQueue::remove_u(axObject * o)
{
  return (m_queue.list.remove(o));
}


//********************************************************************
// method: 
//********************************************************************
axObject *
axConsumerSupplierQueue::remove(axListBase::listType_t::iterator & iter)
{
  axObject * ret;

  lock();

  ret = remove_u(iter);

  unlock();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axConsumerSupplierQueue::remove_u(axListBase::listType_t::iterator & iter)
{
  return (m_queue.list.remove(iter));
}


//********************************************************************
// method:
//********************************************************************
axObject *
axConsumerSupplierQueue::remove(void)
{
  axObject * ret;

  lock();

  ret = remove_u();

  unlock();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axConsumerSupplierQueue::remove_u(void)
{
  axObject * ret = m_queue.list.remove();

  while(!ret) {
    wait();
    ret = m_queue.list.remove();
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
size_t
axConsumerSupplierQueue::size(void)
{
  size_t ret;
  lock();
  ret = size_u();
  unlock();
  return (ret);
}


//********************************************************************
// method:
//********************************************************************
size_t
axConsumerSupplierQueue::size_u(void)
{
  size_t ret = m_queue.list.size();
  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axConsumerSupplierQueue::isEmpty(void)
{
  bool ret;
  lock();
  ret = isEmpty_u();
  unlock();
  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axConsumerSupplierQueue::isEmpty_u(void)
{
  bool ret = m_queue.list.isEmpty();
  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axConsumerSupplierQueue::clear(void)
{
  lock();
  clear_u();
  unlock();
}


//********************************************************************
// method:
//********************************************************************
void
axConsumerSupplierQueue::clear_u(void)
{
  m_queue.list.clear();
}


//********************************************************************
// method:
//********************************************************************
void
axConsumerSupplierQueue::clearAndFreeEntries(void)
{
  lock();
  clearAndFreeEntries_u();
  unlock();
}


//********************************************************************
// method:
//********************************************************************
void
axConsumerSupplierQueue::clearAndFreeEntries_u(void)
{
  m_queue.list.clearAndFreeEntries();
}


//********************************************************************
// method:
//********************************************************************
axListBase::listType_t::iterator
axConsumerSupplierQueue::begin(void)
{
  return (m_queue.list.begin());
}


//********************************************************************
// method:
//********************************************************************
axListBase::listType_t::iterator
axConsumerSupplierQueue::end(void)
{
  return (m_queue.list.end());
}


//********************************************************************
// method: createIterator
//********************************************************************
axAbstractIterator *
axConsumerSupplierQueue::createIterator(void)
{
  axAbstractIterator * ret = NULL;

  return (ret);
}


//********************************************************************
// method: freeIterator
//********************************************************************
void
axConsumerSupplierQueue::freeIterator(axAbstractIterator * iter)
{
  delete iter;
}


//********************************************************************
// method: timedWait
//********************************************************************
int
axConsumerSupplierQueue::timedWait(time_t wakeUpTime)
{
  lock();

  struct timespec ts = {0};
  ts.tv_sec = wakeUpTime;

  int ret =
    pthread_cond_timedwait(&m_queue.cond, &m_queue.queueLock, &ts);

  unlock();

  return (ret);
}


