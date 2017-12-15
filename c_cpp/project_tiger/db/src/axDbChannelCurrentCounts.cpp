//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbChannelCurrentCounts.hpp"
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
AX_INT8 * axDbChannelCurrentCounts::QueryCurrentCounts = \
"select * from caperf.channel_current_counts";

AX_INT8 * axDbChannelCurrentCounts::UpdateCurrentCounts = \
"update caperf.channel_current_counts set cm_total=%d, cm_online=%d, "
"mta_total=%d, mta_available=%d, last_change_tm=%d where channel_res_id=%u";

AX_INT8 * axDbChannelCurrentCounts::InsertCurrentCounts = \
"insert into caperf.channel_current_counts(channel_res_id,last_change_tm,"
"cm_total,cm_online,mta_total,mta_available) values(%u,%u,%d,%d,%d,%d)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbChannelCurrentCounts::axDbChannelCurrentCounts()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbChannelCurrentCounts::~axDbChannelCurrentCounts()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbChannelCurrentCounts::axDbChannelCurrentCounts(DB_RESID_t resId) :
  axDbAbstractCurrentCounts(resId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbChannelCurrentCounts::axDbChannelCurrentCounts(DB_RESID_t resId,
                              time_t tv_sec, axIntCounts_t * counts) :
  axDbAbstractCurrentCounts(resId, tv_sec, counts)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannelCurrentCounts::getRow(void)
{
  bool ret = false;

  char qryStr[512];
  char whereClause[128];

  if (m_resId) {
    sprintf(whereClause, "where channel_res_id=%u", m_resId);
    sprintf(qryStr, "%s %s", QueryCurrentCounts, whereClause);
    ret = getRow(qryStr);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannelCurrentCounts::getRow(AX_INT8 * sqlStr)
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
axDbChannelCurrentCounts::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannelCurrentCounts::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannelCurrentCounts::insertOrUpdateRow(void)
{
  bool ret;

  axDbChannelCurrentCounts tmpObj;
  tmpObj.m_resId = m_resId;
  if (tmpObj.getRow()) {
    ret = updateRow();
  } else {
    ret = insertRow();
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannelCurrentCounts::insertRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, InsertCurrentCounts, 
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
axDbChannelCurrentCounts::updateRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, UpdateCurrentCounts,
            m_totalCm, m_onlineCm, m_totalMta, m_availableMta, m_timeSec, 
            m_resId);

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
axDbChannelCurrentCounts::deleteRow(void)
{
  bool ret = false;

  return (ret);
}



