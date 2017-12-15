
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
#include "axDbCARootAlarm.hpp"
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
AX_INT8 * axDbCARootAlarm::Insert = \
"insert into caalarm.root_alarm(res_id,detection_time,det_time_usec,"
"alarm_type,alarm_sub_type) "
"values(%u,%u,%u,%d,%d)";

AX_INT8 * axDbCARootAlarm::Query = \
"select ra.* from caalarm.root_alarm ra ";

AX_INT8 * axDbCARootAlarm::QueryByState = \
"select ra.* from caalarm.current_alarm ca join "
"caalarm.root_alarm ra using(root_alarm_id) where ca.alarm_state=%d";

AX_INT8 * axDbCARootAlarm::Delete = \
"delete from caalarm.root_alarm where root_alarm_id=%lld";

AX_INT8 * axDbCARootAlarm::Update = \
"update caalarm.root_alarm set alarm_type=%d, alarm_sub_type=%d "
"where root_alarm_id=%lld";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCARootAlarm::axDbCARootAlarm() :
  m_rootAlarmId((AX_UINT64)0),
  m_resId(0),
  m_detectionTime(0),
  m_detectionTimeUSec(0),
  m_alarmType(0),
  m_alarmSubType(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCARootAlarm::~axDbCARootAlarm()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCARootAlarm::axDbCARootAlarm(AX_UINT64 rootAlmId) :
  m_rootAlarmId(rootAlmId),
  m_resId(0),
  m_detectionTime(0),
  m_detectionTimeUSec(0),
  m_alarmType(0),
  m_alarmSubType(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCARootAlarm::axDbCARootAlarm(DB_RESID_t resId) :
  m_rootAlarmId((AX_UINT64)0),
  m_resId(resId),
  m_detectionTime(0),
  m_detectionTimeUSec(0),
  m_alarmType(0),
  m_alarmSubType(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCARootAlarm::axDbCARootAlarm(axAbstractCAAlarm * hO) :
  m_rootAlarmId((AX_UINT64)0),
  m_resId(hO->getResId()),
  m_detectionTime(hO->getAlarmDetectionTime()),
  m_detectionTimeUSec(hO->getAlarmDetectionTimeUSec()),
  m_alarmType(hO->getAlarmType()),
  m_alarmSubType(hO->getAlarmSubType())
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCARootAlarm::axDbCARootAlarm(const axDbCARootAlarm & in) :
  axDbCAAlarm(),
  m_rootAlarmId(in.m_rootAlarmId),
  m_resId(in.m_resId),
  m_detectionTime(in.m_detectionTime),
  m_detectionTimeUSec(in.m_detectionTimeUSec),
  m_alarmType(in.m_alarmType),
  m_alarmSubType(in.m_alarmSubType)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCARootAlarm::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[64];

  if (m_rootAlarmId) {
    sprintf(whereClause, "where ra.root_alarm_id=%lld", m_rootAlarmId);
    sprintf(qryStr, "%s %s", Query, whereClause);
    ret = getRow(qryStr);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCARootAlarm::getRow(AX_INT8 * qryStr)
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
axDbCARootAlarm::getRows(axListBase & rl)
{
  bool ret = false;

  char qryStr[512];
  char whereClause[64];

  if (!m_resId) {
    goto EXIT_LABEL;
  }

  sprintf(whereClause, "where ra.res_id=%u", m_resId);

#if 0
  if (m_alarmType && m_alarmSubType) {
    sprintf(whereClause, 
                  "%s and ra.alarm_type=%d and ra.alarm_sub_type=%d", 
                             whereClause, m_alarmType, m_alarmSubType);
  }
#endif

  sprintf(qryStr, "%s %s", Query, whereClause);

  ret = getRows(rl, qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCARootAlarm::getRows(axListBase & rl, AX_INT8 * qryStr)
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
      axDbCARootAlarm * out = new axDbCARootAlarm(*this);
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
axDbCARootAlarm::insertRow(void)
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
            m_alarmType, m_alarmSubType);

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    m_rootAlarmId = query->getInsertId();

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCARootAlarm::updateRow(void)
{
  bool ret = false;

  char sqlStr[512];

  sprintf(sqlStr, Update, m_alarmType, m_alarmSubType, m_rootAlarmId);

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
axDbCARootAlarm::deleteRow(void)
{
  bool ret = false;
  char sqlStr[512];
  bool rc;

  // rc = deleteChildren();

  sprintf(sqlStr, Delete, m_rootAlarmId);

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


#if 0
//********************************************************************
// method:
//********************************************************************
bool
axDbCARootAlarm::deleteChildren(void)
{
  bool ret = false;

  axDbCAAlarmSecondary secAlarm(m_rootAlarmId);
  secAlarm.deleteRow();



  return (ret);
}
#endif


