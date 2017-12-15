//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axDbCmtsCountsLog.hpp"
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
AX_INT8 * axDbCmtsCountsLog::QueryCountsLog = \
"select * from caperf.cmts_counts_log";

AX_INT8 * axDbCmtsCountsLog::InsertCountsLog = \
"insert into caperf.cmts_counts_log(cmts_res_id,tm_sec,cm_total,cm_online,"
"mta_total,mta_available) values(%u,%d,%d,%d,%d,%d)";


//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCmtsCountsLog::axDbCmtsCountsLog()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCmtsCountsLog::~axDbCmtsCountsLog()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCmtsCountsLog::axDbCmtsCountsLog(DB_RESID_t resId) :
  axDbAbstractCounts(resId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCmtsCountsLog::axDbCmtsCountsLog(DB_RESID_t resId, time_t tv_sec,
                                             axIntCounts_t * counts) :
  axDbAbstractCounts(resId, tv_sec, counts)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmtsCountsLog::getRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmtsCountsLog::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmtsCountsLog::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmtsCountsLog::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmtsCountsLog::insertRow(void)
{
  static const char * myName="cmtsCntsLogInsert:";

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

    // ACE_DEBUG((LM_DB_DEBUG, "%s %s\n", myName, sqlStr));

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
axDbCmtsCountsLog::updateRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmtsCountsLog::deleteRow(void)
{
  bool ret = false;

  return (ret);
}



