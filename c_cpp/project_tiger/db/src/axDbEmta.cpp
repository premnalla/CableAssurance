
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
#include "axDbUtils.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbWriteConnHelper.hpp"
#include "axDbXResource.hpp"
#include "axDbEmta.hpp"
#include "axInternalEmta.hpp"
#include "axInternalUtils.hpp"
#include "axDbMtaAvailLog.hpp"
#include "axDbMtaPingStatusLog.hpp"
#include "axDbMtaProvStatusLog.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbEmta::BasicEmtaQuery = \
"select em.*, cm.*, uch.channel_index, uch.name, dch.channel_index, dch.name, hfc.name "
"from canet.emta em join canet.cable_modem cm on (em.cm_res_id=cm.cm_res_id) "
"left outer join canet.cmts c on (cm.cmts_res_id=c.cmts_res_id) "
"left outer join canet.channel uch on (cm.upstream_chnl_res_id=uch.channel_res_id) "
"left outer join canet.channel dch on (cm.downstream_chnl_res_id=dch.channel_res_id) "
"left outer join canet.hfc_plant hfc on (cm.hfc_res_id=hfc.hfc_res_id) "
"";

AX_INT8 * axDbEmta::CmtsEmtaQuery = \
"select em.*, cm.*, uch.channel_index, uch.name, dch.channel_index, dch.name, hfc.name "
"from canet.emta em join canet.cable_modem cm on (em.cm_res_id=cm.cm_res_id) "
"left outer join canet.cmts c on (cm.cmts_res_id=c.cmts_res_id) "
"left outer join canet.channel uch on (cm.upstream_chnl_res_id=uch.channel_res_id) "
"left outer join canet.channel dch on (cm.downstream_chnl_res_id=dch.channel_res_id) "
"left outer join canet.hfc_plant hfc on (cm.hfc_res_id=hfc.hfc_res_id) "
"where c.cmts_res_id=%u and em.is_deleted=0"
"";

AX_INT8 * axDbEmta::CmtsEmtaQueryStartup = \
"select em.*, cm.*, uch.channel_index, uch.name, dch.channel_index, dch.name, hfc.name, st.* "
"from canet.emta em join canet.cable_modem cm on (em.cm_res_id=cm.cm_res_id) "
"left outer join canet.cmts c on (cm.cmts_res_id=c.cmts_res_id) "
"left outer join canet.channel uch on (cm.upstream_chnl_res_id=uch.channel_res_id) "
"left outer join canet.channel dch on (cm.downstream_chnl_res_id=dch.channel_res_id) "
"left outer join canet.hfc_plant hfc on (cm.hfc_res_id=hfc.hfc_res_id) "
"left outer join caperf.mta_current_avail st on (em.emta_res_id=st.mta_res_id) "
"where c.cmts_res_id=%u and em.is_deleted=0"
"";

AX_INT8 * axDbEmta::UpstreamChannelEmtaQuery = \
"select em.*, cm.*, uch.channel_index, uch.name, dch.channel_index, dch.name, hfc.name "
"from canet.emta em join canet.cable_modem cm on (em.cm_res_id=cm.cm_res_id) "
"left outer join canet.channel uch on (uch.channel_res_id=cm.upstream_chnl_res_id) "
"left outer join canet.channel dch on (cm.downstream_chnl_res_id=dch.channel_res_id) "
"left outer join canet.hfc_plant hfc on (cm.hfc_res_id=hfc.hfc_res_id) "
"where uch.channel_res_id=%u and em.is_deleted=0"
"";

AX_INT8 * axDbEmta::DownstreamChannelEmtaQuery = \
"select em.*, cm.*, uch.channel_index, uch.name, dch.channel_index, dch.name, hfc.name "
"from canet.emta em join canet.cable_modem cm on (em.cm_res_id=cm.cm_res_id) "
"left outer join canet.channel dch on (dch.channel_res_id=cm.downstream_chnl_res_id) "
"left outer join canet.channel uch on (cm.upstream_chnl_res_id=uch.channel_res_id) "
"left outer join canet.hfc_plant hfc on (cm.hfc_res_id=hfc.hfc_res_id) "
"where dch.channel_res_id=%u and em.is_deleted=0"
"";

