####### This is the input file for automake, which will generate Makefile.in ##########
## not a GNU package. You can remove this line, if
## you have all needed files, that a GNU package needs
AUTOMAKE_OPTIONS = foreign 1.4

noinst_PROGRAMS=calcclient calcserver
SOAPHEADER=calc.h

include ../Makefile.defines
SOAP_NS=calc.nsmap calc.wsdl
include ../Makefile.c_rules

calcclient_SOURCES = calcclient.c $(SOAPHEADER) $(SOAP_C_CLIENT)
calcclient_LDADD=$(SOAP_C_LIB) $(SAMPLE_EXTRA_LIBS) -lm

calcserver_SOURCES = calcserver.c $(SOAPHEADER) $(SOAP_C_SERVER)
calcserver_LDADD=$(SOAP_C_LIB) $(SAMPLE_EXTRA_LIBS) -lm
