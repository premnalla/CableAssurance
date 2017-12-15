
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
#include "axCmPerfThreshSummary.hpp"
#include "axCmPerfUtils.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axCmPerfThreshSummary::SQL_RES_ID = "\
select cm_res_id from catmpsumm.cm_resources;\
";

AX_INT8 * axCmPerfThreshSummary::SQL_CURR_PERF = "\
select sum_tcafree_tm,sum_tca_tm,last_log_tm,tca_change_tm,\
downstream_snr,downstream_power,upstream_power,state_changes \
from catmpsumm.cm_current_perf where cm_res_id=%u;\
";

AX_INT8 * axCmPerfThreshSummary::SQL_INSERT_SUMMARY = "\
insert into casumm.cm_perf_thresh_summ(cm_res_id,month,day,year,\
sum_tcafree_tm,sum_tca_tm,state_changes) values (%u,%d,%d,%d,%u,%u,%d);\
";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCmPerfThreshSummary::axCmPerfThreshSummary() :
  m_todaysDate(NULL),
  m_startTime(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmPerfThreshSummary::~axCmPerfThreshSummary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmPerfThreshSummary::axCmPerfThreshSummary(struct tm * in, 
                                                      time_t stTime) :
  m_todaysDate(in),
  m_startTime(stTime)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axCmPerfThreshSummary::summarize(void)
{
  static const char * myName="cmPerfSummary:";

  bool ret = false;

  int       col;
  bool      errored = false;
  AX_UINT32 cmResId;

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  axDbReadConnHelper h(cf);
  axDbMySqlConnection * mc = dynamic_cast<axDbMySqlConnection *>
                                                   (h.getConnection());
  MYSQL * dbHdl = (MYSQL *) mc->getConnectionHandle();

  MYSQL_RES * cmResIdResult;

  MYSQL_ROW row;

  if (mysql_real_query(dbHdl, SQL_RES_ID, strlen(SQL_RES_ID))) {
    ACE_DEBUG((LM_ERROR, "%s sql failed\n", myName));
    goto EXIT_LABEL;
  }

  cmResIdResult = mysql_store_result(dbHdl);

  for (row=mysql_fetch_row(cmResIdResult); 
       row && !errored; 
       row=mysql_fetch_row(cmResIdResult)) {

    col = 0;

    MYSQL_ROWCOL_LEN_t lengths = mysql_fetch_lengths(cmResIdResult);

    if (lengths[col]) {
      cmResId = (AX_UINT32) strtoul(row[col], NULL, 10);
    } else {
      errored = true;
      break;
    }
    col++;

    if (!summarizeForCm(dbHdl, cmResId)) {
      ACE_DEBUG((LM_ERROR, "%s summary failed for cmts(%u)\n", 
                                                   myName, cmResId));
    }

  }

  if (!errored) {
    ret = true;
  }

  mysql_free_result(cmResIdResult);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axCmPerfThreshSummary::summarizeForCm(MYSQL * dbHdl, AX_UINT32 cmResId)
{
  static const char * myName="summPerfForCm:";

  bool ret = false;

  char sql[2048];

  int   col;
  bool  errored = false;

  AX_UINT32 cumulative_nontca_time = 0;
  AX_UINT32 cumulative_tca_time = 0;

  AX_UINT32 tca_time = 0;
  AX_UINT32 nontca_time = 0;
  AX_UINT32 last_log_time = 0;
  AX_UINT32 last_state_chg_time = 0;
  AX_INT16  downstream_snr = 0;
  AX_INT16  downstream_pwr = 0;
  AX_INT16  upstream_pwr = 0;
  AX_UINT16 state_changes = 0;

  sprintf(sql, SQL_CURR_PERF, cmResId);
  // ACE_DEBUG((LM_DEBUG, "%s\n", sql));

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
      nontca_time = (AX_UINT32) strtoul(row[col], NULL, 10);
    } else {
      errored = true;
    }
    col++;

    if (lengths[col]) {
      tca_time = (AX_UINT32) strtoul(row[col], NULL, 10);
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
      downstream_snr = (AX_INT16) atoi(row[col]);
    } else {
      errored = true;
    }
    col++;

    if (lengths[col]) {
      downstream_pwr = (AX_INT16) atoi(row[col]);
    } else {
      errored = true;
    }
    col++;

    if (lengths[col]) {
      upstream_pwr = (AX_INT16) atoi(row[col]);
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

    /*
     * Determine if Threshold crossed
     */

    bool tca = axCmPerfUtils::IsCmThresholdCrossed(
                      downstream_snr, downstream_pwr, upstream_pwr);

    /*
     * Initially: cumulative_nontca_time = 0,
     *            cumulative_tca_time = 0
     */

    if (!nontca_time) {
      if (!tca) {
        cumulative_nontca_time = m_startTime - last_state_chg_time;
      }
    } else {
      if (!tca) {
        cumulative_nontca_time = nontca_time +
                                  (m_startTime - last_state_chg_time);
      } else {
        cumulative_nontca_time = nontca_time;
      }
    }

    if (!tca_time) {
      if (tca) {
        cumulative_tca_time = m_startTime - last_state_chg_time;
      }
    } else {
      if (tca) {
        cumulative_tca_time = tca_time +
                                  (m_startTime - last_state_chg_time);
      } else {
        cumulative_tca_time = tca_time;
      }
    }

  }

  mysql_free_result(countsRs);

  if (errored) {
    goto EXIT_LABEL;
  }

  sprintf(sql, SQL_INSERT_SUMMARY, cmResId,
     m_todaysDate->tm_mon,m_todaysDate->tm_mday,m_todaysDate->tm_year,
     cumulative_nontca_time,cumulative_tca_time,state_changes);
  // ACE_DEBUG((LM_DEBUG, "%s\n", sql));

  if (mysql_real_query(dbHdl, sql, strlen(sql))) {
    ACE_DEBUG((LM_ERROR, "%s insert summary failed\n", myName));
    goto EXIT_LABEL;
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


