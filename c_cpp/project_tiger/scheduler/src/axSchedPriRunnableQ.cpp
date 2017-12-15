//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axSchedPriRunnableQ.hpp"
#include "axAbstractRunnable.hpp"

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
axSchedPriRunnableQ::axSchedPriRunnableQ()
{
  pthread_mutex_init(&m_queue.queueLock, NULL);
  pthread_cond_init(&m_queue.cond, NULL);
}

//********************************************************************
// destructor:
//********************************************************************
axSchedPriRunnableQ::~axSchedPriRunnableQ()
{
  clearAndFreeEntries();
  pthread_mutex_destroy(&m_queue.queueLock);
  pthread_cond_destroy(&m_queue.cond);
}

//********************************************************************
// data constructor:
//********************************************************************
// axSchedPriRunnableQ::axSchedPriRunnableQ()
// {
// }


//********************************************************************
// method:
//********************************************************************
void
axSchedPriRunnableQ::lock(void)
{
  pthread_mutex_lock(&m_queue.queueLock);
}


//********************************************************************
// method:
//********************************************************************
void
axSchedPriRunnableQ::unlock(void)
{
  pthread_mutex_unlock(&m_queue.queueLock);
}


//********************************************************************
// method:
//********************************************************************
int
axSchedPriRunnableQ::wait(void)
{
  int ret = pthread_cond_wait(&m_queue.cond, &m_queue.queueLock);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
int
axSchedPriRunnableQ::wait(struct timespec * timeSpec)
{
  int ret = 
    pthread_cond_timedwait(&m_queue.cond, &m_queue.queueLock, timeSpec);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axSchedPriRunnableQ::notify(void)
{
  pthread_cond_broadcast(&m_queue.cond);
}


//********************************************************************
// method: add
//********************************************************************
// void
axObject *
axSchedPriRunnableQ::add(axObject * o)
{
  axAbstractRunnable * r = dynamic_cast<axAbstractRunnable *> (o);

  int pri = r->getPriority();

  lock();

  if (pri >= SCHED_MIN_PRIORITY && pri <= SCHED_MAX_PRIORITY) {
    m_queue.list[pri].add(r);
  }

  notify();

  unlock();

  return (o);
}


//********************************************************************
// method: 
//********************************************************************
axObject *
axSchedPriRunnableQ::find(axObject * o)
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
axSchedPriRunnableQ::find_u(axObject * o)
{
  axObject * ret = NULL;

  for (int i=SCHED_MIN_PRIORITY; i<=SCHED_MAX_PRIORITY && !ret; i++) {
    ret = m_queue.list[i].find(o);
  }

  return (ret);
}


//********************************************************************
// method: 
//********************************************************************
axObject *
axSchedPriRunnableQ::remove(axObject * o)
{
  axObject * ret;

  lock();

  ret = remove_u(o);

  while(!ret) {
    wait();
    ret = remove_u(o);
  }

  unlock();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axSchedPriRunnableQ::remove_u(axObject * o)
{
  axObject * ret = NULL;

  for (int i=SCHED_MIN_PRIORITY; i<=SCHED_MAX_PRIORITY && !ret; i++) {
    ret = m_queue.list[i].remove(o);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axSchedPriRunnableQ::remove(void)
{
  axObject * ret;

  lock();

  ret = remove_u();

  while(!ret) {
    wait();
    ret = remove_u();
  }

  unlock();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axObject *
axSchedPriRunnableQ::remove_u(void)
{
  axObject * ret = NULL;

  for (int i=SCHED_MIN_PRIORITY; i<=SCHED_MAX_PRIORITY && !ret; i++) {
    ret = m_queue.list[i].remove();
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
size_t
axSchedPriRunnableQ::size(void)
{
  size_t ret = 0;

  lock();

  for (int i=0; i<SCHED_NUM_PRIORITIES; i++) {
    ret += m_queue.list[i].size();
  }

  unlock();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axSchedPriRunnableQ::isEmpty(void)
{
  bool ret = false;

  lock();

  for (int i=0; i<SCHED_NUM_PRIORITIES && !ret; i++) {
    ret = m_queue.list[i].isEmpty();
  }

  unlock();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axSchedPriRunnableQ::clear(void)
{
  lock();

  for (int i=0; i<SCHED_NUM_PRIORITIES; i++) {
    m_queue.list[i].clear();
  }

  unlock();
}


//********************************************************************
// method:
//********************************************************************
void
axSchedPriRunnableQ::clearAndFreeEntries(void)
{
  lock();

  for (int i=0; i<SCHED_NUM_PRIORITIES; i++) {
    m_queue.list[i].clearAndFreeEntries();
  }

  unlock();
}


//********************************************************************
// method: createIterator
//********************************************************************
axAbstractIterator *
axSchedPriRunnableQ::createIterator(void)
{
  axAbstractIterator * ret = NULL;

  return (ret);
}


//********************************************************************
// method: freeIterator
//********************************************************************
void
axSchedPriRunnableQ::freeIterator(axAbstractIterator * iter)
{
  delete iter;
}


