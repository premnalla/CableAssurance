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

all: Gateway_Peer gateway_peerd

.PHONY: Gateway_Peer
Gateway_Peer:
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Gateway_Peer

.PHONY: gateway_peerd
gateway_peerd: Gateway_Peer
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.gateway_peerd

REMAINING_TARGETS := $(subst all, , $(TARGETS_NESTED:.nested=)) $(CUSTOM_TARGETS)
$(REMAINING_TARGETS):
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.Gateway_Peer $(@)
	$(KEEP_GOING)@$(MAKE) -f GNUmakefile.gateway_peerd $(@)

