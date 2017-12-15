
#include <stdio.h>
#include <unistd.h>
#include <pthread.h>
#include <string.h>
#include <sys/time.h>
#include "axDbExternalizer.hpp"
#include "axDbMySqlConnFactory.hpp"
#include "axDbMySqlConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbResultSet.hpp"
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
#include "axDate.hpp"

#define MAX_THREADS 10000

void createThreads(void);
void hexTest(void);
void inheritanceHierarchyAndVirtualFunctionTest(void);
void signedPromotionRulesTest(void);
void dateTimeTest(void);
void createDbTableTest(void);
void createDbTableTestMultiStmt(void);
void leftShiftTest(void);
void dateTest(void);

static void * thread_func(void * arg)
{
  pthread_mutex_t * m = (pthread_mutex_t *) malloc(sizeof(pthread_mutex_t));
  while (1) {
    pthread_mutex_lock(m);
    sleep(1);
    pthread_mutex_unlock(m);
  }
}


struct BitsStruct_s {
  char a1:4;
  char a2:4;
  char a3:4;
};

int
main (int argc, char * argv[])
{

#if 0
  BitsStruct_s myBitsVar;

  printf("%d\n", sizeof(myBitsVar));
#endif

#if 0
  axCAScheduler * sched = axCAScheduler::getInstance();

  axGlobalData * globD = axGlobalData::getInstance();

  axMessageSubSystem::getInstance();
  axMessageManager::getInstance();
#endif

  axDbMySqlConnFactory * cf = axDbMySqlConnFactory::getInstance();

#if 0
  axGlobalTimer * timer = axGlobalTimer::getInstance();
  axPoppedTimerQ * timerQ = axPoppedTimerQ::getInstance();

  axSoakTimerQ::getInstance();

  axPoppedTimerProcessor * ptp = new axPoppedTimerProcessor();
  ptp->start();

  axTimerQProcessor * tqp = new axTimerQProcessor();
  tqp->start();

  axSoakTimerQProcessor * soakTimerP = new axSoakTimerQProcessor();
  soakTimerP->start();

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

#endif

#if 0
  axSnmpCmtsCmStatusReqType * cmtsReqTypeIns = axSnmpCmtsCmStatusReqType::getInstance();
  axSnmpCmtsCmOids_s cmtsOids;
  printf("BEFORE: numOids=%d, maxOids=%d\n", cmtsOids.numOids, cmtsOids.maxOids);
  cmtsReqTypeIns->getOids(cmtsOids);
  printf("AFTER: numOids=%d, maxOids=%d\n", cmtsOids.numOids, cmtsOids.maxOids);
  printf("sizeof(oid)=%dx\n", sizeof(oid));
  int hex15 = 15;
  printf("%ld\n", hex15);
  printf("%lx\n", hex15);
  printf("%x\n", hex15);
  char buf[64]; buf[0]='\0';
  sprintf(buf, "%s:%s", buf, "foo");
  printf("%s\n", buf);
#endif

#if 0
  axCblAssureDataLoader * loader = axCblAssureDataLoader::getInstance();
  loader->loadData();
  loader->createRunnableCollections();
  loader->queueTimer();

  // moved to axCblAssureDataLoader::createRunnableCollections();
  // axCmtsPoller::getInstance();
  // axMtaPoller::getInstance();
  // axMtaPinger::getInstance();
  // axCmtsCmPerfPoller::getInstance();
  // axHfcAlarmCollectionofRCs::getInstance();

  while(true) {
    sleep(10);
  }
#endif

  hexTest();

  inheritanceHierarchyAndVirtualFunctionTest();

  signedPromotionRulesTest();

  dateTimeTest();

  createDbTableTest();

  createDbTableTestMultiStmt();

  leftShiftTest();

  dateTest();

  return (0);
}


void createThreads(void)
{
  pthread_t threadT;
  pthread_attr_t threadAttrT;

  pthread_attr_init(&threadAttrT);
  memset(&threadT, '\0', sizeof(threadT));

  for (int i=0; i<MAX_THREADS; i++) {
    pthread_create(&threadT, &threadAttrT, thread_func, NULL);
  }

}



void
hexTest(void)
{
#if 0
  char   resultMac[32];
  char * resultPtr = &resultMac[0];
  unsigned char   octetString[] = { 255, 128, 127, 0, 23, 15, 16};
  int             octetStrLen = 7;
  unsigned char * octetPtr = &octetString[0];
  unsigned char * endPtr = octetPtr + octetStrLen;


  resultMac[0]='\0';
  resultPtr = &resultMac[0];
  for (int i=0; i<octetStrLen; i++) {
    // this works 
    sprintf(resultPtr, "%.2x", octetString[i]);
    printf("Part=%.2x - %s\n", octetString[i], resultPtr);
    resultPtr += 2;
  }
  printf("MAC=%s\n", resultMac);

  resultMac[0]='\0';
  resultPtr = &resultMac[0];
  while (octetPtr != endPtr) {
    sprintf(resultPtr, "%.2x", *octetPtr++);
    resultPtr += 2;
  }
  printf("MAC=%s\n", resultMac);
#endif

}


