/************************************************************************* 
 * Copyright (c) 2006 by Premraj Nallasivampillai. All rights reserved.
 *************************************************************************
 *
 * axCAPmMain.cpp
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
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <string.h>
#include <sys/time.h>
#include <sys/types.h>
#include "ace/Signal.h"
#include "axAll.h"
#include "axCALog.hpp"
#include "axPmData.hpp"
#include "axPmController.hpp"
#include "axCAInittabParser.hpp"

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

  ACE_LOG_MSG->set_flags(ACE_Log_Msg::VERBOSE_LITE);
  AX_ULONG logMask = LM_EMERGENCY|LM_ALERT|LM_CRITICAL|
                     LM_ERROR|LM_STARTUP|LM_WARNING|
                     LM_NOTICE|LM_INFO|LM_DEBUG;
  ACE_LOG_MSG->priority_mask(logMask, ACE_Log_Msg::PROCESS);

  axPmData * pmData = axPmData::getInstance();
  axPmController * pmCtlr = axPmController::getInstance();

  char * caHome = getenv("CA_HOME");

  char inittab[128];
  sprintf(inittab, "%s/%s", caHome, "process_mgr/capm.inittab");

  axCAInittabParser psr(inittab);
  if (!psr.parseFile()) {
    printf("error\n");
    exit (-1);
  }

  // printf("inittab = %s\n", inittab);

  pmData->dumpInittabEntries();

  setupSignalHandlers();

  /*
   * First fork
   */

  pid_t pid = fork();

  if (pid < 0) {
    printf("fork error, exitting ...\n");
    exit(-1);
  } else if (pid > 0) {
    // Parent
    exit(0);
  }

  /*
   * We are now the first child
   */

  /*
   * Now do subsequent fork's to run applications...
   */

  axPmInittabEntries_s * inittabEntries = pmData->getInittabEntries();

  for (int i=0; i<inittabEntries->numEntries; i++) {

    pid = fork();

    if (pid < 0) {
      printf("fork error, exitting ...\n");
      exit(-1);
    } else if (pid > 0) {
      // Parent
      // exit(0);
      inittabEntries->entries[i].pid = pid;
    } else {
      // child
      fclose(stderr);
      execv(inittabEntries->entries[i].path, 
                                          inittabEntries->entries[i].argv);
      printf("error execv()'ing CAEngine\n");
      exit(-1);
    }

  }

  /*
   * We are still the first child
   */

  while(true) {

    // fprintf(STDERR, "I'm child\n");
    sleep(1);

    for (int i=0; i<inittabEntries->numEntries; i++) {
      if (inittabEntries->entries[i].pid == 0 &&
          !strcmp(inittabEntries->entries[i].action, AX_CAPM_ACT_RESPAWN)) {

        pid = fork();

        if (pid < 0) {
          printf("fork error, exitting ...\n");
          exit(-1);
        } else if (pid > 0) {
          // Parent
          // exit(0);
          inittabEntries->entries[i].pid = pid;
        } else {
          // child
          fclose(stderr);
          execv(inittabEntries->entries[i].path,
                                          inittabEntries->entries[i].argv);
          printf("error execv()'ing CAEngine\n");
          exit(-1);
        }

      }
    }

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

  sa.register_action(SIGCHLD);
  sa.register_action(SIGINT);
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
  pid_t childPid;
  int   waitOptions;
  int   childExitStatus;

  axPmData * pmData = axPmData::getInstance();
  axPmController * pmCtlr = axPmController::getInstance();

  switch (sig) {
    case SIGCHLD:
      waitOptions = WNOHANG;
      childPid = waitpid(-1, &childExitStatus, waitOptions);
      if (childPid < 0) {
        printf("waitpid(): error in sig handler\n");
      } else if (childPid == 0) {
        printf("sig handler waitpid() -> no children exitted\n");
      } else {
        pmData->setChildDied(childPid);
      }
      break;

    case SIGINT:
      pmCtlr->setExit();
      break;

    default:
      break;
  }
}


