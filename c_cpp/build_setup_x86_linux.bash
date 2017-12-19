#
# --------------------------------------------------------
#
# --------------------------------------------------------
#
source ./build_setup_common.bash

# --------------------------------------------------------
#
# Enumeration of all possiblilities
# CPU's: AMD32, Intel x86, AMD64, Intel EM64T, SPARC(64)
#    Categories: x86-32 (INTEL: i386, i486, i586, i686)
#                       (AMD: 32)
#    Categories: x86-64 (INTEL: EM64T)
#                       (AMD: 64)
# OS's: Solaris, Linux(RedHat, SuSe, Debian, etc.,), Win32
#
# --------------------------------------------------------

# CPU Architecture
# CPU_MAJOR valid values = {SPARC,X86}
declare -x CPU_MAJOR=X86
# CPU_MINOR valid values = {32,X86}
declare -x CPU_MINOR=32
echo "setting CPU_MAJOR=$CPU_MAJOR and CPU_MINOR=$CPU_MINOR"

# --------------------------------------------------------
# OBSOLETE: covered by CPU_MINOR
# --------------------------------------------------------
# CPU Architecture
# valid values = {32,64)
#declare -x CPU_ARCH_MAJOR=32
#declare -x CPU_ARCH_MINOR=

# Operating Systems
# valid values = {SOLARIS,LINUX,WIN32}
declare -x OS_MAJOR=LINUX
declare -x OS_MINOR=
declare -x OS_MAJOR_VERSION=2
declare -x OS_MINOR_VERSION=6
declare -x OS_DOT_VERSION=
echo "setting OS_MAJOR=$OS_MAJOR and OS_MINOR=$OS_MINOR"
echo "setting OS_MAJOR_VERSION=$OS_MAJOR_VERSION, OS_MINOR_VERSION=$OS_MINOR_VERSION \
and OS_DOT_VERSION=$OS_DOT_VERSION"

display_build_env ()
{
  echo ""
  echo "PROJECT_ROOT=$PROJECT_ROOT"
  echo "ACE_ROOT=$ACE_ROOT"
  echo "CPU_MAJOR=$CPU_MAJOR and CPU_MINOR=$CPU_MINOR"
  echo "OS_MAJOR=$OS_MAJOR and OS_MINOR=$OS_MINOR"
  echo "OS_MAJOR_VERSION=$OS_MAJOR_VERSION, OS_MINOR_VERSION=$OS_MINOR_VERSION \
and OS_DOT_VERSION=$OS_DOT_VERSION"
  echo "static_libs_only=$static_libs_only"
  echo "shared_libs_only=$shared_libs_only"
  echo "LD_LIBRARY_PATH=$LD_LIBRARY_PATH"
  echo "CA_HOME=$CA_HOME"
  echo ""
}


