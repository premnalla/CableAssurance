dnl -------------------------------------------------------------------------
dnl       ace.m4,v 1.47 2006/02/24 15:37:59 jtc Exp
dnl
dnl       ace.m4
dnl
dnl       ACE M4 include file which contains ACE specific M4 macros
dnl       for enabling/disabling certain ACE features.
dnl
dnl -------------------------------------------------------------------------

dnl  Copyright (C) 1998, 1999, 2000, 2002  Ossama Othman
dnl
dnl  All Rights Reserved
dnl
dnl This library is free software; you can redistribute it and/or
dnl modify it under the current ACE distribution terms.
dnl
dnl This library is distributed in the hope that it will be useful,
dnl but WITHOUT ANY WARRANTY; without even the implied warranty of
dnl MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.


dnl Macros that add ACE configuration options to a `configure' script.
dnl ACE_CONFIGURATION_OPTIONS
AC_DEFUN([ACE_CONFIGURATION_OPTIONS],
[
 AM_CONDITIONAL([BUILD_ACE_FOR_TAO], false)

 AC_ARG_ENABLE([ace-codecs],
  AS_HELP_STRING(--enable-ace-codecs,build ACE with codecs support [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_ace_codecs=yes
      ;;
    no)
      ace_user_enable_ace_codecs=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-ace-codecs])
      ;;
   esac
  ],
  [
   ace_user_enable_ace_codecs=yes
  ])
 AM_CONDITIONAL([BUILD_ACE_CODECS], [test X$ace_user_enable_ace_codecs = Xyes])

 AC_ARG_ENABLE([ace-filecache],
  AS_HELP_STRING(--enable-ace-filecache,build ACE_Filecache support [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_ace_filecache=yes
      ;;
    no)
      ace_user_enable_ace_filecache=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-ace-filecache])
      ;;
   esac
  ],
  [
   dnl Enable ACE_Filecache support by default since it's never turned off
   dnl in the ACE lib itself. Just required for some things like JAWS.
   ace_user_enable_ace_filecache=yes
  ])
 AM_CONDITIONAL([BUILD_ACE_FILECACHE], [test X$ace_user_enable_ace_filecache = Xyes])

 AC_ARG_ENABLE([ace-other],
  AS_HELP_STRING(--enable-ace-other,build ACE with all misc pieces [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_ace_other=yes
      ;;
    no)
      ace_user_enable_ace_other=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-ace-other])
      ;;
   esac
  ],
  [
   ace_user_enable_ace_other=yes
  ])
 AM_CONDITIONAL([BUILD_ACE_OTHER], [test X$ace_user_enable_ace_other = Xyes])

 AC_ARG_ENABLE([ace-token],
  AS_HELP_STRING(--enable-ace-token,build ACE with tokens support [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_ace_token=yes
      ;;
    no)
      ace_user_enable_ace_token=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-ace-token])
      ;;
   esac
  ],
  [
   ace_user_enable_ace_token=yes
  ])
 AM_CONDITIONAL([BUILD_ACE_TOKEN], [test X$ace_user_enable_ace_token = Xyes])

 AC_ARG_ENABLE([ace-uuid],
  AS_HELP_STRING(--enable-ace-uuid,build ACE with UUID support [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_ace_uuid=yes
      ;;
    no)
      ace_user_enable_ace_uuid=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-ace-uuid])
      ;;
   esac
  ],
  [
   ace_user_enable_ace_uuid=yes
  ])
 AM_CONDITIONAL([BUILD_ACE_UUID], [test X$ace_user_enable_ace_uuid = Xyes])

 AC_ARG_ENABLE([alloca],
  AS_HELP_STRING(--enable-alloca,compile with alloca() support [[[no]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_alloca=yes
      ;;
    no)
      ace_user_enable_alloca=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-alloca])
      ;;
   esac
  ],
  [
   dnl Disable alloca() support by default since its use is generally
   dnl not recommended.
   ace_user_enable_alloca=no
  ])

 AC_ARG_ENABLE([rwho],
  AS_HELP_STRING(--enable-rwho,build the distributed rwho program [[[no]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_rwho=yes
      ;;
    no)
      ace_user_enable_rwho=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-rwho])
      ;;
   esac
  ],)
 AM_CONDITIONAL([BUILD_RWHO], [test X$ace_user_enable_rwho = Xyes])

 AC_ARG_ENABLE([ipv4-ipv6],
  AS_HELP_STRING(--enable-ipv4-ipv6,compile with IPv4/IPv6 migration support [[[no]]]),
  [
   case "${enableval}" in
    yes)
      AC_DEFINE(ACE_HAS_IPV6)
      AC_DEFINE(ACE_USES_IPV4_IPV6_MIGRATION)
      ;;
    no)
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-ipv4-ipv6])
      ;;
   esac
  ],)

 AC_ARG_ENABLE([ipv6],
  AS_HELP_STRING(--enable-ipv6,compile with IPv6 support [[[no]]]),
  [
   case "${enableval}" in
    yes)
      AC_DEFINE(ACE_HAS_IPV6)
      ace_user_enable_ipv6=yes
      ;;
    no)
      ace_user_enable_ipv6=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-ipv6])
      ;;
   esac
  ],)
 AM_CONDITIONAL([BUILD_IPV6], [test X$ace_user_enable_ipv6 = Xyes])

 AC_ARG_ENABLE([log-msg-prop],
  AS_HELP_STRING(--enable-log-msg-prop,enable threads inheriting ACE_Log_Msg properties from parent thread [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      dnl nothing to do
      ;;
    no)
      AC_DEFINE(ACE_THREADS_DONT_INHERIT_LOG_MSG)
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-log-msg-prop])
      ;;
   esac
  ],)

 AC_ARG_ENABLE([logging],
  AS_HELP_STRING(--enable-logging,enable ACE logging macros [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      dnl nothing to do
      ;;
    no)
      AC_DEFINE([ACE_NLOGGING])
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-logging])
      ;;
   esac
  ],)

 AC_ARG_ENABLE([malloc-stats],
  AS_HELP_STRING(--enable-malloc-stats,enable malloc statistics collection [[[no]]]),
  [
   case "${enableval}" in
    yes)
      AC_DEFINE([ACE_HAS_MALLOC_STATS])
      ;;
    no)
      dnl nothing to do
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-malloc-stats])
      ;;
   esac
  ],)

 AC_ARG_ENABLE([pi-pointers],
  AS_HELP_STRING(--enable-pi-pointers,enable pos. indep. pointers [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      AC_DEFINE([ACE_HAS_POSITION_INDEPENDENT_POINTERS])
      ;;
    no)
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-pi-pointers])
      ;;
   esac
  ],
  [
   AC_DEFINE([ACE_HAS_POSITION_INDEPENDENT_POINTERS])
  ])

 AC_ARG_ENABLE([probe],
  AS_HELP_STRING(--enable-probe,enable ACE_Timeprobes [[[no]]]),
  [
   case "${enableval}" in
    yes)
      AC_DEFINE([ACE_COMPILE_TIMEPROBES])
      ;;
    no)
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-probe])
      ;;
   esac
  ],)

 AC_ARG_ENABLE([static-obj-mgr],
  AS_HELP_STRING(--enable-static-obj-mgr,enable static Object_Manager [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      dnl nothing to do
      ;;
    no)
      AC_DEFINE([ACE_HAS_NONSTATIC_OBJECT_MANAGER])
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-static-obj-mgr])
      ;;
   esac
  ],)


 AC_ARG_ENABLE([threads],
  AS_HELP_STRING(--enable-threads,enable thread support [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_threads=yes
      ;;
    no)
      ace_user_enable_threads=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-threads])
      ;;
   esac
  ],
  [
    ace_user_enable_threads=yes
  ])
 AM_CONDITIONAL([BUILD_THREADS], [test X$ace_user_enable_threads = Xyes])

 AC_ARG_ENABLE([pthreads],
  AS_HELP_STRING(--enable-pthreads,enable POSIX thread (Pthreads) support [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_pthreads=yes
      ;;
    no)
      ace_user_enable_pthreads=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-pthreads])
      ;;
   esac
  ],
  [
    ace_user_enable_pthreads=yes
  ])

 AC_ARG_ENABLE([uithreads],
  AS_HELP_STRING(--enable-uithreads,enable UNIX International thread support [[[no]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_uithreads=yes
      ;;
    no)
      ace_user_enable_uithreads=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-uithreads])
      ;;
   esac
  ],
  [
    dnl The default is to disable UI threads. However, on Solaris, we
    dnl enable it by default since it's functionality is very useful and
    dnl has traditionally been enabled in ACE.
    case "$host" in
      *solaris2*)
        ace_user_enable_uithreads=yes
        AC_MSG_NOTICE([[--enable-uithreads enabled by default for Solaris; use --enable-uithreads=no to disable it.]])
        ;;
      *)
        ace_user_enable_uithreads=no
        ;;
    esac
  ])

 AC_ARG_ENABLE([verb-not-sup],
  AS_HELP_STRING(--enable-verb-not-sup,enable verbose ENOTSUP reports [[[no]]]),
  [
   case "${enableval}" in
    yes)
      AC_DEFINE([ACE_HAS_VERBOSE_NOTSUP])
      ;;
    no)
      dnl Do nothing
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-verb-not-sup])
      ;;
   esac
  ],)

 dnl The ace/config-all.h file defaults ACE_NTRACE properly, so only emit
 dnl something if the user specifies this option.
 AC_ARG_ENABLE([trace],
  AS_HELP_STRING(--enable-trace,enable ACE tracing [[[no]]]),
  [
   case "${enableval}" in
    yes)
      AC_DEFINE([ACE_NTRACE],0)
      ;;
    no)
      AC_DEFINE([ACE_NTRACE],1)
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-trace])
      ;;
   esac
  ],)

 AC_ARG_ENABLE([wfmo],
  AS_HELP_STRING(--enable-wfmo,build WFMO-using examples [[[no]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_wfmo=yes
      ;;
    no)
      ace_user_enable_wfmo=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-wfmo])
      ;;
   esac
  ],
  [
    case "$host" in
      *win32*)
           ace_user_enable_wfmo=yes
               ;;
      *)
           ace_user_enable_wfmo=no
               ;;
    esac
  ])
 AM_CONDITIONAL([BUILD_WFMO], [test X$ace_user_enable_wfmo = Xyes])

 AC_ARG_ENABLE([winregistry],
  AS_HELP_STRING(--enable-winregistry,build Windows registry-using examples [[[no]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_winregistry=no
      ;;
    no)
      ace_user_enable_winregistry=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-winregistry])
      ;;
   esac
  ],
  [
    case "$host" in
      *win32*)
           ace_user_enable_winregistry=yes
               ;;
      *)
           ace_user_enable_winregistry=no
               ;;
    esac
  ])
 AM_CONDITIONAL([BUILD_WINREGISTRY], [test X$ace_user_enable_winregistry = Xyes])

 ACE_ENABLE_FL_REACTOR
 ACE_ENABLE_QT_REACTOR
 ACE_ENABLE_TK_REACTOR
 ACE_ENABLE_XT_REACTOR

 AC_ARG_WITH([gperf],
  AS_HELP_STRING(--with-gperf,compile the gperf program [[[yes]]]),
  [
   case "${withval}" in
    yes)
      ace_user_with_gperf=yes
      AC_DEFINE([ACE_HAS_GPERF])
      AS_IF([test -n "$GPERF"],
        [
         AC_MSG_WARN([gperf program already exists])
         AC_MSG_WARN([existing gperf may be overwritten during installation])
        ],[])
      ;;
    no)
      ace_user_with_gperf=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${withval} for --with-gperf])
      ;;
   esac
  ],
  [
   ace_user_with_gperf=yes
   AC_DEFINE([ACE_HAS_GPERF])
   AS_IF([test -n "$GPERF"],
    [
     AC_MSG_WARN([gperf program already exists])
     AC_MSG_WARN([existing gperf may be overwritten during installation])
    ],[])
  ])
 AM_CONDITIONAL([COMPILE_GPERF], [test X$ace_user_with_gperf = Xyes])

 ACE_ENABLE_QOS
 ACE_ENABLE_SSL
 ACE_ENABLE_ACEXML

 AC_ARG_WITH([tao],
  AS_HELP_STRING(--with-tao,build TAO (the ACE ORB) [[[yes]]]),
  [
   case "${withval}" in
    yes)
      ace_user_with_tao=yes
      ;;
    no)
      ace_user_with_tao=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${withval} for --with-tao])
      ;;
   esac
  ],
  [
   ace_user_with_tao=yes
  ])

 AC_ARG_WITH([tli-device],
  AS_HELP_STRING(--with-tli-device(=DEV),device for TCP on TLI [[/dev/tcp]]),
  [
   case "${withval}" in
    yes)
      AC_MSG_ERROR([Specify the TLI/TCP device if you use this option.])
      ;;
    no)
      ;;
    *)
      if test -e "${withval}"; then
        AC_DEFINE_UNQUOTED([ACE_TLI_TCP_DEVICE], ["${withval}"])
      else
        AC_MSG_ERROR([TLI/TCP device ${withval} does not exist.])
      fi
      ;;
   esac
  ],)

 AC_ARG_ENABLE([reentrant],
  AS_HELP_STRING(--enable-reentrant,enable reentrant functions [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_reentrant_funcs=yes
      ;;
    no)
      ace_user_enable_reentrant_funcs=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-reentrant])
      ;;
   esac
  ],
  [
   ace_user_enable_reentrant_funcs=yes
  ])

 AC_ARG_ENABLE([ace-examples],
  AS_HELP_STRING(--enable-ace-examples,build ACE examples [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      ace_build_examples=yes
      ;;
    no)
      ace_build_examples=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-ace-examples])
      ;;
   esac
  ],
  [
   ace_build_examples=yes
  ])
  AM_CONDITIONAL([BUILD_EXAMPLES], [test X$ace_build_examples = Xyes])

 AC_ARG_ENABLE([ace-tests],
  AS_HELP_STRING(--enable-ace-tests,build ACE tests [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      ace_build_tests=yes
      ;;
    no)
      ace_build_tests=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-ace-tests])
      ;;
   esac
  ],
  [
   ace_build_tests=yes
  ])
 AM_CONDITIONAL([BUILD_TESTS], [test X$ace_build_tests = Xyes])

 ACE_ENABLE_CDR_SWAP_ON_READ
 ACE_ENABLE_CDR_SWAP_ON_WRITE
 ACE_ENABLE_CDR_ALIGNMENT
 ACE_ENABLE_STRDUP_EMULATION
 ACE_ENABLE_WCSDUP_EMULATION
])


dnl Macros that add ACE compilation options to a `configure' script.
dnl ACE_COMPILATION_OPTIONS
AC_DEFUN([ACE_COMPILATION_OPTIONS],
[
 AC_ARG_ENABLE([debug],
  AS_HELP_STRING(--enable-debug,enable debugging [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      ACE_CXXFLAGS="$ACE_CXXFLAGS $DCXXFLAGS"
      ;;
    no)
      AC_DEFINE([ACE_NDEBUG])
      AC_DEFINE([ACE_USE_RCSID],[0])
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-debug])
      ;;
   esac
  ],)

 AC_ARG_ENABLE([exceptions],
  AS_HELP_STRING(--enable-exceptions,enable C++ exception handling [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_exceptions=yes
      ;;
    no)
      ace_user_enable_exceptions=no
      if test "$GXX" = yes; then
        if $CXX --version | $EGREP -v '^2\.[[0-7]]' > /dev/null; then
          ACE_CXXFLAGS="$ACE_CXXFLAGS -fno-exceptions"
        fi
      fi
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-exceptions])
      ;;
   esac
  ],
  [
   ace_user_enable_exceptions=yes

dnl THE FOLLOWING WAS ONLY USED WHEN DISABLING EXCEPTION SUPPORT BY
dnl DEFAULT.
dnl
dnl    if test "$GXX" = yes; then
dnl      if $CXX --version | $EGREP -v '^2\.[[0-7]]' > /dev/null; then
dnl        ACE_CXXFLAGS="$ACE_CXXFLAGS -fno-exceptions"
dnl      fi
dnl    fi
  ])
 AM_CONDITIONAL([BUILD_EXCEPTIONS], [test X$ace_user_enable_exceptions = Xyes])

 AC_ARG_ENABLE([fast],
  AS_HELP_STRING(--enable-fast,enable -fast flag (e.g. Sun C++) [[[no]]]),
  [
   case "${enableval}" in
    yes)
      ACE_CXXFLAGS="$ACE_CXXFLAGS -fast"
      ACE_CFLAGS="$ACE_CFLAGS -fast"
      ;;
    no)
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-fast])
      ;;
   esac
  ],)

 AC_ARG_ENABLE([inline],
  AS_HELP_STRING(--enable-inline,enable code inlining [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      AC_DEFINE([__ACE_INLINE__])
      ;;
    no)
      AC_DEFINE([ACE_NO_INLINE])
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-inline])
      ;;
   esac
  ],
  [
   AC_DEFINE([__ACE_INLINE__])
  ])

 AC_ARG_ENABLE([optimize],
  AS_HELP_STRING(--enable-optimize,enable additional optimizations [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_optimize=yes
      ;;
    no)
      AC_MSG_WARN([Optimization configure support not fully implemented yet.])
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-optimize])
      ;;
   esac
  ],
  [
   ace_user_enable_optimize=yes
  ])


 AC_ARG_ENABLE([profile],
  AS_HELP_STRING(--enable-profile,enable profiling [[[no]]]),
  [
   case "${enableval}" in
    yes)
      if test -z "$PROF"; then
        AC_MSG_WARN([No profiling program found.  Assuming 'prof' exists.])
        ACE_CXXFLAGS="$ACE_CXXFLAGS -p"
        ACE_CFLAGS="$ACE_CFLAGS -p"
      else
        case "$PROF" in
        gprof)
          echo "Building with 'gprof' support"
          ACE_CXXFLAGS="$ACE_CXXFLAGS -pg"
          ACE_CFLAGS="$ACE_CFLAGS -pg"
          ;;
        prof)
          echo "Building with 'prof' support"
          ACE_CXXFLAGS="$ACE_CXXFLAGS -p"
          ACE_CFLAGS="$ACE_CFLAGS -p"
          ;;
        *)
          dnl We shouldn't get here.
          AC_MSG_WARN([Assuming 'prof' exists.])
          ACE_CXXFLAGS="$ACE_CXXFLAGS -p"
          ACE_CFLAGS="$ACE_CFLAGS -p"
          ;;
        esac
      fi
      ;;
    no)
      dnl Do nothing
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-profile])
      ;;
   esac
  ],)

 AC_ARG_ENABLE([purify],
  AS_HELP_STRING(--enable-purify,Purify all executables [[[no]]]),
  [
   case "${enableval}" in
    yes)
      AC_CHECK_PROG([PURIFY], [purify], [purify],[])
      if test -n "$PURIFY"; then
        PURE_CACHE_BASE_DIR=/tmp/purifycache
        PURE_CACHE_DIR="${PURE_CACHE_BASE_DIR}-${LOGNAME}"
        PURE_CACHE_DIR="${PURE_CACHE_DIR}-"`basename $CXX`
        PURELINK="$PURIFY -best-effort -chain-length=20 -cache-dir=$PURE_CACHE_DIR -fds-inuse-at-exit=no -inuse-at-exit -max_threads=100"
        dnl Pick up Quantify directory from the users PATH.
		    ACE_PURIFY_DIR=`type purify | sed -e 's/.* is //' -e 's%/purify'`
        ACE_CPPFLAGS="-DACE_HAS_PURIFY -I$ACE_PURIFY_DIR"
      else
        AC_MSG_WARN([Purify program was not found.])
        AC_MSG_WARN([Disabling purify support.])
      fi
      ;;
    no)
      PURELINK=""
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-purify])
      ;;
   esac
  ], PURELINK="")

 AC_ARG_ENABLE([quantify],
  AS_HELP_STRING(--enable-quantify,Quantify all executables [[[no]]]),
  [
   case "${enableval}" in
    yes)
      AC_CHECK_PROG([QUANTIFY], [quantify], [quantify],[])
      if test -n "$QUANTIFY"; then
        PURE_CACHE_BASE_DIR=/tmp/purifycache
        PURE_CACHE_DIR="${PURE_CACHE_BASE_DIR}-${LOGNAME}"
        PURE_CACHE_DIR="${PURE_CACHE_DIR}-"`basename $CXX`

        PRELINK="$QUANTIFY -best-effort -max_threads=100 -cache-dir=$PURE_CACHE_DIR"
        dnl Pick up Quantify directory from the users PATH.
		    ACE_QUANTIFY_DIR=`type quantify | sed -e 's/.* is //' -e 's%/quantify$$%%'`
        ACE_CPPFLAGS="-DACE_HAS_QUANTIFY -I$ACE_QUANTIFY_DIR"
      else
        AC_MSG_WARN([Quantify program was not found.])
        AC_MSG_WARN([Disabling quantify support.])
      fi
      ;;
    no)
      PRELINK=""
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-quantify])
      ;;
   esac
  ], PRELINK="")

 AC_ARG_ENABLE([repo],
  AS_HELP_STRING(--enable-repo,use GNU template repository GNU C++ with repo patches and EGCS only [[[no]]]),
  [
   case "${enableval}" in
    yes)
      if test "$GXX" = yes; then
        ace_user_enable_repo=yes
        ACE_CXXFLAGS="$ACE_CXXFLAGS -frepo"
        AC_DEFINE(ACE_HAS_GNU_REPO)
      else
        ace_user_enable_repo=no
        AC_MSG_WARN([Not using GNU C++! GNU template respository disabled.])
      fi
      ;;
    no)
      ace_user_enable_repo=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-repo])
      ;;
   esac
  ],
  [
   ace_user_enable_repo=no
  ])

 AC_ARG_ENABLE([rtti],
  AS_HELP_STRING(--enable-rtti,enable run-time type identification [[[yes]]]),
  [
   case "${enableval}" in
    yes)
      if test "$GXX" = no; then
        case "$host" in
          *solaris*)
               ace_user_enable_rtti=yes
               ;;
          *aix*)
               ace_user_enable_rtti=yes
               ;;
          *)
               ;;
        esac
      else
        AC_MSG_WARN([We do not know if rtti needs enabling for this compiler.])
      fi
      ;;
    no)
      ace_user_enable_rtti=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-rtti])
      ;;
   esac
  ],
  [
   ace_user_enable_rtti=yes
  ])

 AC_ARG_ENABLE([stdcpplib],
  AS_HELP_STRING([--enable-stdcpplib],[enable standard C++ library [[yes]]]),
  [
   case "${enableval}" in
    yes)
      ace_user_enable_stdcpplib=yes
      ;;
    no)
      ace_user_enable_stdcpplib=no
      ;;
    *)
      AC_MSG_ERROR([bad value ${enableval} for --enable-stdcpplib])
      ;;
   esac
  ],
  [
   ace_user_enable_stdcpplib=yes
  ])

 AC_ARG_ENABLE([uses-wchar],
               AS_HELP_STRING([--enable-uses-wchar],
                            [enable use of wide characters [[no]]]),
               [case "${withval}" in
                 yes)
                  AC_DEFINE([ACE_USES_WCHAR])
                  ace_user_enable_wide_char=yes
                  ;;
                 no)
                  ace_user_enable_wide_char=no
                  ;;
                 *)
                  AC_MSG_ERROR([bad value ${enableval} for --enable-uses-wchar])
                  ;;
                esac])
 AC_CACHE_CHECK([whether to use wide characters internally],
                [ace_user_enable_wide_char], [ace_user_enable_wide_char=no])
 AM_CONDITIONAL([BUILD_USES_WCHAR], [test X$ace_user_enable_wide_char = Xyes])

])

