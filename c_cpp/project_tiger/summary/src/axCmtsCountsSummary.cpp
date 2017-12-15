
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axCmtsCountsSummary.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axCmtsCountsSummary::SQL_CMTS_RES_ID = " \
  select cmts_res_id from catmpsumm.cmts_resources; \
";

AX_INT8 * axCmtsCountsSummary::SQL_CMTS_COUNTS_LOG = " \
  select tm_sec,cm_total,cm_online,mta_total,mta_available \
  from catmpsumm.cmts_counts_log where cmts_res_id=%u; \
";

AX_INT8 * axCmtsCountsSummary::SQL_INSERT_SUMMARY = " \
insert into casumm.cmts_counts_summary(cmts_res_id,month,day,year, \
  min_cm_total,max_cm_total,avg_cm_total,min_cm_online,max_cm_online, \
  avg_cm_online,min_mta_total,max_mta_total,avg_mta_total, \
  min_mta_avail,max_mta_avail,avg_mta_avail) values ( \
  %u,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d); \
";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCmtsCountsSummary::axCmtsCountsSummary() :
  axAbstractCountsSummary(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmtsCountsSummary::~axCmtsCountsSummary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmtsCountsSummary::axCmtsCountsSummary(struct tm * in) :
  axAbstractCountsSummary(in)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axCmtsCountsSummary::getResIdSql(void)
{
  return (SQL_CMTS_RES_ID);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axCmtsCountsSummary::getCountsLog(void)
{
  return (SQL_CMTS_COUNTS_LOG);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axCmtsCountsSummary::getInsertSql(void)
{
  return (SQL_INSERT_SUMMARY);
}


#if 0
//********************************************************************
// method:
//********************************************************************
bool
axCmtsCountsSummary::summarize(void)
{
  static const char * myName="cmtsCountSummary:";

  bool ret = false;

  int       col;
  bool      errored = false;
  AX_UINT32 cmtsResId;

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  axDbReadConnHelper h(cf);
  axDbMySqlConnection * mc = dynamic_cast<axDbMySqlConnection *>
                                                   (h.getConnection());
  MYSQL * dbHdl = (MYSQL *) mc->getConnectionHandle();

  if (mysql_real_query(dbHdl, SQL_CMTS_RES_ID, 
                                            strlen(SQL_CMTS_RES_ID))) {
    ACE_DEBUG((LM_ERROR, "%s sql failed\n", myName));
    goto EXIT_LABEL;
  }

  MYSQL_RES * cmtsResIdResult = mysql_store_result(dbHdl);

  MYSQL_ROW row;

  for (row=mysql_fetch_row(cmtsResIdResult); 
       row && !errored; 
       row=mysql_fetch_row(cmtsResIdResult)) {

    col = 0;

    MYSQL_ROWCOL_LEN_t lengths = mysql_fetch_lengths(cmtsResIdResult);

    if (lengths[col]) {
      cmtsResId = (AX_UINT32) strtoul(row[col], NULL, 10);
    } else {
      errored = true;
      break;
    }
    col++;

    if (!summarizeForCmts(dbHdl, cmtsResId)) {
      ACE_DEBUG((LM_ERROR, "%s summary failed for cmts(%u)\n", 
                                                   myName, cmtsResId));
    }

  }

  if (!errored) {
    ret = true;
  }

  mysql_free_result(cmtsResIdResult);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axCmtsCountsSummary::summarizeForCmts(MYSQL * dbHdl, AX_UINT32 cmtsResId)
{
  static const char * myName="summarizeCountsForCmts:";

  bool ret = false;

  char sql[2048];

  int   col;
  bool  errored = false;

  AX_UINT32 upper_limit = (2<<30);
  AX_UINT32 num_samples = 0;

  AX_UINT32 tm_sec;
  AX_UINT32 cm_total;
  AX_UINT32 cm_online;
  AX_UINT32 mta_total;
  AX_UINT32 mta_avail;

  AX_UINT32 min_cm_total = upper_limit;
  AX_UINT32 max_cm_total = 0;
  AX_UINT32 avg_cm_total = 0;

  AX_UINT32 min_cm_online = upper_limit;
  AX_UINT32 max_cm_online = 0;
  AX_UINT32 avg_cm_online = 0;

  AX_UINT32 min_mta_total = upper_limit;
  AX_UINT32 max_mta_total = 0;
  AX_UINT32 avg_mta_total = 0;

  AX_UINT32 min_mta_avail = upper_limit;
  AX_UINT32 max_mta_avail = 0;
  AX_UINT32 avg_mta_avail = 0;

  AX_UINT32 min_cm_total_time = 0;
  AX_UINT32 max_cm_total_time = 0;

  AX_UINT32 min_cm_online_time = 0;
  AX_UINT32 max_cm_online_time = 0;

  AX_UINT32 min_mta_total_time = 0;
  AX_UINT32 max_mta_total_time = 0;

  AX_UINT32 min_mta_avail_time = 0;
  AX_UINT32 max_mta_avail_time = 0;

  AX_UINT32 cumulative_cm_total = 0;
  AX_UINT32 cumulative_cm_online = 0;
  AX_UINT32 cumulative_mta_total = 0;
  AX_UINT32 cumulative_mta_avail = 0;

  sprintf(sql, SQL_CMTS_COUNTS_LOG, cmtsResId);

  if (mysql_real_query(dbHdl, sql, strlen(sql))) {
    ACE_DEBUG((LM_ERROR, "%s sql failed\n", myName));
    goto EXIT_LABEL;
  }

  MYSQL_RES * cmtsCountsRs = mysql_store_result(dbHdl);

  MYSQL_ROW row;

  for (row=mysql_fetch_row(cmtsCountsRs);
       row && !errored;
       row=mysql_fetch_row(cmtsCountsRs)) {

    col = 0;

    MYSQL_ROWCOL_LEN_t lengths = mysql_fetch_lengths(cmtsCountsRs);

    if (lengths[col]) {
      tm_sec = (AX_UINT32) strtoul(row[col], NULL, 10);
    } else {
      errored = true;
      break;
    }
    col++;

    if (lengths[col]) {
      cm_total = (AX_UINT32) atoi(row[col]);
    } else {
      errored = true;
      break;
    }
    col++;

    if (lengths[col]) {
      cm_online = (AX_UINT32) atoi(row[col]);
    } else {
      errored = true;
      break;
    }
    col++;

    if (lengths[col]) {
      mta_total = (AX_UINT32) atoi(row[col]);
    } else {
      errored = true;
      break;
    }
    col++;

    if (lengths[col]) {
      mta_avail = (AX_UINT32) atoi(row[col]);
    } else {
      errored = true;
      break;
    }
    col++;

    if (cm_total < min_cm_total) {
      min_cm_total = cm_total;
      min_cm_total_time = tm_sec;
    }

    if (cm_total > max_cm_total) {
      max_cm_total = cm_total;
      max_cm_total_time = tm_sec;
    }

    if (cm_online < min_cm_online) {
      min_cm_online = cm_online;
      min_cm_online_time = tm_sec;
    }

    if (cm_online > max_cm_online) {
      max_cm_online = cm_online;
      max_cm_online_time = tm_sec;
    }

    if (mta_total < min_mta_total) {
      min_mta_total = mta_total;
      min_mta_total_time = tm_sec;
    }

    if (mta_total > max_mta_total) {
      max_mta_total = mta_total;
      max_mta_total_time = tm_sec;
    }

    if (mta_avail < min_mta_avail) {
      min_mta_avail = mta_avail;
      min_mta_avail_time = tm_sec;
    }

    if (mta_avail > max_mta_avail) {
      max_mta_avail = mta_avail;
      max_mta_avail_time = tm_sec;
    }

    num_samples++;

    cumulative_cm_total += cm_total;
    cumulative_cm_online += cm_online;
    cumulative_mta_total += mta_total;
    cumulative_mta_avail += mta_avail;
  }

  mysql_free_result(cmtsCountsRs);

  if (errored) {
    goto EXIT_LABEL;
  }

  avg_cm_total = cumulative_cm_total / num_samples;
  avg_cm_online = cumulative_cm_online / num_samples;
  avg_mta_total = cumulative_mta_total / num_samples;
  avg_mta_avail = cumulative_mta_avail / num_samples;

  sprintf(sql, SQL_INSERT_SUMMARY, cmtsResId,
     m_todaysDate->tm_mon,m_todaysDate->tm_mday,m_todaysDate->tm_year,
     min_cm_total,max_cm_total,avg_cm_total,
     min_cm_online,max_cm_online,avg_cm_online,
     min_mta_total,max_mta_total,avg_mta_total,
     min_mta_avail,max_mta_avail,avg_mta_avail);

  if (mysql_real_query(dbHdl, sql, strlen(sql))) {
    ACE_DEBUG((LM_ERROR, "%s sql failed\n", myName));
    goto EXIT_LABEL;
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}
#endif
