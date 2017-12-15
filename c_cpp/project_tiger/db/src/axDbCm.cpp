
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
#include "axDbCm.hpp"
#include "axInternalCm.hpp"
#include "axDbUtils.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbWriteConnHelper.hpp"
#include "axDbXResource.hpp"
// #include "axDbCmts.hpp"
#include "axDbChannel.hpp"
// #include "axDbHfc.hpp"
#include "axDbCmStatusLog.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbCm::BasicCmQuery = \
"select cm.* uch.channel_index, uch.name, dch.channel_index, dch.name, hfc.name "
"from canet.cmts c join canet.cable_modem cm on (c.cmts_res_id=cm.cmts_res_id) "
"left outer join canet.channel uch on (cm.upstream_chnl_res_id=uch.channel_res_id) "
"left outer join canet.channel dch on (cm.downstream_chnl_res_id=dch.channel_res_id) "
"left outer join canet.hfc_plant hfc on (cm.hfc_res_id=hfc.hfc_res_id) "
"";

/***** All CM's that belong to a CMTS ****/
AX_INT8 * axDbCm::CmtsCmQuery = \
"select cm.*, uch.channel_index, uch.name, dch.channel_index, dch.name, hfc.name "
"from canet.cmts c join canet.cable_modem cm on (c.cmts_res_id=cm.cmts_res_id) "
"left outer join canet.channel uch on (cm.upstream_chnl_res_id=uch.channel_res_id) "
"left outer join canet.channel dch on (cm.downstream_chnl_res_id=dch.channel_res_id) "
"left outer join canet.hfc_plant hfc on (cm.hfc_res_id=hfc.hfc_res_id) "
"where c.cmts_res_id=%u and cm.is_deleted=0";

/***** All CM's that belong to a CMTS @ Startup ****/
AX_INT8 * axDbCm::CmtsCmQueryStartup = \
"select cm.*, uch.channel_index, uch.name, dch.channel_index, "
"dch.name, hfc.name, st.*, perf.* "
"from canet.cmts c join canet.cable_modem cm using (cmts_res_id) "
"left outer join canet.channel uch on (cm.upstream_chnl_res_id=uch.channel_res_id) "
"left outer join canet.channel dch on (cm.downstream_chnl_res_id=dch.channel_res_id) "
"left outer join canet.hfc_plant hfc using (hfc_res_id) "
"left outer join caperf.cm_current_status st on (cm.cm_res_id=st.cm_res_id) "
"left outer join caperf.cm_current_perf perf on (cm.cm_res_id=perf.cm_res_id) "
"where c.cmts_res_id=%u and cm.is_deleted=0";

/***** All CM's that belong to a Upstream Channel ****/
AX_INT8 * axDbCm::UpstreamChannelCmQuery = \
"select cm.*, uch.channel_index, uch.name, dch.channel_index, dch.name, hfc.name "
"from canet.channel uch join canet.cable_modem cm on (uch.channel_res_id=cm.upstream_chnl_res_id) "
"left outer join canet.channel dch on (cm.downstream_chnl_res_id=dch.channel_res_id) "
"left outer join canet.hfc_plant hfc on (cm.hfc_res_id=hfc.hfc_res_id) "
"where uch.channel_res_id=%u and cm.is_deleted=0";

/***** All CM's that belong to a Downstream Channel ****/
AX_INT8 * axDbCm::DownstreamChannelCmQuery = \
"select cm.*, uch.channel_index, uch.name, dch.channel_index, dch.name, hfc.name "
"from canet.channel dch join canet.cable_modem cm on (dch.channel_res_id=cm.downstream_chnl_res_id) "
"left outer join canet.channel uch on (cm.upstream_chnl_res_id=uch.channel_res_id) "
"left outer join canet.hfc_plant hfc on (cm.hfc_res_id=hfc.hfc_res_id) "
"where dch.channel_res_id=%u and cm.is_deleted=0";