# ACE_ENABLE_CDR_SWAP_ON_READ
#---------------------------------------------------------------------------
AC_DEFUN([ACE_ENABLE_CDR_SWAP_ON_READ],
[AC_ARG_ENABLE([ace-cdr-swap-on-read],
               AS_HELP_STRING([--enable-ace-cdr-swap-on-read],
                              [configure CDR to support swap on read [[yes]]]),
	       [case "${enableval}" in
		 yes)
		  ace_user_cdr_swap_on_read=yes
		  ;;
		 no)
		  ace_user_cdr_swap_on_read=no
		  ;;
		 *)
		  AC_MSG_ERROR(bad value ${enableval} for --enable-ace-cdr-swap-on-read)
		  ;;
		esac],[
		  ace_user_cdr_swap_on_read=yes
		])
if test X$ace_user_cdr_swap_on_read = Xno; then
  AC_DEFINE(ACE_DISABLE_SWAP_ON_READ, 1,
	    [Define to 1 to disable swapping swapping CDR on read])
fi
])

# ACE_ENABLE_CDR_SWAP_ON_WRITE
#---------------------------------------------------------------------------
AC_DEFUN([ACE_ENABLE_CDR_SWAP_ON_WRITE],
[AC_ARG_ENABLE([ace-cdr-swap-on-write],
               AS_HELP_STRING([--enable-ace-cdr-swap-on-write],
                              [configure CDR to support swap on write [[no]]]),
	       [case "${enableval}" in
		 yes)
		  ace_user_cdr_swap_on_write=yes
		  ;;
		 no)
		  ace_user_cdr_swap_on_write=no
		  ;;
		 *)
		  AC_MSG_ERROR(bad value ${enableval} for --enable-ace-cdr-swap-on-write)
		  ;;
		esac],[
		  ace_user_cdr_swap_on_write=no
		])
