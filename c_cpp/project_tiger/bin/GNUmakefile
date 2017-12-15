#-------------------------------------------------------------------------
#
#-------------------------------------------------------------------------
MAKEFILE = GNUmakefile

ifeq ($(findstring k,$(MAKEFLAGS)),k)
  KEEP_GOING = -
endif

include $(PROJECT_ROOT)/include/makeinclude/macros.GNU

all: CAEngine CAEngineTest CATrapForwarder

.PHONY: CAEngine
CAEngine:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.CAEngine

.PHONY: CAEngineTest
CAEngineTest:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.CAEngineTest

.PHONY: CATrapForwarder
CATrapForwarder:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.TrapForwarder

REMAINING_TARGETS := $(subst all, , $(TARGETS_NESTED:.nested=)) $(CUSTOM_TARGETS)
$(REMAINING_TARGETS):
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.CAEngine $(@)
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.CAEngineTest $(@)
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.TrapForwarder $(@)

