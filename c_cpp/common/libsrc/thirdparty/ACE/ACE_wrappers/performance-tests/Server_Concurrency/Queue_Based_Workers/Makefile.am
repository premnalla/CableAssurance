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

## Makefile.Svr_Conc_Queue_Based_Workers.am 
noinst_PROGRAMS = workers

workers_CPPFLAGS = \
  -I$(ACE_ROOT) \
  -I$(ACE_BUILDDIR)

workers_SOURCES = \
  workers.cpp \
  RT_CORBA_Workers.h

workers_LDADD = \
 $(top_builddir)/ace/libACE.la

## Makefile.Svr_Conc_Queue_Based_Workers_RTCorba.am 
noinst_PROGRAMS += RT_CORBA_Workers

RT_CORBA_Workers_CPPFLAGS = \
  -I$(ACE_ROOT) \
  -I$(ACE_BUILDDIR)

RT_CORBA_Workers_SOURCES = \
  RT_CORBA_Workers.cpp \
  RT_CORBA_Workers.h

RT_CORBA_Workers_LDADD = \
 $(top_builddir)/ace/libACE.la

## Clean up template repositories, etc.
clean-local:
	-rm -f *~ *.bak *.rpo *.sym lib*.*_pure_* core core.*
	-rm -f gcctemp.c gcctemp so_locations *.ics
	-rm -rf cxx_repository ptrepository ti_files
	-rm -rf templateregistry ir.out
	-rm -rf ptrepository SunWS_cache Templates.DB
