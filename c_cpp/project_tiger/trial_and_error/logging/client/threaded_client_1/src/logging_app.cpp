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

ACE_RCSID(client, logging_app, "logging_app.cpp,v 4.9 2004/10/27 21:06:58 shuston Exp")

static u_short LOGGER_PORT = ACE_DEFAULT_SERVER_PORT;
static const ACE_TCHAR *const LOGGER_HOST = ACE_DEFAULT_SERVER_HOST;
// static const int MAX_ITERATIONS = 10;
static const int MAX_ITERATIONS = 1;

ACE_SOCK_Stream logger;

static ACE_THR_FUNC_RETURN thread_function(void * arg)
{
  ACE_THR_FUNC_RETURN ret = 0;

  // ACE_SOCK_Stream logger;

  int id = (int) pthread_self();

  while (true) {
    ACE_DEBUG((LM_DEBUG, "thread-id = %d\n", id));
    ACE_Log_Record log_record (LM_DEBUG,
                               ACE_OS::time ((time_t *) 0),
                               ACE_OS::getpid ());

    ACE_TCHAR buf[BUFSIZ];
    ACE_OS::sprintf (buf, ACE_TEXT ("thread-id = %d\n"), id);
    log_record.msg_data (buf);
    size_t len = log_record.length ();
    size_t encoded_len = htonl (len);

    log_record.encode ();

    if (logger.send (4, &encoded_len, sizeof encoded_len,
                                        (char *) &log_record, len) == -1) {
      // ACE_ERROR_RETURN ((LM_ERROR, ACE_TEXT ("%p\n"), ACE_TEXT ("send")),-1);
    } else {
      ACE_OS::sleep (1);
    }
  }

  return (ret);
}


int
ACE_TMAIN (int argc, ACE_TCHAR *argv[])
{
  const ACE_TCHAR *logger_host = argc > 1 ? argv[1] : LOGGER_HOST;
  u_short logger_port  = argc > 2 ? ACE_OS::atoi (argv[2]) : LOGGER_PORT;
  int max_iterations = argc > 3 ? ACE_OS::atoi (argv[3]) : MAX_ITERATIONS;

  ACE_Log_Msg * logMsg = ACE_Log_Msg::instance();
  logMsg->open("foo", ACE_Log_Msg::LOGGER | ACE_Log_Msg::VERBOSE);

  // ACE_SOCK_Stream logger;
  ACE_SOCK_Connector connector;
  ACE_INET_Addr addr (logger_port, logger_host);

  if (connector.connect (logger, addr) == -1)
    ACE_ERROR_RETURN ((LM_ERROR, ACE_TEXT ("%p\n"), ACE_TEXT ("open")), -1);

  for (int i = 0; i < max_iterations; i++)
    {
    ACE_DEBUG((LM_DEBUG, "iteration = %d\n", i));
    // ACE_Thread::spawn(thread_function, NULL, THR_DETACHED);

    // following works
    // ACE_Thread_Manager::instance()->spawn(thread_function, NULL, THR_NEW_LWP | THR_DETACHED);

    ACE_Thread_Manager::instance()->spawn_n(2, thread_function, NULL, THR_NEW_LWP | THR_DETACHED);

#if 1
      ACE_Log_Record log_record (LM_DEBUG, 
                                 ACE_OS::time ((time_t *) 0), 
                                 ACE_OS::getpid ());

      ACE_TCHAR buf[BUFSIZ];
      ACE_OS::sprintf (buf, ACE_TEXT ("message = %d\n"), i + 1);
      log_record.msg_data (buf);
      size_t len = log_record.length ();
      size_t encoded_len = htonl (len);

      log_record.encode ();

      if (logger.send (4, &encoded_len, sizeof encoded_len,
		       (char *) &log_record, len) == -1)
	ACE_ERROR_RETURN ((LM_ERROR, ACE_TEXT ("%p\n"), ACE_TEXT ("send")),-1);
      else
	ACE_OS::sleep (1);
#endif
    }

  while (true) {
    ACE_OS::sleep (60);
  }

  if (logger.close () == -1)
    ACE_ERROR_RETURN ((LM_ERROR, ACE_TEXT ("%p\n"), ACE_TEXT ("close")), -1);

  return 0;
}
