
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbMtaAvailLog.hpp"
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
AX_INT8 * axDbMtaAvailLog::Query = \
"select * from caperf.mta_availability_log";

AX_INT8 * axDbMtaAvailLog::Insert = \
"insert into caperf.mta_availability_log(mta_res_id,tm_sec,availability) "
"values(%u,%u,%d)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbMtaAvailLog::axDbMtaAvailLog() :
  m_availability(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbMtaAvailLog::~axDbMtaAvailLog()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbMtaAvailLog::axDbMtaAvailLog(DB_RESID_t resId) :
  axDbAbstractStatus(resId),
  m_availability(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbMtaAvailLog::axDbMtaAvailLog(DB_RESID_t resId, time_t tv_sec,
                                        axIntGenMtaNonkey_t * mtaNk) :
  axDbAbstractStatus(resId, tv_sec),
  m_availability(mtaNk->available)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaAvailLog::getRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaAvailLog::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaAvailLog::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaAvailLog::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaAvailLog::insertRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_resId, m_lastLogTime, m_availability);

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
axDbMtaAvailLog::updateRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMtaAvailLog::deleteRow(void)
{
  bool ret = false;

  return (ret);
}


