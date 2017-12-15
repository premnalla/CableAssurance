
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbMtaCurrentAvailability.hpp"
#include "axDbExternalizer.hpp"
#include "axDbResultSet.hpp"
#include "axDbConnectionFactory.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbGenericQuery.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbMtaCurrentAvailability::Query = \
"select * from caperf.mta_current_avail ";

AX_INT8 * axDbMtaCurrentAvailability::Insert = \
"insert into caperf.mta_current_avail(mta_res_id,sum_avail_tm,"
"sum_unavail_tm,last_log_tm,last_avail_chg_tm,available,state_changes) "
"values(%u,%u,%u,%u,%u,%d,%d)";

AX_INT8 * axDbMtaCurrentAvailability::Update = \
"update caperf.mta_current_avail set sum_avail_tm=%u,sum_unavail_tm=%u,"
"last_log_tm=%u,last_avail_chg_tm=%u,available=%d,state_changes=%d "
"where mta_res_id=%u";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbMtaCurrentAvailability::axDbMtaCurrentAvailability()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbMtaCurrentAvailability::~axDbMtaCurrentAvailability()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbMtaCurrentAvailability::axDbMtaCurrentAvailability(DB_RESID_t resId) :
  axDbAbstractCurrentStatus(resId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbMtaCurrentAvailability::axDbMtaCurrentAvailability(
       DB_RESID_t resId, time_t tv_sec, axIntGenMtaNonkey_t * mtaNk) :
  axDbAbstractCurrentStatus(resId, tv_sec)
{
  m_currentValue = mtaNk->available;
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaCurrentAvailability::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[64];

  if (!m_resId) {
    goto EXIT_LABEL;
  }

  sprintf(whereClause, "where mta_res_id=%u", m_resId);

  sprintf(qryStr, "%s %s", Query, whereClause);

  ret = getRow(qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaCurrentAvailability::getRow(AX_INT8 * qryStr)
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
axDbMtaCurrentAvailability::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaCurrentAvailability::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaCurrentAvailability::insertRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_resId, m_time1, m_time2, m_lastLogTime, 
            m_lastStateChangeTime, m_currentValue, m_stateChanges);

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
axDbMtaCurrentAvailability::updateRow(void)
{
  bool ret = false;

  char sqlStr[512];

  sprintf(sqlStr, Update, m_time1, m_time2, m_lastLogTime, 
          m_lastStateChangeTime, m_currentValue, m_stateChanges, m_resId);

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
axDbMtaCurrentAvailability::deleteRow(void)
{
  bool ret = false;

  return (ret);
}


