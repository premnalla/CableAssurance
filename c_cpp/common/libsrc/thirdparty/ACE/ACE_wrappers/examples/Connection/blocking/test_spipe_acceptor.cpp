// test_spipe_acceptor.cpp,v 4.5 2004/10/27 21:06:58 shuston Exp

// ACE_SPIPE Server.

#include "SPIPE-acceptor.h"

ACE_RCSID(blocking, test_spipe_acceptor, "test_spipe_acceptor.cpp,v 4.5 2004/10/27 21:06:58 shuston Exp")

#if ((defined (ACE_WIN32) && !defined (ACE_HAS_WINCE)) || (defined (ACE_HAS_AIO_CALLS)))

int
ACE_TMAIN (int argc, ACE_TCHAR *argv[])
{
  // Perform Service_Config initializations
  ACE_Service_Config daemon (argv[0]);

  IPC_Server peer_acceptor;

  if (peer_acceptor.init (argc, argv) == -1)
    ACE_ERROR_RETURN ((LM_ERROR, ACE_TEXT ("%p\n"), ACE_TEXT ("init")), -1);

  return peer_acceptor.svc ();
}

#else

int
main (int, char *[])
{
  ACE_DEBUG ((LM_INFO,
              ACE_TEXT ("Asynchronous IO is unsupported.\n")));

  return 0;
}

#endif
