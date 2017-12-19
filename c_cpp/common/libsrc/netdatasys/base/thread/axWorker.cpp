//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <stdio.h>
#include <unistd.h>       // sleep(),
#include <string.h>       // memset()
#include <errno.h>        // errno,
#include <string>
#include "axWorker.hpp"
// #include "axTraceHandler.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************

#if 0
AX_INT32 axWorker::m_signalList[] = {
  SIGHUP,
  SIGINT,
  // SIGQUIT,
  // SIGILL,
  // SIGTRAP,
  SIGFPE,
  // SIGBUS,
  // SIGSEGV,
  // SIGALRM,
  SIGTERM,
  SIGUSR1,
  SIGUSR2,

  -1  // MUST be last !!!
};
#else 
AX_INT32 axWorker::m_signalList[] = {
  SIGHUP,
  SIGINT,
  // SIGQUIT,
  // SIGILL,
  // SIGTRAP,
  SIGFPE,
  SIGBUS,
  SIGSEGV,
  // SIGALRM,
  SIGTERM,
  SIGUSR1,
  SIGUSR2,

  -1  // MUST be last !!!
}; 
#endif 


//********************************************************************
// forward declerations
//********************************************************************
static void * WorkerRoutine(void * arg);

//********************************************************************
// default constructor:
//********************************************************************
axWorker::axWorker()
{
  InitAtInstantiation();
}

//********************************************************************
// destructor:
//********************************************************************
axWorker::~axWorker()
{
  if (m_controllerProxy) {
    delete m_controllerProxy; 
    m_controllerProxy = NULL;
  }

  pthread_attr_destroy(&m_workerProperties);
}

//********************************************************************
// data constructor:
//********************************************************************
axWorker::axWorker(axControllerProxy * p)
{
  InitAtInstantiation();
  m_controllerProxy = p;
}


//********************************************************************
// method: InitAtInstantiation
//********************************************************************
void
axWorker::InitAtInstantiation(void) 
{
  struct threadSettableAttributes f = {-1};
  m_threadSettableAttrs = f;
  m_threadSettableAttrs.detachState = PTHREAD_CREATE_DETACHED;
  memset(&m_workerId, 0, sizeof(m_workerId));
  pthread_attr_init(&m_workerProperties);
  m_controllerProxy = NULL;
}


//********************************************************************
// method:
//********************************************************************
void
axWorker::setPriority(struct sched_param & p)
{
  static const char * myName="axWorker::setPriority:";

#if 0
  if (pthread_attr_setinheritsched(&m_workerProperties, 0)) {
    goto EXIT_LABEL;
  }

  if (pthread_attr_setprio(&m_workerProperties, p.sched_priority)) {
    goto EXIT_LABEL;
  }
#endif

  struct sched_param currVal;
  if (pthread_attr_getschedparam(&m_workerProperties, &currVal)) {
    goto EXIT_LABEL;
  }

  m_threadSettableAttrs.priority = currVal;
  m_threadSettableAttrs.priority.sched_priority = p.sched_priority;
  m_threadSettableAttrs.inheritSchedule = PTHREAD_EXPLICIT_SCHED;

EXIT_LABEL:
  return;
}


//********************************************************************
// method:
//********************************************************************
void
axWorker::setContentionScope(AX_INT32 i)
{
  m_threadSettableAttrs.contentionScope = i;
}


//********************************************************************
// method:
//********************************************************************
void
axWorker::setDetachState(AX_INT32 i)
{
  m_threadSettableAttrs.detachState = i;
}


//********************************************************************
// method:
//********************************************************************
void
axWorker::setPolicy(AX_INT32 i)
{
  m_threadSettableAttrs.policy = i;
}


//********************************************************************
// method:
//********************************************************************
void
axWorker::setInheritSchedule(AX_INT32 i)
{
  m_threadSettableAttrs.inheritSchedule = i;
}


//********************************************************************
// method:
//********************************************************************
pthread_t
axWorker::getWorkerId(void) const
{
  return (m_workerId);
}


//********************************************************************
// method:
//********************************************************************
axWorker::threadSettableAttributes *
axWorker::getThreadAttribuites(void)
{
  return (&m_threadSettableAttrs);
}


//********************************************************************
// method:
//********************************************************************
void
axWorker::setWorkerId(pthread_t tid)
{
  m_workerId = tid;
}


//********************************************************************
// method:
//********************************************************************
void
axWorker::setThreadProperties(void)
{
  static const char * myName="axWorker::setThreadProperties:";

  if (m_threadSettableAttrs.contentionScope != -1) {
    pthread_attr_setscope(&m_workerProperties,
      m_threadSettableAttrs.contentionScope);
  }

  if (m_threadSettableAttrs.detachState != -1) {
    pthread_attr_setdetachstate(&m_workerProperties,
      m_threadSettableAttrs.detachState);
  }

  if (m_threadSettableAttrs.priority.sched_priority != -1) {
    if (pthread_attr_setschedparam(&m_workerProperties, 
        &m_threadSettableAttrs.priority)) {
    }
  }

  if (m_threadSettableAttrs.policy != -1) {
    pthread_attr_setschedpolicy(&m_workerProperties,
      m_threadSettableAttrs.policy);
  }

  if (m_threadSettableAttrs.inheritSchedule != -1) {
    pthread_attr_setinheritsched(&m_workerProperties,
      m_threadSettableAttrs.inheritSchedule);
  }
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axWorker::start(void)
{
  static char * myName="axWorker::start:";

  AX_INT32 ret = 0;

  // setup pthread_attr_t
  setThreadProperties();

  ret = pthread_create(&m_workerId, &m_workerProperties, 
          WorkerRoutine, this);

  if (ret != 0) {
    std::string errMsg(strerror(errno));
    // log/trace error message ....
  } else {
    // log/trace error message ....
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axWorker::run(void)
{
  AX_INT32 ret = 0;

  bool exitWorker = false;

  /**
   * exit loop if (exitWorker==true || 
   *               (m_controllerProxy!=NULL && 
   *                m_controllerProxy->isExit()==true)
   */

  while (! (exitWorker || 
              (m_controllerProxy && m_controllerProxy->isExit())) ) {
    sleep(1);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axControllerProxy *
axWorker::getControllerProxy(void) const
{
  return (m_controllerProxy); 
}


//********************************************************************
// static function : setSignalsToBlock
//********************************************************************
void
axWorker::setSignalsToBlock(void)
{
  sigset_t signalsToBlock;
  sigemptyset(&signalsToBlock);

  for (AX_INT32 i=0; axWorker::m_signalList[i] != -1; i++) {
    sigaddset(&signalsToBlock, axWorker::m_signalList[i]);
  }

  pthread_sigmask(SIG_BLOCK, &signalsToBlock, NULL);
}


//********************************************************************
// static function : WorkerRoutine
//********************************************************************
static void *
WorkerRoutine(void * arg)
{
  axWorker * worker = (axWorker *) arg;

  /*
   * Need to let App generate core. Hence need to comment out this line
   * of code.
   */
  // worker->setSignalsToBlock();

  AX_INT32 rc = worker->run();
  return ((void*) rc);
}


#if 0
//********************************************************************
// method:
//********************************************************************
void
axWorker::setTraceHandler(axTraceHandler * tr)
{
  m_traceHandler = tr;
}


//********************************************************************
// method:
//********************************************************************
axTraceHandler *
axWorker::getTraceHandler(void)
{
  return (m_traceHandler);
}
#endif


