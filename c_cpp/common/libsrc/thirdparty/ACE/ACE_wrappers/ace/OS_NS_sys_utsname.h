// -*- C++ -*-

//=============================================================================
/**
 *  @file   OS_NS_sys_utsname.h
 *
 *  OS_NS_sys_utsname.h,v 1.6 2006/01/05 11:47:04 jwillemsen Exp
 *
 *  @author Douglas C. Schmidt <schmidt@cs.wustl.edu>
 *  @author Jesper S. M|ller<stophph@diku.dk>
 *  @author and a cast of thousands...
 *
 *  Originally in OS.h.
 */
//=============================================================================

#ifndef ACE_OS_NS_SYS_UTSNAME_H
# define ACE_OS_NS_SYS_UTSNAME_H

# include /**/ "ace/pre.h"

# include "ace/config-all.h"

# if !defined (ACE_LACKS_PRAGMA_ONCE)
#  pragma once
# endif /* ACE_LACKS_PRAGMA_ONCE */

#include "ace/ACE_export.h"

#if defined (ACE_EXPORT_MACRO)
#  undef ACE_EXPORT_MACRO
#endif
#define ACE_EXPORT_MACRO ACE_Export

#if defined (ACE_LACKS_UTSNAME_T)
#   if !defined (SYS_NMLN)
#     define SYS_NMLN 257
#   endif /* SYS_NMLN */
#   if !defined (_SYS_NMLN)
#     define _SYS_NMLN SYS_NMLN
#   endif /* _SYS_NMLN */
ACE_BEGIN_VERSIONED_NAMESPACE_DECL
struct ACE_utsname
{
  ACE_TCHAR sysname[_SYS_NMLN];
  ACE_TCHAR nodename[_SYS_NMLN];
  ACE_TCHAR release[_SYS_NMLN];
  ACE_TCHAR version[_SYS_NMLN];
  ACE_TCHAR machine[_SYS_NMLN];
};
ACE_END_VERSIONED_NAMESPACE_DECL
# else
#   include "ace/os_include/sys/os_utsname.h"
ACE_BEGIN_VERSIONED_NAMESPACE_DECL
typedef struct utsname ACE_utsname;
ACE_END_VERSIONED_NAMESPACE_DECL
# endif /* ACE_LACKS_UTSNAME_T */

ACE_BEGIN_VERSIONED_NAMESPACE_DECL

namespace ACE_OS {

  extern ACE_Export
  int uname (ACE_utsname *name);

} /* namespace ACE_OS */

ACE_END_VERSIONED_NAMESPACE_DECL

# include /**/ "ace/post.h"
#endif /* ACE_OS_NS_SYS_UTSNAME_H */
