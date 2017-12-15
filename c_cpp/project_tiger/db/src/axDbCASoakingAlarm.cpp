
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
#include "axDbCASoakingAlarm.hpp"
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
AX_INT8 * axDbCASoakingAlarm::Insert = \
"insert into caalarm.soaking_alarms(root_alarm_id) values(%lld)";

AX_INT8 * axDbCASoakingAlarm::Query = \
"select alm.* from caalarm.soaking_alarms alm ";

AX_INT8 * axDbCASoakingAlarm::Delete = \
"delete from caalarm.soaking_alarms where root_alarm_id=%lld";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCASoakingAlarm::axDbCASoakingAlarm() :
  m_id((AX_UINT64)0),
  m_rootAlarmId((AX_UINT64)0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCASoakingAlarm::~axDbCASoakingAlarm()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCASoakingAlarm::axDbCASoakingAlarm(AX_UINT64 alarmId) :
  m_id((AX_UINT64)0),
  m_rootAlarmId(alarmId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCASoakingAlarm::axDbCASoakingAlarm(const axDbCASoakingAlarm & in) :
  axDbCAAlarm(),
  m_id(in.m_id),
  m_rootAlarmId(in.m_rootAlarmId)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCASoakingAlarm::getRow(void)
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
axDbCASoakingAlarm::getRow(AX_INT8 * qryStr)
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
axDbCASoakingAlarm::getRows(axListBase & rl)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCASoakingAlarm::getRows(axListBase & rl, AX_INT8 * qryStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCASoakingAlarm::insertRow(void)
{
  bool ret = false;
  char sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_rootAlarmId);

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
axDbCASoakingAlarm::updateRow(void)
{
  return (false);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCASoakingAlarm::deleteRow(void)
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


