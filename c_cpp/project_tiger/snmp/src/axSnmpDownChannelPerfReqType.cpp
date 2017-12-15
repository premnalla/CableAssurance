
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

//********************************************************************
// include files
//********************************************************************
#include "axSnmpDownChannelPerfReqType.hpp"
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
axSnmpDownChannelPerfReqType * axSnmpDownChannelPerfReqType::m_instance = NULL;

//********************************************************************
// forward declerations
//********************************************************************


//********************************************************************
// default constructor:
//********************************************************************
axSnmpDownChannelPerfReqType::axSnmpDownChannelPerfReqType()
{
  int i = 0;

  if (snmp_parse_oid(SNMP_DWN_CHNL_POWER_OID, 
       m_channelOids.oids[i].nativeOid, &m_channelOids.oids[i].nativeOidLen)) {
    i++;
  } else {
    snmp_perror(SNMP_DWN_CHNL_POWER_OID);
  }

  m_channelOids.numOids = i;

  setMaxRepetitions(SNMP_MAX_CHNL_PERF_ROWS_PER_VAR);
}


//********************************************************************
// destructor:
//********************************************************************
axSnmpDownChannelPerfReqType::~axSnmpDownChannelPerfReqType()
{
}


//********************************************************************
// data constructor:
//********************************************************************
// axSnmpDownChannelPerfReqType::axSnmpDownChannelPerfReqType()
// {
// }


//********************************************************************
// method:
//********************************************************************
axSnmpDownChannelPerfReqType *
axSnmpDownChannelPerfReqType::getInstance(void)
{
  if (!m_instance) {
    m_instance = new axSnmpDownChannelPerfReqType();
  }

  return (m_instance);
}


//********************************************************************
// method:
//********************************************************************
axSnmpOidsBase_s *
axSnmpDownChannelPerfReqType::getOids(void)
{
  return (&m_channelOids);
}


