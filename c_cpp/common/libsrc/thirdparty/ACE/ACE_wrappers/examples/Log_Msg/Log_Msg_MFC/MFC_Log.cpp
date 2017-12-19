// MFC_Log.cpp,v 1.6 2005/01/31 07:16:16 jwillemsen Exp
// MFC_Log.cpp: implementation of the MFC_Log class.
//
//////////////////////////////////////////////////////////////////////

#include "ace/OS_NS_stdio.h"
#include "MFC_Log.h"

#if defined (ACE_WIN32)
#  include "stdafx.h"
#endif /* ACE_WIN32 */

#include "ace/Log_Record.h"
#include "ace/Time_Value.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

MFC_Log::MFC_Log ()
{

}

void
MFC_Log::log (ACE_Log_Record& log_record)
{
  unsigned long priority = log_record.type();
  ACE_TCHAR Message[512];

  ACE_OS::sprintf (Message,
                   ACE_TEXT ("%d.%03ld %s"),
                   log_record.time_stamp().sec(),
                   log_record.time_stamp().usec()/ 1000,
                   log_record.msg_data());

#if defined (ACE_WIN32)
  ACE_TEXT_OutputDebugString(Message);
#endif /* ACE_WIN32 */
}

MFC_Log::~MFC_Log ()
{

}
