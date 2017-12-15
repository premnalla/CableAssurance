
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
#include "axDbCAAlarmCoincidentalSoaking.hpp"
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
AX_INT8 * axDbCAAlarmCoincidentalSoaking::Insert = \
"insert into caalarm.alarm_coincidental_soaking(root_alarm_id,res_id) "
"values(%lld,%u)";

AX_INT8 * axDbCAAlarmCoincidentalSoaking::Query = \
"select alm.* from caalarm.alarm_coincidental_soaking alm ";

AX_INT8 * axDbCAAlarmCoincidentalSoaking::Delete = \
"delete from caalarm.alarm_coincidental_soaking where ";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCAAlarmCoincidentalSoaking::axDbCAAlarmCoincidentalSoaking() :
  m_id((AX_UINT64)0),
  m_rootAlarmId((AX_UINT64)0),
  m_resId(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCAAlarmCoincidentalSoaking::~axDbCAAlarmCoincidentalSoaking()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAAlarmCoincidentalSoaking::axDbCAAlarmCoincidentalSoaking(
                                     AX_UINT64 id, DB_RESID_t resId) :
  m_id((AX_UINT64)0),
  m_rootAlarmId(id),
  m_resId(resId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAAlarmCoincidentalSoaking::axDbCAAlarmCoincidentalSoaking(
                                                  AX_UINT64 alarmId) :
  m_id((AX_UINT64)0),
  m_rootAlarmId(alarmId),
  m_resId(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAAlarmCoincidentalSoaking::axDbCAAlarmCoincidentalSoaking(
                          const axDbCAAlarmCoincidentalSoaking & in) :
  axDbCAAlarm(),
  m_id(in.m_id),
  m_rootAlarmId(in.m_rootAlarmId),
  m_resId(in.m_resId)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmCoincidentalSoaking::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[64];

  if (m_rootAlarmId) {
    sprintf(whereClause, "where alm.root_alarm_id=%lld and alm.res_id=%u", 
                                                  m_rootAlarmId, m_resId);
    sprintf(qryStr, "%s %s", Query, whereClause);
    ret = getRow(qryStr);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmCoincidentalSoaking::getRow(AX_INT8 * qryStr)
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
axDbCAAlarmCoincidentalSoaking::getRows(axListBase & rl)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmCoincidentalSoaking::getRows(axListBase & rl, AX_INT8 * qryStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmCoincidentalSoaking::insertRow(void)
{
  bool ret = false;
  char sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_rootAlarmId, m_resId);

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
axDbCAAlarmCoincidentalSoaking::updateRow(void)
{
  return (false);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmCoincidentalSoaking::deleteRow(void)
{
  bool ret = false;
  char sqlStr[512];
  char whereClause[64];

  axDbConnectionFactory * cf;

  if (m_rootAlarmId && m_resId) {
    sprintf(whereClause, "where alm.root_alarm_id=%lld and alm.res_id=%u", 
                                                 m_rootAlarmId, m_resId);
  } else if (m_rootAlarmId) {
    sprintf(whereClause, "where alm.root_alarm_id=%lld", m_rootAlarmId);
  } else {
    goto EXIT_LABEL;
  }

  cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, "%s %s", Delete, whereClause);

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


