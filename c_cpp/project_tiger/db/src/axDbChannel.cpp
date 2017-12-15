
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
#include "axDbChannel.hpp"
#include "axInternalChannel.hpp"
#include "axDbUtils.hpp"
#include "axDbReadConnHelper.hpp"
#include "axDbWriteConnHelper.hpp"
#include "axDbXResource.hpp"
// #include "axDbChannelStatusLog.hpp"


//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbChannel::BasicChannelQuery = \
"select ch.* from channel canet.ch ";

AX_INT8 * axDbChannel::CmtsChannelQuery = \
"select ch.* from canet.channel ch join canet.cmts c on (ch.cmts_res_id=c.cmts_res_id) "
"where c.cmts_res_id=%u and ch.is_deleted=0";

AX_INT8 * axDbChannel::CmtsChannelQueryStartup = \
"select ch.*,n.* from canet.channel ch join canet.cmts c using (cmts_res_id) "
"left outer join caperf.channel_current_counts n using (channel_res_id) "
"where c.cmts_res_id=%u and ch.is_deleted=0";

AX_INT8 * axDbChannel::BasicChannelUpdate = \
"update canet.channel set channel_index=%d, channel_type=%d, "
"alert_level=%d, is_deleted=%d where id=%u ";

AX_INT8 * axDbChannel::BasicChannelInsert = \
"insert into canet.channel(channel_res_id,cmts_res_id,channel_index,"
"channel_type,alert_level,name) values(%u,%u,%d,%d,%d,'%s')";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// method:
//********************************************************************
void
axDbChannel::instantiationInit(void)
{
  DB_UPDATE_TIME_t t = {0};
  m_lastUpdated = t;

  m_channelName[0] = '\0';
}


//********************************************************************
// default constructor:
//********************************************************************
axDbChannel::axDbChannel() :
  m_id(0), m_channelResId(0), m_cmtsResId(0), m_channelIndex(0), 
  m_channelType(0), m_alertLevel(0), m_deleted(0), m_downstreamPower(0)
{
  instantiationInit();
}


//********************************************************************
// destructor:
//********************************************************************
axDbChannel::~axDbChannel()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbChannel::axDbChannel(DB_RESID_t channelResId) :
  m_id(0), m_channelResId(channelResId), m_cmtsResId(0), m_channelIndex(0),
  m_channelType(0), m_alertLevel(0), m_deleted(0), m_downstreamPower(0)
{
  instantiationInit();
}


//********************************************************************
// data constructor:
//********************************************************************
axDbChannel::axDbChannel(DB_CHANNEL_NAME_t channelName, DB_RESID_t cmtsResId) :
  m_id(0), m_channelResId(0), m_cmtsResId(cmtsResId), m_channelIndex(0),
  m_channelType(0), m_alertLevel(0), m_deleted(0), m_downstreamPower(0)
{
  instantiationInit();
  copyDbChannelName(m_channelName, channelName);
}


//********************************************************************
// data constructor:
//********************************************************************
axDbChannel::axDbChannel(AX_UINT32 channelIndex, DB_RESID_t cmtsResId) :
  m_id(0), m_channelResId(0), m_cmtsResId(cmtsResId), m_channelIndex(channelIndex),
  m_channelType(0), m_alertLevel(0), m_deleted(0), m_downstreamPower(0)
{
  instantiationInit();
}


//********************************************************************
// data constructor:
//********************************************************************
axDbChannel::axDbChannel(axInternalChannel * intChannel, DB_RESID_t cmtsResId) :
  m_id(0), m_channelResId(0), m_cmtsResId(cmtsResId), m_alertLevel(0),
  m_deleted(0), m_downstreamPower(0)
{
  instantiationInit();

  axIntChannelKey_t    * keyPart = (axIntChannelKey_t *) intChannel->getKey();
  axIntChannelNonkey_t * nonkeyPart = (axIntChannelNonkey_t *) intChannel->getNonKey();

  copyDbChannelName(m_channelName, keyPart->name);
  m_channelIndex = nonkeyPart->channelIndex;
  m_channelType = nonkeyPart->channelType;
}


