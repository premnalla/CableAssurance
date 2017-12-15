/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axSnmpDataTypes.hpp      
 *
 *************************************************************************
 * Modification History:
 *			
 *************************************************************************
 * Description:
 *
 *************************************************************************
 * Modification History:
 *
 *    08/18/06    Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

#ifndef _axSnmpDataTypes_hpp_
#define _axSnmpDataTypes_hpp_

/*************************************************************************
 * include files
 *************************************************************************/
#include <net-snmp/net-snmp-config.h>
#include <net-snmp/net-snmp-includes.h>
#include <string.h>
#include "axAll.h"
#include "axInternalDsTypes.hpp"


/*************************************************************************
 * Forward Declerations
 *************************************************************************/


/*************************************************************************
 * Global definitions/macros
 *************************************************************************/
#define SNMP_MAX_CMTS_CM_STATUS_TBL_OIDS    8
#define SNMP_MAX_OID_STR_LEN              128
#define SNMP_MAX_OID_LEN                   16      /* number of u_long's */
#define SNMP_MAX_RESULT_ROWS_PER_VAR      100

#define SNMP_MAX_CMTS_CHNL_TBL_OIDS         4
#define SNMP_MAX_HR_MTA_POLL_OIDS           4
#define SNMP_MAX_CM_PERF_CM_OIDS           10

//
#define SNMP_MAX_CHNL_PERF_OIDS             1
#define SNMP_MAX_CHNL_PERF_ROWS_PER_VAR   500

#define SNMP_CMTS_DOCSIS_CAPABILITY_OID   "1.3.6.1.2.1.10.127.1.1.5"
#define SNMP_IFSTACK_LOWER_LAYER_BASE_OID "1.3.6.1.2.1.31.1.2.1.2."

/*
 * DOCSIS STATES
 */
#define DOCSIS_CM_STATE_ONLINE 6

/*
 * DOCSIS capability
 */
#define DOCSIS_CAPABILITY_1_0  1
#define DOCSIS_CAPABILITY_1_1  2
#define DOCSIS_CAPABILITY_2_0  3

/*
 * ifType values
 */
#define IF_TYPE_DOWN_STREAM  128
#define IF_TYPE_UP_STREAM_1X 129
#define IF_TYPE_UP_STREAM_2X 205

/*
 * IP Address Versions
 */
#define AX_SNMP_IPADDR_TYPE_NULL                   AX_INT_IPADDR_TYPE_NULL
#define AX_SNMP_IPADDR_TYPE_IPv4                   AX_INT_IPADDR_TYPE_IPv4
#define AX_SNMP_IPADDR_TYPE_IPv6                   AX_INT_IPADDR_TYPE_IPv6

/*************************************************************************
 * Global Type definitions
 *************************************************************************/


/***********************************************************/
/****************** Request OID's **************************/
/***********************************************************/

struct axSnmpNativeOid_s
{
  axSnmpNativeOid_s() : nativeOidLen(SNMP_MAX_OID_LEN)
  {
    memset(nativeOid, '\0', sizeof(nativeOid));
  }
  size_t  nativeOidLen;
  oid     nativeOid[SNMP_MAX_OID_LEN];
};


struct axSnmpOidsBase_s 
{
  axSnmpOidsBase_s() : numOids(0)
  {
  }

  virtual ~axSnmpOidsBase_s()
  {
  }

  virtual axSnmpNativeOid_s * getNativeOids(void)=0;

#ifdef BIT_64
  AX_INT8  numOids;
  AX_INT8  unused1;
  AX_INT16 unused2;
  AX_INT32 unused3;
#else
  AX_INT8  numOids;
  AX_INT8  unused1;
  AX_INT16 unused2;
#endif

};


/***********************************************************/
/****************** CMTS CM status OID's *******************/
/***********************************************************/

struct axSnmpCmtsCmOids_s : public axSnmpOidsBase_s
{
  axSnmpCmtsCmOids_s()
  {
  }

  virtual axSnmpNativeOid_s * getNativeOids(void)
  {
    return (&oids[0]);
  }

  axSnmpNativeOid_s oids[SNMP_MAX_CMTS_CM_STATUS_TBL_OIDS];

  static const AX_INT8 maxOids = SNMP_MAX_CMTS_CM_STATUS_TBL_OIDS;
};


/***********************************************************/
/****************** CMTS ifTable status OID's **************/
/***********************************************************/

struct axSnmpCmtsChannelOids_s : public axSnmpOidsBase_s
{
  axSnmpCmtsChannelOids_s()
  {
  }

