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

## Makefile.SV_Semaphores_1.am 
noinst_PROGRAMS = sem1

sem1_CPPFLAGS = \
  -I$(ACE_ROOT) \
  -I$(ACE_BUILDDIR)

sem1_SOURCES = \
  Semaphores_1.cpp

sem1_LDADD = \
 $(top_builddir)/ace/libACE.la

## Makefile.SV_Semaphores_2.am 
noinst_PROGRAMS += sem2

sem2_CPPFLAGS = \
  -I$(ACE_ROOT) \
  -I$(ACE_BUILDDIR)

sem2_SOURCES = \
  Semaphores_2.cpp

sem2_LDADD = \
 $(top_builddir)/ace/libACE.la

## Clean up template repositories, etc.
clean-local:
	-rm -f *~ *.bak *.rpo *.sym lib*.*_pure_* core core.*
	-rm -f gcctemp.c gcctemp so_locations *.ics
	-rm -rf cxx_repository ptrepository ti_files
	-rm -rf templateregistry ir.out
	-rm -rf ptrepository SunWS_cache Templates.DB
