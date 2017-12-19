#!/bin/ksh
export CLASSPATH=$CLASSPATH:/opt/SUNWappserver7/pointbase/client_tools/lib/pbclient42RE.jar
/opt/SUNWappserver7/bin/capture-schema -dburl jdbc:pointbase:server://localhost/pointbase -username PBPUBLIC -password PBPUBLIC -driver com.pointbase.jdbc.jdbcUniversalDriver -out /sa/src/sql/ossjsari_as7.dbschema
