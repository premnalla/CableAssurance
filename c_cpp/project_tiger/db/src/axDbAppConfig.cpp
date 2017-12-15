
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
#include "axDbAppConfig.hpp"
#include "axDbUtils.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbWriteConnHelper.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbAppConfig::QueryAll = ""
"select a.* from canet.app_config a";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbAppConfig::axDbAppConfig()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbAppConfig::~axDbAppConfig()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axDbAppConfig::axDbAppConfig()
// {
// }


//********************************************************************
// copy constructor:
//********************************************************************
axDbAppConfig::axDbAppConfig(const axDbAppConfig & in) :
  axAbstractDbObject(),
  m_varName(in.m_varName),
  m_varValue(in.m_varValue)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbAppConfig::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[128];

  if (strlen(m_varName.c_str())) {
    sprintf(whereClause, "where a.var_name=%s", m_varName.c_str());
  } else {
    goto EXIT_LABEL;
  }

  sprintf(qryStr, "%s %s", QueryAll, whereClause);

  ret = getRow(qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbAppConfig::getRow(AX_INT8 * qryStr)
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
axDbAppConfig::getRows(axListBase & cmList)
{
  bool ret = getRows(cmList, QueryAll);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbAppConfig::getRows(axListBase & cmtsList, AX_INT8 * qryStr)
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
      axDbAppConfig * newCmts = new axDbAppConfig(*this);
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
axDbAppConfig::insertRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbAppConfig::updateRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbAppConfig::deleteRow(void)
{
  bool ret = false;

  return (ret);
}



//********************************************************************
// method:
//********************************************************************
bool
axDbAppConfig::isKeyEqual(axObject * o)
{
  axDbAppConfig * oo = dynamic_cast<axDbAppConfig *> (o);
  bool ret = (!strcmp(m_varName.c_str(), oo->m_varName.c_str()) ? 
                                                        true : false);
  return (ret);
}


