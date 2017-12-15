
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <string>
#include "ace/Thread.h"
#include "ace/Thread_Manager.h"
#include "axCALog.hpp"
#include "axAceWorker.hpp"

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
axAceWorker::axAceWorker() :
#if 0
  m_threadFlags((AX_ULONG) THR_NEW_LWP | THR_DETACHED)
#else
  m_threadFlags((AX_ULONG) THR_DETACHED)
#endif
{
}


//********************************************************************
// destructor:
//********************************************************************
axAceWorker::~axAceWorker()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAceWorker::axAceWorker(axControllerProxy * p) :
  axWorker(p),
#if 0
  m_threadFlags((AX_ULONG) THR_NEW_LWP | THR_DETACHED)
#else
  m_threadFlags((AX_ULONG) THR_DETACHED)
#endif
{
}


//********************************************************************
// static function : WorkerRoutine
//********************************************************************
static void *
WorkerRoutine(void * arg)
{
  axAceWorker * worker = (axAceWorker *) arg;

  ACE_TRACE(ACE_TEXT ("axAceWorker::WorkerRoutine\n"));

  AX_INT32 rc = worker->run();
  return ((void*) rc);
}


//********************************************************************
// method:
//********************************************************************
void
axAceWorker::setThreadFlags(AX_ULONG flags)
{
  m_threadFlags = flags;
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axAceWorker::start(void)
{
  static char * myName="axAceWorker::start:";

  AX_INT32 ret;

  ACE_thread_t tid;
  ret = ACE_Thread_Manager::instance()->spawn(WorkerRoutine, this, 
                                                 m_threadFlags, &tid);
  if (ret == -1) {
    std::string errMsg(strerror(errno));
    ACE_ERROR((LM_ERROR, "%s: error spawning thread: error %s\n", 
                                             myName, errMsg.c_str()));
  } else {
    setWorkerId((pthread_t) tid);
  }

  return (ret);
}

