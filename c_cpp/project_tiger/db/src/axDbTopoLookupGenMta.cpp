//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axDbTopoLookupGenMta.hpp"
#include "axDbConnectionFactory.hpp"
#include "axDbExternalizer.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbGenericQuery.hpp"
#include "axDbResultSet.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbTopoLookupGenMta::Query = \
"select * from canet.topo_lookup_mta ";

AX_INT8 * axDbTopoLookupGenMta::Update = \
"update canet.topo_lookup_mta set topo_container_id=%d where id=%u";

AX_INT8 * axDbTopoLookupGenMta::Insert = \
"insert into canet.topo_lookup_mta(mta_res_id,mac_address,topo_container_id) "
"values(%u,'%s',%d)";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbTopoLookupGenMta::axDbTopoLookupGenMta()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbTopoLookupGenMta::~axDbTopoLookupGenMta()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbTopoLookupGenMta::axDbTopoLookupGenMta(AX_UINT16 containerId,
                                                   DB_RESID_t resId) :
  axDbAbstractTopoLookup(containerId, resId)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbTopoLookupGenMta::axDbTopoLookupGenMta(const AX_INT8 * strId):
  axDbAbstractTopoLookup(strId)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbTopoLookupGenMta::getRow(void)
{
  bool ret = false;

  char qryStr[512];
  char whereClause[128];

  if (m_topoContainerId && m_resId) {
    sprintf(whereClause, "where topo_container_id=%d and mta_res_id=%u", 
                                          m_topoContainerId, m_resId);
    sprintf(qryStr, "%s %s", Query, whereClause);
    ret = getRow(qryStr);
  } else if (strlen(m_strId.c_str())) {
    sprintf(whereClause, "where mac_address='%s'", m_strId.c_str());
    sprintf(qryStr, "%s %s", Query, whereClause);
    ret = getRow(qryStr);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbTopoLookupGenMta::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    bool qrc = query->execute(sqlStr, (axDbAbstractTopoLookup &) (*this));
    if (!qrc) {
      goto EXIT_LABEL;
    }

    axDbResultSet * rs = dynamic_cast<axDbResultSet *> (query->getResultSet());

    if ( !rs->getNext((axDbAbstractTopoLookup &) (*this)) ) {
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
axDbTopoLookupGenMta::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbTopoLookupGenMta::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbTopoLookupGenMta::insertOrUpdateRow(void)
{
  bool ret;

  axDbTopoLookupGenMta tmpObj1(m_topoContainerId, m_resId);
  axDbTopoLookupGenMta tmpObj2(m_strId.c_str());
  bool rc1, rc2;
  if (!(rc1=tmpObj1.getRow()) && !(rc2=tmpObj2.getRow())) {
    ret = insertRow();
  } else {
    if (rc1) {
      m_id = tmpObj1.m_id;
    } else {
      m_id = tmpObj2.m_id;
    }
    ret = updateRow();
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbTopoLookupGenMta::insertRow(void)
{
  static const char * myName="DbTopoGenMta:Ins:";

  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Insert, m_resId, m_strId.c_str(), m_topoContainerId);

    ACE_DEBUG((LM_DB_DEBUG, "%s %s\n", myName, sqlStr));

    bool qrc = query->execute(sqlStr, (axDbAbstractTopoLookup &) (*this));
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
axDbTopoLookupGenMta::updateRow(void)
{
  static const char * myName="cmtsCurrCntsUpd:";

  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Update, m_topoContainerId, m_id);

    ACE_DEBUG((LM_DB_DEBUG, "%s %s\n", myName, sqlStr));

    bool qrc = query->execute(sqlStr, (axDbAbstractTopoLookup &) (*this));
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
axDbTopoLookupGenMta::deleteRow(void)
{
  bool ret = false;

  return (ret);
}