//********************************************************************
// copy constructor:
//********************************************************************
axDbChannel::axDbChannel(const axDbChannel & in) :
  axAbstractDbObject(),
  m_id(in.m_id), m_channelResId(in.m_channelResId), m_cmtsResId(in.m_cmtsResId), 
  m_channelIndex(in.m_channelIndex), m_channelType(in.m_channelType), 
  m_alertLevel(in.m_alertLevel), m_deleted(in.m_deleted),
  m_downstreamPower(in.m_downstreamPower)
{
  copyDbChannelName(m_channelName, in.m_channelName);
}


//********************************************************************
// method:
//********************************************************************
AX_UINT8
axDbChannel::getResourceType(void)
{
  return (DB_REST_CHNL);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannel::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[128];

  if (m_id) {
    sprintf(whereClause, "where ch.id=%u", m_id);
  } else if (m_channelResId) {
    sprintf(whereClause, "where ch.channel_res_id=%u", m_channelResId);
  } else if (m_cmtsResId && m_channelName[0] != '\0') {
    sprintf(whereClause, "where ch.cmts_res_id=%u and ch.name='%s'",
            m_cmtsResId, m_channelName);
  } else {
    goto EXIT_LABEL;
  }

  sprintf(qryStr, "%s %s", BasicChannelQuery, whereClause);

  ret = getRow(qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannel::getRow(AX_INT8 * qryStr)
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
axDbChannel::getRows(axListBase & chList)
{
  bool   ret = false;
  char   qryStr[512];
  char   whereClause[128];

  if (m_cmtsResId && m_channelName[0] != '\0') {
    sprintf(whereClause, "where ch.cmts_res_id=%u and ch.name='%s'",
            m_cmtsResId, m_channelName);
  } else if (m_cmtsResId && m_channelIndex) {
    sprintf(whereClause, "where ch.cmts_res_id=%u and ch.channel_index=%d",
            m_cmtsResId, m_channelIndex);
  } else {
    goto EXIT_LABEL;
  }

  sprintf(qryStr, "%s %s", BasicChannelQuery, whereClause);

  ret = getRows(chList, qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbChannel::getRows(axListBase & chList, AX_INT8 * qryStr)
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
      axDbChannel * newCh = new axDbChannel(*this);
      if (newCh) {
        chList.add(newCh);
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
axDbChannel::insertRow(void)
{
  bool ret = false;
  char sqlStr[512];

  bool          isUpdate = false;
  axDbChannel   tmpCh;

  axDbConnectionFactory * cf;

  tmpCh.m_cmtsResId = m_cmtsResId;
  copyDbChannelName(tmpCh.m_channelName, m_channelName);

  if (tmpCh.getRow()) {

    isUpdate = true;

    if (tmpCh.m_deleted) {

      /*
       * Update scenario; make sure we set deleted=0;
       */

      m_deleted = 0;

    } else {

      /*
       * WE SHOULD NEVER BE HERE !!!
       * Don't understand this case
       */

      goto EXIT_LABEL;

    }

    m_id = tmpCh.m_id;
    m_channelResId = tmpCh.m_channelResId;
  }

  cf = axDbExternalizer::getConnectionFactory();

  if (!isUpdate) {

    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbXResource res;
    res.m_resType = getResourceType();
    if (!res.insertRow(mc)) {
      goto EXIT_LABEL;
    }

    m_channelResId = res.m_resId;

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, BasicChannelInsert, m_channelResId,
            m_cmtsResId, m_channelIndex, m_channelType, m_alertLevel,
            m_channelName);

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

#if 0
    {
      axDbChannelStatusLog sLog(m_channelResId);
      sLog.m_XXX = foo;
      sLog.insertRow();
    }
#endif

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
axDbChannel::updateRow(void)
{
  bool ret = false;
  char sqlStr[512];

  sprintf(sqlStr, BasicChannelUpdate, 
          m_channelIndex, m_channelType, m_alertLevel, m_deleted, m_id);

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
axDbChannel::deleteRow(void)
{
  bool ret = false;


  return (ret);
}



