//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbCurrentCmPerf.hpp"
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
AX_INT8 * axDbCurrentCmPerf::QueryCurrent = \
"select * from caperf.cm_current_perf ";

AX_INT8 * axDbCurrentCmPerf::UpdateCurrent = \
"update caperf.cm_current_perf set downstream_snr=%d,downstream_power=%d,"
"upstream_power=%d,t1_timeouts=%u,t2_timeouts=%u,t3_timeouts=%u"
"t4_timeouts=%u,sum_tca_tm=%u,sum_tcafree_tm=%u,tca_change_tm=%u,"
"state_changes=%d,last_log_tm=%u where cm_res_id=%u";

AX_INT8 * axDbCurrentCmPerf::InsertCurrent = \
"insert into caperf.cm_current_perf(cm_res_id,downstream_snr,"
"downstream_power,upstream_power,t1_timeouts,t2_timeouts,t3_timeouts,"
"t4_timeouts,sum_tca_tm,sum_tcafree_tm,tca_change_tm,state_changes,last_log_tm) "
"values(%u,%d,%d,%d,%u,%u,%u,%u,%u,%u,%u,%d,%u)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCurrentCmPerf::axDbCurrentCmPerf() :
  m_sumTcaTime(0), m_sumNonTcaTime(0), m_lastTcaChangeTime(0),
  m_stateChanges(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCurrentCmPerf::~axDbCurrentCmPerf()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCurrentCmPerf::axDbCurrentCmPerf(DB_RESID_t resId) :
  axDbAbstractCmPerf(resId),
  m_sumTcaTime(0), m_sumNonTcaTime(0), m_lastTcaChangeTime(0),
  m_stateChanges(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCurrentCmPerf::axDbCurrentCmPerf(DB_RESID_t resId, time_t tv_sec,
                                             axIntCmNonkey_t * cmNk) :
  axDbAbstractCmPerf(resId, tv_sec, cmNk),
  m_sumTcaTime(0), m_sumNonTcaTime(0), m_lastTcaChangeTime(0),
  m_stateChanges(0)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCurrentCmPerf::getRow(void)
{
  bool ret = false;

  char qryStr[512];
  char whereClause[128];

  if (m_resId) {
    sprintf(whereClause, "where cm_res_id=%u", m_resId);
    sprintf(qryStr, "%s %s", QueryCurrent, whereClause);
    ret = getRow(qryStr);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCurrentCmPerf::getRow(AX_INT8 * sqlStr)
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

    axDbResultSet * rs = dynamic_cast<axDbResultSet *> (query->getResultSet());

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
axDbCurrentCmPerf::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCurrentCmPerf::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCurrentCmPerf::insertRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, InsertCurrent, 
            m_resId, m_downstreamSnr, m_downstreamPower, m_upstreamPower, 
            m_t1Timeouts, m_t2Timeouts, m_t3Timeouts, m_t4Timeouts,
            m_sumTcaTime, m_sumNonTcaTime, m_lastTcaChangeTime, 
            m_stateChanges, m_lastLogTime);

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
axDbCurrentCmPerf::updateRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, UpdateCurrent, 
            m_downstreamSnr, m_downstreamPower, m_upstreamPower, 
            m_t1Timeouts, m_t2Timeouts, m_t3Timeouts, m_t4Timeouts, 
            m_sumTcaTime, m_sumNonTcaTime, m_lastTcaChangeTime,
            m_stateChanges, m_lastLogTime, m_resId);

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
axDbCurrentCmPerf::deleteRow(void)
{
  bool ret = false;

  return (ret);
}



