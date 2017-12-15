
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
#include "axDbXResource.hpp"
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
AX_INT8 * axDbXResource::BasicQuery = \
"";

AX_INT8 * axDbXResource::BasicUpdate = \
"";

AX_INT8 * axDbXResource::BasicInsert = \
"insert into xresource(res_type) values(%d)"
"";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbXResource::axDbXResource() :
  m_resId(0), m_resType(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbXResource::~axDbXResource()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbXResource::axDbXResource(DB_RESID_t resId, DB_RESTYPE_t resType) :
  m_resId(resId), m_resType(resType)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbXResource::axDbXResource(DB_RESID_t resId) :
  m_resId(resId), m_resType(0)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbXResource::insertRow(void)
{
  bool ret;

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();
    ret = this->insertRow(mc);
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbXResource::insertRow(axDbConnection * conn)
{
  bool ret;
  char sqlStr[512];

  sprintf(sqlStr, BasicInsert, m_resId, m_resType);

  axDbQueryHelper queryH(axDbExternalizer::getQuery(conn));
  axDbGenericQuery * query = queryH.getQuery();

  ret = query->execute(sqlStr, *this);

  if (ret) {
    m_resId = (DB_RESID_t) query->getInsertId();
  }

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbXResource::getRow(void)
{
  return (false);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbXResource::updateRow(void)
{
  return (false);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbXResource::deleteRow(void)
{
  return (false);
}



