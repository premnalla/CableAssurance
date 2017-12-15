
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbCmtsCounts.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbCmtsCounts::UpdateCurrentCounts = \
"update caperf.cmts_current_counts set cm_total=%d, cm_online=%d, "
"mta_total=%d, mta_available=%d where cmts_res_id=%u";

AX_INT8 * axDbCmtsCounts::InsertCurrentCounts = \
"insert into caperf.cmts_current_counts(cmts_res_id,cm_total,"
"cm_online,mta_total,mta_available) values(%u,%d,%d,%d,%d)";

AX_INT8 * axDbCmtsCounts::InsertCountsLog = \
"insert into caperf.cmts_counts_log(cmts_res_id,cm_total,cm_online,"
"mta_total,mta_available) values(%u,%d,%d,%d,%d)";


//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCmtsCounts::axDbCmtsCounts()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCmtsCounts::~axDbCmtsCounts()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCmtsCounts::axDbCmtsCounts(DB_RESID_t resId,
                                             axIntCounts_t * counts) : 
  axDbAbstractCounts(resId, counts)
{
}


//********************************************************************
// method:
//********************************************************************
void
axDbCmtsCounts::getInsertCurrentSql(AX_INT8 * ret)
{
  sprintf(ret, InsertCurrentCounts, 
          m_resId, m_totalCm, m_onlineCm, m_totalMta, m_availableMta);
}


//********************************************************************
// method:
//********************************************************************
void
axDbCmtsCounts::getUpdateCurrentSql(AX_INT8 * ret)
{
  sprintf(ret, UpdateCurrentCounts, 
          m_totalCm, m_onlineCm, m_totalMta, m_availableMta, m_resId);
}


//********************************************************************
// method:
//********************************************************************
void
axDbCmtsCounts::getInsertLogSql(AX_INT8 * ret)
{
  sprintf(ret, InsertCountsLog, 
          m_resId, m_totalCm, m_onlineCm, m_totalMta, m_availableMta);
}


