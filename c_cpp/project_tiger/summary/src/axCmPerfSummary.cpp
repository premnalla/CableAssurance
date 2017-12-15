
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
#include "axCmPerfSummary.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axCmPerfSummary::SQL_RES_ID = " \
  select cm_res_id from catmpsumm.cm_resources; \
";

AX_INT8 * axCmPerfSummary::SQL_PERF_LOG = " \
  select tm_sec,downstream_snr,downstream_power,upstream_power, \
  t1_timeouts,t2_timeouts,t3_timeouts,t4_timeouts \
  from catmpsumm.cm_perf_log where cm_res_id=%u; \
";

AX_INT8 * axCmPerfSummary::SQL_INSERT_SUMMARY = " \
insert into casumm.cm_perf_summary(cm_res_id,month,day,year, \
  min_dwnstrm_snr,max_dwnstrm_snr,avg_dwnstrm_snr, \
  min_dwnstrm_pwr,max_dwnstrm_pwr,avg_dwnstrm_pwr, \
  min_upstream_pwr,max_upstream_pwr,avg_upstream_pwr, \
  t1_timeouts,t2_timeouts,t3_timeouts,t4_timeouts) values ( \
  %u,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%u,%u,%u,%u); \
";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCmPerfSummary::axCmPerfSummary() :
  m_todaysDate(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmPerfSummary::~axCmPerfSummary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmPerfSummary::axCmPerfSummary(struct tm * in) :
  m_todaysDate(in)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axCmPerfSummary::summarize(void)
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
axCmPerfSummary::summarizeForCm(MYSQL * dbHdl, AX_UINT32 cmResId)
{
  static const char * myName="summPerfForCm:";

  bool ret = false;

  char sql[2048];

  bool  errored = false;
  int   col;
  int   rowNum;

  AX_INT16  num_samples = 0;

  AX_INT16  downstream_snr;
  AX_INT16  downstream_power;
  AX_INT16  upstream_power;
  AX_UINT32 tm_sec;
  AX_UINT32 t1_to;
  AX_UINT32 t2_to;
  AX_UINT32 t3_to;
  AX_UINT32 t4_to;

  AX_INT16  min_downstream_snr = 0;
  AX_INT16  max_downstream_snr = 0;
  AX_INT16  avg_downstream_snr = 0;

  AX_INT16  min_downstream_power = 0;
  AX_INT16  max_downstream_power = 0;
  AX_INT16  avg_downstream_power = 0;

  AX_INT16  min_upstream_power = 0;
  AX_INT16  max_upstream_power = 0;
  AX_INT16  avg_upstream_power = 0;

  AX_UINT32 t1_timeouts = 0;
  AX_UINT32 t2_timeouts = 0;
  AX_UINT32 t3_timeouts = 0;
  AX_UINT32 t4_timeouts = 0;

  AX_UINT32 min_downstream_snr_time = 0;
  AX_UINT32 max_downstream_snr_time = 0;

  AX_UINT32 min_downstream_power_time = 0;
  AX_UINT32 max_downstream_power_time = 0;

  AX_UINT32 min_upstream_power_time = 0;
  AX_UINT32 max_upstream_power_time = 0;

  AX_INT32  cumulative_downstream_snr = 0;
  AX_INT32  cumulative_downstream_power = 0;
  AX_INT32  cumulative_upstream_power = 0;

  sprintf(sql, SQL_PERF_LOG, cmResId);
  // ACE_DEBUG((LM_DEBUG, "%s\n", sql));

  MYSQL_RES * cmtsCountsRs;

  MYSQL_ROW row;

  if (mysql_real_query(dbHdl, sql, strlen(sql))) {
    ACE_DEBUG((LM_ERROR, "%s sql failed\n", myName));
    goto EXIT_LABEL;
  }

  cmtsCountsRs = mysql_store_result(dbHdl);

  for (row=mysql_fetch_row(cmtsCountsRs), rowNum=0;
       row && !errored;
       row=mysql_fetch_row(cmtsCountsRs), rowNum++) {

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
      downstream_snr = (AX_INT16) atoi(row[col]);
    } else {
      errored = true;
      break;
    }
    col++;

    if (lengths[col]) {
      downstream_power = (AX_INT16) atoi(row[col]);
    } else {
      errored = true;
      break;
    }
    col++;

    if (lengths[col]) {
      upstream_power = (AX_INT16) atoi(row[col]);
    } else {
      errored = true;
      break;
    }
    col++;

    if (lengths[col]) {
      t1_to = (AX_UINT32) strtoul(row[col], NULL, 10);
    } else {
      errored = true;
      break;
    }
    col++;

    if (lengths[col]) {
      t2_to = (AX_UINT32) strtoul(row[col], NULL, 10);
    } else {
      errored = true;
      break;
    }
    col++;

    if (lengths[col]) {
      t3_to = (AX_UINT32) strtoul(row[col], NULL, 10);
    } else {
      errored = true;
      break;
    }
    col++;

    if (lengths[col]) {
      t4_to = (AX_UINT32) strtoul(row[col], NULL, 10);
    } else {
      errored = true;
      break;
    }
    col++;

    if (rowNum) {

      if (downstream_snr < min_downstream_snr) {
        min_downstream_snr = downstream_snr;
        min_downstream_snr_time = tm_sec;
      }

      if (downstream_snr > max_downstream_snr) {
        max_downstream_snr = downstream_snr;
        max_downstream_snr_time = tm_sec;
      }

      if (downstream_power < min_downstream_power) {
        min_downstream_power = downstream_power;
        min_downstream_power_time = tm_sec;
      }

      if (downstream_power > max_downstream_power) {
        max_downstream_power = downstream_power;
        max_downstream_power_time = tm_sec;
      }

      if (upstream_power < min_upstream_power) {
        min_upstream_power = upstream_power;
        min_upstream_power_time = tm_sec;
      }

      if (upstream_power > max_upstream_power) {
        max_upstream_power = upstream_power;
        max_upstream_power_time = tm_sec;
      }

    } else {

      min_downstream_snr = max_downstream_snr = downstream_snr;
      min_downstream_snr_time = max_downstream_snr_time = tm_sec;

      min_downstream_power = max_downstream_power = downstream_power;
      min_downstream_power_time = max_downstream_power_time = tm_sec;

      min_upstream_power = max_upstream_power = upstream_power;
      min_upstream_power_time = max_upstream_power_time = tm_sec;

    }

    if (t1_to > t1_timeouts) t1_timeouts = t1_to;
    if (t2_to > t2_timeouts) t2_timeouts = t2_to;
    if (t3_to > t3_timeouts) t3_timeouts = t3_to;
    if (t4_to > t4_timeouts) t4_timeouts = t4_to;

    num_samples++;

    cumulative_downstream_snr += downstream_snr;
    cumulative_downstream_power += downstream_power;
    cumulative_upstream_power += upstream_power;

#if 0
    if (cmResId==4) {
      ACE_DEBUG((LM_DEBUG, "%s dwn_power=%d, cumulative=%d\n", 
             myName, downstream_power, cumulative_downstream_power));
    }
#endif

  }

  mysql_free_result(cmtsCountsRs);

  if (errored) {
    goto EXIT_LABEL;
  }

  if (num_samples) {

    avg_downstream_snr = cumulative_downstream_snr / num_samples;
    avg_downstream_power = cumulative_downstream_power / num_samples;
    avg_upstream_power = cumulative_upstream_power / num_samples;

    sprintf(sql, SQL_INSERT_SUMMARY, cmResId,
       m_todaysDate->tm_mon,m_todaysDate->tm_mday,m_todaysDate->tm_year,
       min_downstream_snr,max_downstream_snr,avg_downstream_snr,
       min_downstream_power,max_downstream_power,avg_downstream_power,
       min_upstream_power,max_upstream_power,avg_upstream_power,
       t1_timeouts,t2_timeouts,t3_timeouts,t4_timeouts);

    // printf("SQL: %s\n", sql);

#if 0
    if (cmResId==4) {
      ACE_DEBUG((LM_DEBUG, "%s cum=%d, samples=%d, avg_dwn_pwr=%d\n",
        myName, cumulative_downstream_power, num_samples, 
        avg_downstream_power));
    }
#endif

    if (mysql_real_query(dbHdl, sql, strlen(sql))) {
      ACE_DEBUG((LM_ERROR, "%s sql failed\n", myName));
      goto EXIT_LABEL;
    }

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