AX_INT8 * axDbEmta::HfcEmtaQuery = \
"select em.*, cm.*, uch.channel_index, uch.name, dch.channel_index, dch.name, hfc.name "
"from canet.emta em join canet.cable_modem cm on (em.cm_res_id=cm.cm_res_id) "
"left outer join canet.hfc_plant hfc on (hfc.hfc_res_id=cm.hfc_res_id) "
"left outer join canet.channel uch on (cm.upstream_chnl_res_id=uch.channel_res_id) "
"left outer join canet.channel dch on (cm.downstream_chnl_res_id=dch.channel_res_id) "
"where hfc.hfc_res_id=%u and em.is_deleted=0"
"";

AX_INT8 * axDbEmta::BasicEmtaUpdate = \
"update cannet.emta set cms_res_id=%u,fqdn='%s',"
"ip_address='%s',mac_address='%s',pktcbl_prov_state=%d,is_prov_pass=%d,"
"ping_state=%d,is_ping_failure=%d,available=%d,is_deleted=%d,ip_address_type=%d "
"alert_level=%d where id=%u";

AX_INT8 * axDbEmta::BasicEmtaInsert = \
"insert into canet.emta(emta_res_id,cms_res_id,cm_res_id,"
"fqdn,ip_address,mac_address,pktcbl_prov_state,is_prov_pass,ping_state,"
"is_ping_failure,available,ip_address_type,alert_level) values(%u,%u,%u,%u,"
"'%s','%s','%s',%d,%d,%d,%d,%d,%d,%d)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// method:
//********************************************************************
void
axDbEmta::initAtInstantiation(void)
{
}


//********************************************************************
// default constructor:
//********************************************************************
axDbEmta::axDbEmta() :
  m_cm()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbEmta::~axDbEmta()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbEmta::axDbEmta(DB_RESID_t emtaResId) :
  axDbGenMta(emtaResId),
  m_cm()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbEmta::axDbEmta(DB_MAC_t cmMac, DB_MAC_t mtaMac, 
                                               DB_RESID_t cmtsResId) :
  m_cm(cmMac, cmtsResId)
{
  copyDbMac(m_mtaMac, mtaMac);
}


//********************************************************************
// data constructor:
//********************************************************************
axDbEmta::axDbEmta(DB_MAC_t mtaMac, DB_RESID_t cmtsResId)
{
  copyDbMac(m_mtaMac, mtaMac);
  m_cm.m_cmtsResId = cmtsResId;
}


#if 0
//********************************************************************
// data constructor:
//********************************************************************
axDbEmta::axDbEmta(axInternalEmta * intMta, DB_RESID_t cmtsResId) :
  axDbGenMta(intMta),
  m_cm()
{
  m_cm.m_cmtsResId = cmtsResId;
}
#endif


