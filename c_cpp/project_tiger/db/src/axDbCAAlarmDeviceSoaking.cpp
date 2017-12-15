
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
#include "axDbCAAlarmDeviceSoaking.hpp"
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
AX_INT8 * axDbCAAlarmDeviceSoaking::Insert = \
"insert into caalarm.alarm_devices_soaking(root_alarm_id,res_id) values(%lld,%u)";

AX_INT8 * axDbCAAlarmDeviceSoaking::Query = \
"select alm.* from caalarm.alarm_devices_soaking alm ";

AX_INT8 * axDbCAAlarmDeviceSoaking::Delete = \
"delete from caalarm.alarm_devices_soaking where ";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCAAlarmDeviceSoaking::axDbCAAlarmDeviceSoaking() :
  m_id((AX_UINT64)0),
  m_rootAlarmId((AX_UINT64)0),
  m_resId(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCAAlarmDeviceSoaking::~axDbCAAlarmDeviceSoaking()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAAlarmDeviceSoaking::axDbCAAlarmDeviceSoaking(AX_UINT64 id, 
                                                    DB_RESID_t resId) :
  m_id((AX_UINT64)0),
  m_rootAlarmId(id),
  m_resId(resId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAAlarmDeviceSoaking::axDbCAAlarmDeviceSoaking(AX_UINT64 alarmId) :
  m_id((AX_UINT64)0),
  m_rootAlarmId(alarmId),
  m_resId(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAAlarmDeviceSoaking::axDbCAAlarmDeviceSoaking(
                                const axDbCAAlarmDeviceSoaking & in) :
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
axDbCAAlarmDeviceSoaking::getRow(void)
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
axDbCAAlarmDeviceSoaking::getRow(AX_INT8 * qryStr)
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
axDbCAAlarmDeviceSoaking::getRows(axListBase & rl)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmDeviceSoaking::getRows(axListBase & rl, AX_INT8 * qryStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmDeviceSoaking::insertRow(void)
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
axDbCAAlarmDeviceSoaking::updateRow(void)
{
  return (false);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAAlarmDeviceSoaking::deleteRow(void)
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


