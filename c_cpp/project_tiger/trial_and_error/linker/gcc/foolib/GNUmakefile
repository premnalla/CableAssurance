# -*- Makefile -*-
#----------------------------------------------------------------------------
#       GNU Makefile
#
#----------------------------------------------------------------------------
MAKEFILE         = GNUmakefile
DEPENDENCY_FILE  = .depend.libfoo

## LIB may be set to empty later on in this file
LIB_UNCHECKED    = libfoo.a
LIB              = $(LIB_UNCHECKED)

## SHLIB may be set to empty later on in this file
SHLIB_UNCHECKED  = libfoo.$(SOEXT)
SHLIB            = $(SHLIB_UNCHECKED)

FILES = \
  src/foo.c \
#

#----------------------------------------------------------------------------
#       Include macros and targets
#----------------------------------------------------------------------------
ACE_SHLIBS =

INSLIB ?= ../lib
ifeq ($(INSLIB),.)
  ifeq ($(PWD),)
    PWD=$(shell pwd)
  endif
  INSLIB = $(PWD)
endif
OUTPUT_DIRECTORY = $(INSLIB)

include $(PROJECT_ROOT)/include/makeinclude/wrapper_macros.GNU
## We don't need the ACELIB setting from wrapper_macros.GNU
ACELIB =

ifneq ($(OUTPUT_DIRECTORY),)
all: $(OUTPUT_DIRECTORY)
$(OUTPUT_DIRECTORY):
	-@$(MKDIR) "$(OUTPUT_DIRECTORY)"
endif

LIBCHECK = 1
ifneq ($(LIBCHECK), 1)
  LIB   =
  SHLIB =
  all: lib_warning
endif

LSRC   = $(FILES)

include $(PROJECT_ROOT)/include/makeinclude/macros.GNU
include $(PROJECT_ROOT)/include/makeinclude/rules.common.GNU
include $(PROJECT_ROOT)/include/makeinclude/rules.nonested.GNU
include $(PROJECT_ROOT)/include/makeinclude/rules.lib.GNU
include $(PROJECT_ROOT)/include/makeinclude/rules.local.GNU

CPPFLAGS     += -I./include \
#

ifeq ($(shared_libs),1)
  ifneq ($(SHLIB),)
    CPPFLAGS += -DACE_BUILD_DLL
  endif
endif
ifeq ($(static_libs),1)
  CPPFLAGS += -DACE_AS_STATIC_LIBS
endif

#----------------------------------------------------------------------------
#       Local targets
#----------------------------------------------------------------------------
lib_warning:
	@echo This project will not be built due to the following missing library:
	@echo $(LIBCHECK)

## Some OS's have /bin/test others only have /usr/bin/test
ifeq ($(wildcard /bin/test), /bin/test)
  TEST_EXE = /bin/test
else
ifeq ($(wildcard /usr/bin/test), /usr/bin/test)
  TEST_EXE = /usr/bin/test
endif
endif

realclean: clean
	-$(RM) -rf .obj
	-$(RM) -rf .shobj
	-$(RM) $(LIB_UNCHECKED)
	-$(RM) $(SHLIB_UNCHECKED)
	-$(RM) $(DEPENDENCY_FILE)
	-$(RM) lib*
ifneq ($(GENERATED_DIRTY),)
	-$(RM) -r $(GENERATED_DIRTY)
endif