/***** All CM's that belong to an HFC ****/
AX_INT8 * axDbCm::HfcCmQuery = \
"select cm.*, uch.channel_index, uch.name, dch.channel_index, dch.name, hfc.name "
"from canet.hfc_plant hfc join canet.cable_modem cm on (hfc.hfc_res_id=cm.hfc_res_id) "
"left outer join canet.channel uch on (cm.upstream_chnl_res_id=uch.channel_res_id) "
"left outer join canet.channel dch on (cm.downstream_chnl_res_id=dch.channel_res_id) "
"where hfc.hfc_res_id=%u and cm.is_deleted=0";

AX_INT8 * axDbCm::BasicCmUpdate = \
"update canet.cable_modem set "
"hfc_res_id=%u, upstream_chnl_res_id=%u, downstream_chnl_res_id=%u, "
"cm_index=%d, fqdn='%s', "
"ip_address='%s', docsis_state=%d, end_user_dev_type=%d, is_deleted=%d, "
"is_online=%d, ip_address_type=%d where id=%u ";

AX_INT8 * axDbCm::BasicCmInsert = \
"insert into canet.cable_modem("
"cm_res_id,cmts_res_id,upstream_chnl_res_id,downstream_chnl_res_id,"
"cm_index,mac_address,fqdn,ip_address,docsis_state,end_user_dev_type,"
"is_online,ip_address_type) "
"values(%u,%u,%u,%u,%d,'%s','%s','%s',%d,%d,%d,%d)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// method:
//********************************************************************
void
axDbCm::instantiationInit(void)
{
  DB_UPDATE_TIME_t t = {0};
  m_lastUpdated = t;

  m_cmMac[0] = '\0';
  m_fqdn[0] = '\0';
  m_ipAddress[0] = '\0';
}


//********************************************************************
// default constructor:
//********************************************************************
axDbCm::axDbCm() :
  m_id(0), m_cmResId(0), m_cmtsResId(0), m_hfcResId(0),
  m_upstreamChannelResId(0), m_upstreamChannelIndex(0), 
  m_downstreamChannelResId(0), m_downstreamChannelIndex(0),
  m_modemIndex(0), m_docsisState(0), 
  m_enduserDeviceType(DB_EU_DEVIE_TYPE_CM), m_deleted(0), m_isOnline(0), 
  m_ipAddressType(DB_IPADDR_TYPE_IPv4), m_alertLevel(0),
  m_downstreamSNR(0), m_downstreamPower(0), m_upstreamPower(0),
  m_upstreamSNRatCmts(0), m_upstreamPowerAtCmts(0),
  m_t1Timeouts(0), m_t2Timeouts(0), m_t3Timeouts(0), m_t4Timeouts(0)
{
  instantiationInit();
}


