
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbMtaProvStatusLog.hpp"
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
AX_INT8 * axDbMtaProvStatusLog::Query = \
"select * from caperf.mta_prov_status_log";

AX_INT8 * axDbMtaProvStatusLog::Insert = \
"insert into caperf.mta_prov_status_log(mta_res_id,tm_sec,prov_state) "
"values(%u,%u,%d)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbMtaProvStatusLog::axDbMtaProvStatusLog() :
  m_provState(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbMtaProvStatusLog::~axDbMtaProvStatusLog()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbMtaProvStatusLog::axDbMtaProvStatusLog(DB_RESID_t resId) :
  axDbAbstractStatus(resId),
  m_provState(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbMtaProvStatusLog::axDbMtaProvStatusLog(DB_RESID_t resId, 
                         time_t tv_sec, axIntGenMtaNonkey_t * mtaNk) :
  axDbAbstractStatus(resId, tv_sec),
  m_provState(mtaNk->provState)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaProvStatusLog::getRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaProvStatusLog::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaProvStatusLog::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaProvStatusLog::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaProvStatusLog::insertRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_resId, m_lastLogTime, m_provState);

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
axDbMtaProvStatusLog::updateRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaProvStatusLog::deleteRow(void)
{
  bool ret = false;

  return (ret);
}