//********************************************************************
// data constructor:
//********************************************************************
axDbEmta::axDbEmta(const axDbEmta & in) :
  axDbGenMta(in),
  m_cm(in.m_cm)
{
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axDbEmta::getResourceType(void)
{
  return (DB_REST_EMTA);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbEmta::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[128];

  if (m_id) {

    sprintf(whereClause, "where em.id=%u", m_id);

  } else if (m_mtaResId) {

    sprintf(whereClause, "where em.emta_res_id=%u", m_mtaResId);

  } else if (m_mtaMac[0] !='\0') {

    sprintf(whereClause, "where em.mac_address='%s'", m_mtaMac);

  } else if (m_cm.m_cmMac[0] !='\0') {

    sprintf(whereClause, "where cm.mac_address='%s'", m_cm.m_cmMac);

  } else {
    goto EXIT_LABEL;
  }

  sprintf(qryStr, "%s %s", BasicEmtaQuery, whereClause);

  ret = getRow(qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbEmta::getRow(AX_INT8 * qryStr)
{
  bool ret = false;

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    bool qrc = query->execute(qryStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    axDbResultSet * rs = dynamic_cast<axDbResultSet *> (query->getResultSet());

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
axDbEmta::getRows(axListBase & mtaList)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[128];

  if (m_mtaMac[0]!='\0') {
    sprintf(whereClause, "where em.mac_address='%s'", m_mtaMac);
  } else if (m_cm.m_cmMac[0]!='\0') {
    sprintf(whereClause, "where cm.mac_address='%s'", m_cm.m_cmMac);
  } else {
    goto EXIT_LABEL;
  }

  sprintf(qryStr, "%s %s", BasicEmtaQuery, whereClause);

  ret = getRows(mtaList, qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbEmta::getRows(axListBase & mtaList, AX_INT8 * qryStr)
{
  bool ret = false;

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    bool qrc = query->execute(qryStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    axDbResultSet * rs = dynamic_cast<axDbResultSet *> (query->getResultSet());

    while (rs->getNext(*this)) {
      axDbEmta * newEmta = new axDbEmta(*this);
      if (newEmta) {
        mtaList.add(newEmta);
      }
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
axDbEmta::insertRow(void)
{
  bool ret = false;
  char sqlStr[512];

  bool         isUpdate = false;
  axDbEmta     tmpEmta;

  copyDbMac(tmpEmta.m_mtaMac,m_mtaMac);

  if (tmpEmta.getRow()) {

    /*
     * Update Scenario
     */

    isUpdate = true;

    /*
     * TODO: not sure how to handle this case
     */

    m_id = tmpEmta.m_id;
    m_mtaResId = tmpEmta.m_mtaResId;
    m_deleted = 0;
  }

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  if (!isUpdate) {

    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    /*
     * CM will be detected at the time CMTS is polled. Cannot add here.
     * What we need to do is associate the existing CM with the eMTA by
     * updating the CM entry (end-user-dev-type==EMTA)
     * 
     * NOTE: no need to do the above as it is done in device classification
     *       code
     */

    /*
     * Now ready to insert eMTA
     */

    axDbXResource res;
    res.m_resType = getResourceType();
    if (!res.insertRow(mc)) {
      goto EXIT_LABEL;
    }

    m_mtaResId = res.m_resId;

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, BasicEmtaInsert, m_mtaResId,
            m_cmsResId,m_fqdn,m_ipAddress,
            m_mtaMac,m_provState,m_isProvStatePass,m_pingState,
            m_isPingFailure,m_available,m_ipAddressType,m_alertLevel);

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    {
      axDbMtaAvailLog mtaAvailLog(m_mtaResId);
      mtaAvailLog.m_availability = m_available;
      mtaAvailLog.insertRow();

      axDbMtaPingStatusLog mtaPingLog(m_mtaResId);
      mtaPingLog.m_pingState = m_pingState;
      mtaPingLog.insertRow();

      axDbMtaProvStatusLog mtaProvLog(m_mtaResId);
      mtaProvLog.m_provState = m_provState;
      mtaProvLog.insertRow();
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
axDbEmta::updateRow(void)
{
  bool ret = false;
  char sqlStr[512];

  /*
   * First update the CM part of the eMTA
   */
  if (!m_cm.updateRow()) {
    /* LOG_MSG */
  }

  sprintf(sqlStr, BasicEmtaUpdate,
          m_cmsResId,m_fqdn,m_ipAddress,
          m_mtaMac,m_provState,m_isProvStatePass,m_pingState,
          m_isPingFailure,m_available,m_deleted,m_ipAddressType,
          m_alertLevel, m_id);

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


//********************************************************************
// method:
//********************************************************************
bool
axDbEmta::deleteRow(void)
{
  bool ret = false;


  return (ret);
}


