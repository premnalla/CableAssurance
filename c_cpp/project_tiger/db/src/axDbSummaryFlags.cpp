//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axDbSummaryFlags.hpp"
#include "axDbConnectionFactory.hpp"
#include "axDbExternalizer.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbGenericQuery.hpp"
#include "axDbResultSet.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbSummaryFlags::Query = \
"select * from casumm.summarization_flags";

AX_INT8 * axDbSummaryFlags::Update = \
"update casumm.summarization_flags set "
"overall_summ_started=%d, overall_summ_ended=%d, "
"db_cp_started=%d, db_cp_ended=%d, "
"cmts_counts_started=%d, cmts_counts_ended=%d, "
"channel_counts_started=%d, channel_counts_ended=%d, "
"hfc_counts_started=%d, hfc_counts_ended=%d, "
"cm_perf_summ_started=%d, cm_perf_summ_ended=%d, "
"cm_status_summ_started=%d, cm_status_summ_ended=%d, "
"mta_avail_summ_started=%d, mta_avail_summ_ended=%d, "
"alarm_summ_started=%d, alarm_summ_ended=%d, "
"cmperf_thresh_started=%d, cmperf_thresh_ended=%d, "
"hfc_status_started=%d, hfc_status_ended=%d, "
"hfc_tca_started=%d, hfc_tca_ended=%d "
"where id=1";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbSummaryFlags::axDbSummaryFlags() :
  m_overallSummStarted(0), m_overallSummEnded(0),
  m_dbCopyStarted(0), m_dbCopyEnded(0),
  m_cmtsCountsSummStarted(0), m_cmtsCountsSummEnded(0),
  m_hfcCountsSummStarted(0), m_hfcCountsSummEnded(0),
  m_channelCountsSummStarted(0), m_channelCountsSummEnded(0),
  m_cmPerfSummStarted(0), m_cmPerfSummEnded(0),
  m_cmStatusSummStarted(0), m_cmStatusSummEnded(0),
  m_mtaAvailSummStarted(0), m_mtaAvailSummEnded(0),
  m_alarmSummStarted(0), m_alarmSummEnded(0),
  m_cmPerfThreshSummStarted(0), m_cmPerfThreshSummEnded(0),
  m_hfcStatusSummStarted(0), m_hfcStatusSummEnded(0),
  m_hfcTcaSummStarted(0), m_hfcTcaSummEnded(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbSummaryFlags::~axDbSummaryFlags()
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbSummaryFlags::getRow(void)
{
  bool ret = false;

  ret = getRow(Query);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbSummaryFlags::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    axDbResultSet * rs = dynamic_cast<axDbResultSet *> 
                                               (query->getResultSet());

    if (!rs->getNext(*this)) {
      goto EXIT_LABEL;
    }

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbSummaryFlags::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbSummaryFlags::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbSummaryFlags::insertRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbSummaryFlags::updateRow(void)
{
  static const char * myName="summUpd:";

  bool ret = false;

  AX_INT8 sqlStr[2048];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Update,
            m_overallSummStarted, m_overallSummEnded, 
            m_dbCopyStarted, m_dbCopyEnded, 
            m_cmtsCountsSummStarted, m_cmtsCountsSummEnded, 
            m_hfcCountsSummStarted, m_hfcCountsSummEnded, 
            m_channelCountsSummStarted, m_channelCountsSummEnded, 
            m_cmPerfSummStarted, m_cmPerfSummEnded, 
            m_cmStatusSummStarted, m_cmStatusSummEnded, 
            m_mtaAvailSummStarted, m_mtaAvailSummEnded, 
            m_alarmSummStarted, m_alarmSummEnded,
            m_cmPerfThreshSummStarted, m_cmPerfThreshSummEnded,
            m_hfcStatusSummStarted, m_hfcStatusSummEnded,
            m_hfcTcaSummStarted, m_hfcTcaSummEnded
           );

    // ACE_DEBUG((LM_DB_DEBUG, "%s %s\n", myName, sqlStr));

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbSummaryFlags::deleteRow(void)
{
  bool ret = false;

  return (ret);
}



