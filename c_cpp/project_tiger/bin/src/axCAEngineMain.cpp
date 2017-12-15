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
#include "axCAController.hpp"
#include "axCASystemConfig.hpp"
#include "axCACfgLoader.hpp"
#include "axDbMySqlConnFactory.hpp"
#include "axDbMySqlConnection.hpp"
#include "axCAScheduler.hpp"
#include "axGlobalData.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbSampleQuery.hpp"
#include "axCblAssureDataLoader.hpp"
#include "axGlobalTimer.hpp"
#include "axPoppedTimerQ.hpp"
#include "axTimerQProcessor.hpp"
#include "axPoppedTimerProcessor.hpp"
#include "axCmtsPoller.hpp"
#include "axSnmpSessionFactory.hpp"
#include "axSnmpSubSystem.hpp"
#include "axSnmpCmtsCmStatusReqType.hpp"
#include "axCAAlarmCollection.hpp"
#include "axHfcAlarm.hpp"
#include "axMessageSubSystem.hpp"
#include "axMessageManager.hpp"
#include "axIcmpSocketFactory.hpp"
#include "axCountsSubSystem.hpp"
#include "axMtaPoller.hpp"
#include "axMtaPinger.hpp"
#include "axCmtsCmPerfPoller.hpp"
#include "axSnmpCmtsChannelReqType.hpp"
#include "axSnmpCmtsCmStatusReqType.hpp"
#include "axSnmpHRMtaReqType.hpp"
#include "axSnmpCmPerfCmReqType.hpp"
#include "axSnmpDownChannelPerfReqType.hpp"
#include "axAlarmSubSystem.hpp"
#include "axHfcAlarmCollectionofRCs.hpp"
#include "axHfcMtaAlarmCollectionOfRCs.hpp"
#include "axSoakTimerQ.hpp"
#include "axSoakTimerQProcessor.hpp"
#include "axCountsAndAlarmMsgQ.hpp"
#include "axCountsAndAlarmsQWorker.hpp"
#include "axAlarmProcessingMsgQ.hpp"
#include "axAlarmProcessingQWorker.hpp"
#include "axCADbConfigChangePoller.hpp"
#include "axCALoadDbConfig.hpp"
#include "axCAFqdnToIpPoller.hpp"
#include "axDailySummaryRC.hpp"
#include "axDailySummaryTimer.hpp"
#include "axSoapServiceAcceptor.hpp"
#include "axXmlTrapReceiver.hpp"
#include "axConfigDownloader.hpp"

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
#if 1
                     LM_SCHED_DEBUG|
                     LM_TIMER_DEBUG|
                     LM_INTDS_DEBUG|
                     LM_DB_DEBUG|
                     LM_ALARM_DEBUG|
                     LM_SNMP_DEBUG|
                     LM_PING_DEBUG|
                     LM_POLL_DEBUG|
                     LM_MISC_DEBUG|
                     LM_TIMING_DEBUG|
                     LM_DEBUG|
                     LM_INFO|
#endif
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

  axCAController * ctlr = axCAController::getInstance();

  // axCACfgLoader cfgLoader("./CAEngine.cfg");
  axCACfgLoader cfgLoader(cfgFile);
  cfgLoader.loadConfig();

  axCALoadDbConfig dbConfig;
  dbConfig.loadConfig();

  axCAScheduler * sched = axCAScheduler::getInstance();

  axGlobalData * globD = axGlobalData::getInstance();

  axMessageSubSystem::getInstance();
  axMessageManager::getInstance();

  axDbMySqlConnFactory * cf = axDbMySqlConnFactory::getInstance();

  axGlobalTimer * timer = axGlobalTimer::getInstance();
  axPoppedTimerQ * timerQ = axPoppedTimerQ::getInstance();

  axSoakTimerQ::getInstance();

  axPoppedTimerProcessor * ptp = new axPoppedTimerProcessor();
  ptp->start();

  int numTimerQProcessors = 
                   axCASystemConfig::getInstance()->getNumTimerWorkers();

  for (int i=0; i<numTimerQProcessors; i++) {
    axTimerQProcessor * tqp = new axTimerQProcessor();
    tqp->start();
  }

  axSoakTimerQProcessor * soakTimerP = new axSoakTimerQProcessor();
  soakTimerP->start();

  axSoapServiceAcceptor * soapAcceptor = new axSoapServiceAcceptor();
  soapAcceptor->start();

  axAlarmSubSystem::getInstance();
  axCountsSubSystem::getInstance();
  axIcmpSocketFactory::getInstance();

  axSnmpSubSystem * snmpSS = axSnmpSubSystem::getInstance();

  axSnmpSessionFactory * snmpSf = axSnmpSessionFactory::getInstance();

  axSnmpCmtsChannelReqType::getInstance();
  axSnmpCmtsCmStatusReqType::getInstance();
  axSnmpHRMtaReqType::getInstance();
  axSnmpCmPerfCmReqType::getInstance();
  axSnmpDownChannelPerfReqType::getInstance();

  axCountsAndAlarmMsgQ::getInstance();
  axCountsAndAlarmsQWorker * countsQWorker = new axCountsAndAlarmsQWorker();
  countsQWorker->start();

  axAlarmProcessingMsgQ::getInstance();
  axAlarmProcessingQWorker * almProcWorker = new axAlarmProcessingQWorker();
  almProcWorker->start();

  /*
   * Synchronize/download config from Market (if we are a Blade, that is)
   */
  axConfigDownloader cfgDownloader;
  cfgDownloader.downloadConfig();

  /*
   * The following order is preferred
   */
  axCblAssureDataLoader * loader = axCblAssureDataLoader::getInstance();
  loader->loadData();
  loader->createRunnableCollections();
  //
  axDailySummaryRC * rc = axDailySummaryRC::getInstance();
  time_t dailySummTime = tm.tv_sec + 5;
  axDailySummaryTimer * nextSumm = new axDailySummaryTimer(dailySummTime);
  timer->add(nextSumm);
  //
  loader->queueTimer();

  // moved to axCblAssureDataLoader::createRunnableCollections();
  // axCmtsPoller::getInstance();
  // axMtaPoller::getInstance();
  // axMtaPinger::getInstance();
  // axCmtsCmPerfPoller::getInstance();
  // axHfcAlarmCollectionofRCs::getInstance();

  /*
   * Is this still needed, since we have an event based system that will
   * notify if CMTS/Alarm configs change?
   */
  // axCADbConfigChangePoller * dbCfgPoller = new axCADbConfigChangePoller();
  // dbCfgPoller->start();

  axCAFqdnToIpPoller * fqdnPoller = new axCAFqdnToIpPoller();
  fqdnPoller->start();

  // fclose(stderr);

  axXmlTrapReceiver * trapRcvr = new axXmlTrapReceiver();
  trapRcvr->start();

  while(true) {
    sleep(5);
    if (ctlr->isExit()) {
      exit(0);
    }
    if (ctlr->isRereadConfig()) {
      axCACfgLoader cfgLoader(cfgFile);
      cfgLoader.loadConfig();
      ctlr->setRereadConfig(false);
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
  axCAController * caCtlr = axCAController::getInstance();

  switch (sig) {
    case SIGHUP:
      caCtlr->setRereadConfig(true);
      break;

    case SIGINT:
      caCtlr->setExit();
      break;

    default:
      break;
  }
}


