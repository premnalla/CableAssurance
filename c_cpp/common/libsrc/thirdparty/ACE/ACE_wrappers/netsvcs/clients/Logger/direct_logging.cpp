// direct_logging.cpp,v 4.9 2004/06/14 18:26:58 jwillemsen Exp

// This program sends logging records directly to the server, rather
// than going through the client logging daemon.

#include "ace/SOCK_Connector.h"
#include "ace/Log_Record.h"
#include "ace/Log_Msg.h"
#include "ace/OS_NS_time.h"
#include "ace/OS_NS_stdlib.h"
#include "ace/OS_NS_unistd.h"

ACE_RCSID(Logger, direct_logging, "direct_logging.cpp,v 4.9 2004/06/14 18:26:58 jwillemsen Exp")

static u_short LOGGER_PORT = ACE_DEFAULT_SERVER_PORT;
static const ACE_TCHAR *const LOGGER_HOST = ACE_DEFAULT_SERVER_HOST;
static const ACE_TCHAR *const DATA = ACE_TEXT("hello world\n");

int
ACE_TMAIN (int argc, ACE_TCHAR *argv[])
{
  u_short logger_port  = argc > 1 ? ACE_OS::atoi (argv[1]) : LOGGER_PORT;
  const ACE_TCHAR *logger_host = argc > 2 ? argv[2] : LOGGER_HOST;

  ACE_SOCK_Stream logger;
  ACE_SOCK_Connector connector;
  ACE_INET_Addr addr (logger_port, logger_host);

  if (connector.connect (logger, addr) == -1)
    ACE_ERROR_RETURN ((LM_ERROR, "%p\n", "open"), -1);

  ACE_Log_Record log_record (LM_DEBUG,
           ACE_OS::time ((time_t *) 0),
           ACE_OS::getpid ());

  ACE_TCHAR buf[BUFSIZ];
  ACE_OS::sprintf (buf, ACE_TEXT ("hello world = %d\n"), getpid());

  log_record.msg_data (buf);
  size_t len = log_record.length ();
  size_t encoded_len = htonl (len);
  log_record.encode ();

  for (int i = 0; i<5; i++) {
    printf("%s\n", buf);
    if (logger.send (4, &encoded_len, sizeof encoded_len,
           (char *) &log_record, len) == -1) {
      ACE_ERROR_RETURN ((LM_ERROR, "%p\n", "send"), -1);
    }

    ACE_OS::sleep(1);
  }

  logger.close ();

  return 0;
}
