//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbHfcCountsLog.hpp"
#include "axDbConnectionFactory.hpp"
#include "axDbExternalizer.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbGenericQuery.hpp"
#include "axDbResultSet.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbHfcCountsLog::QueryCountsLog = \
"select * from caperf.hfc_counts_log";

AX_INT8 * axDbHfcCountsLog::InsertCountsLog = \
"insert into caperf.hfc_counts_log(hfc_res_id,tm_sec,cm_total,cm_online,"
"mta_total,mta_available) values(%u,%u,%d,%d,%d,%d)";


//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbHfcCountsLog::axDbHfcCountsLog()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbHfcCountsLog::~axDbHfcCountsLog()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbHfcCountsLog::axDbHfcCountsLog(DB_RESID_t resId) :
  axDbAbstractCounts(resId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbHfcCountsLog::axDbHfcCountsLog(DB_RESID_t resId, time_t tv_sec,
                                             axIntCounts_t * counts) :
  axDbAbstractCounts(resId, tv_sec, counts)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcCountsLog::getRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcCountsLog::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcCountsLog::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcCountsLog::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcCountsLog::insertRow(void)
{
  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, InsertCountsLog, 
            m_resId, m_timeSec, m_totalCm, m_onlineCm, m_totalMta, 
                                                     m_availableMta);
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
axDbHfcCountsLog::updateRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfcCountsLog::deleteRow(void)
{
  bool ret = false;

  return (ret);
}