void 
inheritanceHierarchyAndVirtualFunctionTest(void)
{
#if 0
  axCAAlarmCollection alarmColl(axObject::CompareFunction);
  axHfcAlarm * alarm = new axHfcAlarm(1, "foo");
  alarmColl.add(alarm);
#endif
}


void 
signedPromotionRulesTest(void)
{
#if 0
  AX_INT8 c = -127;
  AX_UINT32 ui = c;
  AX_UINT8 uc = 255;
  AX_INT32 i = uc;

  printf("c=%d; ui=%d\n", c, ui);
  printf("uc=%d; i=%d\n", uc, i);
#endif
}

void
dateTimeTest(void)
{
#if 0
  struct timeval tm;
  gettimeofday(&tm, NULL);
  printf("Sec=%d\n", tm.tv_sec);
  struct tm myTm = *gmtime((time_t *)&tm.tv_sec);
  printf("day = %d\n", myTm.tm_mday);

  /* next day */
  tm.tv_sec += 86400;
  myTm = *gmtime((time_t *)&tm.tv_sec);
  printf("day = %d\n", myTm.tm_mday);
  myTm.tm_sec=0;
  myTm.tm_min=0;
  myTm.tm_hour=0;
  time_t oHundredHrsNextDay = mktime(&myTm);
  printf("Sec=%d\n", oHundredHrsNextDay);
#endif
}


/**********************************************************************/

char * sql_drop_db = "drop database if exists catmpsumm";
char * sql_create_db = "create database catmpsumm";
char * sql_use_db = "use catmpsumm";

char * sql_create_tbl_1 = "create table foo.prem ("
"id int unsigned not null,"
"primary key(id)"
") type=MyISAM"
"";

char * grant_priv = ""
"GRANT ALL PRIVILEGES ON foo.* to root@'192.168.1.0/255.255.255.0'"
"";

void
createDbTableTest(void)
{
#if 0

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbMySqlConnection * myC = dynamic_cast<axDbMySqlConnection *> (mc);
    MYSQL * dbHdl = (MYSQL *) myC->getConnectionHandle();
    if (mysql_set_server_option(dbHdl, MYSQL_OPTION_MULTI_STATEMENTS_ON)) {
      printf("mysql_set_server_option: failed\n");
      goto EXIT_LABEL;
    }

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    bool qrc = query->execute(sql_drop_db);
    if (!qrc) {
      goto EXIT_LABEL;
    }
    printf("drop db succeeded\n");

    qrc = query->execute(sql_create_db);
    if (!qrc) {
      goto EXIT_LABEL;
    }
    printf("create db succeeded\n");

    qrc = query->execute(sql_use_db);
    if (!qrc) {
      goto EXIT_LABEL;
    }
    printf("use db succeeded\n");

    qrc = query->execute(sql_create_tbl_1);
    if (!qrc) {
      goto EXIT_LABEL;
    }
    printf("create table succeeded\n");

    qrc = query->execute(grant_priv);
    if (!qrc) {
      goto EXIT_LABEL;
    }
    printf("grant succeeded\n");

    axDbResultSet * rs = dynamic_cast<axDbResultSet *>
                                             (query->getResultSet());




    /* finally */
    if (mysql_set_server_option(dbHdl, MYSQL_OPTION_MULTI_STATEMENTS_OFF)) {
      printf("mysql_set_server_option: failed\n");
      goto EXIT_LABEL;
    }

  }

EXIT_LABEL:

  return;
#endif
}


char * sql_multi_create_schema = "\
drop database if exists catmpsumm; \
create database catmpsumm; \
create table catmpsumm.prem ( \
  id int unsigned not null, \
  primary key(id) \
)type=MyISAM; \
";

/**********************************************************************/

