
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbExternalizer.hpp"
#include "axDbConnectionFactory.hpp"
#include "axDbGenericQuery.hpp"
#include "axDbConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbResultSet.hpp"
#include "axDbCAAlarmHistory.hpp"
#include "axDbReadConnHelper.hpp"
#include "axIteratorHolder.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbCAAlarmHistory::Insert = \
"insert into caalarm.alarm_state_history(root_alarm_id,alarm_state) values(%lld,%u)";

AX_INT8 * axDbCAAlarmHistory::Query = \
"select alm.* from caalarm.alarm_state_history alm ";

AX_INT8 * axDbCAAlarmHistory::Delete = \
"delete from caalarm.alarm_state_history where ash_id=%u";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCAAlarmHistory::axDbCAAlarmHistory() :
  m_id((AX_UINT64)0),
  m_rootAlarmId((AX_UINT64)0),
  m_alarmState(DB_ALARM_STATE_SOAKING)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCAAlarmHistory::~axDbCAAlarmHistory()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAAlarmHistory::axDbCAAlarmHistory(AX_UINT64 id) :
  m_id((AX_UINT64)0),
  m_rootAlarmId(id),
  m_alarmState(DB_ALARM_STATE_UNKNOWN)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAAlarmHistory::axDbCAAlarmHistory(axDbCARootAlarm & rootAlm) :
  m_id((AX_UINT64)0),
  m_rootAlarmId(rootAlm.m_rootAlarmId),
  m_alarmState(DB_ALARM_STATE_UNKNOWN)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAAlarmHistory::axDbCAAlarmHistory(axDbCACurrentAlarm & currAlm) :
  m_id((AX_UINT64)0),
  m_rootAlarmId(currAlm.m_rootAlarmId),
  m_alarmState(currAlm.m_alarmState)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAAlarmHistory::axDbCAAlarmHistory(axDbCAHistoricalAlarm & histAlm) :
  m_id((AX_UINT64)0),
  m_rootAlarmId(histAlm.m_rootAlarmId),
  m_alarmState(histAlm.m_alarmState)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAAlarmHistory::axDbCAAlarmHistory(const axDbCAAlarmHistory & in) :
  axDbCAAlarm(),
  m_id(in.m_id),
  m_rootAlarmId(in.m_rootAlarmId),
  m_alarmState(in.m_alarmState)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmHistory::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[64];

  if (m_id) {
    sprintf(whereClause, "where alm.ash_id=%u", m_id);
    sprintf(qryStr, "%s %s", Query, whereClause);
    ret = getRow(qryStr);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmHistory::getRow(AX_INT8 * qryStr)
{
  bool ret = false;

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    bool qrc = query->execute(qryStr, *this);
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
axDbCAAlarmHistory::getRows(axListBase & rl)
{
  bool ret = false;

  char qryStr[512];
  char whereClause[64];

  if (!m_rootAlarmId) {
    goto EXIT_LABEL;
  }

  sprintf(whereClause, "where alm.root_alarm_id=%lld", m_rootAlarmId);

  sprintf(qryStr, "%s %s", Query, whereClause);

  ret = getRows(rl, qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmHistory::getRows(axListBase & rl, AX_INT8 * qryStr)
{
  bool ret = false;

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    bool qrc = query->execute(qryStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    axDbResultSet * rs = dynamic_cast<axDbResultSet *> (query->getResultSet());

    while (rs->getNext(*this)) {
      axDbCAAlarmHistory * out = new axDbCAAlarmHistory(*this);
      if (out) {
        rl.add(out);
      }
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
axDbCAAlarmHistory::insertRow(void)
{
  bool ret = false;
  char sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_rootAlarmId, m_alarmState);

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    m_id = query->getInsertId();

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmHistory::updateRow(void)
{
  return (false);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmHistory::deleteRow(void)
{
  bool ret = false;
  char sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Delete, m_id);

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


