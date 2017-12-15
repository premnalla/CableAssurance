
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axDbExternalizer.hpp"
#include "axDbConnectionFactory.hpp"
#include "axDbGenericQuery.hpp"
#include "axDbConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbMySqlConnection.hpp"
#include "axDbResultSet.hpp"
#include "axDailySummary.hpp"
#include "axCATaskCoordHelper.hpp"
#include "axWriteLockOperator.hpp"
#include "axDate.hpp"
#include "axCmtsCountsSummary.hpp"
#include "axHfcCountsSummary.hpp"
#include "axChannelCountsSummary.hpp"
#include "axCmPerfSummary.hpp"
#include "axCmStatusSummary.hpp"
#include "axMtaAvailabilitySummary.hpp"
#include "axCmPerfThreshSummary.hpp"
#include "axHfcStatusSummary.hpp"
#include "axHfcTcaSummary.hpp"
#include "axGlobalData.hpp"
#include "axReadLockOperator.hpp"
#include "axWriteLockOperator.hpp"
#include "axInternalCmts.hpp"
#include "axInternalCm.hpp"
#include "axInternalGenMta.hpp"
#include "axInternalHfc.hpp"
#include "axAvlTree.hpp"
#include "axAvlIterator.hpp"
#include "axIteratorHolder.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDailySummary::SQL_CREATE_SCHEMA = " \
drop database if exists catmpsumm; \
create database catmpsumm; \
create table catmpsumm.cmts_resources ( \
  cmts_res_id int unsigned not null, \
  primary key (cmts_res_id) \
) type=MyISAM; \
create table catmpsumm.channel_resources ( \
  channel_res_id int unsigned not null, \
  primary key (channel_res_id) \
) type=MyISAM; \
create table catmpsumm.hfc_resources ( \
  hfc_res_id int unsigned not null, \
  primary key (hfc_res_id) \
) type=MyISAM; \
create table catmpsumm.cm_resources ( \
  cm_res_id int unsigned not null, \
  primary key (cm_res_id) \
) type=MyISAM; \
create table catmpsumm.mta_resources ( \
  mta_res_id int unsigned not null, \
  primary key (mta_res_id) \
) type=MyISAM; \
create table catmpsumm.cm_current_status ( \
  cm_res_id int unsigned not null, \
  sum_online_tm int unsigned not null default 0, \
  sum_offline_tm int unsigned not null default 0, \
  last_log_tm int unsigned not null default 0, \
  last_online_chg_tm int unsigned not null default 0, \
  state_changes smallint unsigned not null default 0, \
  online_state tinyint unsigned not null default 0, \
  primary key (cm_res_id) \
) type=MyISAM; \
create table catmpsumm.mta_current_avail ( \
  mta_res_id int unsigned not null, \
  sum_avail_tm int unsigned not null default 0, \
  sum_unavail_tm int unsigned not null default 0, \
  last_log_tm int unsigned not null default 0, \
  last_avail_chg_tm int unsigned not null default 0, \
  state_changes smallint unsigned not null default 0, \
  available tinyint(1) unsigned not null default 0, \
  primary key (mta_res_id) \
) type=MyISAM; \
create table catmpsumm.cm_current_perf ( \
  cm_res_id int unsigned not null, \
  sum_tcafree_tm int unsigned not null default 0, \
  sum_tca_tm int unsigned not null default 0, \
  last_log_tm int unsigned not null default 0, \
  tca_change_tm int unsigned not null default 0, \
  t1_timeouts int unsigned default 0, \
  t2_timeouts int unsigned default 0, \
  t3_timeouts int unsigned default 0, \
  t4_timeouts int unsigned default 0, \
  downstream_snr smallint default 0, \
  downstream_power smallint default 0, \
  upstream_power smallint default 0, \
  state_changes smallint unsigned not null default 0, \
  primary key (cm_res_id) \
) type=MyISAM; \
create table catmpsumm.cm_perf_log ( \
  id bigint unsigned not null auto_increment, \
  cm_res_id int unsigned not null, \
  tm_sec int unsigned not null default 0, \
  t1_timeouts int unsigned default 0, \
  t2_timeouts int unsigned default 0, \
  t3_timeouts int unsigned default 0, \
  t4_timeouts int unsigned default 0, \
  downstream_snr smallint default 0, \
  downstream_power smallint default 0, \
  upstream_power smallint default 0, \
  primary key (id), \
  index cmri(cm_res_id) \
) type=MyISAM; \
create table catmpsumm.cmts_counts_log ( \
  id bigint unsigned not null auto_increment, \
  cmts_res_id int unsigned not null, \
  tm_sec int unsigned not null default 0, \
  cm_total smallint unsigned not null default 0, \
  cm_online smallint unsigned not null default 0, \
  mta_total smallint unsigned not null default 0, \
  mta_available smallint unsigned not null default 0, \
  primary key (id), \
  index cri(cmts_res_id) \
) type=MyISAM; \
create table catmpsumm.hfc_counts_log ( \
  id bigint unsigned not null auto_increment, \
  hfc_res_id int unsigned not null, \
  tm_sec int unsigned not null default 0, \
  cm_total smallint unsigned not null default 0, \
  cm_online smallint unsigned not null default 0, \
  mta_total smallint unsigned not null default 0, \
  mta_available smallint unsigned not null default 0, \
  primary key (id), \
  index hri(hfc_res_id) \
) type=MyISAM; \
create table catmpsumm.channel_counts_log ( \
  id bigint unsigned not null auto_increment, \
  channel_res_id int unsigned not null, \
  tm_sec int unsigned not null default 0, \
  cm_total smallint unsigned not null, \
  cm_online smallint unsigned not null, \
  mta_total smallint unsigned not null, \
  mta_available smallint unsigned not null, \
  primary key (id), \
  index cri(channel_res_id) \
) type=MyISAM; \
create table catmpsumm.hfc_current_alarm ( \
  hfc_res_id int unsigned not null, \
  sum_alarmfree_tm int unsigned not null default 0, \
  sum_alarm_tm int unsigned not null default 0, \
  last_log_tm int unsigned not null default 0, \
  alarm_chg_tm int unsigned not null default 0, \
  state_changes smallint unsigned not null default 0, \
  hfc_alarm tinyint unsigned not null default 0, \
  primary key (hfc_res_id) \
) type=MyISAM; \
create table catmpsumm.hfc_current_tca ( \
  hfc_res_id int unsigned not null, \
  sum_tcafree_tm int unsigned not null default 0, \
  sum_tca_tm int unsigned not null default 0, \
  last_log_tm int unsigned not null default 0, \
  tca_change_tm int unsigned not null default 0, \
  state_changes smallint unsigned not null default 0, \
  hfc_tca tinyint unsigned not null default 0, \
  primary key (hfc_res_id) \
) type=MyISAM; \
GRANT ALL PRIVILEGES ON catmpsumm.* TO root@'192.168.1.0/255.255.255.0'; \
insert into catmpsumm.cmts_resources (select cmts_res_id from canet.cmts); \
insert into catmpsumm.channel_resources (select channel_res_id from canet.channel); \
insert into catmpsumm.hfc_resources (select hfc_res_id from canet.hfc_plant); \
insert into catmpsumm.cm_resources (select cm_res_id from canet.cable_modem); \
insert into catmpsumm.mta_resources (select emta_res_id from canet.emta); \
insert into catmpsumm.cm_current_status (\
  select cm_res_id,sum_online_tm,sum_offline_tm,last_log_tm,last_online_chg_tm,\
  state_changes,online_state from caperf.cm_current_status); \
