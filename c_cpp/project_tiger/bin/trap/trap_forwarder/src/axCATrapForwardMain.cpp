/************************************************************************* 
 * Copyright (c) 2006 by Premraj Nallasivampillai. All rights reserved.
 *************************************************************************
 *
 * axCAEngineMain.cpp
 *
 *************************************************************************
 * Modification History:
 *			
 *************************************************************************
 * Description:
 *
 *************************************************************************
 * Modification History:
 *
 *    10/26/06    Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

/*************************************************************************
 * include files
 *************************************************************************/
#include <stdio.h>
#include <unistd.h>
#include <pthread.h>
#include <string.h>
#include <sys/time.h>
#include "ace/Service_Config.h"
#include "ace/Signal.h"
#include "axCATrapFwdCfgLoader.hpp"
#include "axTrapFwdDataLoader.hpp"
#include "axTFSnmpTrapReceiver.hpp"
#include "axTFTrapProcessor.hpp"
#include "axTFClientReceiver.hpp"
#include "axTFSystemConfig.hpp"

/*************************************************************************
 * definitions/macros
 *************************************************************************/


/*************************************************************************
 * constants
 *************************************************************************/

/*************************************************************************
 * static member initialization
 *************************************************************************/


/*************************************************************************
 * forward declerations
 *************************************************************************/
void signalHandler(int);
void setupSignalHandlers(void);


/*************************************************************************
 * functions
 *************************************************************************/

int
main (int argc, char * argv[])
{
  if (ACE_Service_Config::open (argc,
                                argv,
                                ACE_DEFAULT_LOGGER_KEY,
                                1,
                                0,
                                1) < 0) {

    ACE_ERROR_RETURN ((LM_ERROR, ACE_TEXT ("%p\n"),
                       ACE_TEXT ("Service Config open")),
                      1);
  }

  ACE_LOG_MSG->set_flags(ACE_Log_Msg::VERBOSE_LITE);
  AX_ULONG logMask = 
                     LM_EMERGENCY|
                     LM_ALERT|
                     LM_CRITICAL|
                     LM_ERROR|
                     LM_STARTUP|
                     LM_WARNING|
                     LM_NOTICE;
  ACE_LOG_MSG->priority_mask(logMask, ACE_Log_Msg::PROCESS);

  char * caHome = getenv("CA_HOME");
  char cfgFile[128];
  sprintf(cfgFile, "%s/%s", caHome, "bin/CAEngine.cfg");

  struct timeval tm;
  gettimeofday(&tm, NULL);

  axCATrapFwdCfgLoader cfgLoader(cfgFile);
  cfgLoader.loadConfig();

  axTFSystemConfig * sysCfg = axTFSystemConfig::getInstance();

  axTrapFwdDataLoader * loader = axTrapFwdDataLoader::getInstance();
  loader->loadData();

  axTFTrapProcessor * trapProcessor = new axTFTrapProcessor();
  trapProcessor->start();

  axTFClientReceiver * clientReceiver = new axTFClientReceiver();
  clientReceiver->start();
  trapProcessor->setSockFailHandler(clientReceiver);

  axTFSnmpTrapReceiver * trapReceiver = new axTFSnmpTrapReceiver();
  trapReceiver->start();

  while(true) {
    sleep(5);
#if 0
    if (ctlr->isExit()) {
      exit(0);
    }
    if (ctlr->isRereadConfig()) {
      axCACfgLoader cfgLoader(cfgFile);
      cfgLoader.loadConfig();
      ctlr->setRereadConfig(false);
    }
#endif
  }

  return (0);
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
void setupSignalHandlers(void)
{
  ACE_Sig_Action sa(signalHandler);

  // ACE_Sig_Set ss;
  // ss.sig_add(...);
  // ss.sig_add(...);
  // sa.mask(ss);

  // sa.register_action(SIGCHLD);
  sa.register_action(SIGINT);
  sa.register_action(SIGHUP);
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
void signalHandler(int sig)
{
  // axCAController * caCtlr = axCAController::getInstance();

  switch (sig) {
    case SIGHUP:
      // caCtlr->setRereadConfig(true);
      break;

    case SIGINT:
      // caCtlr->setExit();
      break;

    default:
      break;
  }
}