if test X$ace_user_cdr_swap_on_write = Xyes; then
  AC_DEFINE(ACE_ENABLE_SWAP_ON_WRITE, 1,
	    [Define to 1 to enable swapping swapping CDR on write])
fi
])

# ACE_ENABLE_CDR_ALIGNMENT
#---------------------------------------------------------------------------
AC_DEFUN([ACE_ENABLE_CDR_ALIGNMENT],
[AC_ARG_ENABLE([ace-cdr-alignment],
               AS_HELP_STRING([--enable-ace-cdr-alignment],
                              [configure CDR to require aligned access [[yes]]]),
	       [case "${enableval}" in
		 yes)
		  ace_user_cdr_alignment=yes
		  ;;
		 no)
		  ace_user_cdr_alignment=no
		  ;;
		 *)
		  AC_MSG_ERROR(bad value ${enableval} for --enable-ace-cdr-alignment)
		  ;;
		esac],[
		  ace_user_cdr_alignment=yes
		])
if test X$ace_user_cdr_alignment = Xno; then
  AC_DEFINE(ACE_LACKS_CDR_ALIGNMENT, 1,
	    [Define to 1 to support unaligned CDR])
fi
])

# ACE_ENABLE_STRDUP_EMULATION
#---------------------------------------------------------------------------
AC_DEFUN([ACE_ENABLE_STRDUP_EMULATION],
[AC_ARG_ENABLE([ace-strdup-emulation],
               AS_HELP_STRING([--enable-ace-strdup-emulation],
                              [use ACE's strdup emulation [[no]]]),
	       [case "${enableval}" in
		 yes)
		  ace_user_strdup_emulation=yes
		  ;;
		 no)
		  ace_user_strdup_emulation=no
		  ;;
		 *)
		  AC_MSG_ERROR(bad value ${enableval} for --enable-ace-strdup-emulation)
		  ;;
		esac],[
		  ace_user_strdup_emulation=no
		])
if test X$ace_user_strdup_emulation = Xyes; then
  AC_DEFINE(ACE_HAS_STRDUP_EMULATION, 1,
	    [Define to 1 use ACE's strdup() emulation])
fi
])

# ACE_ENABLE_WCSDUP_EMULATION
#---------------------------------------------------------------------------
AC_DEFUN([ACE_ENABLE_WCSDUP_EMULATION],
[AC_ARG_ENABLE([ace-wcsdup-emulation],
               AS_HELP_STRING([--enable-ace-wcsdup-emulation],
                              [use ACE's wcsdup emulation [[no]]]),
	       [case "${enableval}" in
		 yes)
		  ace_user_wcsdup_emulation=yes
		  ;;
		 no)
		  ace_user_wcsdup_emulation=no
		  ;;
		 *)
		  AC_MSG_ERROR(bad value ${enableval} for --enable-ace-wcsdup-emulation)
		  ;;
		esac],[
		  ace_user_wcsdup_emulation=no
		])
if test X$ace_user_wcsdup_emulation = Xyes; then
  AC_DEFINE(ACE_HAS_WCSDUP_EMULATION, 1,
	    [Define to 1 use ACE's wcsdup() emulation])
fi
])

AC_DEFUN([ACE_ENABLE_QOS],
[AC_ARG_ENABLE([qos],
	       AS_HELP_STRING([--enable-qos],
			      [compile/use the ACE_QoS library [[no]]]),
	       [case "${enableval}" in
		 yes)
		  ace_user_enable_qos=yes
		  ;;
		 no)
		  ace_user_enable_qos=no
		  ;;
		 *)
		  AC_MSG_ERROR(bad value ${enableval} for --enable-qos)
		  ;;
		esac])
AC_CACHE_CHECK([whether to compile/use the ACE_QoS library],
               [ace_user_enable_qos],[ace_user_enable_qos=no])
AM_CONDITIONAL([BUILD_QOS], [test X$ace_user_enable_qos = Xyes])
])

