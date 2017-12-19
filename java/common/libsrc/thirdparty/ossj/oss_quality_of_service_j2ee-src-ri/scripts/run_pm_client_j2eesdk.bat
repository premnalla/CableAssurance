@echo off
rem --------------------------------------------------------------------------
rem Set user-defined variables.
rem --------------------------------------------------------------------------
call env_j2eesdk.bat


%JAVA_HOME%\bin\java -Dcom.sun.aas.imqLib=%AS_IMQ_LIB% -Djava.security.policy=file:eis\rmi.policy -Dorg.omg.CORBA.ORBInitialHost=%HOSTNAME% -Dorg.omg.CORBA.ORBInitialPort=3700 -Djava.library.path=%LD_LIBRARY_PATH% -classpath "%CLASSPATH%" ossj.qos.ri.pm.testclient.PmClient client.properties

