
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
#include "axAbstractStatusSummary.hpp"

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
axAbstractStatusSummary::axAbstractStatusSummary() :
  m_todaysDate(NULL),
  m_startTime(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axAbstractStatusSummary::~axAbstractStatusSummary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axAbstractStatusSummary::axAbstractStatusSummary(struct tm * bdTime,
                                                     time_t timeSec) :
  m_todaysDate(bdTime),
  m_startTime(timeSec)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axAbstractStatusSummary::summarize(void)
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
axAbstractStatusSummary::summarizeForResource(MYSQL * dbHdl, 
                                                      AX_UINT32 resId)
{
  static const char * myName="summCountsForRes:";

  bool ret = false;

  char sql[2048];

  int   col;
  bool  errored = false;

  AX_UINT32 good_state_time = 0;
  AX_UINT32 bad_state_time = 0;
  AX_UINT32 last_log_time = 0;
  AX_UINT32 last_state_chg_time = 0;
  AX_UINT16 state_changes = 0;
  AX_UINT8  current_state = 0;

  AX_UINT32 cumulative_good_st_time = 0;
  AX_UINT32 cumulative_bad_st_time = 0;

  AX_INT8 * countsLogSql = getCurrentStatusSql();

  sprintf(sql, countsLogSql, resId);

  MYSQL_RES * countsRs;

  MYSQL_ROW row;

  if (mysql_real_query(dbHdl, sql, strlen(sql))) {
    ACE_DEBUG((LM_ERROR, "%s sql failed\n", myName));
    goto EXIT_LABEL;
  }

  countsRs = mysql_store_result(dbHdl);

  row = mysql_fetch_row(countsRs);

  if (row) {

    col = 0;

    MYSQL_ROWCOL_LEN_t lengths = mysql_fetch_lengths(countsRs);

    if (lengths[col]) {
      good_state_time = (AX_UINT32) strtoul(row[col], NULL, 10);
    } else {
      errored = true;
    }
    col++;

    if (lengths[col]) {
      bad_state_time = (AX_UINT32) strtoul(row[col], NULL, 10);
    } else {
      errored = true;
    }
    col++;

    if (lengths[col]) {
      last_log_time = (AX_UINT32) strtoul(row[col], NULL, 10);
    } else {
      errored = true;
    }
    col++;

    if (lengths[col]) {
      last_state_chg_time = (AX_UINT32) strtoul(row[col], NULL, 10);
    } else {
      errored = true;
    }
    col++;

    if (lengths[col]) {
      state_changes = (AX_UINT16) atoi(row[col]);
    } else {
      errored = true;
    }
    col++;

    if (lengths[col]) {
      current_state = (AX_UINT8) atoi(row[col]);
    } else {
      errored = true;
    }
    col++;

    /*
     * Initially: cumulative_good_st_time = 0,
     *            cumulative_bad_st_time = 0
     */
    if (!good_state_time) {
      if (isDeviceStateGood(current_state)) {
        cumulative_good_st_time = m_startTime - last_state_chg_time;
      }
    } else {
      if (isDeviceStateGood(current_state)) {
        cumulative_good_st_time = good_state_time +
                                  (m_startTime - last_state_chg_time);
      } else {
        cumulative_good_st_time = good_state_time;
      }
    }

    if (!bad_state_time) {
      if (!isDeviceStateGood(current_state)) {
        cumulative_bad_st_time = m_startTime - last_state_chg_time;
      }
    } else {
      if (!isDeviceStateGood(current_state)) {
        cumulative_bad_st_time = bad_state_time +
                                  (m_startTime - last_state_chg_time);
      } else {
        cumulative_bad_st_time = bad_state_time;
      }
    }

  }

  mysql_free_result(countsRs);

  AX_INT8 * summSql;

  if (errored) {
    goto EXIT_LABEL;
  }

  summSql = getInsertSql();

  sprintf(sql, summSql, resId,
     m_todaysDate->tm_mon,m_todaysDate->tm_mday,m_todaysDate->tm_year,
     cumulative_good_st_time,cumulative_bad_st_time,state_changes);

  if (mysql_real_query(dbHdl, sql, strlen(sql))) {
    ACE_DEBUG((LM_ERROR, "%s insert summary failed\n", myName));
    goto EXIT_LABEL;
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}

