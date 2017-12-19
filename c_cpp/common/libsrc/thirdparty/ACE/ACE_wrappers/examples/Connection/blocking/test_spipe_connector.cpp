// test_spipe_connector.cpp,v 4.5 2004/10/27 21:06:58 shuston Exp

// ACE_SPIPE Client.

#include "SPIPE-connector.h"

ACE_RCSID(blocking, test_spipe_connector, "test_spipe_connector.cpp,v 4.5 2004/10/27 21:06:58 shuston Exp")

int 
ACE_TMAIN (int argc, ACE_TCHAR *argv[])                       
{                                                       
  // Perform Service_Config initializations
  ACE_Service_Config daemon (argv[0]);

  IPC_Client peer_connector;

  if (peer_connector.init (argc, argv) == -1)
    ACE_ERROR_RETURN ((LM_ERROR, ACE_TEXT ("%p\n"), ACE_TEXT ("init")), -1);
  
  return peer_connector.svc ();
}                                                       
