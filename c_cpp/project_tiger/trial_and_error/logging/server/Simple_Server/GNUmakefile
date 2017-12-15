#-------------------------------------------------------------------------
#
#-------------------------------------------------------------------------
MAKEFILE = GNUmakefile

ifeq ($(findstring k,$(MAKEFLAGS)),k)
  KEEP_GOING = -
endif

include $(PROJECT_ROOT)/include/makeinclude/macros.GNU

all: Simple_Server

.PHONY: Simple_Server
Simple_Server:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Simple_Server

REMAINING_TARGETS := $(subst all, , $(TARGETS_NESTED:.nested=)) $(CUSTOM_TARGETS)
$(REMAINING_TARGETS):
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Simple_Server $(@)

