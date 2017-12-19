// -*- C++ -*-
// RMCast_Export.h,v 1.3 2005/02/11 16:44:28 ossama Exp
// Definition for Win32 Export directives.
// This file is generated automatically by
// generate_export_file.pl
// ------------------------------
#if !defined (ACE_RMCAST_EXPORT_H)
#define ACE_RMCAST_EXPORT_H

#include "ace/config-all.h"

#if defined (ACE_AS_STATIC_LIBS) && !defined (ACE_RMCAST_HAS_DLL)
#  define ACE_RMCAST_HAS_DLL 0
#endif /* ACE_AS_STATIC_LIBS && ACE_RMCAST_HAS_DLL */

#if !defined (ACE_RMCAST_HAS_DLL)
#define ACE_RMCAST_HAS_DLL 1
#endif /* ! ACE_RMCAST_HAS_DLL */

#if defined (ACE_RMCAST_HAS_DLL)
#  if (ACE_RMCAST_HAS_DLL == 1)
#    if defined (ACE_RMCAST_BUILD_DLL)
#      define ACE_RMCast_Export ACE_Proper_Export_Flag
#      define ACE_RMCAST_SINGLETON_DECLARATION(T) ACE_EXPORT_SINGLETON_DECLARATION (T)
#      define ACE_RMCAST_SINGLETON_DECLARE(SINGLETON_TYPE, CLASS, LOCK) ACE_EXPORT_SINGLETON_DECLARE(SINGLETON_TYPE, CLASS, LOCK)
#    else
#      define ACE_RMCast_Export ACE_Proper_Import_Flag
#      define ACE_RMCAST_SINGLETON_DECLARATION(T) ACE_IMPORT_SINGLETON_DECLARATION (T)
#      define ACE_RMCAST_SINGLETON_DECLARE(SINGLETON_TYPE, CLASS, LOCK) ACE_IMPORT_SINGLETON_DECLARE(SINGLETON_TYPE, CLASS, LOCK)
#    endif /* ACE_RMCAST_BUILD_DLL */
#  else
#    define ACE_RMCast_Export
#    define ACE_RMCAST_SINGLETON_DECLARATION(T)
#    define ACE_RMCAST_SINGLETON_DECLARE(SINGLETON_TYPE, CLASS, LOCK)
#  endif   /* ! ACE_RMCAST_HAS_DLL == 1 */
#else
#  define ACE_RMCast_Export
#  define ACE_RMCAST_SINGLETON_DECLARATION(T)
#  define ACE_RMCAST_SINGLETON_DECLARE(SINGLETON_TYPE, CLASS, LOCK)
#endif     /* ACE_RMCAST_HAS_DLL */

#endif     /* ACE_RMCAST_EXPORT_H */

// End of auto generated file.
