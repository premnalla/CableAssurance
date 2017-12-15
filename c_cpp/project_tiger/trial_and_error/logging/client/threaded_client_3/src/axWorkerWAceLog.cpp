
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "ace/Log_Msg.h"
#include "ace/Thread.h"
#include "ace/Thread_Manager.h"
#include "axWorkerWAceLog.hpp"

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
axWorkerWAceLog::axWorkerWAceLog()
{
}


//********************************************************************
// destructor:
//********************************************************************
axWorkerWAceLog::~axWorkerWAceLog()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axWorkerWAceLog::axWorkerWAceLog()
// {
// }


//********************************************************************
// method: main loop
//********************************************************************
AX_INT32
axWorkerWAceLog::run(void)
{
  bool exitCondition = false;

  while(!exitCondition) {
    ACE_DEBUG((LM_DEBUG, "axWorkerWAceLog::run(): thrId=%d\n", 
                                                 (int)getWorkerId()));
    //printf("axWorkerWAceLog::run(): thrId=%d\n", (int)getWorkerId());
    sleep(1);
  }

  return (0);
}


#if 0
//********************************************************************
// method: init
//********************************************************************
AX_INT32
axWorkerWAceLog::init(void)
{
  ACE_LOG_MSG;
  return (0);
}
#endif


//********************************************************************
// static function : WorkerRoutine
//********************************************************************
static void *
TestWorkerRoutine(void * arg)
{
  axWorker * worker = (axWorker *) arg;

  ACE_DEBUG((LM_DEBUG, "In new worker routine\n"));

  ACE_LOG_MSG;

  unsigned long logMask = LM_SCHED_DEBUG|LM_TIMER_DEBUG|
                     LM_INTDS_DEBUG|LM_DB_DEBUG|
                     LM_ALARM_DEBUG|LM_SNMP_DEBUG|
                     LM_PING_DEBUG|LM_MISC_DEBUG|
                     LM_EMERGENCY|LM_ALERT|LM_CRITICAL|
                     LM_ERROR|LM_STARTUP|LM_WARNING|
                     LM_NOTICE|LM_INFO|LM_DEBUG;
  ACE_LOG_MSG->priority_mask(logMask, ACE_Log_Msg::THREAD);

  /*
   * Need to let App generate core. Hence need to comment out this line
   * of code.
   */
  // worker->setSignalsToBlock();

  // worker->init();
  AX_INT32 rc = worker->run();
  return ((void*) rc);
}


//********************************************************************
// method:
//********************************************************************
AX_INT32
axWorkerWAceLog::start(void)
{
  static char * myName="axWorkerWAceLog::start:";

  AX_INT32 ret = 0;

  printf("In new worker routine\n");

  // setup pthread_attr_t
  setThreadProperties();

  ACE_Thread::spawn(TestWorkerRoutine, this, THR_NEW_LWP | THR_DETACHED);

  ret = pthread_create(&m_workerId, &m_workerProperties,
          TestWorkerRoutine, this);

  if (ret != 0) {
    printf("Error creating\n");
    string errMsg(strerror(errno));
    // log/trace error message ....
  } else {
    printf("Success creating\n");
    // log/trace error message ....
  }

  return (ret);
}

