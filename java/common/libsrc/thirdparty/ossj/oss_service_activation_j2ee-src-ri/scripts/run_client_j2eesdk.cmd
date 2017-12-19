@echo off
call env_j2eesdk.cmd

REM ----------------------------------------------------------------------------
REM see http://docs.sun.com/source/819-0079/dgacc.html#wp1022105 on 
REM information, how to run a J2EE stand-alone client
REM ----------------------------------------------------------------------------

set command=%AS_JAVA%\bin\java -classpath %CLASSPATH% %JNDI_ARGS% com.nokia.oss.ossj.sa.client.GenericTextClient -l3 %1 %2 %3 %4 %5 %6
echo %command%
%command%


