
//********************************************************************
// OBSOLETED
//********************************************************************

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
#include "axDbCACurrentOutage.hpp"
#include "axDbReadConnHelper.hpp"
#include "axIteratorHolder.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
AX_INT8 * axDbCACurrentOutage::BasicCurrAlarmInsert = \
"insert into caalarm.alarm_basic(res_id,detection_time,alarm_type,alarm_sub_type) "
"values(%u,%u,%d,%d)";

AX_INT8 * axDbCACurrentOutage::BasicCurrAlarmQuery = \
"select alm.* from caalarm.alarm_basic alm ";

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbCACurrentOutage::axDbCACurrentOutage() :
  m_alarmId((AX_UINT64)0),
  m_resId(0),
  m_detectionTime(0),
  m_alarmType(0),
  m_alarmSubType(0)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbCACurrentOutage::~axDbCACurrentOutage()
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCACurrentOutage::axDbCACurrentOutage(AX_UINT64 id) :
  m_alarmId(id),
  m_resId(0),
  m_detectionTime(0),
  m_alarmType(0),
  m_alarmSubType(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCACurrentOutage::axDbCACurrentOutage(DB_RESID_t resId) :
  m_alarmId((AX_UINT64)0),
  m_resId(resId),
  m_detectionTime(0),
  m_alarmType(0),
  m_alarmSubType(0)
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCACurrentOutage::axDbCACurrentOutage(axHfcOutage * hO) :
  m_alarmId((AX_UINT64)0),
  m_resId(hO->getResId()),
  m_detectionTime(hO->getDetectionTime()),
  m_alarmType(hO->getAlarmType()),
  m_alarmSubType(hO->getAlarmSubType())
{
}


//********************************************************************
// data constructor:
//********************************************************************
axDbCACurrentOutage::axDbCACurrentOutage(const axDbCACurrentOutage & in) :
  axDbCAOutage(),
  m_alarmId(in.m_alarmId),
  m_resId(in.m_resId),
  m_detectionTime(in.m_detectionTime),
  m_alarmType(in.m_alarmType),
  m_alarmSubType(in.m_alarmSubType)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCACurrentOutage::getRow(void)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[64];

  if (!m_alarmId && !m_resId) {
    goto EXIT_LABEL;
  }

  if (m_alarmId) {
    sprintf(whereClause, "where alm.alarm_id=%lld", m_alarmId);
  } else {
    sprintf(whereClause, "where alm.res_id=%u", m_resId);
  }

  sprintf(qryStr, "%s %s", BasicCurrAlarmQuery, whereClause);

  ret = getRow(qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCACurrentOutage::getRow(AX_INT8 * qryStr)
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
axDbCACurrentOutage::getRows(axListBase & rl)
{
  bool ret = false;
  char qryStr[512];
  char whereClause[64];

  if (!m_resId) {
    goto EXIT_LABEL;
  }

  sprintf(whereClause, "where alm.res_id='%u'", m_resId);

  sprintf(qryStr, "%s %s", BasicCurrAlarmQuery, whereClause);

  ret = getRows(rl, qryStr);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCACurrentOutage::getRows(axListBase & rl, AX_INT8 * qryStr)
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
      axDbCACurrentOutage * out = new axDbCACurrentOutage(*this);
      if (out) {
        rl.add(out);
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
axDbCACurrentOutage::insertRow(void)
{
  bool ret = false;
  char sqlStr[512];

  axDbCACurrentOutage   tmpOut;
  axDbCACurrentOutage * outPtr;
  axListBase            outList;

  tmpOut.m_resId = m_resId;
  tmpOut.getRows(outList);
  bool foundOutage = false;

  if (outList.size()) {

    /*
     *  
     */

    axIteratorHolder iH(outList.createIterator());
    axAbstractIterator * iter = iH.getIterator();
    outPtr = (axDbCACurrentOutage *) iter->getFirst();
    while (outPtr && !foundOutage) {

      if (outPtr->m_alarmType == m_alarmType &&
          outPtr->m_alarmSubType == m_alarmSubType &&
          outPtr->m_detectionTime == m_detectionTime) {
        foundOutage = true;
        break;
      }

      outPtr = (axDbCACurrentOutage *) iter->getNext();
    }

  } // if

  if (foundOutage) {
    goto EXIT_LABEL;
  }

  axDbConnectionFactory * cf = axDbExternalizer::getConnectionFactory();

  {
    axDbReadConnHelper h(cf);
    axDbConnection * mc = h.getConnection();

    axDbQueryHelper queryH(axDbExternalizer::getQuery(mc));
    axDbGenericQuery * query = queryH.getQuery();

    sprintf(sqlStr, BasicCurrAlarmInsert,
            m_resId, m_detectionTime, m_alarmType, m_alarmSubType);

    bool qrc = query->execute(sqlStr, *this);
    if (!qrc) {
      goto EXIT_LABEL;
    }

  }

  ret = true;

EXIT_LABEL:

  outList.clearAndFreeEntries();

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCACurrentOutage::updateRow(void)
{
  return (false);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCACurrentOutage::deleteRow(void)
{
  return (false);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbCACurrentOutage::getSecondaryData(void)
{
  return (false);
}


