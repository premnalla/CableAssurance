/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axNetSnmpDataTypes.h      
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
 *    12/25/01    Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

#ifndef _axNetSnmpDataTypes_h_
#define _axNetSnmpDataTypes_h_

/*************************************************************************
 * include files
 *************************************************************************/
#include <net-snmp/net-snmp-config.h>
#include <net-snmp/net-snmp-includes.h>
#include <net-snmp/library/snmp_api.h>


#ifdef __cplusplus
extern "C"
{
#endif


/*************************************************************************
 * Global definitions/macros
 *************************************************************************/


/*************************************************************************
 * Global Type definitions
 *************************************************************************/


/*************************************************************************
 * Extern Variables, Structures, etc.
 *************************************************************************/

/**
 * Directly from snmp_api.c
 *
 *
 */
typedef struct netsnmp_snmp_internal_session {
    netsnmp_request_list *requests;     /* Info about outstanding requests */
    netsnmp_request_list *requestsEnd;  /* ptr to end of list */
    int             (*hook_pre) (netsnmp_session *, netsnmp_transport *,
                                 void *, int);
    int             (*hook_parse) (netsnmp_session *, netsnmp_pdu *,
                                   u_char *, size_t);
    int             (*hook_post) (netsnmp_session *, netsnmp_pdu *, int);
    int             (*hook_build) (netsnmp_session *, netsnmp_pdu *,
                                   u_char *, size_t *);
    int             (*hook_realloc_build) (netsnmp_session *,
                                           netsnmp_pdu *, u_char **,
                                           size_t *, size_t *);
    int             (*check_packet) (u_char *, size_t);
    netsnmp_pdu    *(*hook_create_pdu) (netsnmp_transport *,
                                        void *, size_t);

    u_char         *packet;
    size_t          packet_len, packet_size;
} netsnmp_snmp_internal_session;


/**
 * Directly from snmp_api.c
 *
 *
 */
typedef struct netsnmp_session_list {
    struct netsnmp_session_list * next;
    netsnmp_session * session;
    netsnmp_transport * transport;
    netsnmp_snmp_internal_session * internal;
} netsnmp_session_list;


/*************************************************************************
 * Extern functions
 *************************************************************************/




#ifdef __cplusplus
}
#endif

#endif /* #ifndef _axNetSnmpDataTypes_h_ */
