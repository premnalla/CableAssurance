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
#include "axDbCAHistoricalAlarm.hpp"
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
AX_INT8 * axDbCAHistoricalAlarm::Insert = \
"insert into caalarm.historical_alarm(root_alarm_id,cleared_user_id,"
"soak_duration, alarm_state) values(%lld,%u,%d,%d)";

AX_INT8 * axDbCAHistoricalAlarm::Query = \
"select ha.* from caalarm.historical_alarm ha ";

AX_INT8 * axDbCAHistoricalAlarm::Update = \
"update caalarm.caalarm.historical_alarm set cleared_user_id=%u, "
"alarm_state=%d where id=%lld";

AX_INT8 * axDbCAHistoricalAlarm::Delete = \
"delete from caalarm.caalarm.historical_alarm where id=%lld";

AX_INT8 * axDbCAHistoricalAlarm::QueryByState = \
"select ha.* from caalarm.caalarm.historical_alarm ha where ha.alarm_state=%d";

AX_INT8 * axDbCAHistoricalAlarm::QueryByResource = \
"select ha.* from caalarm.caalarm.historical_alarm ha join "
"caalarm.root_alarm ra using(root_alarm_id) where ra.res_id=%u";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCAHistoricalAlarm::axDbCAHistoricalAlarm() :
  m_id((AX_UINT64)0),
  m_rootAlarmId((AX_UINT64)0),
  m_clearedUserId(0),
  m_soakDuration(0),
  m_alarmState(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCAHistoricalAlarm::~axDbCAHistoricalAlarm()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAHistoricalAlarm::axDbCAHistoricalAlarm(AX_UINT64 id) :
  m_id(0),
  m_rootAlarmId(id),
  m_clearedUserId(0),
  m_soakDuration(0),
  m_alarmState(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAHistoricalAlarm::axDbCAHistoricalAlarm(axAbstractCAAlarm * hO) :
  m_id(0),
  m_rootAlarmId(hO->getAlarmId()),
  m_clearedUserId(0),
  m_soakDuration(hO->getAlarmSoakWindow()),
  m_alarmState(DB_ALARM_STATE_SOAKING)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCAHistoricalAlarm::axDbCAHistoricalAlarm(const axDbCAHistoricalAlarm & in) :
  axDbCAAlarm(),
  m_id(in.m_id),
  m_rootAlarmId(in.m_rootAlarmId),
  m_clearedUserId(in.m_clearedUserId),
  m_soakDuration(in.m_soakDuration),
  m_alarmState(in.m_alarmState)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAHistoricalAlarm::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[64];

  if (!m_rootAlarmId) {
    goto EXIT_LABEL;
  }

  sprintf(whereClause, "where ha.root_alarm_id=%lld", m_rootAlarmId);

  sprintf(qryStr, "%s %s", Query, whereClause);

  ret = getRow(qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAHistoricalAlarm::getRow(AX_INT8 * qryStr)
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
axDbCAHistoricalAlarm::getRows(axListBase & rl)
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
axDbCAHistoricalAlarm::getRows(axListBase & rl, AX_INT8 * qryStr)
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
      axDbCAHistoricalAlarm * out = new axDbCAHistoricalAlarm(*this);
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
axDbCAHistoricalAlarm::insertRow(void)
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
                        m_rootAlarmId, m_clearedUserId, m_soakDuration, 
                                                         m_alarmState);

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
axDbCAHistoricalAlarm::updateRow(void)
{
  bool ret = false;

  char sqlStr[512];

  sprintf(sqlStr, Update, m_clearedUserId, m_alarmState, m_id);

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
axDbCAHistoricalAlarm::deleteRow(void)
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
axDbCAHistoricalAlarm::getSecondaryData(void)
{
  return (false);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCAHistoricalAlarm::GetCurrentAlarms(axAbstractCAAlarm * intAlarm, 
                                                    axListBase & retL)
{
  bool ret = false;

  axDbCAHistoricalAlarm dbCurrAlm(intAlarm);
  axListBase l;
  dbCurrAlm.getRows(l);

  return (ret);
}
#endif


