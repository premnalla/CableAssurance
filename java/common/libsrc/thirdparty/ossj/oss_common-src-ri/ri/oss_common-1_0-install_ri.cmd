@echo off
@rem This script can be used to set up your environment for your
@rem own development work with WebLogic Server. This script ensures
@rem that the CLASSPATH variable is set to include the WebLogic
@rem Server classes and required JDK classes. It also ensures that
@rem the bin directory of the WebLogic Server and JDK distributions
@rem are added to the PATH variables. This script contains the
@rem following variables: 
@rem 
@rem WL_HOME   - This must point to the root directory of your WebLogic 
@rem             installation.
@rem 

set WL_HOME=c:\BEA\wlserver6.1

@echo "=> copy the RI in %WL_HOME%\config\mydomain\applications"
copy oss_common-1_0-ri_depl.ear %WL_HOME%\config\mydomain\applications
