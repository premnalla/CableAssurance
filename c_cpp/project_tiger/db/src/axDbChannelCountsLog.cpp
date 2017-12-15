//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbChannelCountsLog.hpp"
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
AX_INT8 * axDbChannelCountsLog::QueryCountsLog = \
"select * from caperf.channel_counts_log";

AX_INT8 * axDbChannelCountsLog::InsertCountsLog = \
"insert into caperf.channel_counts_log(channel_res_id,tm_sec,cm_total,cm_online,"
"mta_total,mta_available) values(%u,%u,%d,%d,%d,%d)";


//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbChannelCountsLog::axDbChannelCountsLog()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbChannelCountsLog::~axDbChannelCountsLog()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbChannelCountsLog::axDbChannelCountsLog(DB_RESID_t resId) :
  axDbAbstractCounts(resId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbChannelCountsLog::axDbChannelCountsLog(DB_RESID_t resId,
                              time_t tv_sec, axIntCounts_t * counts) :
  axDbAbstractCounts(resId, tv_sec, counts)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannelCountsLog::getRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannelCountsLog::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannelCountsLog::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannelCountsLog::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannelCountsLog::insertRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, InsertCountsLog, 
            m_resId, m_timeSec, m_totalCm, m_onlineCm, m_totalMta, 
                                                     m_availableMta);

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
axDbChannelCountsLog::updateRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannelCountsLog::deleteRow(void)
{
  bool ret = false;

  return (ret);
}


