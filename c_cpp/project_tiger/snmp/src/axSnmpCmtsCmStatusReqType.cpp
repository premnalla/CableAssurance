
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axSnmpCmtsCmStatusReqType.hpp"
#include "axSnmpUtils.hpp"
#include "axSnmpDataTypes.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axSnmpCmtsCmStatusReqType * axSnmpCmtsCmStatusReqType::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axSnmpCmtsCmStatusReqType::axSnmpCmtsCmStatusReqType()
{
  int i = 0;

  if (snmp_parse_oid(SNMP_CMTS_CM_INDEX_OID, 
       m_cmtsOids.oids[i].nativeOid, &m_cmtsOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_INDEX_OID);
  }

  if (snmp_parse_oid(SNMP_CMTS_CM_MACADDR_OID, 
       m_cmtsOids.oids[i].nativeOid, &m_cmtsOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_MACADDR_OID);
  }

  if (snmp_parse_oid(SNMP_CMTS_CM_IPADDR_OID,
       m_cmtsOids.oids[i].nativeOid, &m_cmtsOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_IPADDR_OID);
  }

  if (snmp_parse_oid(SNMP_CMTS_CM_DWNCHNL_OID,
       m_cmtsOids.oids[i].nativeOid, &m_cmtsOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_DWNCHNL_OID);
  }

  if (snmp_parse_oid(SNMP_CMTS_CM_UPCHNL_OID,
       m_cmtsOids.oids[i].nativeOid, &m_cmtsOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_UPCHNL_OID);
  }

  if (snmp_parse_oid(SNMP_CMTS_CM_STATUS_OID,
       m_cmtsOids.oids[i].nativeOid, &m_cmtsOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_STATUS_OID);
  }

  if (snmp_parse_oid(SNMP_CMTS_CM_UP_PWR_OID,
       m_cmtsOids.oids[i].nativeOid, &m_cmtsOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_UP_PWR_OID);
  }

  if (snmp_parse_oid(SNMP_CMTS_CM_SNR_OID,
       m_cmtsOids.oids[i].nativeOid, &m_cmtsOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_SNR_OID);
  }

  m_cmtsOids.numOids = i;

  setMaxRepetitions(SNMP_MAX_RESULT_ROWS_PER_VAR);
}


//********************************************************************
// destructor:
//********************************************************************
axSnmpCmtsCmStatusReqType::~axSnmpCmtsCmStatusReqType()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axSnmpCmtsCmStatusReqType::axSnmpCmtsCmStatusReqType()
// {
// }


//********************************************************************
// method:
//********************************************************************
axSnmpCmtsCmStatusReqType *
axSnmpCmtsCmStatusReqType::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axSnmpCmtsCmStatusReqType();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpCmtsCmStatusReqType::getOids(axSnmpCmtsCmOids_s & out)
{
  out = m_cmtsOids;
}


//********************************************************************
// method:
//********************************************************************
axSnmpOidsBase_s *
axSnmpCmtsCmStatusReqType::getOids(void)
{
  return (&m_cmtsOids);
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpCmtsCmStatusReqType::getOids(AX_INT32 modemIndex, 
                                             axSnmpCmtsCmOids_s & out)
{
  char buffer[128];
  int i = 0;

  sprintf(buffer, "%s.%d", SNMP_CMTS_CM_INDEX_OID, modemIndex);
  if (snmp_parse_oid(buffer,
       out.oids[i].nativeOid, &out.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_INDEX_OID);
  }

  sprintf(buffer, "%s.%d", SNMP_CMTS_CM_MACADDR_OID, modemIndex);
  if (snmp_parse_oid(buffer,
       out.oids[i].nativeOid, &out.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_MACADDR_OID);
  }

  sprintf(buffer, "%s.%d", SNMP_CMTS_CM_IPADDR_OID, modemIndex);
  if (snmp_parse_oid(buffer,
       out.oids[i].nativeOid, &out.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_IPADDR_OID);
  }

  sprintf(buffer, "%s.%d", SNMP_CMTS_CM_DWNCHNL_OID, modemIndex);
  if (snmp_parse_oid(buffer,
       out.oids[i].nativeOid, &out.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_DWNCHNL_OID);
  }

  sprintf(buffer, "%s.%d", SNMP_CMTS_CM_UPCHNL_OID, modemIndex);
  if (snmp_parse_oid(buffer,
       out.oids[i].nativeOid, &out.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_UPCHNL_OID);
  }

  sprintf(buffer, "%s.%d", SNMP_CMTS_CM_STATUS_OID, modemIndex);
  if (snmp_parse_oid(buffer,
       out.oids[i].nativeOid, &out.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_STATUS_OID);
  }

  sprintf(buffer, "%s.%d", SNMP_CMTS_CM_UP_PWR_OID, modemIndex);
  if (snmp_parse_oid(buffer,
       out.oids[i].nativeOid, &out.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_UP_PWR_OID);
  }

  sprintf(buffer, "%s.%d", SNMP_CMTS_CM_SNR_OID, modemIndex);
  if (snmp_parse_oid(buffer,
       out.oids[i].nativeOid, &out.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CM_SNR_OID);
  }

  out.numOids = i;
}