insert into catmpsumm.mta_current_avail (\
  select mta_res_id,sum_avail_tm,sum_unavail_tm,last_log_tm,last_avail_chg_tm,\
  state_changes,available from caperf.mta_current_avail); \
insert into catmpsumm.cm_current_perf (\
  select cm_res_id,sum_tcafree_tm,sum_tca_tm,last_log_tm,tca_change_tm,\
  t1_timeouts,t2_timeouts,t3_timeouts,t4_timeouts,\
  downstream_snr, downstream_power,upstream_power,\
  state_changes from caperf.cm_current_perf);\
insert into catmpsumm.hfc_current_alarm (\
  select hfc_res_id,sum_alarmfree_tm,sum_alarm_tm,last_log_tm,alarm_chg_tm,\
  state_changes,hfc_alarm from caperf.hfc_current_alarm);\
insert into catmpsumm.hfc_current_tca (\
  select hfc_res_id,sum_tcafree_tm,sum_tca_tm,last_log_tm,tca_change_tm,\
  state_changes,hfc_tca from caperf.hfc_current_tca);\
";


AX_INT8 * axDailySummary::CM_PERF_LOG = "\
insert into catmpsumm.cm_perf_log \
  (select * from caperf.cm_perf_log where tm_sec>=%u and tm_sec<%u);\
";

