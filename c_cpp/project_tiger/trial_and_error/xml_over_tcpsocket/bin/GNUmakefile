#-------------------------------------------------------------------------
#
#-------------------------------------------------------------------------
MAKEFILE = GNUmakefile

ifeq ($(findstring k,$(MAKEFLAGS)),k)
  KEEP_GOING = -
endif

include $(PROJECT_ROOT)/include/makeinclude/macros.GNU

all: client server XMLFileParser

.PHONY: client
client:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Client

.PHONY: server
server:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Server

.PHONY: XMLFileParser
XMLFileParser:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.XMLFileParser

REMAINING_TARGETS := $(subst all, , $(TARGETS_NESTED:.nested=)) $(CUSTOM_TARGETS)
$(REMAINING_TARGETS):
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Client $(@)
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Server $(@)
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.XMLFileParser

