#!/bin/sh -x
# --------------------------------------------------------------------------
# Set user-defined variables.
# --------------------------------------------------------------------------
BASE_DIR=`dirname $0`
. $BASE_DIR/env_j2eesdk.sh

# --------------------------------------------------------------------------
echo "Starting fm_simulator..."
# --------------------------------------------------------------------------

$JAVA_HOME/bin/java -Dorg.omg.CORBA.ORBInitialHost=$HOSTNAME -Dorg.omg.CORBA.ORBInitialPort=3700 -Djava.library.path=$LD_LIBRARY_PATH ossj.qos.fmri.BackEndEventProducer $1