AX_INT8 * axDailySummary::CMTS_COUNTS_LOG = "\
insert into catmpsumm.cmts_counts_log \
  (select * from caperf.cmts_counts_log where tm_sec>=%u and tm_sec<%u);\
";

AX_INT8 * axDailySummary::HFC_COUNTS_LOG = "\
insert into catmpsumm.hfc_counts_log \
(select * from caperf.hfc_counts_log where tm_sec>=%u and tm_sec<%u);\
";

AX_INT8 * axDailySummary::CHANNEL_COUNTS_LOG = "\
insert into catmpsumm.channel_counts_log \
(select * from caperf.channel_counts_log where tm_sec>=%u and tm_sec<%u);\
";

AX_INT8 * axDailySummary::CM_CURR_STATUS_UPD = "\
update caperf.cm_current_status set sum_online_tm=0,sum_offline_tm=0,\
// last_log_tm=%u,last_online_chg_tm=%u,state_changes=0; \
last_online_chg_tm=%u,state_changes=0; \
";

AX_INT8 * axDailySummary::MTA_AVAIL_UPD = "\
update caperf.mta_current_avail set sum_avail_tm=0,sum_unavail_tm=0,\
// last_log_tm=%u,last_avail_chg_tm=%u,state_changes=0; \
last_avail_chg_tm=%u,state_changes=0; \
";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDailySummary::axDailySummary()
{
  time(&m_startTm);
  gmtime_r(&m_startTm, &m_todaysDate);
  m_todaysDate.tm_year += 1900;
}


//********************************************************************
// destructor:
//********************************************************************
axDailySummary::~axDailySummary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axDailySummary::axDailySummary()
// {
// }


