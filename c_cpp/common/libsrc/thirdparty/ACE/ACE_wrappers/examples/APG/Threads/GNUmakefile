# -*- makefile -*-
#-------------------------------------------------------------------------
#       GNU ACE Workspace
#
# GNUACEWorkspaceCreator.pm,v 1.36 2006/01/24 12:55:22 elliott_c Exp
#
# This file was generated by MPC.  Any changes made directly to
# this file will be lost the next time it is generated.
#
# MPC Command:
# bin/mwc.pl -exclude TAO/TAO_*.mwc,TAO/CIAO/CIAO_*.mwc -recurse -hierarchy -relative ACE_ROOT=/tmp/ACE_wrappers_stage-7814/ACE_wrappers
#
#-------------------------------------------------------------------------
MAKEFILE = GNUmakefile

ifeq ($(findstring k,$(MAKEFLAGS)),k)
  KEEP_GOING = -
endif

include $(ACE_ROOT)/include/makeinclude/macros.GNU

all: Activate Condition_Variables Guards Message_Blocks Message_Queue Mutexes

.PHONY: Activate
Activate:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Activate

.PHONY: Condition_Variables
Condition_Variables:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Condition_Variables

.PHONY: Guards
Guards:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Guards

.PHONY: Message_Blocks
Message_Blocks:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Message_Blocks

.PHONY: Message_Queue
Message_Queue:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Message_Queue

.PHONY: Mutexes
Mutexes:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Mutexes

REMAINING_TARGETS := $(subst all, , $(TARGETS_NESTED:.nested=)) $(CUSTOM_TARGETS)
$(REMAINING_TARGETS):
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Activate $(@)
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Condition_Variables $(@)
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Guards $(@)
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Message_Blocks $(@)
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Message_Queue $(@)
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Mutexes $(@)

