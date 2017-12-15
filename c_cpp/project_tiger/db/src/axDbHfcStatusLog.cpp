
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbHfcStatusLog.hpp"
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
AX_INT8 * axDbHfcStatusLog::Query = \
"select * from caperf.hfc_alarm_log";

AX_INT8 * axDbHfcStatusLog::Insert = \
"insert into caperf.hfc_alarm_log(hfc_res_id,tm_sec,hfc_alarm) "
"values(%u,%u,%d)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbHfcStatusLog::axDbHfcStatusLog() :
  m_status(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbHfcStatusLog::~axDbHfcStatusLog()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbHfcStatusLog::axDbHfcStatusLog(DB_RESID_t resId) :
  axDbAbstractStatus(resId),
  m_status(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbHfcStatusLog::axDbHfcStatusLog(DB_RESID_t resId, time_t tv_sec,
                                            axIntHfcNonkey_t * hfcNk) :
  axDbAbstractStatus(resId, tv_sec),
  m_status(hfcNk->percentAlarm)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcStatusLog::getRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcStatusLog::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcStatusLog::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcStatusLog::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcStatusLog::insertRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_resId, m_lastLogTime, m_status);

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
axDbHfcStatusLog::updateRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcStatusLog::deleteRow(void)
{
  bool ret = false;

  return (ret);
}



