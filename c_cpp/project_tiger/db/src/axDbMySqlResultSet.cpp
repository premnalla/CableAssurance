
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include <typeinfo>
#include "axCALog.hpp"
#include "axDbMySqlResultSet.hpp"
#include "axDbUtils.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axDbMySqlResultSet::axDbMySqlResultSet() :
  axDbResultSet((axDbConnection *) NULL),
  m_rsHandle(NULL)
{
}


//********************************************************************
// destructor:
//********************************************************************
axDbMySqlResultSet::~axDbMySqlResultSet()
{
  if (m_rsHandle) {
    mysql_free_result(m_rsHandle);
    m_rsHandle = NULL;
  }
}


//********************************************************************
// data constructor:
//********************************************************************
axDbMySqlResultSet::axDbMySqlResultSet (
  axDbConnection * c, MYSQL_RES * rs) : 
    axDbResultSet(c), m_rsHandle(rs)
{
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCmts & cmts)
{
  bool ret = false;

  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNext_I(cmts, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCmts & cmts,
                                       axDbCmtsCurrentCounts & counts)
{
  bool ret = false;

  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNext_I(cmts, row, lengths, col);

  getNextCurrentCounts(counts, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlResultSet::getNext_I(axDbCmts & cmts, MYSQL_ROW & row, 
                              MYSQL_ROWCOL_LEN_t & lengths, int & col)
{
  if (lengths[col]) {
    cmts.m_id = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    cmts.m_id = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    cmts.m_cmtsResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    cmts.m_cmtsResId = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    // cmts.m_lastUpdated = (AX_UINT32) strtoul(row[col], NULL, 10);
  }
  col++;

  if (lengths[col]) {
    copyDbCmtsName(cmts.m_cmtsName, row[col]);
  } else {
    cmts.m_cmtsName[0] = '\0';
  }
  col++;

  if (lengths[col]) {
    copyDbFqdn(cmts.m_fqdn, row[col]);
  } else {
    cmts.m_fqdn[0] = '\0';
  }
  col++;

  if (lengths[col]) {
    copyDbIpAddress(cmts.m_ipAddress, row[col]);
  } else {
    cmts.m_ipAddress[0] = '\0';
  }
  col++;

  if (lengths[col]) {
    cmts.m_cmtsSnmpVersion = (AX_UINT8) atoi(row[col]);
  } else {
    cmts.m_cmtsSnmpVersion = (AX_UINT8) 0;
  }
  col++;

  if (lengths[col]) {
    cmts.m_cmSnmpVersion = (AX_UINT8) atoi(row[col]);
  } else {
    cmts.m_cmSnmpVersion = (AX_UINT8) 0;
  }
  col++;

  if (lengths[col]) {
    cmts.m_mtaSnmpVersion = (AX_UINT8) atoi(row[col]);
  } else {
    cmts.m_mtaSnmpVersion = (AX_UINT8) 0;
  }
  col++;

  if (lengths[col]) {
    cmts.m_onlineState = (AX_UINT8) atoi(row[col]);
  } else {
    cmts.m_onlineState = (AX_UINT8) 0;
  }
  col++;

  if (lengths[col]) {
    cmts.m_ipAddressType = (AX_UINT8) atoi(row[col]);
  } else {
    cmts.m_ipAddressType = (AX_UINT8) 0;
  }
  col++;

  if (lengths[col]) {
    cmts.m_alertLevel = (AX_UINT8) atoi(row[col]);
  } else {
    cmts.m_alertLevel = (AX_UINT8) 0;
  }
  col++;

  if (lengths[col]) {
    cmts.m_deleted = (AX_UINT8) atoi(row[col]);
  } else {
    cmts.m_deleted = (AX_UINT8) 0;
  }
  col++;

}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbChannel & channel)
{
  bool ret = false;

  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNext_I(channel, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbChannel & channel,
                                    axDbChannelCurrentCounts & counts)
{
  bool ret = false;

  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNext_I(channel, row, lengths, col);

  getNextCurrentCounts(counts, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlResultSet::getNext_I(axDbChannel & channel, MYSQL_ROW & row,
                              MYSQL_ROWCOL_LEN_t & lengths, int & col)
{
  if (lengths[col]) {
    channel.m_id = (AX_UINT32) strtoul(row[col], NULL, 10);
  }
  col++;

  if (lengths[col]) {
    channel.m_channelResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  }
  col++;

  if (lengths[col]) {
    channel.m_cmtsResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  }
  col++;

  if (lengths[col]) {
    channel.m_channelIndex = (AX_UINT32) strtoul(row[col], NULL, 10);
  }
  col++;

  if (lengths[col]) {
    // channel.m_lastUpdated = (AX_UINT32) strtoul(row[col], NULL, 10);
  }
  col++;

  if (lengths[col]) {
    copyDbChannelName(channel.m_channelName, row[col]);
  } else {
    channel.m_channelName[0] = '\0';
  }
  col++;

  if (lengths[col]) {
    channel.m_channelType = (AX_UINT8) atoi(row[col]);
  } else {
    channel.m_channelType = (AX_UINT8) 0;
  }
  col++;

  if (lengths[col]) {
    channel.m_alertLevel = (AX_UINT8) atoi(row[col]);
  } else {
    channel.m_alertLevel = (AX_UINT8) 0;
  }
  col++;

  if (lengths[col]) {
    channel.m_deleted = (AX_UINT8) atoi(row[col]);
  } else {
    channel.m_deleted = (AX_UINT8) 0;
  }
  col++;

}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbHfc & hfc)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNext_I(hfc, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbHfc & hfc, 
             axDbHfcCurrentCounts & counts, axDbHfcCurrentStatus & st, 
                                              axDbHfcCurrentTca & tca)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;

  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNext_I(hfc, row, lengths, col);

  getNextCurrentCounts(counts, row, lengths, col);

  getNext_I(st, row, lengths, col);

  getNext_I(tca, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlResultSet::getNext_I(axDbHfc & hfc, MYSQL_ROW & row,
                              MYSQL_ROWCOL_LEN_t & lengths, int & col)
{
  if (lengths[col]) {
    hfc.m_id = (AX_UINT32) strtoul(row[col], NULL, 10);
  }
  col++;

  if (lengths[col]) {
    hfc.m_hfcResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  }
  col++;

  if (lengths[col]) {
    hfc.m_cmtsResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  }
  col++;

  if (lengths[col]) {
    // hfc.m_lastUpdated = (AX_UINT32) strtoul(row[col], NULL, 10);
  }
  col++;

  if (lengths[col]) {
    copyDbHfcName(hfc.m_hfcName, row[col]);
  } else {
    hfc.m_hfcName[0] = '\0';
  }
  col++;

  if (lengths[col]) {
    hfc.m_alertLevel = (AX_UINT8) atoi(row[col]);
  } else {
    hfc.m_alertLevel = (AX_UINT8) 0;
  }
  col++;

  if (lengths[col]) {
    hfc.m_deleted = (AX_UINT8) atoi(row[col]);
  } else {
    hfc.m_deleted = (AX_UINT8) 0;
  }
  col++;

}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCm & cm)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getRow(cm, col, row, lengths);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCm & cm, axDbCmCurrentStatus & st,
                                             axDbCurrentCmPerf & perf)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getRow(cm, col, row, lengths);

  getNext_I(st, row, lengths, col);

  getNext_I(perf, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlResultSet::getRow(axDbCm & cm, int & col, MYSQL_ROW & row, 
                                         MYSQL_ROWCOL_LEN_t & lengths)
{
  if (lengths[col]) {
    cm.m_id = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    cm.m_id = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    cm.m_cmResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    cm.m_cmResId = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    cm.m_cmtsResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    cm.m_cmtsResId = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    cm.m_hfcResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    cm.m_hfcResId = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    cm.m_upstreamChannelResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    cm.m_upstreamChannelResId = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    cm.m_downstreamChannelResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    cm.m_downstreamChannelResId = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    cm.m_modemIndex = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    cm.m_modemIndex = (AX_UINT32) 0;
  }
  col++;

#if 0 // moved do cm_current_status table
  if (lengths[col]) {
    cm.m_onlineOfflineChangeTime = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    cm.m_onlineOfflineChangeTime = (AX_UINT32) 0;
  }
  col++;
#endif

  if (lengths[col]) {
    // cm.m_lastUpdated = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    // cm.m_lastUpdated = (AX_UINT32) strtoul(row[col], NULL, 10);
  }
  col++;

  copyDbMac(cm.m_cmMac, row[col]);
  // printf("MAC: %s - %s\n", cm.m_cmMac, row[col]);
  col++;

  copyDbFqdn(cm.m_fqdn, row[col]);
  col++;

  copyDbIpAddress(cm.m_ipAddress, row[col]);
  col++;

  if (lengths[col]) {
    cm.m_docsisState = (AX_UINT8) atoi(row[col]);
  } else {
    cm.m_docsisState = 0;
  }
  col++;

  if (lengths[col]) {
    cm.m_enduserDeviceType = (AX_UINT8) atoi(row[col]);
  } else {
    cm.m_enduserDeviceType = 0;
  }
  col++;

  if (lengths[col]) {
    cm.m_ipAddressType = (AX_UINT8) atoi(row[col]);
  } else {
    cm.m_ipAddressType = 0;
  }
  col++;

  if (lengths[col]) {
    cm.m_alertLevel = (AX_UINT8) atoi(row[col]);
  } else {
    cm.m_alertLevel = 0;
  }
  col++;

  if (lengths[col]) {
    cm.m_deleted = (AX_UINT8) atoi(row[col]);
  } else {
    cm.m_deleted = 0;
  }
  col++;

  if (lengths[col]) {
    cm.m_isOnline = (AX_UINT8) atoi(row[col]);
  } else {
    cm.m_isOnline = 0;
  }
  col++;

  /*
   * Values from other tables which are pointed to by various res_id's
   */

  /** upstream channel index **/
  if (lengths[col]) {
    cm.m_upstreamChannelIndex = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    cm.m_upstreamChannelIndex = (AX_UINT32) 0;
  }
  col++;

  /** upstream channel name **/
  if (lengths[col]) {
    cm.m_upstreamChannelName = row[col];
  } else {
    cm.m_upstreamChannelName = "";
  }
  col++;

  /** downstream channel index **/
  if (lengths[col]) {
    cm.m_downstreamChannelIndex = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    cm.m_downstreamChannelIndex = (AX_UINT32) 0;
  }
  col++;

  /** downstream channel name **/
  if (lengths[col]) {
    cm.m_downstreamChannelName = row[col];
  } else {
    cm.m_downstreamChannelName = "";
  }
  col++;

  /** HFC name **/
  if (lengths[col]) {
    cm.m_hfcName = row[col];
  } else {
    cm.m_hfcName = "";
  }
  col++;

  return;
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbEmta & emta)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNext_I(emta, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbEmta & emta, 
                                      axDbMtaCurrentAvailability & st)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNext_I(emta, row, lengths, col);

  getNext_I(st, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlResultSet::getNext_I(axDbEmta & emta, MYSQL_ROW & row,
                              MYSQL_ROWCOL_LEN_t & lengths, int & col)
{
  if (lengths[col]) {
    emta.m_id = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    emta.m_id = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    emta.m_mtaResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    emta.m_mtaResId = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    emta.m_cmsResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    emta.m_cmsResId = (AX_UINT32) 0;
  }
  col++;

  // if (lengths[col]) {
  //   emta.m_cm.m_cmResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  // } else {
  //   emta.m_cm.m_cmResId = (AX_UINT32) 0;
  // }
  col++;

#if 0 // moved to mta_current_avail table
  if (lengths[col]) {
    emta.m_availUnavailChangeTime = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    emta.m_availUnavailChangeTime = (AX_UINT32) 0;
  }
  col++;
#endif

  if (lengths[col]) {
    // emta.m_lastUpdated = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    // emta.m_lastUpdated = (AX_UINT32) strtoul(row[col], NULL, 10);
  }
  col++;

  copyDbFqdn(emta.m_fqdn, row[col]);
  col++;

  copyDbIpAddress(emta.m_ipAddress, row[col]);
  col++;

  copyDbMac(emta.m_mtaMac, row[col]);
  col++;

  if (lengths[col]) {
    emta.m_provState = (AX_UINT8) atoi(row[col]);
  } else {
    emta.m_provState = 0;
  }
  col++;

  if (lengths[col]) {
    emta.m_pingState = (AX_UINT8) atoi(row[col]);
  } else {
    emta.m_pingState = 0;
  }
  col++;

  if (lengths[col]) {
    emta.m_ipAddressType = (AX_UINT8) atoi(row[col]);
  } else {
    emta.m_ipAddressType = 0;
  }
  col++;

  if (lengths[col]) {
    emta.m_alertLevel = (AX_UINT8) atoi(row[col]);
  } else {
    emta.m_alertLevel = 0;
  }
  col++;

  if (lengths[col]) {
    emta.m_isProvStatePass = (AX_UINT8) atoi(row[col]);
  } else {
    emta.m_isProvStatePass = 0;
  }
  col++;

  if (lengths[col]) {
    emta.m_isPingFailure = (AX_UINT8) atoi(row[col]);
  } else {
    emta.m_isPingFailure = 0;
  }
  col++;

  if (lengths[col]) {
    emta.m_available = (AX_UINT8) atoi(row[col]);
  } else {
    emta.m_available = 0;
  }
  col++;

  if (lengths[col]) {
    emta.m_deleted = (AX_UINT8) atoi(row[col]);
  } else {
    emta.m_deleted = 0;
  }
  col++;

  getRow(emta.m_cm, col, row, lengths);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCARootAlarm & alarm)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    alarm.m_rootAlarmId = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    alarm.m_rootAlarmId = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    alarm.m_resId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    alarm.m_resId = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    alarm.m_detectionTime = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    alarm.m_detectionTime = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    alarm.m_detectionTimeUSec = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    alarm.m_detectionTimeUSec = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    alarm.m_alarmType = (AX_UINT8) atoi(row[col]);
  } else {
    alarm.m_alarmType = 0;
  }
  col++;

  if (lengths[col]) {
    alarm.m_alarmSubType = (AX_UINT8) atoi(row[col]);
  } else {
    alarm.m_alarmSubType = 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCACurrentAlarm & alarm)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    alarm.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    alarm.m_id = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    alarm.m_rootAlarmId = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    alarm.m_rootAlarmId = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    alarm.m_soakDuration = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    alarm.m_soakDuration = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    alarm.m_alarmState = (AX_UINT16) atoi(row[col]);
  } else {
    alarm.m_alarmState = 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCAHistoricalAlarm & alarm)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    alarm.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    alarm.m_id = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    alarm.m_rootAlarmId = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    alarm.m_rootAlarmId = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    alarm.m_clearedUserId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    alarm.m_clearedUserId = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    alarm.m_soakDuration = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    alarm.m_soakDuration = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    alarm.m_alarmState = (AX_UINT16) atoi(row[col]);
  } else {
    alarm.m_alarmState = (AX_UINT16) 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCmtsCurrentCounts & a)
{
  bool ret = false;

  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNextCurrentCounts(a, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCmtsCountsLog & a)
{
  bool ret = false;

  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    a.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    a.m_id = (AX_UINT64) 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbHfcCurrentCounts & a)
{
  bool ret = false;

  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNextCurrentCounts(a, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbHfcCountsLog & a)
{
  bool ret = false;

  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    a.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    a.m_id = (AX_UINT64) 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbChannelCurrentCounts & a)
{
  bool ret = false;

  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNextCurrentCounts(a, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);

}


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlResultSet::getNextCurrentCounts(axDbAbstractCounts & a, 
             MYSQL_ROW & row, MYSQL_ROWCOL_LEN_t & lengths, int & col)
{
  if (lengths[col]) {
    a.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    a.m_id = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_resId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_resId = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_timeSec = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_timeSec = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_totalCm = atoi(row[col]);
  } else {
    a.m_totalCm = 0;
  }
  col++;

  if (lengths[col]) {
    a.m_onlineCm = atoi(row[col]);
  } else {
    a.m_onlineCm = 0;
  }
  col++;

  if (lengths[col]) {
    a.m_totalMta = atoi(row[col]);
  } else {
    a.m_totalMta = 0;
  }
  col++;

  if (lengths[col]) {
    a.m_availableMta = atoi(row[col]);
  } else {
    a.m_availableMta = 0;
  }
  col++;

}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbChannelCountsLog & a)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    a.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    a.m_id = (AX_UINT64) 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCurrentCmPerf & a)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNext_I(a, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlResultSet::getNext_I(axDbCurrentCmPerf & a, MYSQL_ROW & row,
                              MYSQL_ROWCOL_LEN_t & lengths, int & col)
{
  if (lengths[col]) {
    a.m_id = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_id = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_resId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_resId = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_sumNonTcaTime = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_sumNonTcaTime = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_sumTcaTime = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_sumTcaTime = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_lastLogTime = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_lastLogTime = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_lastTcaChangeTime = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_lastTcaChangeTime = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_t1Timeouts = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_t1Timeouts = 0;
  }
  col++;

  if (lengths[col]) {
    a.m_t2Timeouts = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_t2Timeouts = 0;
  }
  col++;

  if (lengths[col]) {
    a.m_t3Timeouts = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_t3Timeouts = 0;
  }
  col++;

  if (lengths[col]) {
    a.m_t4Timeouts = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_t4Timeouts = 0;
  }
  col++;

  if (lengths[col]) {
    a.m_downstreamSnr = atoi(row[col]);
  } else {
    a.m_downstreamSnr = 0;
  }
  col++;

  if (lengths[col]) {
    a.m_downstreamPower = atoi(row[col]);
  } else {
    a.m_downstreamPower = 0;
  }
  col++;

  if (lengths[col]) {
    a.m_upstreamPower = atoi(row[col]);
  } else {
    a.m_upstreamPower = 0;
  }
  col++;

  if (lengths[col]) {
    a.m_stateChanges = atoi(row[col]);
  } else {
    a.m_stateChanges = 0;
  }
  col++;

}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCmPerfLog & a)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    a.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    a.m_id = (AX_UINT64) 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCmStatusLog & a)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    a.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    a.m_id = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_resId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_resId = (AX_UINT32) 0;
  }
  col++;

  // skip timestamp
  col++;

  if (lengths[col]) {
    a.m_docsisState = atoi(row[col]);
  } else {
    a.m_docsisState = 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbMtaPingStatusLog & a)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    a.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    a.m_id = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_resId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_resId = (AX_UINT32) 0;
  }
  col++;

  // skip timestamp
  col++;

  if (lengths[col]) {
    a.m_pingState = atoi(row[col]);
  } else {
    a.m_pingState = 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbMtaProvStatusLog & a)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    a.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    a.m_id = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_resId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_resId = (AX_UINT32) 0;
  }
  col++;

  // skip timestamp
  col++;

  if (lengths[col]) {
    a.m_provState = atoi(row[col]);
  } else {
    a.m_provState = 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbMtaAvailLog & a)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    a.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    a.m_id = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_resId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    a.m_resId = (AX_UINT32) 0;
  }
  col++;

  // skip timestamp
  col++;

  if (lengths[col]) {
    a.m_availability = atoi(row[col]);
  } else {
    a.m_availability = 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCASoakingAlarm & a)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    a.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    a.m_id = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    a.m_rootAlarmId = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    a.m_rootAlarmId = (AX_UINT64) 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCAAlarmHistory & h)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    h.m_id = (AX_UINT32) strtoull(row[col], NULL, 10);
  } else {
    h.m_id = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    h.m_rootAlarmId = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    h.m_rootAlarmId = (AX_UINT64) 0;
  }
  col++;

  // skip timestamp 
  col++;

  if (lengths[col]) {
    h.m_alarmState = atoi(row[col]);
  } else {
    h.m_alarmState = 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCAAlarmDeviceSoaking & c)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    c.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    c.m_id = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    c.m_rootAlarmId = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    c.m_rootAlarmId = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    c.m_resId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    c.m_resId = (AX_UINT32) 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCAAlarmDevicePostSoak & c)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    c.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    c.m_id = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    c.m_rootAlarmId = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    c.m_rootAlarmId = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    c.m_resId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    c.m_resId = (AX_UINT32) 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCAAlarmCoincidentalSoaking & c)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    c.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    c.m_id = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    c.m_rootAlarmId = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    c.m_rootAlarmId = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    c.m_resId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    c.m_resId = (AX_UINT32) 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCAAlarmCoincidentalPostSoak & c)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    c.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    c.m_id = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    c.m_rootAlarmId = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    c.m_rootAlarmId = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    c.m_resId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    c.m_resId = (AX_UINT32) 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCAAlarmSecondary & as)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    as.m_id = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    as.m_id = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    as.m_rootAlarmId = (AX_UINT64) strtoull(row[col], NULL, 10);
  } else {
    as.m_rootAlarmId = (AX_UINT64) 0;
  }
  col++;

  if (lengths[col]) {
    as.m_preSoakTotal = atoi(row[col]);
  } else {
    as.m_preSoakTotal = 0;
  }
  col++;

  if (lengths[col]) {
    as.m_preSoakCount = atoi(row[col]);
  } else {
    as.m_preSoakCount = 0;
  }
  col++;

  if (lengths[col]) {
    as.m_postSoakTotal = atoi(row[col]);
  } else {
    as.m_postSoakTotal = 0;
  }
  col++;

  if (lengths[col]) {
    as.m_postSoakCount = atoi(row[col]);
  } else {
    as.m_postSoakCount = 0;
  }
  col++;

  if (lengths[col]) {
    as.m_alarmDetectionWindow = atoi(row[col]);
  } else {
    as.m_alarmDetectionWindow = 0;
  }
  col++;

  if (lengths[col]) {
    as.m_alarmThreshold = atoi(row[col]);
  } else {
    as.m_alarmThreshold = 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbAppConfig & ap)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    ap.m_varName = row[col];
  }
  col++;

  if (lengths[col]) {
    ap.m_varValue = row[col];
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbSnmpV2CAttrs & v2)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    v2.m_id = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    v2.m_id = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    v2.m_cmtsResId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    v2.m_cmtsResId = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    copyDbSnmpCommunity(v2.m_readCommunity, row[col]);
  } else {
    v2.m_readCommunity[0] = '\0';
  }
  col++;

  if (lengths[col]) {
    copyDbSnmpCommunity(v2.m_writeCommunity, row[col]);
  } else {
    v2.m_writeCommunity[0] = '\0';
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbMtaCurrentAvailability & ca)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNext_I((axDbAbstractCurrentStatus &)ca, row, lengths, col);  

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbCmCurrentStatus & ca)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNext_I((axDbAbstractCurrentStatus &)ca, row, lengths, col);

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlResultSet::getNext_I(axDbAbstractCurrentStatus & ca,
             MYSQL_ROW & row, MYSQL_ROWCOL_LEN_t & lengths, int & col)
{
  if (lengths[col]) {
    ca.m_id = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    ca.m_id = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    ca.m_resId = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    ca.m_resId = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    ca.m_time1 = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    ca.m_time1 = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    ca.m_time2 = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    ca.m_time2 = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    ca.m_lastLogTime = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    ca.m_lastLogTime = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    ca.m_lastStateChangeTime = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    ca.m_lastStateChangeTime = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    ca.m_stateChanges = (AX_UINT16) atoi(row[col]);
  } else {
    ca.m_stateChanges = 0;
  }
  col++;

  if (lengths[col]) {
    ca.m_currentValue = (AX_UINT16) atoi(row[col]);
  } else {
    ca.m_currentValue = 0;
  }
  col++;

}

