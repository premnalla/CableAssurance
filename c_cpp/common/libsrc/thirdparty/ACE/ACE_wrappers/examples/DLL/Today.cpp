// Today.cpp,v 1.10 2004/05/06 15:09:18 shuston Exp

#define ACE_BUILD_SVC_DLL

#include "Today.h"
#include "ace/Log_Msg.h"
#include "ace/svc_export.h"
#include "ace/OS_Memory.h"

// Implementation of the abstract class method which describes the
// magazine.

void
Today::title (void)
{
  ACE_DEBUG ((LM_DEBUG,
              "Today: XML Special Apr 02\n"));
}

void *
Today::operator new (size_t bytes)
{
  return ::new char[bytes];
}
#if defined (ACE_HAS_NEW_NOTHROW)
void *
Today::operator new (size_t bytes, const ACE_nothrow_t&)
{
  return ::new (ACE_nothrow) char[bytes];
}
#endif
void
Today::operator delete (void *ptr)
{
  delete [] ((char *) ptr);
}

// Returns the pointer to the Today class.
// The ACE_BUILD_SVC_DLL and ACE_Svc_Export directives are necessary to
// take care of exporting the function for Win32 platforms.
extern "C" ACE_Svc_Export Magazine *create_magazine (void);

Magazine *create_magazine (void)
{
  Magazine *mag;
  ACE_NEW_RETURN (mag, Today, 0);
  return mag;
}
