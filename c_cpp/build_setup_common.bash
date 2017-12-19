#
# --------------------------------------------------------
#
# --------------------------------------------------------
#
declare -x PROJECT_ROOT=`pwd`
declare -x ACE_ROOT=$PROJECT_ROOT/common/c_cpp/libsrc/thirdparty/ACE/ACE_wrappers
echo "setting PROJECT_ROOT=$PROJECT_ROOT"
echo "setting ACE_ROOT=$ACE_ROOT"

# Library types (static,shared)
declare -x static_libs_only=
declare -x shared_libs_only=1
#declare -x no_hidden_visibility=1

# Database Vendor types
declare -x DATABASE_TYPE=MySQL

#
declare -x THIRDPTYBASE="$PROJECT_ROOT/common/c_cpp/libsrc/thirdparty"
declare -x THIRDPTYLIBBASE="$PROJECT_ROOT/common/c_cpp/lib/thirdparty"
declare -x NDSBASE="$PROJECT_ROOT/common/c_cpp/libsrc/netdatasys"
#
declare -x LD_LIBRARY_PATH="$LD_LIBRARY_PATH:$ACE_ROOT/ace:$THIRDPTYBASE/ds_and_algos/gnu_avl"
declare -x LD_LIBRARY_PATH="$LD_LIBRARY_PATH:$NDSBASE/base:$PROJECT_ROOT/include"
declare -x LD_LIBRARY_PATH="$LD_LIBRARY_PATH:$THIRDPTYBASE/netsnmp/netsnmp/snmplib/.libs"
declare -x LD_LIBRARY_PATH="$LD_LIBRARY_PATH:$THIRDPTYLIBBASE/gsoap/linux/lib"
declare -x LD_LIBRARY_PATH="$LD_LIBRARY_PATH:$THIRDPTYLIBBASE/mysql/mysql/linux"
declare -x LD_LIBRARY_PATH="$LD_LIBRARY_PATH:$THIRDPTYBASE/xerces/xerces"
declare -x LD_LIBRARY_PATH="$LD_LIBRARY_PATH:$THIRDPTYBASE/xerces/xerces/lib"
declare -x LD_LIBRARY_PATH="$LD_LIBRARY_PATH:$PROJECT_ROOT/project_tiger"
#
#
declare -x CA_HOME=/home/prem/cvs/src/project_tiger
