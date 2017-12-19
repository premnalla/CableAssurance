#!/bin/sh
# --------------------------------------------------------------------------
# Set user-defined variables.
# --------------------------------------------------------------------------
BASE_DIR=`dirname $0`
. $BASE_DIR/env_j2eesdk.sh


$JAVA_HOME/bin/java -Dcom.sun.aas.imqLib=$AS_IMQ_LIB -Djava.security.policy=file:eis/rmi.policy -Dorg.omg.CORBA.ORBInitialHost=$HOSTNAME -Dorg.omg.CORBA.ORBInitialPort=3700 -Djava.library.path=$LD_LIBRARY_PATH -classpath "$CLASSPATH" ossj.qos.ri.pm.testclient.PmClient client.properties

