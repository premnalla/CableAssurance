
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbDataTypes_hpp_
#define _axDbDataTypes_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axInternalDsTypes.hpp"
#include "axVendorSpecificDb.hpp"

//********************************************************************
// definitions/macros
//********************************************************************
//
#define DB_CMTS_NAME_SIZE  80

//
#define DB_FQDN_SIZE  80

//
#define DB_MAX_SNMP_COMMUNITY_SIZE  32

//
#define DB_IP_ADDRESS_SIZE  40

//
#define DB_CHANNEL_NAME_SIZE  32

//
#define DB_HFC_NAME_SIZE  40

//********************************************************************
// forward declerations
//********************************************************************

//********************************************************************
// Database Types
//********************************************************************

//
typedef INTDS_RESID_t DB_RESID_t;

//
typedef AX_UINT32  DB_OBJ_AUTO_INC_ID_t;
typedef AX_UINT64  DB_LOG_AUTO_INC_ID_t;

//
typedef AX_UINT8  DB_RESTYPE_t;

//
typedef AX_INT8 DB_FQDN_t[DB_FQDN_SIZE];

//
typedef AX_INT8 DB_IP_ADDRESS_t[DB_IP_ADDRESS_SIZE];

//
typedef AX_INT8 DB_SNMP_COMMUNITY_t[DB_MAX_SNMP_COMMUNITY_SIZE];

//
typedef AX_INT8 DB_CMTS_NAME_t[DB_CMTS_NAME_SIZE];

//
typedef AX_MAC_t DB_MAC_t;

//
typedef AX_UINT32 DB_CM_INDEX_t;

//
typedef AX_UINT8 DB_DOCSIS_STATUS_t;
typedef AX_UINT8 DB_PING_STATUS_t;
typedef AX_UINT8 DB_PROV_STATUS_t;
typedef AX_UINT8 DB_AVAILABLE_STATUS_t;

//
typedef AX_UINT32 DB_UTF_TIME_t;

// moved to axMySqlDb.hpp
// typedef MYSQL_TIME DB_UPDATE_TIME_t;

//
typedef AX_INT8 DB_CHANNEL_NAME_t[DB_CHANNEL_NAME_SIZE];

//
typedef AX_INT8 DB_HFC_NAME_t[DB_HFC_NAME_SIZE];

//********************************************************************
// Enumerated types
//********************************************************************

/*
 * Resource Types
 */
#define DB_REST_UNKNOWN                            ((DB_RESTYPE_t)0x01)
#define DB_REST_ENTERPRISE                         ((DB_RESTYPE_t)0x02)
#define DB_REST_REGION                             ((DB_RESTYPE_t)0x03)
#define DB_REST_MARKET                             ((DB_RESTYPE_t)0x04)
#define DB_REST_BLADE                              ((DB_RESTYPE_t)0x05)
#define DB_REST_CMTS                               ((DB_RESTYPE_t)0x06)
#define DB_REST_CMS                                ((DB_RESTYPE_t)0x07)
#define DB_REST_CHNL                               ((DB_RESTYPE_t)0x08)
#define DB_REST_HFCG                               ((DB_RESTYPE_t)0x09)
#define DB_REST_CM                                 ((DB_RESTYPE_t)0x0A)
#define DB_REST_EMTA                               ((DB_RESTYPE_t)0x0B)
#define DB_REST_MTA                                ((DB_RESTYPE_t)0x0C)


/*
 * Channel Types
 */
#define DB_CHANNEL_TYPE_UNKNOWN             AX_INT_CHANNEL_TYPE_UNKNOWN
#define DB_CHANNEL_TYPE_UPSTREAM           AX_INT_CHANNEL_TYPE_UPSTREAM
#define DB_CHANNEL_TYPE_DOWNSTREAM       AX_INT_CHANNEL_TYPE_DOWNSTREAM


/*
 * End-User Device Types
 */
#define DB_EU_DEVIE_TYPE_UNKNOWN           AX_INT_EU_DEVIE_TYPE_UNKNOWN
#define DB_EU_DEVIE_TYPE_CM                     AX_INT_EU_DEVIE_TYPE_CM
#define DB_EU_DEVIE_TYPE_EMTA                 AX_INT_EU_DEVIE_TYPE_EMTA

/*
 * IP Address Types 
 */
#define DB_IPADDR_TYPE_NULL                     AX_INT_IPADDR_TYPE_NULL
#define DB_IPADDR_TYPE_IPv4                     AX_INT_IPADDR_TYPE_IPv4
#define DB_IPADDR_TYPE_IPv6                     AX_INT_IPADDR_TYPE_IPv6


/*
 * Alarm States
 */
#define DB_ALARM_STATE_UNKNOWN                                     0x00
#define DB_ALARM_STATE_SOAKING                                     0x01
#define DB_ALARM_STATE_SOAK_COMPLETE                               0x02
#define DB_ALARM_STATE_TICKETING                                   0x03
#define DB_ALARM_STATE_TICKETED                                    0x04
#define DB_ALARM_STATE_TICKETING_FAILED                            0x05
#define DB_ALARM_STATE_CLEARED                                     0x06
#define DB_ALARM_STATE_COINCIDENTAL                                0x07
#define DB_ALARM_STATE_RESTART_DISCARD                             0x08
#define DB_ALARM_STATE_LAST                                        0x08



#endif // _axDbDataTypes_hpp_
