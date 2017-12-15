
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCblAssureConstants.hpp"
#include "axDbExternalizer.hpp"
#include "axDbConnectionFactory.hpp"
#include "axDbGenericQuery.hpp"
#include "axDbConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbResultSet.hpp"
#include "axDbCmts.hpp"
#include "axDbUtils.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbWriteConnHelper.hpp"
#include "axDbXResource.hpp"
#include "axDbSnmpV2CAttrs.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbCmts::Insert = ""
"insert into canet.cmts (cmts_res_id,name,fqdn,ip_address,"
"cmts_snmp_ver,cm_cm_snmp_ver,mta_snmp_ver,online_state,ip_address_type,"
"alert_level) values(%u,'%s','%s','%s','%s','%s','%s','%s','%s','%s',%d,%d,%d)";

AX_INT8 * axDbCmts::Query = ""
"select c.* from canet.cmts c ";

AX_INT8 * axDbCmts::QueryAll = ""
"select c.* from canet.cmts c where c.is_deleted=0";

AX_INT8 * axDbCmts::QueryAllStartup = ""
"select c.*,n.* from canet.cmts c left outer join "
"caperf.cmts_current_counts n using(cmts_res_id) where c.is_deleted=0";

AX_INT8 * axDbCmts::Update = ""
"update canet.cmts set name='%s',fqdn='%s',ip_address='%s',"
"cmts_snmp_ver='%d',cm_snmp_ver='%d',mta_snmp_ver='%d',"
"online_state=%d,is_deleted=%d,ip_address_type=%d,alert_level=%d "
"where cmts_res_id=%u";

AX_INT8 * axDbCmts::Delete = ""
"delete from canet.cmts where cmts_res_id=%u";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// destructor:
//********************************************************************
void
axDbCmts::initAtInstantiation(void)
{
  DB_UPDATE_TIME_t t = {0};
  m_lastUpdated = t;

  m_cmtsName[0] = '\0';
  m_fqdn[0] = '\0';
  m_ipAddress[0] = '\0';
}


//********************************************************************
// default constructor:
//********************************************************************
axDbCmts::axDbCmts() :
  m_id(0), m_cmtsResId(0),
  m_cmtsSnmpVersion(AX_SNMP_VERSION_2c), 
  m_cmSnmpVersion(AX_SNMP_VERSION_2c),
  m_mtaSnmpVersion(AX_SNMP_VERSION_2c),
  m_onlineState(0), m_deleted(0), m_ipAddressType(DB_IPADDR_TYPE_IPv4),
  m_alertLevel(0)
{
  // DB_CMTS_FIELDS f = {0};
  // m_cmtsData = f;

  initAtInstantiation();
}


//********************************************************************
// destructor:
//********************************************************************
axDbCmts::~axDbCmts()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axDbCmts::axDbCmts()
// {
// }


