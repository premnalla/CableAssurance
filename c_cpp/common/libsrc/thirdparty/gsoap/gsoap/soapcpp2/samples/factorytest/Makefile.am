####### This is the input file for automake, which will generate Makefile.in ##########
## not a GNU package. You can remove this line, if
## you have all needed files, that a GNU package needs
AUTOMAKE_OPTIONS = foreign 1.4

noinst_PROGRAMS=factorytest
SOAPHEADER=factorytest.h

include ../Makefile.defines
SOAP_NS=factorytest.nsmap factorytest.wsdl
include ../Makefile.cpp_rules

factorytest_SOURCES = factorytest.cpp $(SOAPHEADER) $(SOAP_CPP_CLIENT)

