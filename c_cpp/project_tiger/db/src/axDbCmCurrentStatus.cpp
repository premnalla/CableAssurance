
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbCmCurrentStatus.hpp"
#include "axDbExternalizer.hpp"
#include "axDbResultSet.hpp"
#include "axDbConnectionFactory.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbGenericQuery.hpp"
#include "axInternalCm.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbCmCurrentStatus::Query = \
"select * from caperf.cm_current_status";

AX_INT8 * axDbCmCurrentStatus::Insert = \
"insert into caperf.cm_current_status(cm_res_id,sum_online_tm,sum_offline_tm,"
"last_log_tm,last_online_chg_tm,online_state,state_changes) "
"values(%u,%u,%u,%u,%u,%d,%d)";

AX_INT8 * axDbCmCurrentStatus::Update = \
"update caperf.cm_current_status set sum_online_tm=%u,sum_offline_tm=%u,"
"last_log_tm=%u,last_online_chg_tm=%u,online_state=%d,state_changes=%d "
"where cm_res_id=%u";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCmCurrentStatus::axDbCmCurrentStatus()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCmCurrentStatus::~axDbCmCurrentStatus()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCmCurrentStatus::axDbCmCurrentStatus(DB_RESID_t resId) :
  axDbAbstractCurrentStatus(resId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCmCurrentStatus::axDbCmCurrentStatus(DB_RESID_t resId, 
                              time_t tv_sec, axIntCmNonkey_t * cmNk) :
  axDbAbstractCurrentStatus(resId, tv_sec)
{
  m_currentValue = axInternalCm::isCmOnline(cmNk->docsisState);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmCurrentStatus::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[64];

  if (!m_resId) {
    goto EXIT_LABEL;
  }

  sprintf(whereClause, "where cm_res_id=%d", m_resId);

  sprintf(qryStr, "%s %s", Query, whereClause);

  ret = getRow(qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmCurrentStatus::getRow(AX_INT8 * qryStr)
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
axDbCmCurrentStatus::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmCurrentStatus::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmCurrentStatus::insertRow(void)
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
axDbCmCurrentStatus::updateRow(void)
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
axDbCmCurrentStatus::deleteRow(void)
{
  bool ret = false;

  return (ret);
}