AC_DEFUN([ACE_ENABLE_SSL],
[AC_REQUIRE([ACE_CHECK_TLS])
AC_ARG_ENABLE([ssl],
	       AS_HELP_STRING([--enable-ssl],
			      [compile/use the ACE_SSL library [[yes]]]),
	       [case "${enableval}" in
		 yes)
		  ace_user_enable_ssl=yes
		  ;;
		 no)
		  ace_user_enable_ssl=no
		  ;;
		 *)
		  AC_MSG_ERROR(bad value ${enableval} for --enable-ssl)
		  ;;
		esac])
AC_CACHE_CHECK([whether to compile/use the ACE_SSL library],
               [ace_user_enable_ssl], [ace_user_enable_ssl=yes])
AM_CONDITIONAL([BUILD_SSL], [test X$ace_user_enable_ssl = Xyes])
])

AC_DEFUN([ACE_ENABLE_ACEXML],
[AC_ARG_ENABLE([acexml],
	       AS_HELP_STRING([--enable-acexml],
			      [compile/use the ACEXML library [[yes]]]),
	       [case "${enableval}" in
		 yes)
		  ace_user_enable_acexml=yes
		  ;;
		 no)
		  ace_user_enable_acexml=no
		  ;;
		 *)
		  AC_MSG_ERROR(bad value ${enableval} for --enable-acexml)
		  ;;
		esac],
		[
		 ace_user_enable_acexml=yes
		])
