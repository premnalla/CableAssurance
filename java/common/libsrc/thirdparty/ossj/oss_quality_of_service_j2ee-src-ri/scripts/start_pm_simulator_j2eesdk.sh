#!/bin/sh
# --------------------------------------------------------------------------
# Set user-defined variables.
# --------------------------------------------------------------------------
BASE_DIR=`dirname $0`
. $BASE_DIR/env_j2eesdk.sh

ABS_PATH=`pwd`
if [ ! -r $ABS_PATH/eis/eis.properties ] ; then
  echo "eis.properties not found from $ABS_PATH/eis/eis.properties"
  exit 1
fi

echo "ABS_PATH=$ABS_PATH"

# --------------------------------------------------------------------------
# config the eis properties file automatically
# --------------------------------------------------------------------------

echo "#LOG PROPERTIES" > $ABS_PATH/eis/eis.properties
echo "LOG_LEVEL = 1000" >> $ABS_PATH/eis/eis.properties
echo "#LOG_FILE  = c:/OSS_QOS/pmreports/logfile.txt" >> $ABS_PATH/eis/eis.properties
echo "LOG_FILE  = $ABS_PATH/../pmreports/logfile.txt" >> $ABS_PATH/eis/eis.properties
echo "" >> $ABS_PATH/eis/eis.properties
echo "#Network Model" >> $ABS_PATH/eis/eis.properties
echo "#NETWORK_MODEL = file:////mnt/MY_QOS_AS7/oss_quality_of_service-1_0_1-src-ri_s1as7/scripts/eis/network_model/Network.xml" >> $ABS_PATH/eis/eis.properties
echo "NETWORK_MODEL = file:///$ABS_PATH/eis/network_model/Network.xml" >> $ABS_PATH/eis/eis.properties
echo "" >> $ABS_PATH/eis/eis.properties
echo "#Network Model Data" >> $ABS_PATH/eis/eis.properties
echo "#NETWORK_MODEL_DATA_DIR = file:///c:/bea/wlserver6.0/config/mydomain/data/demo/" >> $ABS_PATH/eis/eis.properties
echo "#NETWORK_MODEL_DATA_DIR = c:/OSS_QOS/qospmri/network_model/data/demo/" >> $ABS_PATH/eis/eis.properties
echo "NETWORK_MODEL_DATA_DIR = $ABS_PATH/eis/network_model/data/demo/" >> $ABS_PATH/eis/eis.properties
echo "" >> $ABS_PATH/eis/eis.properties
echo "#A scale factor that specifies how many times faster time should" >> $ABS_PATH/eis/eis.properties
echo "#proceed in the system. Time could be speeded up in demo situations." >> $ABS_PATH/eis/eis.properties
echo "#TIME_SPEEDUP_FACTOR = 60" >> $ABS_PATH/eis/eis.properties
echo "TIME_SPEEDUP_FACTOR = 1" >> $ABS_PATH/eis/eis.properties
echo "" >> $ABS_PATH/eis/eis.properties
echo "#End date for the ScaledTimeGMT class. This is a class written by" >> $ABS_PATH/eis/eis.properties
echo "#NEC used for time transformation. " >> $ABS_PATH/eis/eis.properties
echo "END_DATE = 2004-12-01_00.00.00" >> $ABS_PATH/eis/eis.properties
echo "" >> $ABS_PATH/eis/eis.properties
echo "#SWITCH PM PROPERTY MODE" >> $ABS_PATH/eis/eis.properties
echo "PM_PROPERTY_MODE		   = true	" >> $ABS_PATH/eis/eis.properties
echo "" >> $ABS_PATH/eis/eis.properties
echo "#PM HOME INTERFACE" >> $ABS_PATH/eis/eis.properties
echo "PM_INITIAL_CONTEXT_FACTORY = com.sun.appserv.naming.S1ASCtxFactory" >> $ABS_PATH/eis/eis.properties
echo "PM_PROVIDER_URL            = iiop://localhost:3700" >> $ABS_PATH/eis/eis.properties
echo "PM_SECURITY_PRINCIPAL      = guest" >> $ABS_PATH/eis/eis.properties
echo "PM_SECURITY_CREDENTIALS    = guest" >> $ABS_PATH/eis/eis.properties
echo "" >> $ABS_PATH/eis/eis.properties
echo "#JMS PROPERTIES" >> $ABS_PATH/eis/eis.properties
echo "JMS_INITIAL_CONTEXT_FACTORY = com.sun.appserv.naming.S1ASCtxFactory" >> $ABS_PATH/eis/eis.properties
echo "JMS_PROVIDER_URL            = iiop://localhost:3700" >> $ABS_PATH/eis/eis.properties
echo "JMS_SECURITY_PRINCIPAL      = guest" >> $ABS_PATH/eis/eis.properties
echo "JMS_SECURITY_CREDENTIALS    = guest" >> $ABS_PATH/eis/eis.properties
echo "" >> $ABS_PATH/eis/eis.properties
echo "JMS_CONNECTION_FACTORY      = System/Gothenburg/ApplicationType/PerformanceMonitor/Application/1-0;1-0;JSR90RIPM/Comp/TopicConnectionFactory" >> $ABS_PATH/eis/eis.properties
echo "JMS_TOPIC                   = System/Gothenburg/ApplicationType/PerformanceMonitor/Application/1-0;1-0;JSR90RIPM/Comp/JVTEventTopic" >> $ABS_PATH/eis/eis.properties
echo "JMS_SECURITY_USERNAME       = guest" >> $ABS_PATH/eis/eis.properties
echo "JMS_SECURITY_PASSWORD       = guest" >> $ABS_PATH/eis/eis.properties
echo "" >> $ABS_PATH/eis/eis.properties
echo "# Pointers where to get the performance data" >> $ABS_PATH/eis/eis.properties
echo "# which is sent in the FILE_SINGLE notification." >> $ABS_PATH/eis/eis.properties
echo "#FTP_URL                     = ftp://user:passwd@host/temp/" >> $ABS_PATH/eis/eis.properties
echo "FTP_URL                     = file:$ABS_PATH/../pmreports/" >> $ABS_PATH/eis/eis.properties
echo "" >> $ABS_PATH/eis/eis.properties
echo "# Local dir where to put the generated performance data files." >> $ABS_PATH/eis/eis.properties
echo "#PM_DATA_PATH                = C:\\OSS_QOS\\pmreports\\" >> $ABS_PATH/eis/eis.properties
echo "PM_DATA_PATH                = $ABS_PATH/../pmreports/" >> $ABS_PATH/eis/eis.properties
echo "" >> $ABS_PATH/eis/eis.properties
echo "#Application Distinguished Name" >> $ABS_PATH/eis/eis.properties
echo "APPLICATION_DN		    = System/Gothenburg/ApplicationType/PerformanceMonitor/Application/1-0;1-0;JSR90RIPM	" >> $ABS_PATH/eis/eis.properties

# --------------------------------------------------------------------------
# start the simulator
# --------------------------------------------------------------------------
$JAVA_HOME/bin/java -hotspot -ms64m -mx64m -Dcom.sun.aas.imqLib=$AS_IMQ_LIB -Djava.security.policy=file:eis/rmi.policy -Dorg.omg.CORBA.ORBInitialHost=$HOSTNAME -Dorg.omg.CORBA.ORBInitialPort=3700 -Djava.library.path=$LD_LIBRARY_PATH   -classpath "$CLASSPATH" ossj.qos.ri.pm.measurement.eis.EisSimulatorImpl eis/eis.properties

