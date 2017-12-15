
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
#include "axDbBlade.hpp"
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
AX_INT8 * axDbBlade::Query = \
"select * from canet.blade ";

AX_INT8 * axDbBlade::Update = \
"";

AX_INT8 * axDbBlade::Insert = \
"";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbBlade::axDbBlade()
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbBlade::~axDbBlade()
{
}


//********************************************************************
// method:
//   IN:
//     len - length of retSql buffer
//   OUT:
//     retSql - sql query string
//********************************************************************
bool
axDbBlade::getQuerySql(AX_INT8 * retSql, size_t len)
{
  bool ret = false;

  char wc[512]; // whereClause
  if (m_id != 0) {
    sprintf(wc, "where blade_id=%d", m_id);
  } else if ( strlen(m_name.c_str()) ) {
    sprintf(wc, "where name='%s'", m_name.c_str());
  } else if ( strlen(m_host.c_str()) ) {
    sprintf(wc, "where ip_address='%s'", m_host.c_str());
  } else {
    goto EXIT_LABEL;
  }

  if ((strlen(wc)+strlen(Query)+2) < len) {
    sprintf(retSql, "%s %s", Query, wc);
    ret = true;
  }

EXIT_LABEL:

  return (ret);
}