void
createDbTableTestMultiStmt(void)
{
#if 0
  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbMySqlConnection * myC = dynamic_cast<axDbMySqlConnection *> (mc);
    MYSQL * dbHdl = (MYSQL *) myC->getConnectionHandle();
    if (mysql_set_server_option(dbHdl, MYSQL_OPTION_MULTI_STATEMENTS_ON)) {
      printf("mysql_set_server_option: failed\n");
      goto EXIT_LABEL;
    }

    char * qry;
    int status;

    qry = sql_multi_create_schema;

    if ( mysql_real_query(dbHdl, qry, strlen(qry)) ) {
      goto EXIT_LABEL;
    }
    printf("multi command seem to have succeeded. Need more determination...\n");

    do {

      MYSQL_RES * result = mysql_store_result(dbHdl);

      if (result)
      {
        /* yes; process rows and free the result set */
        mysql_free_result(result);
      }
      else          /* no result set or error */
      {
        if (mysql_field_count(dbHdl) == 0)
        {
          printf("%lld rows affected\n", mysql_affected_rows(dbHdl));
        }
        else  /* some error occurred */
        {
          printf("Could not retrieve result set\n");
          break;
        }
      }

      /* more results? -1 = no, >0 = error, 0 = yes (keep looping) */
      if ((status = mysql_next_result(dbHdl)) > 0) {
        printf("Could not execute statement\n");
      }

    } while (status == 0);

EXIT_LABEL:

  /* finally */
  if (mysql_set_server_option(dbHdl, MYSQL_OPTION_MULTI_STATEMENTS_OFF)) {
    printf("mysql_set_server_option: failed\n");
  }

  return;
#endif
}

void
leftShiftTest(void)
{

#if 0
  unsigned int val = (2<<30);
  long long lV = val;
  lV *= 2;
  printf("%lld\n", lV);
#endif

#if 0
  long long val = ((long long)(2<<31)) - 1;
  printf("%lld\n", val);
#endif

#if 0
  short sVal = (2<<14)-1;
  printf("sVal: %d\n", sVal);
  // printf("sVal: %u\n", sVal);
  sVal = (2<<14);
  printf("sVal: %d\n", sVal);
  // printf("sVal: %u\n", sVal);
#endif

#if 0
  short sVal = (1<<15)-1;
  printf("sVal-d: %d\n", sVal);
  sVal = (1<<15);
  printf("sVal-d: %d\n", sVal);

  unsigned short usVal = (2<<14)-1;
  printf("usVal-d: %d\n", usVal);
  printf("usVal-u: %u\n", usVal);
  usVal = (2<<14);
  printf("usVal-d: %d\n", usVal);
  printf("usVal-u: %u\n", usVal);
#endif

#if 0
  unsigned int uiVal = (2<<31)-1;
  printf("uiVal: %d\n", uiVal);
  printf("uiVal: %u\n", uiVal);
#endif

#if 0
  unsigned int uiVal = (1<<32) -1;
  printf("uiVal-d: %d\n", uiVal);
  printf("uiVal-u: %u\n", uiVal);
#endif

}


void
dateTest(void)
{
  static const char * myName="dateTest:";

  axDate today;

  printf("%s HIGH: mon=%d;day=%d;year=%d\n", myName,
            today.getMonth(),today.getDayOfMonth(),today.getYear());

  printf("%s seconds since Jan 1, 1970 = %u\n", myName, today.getTimeSeconds());

#if 0
  axDate hDate(today.getMonth(), today.getDayOfMonth(),
                                                     today.getYear());
  time_t hSec = hDate.getTimeSeconds();
  hDate.rollDays(-1);
  time_t lSec = hDate.getTimeSeconds();

  ACE_DEBUG((LM_DEBUG, "%s LOW: mon=%d;day=%d;year=%d\n", myName,
            hDate.getMonth(),hDate.getDayOfMonth(),hDate.getYear()));

  /*
   * We start with Jan 1, 1970. In this case update with last-summ date
   * to be yesterday's date. This also applies if summary has not been
   * executed in the last little while (one or more days).
   */
  if (lastSumm.m_day != hDate.getDayOfMonth() ||
      lastSumm.m_month != hDate.getMonth() ||
      lastSumm.m_year != hDate.getYear() ) {
    lastSumm.m_day = hDate.getDayOfMonth();
    lastSumm.m_month = hDate.getMonth();
    lastSumm.m_year = hDate.getYear();

    lastSumm.updateRow();
  }

  ACE_DEBUG((LM_DEBUG, "%s fromSec=%d; toSec=%d\n", myName, lSec, hSec));
#endif

  time_t timeT;
  time(&timeT);
  struct tm bdTime;
  gmtime_r(&timeT, &bdTime);
  printf("%s HIGH: mon=%d;day=%d;year=%d\n", myName,
            bdTime.tm_mon,bdTime.tm_mday,bdTime.tm_year);

  struct timeval tm;
  gettimeofday(&tm,NULL);
  gmtime_r(&tm.tv_sec, &bdTime);
  printf("%s HIGH: mon=%d;day=%d;year=%d\n", myName,
            bdTime.tm_mon,bdTime.tm_mday,bdTime.tm_year);

  localtime_r(&timeT, &bdTime);
  printf("%s HIGH: mon=%d;day=%d;year=%d\n", myName,
            bdTime.tm_mon,bdTime.tm_mday,bdTime.tm_year);

  printf("%d\n", -10/2);
}


