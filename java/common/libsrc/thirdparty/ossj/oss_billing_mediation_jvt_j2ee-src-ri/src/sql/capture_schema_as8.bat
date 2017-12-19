set AS_HOME=D:\j2eesdk_1.4.01\AppServer
set CLASSPATH=%CLASSPATH%;%AS_HOME%\pointbase\lib\pbclient.jar

%AS_HOME%\bin\capture-schema.bat -dburl jdbc:pointbase:server://localhost/pointbase -username PBPUBLIC -password PBPUBLIC -driver com.pointbase.jdbc.jdbcUniversalDriver -out ossjbilling_mediationri.dbschema