AC_CACHE_CHECK([whether to compile/use the ACEXML library],
               [ace_user_enable_acexml], [ace_user_enable_acexml=yes])
AM_CONDITIONAL([BUILD_ACEXML], [test X$ace_user_enable_acexml = Xyes])
])


# ACE_PATH_GL
#---------------------------------------------------------------------------
# Find OpenGL Libraries, flags, etc.
AC_DEFUN([ACE_PATH_GL],
[
AM_CONDITIONAL([BUILD_GL], [false])
])


# ACE_PATH_FL
#---------------------------------------------------------------------------
# Find FL/TK Libraries, flags, etc.
AC_DEFUN([ACE_PATH_FL],
[AC_REQUIRE([ACE_PATH_GL])
])


# ACE_PATH_QT
#---------------------------------------------------------------------------
# Find Qt Libraries, flags, etc.
AC_DEFUN([ACE_PATH_QT],
[
])


# ACE_PATH_TCL
#---------------------------------------------------------------------------
# Find Tcl Libraries, flags, etc.
AC_DEFUN([ACE_PATH_TCL], 
[AC_ARG_WITH([tclconfig],
 AS_HELP_STRING([--with-tclconfig=DIR],
                [path to tclConfig.sh [[automatic]]]),
 [ ac_tclconfig_dir="${withval}" ])
 if test X"${ac_tclconfig_dir}" = X; then
   AC_PATH_PROG([TCLCONFIG], [tclConfig.sh], [],
                [${PATH}:/usr/local/lib:/usr/pkg/lib:/usr/lib/tcl8.4:/usr/lib/tcl8.3:/usr/lib])
 else
  AC_MSG_CHECKING([whether tclConfig.sh exists in ${ac_tclconfig_dir}])
   if test -f "${ac_tclconfig_dir}/tclConfig.sh"; then
     TCLCONFIG="${ac_tclconfig_dir}/tclConfig.sh"
     AC_MSG_RESULT([yes])
   else
     AC_MSG_RESULT([no])
   fi
 fi
 if test X"${TCLCONFIG}" != X; then
   . ${TCLCONFIG}

   ACE_TCL_CPPFLAGS="${TCL_INCLUDE_SPEC}"
   eval "ACE_TCL_LIBS=\"${TCL_LIB_SPEC}\""

   AC_SUBST(ACE_TCL_CPPFLAGS)
   AC_SUBST(ACE_TCL_LIBS)
 fi
])


