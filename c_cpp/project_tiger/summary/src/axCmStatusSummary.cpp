
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCmStatusSummary.hpp"
#include "axInternalCm.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axCmStatusSummary::SQL_RES_ID = " \
select cm_res_id from catmpsumm.cm_resources; \
";

AX_INT8 * axCmStatusSummary::SQL_CURRENT_STATUS = " \
select sum_online_tm,sum_offline_tm,last_log_tm,last_online_chg_tm,\
state_changes,online_state from catmpsumm.cm_current_status \
where cm_res_id=%u; \
";

AX_INT8 * axCmStatusSummary::SQL_INSERT_SUMMARY = " \
insert into casumm.cm_status_summary(cm_res_id,month,day,year, \
sum_online_tm,sum_offline_tm,state_changes) values (%u,%d,%d,%d,%u,%u,%d) \
";


//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axCmStatusSummary::axCmStatusSummary()
{
}


//********************************************************************
// destructor:
//********************************************************************
axCmStatusSummary::~axCmStatusSummary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axCmStatusSummary::axCmStatusSummary(struct tm * bdTime,
                                                     time_t timeSec) :
  axAbstractStatusSummary(bdTime, timeSec)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axCmStatusSummary::getResIdSql(void)
{
  return SQL_RES_ID;
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axCmStatusSummary::getCurrentStatusSql(void)
{
  return SQL_CURRENT_STATUS;
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axCmStatusSummary::getInsertSql(void)
{
  return SQL_INSERT_SUMMARY;
}


//********************************************************************
// method:
//********************************************************************
bool
axCmStatusSummary::isDeviceStateGood(AX_UINT8 st)
{
  // return (axInternalCm::isCmOnline(st));
  return (st==0?false:true);
}


