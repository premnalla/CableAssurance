
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcStatusSummary.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axHfcStatusSummary::SQL_RES_ID = " \
select hfc_res_id from catmpsumm.hfc_resources; \
";

AX_INT8 * axHfcStatusSummary::SQL_CURRENT_STATUS = " \
select sum_alarmfree_tm,sum_alarm_tm,last_log_tm,alarm_chg_tm,\
state_changes,hfc_alarm from catmpsumm.hfc_current_alarm \
where hfc_res_id=%u; \
";

AX_INT8 * axHfcStatusSummary::SQL_INSERT_SUMMARY = " \
insert into casumm.hfc_alarm_summary(hfc_res_id,month,day,year, \
sum_alarmfree_tm,sum_alarm_tm,state_changes) values (%u,%d,%d,%d,%u,%u,%d) \
";


//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axHfcStatusSummary::axHfcStatusSummary()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcStatusSummary::~axHfcStatusSummary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcStatusSummary::axHfcStatusSummary(struct tm * bdTime,
                                                     time_t timeSec) :
  axAbstractStatusSummary(bdTime, timeSec)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcStatusSummary::getResIdSql(void)
{
  return SQL_RES_ID;
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcStatusSummary::getCurrentStatusSql(void)
{
  return SQL_CURRENT_STATUS;
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcStatusSummary::getInsertSql(void)
{
  return SQL_INSERT_SUMMARY;
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcStatusSummary::isDeviceStateGood(AX_UINT8 st)
{
  return (st==1?false:true);
}


