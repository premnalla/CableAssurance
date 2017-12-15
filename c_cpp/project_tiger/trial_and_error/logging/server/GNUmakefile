#-------------------------------------------------------------------------
#
#-------------------------------------------------------------------------
MAKEFILE = GNUmakefile

ifeq ($(findstring k,$(MAKEFLAGS)),k)
  KEEP_GOING = -
endif

include $(PROJECT_ROOT)/include/makeinclude/macros.GNU

REMAINING_TARGETS := $(subst all, , $(TARGETS_NESTED:.nested=)) $(CUSTOM_TARGETS)
$(REMAINING_TARGETS):
	$(KEEP_GOING)@cd Acceptor_Server && $(MAKE) -f $(MAKEFILE) $(@)
	$(KEEP_GOING)@cd Simple_Server && $(MAKE) -f $(MAKEFILE) $(@)