//********************************************************************
// destructor:
//********************************************************************
axDbCm::~axDbCm()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCm::axDbCm(DB_RESID_t cmResId) :
  m_id(0), m_cmResId(cmResId), m_cmtsResId(0), m_hfcResId(0),
  m_upstreamChannelResId(0), m_upstreamChannelIndex(0),
  m_downstreamChannelResId(0), m_downstreamChannelIndex(0),
  m_modemIndex(0), m_docsisState(0), 
  m_enduserDeviceType(DB_EU_DEVIE_TYPE_CM), m_deleted(0), m_isOnline(0), 
  m_ipAddressType(DB_IPADDR_TYPE_IPv4), m_alertLevel(0),
  m_downstreamSNR(0), m_downstreamPower(0), m_upstreamPower(0),
  m_upstreamSNRatCmts(0), m_upstreamPowerAtCmts(0),
  m_t1Timeouts(0), m_t2Timeouts(0), m_t3Timeouts(0), m_t4Timeouts(0)
{
  instantiationInit();
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCm::axDbCm(DB_MAC_t cmMac, DB_RESID_t cmtsResId) :
  m_id(0), m_cmResId(0), m_cmtsResId(cmtsResId), m_hfcResId(0),
  m_upstreamChannelResId(0), m_upstreamChannelIndex(0),
  m_downstreamChannelResId(0), m_downstreamChannelIndex(0),
  m_modemIndex(0), m_docsisState(0), 
  m_enduserDeviceType(DB_EU_DEVIE_TYPE_CM), m_deleted(0), m_isOnline(0), 
  m_ipAddressType(DB_IPADDR_TYPE_IPv4), m_alertLevel(0),
  m_downstreamSNR(0), m_downstreamPower(0), m_upstreamPower(0),
  m_upstreamSNRatCmts(0), m_upstreamPowerAtCmts(0),
  m_t1Timeouts(0), m_t2Timeouts(0), m_t3Timeouts(0), m_t4Timeouts(0)
{
  instantiationInit();
  copyDbMac(m_cmMac, cmMac);
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCm::axDbCm(axInternalCm * intCm, DB_RESID_t cmtsResId) :
  m_id(0), m_cmResId(0), m_cmtsResId(cmtsResId), m_hfcResId(0),
  m_upstreamChannelResId(0), m_downstreamChannelResId(0),
  m_alertLevel(0)
{
  instantiationInit();

  axIntCmKey_t    * keyPart = (axIntCmKey_t *) intCm->getKey();
  copyDbMac(m_cmMac, keyPart->mac);

  if (intCm->hasData()) {

    axIntCmNonkey_t * nonkeyPart = (axIntCmNonkey_t *) intCm->getNonKey();
    copyDbIpAddress(m_ipAddress, nonkeyPart->ipAddress);
    m_modemIndex = nonkeyPart->modemIndex;
    m_upstreamChannelIndex = nonkeyPart->upstreamChannelIndex;
    m_downstreamChannelIndex = nonkeyPart->downstreamChannelIndex;
    m_docsisState = nonkeyPart->docsisState;
    m_isOnline = axInternalCm::isCmOnline(nonkeyPart->docsisState);
    m_ipAddressType = nonkeyPart->ipAddressType;
    m_enduserDeviceType = nonkeyPart->euDeviceType;
    m_deleted = 0;

    m_downstreamSNR = nonkeyPart->downstreamSNR;
    m_downstreamPower = nonkeyPart->downstreamPower;
    m_upstreamPower = nonkeyPart->upstreamPower;
    // m_upstreamSNRatCmts = nonkeyPart->upstreamSNRatCmts;
    // m_upstreamPowerAtCmts = nonkeyPart->upstreamPowerAtCmts;
    m_t1Timeouts = nonkeyPart->t1Timeouts;
    m_t2Timeouts = nonkeyPart->t2Timeouts;
    m_t3Timeouts = nonkeyPart->t3Timeouts;
    m_t4Timeouts = nonkeyPart->t4Timeouts;

  } else {

    m_modemIndex = 0;
    m_upstreamChannelIndex = 0;
    m_downstreamChannelIndex = 0;
    m_docsisState = 0;
    m_isOnline = 0;
    m_ipAddressType = 0;
    m_enduserDeviceType = 0;
    m_deleted = 1;

  }

}


//********************************************************************
// copy constructor:
//********************************************************************
axDbCm::axDbCm(const axDbCm & in) :
  axAbstractDbObject(),
  m_id(in.m_id), 
  m_cmResId(in.m_cmResId), 
  m_cmtsResId(in.m_cmtsResId), 
  m_hfcResId(in.m_hfcResId), 
  m_upstreamChannelResId(in.m_upstreamChannelResId), 
  m_upstreamChannelIndex(in.m_upstreamChannelIndex),
  m_downstreamChannelResId(in.m_downstreamChannelResId), 
  m_downstreamChannelIndex(in.m_downstreamChannelIndex),
  m_modemIndex(in.m_modemIndex), 
  m_docsisState(in.m_docsisState), 
  m_enduserDeviceType(in.m_enduserDeviceType), 
  m_deleted(in.m_deleted), 
  m_isOnline(in.m_isOnline),
  m_ipAddressType(in.m_ipAddressType),
  m_alertLevel(in.m_alertLevel),
  m_downstreamSNR(in.m_downstreamSNR), 
  m_downstreamPower(in.m_downstreamPower), 
  m_upstreamPower(in.m_upstreamPower), 
  m_upstreamSNRatCmts(in.m_upstreamSNRatCmts), 
  m_upstreamPowerAtCmts(in.m_upstreamPowerAtCmts),
  m_t1Timeouts(in.m_t1Timeouts), m_t2Timeouts(in.m_t2Timeouts), 
  m_t3Timeouts(in.m_t3Timeouts), m_t4Timeouts(in.m_t4Timeouts)
{
  instantiationInit();

  copyDbMac(m_fqdn, in.m_fqdn);
  copyDbMac(m_cmMac, in.m_cmMac);
  copyDbIpAddress(m_ipAddress, const_cast<AX_INT8 *> (in.m_ipAddress));
  // m_docsisState = in.m_docsisState;
  // m_upstreamChannelIndex = in.m_upstreamChannelIndex;
  // m_downstreamChannelIndex = in.m_downstreamChannelIndex;
  // m_modemIndex = in.m_modemIndex;
}


//********************************************************************
// method:
//********************************************************************
axDbCm & 
axDbCm::operator= (const axDbCm & rv)
{
  if (this == &rv) {
    return (*this);
  }

  m_id = (rv.m_id);
  m_cmResId = (rv.m_cmResId);
  m_cmtsResId = (rv.m_cmtsResId);
  m_hfcResId = (rv.m_hfcResId);
  m_upstreamChannelResId = (rv.m_upstreamChannelResId);
  m_upstreamChannelIndex = (rv.m_upstreamChannelIndex);
  m_downstreamChannelResId = (rv.m_downstreamChannelResId);
  m_downstreamChannelIndex = (rv.m_downstreamChannelIndex);
  m_modemIndex = (rv.m_modemIndex);
  m_docsisState = (rv.m_docsisState);
  m_enduserDeviceType = (rv.m_enduserDeviceType);
  m_deleted = (rv.m_deleted);
  m_isOnline = (rv.m_isOnline);
  m_ipAddressType = (rv.m_ipAddressType);
  m_alertLevel = (rv.m_alertLevel);
  m_downstreamSNR = (rv.m_downstreamSNR); 
  m_downstreamPower = (rv.m_downstreamPower);
  m_upstreamPower = (rv.m_upstreamPower); 
  m_upstreamSNRatCmts = (rv.m_upstreamSNRatCmts);
  m_upstreamPowerAtCmts= (rv.m_upstreamPowerAtCmts);
  m_t1Timeouts = (rv.m_t1Timeouts); 
  m_t2Timeouts = (rv.m_t2Timeouts);
  m_t3Timeouts = (rv.m_t3Timeouts); 
  m_t4Timeouts = (rv.m_t4Timeouts);

  instantiationInit();

  copyDbMac(m_fqdn, rv.m_fqdn);
  copyDbMac(m_cmMac, rv.m_cmMac);
  copyDbIpAddress(m_ipAddress, const_cast<AX_INT8 *> (rv.m_ipAddress));

  return (*this);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axDbCm::getResourceType(void)
{
  return (DB_REST_CM);
}



//********************************************************************
// method:
//********************************************************************
bool
axDbCm::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[128];

  if (!m_id && !m_cmResId && m_cmMac[0]=='\0') {
    goto EXIT_LABEL;
  }

  if (m_id) {
    sprintf(whereClause, "where cm.id=%u", m_id);
  } else if (m_cmResId) {
    sprintf(whereClause, "where cm.cm_res_id=%u", m_cmResId);
  } else {
    sprintf(whereClause, "where cm.mac_address='%s'", m_cmMac);
  }

  sprintf(qryStr, "%s %s", BasicCmQuery, whereClause);

  ret = getRow(qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCm::getRow(AX_INT8 * qryStr)
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
axDbCm::getRows(axListBase & cmList)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[128];

  if (m_cmMac[0]=='\0') {
    goto EXIT_LABEL;
  }

  sprintf(whereClause, "where cm.mac_address='%s'", m_cmMac);

  sprintf(qryStr, "%s %s", BasicCmQuery, whereClause);

  ret = getRows(cmList, qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCm::getRows(axListBase & cmList, AX_INT8 * qryStr)
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
      axDbCm * newCm = new axDbCm(*this);
      if (newCm) {
        cmList.add(newCm);
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
axDbCm::insertRow(void)
{
  bool ret = false;
  char sqlStr[512];

  bool        isUpdate = false;
  axDbChannel upChnl;
  axDbChannel downChnl;
  axDbCm      tmpCm;

  axDbConnectionFactory * cf = NULL;

  copyDbMac(tmpCm.m_cmMac,m_cmMac);

  if (tmpCm.getRow()) {

    /*
     *  This CM MAC is perhaps assigned to another CMTS, or
     *  this CM MAC is perhaps assigned to the same CMTS, but the
     *  'deleted' flag is set (to '1').
     */

    isUpdate = true;

    /*
     * A big assumption that CMTS-ResId is NOT Zero
     */
    if (tmpCm.m_cmtsResId == m_cmtsResId) {

      /*
       * Update scenario; make sure we set deleted=0;
       */

      m_deleted = 0;

    } else {

      /*
       * This MAC is trying to register on another CMTS. Don't add it
       * until it becomes online. It could be one of those that is
       * simply jumping frequencies and trying to register on any available
       * CMTS. We should not allow this.
       */

      if (!axInternalCm::isCmOnline(m_docsisState)) {
        goto EXIT_LABEL;
      }

    }

    /*
     * Since it is update, set some basic fields
     */
    m_id = tmpCm.m_id;
    m_cmResId = tmpCm.m_cmResId;
  }

  /*
   * Get HFC based on CMTS ResId and HFC name; Actually we NOT will know
   *   which HFC a new CM belongs to at the time of insert!
   * Get channel based on CMTS ResId and Upstream Channel Index
   * Get channel based on CMTS ResId and Downstream Channel Index
   */

  upChnl.m_cmtsResId = m_cmtsResId;
  upChnl.m_channelIndex = m_upstreamChannelIndex;
  upChnl.getRow();

  downChnl.m_cmtsResId = m_cmtsResId;
  downChnl.m_channelIndex = m_downstreamChannelIndex;
  downChnl.getRow();

  m_upstreamChannelResId = upChnl.m_channelResId;
  m_downstreamChannelResId = downChnl.m_channelResId;

  cf = axDbExternalizer::getConnectionFactory();

  if (!isUpdate) {

    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbXResource res;
    res.m_resType = getResourceType();
    if (!res.insertRow(mc)) {
      goto EXIT_LABEL;
    }

    m_cmResId = res.m_resId;

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, BasicCmInsert, m_cmResId,
            m_cmtsResId, m_upstreamChannelResId, m_downstreamChannelResId, 
            m_modemIndex, m_cmMac, m_fqdn, 
            m_ipAddress, m_docsisState, m_enduserDeviceType, m_isOnline, 
            m_ipAddressType, m_alertLevel);

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    {
      axDbCmStatusLog cmStatusLog(m_cmResId);
      cmStatusLog.m_docsisState = m_docsisState;
      cmStatusLog.insertRow();
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
axDbCm::updateRow(void)
{
  bool ret = false;
  char sqlStr[512];

  /*
   * Get HFC based on CMTS ResId and HFC name
   * Get channel based on CMTS ResId and Upstream Channel Index
   * Get channel based on CMTS ResId and Downstream Channel Index
   */

  sprintf(sqlStr, BasicCmUpdate,
          m_hfcResId, m_upstreamChannelResId, m_downstreamChannelResId, 
          m_modemIndex, m_fqdn, m_ipAddress, 
          m_docsisState, m_enduserDeviceType, m_deleted, m_isOnline, 
          m_ipAddressType, m_alertLevel, m_id);

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
axDbCm::deleteRow(void)
{
  bool ret = false;


  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCm::getPerformance(void)
{
  bool ret = false;


  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCm::isEmta(void)
{
  return (m_enduserDeviceType==DB_EU_DEVIE_TYPE_EMTA);
}


