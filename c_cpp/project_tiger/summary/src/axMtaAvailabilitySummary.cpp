
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axMtaAvailabilitySummary.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axMtaAvailabilitySummary::SQL_RES_ID = " \
select mta_res_id from catmpsumm.mta_resources; \
";

AX_INT8 * axMtaAvailabilitySummary::SQL_CURRENT_STATUS = " \
select sum_avail_tm,sum_unavail_tm,last_log_tm,last_avail_chg_tm,state_changes,\
available from catmpsumm.mta_current_avail where mta_res_id=%u; \
";

AX_INT8 * axMtaAvailabilitySummary::SQL_INSERT_SUMMARY = " \
insert into casumm.mta_status_summary(mta_res_id,month,day,year, \
sum_avail_tm,sum_unavail_tm,state_changes) values (%u,%d,%d,%d,%u,%u,%d) \
";


//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axMtaAvailabilitySummary::axMtaAvailabilitySummary()
{
}


//********************************************************************
// destructor:
//********************************************************************
axMtaAvailabilitySummary::~axMtaAvailabilitySummary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axMtaAvailabilitySummary::axMtaAvailabilitySummary(struct tm * bdTime,
                                                     time_t timeSec) :
  axAbstractStatusSummary(bdTime, timeSec)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axMtaAvailabilitySummary::getResIdSql(void)
{
  return SQL_RES_ID;
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axMtaAvailabilitySummary::getCurrentStatusSql(void)
{
  return SQL_CURRENT_STATUS;
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axMtaAvailabilitySummary::getInsertSql(void)
{
  return SQL_INSERT_SUMMARY;
}


//********************************************************************
// method:
//********************************************************************
bool
axMtaAvailabilitySummary::isDeviceStateGood(AX_UINT8 st)
{
  return (st==0?false:true);
}