  virtual axSnmpNativeOid_s * getNativeOids(void)
  {
    return (&oids[0]);
  }

  axSnmpNativeOid_s oids[SNMP_MAX_CMTS_CHNL_TBL_OIDS];

  static const AX_INT8 maxOids = SNMP_MAX_CMTS_CHNL_TBL_OIDS;
};


/***********************************************************/
/****************** HR MTA Poll OID's **********************/
/***********************************************************/

struct axSnmpHRMtaPollOids_s : public axSnmpOidsBase_s
{
  axSnmpHRMtaPollOids_s()
  {
  }

  virtual axSnmpNativeOid_s * getNativeOids(void)
  {
    return (&oids[0]);
  }

  axSnmpNativeOid_s oids[SNMP_MAX_HR_MTA_POLL_OIDS];

  static const AX_INT8 maxOids = SNMP_MAX_HR_MTA_POLL_OIDS;
};


/***********************************************************/
/****************** Generic Get Next OID's *****************/
/***********************************************************/

struct axSnmpGenericGetNextOid_s : public axSnmpOidsBase_s
{
  axSnmpGenericGetNextOid_s()
  {
  }

  virtual axSnmpNativeOid_s * getNativeOids(void)
  {
    return (&oids[0]);
  }

  axSnmpNativeOid_s oids[1];

  static const AX_INT8 maxOids = 1;
};


/***********************************************************/
/*********************** CM Perf OID's *********************/
/***********************************************************/

struct axSnmpCmPerfCmGetNextOid_s : public axSnmpOidsBase_s
{
  axSnmpCmPerfCmGetNextOid_s()
  {
  }

  virtual axSnmpNativeOid_s * getNativeOids(void)
  {
    return (&oids[0]);
  }

  axSnmpNativeOid_s oids[SNMP_MAX_CM_PERF_CM_OIDS];

  static const AX_INT8 maxOids = SNMP_MAX_CM_PERF_CM_OIDS;
};


/***********************************************************/
/******************** Channel Perf OID's *******************/
/***********************************************************/

struct axSnmpDownChannelPerfOid_s : public axSnmpOidsBase_s
{
  axSnmpDownChannelPerfOid_s()
  {
  }

  virtual axSnmpNativeOid_s * getNativeOids(void)
  {
    return (&oids[0]);
  }

  axSnmpNativeOid_s oids[SNMP_MAX_CHNL_PERF_OIDS];

  static const AX_INT8 maxOids = SNMP_MAX_CHNL_PERF_OIDS;
};


/*************************************************************/
/****************** Response Decode Result Code **************/
/*************************************************************/

struct axSnmpRespDecodeRC_s 
{
  axSnmpRespDecodeRC_s() : 
    hasData(false), moreDataPossible(false)
  {
  }
  void init(void)
  {
    hasData = false;
    moreDataPossible = false;
  }
  bool hasData;
  bool moreDataPossible;
};


/***********************************************************/
/****************** Result Values **************************/
/***********************************************************/

struct axSnmpValueBase_s
{
  axSnmpValueBase_s() : numValues(0)
  {
  }

#ifdef BIT_64
  AX_UINT8  numValues;
  AX_UINT8  unused1;
  AX_UINT16 unused2;
  AX_UINT32 unused3;
#else
  AX_UINT8  numValues;
  AX_UINT8  unused1;
  AX_UINT16 unused2;
#endif

};

/*********************************************************/
/********* HR Cmts Cmts status Poll Result ***************/
/*********************************************************/

struct axSnmpCmtsCmResultRow_s
{
  AX_MAC_t  mac;
  AX_INT8   ipAddress[INT_IP_ADDRESS_SIZE];
  AX_INT32  downstreamChannelIndex;
  AX_INT32  upstreamChannelIndex;
  AX_INT32  modemIndex;
  AX_UINT16 upstreamSNR;
  AX_UINT16 upstreamPower;
  AX_INT8   docsisState;
  AX_INT8   ipAddressType;

  axSnmpCmtsCmResultRow_s() : 
    downstreamChannelIndex(0), upstreamChannelIndex(0), modemIndex(0), 
    upstreamSNR(0), upstreamPower(0), docsisState(0), ipAddressType(0)
  {
    mac[0] = '\0';
    ipAddress[0] = '\0';
  }

};


struct axSnmpCmtsCmResultValues_s : public axSnmpValueBase_s
{
  axSnmpCmtsCmResultValues_s() {}

  axSnmpCmtsCmResultRow_s values[SNMP_MAX_RESULT_ROWS_PER_VAR];

