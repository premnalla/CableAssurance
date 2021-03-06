// test_dump.cpp,v 4.11 2003/11/01 11:15:23 dhinton Exp

// The following code illustrates how the ACE_Dumpable mechanisms are
// integrated into ACE components like the SOCK_Acceptor and
// SOCK_Stream.

#include "ace/OS_main.h"
#include "ace/Dump.h"
#include "test_dump.h"

#include "ace/Reactor.h"

ACE_RCSID(Misc, test_dump, "test_dump.cpp,v 4.11 2003/11/01 11:15:23 dhinton Exp")

int
ACE_TMAIN (int, ACE_TCHAR *[])
{
  SOCK outer_sock;
  // Note that the SOCK superclass is *not* printed.
  SOCK_Stream outer_stream;
  SOCK_Acceptor outer_acceptor;
  ACE_ODB::instance ()->dump_objects ();
  {
    SOCK inner_sock;
    // Note that the SOCK superclass is *not* printed.
    SOCK_Stream inner_stream;
    SOCK_Acceptor inner_acceptor;
    ACE_ODB::instance ()->dump_objects ();
  }
  ACE_ODB::instance ()->dump_objects ();
  return 0;
}

#if defined (ACE_HAS_EXPLICIT_TEMPLATE_INSTANTIATION)
template class ACE_Dumpable_Adapter<SOCK_Stream>;
template class ACE_Dumpable_Adapter<SOCK>;
template class ACE_Dumpable_Adapter<SOCK_Acceptor>;
#elif defined (ACE_HAS_TEMPLATE_INSTANTIATION_PRAGMA)
#pragma instantiate ACE_Dumpable_Adapter<SOCK_Stream>
#pragma instantiate ACE_Dumpable_Adapter<SOCK>
#pragma instantiate ACE_Dumpable_Adapter<SOCK_Acceptor>
#endif /* ACE_HAS_EXPLICIT_TEMPLATE_INSTANTIATION */

