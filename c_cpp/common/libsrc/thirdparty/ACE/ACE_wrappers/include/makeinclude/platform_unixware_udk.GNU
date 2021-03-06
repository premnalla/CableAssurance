# platform_unixware_udk.GNU,v 1.4 2005/05/16 15:12:28 jwillemsen Exp

# UnixWare V2.2.1.2 with g++ version 2.7.2.2

debug ?= 1
exceptions ?= 1
threads ?= 1

CC              = CC
CXX             = CC

CFLAGS         += -Kthread

#CFLAGS          += -W -Wall -Wpointer-arith
CCFLAGS         += $(CFLAGS) -Tno_implicit,no_auto,none -Wf,--display_error_number

#ifeq ($(exceptions),1)
#  CCFLAGS += -fexceptions
#endif

DCFLAGS         += -g
#DCFLAGS         += -g

OCCFLAGS         += -O2

DLD             = $(CXX) -g
LD              = $(CXX)
LIBS            += -lsocket -lnsl -lgen
PIC             = -KPIC
AR              = ar
ARFLAGS         = ruv
RANLIB          = /bin/true
#SOFLAGS        = -assert pure-text
SOFLAGS         += -G
# SOBUILD - compile into .so directly
SOBUILD         = $(COMPILE.cc) $(PIC) -o $(VSHDIR)$*.so $<
# SOLINK        = $(SOLINK.cc) -o $@ $(LDFLAGS) $<
PRELIB          = @true
