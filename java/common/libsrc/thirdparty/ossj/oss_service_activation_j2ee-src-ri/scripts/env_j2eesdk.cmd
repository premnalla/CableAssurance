@echo off
REM --- Location of the J2EE SDK Application Server ----------------------------
REM ----------------------------------------------------------------------------
SET AS_HOME=C:\Sun\AppServer
SET AS_DOMAIN=domain1
CALL %AS_HOME%\config\asenv.bat

REM --- Initial Context Settings -----------------------------------------------
REM ----------------------------------------------------------------------------
SET HOSTNAME=localhost
SET PORTNUMBER=3700
SET JNDI_ARGS=-Djava.naming.noProps=true -Dorg.omg.CORBA.ORBInitialHost=%HOSTNAME% -Dorg.omg.CORBA.ORBInitialPort=%PORTNUMBER%

REM --- OSS/J Standardized Interfaces ------------------------------------------
REM ----------------------------------------------------------------------------
SET SA_RI=..
SET OSS_SA_SPEC=%SA_RI%\lib\ossjsa.jar

REM --- OSS/J SA Reference Implementation --------------------------------------
REM ----------------------------------------------------------------------------
REM If this client application is running on a different host than the J2EE 
REM server, you will have to copy the jar from the server host located at
REM %AS_DEF_DOMAINS_PATH%\%AS_DOMAIN%\applications\j2ee-apps\...
REM                                           ...ossj_sa_ri\ossj_sa_riClient.jar
REM to the client host at ..\lib\ossj_sa_riClient.jar and uncomment the second
REM line after this (line 29)
REM ----------------------------------------------------------------------------
SET OSS_SA_IMPL=%AS_DEF_DOMAINS_PATH%\%AS_DOMAIN%\applications\j2ee-apps\ossj_sa_ri\ossj_sa_riClient.jar
REM set OSS_SA_IMPL=..\lib\ossj_sa_riClient.jar

REM --- OSS/J SA Reference Implementation Test Client --------------------------
REM ----------------------------------------------------------------------------
SET OSS_SA_TESTCLIENT=%SA_RI%\lib\ossj_sa_ri_genericClient.jar

REM --- Classpath Settings -----------------------------------------------------
REM ----------------------------------------------------------------------------
SET JMS_SUPPORT_CLASSPATH=%AS_HOME%\lib\appserv-rt.jar;%AS_HOME%\lib\j2ee.jar;%AS_HOME%\lib\install\applications\jmsra\imqjmsra.jar
SET ADDITIONAL_CP=%AS_HOME%\lib\appserv-admin.jar
SET CLASSPATH=%JMS_SUPPORT_CLASSPATH%;%ADDITIONAL_CP%;%OSS_SA_SPEC%;%OSS_SA_TESTCLIENT%;%OSS_SA_IMPL%