# ACE_PATH_TK
#---------------------------------------------------------------------------
# Find Tk Libraries, flags, etc.
AC_DEFUN([ACE_PATH_TK], 
[AC_REQUIRE([ACE_PATH_TCL])
 AC_ARG_WITH([tkconfig],
 AS_HELP_STRING([--with-tkconfig=DIR],
                [path to tkConfig.sh [[automatic]]]),
 [ ac_tkconfig_dir="${withval}" ])
 if test X"${ac_tkconfig_dir}" = X; then
   AC_PATH_PROG([TKCONFIG], [tkConfig.sh], [],
                [${PATH}:/usr/local/lib:/usr/pkg/lib:/usr/lib/tk8.4:/usr/lib/tk8.3:/usr/lib])
 else
   AC_MSG_CHECKING([whether tkConfig.sh exists in ${ac_tkconfig_dir}])
   if test -f "${ac_tkconfig_dir}/tkConfig.sh"; then
     TKCONFIG="${ac_tkconfig_dir}/tkConfig.sh"
     AC_MSG_RESULT([yes])
   else
     AC_MSG_RESULT([no])
   fi
 fi
 if test X"${TKCONFIG}" != X; then
   . ${TKCONFIG}

   ACE_TK_CPPFLAGS="${TK_INCLUDE_SPEC} ${TK_XINCLUDES}"
   ACE_TK_LIBS="${TK_LIB_SPEC} ${TK_XLIBSW}"

   AC_SUBST(ACE_TK_CPPFLAGS)
   AC_SUBST(ACE_TK_LIBS)
 fi
])


