// DLL_Test_Parent.cpp,v 4.1 2005/10/06 22:51:08 ossama Exp

#include "DLL_Test_Parent.h"
#include "ace/Log_Msg.h"

ACE_RCSID (tests,
           DLL_Test_Parent,
           "DLL_Test_Parent.cpp,v 4.1 2005/10/06 22:51:08 ossama Exp")


Parent::~Parent (void)
{
}

void
Parent::test (void)
{
  ACE_DEBUG ((LM_DEBUG, ACE_TEXT ("parent called\n")));
}
