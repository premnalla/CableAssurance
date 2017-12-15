
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axSnmpCmPerfCmReqType.hpp"
#include "axSnmpUtils.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// constants
//********************************************************************

//********************************************************************
// static member initialization
//********************************************************************
axSnmpCmPerfCmReqType * axSnmpCmPerfCmReqType::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axSnmpCmPerfCmReqType::axSnmpCmPerfCmReqType()
{
  int i = 0;

  if (snmp_parse_oid(SNMP_CM_PERF_CM_DN_SNR_OID, 
       m_cmOids.oids[i].nativeOid, &m_cmOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CM_PERF_CM_DN_SNR_OID);
  }

  if (snmp_parse_oid(SNMP_CM_PERF_CM_DN_PWR_OID,
       m_cmOids.oids[i].nativeOid, &m_cmOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CM_PERF_CM_DN_PWR_OID);
  }

  if (snmp_parse_oid(SNMP_CM_PERF_CM_UP_SNR_OID,
       m_cmOids.oids[i].nativeOid, &m_cmOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CM_PERF_CM_UP_SNR_OID);
  }

  if (snmp_parse_oid(SNMP_CM_PERF_CM_UP_SNR_OID,
       m_cmOids.oids[i].nativeOid, &m_cmOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CM_PERF_CM_UP_SNR_OID);
  }

  if (snmp_parse_oid(SNMP_CM_PERF_CM_T1_TO_OID,
       m_cmOids.oids[i].nativeOid, &m_cmOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CM_PERF_CM_T1_TO_OID);
  }

  if (snmp_parse_oid(SNMP_CM_PERF_CM_T2_TO_OID,
       m_cmOids.oids[i].nativeOid, &m_cmOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CM_PERF_CM_T2_TO_OID);
  }

  if (snmp_parse_oid(SNMP_CM_PERF_CM_T3_TO_OID,
       m_cmOids.oids[i].nativeOid, &m_cmOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CM_PERF_CM_T3_TO_OID);
  }

  if (snmp_parse_oid(SNMP_CM_PERF_CM_T4_TO_OID,
       m_cmOids.oids[i].nativeOid, &m_cmOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CM_PERF_CM_T4_TO_OID);
  }

  m_cmOids.numOids = i;

  setMaxRepetitions(1);
}


//********************************************************************
// destructor:
//********************************************************************
axSnmpCmPerfCmReqType::~axSnmpCmPerfCmReqType()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axSnmpCmPerfCmReqType::axSnmpCmPerfCmReqType()
// {
// }


//********************************************************************
// method:
//********************************************************************
axSnmpCmPerfCmReqType *
axSnmpCmPerfCmReqType::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axSnmpCmPerfCmReqType();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
axSnmpOidsBase_s *
axSnmpCmPerfCmReqType::getOids(void)
{
  return (&m_cmOids);
}