# ACE_ENABLE_FL_REACTOR
#---------------------------------------------------------------------------
AC_DEFUN([ACE_ENABLE_FL_REACTOR],
[AC_REQUIRE([ACE_PATH_FL])
AC_ARG_ENABLE([fl-reactor],
  	       AS_HELP_STRING([--enable-fl-reactor],
		              [build support for the FlReactor [[no]]]),
               [case "${enableval}" in
                 yes)
		  AC_MSG_ERROR([--enable-fl-reactor currently unimplemented])
		  ace_user_enable_fl_reactor=yes
		  ;;
		no)
		  AC_MSG_ERROR([--enable-fl-reactor currently unimplemented])
		  ace_user_enable_fl_reactor=no
		  ;;
		*)
		  AC_MSG_ERROR([bad value ${enableval} for --enable-fl-reactor])
		  ;;
	        esac],
                [
                 ace_user_enable_fl_reactor=no
                ])
AM_CONDITIONAL([BUILD_FL], [test X$ace_user_enable_fl_reactor = Xyes])
AM_CONDITIONAL([BUILD_ACE_FLREACTOR],
               [test X$ace_user_enable_fl_reactor = Xyes])
AM_CONDITIONAL([BUILD_TAO_FLRESOURCE],
               [test X$ace_user_enable_fl_reactor = Xyes])
])


