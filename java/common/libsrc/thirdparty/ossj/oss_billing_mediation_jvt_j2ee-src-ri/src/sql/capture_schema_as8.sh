#!/bin/ksh
AS_HOME=
CLASSPATH=$CLASSPATH:$AS_HOME/pointbase/lib/pbclient.jar
export CLASSPATH
$AS_HOME/bin/capture-schema -dburl jdbc:pointbase:server://localhost/pointbase -username PBPUBLIC -password PBPUBLIC -driver com.pointbase.jdbc.jdbcUniversalDriver -out ossjbilling_mediationri.dbschema
