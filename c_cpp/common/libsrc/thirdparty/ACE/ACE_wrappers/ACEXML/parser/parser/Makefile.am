##  Process this file with automake to create Makefile.in
##
## Makefile.am,v 1.10 2005/04/22 18:14:30 jtc Exp
##
## This file was generated by MPC.  Any changes made directly to
## this file will be lost the next time it is generated.
##
## MPC Command:
## ./bin/mwc.pl -include /home/jtc/ACE/ACE-config3/MPC/config -include /home/jtc/ACE/ACE-config3/MPC/templates -type automake ACE.mwc

includedir = @includedir@/ACEXML/parser/parser
pkgconfigdir = @libdir@/pkgconfig

ACE_BUILDDIR = $(top_builddir)
ACE_ROOT = $(top_srcdir)


lib_LTLIBRARIES = libACEXML_Parser.la

libACEXML_Parser_la_CPPFLAGS = \
  -I$(ACE_ROOT) \
  -I$(ACE_BUILDDIR) \
  -DACEXML_PARSER_BUILD_DLL

libACEXML_Parser_la_SOURCES = \
  Entity_Manager.cpp \
  Parser.cpp \
  ParserContext.cpp \
  ParserInternals.cpp

libACEXML_Parser_la_LDFLAGS = \
 -version-number @ACE_MAJOR@:@ACE_MINOR@:@ACE_BETA@

libACEXML_Parser_la_LIBADD = \
  $(ACE_BUILDDIR)/ACEXML/common/libACEXML.la \
  $(ACE_BUILDDIR)/ace/libACE.la

nobase_include_HEADERS = \
  Entity_Manager.h \
  Entity_Manager.i \
  Parser.h \
  Parser.i \
  ParserContext.h \
  ParserContext.inl \
  ParserInternals.h \
  Parser_export.h

pkgconfig_DATA = ACEXML_Parser.pc

ACEXML_Parser.pc: ${top_builddir}/config.status ${srcdir}/ACEXML_Parser.pc.in
	${top_builddir}/config.status --file $@:${srcdir}/ACEXML_Parser.pc.in

EXTRA_DIST = \
  ACEXML_Parser.pc.in


## Clean up template repositories, etc.
clean-local:
	-rm -f *~ *.bak *.rpo *.sym lib*.*_pure_* core core.*
	-rm -f gcctemp.c gcctemp so_locations *.ics
	-rm -rf cxx_repository ptrepository ti_files
	-rm -rf templateregistry ir.out
	-rm -rf ptrepository SunWS_cache Templates.DB
