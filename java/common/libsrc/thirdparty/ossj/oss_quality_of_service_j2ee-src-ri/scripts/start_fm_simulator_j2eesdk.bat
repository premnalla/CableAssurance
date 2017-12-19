@echo off
rem --------------------------------------------------------------------------
rem Set user-defined variables.
rem --------------------------------------------------------------------------
call env_j2eesdk.bat
rem --------------------------------------------------------------------------
echo "Starting fm_simulator..."
rem --------------------------------------------------------------------------

%JAVA_HOME%\bin\java -Dorg.omg.CORBA.ORBInitialHost=%HOSTNAME% -Dorg.omg.CORBA.ORBInitialPort=3700 -Djava.library.path=%LD_LIBRARY_PATH% ossj.qos.fmri.BackEndEventProducer %%1

