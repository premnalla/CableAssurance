##  Process this file with automake to create Makefile.in
##
## Makefile.am,v 1.4 2005/03/18 22:16:09 jtc Exp
##

includedir = @includedir@/Kokyu
pkgconfigdir = @libdir@/pkgconfig

ACE_BUILDDIR = $(top_builddir)
ACE_ROOT = $(top_srcdir)

SUBDIRS = \
        . \
        tests

## Makefile.Kokyu.am

lib_LTLIBRARIES = libKokyu.la

libKokyu_la_CPPFLAGS = \
  -I$(ACE_ROOT) \
  -I$(ACE_BUILDDIR) \
  -DKOKYU_BUILD_DLL

libKokyu_la_SOURCES = \
  Default_Dispatcher_Impl.cpp \
  Dispatcher_Impl.cpp \
  Dispatcher_Task.cpp \
  Kokyu.cpp \
  Kokyu_defs.cpp

libKokyu_la_LDFLAGS = \
 -version-number @ACE_MAJOR@:@ACE_MINOR@:@ACE_BETA@

libKokyu_la_LIBADD = \
  $(ACE_BUILDDIR)/ace/libACE.la

nobase_include_HEADERS = \
  DSRT_Direct_Dispatcher_Impl_T.cpp \
  DSRT_Direct_Dispatcher_Impl_T.h \
  DSRT_Dispatch_Item_T.cpp \
  DSRT_Dispatch_Item_T.h \
  DSRT_Dispatch_Item_T.i \
  DSRT_Dispatcher_Impl_T.cpp \
  DSRT_Dispatcher_Impl_T.h \
  DSRT_Dispatcher_Impl_T.i \
  DSRT_Sched_Queue_T.cpp \
  DSRT_Sched_Queue_T.h \
  Default_Dispatcher_Impl.h \
  Default_Dispatcher_Impl.i \
  Dispatcher_Impl.h \
  Dispatcher_Impl.i \
  Dispatcher_Task.h \
  Dispatcher_Task.i \
  Kokyu.h \
  Kokyu.i \
  Kokyu_defs.h \
  Kokyu_defs.i \
  Kokyu_dsrt.cpp \
  Kokyu_dsrt.h \
  Kokyu_dsrt.i

pkgconfig_DATA = Kokyu.pc

Kokyu.pc: ${top_builddir}/config.status ${srcdir}/Kokyu.pc.in
	${top_builddir}/config.status --file $@:${srcdir}/Kokyu.pc.in

EXTRA_DIST = \
  Kokyu.pc.in


## Clean up template repositories, etc.
clean-local:
	-rm -f *~ *.bak *.rpo *.sym lib*.*_pure_* core core.*
	-rm -f gcctemp.c gcctemp so_locations *.ics
	-rm -rf cxx_repository ptrepository ti_files
	-rm -rf templateregistry ir.out
	-rm -rf ptrepository SunWS_cache Templates.DB
