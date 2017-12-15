
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axChannelCountsSummary.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axChannelCountsSummary::SQL_CHANNEL_RES_ID = " \
  select channel_res_id from catmpsumm.channel_resources; \
";

AX_INT8 * axChannelCountsSummary::SQL_CHANNEL_COUNTS_LOG = " \
  select tm_sec,cm_total,cm_online,mta_total,mta_available \
  from catmpsumm.channel_counts_log where channel_res_id=%u; \
";

AX_INT8 * axChannelCountsSummary::SQL_INSERT_SUMMARY = " \
insert into casumm.channel_counts_summary(channel_res_id,month,day,year, \
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
axChannelCountsSummary::axChannelCountsSummary() :
  axAbstractCountsSummary(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axChannelCountsSummary::~axChannelCountsSummary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axChannelCountsSummary::axChannelCountsSummary(struct tm * in) :
  axAbstractCountsSummary(in)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axChannelCountsSummary::getResIdSql(void)
{
  return (SQL_CHANNEL_RES_ID);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axChannelCountsSummary::getCountsLog(void)
{
  return (SQL_CHANNEL_COUNTS_LOG);
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axChannelCountsSummary::getInsertSql(void)
{
  return (SQL_INSERT_SUMMARY);
}

