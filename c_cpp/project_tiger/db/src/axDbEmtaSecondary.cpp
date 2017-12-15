
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axDbExternalizer.hpp"
#include "axDbConnectionFactory.hpp"
#include "axDbGenericQuery.hpp"
#include "axDbConnection.hpp"
#include "axDbQueryHelper.hpp"
#include "axDbResultSet.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbEmtaSecondary.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbEmtaSecondary::Query = \
"select * from canet.emta_secondary ";

AX_INT8 * axDbEmtaSecondary::Insert = \
"insert into canet.emta_secondary(emta_res_id,phone1,phone2) values("
"%u,'%s','%s');";

AX_INT8 * axDbEmtaSecondary::Update = \
"update canet.emta_secondary set phone1='%s', phone2='%s' where "
"emta_res_id=%u";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbEmtaSecondary::axDbEmtaSecondary() :
  m_id(0), m_emta_res_id(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbEmtaSecondary::~axDbEmtaSecondary()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axDbEmtaSecondary::axDbEmtaSecondary()
// {
// }


//********************************************************************
// method:
//********************************************************************
bool
axDbEmtaSecondary::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[128];
  axDbConnectionFactory * cf;

  if (!m_id && !m_emta_res_id) {
    goto EXIT_LABEL;
  }

  if (m_id) {
    sprintf(whereClause, "where id=%u", m_id);
  } else if (m_emta_res_id) {
    sprintf(whereClause, "where emta_res_id=%u", m_emta_res_id);
  }

  sprintf(qryStr, "%s %s", Query, whereClause);

  cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    axDbResultSet * rs;

    bool qrc = query->execute(qryStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

    rs = dynamic_cast<axDbResultSet *> (query->getResultSet());

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
axDbEmtaSecondary::insertRow(void)
{
  bool ret = false;
  char sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  axDbReadConnHelper h(cf);
  axDbConnection * mc = h.getConnection();

  axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
  axDbGenericQuery * query = queryH.getQuery();

  sprintf(sqlStr, Insert, m_emta_res_id, m_phone1.c_str(), m_phone2.c_str());

  bool qrc = query->execute(sqlStr, *this);
  if (!qrc) {
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
axDbEmtaSecondary::updateRow(void)
{
  bool ret = false;
  char sqlStr[512];

  axDbConnectionFactory * cf;
  axDbConnection * mc;

  if (!m_emta_res_id) {
    goto EXIT_LABEL;
  }

  {
    axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Update, m_phone1.c_str(), m_phone2.c_str(), m_emta_res_id);

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


