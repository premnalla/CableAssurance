
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbHfcOutageLog.hpp"
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
AX_INT8 * axDbHfcOutageLog::Query = \
"select * from caperf.hfc_outage_log";

AX_INT8 * axDbHfcOutageLog::Insert = \
"insert into caperf.hfc_outage_log(hfc_res_id,tm_sec,hfc_outage) "
"values(%u,%u,%d)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbHfcOutageLog::axDbHfcOutageLog() :
  m_outage(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbHfcOutageLog::~axDbHfcOutageLog()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbHfcOutageLog::axDbHfcOutageLog(DB_RESID_t resId) :
  axDbAbstractStatus(resId),
  m_outage(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbHfcOutageLog::axDbHfcOutageLog(DB_RESID_t resId, 
                                            axIntHfcNonkey_t * hfcNk) :
  axDbAbstractStatus(resId),
  m_outage(hfcNk->percentOutage)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcOutageLog::getRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcOutageLog::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcOutageLog::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcOutageLog::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcOutageLog::insertRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_resId, m_lastLogTime, m_outage);

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
axDbHfcOutageLog::updateRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcOutageLog::deleteRow(void)
{
  bool ret = false;

  return (ret);
}



