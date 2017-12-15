
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
#include "axDbCAAlarmSecondary.hpp"
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
AX_INT8 * axDbCAAlarmSecondary::Insert = \
"insert into caalarm.alarm_secondary(root_alarm_id,presoak_total,presoak_count,"
"postsoak_total,postsoak_count,alarm_det_window,alarm_threshold) "
"values(%lld,%d,%d,%d,%d,%d,%d)";

AX_INT8 * axDbCAAlarmSecondary::Query = \
"select alm.* from caalarm.alarm_secondary alm ";

AX_INT8 * axDbCAAlarmSecondary::Delete = \
"delete from caalarm.alarm_secondary where root_alarm_id=%lld";

AX_INT8 * axDbCAAlarmSecondary::Update = \
"update caalarm.alarm_secondary set presoak_total=%d,presoak_count=%d,"
"postsoak_total=%d,postsoak_count=%d,alarm_det_window=%d,"
"alarm_threshold=% where root_alarm_id=%lld";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCAAlarmSecondary::axDbCAAlarmSecondary() :
  m_id((AX_UINT64)0),
  m_rootAlarmId((AX_UINT64)0),
  m_preSoakTotal(0),
  m_preSoakCount(0),
  m_postSoakTotal(0),
  m_postSoakCount(0),
  m_alarmDetectionWindow(0),
  m_alarmThreshold(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCAAlarmSecondary::~axDbCAAlarmSecondary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAAlarmSecondary::axDbCAAlarmSecondary(AX_UINT64 alarmId) :
  m_id((AX_UINT64)0),
  m_rootAlarmId(alarmId),
  m_preSoakTotal(0),
  m_preSoakCount(0),
  m_postSoakTotal(0),
  m_postSoakCount(0),
  m_alarmDetectionWindow(0),
  m_alarmThreshold(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAAlarmSecondary::axDbCAAlarmSecondary(
                                    const axDbCAAlarmSecondary & in) :
  axDbCAAlarm(),
  m_id(in.m_id),
  m_rootAlarmId(in.m_rootAlarmId),
  m_preSoakTotal(in.m_preSoakTotal),
  m_preSoakCount(in.m_preSoakCount),
  m_postSoakTotal(in.m_postSoakTotal),
  m_postSoakCount(in.m_postSoakCount),
  m_alarmDetectionWindow(in.m_alarmDetectionWindow),
  m_alarmThreshold(in.m_alarmThreshold)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmSecondary::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[64];

  if (m_rootAlarmId) {
    sprintf(whereClause, "where alm.root_alarm_id=%lld", m_rootAlarmId);
    sprintf(qryStr, "%s %s", Query, whereClause);
    ret = getRow(qryStr);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmSecondary::getRow(AX_INT8 * qryStr)
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
axDbCAAlarmSecondary::getRows(axListBase & rl)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmSecondary::getRows(axListBase & rl, AX_INT8 * qryStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmSecondary::insertRow(void)
{
  bool ret = false;
  char sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_rootAlarmId, m_preSoakTotal, m_preSoakCount,
            m_postSoakTotal, m_preSoakCount, m_alarmDetectionWindow, 
            m_alarmThreshold);

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
axDbCAAlarmSecondary::updateRow(void)
{
  bool ret = false;
  char sqlStr[512];

  sprintf(sqlStr, Update,
          m_preSoakTotal, m_preSoakCount, m_postSoakTotal, 
          m_postSoakCount, m_alarmDetectionWindow, m_alarmThreshold, 
          m_rootAlarmId);

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

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmSecondary::deleteRow(void)
{
  bool ret = false;
  char sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Delete, m_rootAlarmId);

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


