
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axSnmpCmtsChannelReqType.hpp"
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
axSnmpCmtsChannelReqType * axSnmpCmtsChannelReqType::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axSnmpCmtsChannelReqType::axSnmpCmtsChannelReqType()
{
  int i = 0;

  // NEW: net-snmp "oid" based oid's
  if (snmp_parse_oid(SNMP_CMTS_CHNL_IF_INDEX_OID, 
       m_cmtsOids.oids[i].nativeOid, &m_cmtsOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CHNL_IF_INDEX_OID);
  }

  if (snmp_parse_oid(SNMP_CMTS_CHNL_IF_TYPE_OID,
       m_cmtsOids.oids[i].nativeOid, &m_cmtsOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CHNL_IF_TYPE_OID);
  }

  if (snmp_parse_oid(SNMP_CMTS_CHNL_IF_DESCR_OID,
       m_cmtsOids.oids[i].nativeOid, &m_cmtsOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_CMTS_CHNL_IF_DESCR_OID);
  }

  m_cmtsOids.numOids = i;

  setMaxRepetitions(SNMP_MAX_RESULT_ROWS_PER_VAR);
}


//********************************************************************
// destructor:
//********************************************************************
axSnmpCmtsChannelReqType::~axSnmpCmtsChannelReqType()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axSnmpCmtsChannelReqType::axSnmpCmtsChannelReqType()
// {
// }


//********************************************************************
// method:
//********************************************************************
axSnmpCmtsChannelReqType *
axSnmpCmtsChannelReqType::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axSnmpCmtsChannelReqType();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
void
axSnmpCmtsChannelReqType::getOids(axSnmpCmtsChannelOids_s & out)
{
  out = m_cmtsOids;
}


//********************************************************************
// method:
//********************************************************************
axSnmpOidsBase_s *
axSnmpCmtsChannelReqType::getOids(void)
{
  return (&m_cmtsOids);
}


