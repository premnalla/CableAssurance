/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axCblAssureConstants.hpp      
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
 *    08/23/06    Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

#ifndef _axCblAssureConstants_hpp_
#define _axCblAssureConstants_hpp_

/*************************************************************************
 * include files
 *************************************************************************/


/*************************************************************************
 * Forward Declerations
 *************************************************************************/

/*************************************************************************
 * Global definitions/macros
 *************************************************************************/

#define AX_CA_CHANNEL_TYPE_UNKNOWN    0x00
#define AX_CA_CHANNEL_TYPE_UPSTREAM   0x01
#define AX_CA_CHANNEL_TYPE_DOWNSTREAM 0x02

#define AX_IP_ADDR_TYPE_UNKNOWN                                          0
#define AX_IP_ADDR_TYPE_IPv4                                             1
#define AX_IP_ADDR_TYPE_IPv6                                             2

#define AX_SNMP_VERSION_UNKNOWN                                          0
#define AX_SNMP_VERSION_1                                                1
#define AX_SNMP_VERSION_2c                                               2
#define AX_SNMP_VERSION_3                                                3

/*
 * DOCSIS Cable Modem States
 */
#define AX_CM_DOCSIS_ST_UNKNOWN                                          0
#define AX_CM_DOCSIS_ST_OTHER                                            1
#define AX_CM_DOCSIS_ST_RANGING                                          2
#define AX_CM_DOCSIS_ST_RANGE_ABORT                                      3
#define AX_CM_DOCSIS_ST_RANGE_COMPETE                                    4
#define AX_CM_DOCSIS_ST_IP_COMPLETE                                      5
#define AX_CM_DOCSIS_ST_REGIS_COMPLETE                                   6
#define AX_CM_DOCSIS_ST_ACCESS_DENIED                                    7
#define AX_CM_DOCSIS_ST_OPERATIONAL                                      8
#define AX_CM_DOCSIS_ST_REGIS_BPI_INIT                                   9

/*
 * Packet Cable Provision States
 */
#define AX_PKTCBL_PROV_ST_UNKNOWN                                        0
#define AX_PKTCBL_PROV_ST_PASS                                           1
#define AX_PKTCBL_PROV_ST_IN_PROG                                        2
#define AX_PKTCBL_PROV_ST_FAIL_CFGFILE_ERR                               3
#define AX_PKTCBL_PROV_ST_PASS_W_WARN                                    4
#define AX_PKTCBL_PROV_ST_PASS_W_INCMPL_PARS                             5
#define AX_PKTCBL_PROV_ST_FAIL_INTERNAL_ERR                              6
#define AX_PKTCBL_PROV_ST_FAIL_OTHER_REASON                              7


/*
 * Ping States
 */
#define AX_PING_ST_UNKNOWN                                               0
#define AX_PING_ST_HOST_ALIVE                                            1
#define AX_PING_ST_HOST_UNREACHABLE                                      2

/*
 * Ping States
 */
#define AX_AVAIL_ST_UNAVAILABLE                                          0
#define AX_AVAIL_ST_AVAILABLE                                            1

/*
 * Onlines States (CMTS,etc)
 */
#define AX_ONLINE_ST_OFFLINE                                             0
#define AX_ONLINE_ST_ONLINE                                              1
#define AX_ONLINE_ST_UNKNOWN                                             2

/*
 * Date & Time constants
 */
#define AX_SECONDS_IN_A_DAY                                          86400

/*************************************************************************
 * Global Type definitions
 *************************************************************************/


/*************************************************************************
 * Extern Variables, Structures, etc.
 *************************************************************************/


/*************************************************************************
 * Extern functions
 *************************************************************************/


#endif /* #ifndef _axCblAssureConstants_hpp_ */
