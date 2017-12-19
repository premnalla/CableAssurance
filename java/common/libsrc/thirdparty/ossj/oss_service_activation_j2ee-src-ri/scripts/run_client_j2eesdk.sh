#! /bin/ksh

. ./env_j2eesdk.sh

COMMAND="$AS_JAVA/bin/java -classpath $CLASSPATH $JNDI_ARGS com.nokia.oss.ossj.sa.client.GenericTextClient -l3 $*"

echo "$COMMAND"
$COMMAND


