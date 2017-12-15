
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
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
#include "axDbLocalSystem.hpp"
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
AX_INT8 * axDbLocalSystem::Query = \
"select * from canet.local_system ";

AX_INT8 * axDbLocalSystem::Update = \
"update canet.local_system set system_type=%d system_name='%s'";

AX_INT8 * axDbLocalSystem::Insert = \
"";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbLocalSystem::axDbLocalSystem() :
  m_systemType(0), m_parentIpType(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbLocalSystem::~axDbLocalSystem()
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbLocalSystem::insertRow(void)
{
  bool ret = false;

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbLocalSystem::getRow(void)
{
  bool ret = false;

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    bool qrc = query->execute(Query, *this);
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
axDbLocalSystem::updateRow(void)
{
  return (false);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbLocalSystem::deleteRow(void)
{
  return (false);
}



