
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbHfcCounts.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbHfcCounts::UpdateCurrentCounts = \
"update caperf.hfc_current_counts set cm_total=%d, cm_online=%d, "
"mta_total=%d, mta_available=%d where cmts_res_id=%u";

AX_INT8 * axDbHfcCounts::InsertCurrentCounts = \
"insert into caperf.hfc_current_counts(cmts_res_id,cm_total,"
"cm_online,mta_total,mta_available) values(%u,%d,%d,%d,%d)";

AX_INT8 * axDbHfcCounts::InsertCountsLog = \
"insert into caperf.hfc_counts_log(cmts_res_id,cm_total,cm_online,"
"mta_total,mta_available) values(%u,%d,%d,%d,%d)";


//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbHfcCounts::axDbHfcCounts()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbHfcCounts::~axDbHfcCounts()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbHfcCounts::axDbHfcCounts(DB_RESID_t resId,
                                             axIntCounts_t * counts) :
  axDbAbstractCounts(resId, counts)
{
}


//********************************************************************
// method:
//********************************************************************
void
axDbHfcCounts::getInsertCurrentSql(AX_INT8 * ret)
{
  sprintf(ret, InsertCurrentCounts, 
          m_resId, m_totalCm, m_onlineCm, m_totalMta, m_availableMta);
}


//********************************************************************
// method:
//********************************************************************
void
axDbHfcCounts::getUpdateCurrentSql(AX_INT8 * ret)
{
  sprintf(ret, UpdateCurrentCounts, 
          m_totalCm, m_onlineCm, m_totalMta, m_availableMta, m_resId);
}


//********************************************************************
// method:
//********************************************************************
void
axDbHfcCounts::getInsertLogSql(AX_INT8 * ret)
{
  sprintf(ret, InsertCountsLog, 
          m_resId, m_totalCm, m_onlineCm, m_totalMta, m_availableMta);
}


