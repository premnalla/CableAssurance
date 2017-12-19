#!/bin/sh

#
export AS_HOME; AS_HOME=/mnt/j2ee
export JAVA_HOME; JAVA_HOME=$AS_HOME/jdk
export AS_INSTALL; AS_INSTALL=$AS_HOME
export AS_IMQ_LIB; AS_IMQ_LIB=$AS_HOME/imq/lib
export AS_WEBSERVICES_LIB; AS_WEBSERVICES_LIB=$AS_HOME/lib
export AS_DEF_DOMAINS_PATH; AS_DEF_DOMAINS_PATH=$AS_HOME/domains
export AS_CONF; AS_CONF=$AS_HOME/config
export HOSTNAME; HOSTNAME=localhost

# Set Application Server environement PATH
. $AS_CONF/asenv.conf

JVM_CLASSPATH=.:$JAVA_HOME/lib/tools.jar:$AS_HOME/lib/j2ee.jar:$AS_INSTALL/lib/appserv-rt.jar:$AS_INSTALL/lib/appserv-ext.jar:$AS_INSTALL/lib/appserv-cmp.jar:$AS_WEBSERVICES_LIB/mail.jar:$AS_INSTALL/lib/appserv-admin.jar:$AS_IMQ_LIB/imq.jar:$AS_IMQ_LIB/imqadmin.jar:$AS_IMQ_LIB/fscontext.jar:$AS_WEBSERVICES_LIB/activation.jar

LD_LIBRARY_PATH=$AS_INSTALL/lib:$AS_IMQ_LIB:$AS_NSS:${LD_LIBRARY_PATH}
export LD_LIBRARY_PATH

# for start_fm_simulator
TMP_CLASSPATH=../lib/oss_common_spec-1.0.jar:../lib/oss_quality_of_service_spec-1.0.jar
TMP_CLASSPATH=$TMP_CLASSPATH:$JAVA_HOME/lib/tools.jar
# for run_fm_client
TMP_CLASSPATH=$TMP_CLASSPATH:../lib/ossj_qos_client.jar
# for start_pm_simulator
TMP_CLASSPATH=$TMP_CLASSPATH:../lib/ossj_qos_pm_eis.jar
TMP_CLASSPATH=$TMP_CLASSPATH:../lib/ossj_qos_pm_ri.jar
TMP_CLASSPATH=$TMP_CLASSPATH:../lib/xercesImpl.jar:../lib/xmlParserAPIs.jar

#Set the CLASSPATH
#
CLIENT_CLASSPATH=$AS_DEF_DOMAINS_PATH/domain1/applications/j2ee-apps/ossj_qos_ri/ossj_qos_riClient.jar

CLASSPATH=$TMP_CLASSPATH:$JVM_CLASSPATH:$CLIENT_CLASSPATH
export CLASSPATH

# Uncomment the 2 following Lines to display the CLASSPATH
#echo "Info> CLASSPATH="
echo $CLASSPATH | awk -F: '{for (i=1;i <= NF;i++) printf("	%d = %s\n",i,$i);}'

