//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbCmPerfLog.hpp"
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
AX_INT8 * axDbCmPerfLog::Query = \
"select * from caperf.cm_perf_log";

AX_INT8 * axDbCmPerfLog::Insert = \
"insert into caperf.cm_perf_log(cm_res_id,tm_sec,downstream_snr,"
"downstream_power,upstream_power,t1_timeouts,t2_timeouts,t3_timeouts,"
"t4_timeouts) values(%u,%u,%d,%d,%d,%u,%u,%u,%u)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCmPerfLog::axDbCmPerfLog()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCmPerfLog::~axDbCmPerfLog()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCmPerfLog::axDbCmPerfLog(DB_RESID_t resId) :
  axDbAbstractCmPerf(resId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCmPerfLog::axDbCmPerfLog(DB_RESID_t resId, time_t tv_sec,
                                             axIntCmNonkey_t * cmNk) :
  axDbAbstractCmPerf(resId, tv_sec, cmNk)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmPerfLog::getRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmPerfLog::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmPerfLog::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmPerfLog::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmPerfLog::insertRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, 
            m_resId, m_lastLogTime, m_downstreamSnr, m_downstreamPower, 
            m_upstreamPower, m_t1Timeouts, m_t2Timeouts, m_t3Timeouts, 
                                                        m_t4Timeouts);

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
axDbCmPerfLog::updateRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmPerfLog::deleteRow(void)
{
  bool ret = false;

  return (ret);
}



