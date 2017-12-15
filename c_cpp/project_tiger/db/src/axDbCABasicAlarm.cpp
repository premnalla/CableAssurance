
//********************************************************************
// OBSOLETED
//********************************************************************

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
#include "axDbCABasicAlarm.hpp"
#include "axDbReadConnHelper.hpp"
#include "axIteratorHolder.hpp"
#include "axDbCAAlarmSecondary.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbCABasicAlarm::Insert = \
"insert into caalarm.alarm_basic(res_id,detection_time,det_time_usec,"
"soak_duration,alarm_state,alarm_type,alarm_sub_type) "
"values(%u,%u,%u,%d,%d%d,%d)";

AX_INT8 * axDbCABasicAlarm::Query = \
"select alm.* from caalarm.alarm_basic alm ";

AX_INT8 * axDbCABasicAlarm::QueryByState = \
"select alm.* from caalarm.alarm_basic alm where alm.alarm_state=%d";

AX_INT8 * axDbCABasicAlarm::Update = \
"update caalarm.alarm_basic set alarm_state=%d where alarm_id=%lld";

AX_INT8 * axDbCABasicAlarm::Delete = \
"delete from caalarm.alarm_basic where alarm_id=%lld";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCABasicAlarm::axDbCABasicAlarm() :
  m_alarmId((AX_UINT64)0),
  m_resId(0),
  m_detectionTime(0),
  m_detectionTimeUSec(0),
  m_soakDuration(0),
  m_alarmState(DB_ALARM_STATE_SOAKING),
  m_alarmType(0),
  m_alarmSubType(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCABasicAlarm::~axDbCABasicAlarm()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCABasicAlarm::axDbCABasicAlarm(AX_UINT64 alarmId) :
  m_alarmId(alarmId),
  m_resId(0),
  m_detectionTime(0),
  m_detectionTimeUSec(0),
  m_soakDuration(0),
  m_alarmState(DB_ALARM_STATE_SOAKING),
  m_alarmType(0),
  m_alarmSubType(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCABasicAlarm::axDbCABasicAlarm(DB_RESID_t resId) :
  m_alarmId((AX_UINT64)0),
  m_resId(resId),
  m_detectionTime(0),
  m_detectionTimeUSec(0),
  m_soakDuration(0),
  m_alarmState(DB_ALARM_STATE_SOAKING),
  m_alarmType(0),
  m_alarmSubType(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCABasicAlarm::axDbCABasicAlarm(axAbstractCAAlarm * hO) :
  m_alarmId((AX_UINT64)0),
  m_resId(hO->getResId()),
  m_detectionTime(hO->getAlarmDetectionTime()),
  m_detectionTimeUSec(hO->getAlarmDetectionTimeUSec()),
  m_soakDuration(hO->getAlarmSoakWindow()),
  m_alarmState(DB_ALARM_STATE_SOAKING),
  m_alarmType(hO->getAlarmType()),
  m_alarmSubType(hO->getAlarmSubType())
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCABasicAlarm::axDbCABasicAlarm(const axDbCABasicAlarm & in) :
  axDbCAAlarm(),
  m_alarmId(in.m_alarmId),
  m_resId(in.m_resId),
  m_detectionTime(in.m_detectionTime),
  m_detectionTimeUSec(in.m_detectionTimeUSec),
  m_soakDuration(in.m_soakDuration),
  m_alarmState(in.m_alarmState),
  m_alarmType(in.m_alarmType),
  m_alarmSubType(in.m_alarmSubType)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCABasicAlarm::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[64];

  if (m_alarmId) {
    sprintf(whereClause, "where alm.alarm_id=%lld", m_alarmId);
    sprintf(qryStr, "%s %s", Query, whereClause);
    ret = getRow(qryStr);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCABasicAlarm::getRow(AX_INT8 * qryStr)
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
axDbCABasicAlarm::getRows(axListBase & rl)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[64];

  if (!m_resId) {
    goto EXIT_LABEL;
  }

  sprintf(whereClause, "where alm.res_id=%u", m_resId);

  if (m_alarmType && m_alarmSubType) {
    sprintf(whereClause, 
                  "%s and alm.alarm_type=%d and alm.alarm_sub_type=%d", 
                             whereClause, m_alarmType, m_alarmSubType);
  }

  sprintf(qryStr, "%s %s", Query, whereClause);

  ret = getRows(rl, qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCABasicAlarm::getRows(axListBase & rl, AX_INT8 * qryStr)
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
      axDbCABasicAlarm * out = new axDbCABasicAlarm(*this);
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
axDbCABasicAlarm::insertRow(void)
{
  bool ret = false;
  char sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert,
            m_resId, m_detectionTime, m_detectionTimeUSec,
            m_soakDuration, m_alarmState, m_alarmType, m_alarmSubType);

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    m_alarmId = query->getInsertId();

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCABasicAlarm::updateRow(void)
{
  bool ret = false;
  char sqlStr[512];

  sprintf(sqlStr, Update, m_alarmState, m_alarmId);

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
axDbCABasicAlarm::deleteRow(void)
{
  bool ret = false;
  char sqlStr[512];
  bool rc;

  rc = deleteChildren();

  sprintf(sqlStr, Delete, m_alarmId);

  axDbConnectionFactory * cf = 
                             axDbExternalizer::getConnectionFactory();

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
axDbCABasicAlarm::deleteChildren(void)
{
  bool ret = false;

  axDbCAAlarmSecondary secAlarm(m_alarmId);
  secAlarm.deleteRow();



  return (ret);
}