# ACE_ENABLE_QT_REACTOR
#---------------------------------------------------------------------------
AC_DEFUN([ACE_ENABLE_QT_REACTOR],
[AC_REQUIRE([ACE_PATH_QT])
AC_ARG_ENABLE([qt-reactor],
  	       AS_HELP_STRING([--enable-qt-reactor],
		              [build support for the QtReactor [[no]]]),
               [case "${enableval}" in
                 yes)
		  AC_MSG_ERROR([--enable-qt-reactor currently unimplemented])
		  ace_user_enable_qt_reactor=yes
		  ;;
		no)
		  AC_MSG_ERROR([--enable-qt-reactor currently unimplemented])
		  ace_user_enable_qt_reactor=no
		  ;;
		*)
		  AC_MSG_ERROR([bad value ${enableval} for --enable-qt-reactor])
		  ;;
	        esac],
                [
                 ace_user_enable_qt_reactor=no
                ])
AM_CONDITIONAL([BUILD_QT], [test X$ace_user_enable_qt_reactor = Xyes])
AM_CONDITIONAL([BUILD_ACE_QTREACTOR],
               [test X$ace_user_enable_qt_reactor = Xyes])
AM_CONDITIONAL([BUILD_TAO_QTRESOURCE],
               [test X$ace_user_enable_qt_reactor = Xyes])
])


# ACE_ENABLE_TK_REACTOR
#---------------------------------------------------------------------------
AC_DEFUN([ACE_ENABLE_TK_REACTOR],
[AC_REQUIRE([ACE_PATH_TK])
AC_ARG_ENABLE([tk-reactor],
  	       AS_HELP_STRING([--enable-tk-reactor],
		              [build support for the TkReactor [[no]]]),
               [case "${enableval}" in
                 yes)
                  AS_IF([test X"${TCLCONFIG}" != X],
                        [AS_IF([test X"${TKCONFIG}" != X],
                               [ace_user_enable_tk_reactor=yes],
                               [AC_MSG_ERROR([ACE_TkReactor cannot be enabled: tkConfig not found.])])],
                        [AC_MSG_ERROR([ACE_TkReactor cannot be enabled: tclConfig not found.])])
		  ;;
		no)
		  ace_user_enable_tk_reactor=no
		  ;;
		*)
		  AC_MSG_ERROR([bad value ${enableval} for --enable-tk-reactor])
		  ;;
	        esac],
                [
                 ace_user_enable_tk_reactor=no
                ])
AM_CONDITIONAL([BUILD_TK], [test X$ace_user_enable_tk_reactor = Xyes])
AM_CONDITIONAL([BUILD_ACE_TKREACTOR],
               [test X$ace_user_enable_tk_reactor = Xyes])
AM_CONDITIONAL([BUILD_TAO_TKRESOURCE],
               [test X$ace_user_enable_tk_reactor = Xyes])
])


# ACE_ENABLE_XT_REACTOR
#---------------------------------------------------------------------------
AC_DEFUN([ACE_ENABLE_XT_REACTOR],
[AC_ARG_ENABLE([xt-reactor],
               AS_HELP_STRING([--enable-xt-reactor],
                              [build support for the XtReactor [[no]]]),
               [case "${enableval}" in
                 yes)
                  AC_PATH_XTRA
dnl Here, if X isn't found or the user sets "--without-x" on the command
dnl line, then "no_x" is set to "yes."
                  AS_IF([test "$no_x" != yes],
                        [
		          ACE_XLIBS="-lX11 -lXt"
                          ace_user_enable_xt_reactor=yes
                        ],[
                          ACE_XLIBS=""
                          ace_user_enable_xt_reactor=no
                          AC_MSG_WARN([X was not found or it was disabled.])
                          AC_MSG_WARN([ACE_XtReactor will not be enabled.])
                        ])
                  ;;
                 no)
                  ACE_XLIBS=""
                  ace_user_enable_xt_reactor=no
                  ;;
                 *)
		  AC_MSG_ERROR([bad value ${enableval} for --enable-xt-reactor])
		  ;;
                esac],
		[
                  ACE_XLIBS=""
                  ace_user_enable_xt_reactor=no
		])
AM_CONDITIONAL([BUILD_X11], [test X$ace_user_enable_xt_reactor = Xyes])
AM_CONDITIONAL([BUILD_XT], [test X$ace_user_enable_xt_reactor = Xyes])
AM_CONDITIONAL([BUILD_ACE_XTREACTOR],
               [test X$ace_user_enable_xt_reactor = Xyes])
AM_CONDITIONAL([BUILD_TAO_XTRESOURCE],
               [test X$ace_user_enable_xt_reactor = Xyes])
])
