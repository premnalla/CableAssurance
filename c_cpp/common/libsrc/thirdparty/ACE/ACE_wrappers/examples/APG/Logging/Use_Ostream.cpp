// Use_Ostream.cpp,v 1.6 2005/09/13 11:52:55 jwillemsen Exp

#include "ace/Log_Msg.h"
#include "ace/streams.h"
#if (defined (_MSC_VER) && (_MSC_VER < 1300))
#include <fstream>
#endif
void foo (void);

int ACE_TMAIN (int, ACE_TCHAR *[])
{
  ACE_TRACE ("main");

  ACE_DEBUG ((LM_DEBUG, ACE_TEXT ("%IHi Mom\n")));

  /* Alternatively, you can use the set_flags() method to do the same
     thing after the singleton has been created:
  */
  // Listing 2 code/ch03
  ACE_OSTREAM_TYPE *output =
    (ACE_OSTREAM_TYPE *) new ofstream ("ostream.output.test");
  ACE_LOG_MSG->msg_ostream (output, 1);
  ACE_LOG_MSG->set_flags (ACE_Log_Msg::OSTREAM);
  ACE_LOG_MSG->clr_flags (ACE_Log_Msg::STDERR);
  // Listing 2

  foo ();
  ACE_DEBUG ((LM_INFO, ACE_TEXT ("%IGoodnight\n")));

  return 0;
}

void foo (void)
{
  ACE_TRACE ("foo");

  ACE_DEBUG ((LM_INFO, ACE_TEXT ("%IHowdy Pardner\n")));
}
