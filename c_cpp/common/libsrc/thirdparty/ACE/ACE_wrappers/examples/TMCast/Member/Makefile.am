##  Process this file with automake to create Makefile.in

ACE_BUILDDIR = $(top_builddir)
ACE_ROOT = $(top_srcdir)

## Makefile.Member.am 

if BUILD_EXCEPTIONS
if BUILD_THREADS

noinst_PROGRAMS = member

member_CPPFLAGS = \
  -I$(ACE_ROOT) \
  -I$(ACE_BUILDDIR) \
  -I$(ACE_ROOT)/protocols

member_SOURCES = \
  member.cpp

member_LDADD = \
  ${ACE_BUILDDIR}/protocols/ace/TMCast/libACE_TMCast.la \
  ${ACE_BUILDDIR}/ace/libACE.la

endif BUILD_THREADS
endif BUILD_EXCEPTIONS

## Clean up template repositories, etc.
clean-local:
	-rm -f *~ *.bak *.rpo *.sym lib*.*_pure_* core core.*
	-rm -f gcctemp.c gcctemp so_locations *.ics
	-rm -rf cxx_repository ptrepository ti_files
	-rm -rf templateregistry ir.out
	-rm -rf ptrepository SunWS_cache Templates.DB
