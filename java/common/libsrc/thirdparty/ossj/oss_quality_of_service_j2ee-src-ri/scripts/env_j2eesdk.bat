@echo off

rem
set AS_HOME=c:\Sun\AppServer
set JAVA_HOME=%AS_HOME%\jdk
set AS_INSTALL=%AS_HOME%
set AS_IMQ_LIB=%AS_HOME%\imq\lib
set AS_WEBSERVICES_LIB=%AS_HOME%\lib
set AS_DEF_DOMAINS_PATH=%AS_HOME%\domains
set AS_CONF=%AS_HOME%\config
set HOSTNAME=localhost

rem Set Application Server environement PATH
call %AS_HOME%\config\asenv.bat

set JVM_CLASSPATH=.;%JAVA_HOME%\lib\tools.jar;%AS_HOME%\lib\j2ee.jar;%AS_INSTALL%\lib\appserv-rt.jar;%AS_INSTALL%\lib\appserv-ext.jar;%AS_INSTALL%\lib\appserv-cmp.jar;%AS_WEBSERVICES_LIB%\mail.jar;%AS_INSTALL%\lib\appserv-admin.jar;%AS_IMQ_LIB%\imq.jar;%AS_IMQ_LIB%\imqadmin.jar;%AS_IMQ_LIB%\fscontext.jar;%AS_WEBSERVICES_LIB%\activation.jar

rem for start_fm_simulator
set TMP_CLASSPATH=..\lib\oss_common_spec-1.0.jar;..\lib\oss_quality_of_service_spec-1.0.jar
set TMP_CLASSPATH=%TMP_CLASSPATH%;%JAVA_HOME%\lib\tools.jar
rem for run_fm_client
set TMP_CLASSPATH=%TMP_CLASSPATH%;..\lib\ossj_qos_client.jar
rem for start_pm_simulator
set TMP_CLASSPATH=%TMP_CLASSPATH%;..\lib\ossj_qos_pm_eis.jar
set TMP_CLASSPATH=%TMP_CLASSPATH%;..\lib\ossj_qos_pm_ri.jar
set TMP_CLASSPATH=%TMP_CLASSPATH%;..\lib\xercesImpl.jar;..\lib\xmlParserAPIs.jar

rem Set the CLASSPATH
rem
set CLIENT_CLASSPATH=%AS_DEF_DOMAINS_PATH%\domain1\applications\j2ee-apps\ossj_qos_ri\ossj_qos_riClient.jar

set CLASSPATH=%TMP_CLASSPATH%;%JVM_CLASSPATH%;%CLIENT_CLASSPATH%

rem echo "Info> set CLASSPATH="
rem echo %CLASSPATH%

