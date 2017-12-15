
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axHfcTcaSummary.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axHfcTcaSummary::SQL_RES_ID = " \
select hfc_res_id from catmpsumm.hfc_resources; \
";

AX_INT8 * axHfcTcaSummary::SQL_CURRENT_STATUS = " \
select sum_tcafree_tm,sum_tca_tm,last_log_tm,tca_change_tm,\
state_changes,hfc_tca from catmpsumm.hfc_current_tca \
where hfc_res_id=%u; \
";

AX_INT8 * axHfcTcaSummary::SQL_INSERT_SUMMARY = " \
insert into casumm.hfc_tca_summary(hfc_res_id,month,day,year, \
sum_tcafree_tm,sum_tca_tm,state_changes) values (%u,%d,%d,%d,%u,%u,%d) \
";


//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axHfcTcaSummary::axHfcTcaSummary()
{
}


//********************************************************************
// destructor:
//********************************************************************
axHfcTcaSummary::~axHfcTcaSummary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axHfcTcaSummary::axHfcTcaSummary(struct tm * bdTime,
                                                     time_t timeSec) :
  axAbstractStatusSummary(bdTime, timeSec)
{
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcTcaSummary::getResIdSql(void)
{
  return SQL_RES_ID;
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcTcaSummary::getCurrentStatusSql(void)
{
  return SQL_CURRENT_STATUS;
}


//********************************************************************
// method:
//********************************************************************
AX_INT8 *
axHfcTcaSummary::getInsertSql(void)
{
  return SQL_INSERT_SUMMARY;
}


//********************************************************************
// method:
//********************************************************************
bool
axHfcTcaSummary::isDeviceStateGood(AX_UINT8 st)
{
  return (st==1?false:true);
}


