##  Process this file with automake to create Makefile.in
##
## Makefile.am,v 1.1 2004/11/12 22:28:16 shuston Exp
##
## This file was generated by MPC.  Any changes made directly to
## this file will be lost the next time it is generated.
##
## MPC Command:
## /acebuilds/ACE_wrappers-repository/bin/mwc.pl -include /acebuilds/MPC/config -include /acebuilds/MPC/templates -feature_file /acebuilds/ACE_wrappers-repository/local.features -noreldefs -type automake -exclude build,Kokyu

ACE_BUILDDIR = $(top_builddir)
ACE_ROOT = $(top_srcdir)

## Makefile.asnmp_example_get.am 
noinst_PROGRAMS = get

get_CPPFLAGS = \
  -I$(ACE_ROOT) \
  -I$(ACE_BUILDDIR) \
  -I$(ACE_ROOT)/ASNMP

get_SOURCES = \
  get.cpp

get_LDADD = \
 $(top_builddir)/ASNMP/asnmp/libasnmp.la $(top_builddir)/ace/libACE.la

## Makefile.asnmp_example_get_async.am 
noinst_PROGRAMS += get_async

get_async_CPPFLAGS = \
  -I$(ACE_ROOT) \
  -I$(ACE_BUILDDIR) \
  -I$(ACE_ROOT)/ASNMP

get_async_SOURCES = \
  get_async.cpp

get_async_LDADD = \
 $(top_builddir)/ASNMP/asnmp/libasnmp.la $(top_builddir)/ace/libACE.la

## Clean up template repositories, etc.
clean-local:
	-rm -f *~ *.bak *.rpo *.sym lib*.*_pure_* core core.*
	-rm -f gcctemp.c gcctemp so_locations *.ics
	-rm -rf cxx_repository ptrepository ti_files
	-rm -rf templateregistry ir.out
	-rm -rf ptrepository SunWS_cache Templates.DB
