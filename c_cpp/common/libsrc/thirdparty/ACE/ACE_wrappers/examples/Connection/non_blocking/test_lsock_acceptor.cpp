// test_lsock_acceptor.cpp,v 4.11 2005/01/10 10:24:03 jwillemsen Exp

// ACE_LSOCK Server.

#include "ace/LSOCK_Acceptor.h"
#include "ace/Log_Msg.h"
#include "ace/Service_Config.h"


#if defined (ACE_LACKS_UNIX_DOMAIN_SOCKETS)
int
ACE_TMAIN (int, ACE_TCHAR *argv[])
{
  ACE_ERROR_RETURN ((LM_INFO,
                     ACE_TEXT ("%s: not supported with ")
                     ACE_TEXT ("ACE_LACKS_UNIX_DOMAIN_SOCKETS\n"),
                     argv[0]),
                     -1);
}

#else  /* ! ACE_LACKS_UNIX_DOMAIN_SOCKETS */

#include "ace/UNIX_Addr.h"
#include "CPP-acceptor.h"

ACE_RCSID (non_blocking,
           test_lsock_acceptor,
           "test_lsock_acceptor.cpp,v 4.11 2005/01/10 10:24:03 jwillemsen Exp")

typedef Svc_Handler<ACE_LSOCK_STREAM> SVC_HANDLER;
typedef IPC_Server<SVC_HANDLER, ACE_LSOCK_ACCEPTOR> IPC_SERVER;

int
ACE_TMAIN (int argc, ACE_TCHAR *argv[])
{
  // Perform Service_Config initializations
  ACE_Service_Config daemon (argv[0]);

  IPC_SERVER peer_acceptor;

  if (peer_acceptor.init (argc, argv) == -1)
    ACE_ERROR_RETURN ((LM_ERROR,
                       ACE_TEXT ("%p\n"),
                       ACE_TEXT ("init")),
                      -1);
  return peer_acceptor.svc ();
}


#if defined (ACE_HAS_EXPLICIT_TEMPLATE_INSTANTIATION)
template class ACE_Concurrency_Strategy<SVC_HANDLER>;
template class ACE_Oneshot_Acceptor<SVC_HANDLER, ACE_LSOCK_ACCEPTOR>;
template class ACE_Svc_Handler<ACE_LSOCK_STREAM, ACE_NULL_SYNCH>;
template class IPC_Server<SVC_HANDLER, ACE_LSOCK_ACCEPTOR>;
template class Svc_Handler<ACE_LSOCK_STREAM>;
#elif defined (ACE_HAS_TEMPLATE_INSTANTIATION_PRAGMA)
#pragma instantiate ACE_Concurrency_Strategy<SVC_HANDLER>
#pragma instantiate ACE_Oneshot_Acceptor<SVC_HANDLER, ACE_LSOCK_ACCEPTOR>
#pragma instantiate ACE_Svc_Handler<ACE_LSOCK_STREAM, ACE_NULL_SYNCH>
#pragma instantiate IPC_Server<SVC_HANDLER, ACE_LSOCK_ACCEPTOR>
#pragma instantiate Svc_Handler<ACE_LSOCK_STREAM>
#endif /* ACE_HAS_EXPLICIT_TEMPLATE_INSTANTIATION */

#endif /* ! ACE_LACKS_UNIX_DOMAIN_SOCKETS */
