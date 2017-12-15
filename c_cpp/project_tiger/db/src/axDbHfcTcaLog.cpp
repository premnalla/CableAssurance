
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbHfcTcaLog.hpp"
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
AX_INT8 * axDbHfcTcaLog::Query = \
"select * from caperf.hfc_tca_log";

AX_INT8 * axDbHfcTcaLog::Insert = \
"insert into caperf.hfc_tca_log(hfc_res_id,tm_sec,hfc_tca) "
"values(%u,%u,%d)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbHfcTcaLog::axDbHfcTcaLog() :
  m_tca(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbHfcTcaLog::~axDbHfcTcaLog()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbHfcTcaLog::axDbHfcTcaLog(DB_RESID_t resId) :
  axDbAbstractStatus(resId),
  m_tca(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbHfcTcaLog::axDbHfcTcaLog(DB_RESID_t resId, time_t tv_sec,
                                            axIntHfcNonkey_t * hfcNk) :
  axDbAbstractStatus(resId, tv_sec),
  m_tca(hfcNk->tca)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcTcaLog::getRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcTcaLog::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcTcaLog::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcTcaLog::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcTcaLog::insertRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_resId, m_lastLogTime, m_tca);

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
axDbHfcTcaLog::updateRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcTcaLog::deleteRow(void)
{
  bool ret = false;

  return (ret);
}



