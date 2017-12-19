# ======================================================================
# $Source: /opt/projects/CvsRoot/src/common/c_cpp/libsrc/thirdparty/opensnmp/opensnmp/dist/Makefile,v $
# $Revision: 1.1.1.1 $
# $Date: 2006/04/05 02:29:58 $
# $Author: prem $
# $Name:  $
# $State: Exp $
# ======================================================================

# Makefile for generating rpm package files (.rpm) for opensnmp.

# GNU 'make'-specific features are used in this makefile.
# Other makes may or may not work with it.
# See http://www.gnu.org/manual/make/index.html for details.

SHELL = /bin/sh

# Define the RPM package name
NAME = opensnmp

# Define the default RPM release number.
VERSION = 0.4.2
RELEASE = 1

VER=$(VERSION)
REL=$(RELEASE)

# Define the RPM architecture, i.e., 'noarch', 'i386', 'i686', etc.
ARCH = i386

# Directory structure expected by 'rpm' program
RPM_BASE := $(PWD)/rpm

RPM_TREE := \
    $(RPM_BASE)/BUILD \
    $(RPM_BASE)/RPMS \
    $(RPM_BASE)/SOURCES \
    $(RPM_BASE)/SPECS \
    $(RPM_BASE)/SRPMS

.PHONY:	all clean rpm_files binary_rpm source_rpm all_rpm

all:	all_rpm_files

# Delete all generated files.
clean:
	rm -rf $(RPM_BASE)

PKG_VER := $(NAME)-$(VER)

GZIP_TAR := $(RPM_BASE)/SOURCES/$(PKG_VER).tar.gz

gzip_tar:	$(GZIP_TAR)

# --------------------------------------------------------------------
# Macros and rules for updating the binary and source RPM package files.
# --------------------------------------------------------------------
# Redefine '%_topdir' to allow an RPM package file to be built anywhere,
# instead of in the /usr/src/redhat directory tree.
#
# Don't want 'rpmbuild' to strip your executable programs?
# Then add this line:
#   --define='_os_install_post /usr/lib/rpm/brp-compress' \
# to the RPM_OPTS macro definition.  This will eliminate the 
# stripping of binaries, but still retain the compression of
# manual pages.
#

RPM_OPTS := \
    --define='_topdir $(PWD)/rpm' \
    --define='version $(VER)' \
    --define='release $(REL)' \
    --target $(ARCH)

SPEC_FILE  := $(NAME).spec
RPM_SPEC   := $(RPM_BASE)/SPECS/$(SPEC_FILE)

BINARY_RPM := $(RPM_BASE)/RPMS/$(ARCH)/$(PKG_VER)-$(REL).$(ARCH).rpm
SOURCE_RPM := $(RPM_BASE)/SRPMS/$(PKG_VER)-$(REL).src.rpm

rpm_files:	$(GZIP_TAR) all_rpm_files
binary_rpm:	$(BINARY_RPM)
source_rpm:	$(SOURCE_RPM)

all_rpm_files:	$(RPM_TREE) $(RPM_SPEC)
	(cd $(RPM_BASE)/SPECS; \
	rpmbuild -ba $(RPM_OPTS) $(SPEC_FILE))

$(BINARY_RPM):	$(RPM_TREE) $(RPM_SPEC)
	(cd $(RPM_BASE)/SPECS; \
	rpmbuild -bb $(RPM_OPTS) $(SPEC_FILE))

$(SOURCE_RPM):	$(RPM_TREE) $(RPM_SPEC)
	(cd $(RPM_BASE)/SPECS; \
	rpmbuild -bs $(RPM_OPTS) $(SPEC_FILE))

$(RPM_SPEC):	$(RPM_BASE)/SPECS $(SPEC_FILE)
	cp $(SPEC_FILE) $@

$(RPM_TREE):
	mkdir -p $@

$(SPEC_FILE):
	@echo "ERROR: missing '$(SPEC_FILE)' in the current directory"
	@exit 1

FORCE:

# === End of Makefile === #
