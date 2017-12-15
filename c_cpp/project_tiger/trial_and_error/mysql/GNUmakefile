#-------------------------------------------------------------------------
#
#-------------------------------------------------------------------------
MAKEFILE = GNUmakefile

ifeq ($(findstring k,$(MAKEFLAGS)),k)
  KEEP_GOING = -
endif

include $(PROJECT_ROOT)/include/makeinclude/macros.GNU

all: mysql intPkTable

.PHONY: mysql
mysql:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.mysql

.PHONY: intPkTable
intPkTable:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.intPkTable

REMAINING_TARGETS := $(subst all, , $(TARGETS_NESTED:.nested=)) $(CUSTOM_TARGETS)
$(REMAINING_TARGETS):
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.mysql $(@)
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.intPkTable $(@)

