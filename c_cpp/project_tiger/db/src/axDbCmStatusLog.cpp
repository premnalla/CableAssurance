
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbCmStatusLog.hpp"
#include "axDbExternalizer.hpp"
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
AX_INT8 * axDbCmStatusLog::Query = \
"select * from caperf.cm_status_log";

AX_INT8 * axDbCmStatusLog::Insert = \
"insert into caperf.cm_status_log(cm_res_id,tm_sec,docsis_state) "
"values(%u,%u,%d)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCmStatusLog::axDbCmStatusLog() :
  m_docsisState(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCmStatusLog::~axDbCmStatusLog()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCmStatusLog::axDbCmStatusLog(DB_RESID_t resId) :
  axDbAbstractStatus(resId),
  m_docsisState(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCmStatusLog::axDbCmStatusLog(DB_RESID_t resId, time_t tv_sec,
                                             axIntCmNonkey_t * cmNk) :
  axDbAbstractStatus(resId, tv_sec),
  m_docsisState(cmNk->docsisState)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmStatusLog::getRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmStatusLog::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmStatusLog::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmStatusLog::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmStatusLog::insertRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_resId, m_lastLogTime, m_docsisState);

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
axDbCmStatusLog::updateRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmStatusLog::deleteRow(void)
{
  bool ret = false;

  return (ret);
}



