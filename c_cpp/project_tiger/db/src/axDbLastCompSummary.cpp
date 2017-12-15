//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axCALog.hpp"
#include "axDbLastCompSummary.hpp"
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
AX_INT8 * axDbLastCompSummary::Query = \
"select * from casumm.last_comp_summary";

AX_INT8 * axDbLastCompSummary::Update = \
"update casumm.last_comp_summary set "
"month=%d, day=%d, year=%d "
"where id=1";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbLastCompSummary::axDbLastCompSummary() :
  m_month(0), m_day(0), m_year(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbLastCompSummary::~axDbLastCompSummary()
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbLastCompSummary::getRow(void)
{
  bool ret = false;

  ret = getRow(Query);

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbLastCompSummary::getRow(AX_INT8 * sqlStr)
{
  bool ret = false;

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
axDbLastCompSummary::getRows(axListBase & l)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbLastCompSummary::getRows(axListBase & l, AX_INT8 * sqlStr)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbLastCompSummary::insertRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbLastCompSummary::updateRow(void)
{
  static const char * myName="lastSummUpd:";

  bool ret = false;

  AX_INT8 sqlStr[512];

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, Update, m_month, m_day, m_year);

    // ACE_DEBUG((LM_DB_DEBUG, "%s %s\n", myName, sqlStr));
    ACE_DEBUG((LM_DB_DEBUG, "%s %d-%d-%d\n", 
                                    myName, m_month, m_day, m_year));

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
axDbLastCompSummary::deleteRow(void)
{
  bool ret = false;

  return (ret);
}