//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbSummaryFlags & summ)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    summ.m_overallSummStarted = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_overallSummStarted = 0;
  }

  if (lengths[col]) {
    summ.m_overallSummEnded = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_overallSummEnded = 0;
  }

  if (lengths[col]) {
    summ.m_dbCopyStarted = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_dbCopyStarted = 0;
  }

  if (lengths[col]) {
    summ.m_dbCopyEnded = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_dbCopyEnded = 0;
  }

  if (lengths[col]) {
    summ.m_cmtsCountsSummStarted = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_cmtsCountsSummStarted = 0;
  }

  if (lengths[col]) {
    summ.m_cmtsCountsSummEnded = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_cmtsCountsSummEnded = 0;
  }

  if (lengths[col]) {
    summ.m_hfcCountsSummStarted = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_hfcCountsSummStarted = 0;
  }

  if (lengths[col]) {
    summ.m_hfcCountsSummEnded = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_hfcCountsSummEnded = 0;
  }

  if (lengths[col]) {
    summ.m_channelCountsSummStarted = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_channelCountsSummStarted = 0;
  }

  if (lengths[col]) {
    summ.m_channelCountsSummEnded = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_channelCountsSummEnded = 0;
  }

  if (lengths[col]) {
    summ.m_cmPerfSummStarted = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_cmPerfSummStarted = 0;
  }

  if (lengths[col]) {
    summ.m_cmPerfSummEnded = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_cmPerfSummEnded = 0;
  }

  if (lengths[col]) {
    summ.m_cmStatusSummStarted = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_cmStatusSummStarted = 0;
  }

  if (lengths[col]) {
    summ.m_cmStatusSummEnded = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_cmStatusSummEnded = 0;
  }

  if (lengths[col]) {
    summ.m_mtaAvailSummStarted = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_mtaAvailSummStarted = 0;
  }

  if (lengths[col]) {
    summ.m_mtaAvailSummEnded = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_mtaAvailSummEnded = 0;
  }

  if (lengths[col]) {
    summ.m_alarmSummStarted = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_alarmSummStarted = 0;
  }

  if (lengths[col]) {
    summ.m_alarmSummEnded = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_alarmSummEnded = 0;
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbLastCompSummary & summ)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    summ.m_month = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_month = 0;
  }

  if (lengths[col]) {
    summ.m_day = (AX_UINT8) atoi(row[col]);
  } else {
    summ.m_day = 0;
  }

  if (lengths[col]) {
    summ.m_year = (AX_UINT16) atoi(row[col]);
  } else {
    summ.m_year = 0;
  }

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbHfcCurrentStatus & hfcCurrSt)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNext_I((axDbAbstractCurrentStatus &)hfcCurrSt, row, lengths, col);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlResultSet::getNext_I(axDbHfcCurrentStatus & st,
             MYSQL_ROW & row, MYSQL_ROWCOL_LEN_t & lengths, int & col)
{
  getNext_I((axDbAbstractCurrentStatus &)st, row, lengths, col);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbHfcCurrentTca & hfcCurrTca)
{
  bool ret = false;
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  getNext_I((axDbAbstractCurrentStatus &)hfcCurrTca, row, lengths, col);

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
void
axDbMySqlResultSet::getNext_I(axDbHfcCurrentTca & tca,
             MYSQL_ROW & row, MYSQL_ROWCOL_LEN_t & lengths, int & col)
{
  getNext_I((axDbAbstractCurrentStatus &)tca, row, lengths, col);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbHfcStatusLog & log)
{
  bool ret = false;

#if 0
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

EXIT_LABEL:
#endif

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbHfcTcaLog & log)
{
  bool ret = false;

#if 0
  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

EXIT_LABEL:
#endif

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbAbstractTopoLookup & topoLookup)
{
  bool ret = false;

  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    topoLookup.m_id = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    topoLookup.m_id = (AX_UINT32) 0;
  }
  col++;

  if (lengths[col]) {
    topoLookup.m_strId = row[col];
  } else {
    topoLookup.m_strId = "";
  }
  col++;

  if (lengths[col]) {
    topoLookup.m_topoContainerId = (AX_UINT16) atoi(row[col]);
  } else {
    topoLookup.m_topoContainerId = 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbLocalSystem & ls)
{
  bool ret = false;

  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    ls.m_systemType = atoi(row[col]);
  } else {
    ls.m_systemType = 0;
  }
  col++;

  if (lengths[col]) {
    ls.m_systemName = row[col];
  } else {
    ls.m_systemName = "";
  }
  col++;

  if (lengths[col]) {
    ls.m_parentHost = row[col];
  } else {
    ls.m_parentHost = "";
  }
  col++;

  if (lengths[col]) {
    ls.m_parentIpType = atoi(row[col]);
  } else {
    ls.m_parentIpType = 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbAbstractTopoContainer & c)
{
  bool ret = false;

  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    c.m_id = (AX_UINT16) atoi(row[col]);
  } else {
    c.m_id = (AX_UINT16) 0;
  }
  col++;

  // skip last_updated field
  col++;

  if (lengths[col]) {
    c.m_name = row[col];
  } else {
    c.m_name = "";
  }
  col++;

  if (lengths[col]) {
    c.m_host = row[col];
  } else {
    c.m_host = "";
  }
  col++;

  if (lengths[col]) {
    c.m_ipAddressType = (AX_UINT16) atoi(row[col]);
  } else {
    c.m_ipAddressType = (AX_UINT16) 0;
  }
  col++;

  if (lengths[col]) {
    c.m_isDeleted = (AX_UINT8) atoi(row[col]);
  } else {
    c.m_isDeleted = (AX_UINT8) 0;
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


//********************************************************************
// method:
//********************************************************************
bool
axDbMySqlResultSet::getNext(axDbEmtaSecondary & ms)
{
  bool ret = false;

  int col = 0;

  MYSQL_ROWCOL_LEN_t lengths;
  MYSQL_ROW row = mysql_fetch_row(m_rsHandle);
  if (!row) {
    goto EXIT_LABEL;
  }

  lengths = mysql_fetch_lengths(m_rsHandle);

  if (lengths[col]) {
    ms.m_id = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    ms.m_id = (AX_UINT16) 0;
  }
  col++;

  if (lengths[col]) {
    ms.m_emta_res_id = (AX_UINT32) strtoul(row[col], NULL, 10);
  } else {
    ms.m_emta_res_id = (AX_UINT16) 0;
  }
  col++;

  if (lengths[col]) {
    ms.m_phone1 = row[col];
  } else {
    ms.m_phone1 = "";
  }
  col++;

  if (lengths[col]) {
    ms.m_phone2 = row[col];
  } else {
    ms.m_phone2 = "";
  }
  col++;

  ret = true;

EXIT_LABEL:

  return (ret);
}