//********************************************************************
// method:
//********************************************************************
bool
axDailySummary::summarize(axDbSummaryFlags & summFlags, 
                                        axDbLastCompSummary & lastSumm)
{
  static const char * myName = "dailySummary:";

  bool ret = true;

  ACE_DEBUG((LM_DEBUG, "%s entry\n", myName));

  if (!previousCycleCompleted(summFlags, lastSumm)) {
    continuePerviousCycle(summFlags, lastSumm);
  } else {
    summarizeData(summFlags, lastSumm);
  }

  summFlags.m_overallSummStarted = 0;
  summFlags.m_overallSummEnded = 1;
  if (!summFlags.updateRow()) {
    ACE_DEBUG((LM_WARNING, "%s Update of axDbSummaryFlags Failed\n", 
                                                            myName));
  }

  lastSumm.m_day = m_todaysDate.tm_mday;
  lastSumm.m_month = m_todaysDate.tm_mon;
  lastSumm.m_year = m_todaysDate.tm_year;
  if (!lastSumm.updateRow()) {
    ACE_DEBUG((LM_WARNING, "%s Update of axDbLastCompSummary Failed\n", 
                                                             myName));
  }

  ACE_DEBUG((LM_DEBUG, "%s exit\n", myName));

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDailySummary::previousCycleCompleted(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{
  // static const char * myName = "prevCycleCmpl:";

  bool ret;

  if (summFlags.m_overallSummStarted || !summFlags.m_overallSummEnded) {
    ret = true;
  } else {
    ret = false;
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDailySummary::continuePerviousCycle(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{

  if (summFlags.m_dbCopyStarted || !summFlags.m_dbCopyEnded) {
    copyUpdateDatabases(summFlags, lastSumm);
  }

  /*
   * Db Copy & Update done. Can allow other tasks to proceed
   */
  {
    axCATaskCoordHelper * tc = axCATaskCoordHelper::getInstance();
    axWriteLockOperator wLock(tc->getLock());
    tc->clearSummary();
  }

  if (summFlags.m_cmtsCountsSummStarted || 
                                   !summFlags.m_cmtsCountsSummEnded) {
    doCmtsCountsSummary(summFlags, lastSumm);
  }

  if (summFlags.m_hfcCountsSummStarted || 
                                    !summFlags.m_hfcCountsSummEnded) {
    doHfcCountsSummary(summFlags, lastSumm);
  }

  if (summFlags.m_channelCountsSummStarted || 
                                !summFlags.m_channelCountsSummEnded) {
    doChannelCountsSummary(summFlags, lastSumm);
  }

  if (summFlags.m_cmPerfSummStarted || !summFlags.m_cmPerfSummEnded) {
    doCmPerfSummary(summFlags, lastSumm);
  }

  if (summFlags.m_cmStatusSummStarted || 
                                     !summFlags.m_cmStatusSummEnded) {
    doCmStatusSummary(summFlags, lastSumm);
  }

  if (summFlags.m_cmPerfThreshSummStarted ||
                                     !summFlags.m_cmPerfThreshSummEnded) {
    doCmPerfThresholdSummary(summFlags, lastSumm);
  }

  if (summFlags.m_mtaAvailSummStarted || 
                                     !summFlags.m_mtaAvailSummEnded) {
    doMtaAvailSummary(summFlags, lastSumm);
  }

  if (summFlags.m_alarmSummStarted || !summFlags.m_alarmSummEnded) {
    doAlarmSummary(summFlags, lastSumm);
  }

  if (summFlags.m_hfcStatusSummStarted ||
                                     !summFlags.m_hfcStatusSummEnded) {
    doHfcStatusSummary(summFlags, lastSumm);
  }

  if (summFlags.m_hfcTcaSummStarted ||
                                     !summFlags.m_hfcTcaSummEnded) {
    doHfcTcaSummary(summFlags, lastSumm);
  }

}


//********************************************************************
// method:
//********************************************************************
void
axDailySummary::summarizeData(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{
  static const char * myName = "summData:";

  ACE_DEBUG((LM_DEBUG, "%s entry\n", myName));

  summFlags.m_dbCopyStarted = 1;
  summFlags.m_dbCopyEnded = 0;
  summFlags.m_cmtsCountsSummStarted = 1;
  summFlags.m_cmtsCountsSummEnded = 0;
  summFlags.m_hfcCountsSummStarted = 1;
  summFlags.m_hfcCountsSummEnded = 0;
  summFlags.m_channelCountsSummStarted = 1;
  summFlags.m_channelCountsSummEnded = 0;
  summFlags.m_cmPerfSummStarted = 1;
  summFlags.m_cmPerfSummEnded = 0;
  summFlags.m_cmStatusSummStarted = 1;
  summFlags.m_cmStatusSummEnded = 0;
  summFlags.m_cmPerfThreshSummStarted = 1;
  summFlags.m_cmPerfThreshSummEnded = 0;
  summFlags.m_mtaAvailSummStarted = 1;
  summFlags.m_mtaAvailSummEnded = 0;
  summFlags.m_alarmSummStarted = 1;
  summFlags.m_alarmSummEnded = 0;
  summFlags.m_hfcStatusSummStarted = 1;
  summFlags.m_hfcStatusSummEnded = 0;
  summFlags.m_hfcTcaSummStarted = 1;
  summFlags.m_hfcTcaSummEnded = 0;

  summFlags.updateRow();

  if (!copyUpdateDatabases(summFlags, lastSumm)) {
    goto EXIT_LABEL;
  }

  /*
   * Db Copy & Update done. Can allow other tasks to proceed
   */
  {
    axCATaskCoordHelper * tc = axCATaskCoordHelper::getInstance();
    axWriteLockOperator wLock(tc->getLock());
    tc->clearSummary();
  }

  doCmtsCountsSummary(summFlags, lastSumm);
  doHfcCountsSummary(summFlags, lastSumm);
  doChannelCountsSummary(summFlags, lastSumm);
  doCmPerfSummary(summFlags, lastSumm);
  doCmStatusSummary(summFlags, lastSumm);
  doCmPerfThresholdSummary(summFlags, lastSumm);
  doMtaAvailSummary(summFlags, lastSumm);
  doAlarmSummary(summFlags, lastSumm);
  doHfcStatusSummary(summFlags, lastSumm);

EXIT_LABEL:

  ACE_DEBUG((LM_DEBUG, "%s exit\n", myName));

  return;
}


//********************************************************************
// method:
//********************************************************************
bool
axDailySummary::copyUpdateDatabases(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{
  bool isSuccess = createSchema();

  if (isSuccess) {
    isSuccess = copyData(lastSumm);
  }

#if 0 // temporary while debugging
  if (isSuccess) {
    isSuccess = updateTables();
  }
#endif

  // if (isSuccess) {
  updateInternalStructures();
  // }

  /* finally */
  if (isSuccess) {
    summFlags.m_dbCopyStarted = 0;
    summFlags.m_dbCopyEnded = 1;
    summFlags.updateRow();
  }

  return (isSuccess);
}


//********************************************************************
// method:
//********************************************************************
bool
axDailySummary::updateTables(void)
{
  static const char * myName="summUpdTbls:";

  bool ret = true;

  char sql[1024];

  AX_UINT32 smmaryTime = (AX_UINT32)m_startTm;

  // sprintf(sql, CM_CURR_STATUS_UPD, smmaryTime, smmaryTime);
  sprintf(sql, CM_CURR_STATUS_UPD, smmaryTime);
  ret = (ret && executeSingleStatement(sql));

  // sprintf(sql, MTA_AVAIL_UPD, smmaryTime, smmaryTime);
  sprintf(sql, MTA_AVAIL_UPD, smmaryTime);
  ret = (ret && executeSingleStatement(sql));

  sprintf(sql, "update caperf.cm_current_perf set sum_tcafree_tm=0,"
               // "sum_tca_tm=0,state_changes=0,last_log_tm=%u,"
               "sum_tca_tm=0,state_changes=0,"
               // "tca_change_tm=%u", smmaryTime, smmaryTime);
               "tca_change_tm=%u", smmaryTime);
  ret = (ret && executeSingleStatement(sql));

  sprintf(sql, "update caperf.hfc_current_alarm set sum_alarmfree_tm=0,"
               // "sum_alarm_tm=0,state_changes=0,last_log_tm=%u,"
               "sum_alarm_tm=0,state_changes=0,"
               // "alarm_chg_tm=%u", smmaryTime, smmaryTime);
               "alarm_chg_tm=%u", smmaryTime);
  ret = (ret && executeSingleStatement(sql));

  sprintf(sql, "update caperf.hfc_current_tca set sum_tcafree_tm=0,"
               // "sum_tca_tm=0,state_changes=0,last_log_tm=%u,"
               "sum_tca_tm=0,state_changes=0,"
               // "tca_change_tm=%u", smmaryTime, smmaryTime);
               "tca_change_tm=%u", smmaryTime);
  ret = (ret && executeSingleStatement(sql));

  if (!ret) {
    ACE_DEBUG((LM_WARNING, "%s Failed\n", myName));
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDailySummary::updateInternalStructures(void)
{
  bool ret = true;

  AX_UINT32 smmaryTime = (AX_UINT32)m_startTm;

  axGlobalData * gD = axGlobalData::getInstance();
  axGlobalCmtsTable * gCmtsTable = gD->getCmtsTable();
  synchronizedTable_t * syncCmtsTbl = gCmtsTable->getTable();

  {
    axReadLockOperator cmtsRLock(syncCmtsTbl->lock);
    axAvlTree * cmtsTbl = dynamic_cast<axAvlTree *>
                                                (syncCmtsTbl->table);

    axAvlIterator * cmtsIter;
    axIteratorHolder cmtsIH((cmtsIter=(axAvlIterator *)
                                         cmtsTbl->createIterator()));

    axInternalCmts * cmts;

    for(cmts=(axInternalCmts *)cmtsIter->getFirst(); cmtsIter;
        cmts=(axInternalCmts *)cmtsIter->getNext()) {

      /*
       * Reset CM current status times
       * Reset CM current perf times
       */

      synchronizedTable_t * syncCmTable = cmts->getCmTable();
      axReadLockOperator cmRLock(syncCmTable->lock);
      axAvlTree * cmTbl = dynamic_cast<axAvlTree *>
                                                (syncCmTable->table);

      axAvlIterator * cmIter;
      axIteratorHolder cmIH((cmIter=(axAvlIterator *)
                                           cmTbl->createIterator()));

      axInternalCm * cm;

      for(cm=(axInternalCm *)cmIter->getFirst(); cm;
          cm=(axInternalCm *)cmIter->getNext()) {

        axWriteLockOperator cmWLock(cm->getLock());

        axIntCmNonkey_t * cmData = (axIntCmNonkey_t *) cm->getNonKey();

        if (!cm->hasData()) {
          continue;
        }

         cmData->onlineStateChanges = 0;
         cmData->onlineChangeTime = smmaryTime;
         // cmData->lastStateLogTime = smmaryTime;

         cmData->tcaStateChanges = 0;
         cmData->tcaChangeTime = smmaryTime;
         // cmData->lastPerfLogTime = smmaryTime;
      }

      /*
       * Reset MTA current avail status times
       */

      synchronizedTable_t * syncMtaTable = cmts->getMtaTable();
      axReadLockOperator mtaRLock(syncMtaTable->lock);
      axAvlTree * mtaTbl = dynamic_cast<axAvlTree *>
                                                (syncMtaTable->table);

      axAvlIterator * mtaIter;
      axIteratorHolder mtaIH((mtaIter=(axAvlIterator *)
                                           mtaTbl->createIterator()));

      axInternalGenMta * mta;

      for(mta=(axInternalGenMta *)mtaIter->getFirst(); mta;
          mta=(axInternalGenMta *)mtaIter->getNext()) {

        axWriteLockOperator mtaWLock(mta->getLock());

        axIntGenMtaNonkey_t * mtaData = 
                            (axIntGenMtaNonkey_t *) mta->getNonKey();

        if (!mta->hasData()) {
          continue;
        }

         mtaData->availChanges = 0;
         mtaData->availChangeTime = smmaryTime;
         // mtaData->lastAvailLogTime = smmaryTime;
      }

      /*
       * Reset HFC current alarm times
       * Reset HFC current tca times
       */

      synchronizedTable_t * syncHfcTable = cmts->getHfcTable();
      axReadLockOperator hfcRLock(syncHfcTable->lock);
      axAvlTree * hfcTbl = dynamic_cast<axAvlTree *>
                                                (syncHfcTable->table);

      axAvlIterator * hfcIter;
      axIteratorHolder hfcIH((hfcIter=(axAvlIterator *)
                                           hfcTbl->createIterator()));

      axInternalHfc * hfc;

      for(hfc=(axInternalHfc *)mtaIter->getFirst(); hfc;
          hfc=(axInternalHfc *)mtaIter->getNext()) {

        axWriteLockOperator hfcWLock(hfc->getLock());

        axIntHfcNonkey_t * hfcData =
                            (axIntHfcNonkey_t *) hfc->getNonKey();

        if (!hfc->hasData()) {
          continue;
        }

         hfcData->percentAlarmChanges = 0;
         hfcData->percentAlarmChangeTime = smmaryTime;
         // hfcData->lastAlarmLogTime = smmaryTime;

         hfcData->powerAlarmChanges = 0;
         hfcData->powerAlarmChangeTime = smmaryTime;

         hfcData->tcaStateChanges = 0;
         hfcData->tcaChangeTime = smmaryTime;
         // hfcData->lastTcaLogTime = smmaryTime;
      }

    }
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDailySummary::copyData(axDbLastCompSummary & lastSumm)
{
  static const char * myName="summCpData:";

  bool ret = true;

  char sql[512];

  axDate today;

  axDate hDate(today.getMonth(), today.getDayOfMonth(), 
                                                     today.getYear());

#if 0
  ACE_DEBUG((LM_DEBUG, "%s %d-%d-%d\n", myName, today.getMonth(), 
                             today.getDayOfMonth(), today.getYear()));
#endif

  time_t hSec = hDate.getTimeSeconds();
  hDate.rollDays(-1);
  time_t lSec = hDate.getTimeSeconds();

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

  ACE_DEBUG((LM_DEBUG, "%s %u-%u\n", myName, lSec, hSec));

  sprintf(sql, CM_PERF_LOG, lSec, hSec);
  ret = (ret && executeSingleStatement(sql));
  
  sprintf(sql, CMTS_COUNTS_LOG, lSec, hSec);
  ret = (ret && executeSingleStatement(sql));

  sprintf(sql, HFC_COUNTS_LOG, lSec, hSec);
  ret = (ret && executeSingleStatement(sql));

  sprintf(sql, CHANNEL_COUNTS_LOG, lSec, hSec);
  ret = (ret && executeSingleStatement(sql));

  if (!ret) {
    ACE_DEBUG((LM_WARNING, "%s Failed\n", myName));
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDailySummary::createSchema(void)
{
  static const char * myName = "dailySumm::CpDb:";

  bool ret = executeMultipleStatements(SQL_CREATE_SCHEMA);

  if (!ret) {
    ACE_DEBUG((LM_WARNING, "%s Failed\n", myName));
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDailySummary::executeSingleStatement(AX_INT8 * sql)
{
  // static const char * myName = "execSingleStmt:";

  bool ret;

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  axDbReadConnHelper h(cf);
  axDbConnection * mc = h.getConnection();

  axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
  axDbGenericQuery * query = queryH.getQuery();

  ret = query->execute(sql);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDailySummary::executeMultipleStatements(AX_INT8 * sql)
{
  static const char * myName = "execMultiStmt:";

  bool ret = true;
  bool isOptionSet = true;
  int status;

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  axDbReadConnHelper h(cf);
  axDbMySqlConnection * mc = dynamic_cast<axDbMySqlConnection *> 
                                                   (h.getConnection());
  MYSQL * dbHdl = (MYSQL *) mc->getConnectionHandle();

  if (mysql_set_server_option(dbHdl, 
                                  MYSQL_OPTION_MULTI_STATEMENTS_ON)) {
    ret = false;
    isOptionSet = false;
    ACE_DEBUG((LM_ERROR, "%s mysql_set_server_option: failed\n", 
                                                             myName));
    goto EXIT_LABEL;
  }

  if (mysql_real_query(dbHdl, sql, strlen(sql))) {
    ACE_DEBUG((LM_ERROR, "%s sql failed\n", myName));
    ret = false;
    goto EXIT_LABEL;
  }

  do {

    MYSQL_RES * result = mysql_store_result(dbHdl);

    if (result) {

      /* yes; process rows and free the result set */
      mysql_free_result(result);

    } else {         /* no result set or error */

      if (mysql_field_count(dbHdl) != 0) {
        ACE_DEBUG((LM_ERROR, "%s Could not retrieve result set\n", 
                                                             myName));
        ret = false;
        break;
      }

    }

    /* more results? -1 = no, >0 = error, 0 = yes (keep looping) */
    if ((status = mysql_next_result(dbHdl)) > 0) {
      ACE_DEBUG((LM_ERROR, "%s Could not not execute statement\n",
                                                             myName));
      ret = false;
    }

  } while (status == 0);

EXIT_LABEL:

  if (isOptionSet) {
    if (mysql_set_server_option(dbHdl, 
                                 MYSQL_OPTION_MULTI_STATEMENTS_OFF)) {
      ACE_DEBUG((LM_ERROR, "%s mysql_set_server_option: failed\n", 
                                                             myName));
    }
  }

  return (ret);
}

//********************************************************************
// method:
//********************************************************************
void
axDailySummary::doCmtsCountsSummary(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{ 
  axCmtsCountsSummary summ(&m_todaysDate);
  summ.summarize();

  /* finally */
  summFlags.m_cmtsCountsSummStarted = 0;
  summFlags.m_cmtsCountsSummEnded = 1;
  summFlags.updateRow();
}

//********************************************************************
// method:
//********************************************************************
void
axDailySummary::doHfcCountsSummary(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{
  axHfcCountsSummary summ(&m_todaysDate);
  summ.summarize();

  /* finally */
  summFlags.m_hfcCountsSummStarted = 0;
  summFlags.m_hfcCountsSummEnded = 1;
  summFlags.updateRow();
}

//********************************************************************
// method:
//********************************************************************
void
axDailySummary::doChannelCountsSummary(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{
  axChannelCountsSummary summ(&m_todaysDate);
  summ.summarize();

  /* finally */
  summFlags.m_channelCountsSummStarted = 0;
  summFlags.m_channelCountsSummEnded = 1;
  summFlags.updateRow();
}

//********************************************************************
// method:
//********************************************************************
void
axDailySummary::doCmPerfSummary(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{
  axCmPerfSummary summ(&m_todaysDate);
  summ.summarize();

  /* finally */
  summFlags.m_cmPerfSummStarted = 0;
  summFlags.m_cmPerfSummEnded = 1;
  summFlags.updateRow();
}

//********************************************************************
// method:
//********************************************************************
void
axDailySummary::doCmStatusSummary(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{
  axCmStatusSummary summ(&m_todaysDate, m_startTm);
  summ.summarize();

  /* finally */
  summFlags.m_cmStatusSummStarted = 0;
  summFlags.m_cmStatusSummEnded = 1;
  summFlags.updateRow();
}

//********************************************************************
// method:
//********************************************************************
void
axDailySummary::doCmPerfThresholdSummary(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{
  axCmPerfThreshSummary summ(&m_todaysDate, m_startTm);
  summ.summarize();

  /* finally */
  summFlags.m_cmPerfThreshSummStarted = 0;
  summFlags.m_cmPerfThreshSummEnded = 1;
  summFlags.updateRow();
}

//********************************************************************
// method:
//********************************************************************
void
axDailySummary::doMtaAvailSummary(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{
  axMtaAvailabilitySummary summ(&m_todaysDate, m_startTm);
  summ.summarize();

  /* finally */
  summFlags.m_mtaAvailSummStarted = 0;
  summFlags.m_mtaAvailSummEnded = 1;
  summFlags.updateRow();
}

//********************************************************************
// method:
//********************************************************************
void
axDailySummary::doAlarmSummary(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{

  /* finally */
  summFlags.m_alarmSummStarted = 0;
  summFlags.m_alarmSummEnded = 1;
  summFlags.updateRow();
}


//********************************************************************
// method:
//********************************************************************
void
axDailySummary::doHfcStatusSummary(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{
  axHfcStatusSummary summ(&m_todaysDate, m_startTm);
  summ.summarize();

  /* finally */
  summFlags.m_hfcStatusSummStarted = 0;
  summFlags.m_hfcStatusSummEnded = 1;
  summFlags.updateRow();
}


//********************************************************************
// method:
//********************************************************************
void
axDailySummary::doHfcTcaSummary(axDbSummaryFlags & summFlags,
                                        axDbLastCompSummary & lastSumm)
{
  axHfcTcaSummary summ(&m_todaysDate, m_startTm);
  summ.summarize();

  /* finally */
  summFlags.m_hfcTcaSummStarted = 0;
  summFlags.m_hfcTcaSummEnded = 1;
  summFlags.updateRow();
}


