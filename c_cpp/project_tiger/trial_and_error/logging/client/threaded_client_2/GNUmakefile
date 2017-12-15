#-------------------------------------------------------------------------
#
#-------------------------------------------------------------------------
MAKEFILE = GNUmakefile

ifeq ($(findstring k,$(MAKEFLAGS)),k)
  KEEP_GOING = -
endif

include $(PROJECT_ROOT)/include/makeinclude/macros.GNU

all: Threaded_Client_1

.PHONY: Threaded_Client_1
Threaded_Client_1:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Threaded_Client_1

REMAINING_TARGETS := $(subst all, , $(TARGETS_NESTED:.nested=)) $(CUSTOM_TARGETS)
$(REMAINING_TARGETS):
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Threaded_Client_1 $(@)

