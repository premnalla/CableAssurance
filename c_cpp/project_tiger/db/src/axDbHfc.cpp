
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axDbExternalizer.hpp"
#include "axDbConnectionFactory.hpp"
#include "axDbGenericQuery.hpp"
#include "axDbConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbResultSet.hpp"
#include "axDbHfc.hpp"
#include "axInternalCm.hpp"
#include "axDbUtils.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbWriteConnHelper.hpp"
#include "axDbXResource.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbHfc::BasicHfcQuery = \
"select h.* from canet.hfc_plant h ";

AX_INT8 * axDbHfc::CmtsHfcQuery = \
"select h.* from canet.hfc_plant h join canet.cmts c on "
"(h.cmts_res_id=c.cmts_res_id) where c.cmts_res_id=%u and h.is_deleted=0";

AX_INT8 * axDbHfc::CmtsHfcQueryStartup = \
"select h.*,n.*,st.*,tca.* from canet.hfc_plant h join canet.cmts c using (cmts_res_id) "
"left outer join caperf.hfc_current_counts n using (hfc_res_id) "
"left outer join caperf.hfc_current_alarm st using (hfc_res_id) "
"left outer join caperf.hfc_current_tca tca using (hfc_res_id) "
"where c.cmts_res_id=%u and h.is_deleted=0";

AX_INT8 * axDbHfc::BasicHfcUpdate = \
"update canet.hfc_plant set alert_level=%d, is_deleted=%d where id=%u ";

AX_INT8 * axDbHfc::BasicHfcInsert = \
"insert into canet.hfc_plant(hfc_res_id,cmts_res_id,name,alert_level) "
"values(%u,%u,'%s',%d)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// method:
//********************************************************************
void
axDbHfc::instantiationInit(void)
{
  DB_UPDATE_TIME_t t = {0};
  m_lastUpdated = t;

  m_hfcName[0] = '\0';
}


//********************************************************************
// default constructor:
//********************************************************************
axDbHfc::axDbHfc() :
  m_id(0), m_hfcResId(0), m_cmtsResId(0), m_alertLevel(0), m_deleted(0)
{
  instantiationInit();
}


//********************************************************************
// destructor:
//********************************************************************
axDbHfc::~axDbHfc()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbHfc::axDbHfc(DB_RESID_t hfcResId) :
  m_id(0), m_hfcResId(hfcResId), m_cmtsResId(0), m_alertLevel(0), m_deleted(0)
{
  instantiationInit();
}


//********************************************************************
// data constructor:
//********************************************************************
axDbHfc::axDbHfc(DB_RESID_t cmtsResId, DB_HFC_NAME_t hfcName) :
  m_id(0), m_hfcResId(0), m_cmtsResId(cmtsResId), m_alertLevel(0), m_deleted(0)
{
  instantiationInit();
  copyDbHfcName(m_hfcName, hfcName);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axDbHfc::getResourceType(void)
{
  return (DB_REST_HFCG);
}



//********************************************************************
// method:
//********************************************************************
bool
axDbHfc::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[128];

  axDbConnectionFactory * cf;

  if (m_id) {
    sprintf(whereClause, "where h.id=%u", m_id);
  } else if (m_hfcResId) {
    sprintf(whereClause, "where h.hfc_res_id=%u", m_hfcResId);
  } else if (m_cmtsResId && m_hfcName[0]!='\0') {
    sprintf(whereClause, "where h.cmts_res_id=%u and h.name='%s'",
            m_cmtsResId, m_hfcName);
  } else {
    goto EXIT_LABEL;
  }

  sprintf(qryStr, "%s %s", BasicHfcQuery, whereClause);

  cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    bool qrc = query->execute(qryStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    axDbResultSet * rs = dynamic_cast<axDbResultSet *> 
                                              (query->getResultSet());

    if (!rs->getNext(*this)) {
      goto EXIT_LABEL;
    }

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}



//********************************************************************
// method:
//********************************************************************
bool
axDbHfc::insertRow(void)
{
  bool ret = false;
  char sqlStr[512];

  bool    isUpdate = false;
  axDbHfc tmpHfc;

  tmpHfc.m_cmtsResId = m_cmtsResId;
  copyDbHfcName(tmpHfc.m_hfcName, m_hfcName);

  if (tmpHfc.getRow()) {
    isUpdate = true;
    m_deleted = 0;
    m_id = tmpHfc.m_id;
    m_hfcResId = tmpHfc.m_hfcResId;
  }

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  if (!isUpdate) {

    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbXResource res;
    res.m_resType = getResourceType();
    if (!res.insertRow(mc)) {
      goto EXIT_LABEL;
    }

    m_hfcResId = res.m_resId;

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, BasicHfcInsert, m_hfcResId, m_cmtsResId, m_hfcName, 
                                                         m_alertLevel);

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

  } else {

    ret = updateRow();
    goto EXIT_LABEL;

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbHfc::updateRow(void)
{
  bool ret = false;
  char sqlStr[512];

  sprintf(sqlStr, BasicHfcUpdate, m_alertLevel, m_deleted, m_id);

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


#if 0
//********************************************************************
// method:
//********************************************************************
bool
axDbHfc::insertOrUpdateRow(void)
{
  bool ret = false;


  return (ret);
}
#endif


//********************************************************************
// method:
//********************************************************************
bool
axDbHfc::deleteRow(void)
{
  bool ret = false;


  return (ret);
}