  static const AX_INT16 maxValues = SNMP_MAX_RESULT_ROWS_PER_VAR;
};

/*********************************************************/
/************ HR Channel Poll ****************************/
/*********************************************************/

struct axSnmpCmtsChannelResultRow_s
{
  axSnmpCmtsChannelResultRow_s() :
    ifIndex(0), ifType(0)
  {
    ifDescription[0] = '\0';
  }

  AX_INT32 ifIndex;
  AX_INT32 ifType;
  AX_INT8  ifDescription[INT_CHANNEL_NAME_SIZE];
};


struct axSnmpCmtsChannelResultValues_s : public axSnmpValueBase_s
{
  axSnmpCmtsChannelResultValues_s() {}

  axSnmpCmtsChannelResultRow_s values[SNMP_MAX_RESULT_ROWS_PER_VAR];

  static const AX_INT16 maxValues = SNMP_MAX_RESULT_ROWS_PER_VAR;
};


/*********************************************************/
/******************* HR MTA Poll Result ******************/
/*********************************************************/

struct axSnmpHRMtaPollResultRow_s
{
  AX_UINT8 provisionedStatus;

  axSnmpHRMtaPollResultRow_s() :
    provisionedStatus(0)
  {
  }
};


struct axSnmpHRMtaPollResultValues_s : public axSnmpValueBase_s
{
  axSnmpHRMtaPollResultValues_s() {}

  axSnmpHRMtaPollResultRow_s values[1];

  static const AX_INT16 maxValues = 1;
};


/*********************************************************/
/******************* Generic Get Next Result  ************/
/*********************************************************/

struct axSnmpGenericGetNextResultRow_s
{
  union data_u {
    AX_INT32  intVal;
    AX_INT8 * strPtr;
  } data;
  AX_UINT32 dataType;

  bool isDataString(void)
  {
    return ((dataType==ASN_OCTET_STR ? true : false));
  }

  bool isDataInteger(void)
  {
    return ((dataType==ASN_INTEGER ? true : false));
  }

  axSnmpGenericGetNextResultRow_s() :
    dataType(0)
  {
    memset(&data, '\0', sizeof(data));
  }

  virtual ~axSnmpGenericGetNextResultRow_s()
  {
    if (isDataString() && data.strPtr) {
      free(data.strPtr);
    }
  }

};

struct axSnmpGenericGetResultValue_s : public axSnmpValueBase_s
{
  axSnmpGenericGetResultValue_s() {}

  axSnmpGenericGetNextResultRow_s values[1];

  static const AX_INT16 maxValues = 1;
};


/*********************************************************/
/******************* Generic Get Next Result  ************/
/*********************************************************/

struct axSnmpCmPerfCmResultRow_s
{
  AX_UINT16  downstreamSNR;
  AX_UINT16  downstreamPower;
  AX_UINT16  upstreamPower;
  AX_UINT16  unused1;

  AX_UINT32  t1Timeouts;
  AX_UINT32  t2Timeouts;
  AX_UINT32  t3Timeouts;
  AX_UINT32  t4Timeouts;

  axSnmpCmPerfCmResultRow_s() :
    downstreamSNR(0), downstreamPower(0), upstreamPower(0),
    unused1(0), t1Timeouts(0), t2Timeouts(0), t3Timeouts(0),
    t4Timeouts(0)
  {
  }

};


struct axSnmpCmPerfCmResultValues_s : public axSnmpValueBase_s
{
  axSnmpCmPerfCmResultValues_s() {}

  axSnmpCmPerfCmResultRow_s values[1];

  static const AX_INT16 maxValues = 1;
};

/*********************************************************/
/************ Channel Perf Poll **************************/
/*********************************************************/

struct axSnmpDownChannelPerfResultRow_s
{
  AX_UINT32 ifIndex;
  AX_UINT16 downstreamPower;

  axSnmpDownChannelPerfResultRow_s() :
    ifIndex(0), downstreamPower(0)
  {
  }
};


struct axSnmpDownChannelPerfResultValues_s : public axSnmpValueBase_s
{
  axSnmpDownChannelPerfResultValues_s() {}

  axSnmpDownChannelPerfResultRow_s values[SNMP_MAX_CHNL_PERF_ROWS_PER_VAR];

  static const AX_INT16 maxValues = SNMP_MAX_CHNL_PERF_ROWS_PER_VAR;
};


/*************************************************************************
 * Extern Variables, Structures, etc.
 *************************************************************************/


/*************************************************************************
 * Extern functions
 *************************************************************************/


#endif /* #ifndef _axSnmpDataTypes_hpp_ */
