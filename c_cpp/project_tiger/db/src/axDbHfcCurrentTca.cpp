
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbHfcCurrentTca.hpp"
#include "axDbExternalizer.hpp"
#include "axDbResultSet.hpp"
#include "axDbConnectionFactory.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbGenericQuery.hpp"
#include "axInternalHfc.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbHfcCurrentTca::Query = \
"select * from caperf.cm_current_status";

AX_INT8 * axDbHfcCurrentTca::Insert = \
"insert into caperf.hfc_current_tca(hfc_res_id,sum_tca_tm,"
"sum_tcafree_tm,last_log_tm,tca_change_tm,hfc_tca,state_changes) "
"values(%u,%u,%u,%u,%u,%d,%d)";

AX_INT8 * axDbHfcCurrentTca::Update = \
"update caperf.hfc_current_tca set sum_tca_tm=%u,sum_tcafree_tm=%u,"
"last_log_tm=%u,tca_change_tm=%u,hfc_tca=%d,state_changes=%d "
"where hfc_res_id=%u";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbHfcCurrentTca::axDbHfcCurrentTca()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbHfcCurrentTca::~axDbHfcCurrentTca()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbHfcCurrentTca::axDbHfcCurrentTca(DB_RESID_t resId) :
  axDbAbstractCurrentStatus(resId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbHfcCurrentTca::axDbHfcCurrentTca(DB_RESID_t resId, time_t tv_sec,
                                             axIntHfcNonkey_t * hfcNk) :
  axDbAbstractCurrentStatus(resId, tv_sec)
{
  m_currentValue = hfcNk->tca;
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcCurrentTca::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[64];

  if (!m_resId) {
    goto EXIT_LABEL;
  }

  sprintf(whereClause, "where hfc_res_id=%u", m_resId);

  sprintf(qryStr, "%s %s", Query, whereClause);

  ret = getRow(qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcCurrentTca::getRow(AX_INT8 * qryStr)
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
axDbHfcCurrentTca::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcCurrentTca::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcCurrentTca::insertRow(void)
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
axDbHfcCurrentTca::updateRow(void)
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
axDbHfcCurrentTca::deleteRow(void)
{
  bool ret = false;

  return (ret);
}


