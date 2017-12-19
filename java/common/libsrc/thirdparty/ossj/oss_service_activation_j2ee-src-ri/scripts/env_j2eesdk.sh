#!/bin/ksh
# --- Location of the J2EE SDK Application Server ----------------------------
# ----------------------------------------------------------------------------
AS_HOME=~/SUNWappserver
AS_DOMAIN=domain1
. $AS_HOME/config/asenv.conf

# --- Initial Context Settings -----------------------------------------------
# ----------------------------------------------------------------------------
HOSTNAME=localhost
PORTNUMBER=3700
JNDI_ARGS="-Djava.naming.noProps=true -Dorg.omg.CORBA.ORBInitialHost=$HOSTNAME -Dorg.omg.CORBA.ORBInitialPort=$PORTNUMBER"
export JNDI_ARGS

# --- OSS/J Standardized Interfaces ------------------------------------------
# ----------------------------------------------------------------------------
SA_RI=..
OSS_SA_SPEC=$SA_RI/lib/ossjsa.jar

# --- OSS/J SA Reference Implementation --------------------------------------
# ----------------------------------------------------------------------------
# If this client application is running on a different host than the J2EE 
# server, you will have to copy the jar from the server host located at
# $AS_HOME/domains/domain1/applications/j2ee-apps/ossj_sa_ri/...
#                                                          ossj_sa_riClient.jar
# to the client host at $SA_HOME/lib/ossj_sa_riClient.jar and uncomment the 
# second line after this (line 30)
# ----------------------------------------------------------------------------
OSS_SA_IMPL=$AS_DEF_DOMAINS_PATH/$AS_DOMAIN/applications/j2ee-apps/ossj_sa_ri/ossj_sa_riClient.jar
#OSS_SA_IMPL=$SA_HOME/lib/ossj_sa_riClient.jar

# --- OSS/J SA Reference Implementation Test Client --------------------------
# ----------------------------------------------------------------------------
OSS_SA_TESTCLIENT=$SA_RI/lib/ossj_sa_ri_genericClient.jar

# --- Classpath Settings -----------------------------------------------------
# ----------------------------------------------------------------------------
JMS_SUPPORT_CLASSPATH=$AS_HOME/lib/appserv-rt.jar:$AS_HOME/lib/j2ee.jar:$AS_HOME/lib/install/applications/jmsra/imqjmsra.jar
ADDITIONAL_CP=$AS_HOME/lib/appserv-admin.jar
CLASSPATH=$JMS_SUPPORT_CLASSPATH:$ADDITIONAL_CP:$OSS_SA_SPEC:$OSS_SA_TESTCLIENT:$OSS_SA_IMPL
export CLASSPATH

#echo $CLASSPATH | awk -F: '{for (i=1;i <= NF;i++) printf("%d = %s\n",i,$i);}'
#Library path:
LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$AS_HOME/lib/:$AS_HOME/imq/lib
export LD_LIBRARY_PATH
