
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
#include "axDbCACurrentAlarm.hpp"
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
AX_INT8 * axDbCACurrentAlarm::Insert = \
"insert into caalarm.current_alarm(root_alarm_id,soak_duration,alarm_state) "
"values(%lld,%d,%d)";

AX_INT8 * axDbCACurrentAlarm::Update = \
"update caalarm.current_alarm set alarm_state=%d where id=%lld";

AX_INT8 * axDbCACurrentAlarm::Delete = \
"delete from caalarm.current_alarm where id=%lld";

AX_INT8 * axDbCACurrentAlarm::Query = \
"select ca.* from caalarm.current_alarm ca ";

AX_INT8 * axDbCACurrentAlarm::QueryByState = \
"select ca.* from caalarm.current_alarm ca where ca.alarm_state=%d";

AX_INT8 * axDbCACurrentAlarm::QueryByResource = \
"select ca.* from caalarm.current_alarm ca join "
"caalarm.root_alarm ra using(root_alarm_id) where ra.res_id=%u";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCACurrentAlarm::axDbCACurrentAlarm() :
  m_id((AX_UINT64)0),
  m_rootAlarmId((AX_UINT64)0),
  m_soakDuration(0),
  m_alarmState(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCACurrentAlarm::~axDbCACurrentAlarm()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCACurrentAlarm::axDbCACurrentAlarm(AX_UINT64 id) :
  m_id(0),
  m_rootAlarmId(id),
  m_soakDuration(0),
  m_alarmState(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCACurrentAlarm::axDbCACurrentAlarm(axAbstractCAAlarm * hO) :
  m_id(0),
  m_rootAlarmId(hO->getAlarmId()),
  m_soakDuration(hO->getAlarmSoakWindow()),
  m_alarmState(DB_ALARM_STATE_SOAKING)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCACurrentAlarm::axDbCACurrentAlarm(const axDbCACurrentAlarm & in) :
  axDbCAAlarm(),
  m_id(in.m_id),
  m_rootAlarmId(in.m_rootAlarmId),
  m_soakDuration(in.m_soakDuration),
  m_alarmState(in.m_alarmState)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCACurrentAlarm::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[64];

  if (!m_rootAlarmId) {
    goto EXIT_LABEL;
  }

  sprintf(whereClause, "where ca.root_alarm_id=%lld", m_rootAlarmId);

  sprintf(qryStr, "%s %s", Query, whereClause);

  ret = getRow(qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCACurrentAlarm::getRow(AX_INT8 * qryStr)
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
axDbCACurrentAlarm::getRows(axListBase & rl)
{
  bool ret = false;

  char qryStr[512];

  axDbCARootAlarm rootAlm(m_rootAlarmId);

  if (!m_rootAlarmId) {
    goto EXIT_LABEL;
  }

  if (!rootAlm.getRow()) {
    goto EXIT_LABEL;
  }

  sprintf(qryStr, QueryByResource, rootAlm.m_resId);

  ret = getRows(rl, qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCACurrentAlarm::getRows(axListBase & rl, AX_INT8 * qryStr)
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
      axDbCACurrentAlarm * out = new axDbCACurrentAlarm(*this);
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
axDbCACurrentAlarm::insertRow(void)
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
                        m_rootAlarmId, m_soakDuration, m_alarmState);

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
axDbCACurrentAlarm::updateRow(void)
{
  bool ret = false;

  char sqlStr[512];

  sprintf(sqlStr, Update, m_alarmState, m_id);

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
axDbCACurrentAlarm::deleteRow(void)
{
  bool ret = false;

  char sqlStr[512];

  sprintf(sqlStr, Delete, m_id);

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


#if 0
//********************************************************************
// method:
//********************************************************************
bool
axDbCACurrentAlarm::getSecondaryData(void)
{
  return (false);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCACurrentAlarm::GetCurrentAlarms(axAbstractCAAlarm * intAlarm, 
                                                    axListBase & retL)
{
  bool ret = false;

  axDbCACurrentAlarm dbCurrAlm(intAlarm);
  axListBase l;
  dbCurrAlm.getRows(l);

  return (ret);
}
#endif


