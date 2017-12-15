#-------------------------------------------------------------------------
#
#-------------------------------------------------------------------------
MAKEFILE = GNUmakefile

ifeq ($(findstring k,$(MAKEFLAGS)),k)
  KEEP_GOING = -
endif

include $(PROJECT_ROOT)/include/makeinclude/macros.GNU

all: snmppoll snmpbulkget snmpbulkwalk

.PHONY: snmppoll snmpbulkget snmpbulkwalk
snmppoll:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.snmppoll

snmpbulkget:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.snmpbulkget

snmpbulkwalk:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.snmpbulkwalk

REMAINING_TARGETS := $(subst all, , $(TARGETS_NESTED:.nested=)) $(CUSTOM_TARGETS)
$(REMAINING_TARGETS):
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.snmppoll $(@)
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.snmpbulkget $(@)
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.snmpbulkwalk $(@)

