
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
#include "axAbstractCountsSummary.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axAbstractCountsSummary::axAbstractCountsSummary() :
  m_todaysDate(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractCountsSummary::~axAbstractCountsSummary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAbstractCountsSummary::axAbstractCountsSummary(struct tm * in) :
  m_todaysDate(in)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractCountsSummary::summarize(void)
{
  static const char * myName="countSummary:";

  bool ret = false;

  int       col;
  bool      errored = false;
  AX_UINT32 resId;

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  axDbReadConnHelper h(cf);
  axDbMySqlConnection * mc = dynamic_cast<axDbMySqlConnection *>
                                                   (h.getConnection());
  MYSQL * dbHdl = (MYSQL *) mc->getConnectionHandle();

  AX_INT8 * resSql = getResIdSql();

  MYSQL_RES * resIdResult;

  MYSQL_ROW row;

  if (mysql_real_query(dbHdl, resSql, strlen(resSql))) {
    ACE_DEBUG((LM_ERROR, "%s query failed\n", myName));
    goto EXIT_LABEL;
  }

  resIdResult = mysql_store_result(dbHdl);

  for (row=mysql_fetch_row(resIdResult);
       row && !errored;
       row=mysql_fetch_row(resIdResult)) {

    col = 0;

    MYSQL_ROWCOL_LEN_t lengths = mysql_fetch_lengths(resIdResult);

    if (lengths[col]) {
      resId = (AX_UINT32) strtoul(row[col], NULL, 10);
    } else {
      errored = true;
      break;
    }
    col++;

    if (!summarizeForResource(dbHdl, resId)) {
      ACE_DEBUG((LM_ERROR, "%s summary failed for res(%d)\n",
                                                      myName, resId));
    }

  }

  if (!errored) {
    ret = true;
  }

  mysql_free_result(resIdResult);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractCountsSummary::summarizeForResource(MYSQL * dbHdl, 
                                                      AX_UINT32 resId)
{
  static const char * myName="summCountsForRes:";

  bool ret = false;

  char sql[2048];

  int   col;
  bool  errored = false;

  AX_UINT32 upper_limit = (1<<31);
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

  AX_INT8 * countsLogSql = getCountsLog();

  sprintf(sql, countsLogSql, resId);

  MYSQL_RES * countsRs;

  MYSQL_ROW row;

  if (mysql_real_query(dbHdl, sql, strlen(sql))) {
    ACE_DEBUG((LM_ERROR, "%s sql failed\n", myName));
    goto EXIT_LABEL;
  }

  countsRs = mysql_store_result(dbHdl);

  for (row=mysql_fetch_row(countsRs);
       row && !errored;
       row=mysql_fetch_row(countsRs)) {

    col = 0;

    MYSQL_ROWCOL_LEN_t lengths = mysql_fetch_lengths(countsRs);

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

  mysql_free_result(countsRs);

  if (errored) {
    goto EXIT_LABEL;
  }

  if (num_samples) {

    avg_cm_total = cumulative_cm_total / num_samples;
    avg_cm_online = cumulative_cm_online / num_samples;
    avg_mta_total = cumulative_mta_total / num_samples;
    avg_mta_avail = cumulative_mta_avail / num_samples;

    AX_INT8 * summSql = getInsertSql();

    sprintf(sql, summSql, resId,
       m_todaysDate->tm_mon,m_todaysDate->tm_mday,m_todaysDate->tm_year,
       min_cm_total,max_cm_total,avg_cm_total,
       min_cm_online,max_cm_online,avg_cm_online,
       min_mta_total,max_mta_total,avg_mta_total,
       min_mta_avail,max_mta_avail,avg_mta_avail);

    if (mysql_real_query(dbHdl, sql, strlen(sql))) {
      ACE_DEBUG((LM_ERROR, "%s sql failed\n", myName));
      goto EXIT_LABEL;
    }

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}

