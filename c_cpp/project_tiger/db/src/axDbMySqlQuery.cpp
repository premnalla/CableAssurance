
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axDbMySqlQuery.hpp"
#include "axDbMySqlResultSet.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbMySqlQuery::axDbMySqlQuery() :
  // axDbGenericQuery((axAbstractConnection *)NULL),
  m_storeResult(true)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbMySqlQuery::~axDbMySqlQuery()
{
  axDbMySqlResultSet * rs = (axDbMySqlResultSet *) getResultSet();
  if (rs) {
    delete rs;
    setResultSet(NULL);
  }
}


//********************************************************************
// data constructor:
//********************************************************************
axDbMySqlQuery::axDbMySqlQuery(axDbMySqlConnection * c) :
  axDbGenericQuery(c),
  m_storeResult(true)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::isStoreResult(void)
{
  return (m_storeResult);
}


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlQuery::setStoreResult(void)
{
  m_storeResult = true;
}


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlQuery::setUseResult(void)
{
  m_storeResult = false;
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::executeAndCreateResultSet(const char * qry)
{
  static const char * myName="mySqlQryExecAndCreateResSet:";

  bool ret = false;
  MYSQL_RES * resultSet;

  axDbMySqlResultSet * rs = NULL;

  axDbMySqlConnection * mc = 
    dynamic_cast<axDbMySqlConnection *> (getConnection());
  MYSQL * dbHdl = (MYSQL *) mc->getConnectionHandle();

  if ( mysql_real_query(dbHdl, qry, strlen(qry)) ) {
    ACE_DEBUG((LM_ERROR, "%s mysql_real_query() - failed\n", myName));
    ACE_DEBUG((LM_ERROR, "%s SQL:%s\n", myName, qry));
    goto EXIT_LABEL;
  }

  if (m_storeResult) {
    resultSet = mysql_store_result(dbHdl);
  } else {
    resultSet = mysql_use_result(dbHdl);
  }

  rs = new axDbMySqlResultSet(mc, resultSet);

  setResultSet(rs);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbXResource & res)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCmts & cmts)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbChannel & dbChannel)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbHfc & dbHfc)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCm & cm)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbEmta & emta)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCARootAlarm & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCACurrentAlarm & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCAHistoricalAlarm & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCmtsCurrentCounts & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCmtsCountsLog & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbHfcCurrentCounts & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbHfcCountsLog & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbChannelCurrentCounts & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbChannelCountsLog & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCurrentCmPerf & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCmPerfLog & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCmStatusLog & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbMtaPingStatusLog & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbMtaProvStatusLog & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbMtaAvailLog & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCASoakingAlarm & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCAAlarmHistory & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCAAlarmDeviceSoaking & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCAAlarmDevicePostSoak & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCAAlarmCoincidentalSoaking & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCAAlarmCoincidentalPostSoak & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCAAlarmSecondary & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbAppConfig & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbSnmpV2CAttrs & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbMtaCurrentAvailability & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbCmCurrentStatus & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbSummaryFlags & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbLastCompSummary & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbHfcCurrentStatus & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbHfcCurrentTca & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbHfcStatusLog & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbHfcTcaLog & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbAbstractTopoLookup & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbLocalSystem & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbAbstractTopoContainer & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlQuery::execute(const char * qry, axDbEmtaSecondary & out)
{
  bool ret;

  ret = executeAndCreateResultSet(qry);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
AX_INT64
axDbMySqlQuery::getInsertId(void)
{
  AX_INT64 ret;

  axDbMySqlConnection * mc =
    dynamic_cast<axDbMySqlConnection *> (getConnection());
  MYSQL * dbHdl = (MYSQL *) mc->getConnectionHandle();
  
  ret = mysql_insert_id(dbHdl);

  return (ret);
}