//********************************************************************
// copy constructor:
//********************************************************************
axDbCmts::axDbCmts(const axDbCmts & in) : 
  axAbstractDbObject(),
  m_id(in.m_id),
  m_cmtsResId(in.m_cmtsResId),
  m_cmtsSnmpVersion(in.m_cmtsSnmpVersion),
  m_cmSnmpVersion(in.m_cmSnmpVersion),
  m_mtaSnmpVersion(in.m_mtaSnmpVersion),
  m_onlineState(in.m_onlineState),
  m_deleted(in.m_deleted),
  m_ipAddressType(in.m_ipAddressType),
  m_alertLevel(in.m_alertLevel)
{
  initAtInstantiation();

  copyDbCmtsName(m_cmtsName, in.m_cmtsName);
  copyDbFqdn(m_fqdn,in.m_fqdn);
  copyDbIpAddress(m_ipAddress, in.m_ipAddress);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axDbCmts::getResourceType(void)
{
  return (DB_REST_CMTS);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmts::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[128];

  if (m_id) {
    sprintf(whereClause, "where c.id=%u", m_id);
  } else if (m_cmtsResId) {
    sprintf(whereClause, "where c.cmts_res_id=%u", m_cmtsResId);
  } else if (m_cmtsName[0]!='\0') {
    sprintf(whereClause, "where c.name='%s'", m_cmtsName);
  } else {
    goto EXIT_LABEL;
  }

  sprintf(qryStr, "%s %s", Query, whereClause);

  ret = getRow(qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmts::getRow(AX_INT8 * qryStr)
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
axDbCmts::getRows(axListBase & cmList)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[128];

  if (m_cmtsName[0]!='\0') {
    sprintf(whereClause, "where c.name='%s'", m_cmtsName);
  } else {
    goto EXIT_LABEL;
  }

  sprintf(qryStr, "%s %s", Query, whereClause);

  ret = getRows(cmList, qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCmts::getRows(axListBase & cmtsList, AX_INT8 * qryStr)
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

    while (rs->getNext(*this)) {
      axDbCmts * newCmts = new axDbCmts(*this);
      if (newCmts) {
        cmtsList.add(newCmts);
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
axDbCmts::insertRow(void)
{
  bool ret = false;
  char sqlStr[512];

  bool     isUpdate = false;
  axDbCmts tmpCmts;

  copyDbCmtsName(tmpCmts.m_cmtsName, m_cmtsName);

  if (tmpCmts.getRow()) {
    isUpdate = true;
    m_deleted = 0;
    m_id = tmpCmts.m_id;
    m_cmtsResId = tmpCmts.m_cmtsResId;
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

    m_cmtsResId = res.m_resId;

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_cmtsResId, m_cmtsName,
            m_fqdn, m_ipAddress, m_cmtsSnmpVersion, m_cmSnmpVersion, 
            m_mtaSnmpVersion, m_onlineState, m_ipAddressType, 
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
axDbCmts::updateRow(void)
{
  bool ret = false;
  char sqlStr[512];

  sprintf(sqlStr, Update, m_cmtsName, m_fqdn, m_ipAddress,
          m_cmtsSnmpVersion, m_cmSnmpVersion, m_mtaSnmpVersion,
          m_onlineState, m_deleted, m_ipAddressType, m_alertLevel,
          m_cmtsResId);

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
axDbCmts::deleteRow(void)
{
  bool ret = false;

  char sqlStr[512];

  sprintf(sqlStr, Delete, m_cmtsResId);

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
axAbsDbSnmpVerAttrs *
axDbCmts::getCmtsSnmpVersionAttrs(void)
{
  static const char * V2C_QRY =
    "select c.* from canet.cmts_v2c_attributes c where c.cmts_res_id=%u";
  static const char * V3_QRY =
    "select c.* from canet.cmts_v3_attributes c where c.cmts_res_id=%u";

  axAbsDbSnmpVerAttrs * ret = NULL;
  char qryStr[256];

  if (m_cmtsSnmpVersion == AX_SNMP_VERSION_2c) {
    sprintf(qryStr, V2C_QRY, m_cmtsResId);
    ret = commonGetCmtsSnmpV2CAttrs(qryStr);
  } else if (m_cmtsSnmpVersion == AX_SNMP_VERSION_3) {
    sprintf(qryStr, V3_QRY, m_cmtsResId);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axAbsDbSnmpVerAttrs *
axDbCmts::getCmSnmpVersionAttrs(void)
{
  static const char * V2C_QRY =
    "select c.* from canet.cmts_cm_v2c_attributes c where c.cmts_res_id=%u";
  static const char * V3_QRY =
    "select c.* from canet.cmts_cm_v3_attributes c where c.cmts_res_id=%u";

  axAbsDbSnmpVerAttrs * ret = NULL;
  char qryStr[256];

  if (m_cmSnmpVersion == AX_SNMP_VERSION_2c) {
    sprintf(qryStr, V2C_QRY, m_cmtsResId);
    ret = commonGetCmtsSnmpV2CAttrs(qryStr);
  } else if (m_cmSnmpVersion == AX_SNMP_VERSION_3) {
    sprintf(qryStr, V3_QRY, m_cmtsResId);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axAbsDbSnmpVerAttrs *
axDbCmts::getMtaSnmpVersionAttrs(void)
{
  static const char * V2C_QRY =
    "select c.* from canet.cmts_mta_v2c_attributes c where c.cmts_res_id=%u";
  static const char * V3_QRY =
    "select c.* from canet.cmts_mta_v3_attributes c where c.cmts_res_id=%u";

  axAbsDbSnmpVerAttrs * ret = NULL;
  char qryStr[256];

  if (m_mtaSnmpVersion == AX_SNMP_VERSION_2c) {
    sprintf(qryStr, V2C_QRY, m_cmtsResId);
    ret = commonGetCmtsSnmpV2CAttrs(qryStr);
  } else if (m_mtaSnmpVersion == AX_SNMP_VERSION_3) {
    sprintf(qryStr, V3_QRY, m_cmtsResId);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
axAbsDbSnmpVerAttrs *
axDbCmts::commonGetCmtsSnmpV2CAttrs(char * qryStr)
{
  axAbsDbSnmpVerAttrs * ret = NULL;

  // printf("qryStr:%s\n", qryStr);

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    axDbSnmpV2CAttrs tmpDbV2cAttrs;
    bool qrc = query->execute(qryStr, tmpDbV2cAttrs);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    axDbResultSet * rs = dynamic_cast<axDbResultSet *>
                                             (query->getResultSet());
    if (!rs->getNext(tmpDbV2cAttrs)) {
      goto EXIT_LABEL;
    }

    ret = new axDbSnmpV2CAttrs(tmpDbV2cAttrs);
  }

EXIT_LABEL:

  return (ret);
}


