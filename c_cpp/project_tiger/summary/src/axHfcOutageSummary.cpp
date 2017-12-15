
//********************************************************************
// Obsolete
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcOutageSummary.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axHfcOutageSummary::SQL_RES_ID = " \
select hfc_res_id from catmpsumm.hfc_resources; \
";

AX_INT8 * axHfcOutageSummary::SQL_CURRENT_STATUS = " \
select sum_nonoutage_tm,sum_outage_tm,last_log_tm,outage_chg_tm,\
state_changes,hfc_outage from catmpsumm.hfc_current_outage \
where hfc_res_id=%u; \
";

AX_INT8 * axHfcOutageSummary::SQL_INSERT_SUMMARY = " \
insert into casumm.hfc_outage_summary(hfc_res_id,month,day,year, \
non_outage_tm,outage_tm,state_changes) values (%u,%d,%d,%d,%u,%u,%d) \
";


//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axHfcOutageSummary::axHfcOutageSummary()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcOutageSummary::~axHfcOutageSummary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcOutageSummary::axHfcOutageSummary(struct tm * bdTime,
                                                     time_t timeSec) :
  axAbstractStatusSummary(bdTime, timeSec)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcOutageSummary::getResIdSql(void)
{
  return SQL_RES_ID;
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcOutageSummary::getCurrentStatusSql(void)
{
  return SQL_CURRENT_STATUS;
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcOutageSummary::getInsertSql(void)
{
  return SQL_INSERT_SUMMARY;
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcOutageSummary::isDeviceStateGood(AX_UINT8 st)
{
  return (st==0?false:true);
}


