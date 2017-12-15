// logging_app.cpp,v 4.9 2004/10/27 21:06:58 shuston Exp

// This program sends logging records directly to the server, rather
// than going through the client logging daemon.

#include "ace/SOCK_Connector.h"
#include "ace/Log_Record.h"
#include "ace/Log_Msg.h"
#include "ace/OS_NS_time.h"
#include "ace/OS_NS_stdio.h"
#include "ace/OS_NS_stdlib.h"
#include "ace/OS_NS_unistd.h"
#include "ace/Thread.h"
#include "ace/Thread_Manager.h"
#include "ace/Service_Config.h"
#include "axWorkerWAceLog.hpp"

ACE_RCSID(client, logging_app, "logging_app.cpp,v 4.9 2004/10/27 21:06:58 shuston Exp")

static ACE_THR_FUNC_RETURN thread_function(void * arg)
{
  ACE_THR_FUNC_RETURN ret = 0;

  ACE_LOG_MSG;

  int id = (int) pthread_self();

  while (true) {
    ACE_DEBUG((LM_DEBUG, "thread-id = %d\n", id));
    ACE_OS::sleep (1);
  }

  return (ret);
}


int
ACE_TMAIN (int argc, ACE_TCHAR *argv[])
{
  int max_iterations = 2;

  if (ACE_Service_Config::open (argc,
                                argv,
                                ACE_DEFAULT_LOGGER_KEY,
                                1,
                                0,
                                1) < 0) {

    ACE_ERROR_RETURN ((LM_ERROR, ACE_TEXT ("%p\n"),
                       ACE_TEXT ("Service Config open")),
                      1);
  }

  ACE_LOG_MSG->set_flags(ACE_Log_Msg::VERBOSE_LITE);

  unsigned long logMask = LM_SCHED_DEBUG|LM_TIMER_DEBUG|
                     LM_INTDS_DEBUG|LM_DB_DEBUG|
                     LM_ALARM_DEBUG|LM_SNMP_DEBUG|
                     LM_PING_DEBUG|LM_MISC_DEBUG|
                     LM_EMERGENCY|LM_ALERT|LM_CRITICAL|
                     LM_ERROR|LM_STARTUP|LM_WARNING|
                     LM_NOTICE|LM_INFO|LM_DEBUG;
  ACE_LOG_MSG->priority_mask(logMask, ACE_Log_Msg::PROCESS);

  printf("main: fooooooo\n");

  for (int i = 0; i < max_iterations; i++) {
    ACE_DEBUG((LM_DEBUG, "iteration = %d\n", i));

    // ACE_Thread_Manager::instance()->spawn_n(2, thread_function, NULL, THR_NEW_LWP | THR_DETACHED);
    // ACE_Thread::spawn(thread_function, NULL, THR_NEW_LWP | THR_DETACHED);

    axWorkerWAceLog * worker = new axWorkerWAceLog();
    worker->start();

    ACE_OS::sleep (1);
  }

  while (true) {
    ACE_OS::sleep (10);
    ACE_DEBUG((LM_DEBUG, "main\n"));
  }

  return 0;
}
