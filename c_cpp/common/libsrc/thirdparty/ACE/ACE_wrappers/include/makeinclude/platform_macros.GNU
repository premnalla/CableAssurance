#
#
#
ifeq ($(OS_MAJOR),LINUX)
  include $(ACE_ROOT)/include/makeinclude/platform_linux.GNU
  CFLAGS += -DLINUX
  CPPFLAGS += -DLINUX
else
  ifeq ($(OS_MAJOR),SOLARIS)
    include $(ACE_ROOT)/include/makeinclude/platform_sunos5_g++.GNU
    CFLAGS += -DSOLARIS
    CPPFLAGS += -DSOLARIS
  endif
endif
