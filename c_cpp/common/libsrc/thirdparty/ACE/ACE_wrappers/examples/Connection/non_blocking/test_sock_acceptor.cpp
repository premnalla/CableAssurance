// test_sock_acceptor.cpp,v 4.11 2004/10/27 21:06:58 shuston Exp

#include "ace/SOCK_Acceptor.h"
#include "ace/INET_Addr.h"
#include "ace/Service_Config.h"

#include "CPP-acceptor.h"


ACE_RCSID (non_blocking,
           test_sock_acceptor,
           "test_sock_acceptor.cpp,v 4.11 2004/10/27 21:06:58 shuston Exp")


typedef Svc_Handler<ACE_SOCK_STREAM> SVC_HANDLER;
typedef IPC_Server<SVC_HANDLER, ACE_SOCK_ACCEPTOR> IPC_SERVER;

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
template class ACE_Oneshot_Acceptor<SVC_HANDLER, ACE_SOCK_ACCEPTOR>;
template class ACE_Svc_Handler<ACE_SOCK_STREAM, ACE_NULL_SYNCH>;
template class IPC_Server<SVC_HANDLER, ACE_SOCK_ACCEPTOR>;
template class Svc_Handler<ACE_SOCK_STREAM>;
#elif defined (ACE_HAS_TEMPLATE_INSTANTIATION_PRAGMA)
#pragma instantiate ACE_Concurrency_Strategy<SVC_HANDLER>
#pragma instantiate ACE_Oneshot_Acceptor<SVC_HANDLER, ACE_SOCK_ACCEPTOR>
#pragma instantiate ACE_Svc_Handler<ACE_SOCK_STREAM, ACE_NULL_SYNCH>
#pragma instantiate IPC_Server<SVC_HANDLER, ACE_SOCK_ACCEPTOR>
#pragma instantiate Svc_Handler<ACE_SOCK_STREAM>
#endif /* ACE_HAS_EXPLICIT_TEMPLATE_INSTANTIATION */
