
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axHfcCountsSummary.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axHfcCountsSummary::SQL_HFC_RES_ID = " \
  select hfc_res_id from catmpsumm.hfc_resources; \
";

AX_INT8 * axHfcCountsSummary::SQL_HFC_COUNTS_LOG = " \
  select tm_sec,cm_total,cm_online,mta_total,mta_available \
  from catmpsumm.hfc_counts_log where hfc_res_id=%u; \
";

AX_INT8 * axHfcCountsSummary::SQL_INSERT_SUMMARY = " \
insert into casumm.hfc_counts_summary(hfc_res_id,month,day,year, \
  min_cm_total,max_cm_total,avg_cm_total,min_cm_online,max_cm_online, \
  avg_cm_online,min_mta_total,max_mta_total,avg_mta_total, \
  min_mta_avail,max_mta_avail,avg_mta_avail) values ( \
  %u,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d); \
";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axHfcCountsSummary::axHfcCountsSummary() :
  axAbstractCountsSummary(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcCountsSummary::~axHfcCountsSummary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcCountsSummary::axHfcCountsSummary(struct tm * in) :
  axAbstractCountsSummary(in)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcCountsSummary::getResIdSql(void)
{
  return (SQL_HFC_RES_ID);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcCountsSummary::getCountsLog(void)
{
  return (SQL_HFC_COUNTS_LOG);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcCountsSummary::getInsertSql(void)
{
  return (SQL_INSERT_SUMMARY);
}


