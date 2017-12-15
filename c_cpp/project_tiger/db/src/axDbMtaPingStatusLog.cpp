
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbMtaPingStatusLog.hpp"
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
AX_INT8 * axDbMtaPingStatusLog::Query = \
"select * from caperf.mta_ping_status_log";

AX_INT8 * axDbMtaPingStatusLog::Insert = \
"insert into caperf.mta_ping_status_log(mta_res_id,tm_sec,ping_state) "
"values(%u,%u,%d)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbMtaPingStatusLog::axDbMtaPingStatusLog() :
  m_pingState(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbMtaPingStatusLog::~axDbMtaPingStatusLog()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbMtaPingStatusLog::axDbMtaPingStatusLog(DB_RESID_t resId) :
  axDbAbstractStatus(resId),
  m_pingState(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbMtaPingStatusLog::axDbMtaPingStatusLog(DB_RESID_t resId, 
                         time_t tv_sec, axIntGenMtaNonkey_t * mtaNk) :
  axDbAbstractStatus(resId, tv_sec),
  m_pingState(mtaNk->pingState)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaPingStatusLog::getRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaPingStatusLog::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaPingStatusLog::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaPingStatusLog::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaPingStatusLog::insertRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_resId, m_lastLogTime, m_pingState);

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
axDbMtaPingStatusLog::updateRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaPingStatusLog::deleteRow(void)
{
  bool ret = false;

  return (ret);
}


