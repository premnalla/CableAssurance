/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axDbUtils.hpp
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
 *    08/04/06    Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

#ifndef _axDbUtils_h_
#define _axDbUtils_h_

#ifdef __cplusplus
extern "C"
{
#endif

/*************************************************************************
 * any include files that need this enclosre
 *************************************************************************/

#ifdef __cplusplus
}
#endif


/*************************************************************************
 * include files
 *************************************************************************/
#include "axDbDataTypes.hpp"


/*************************************************************************
 * Global definitions/macros
 *************************************************************************/


/*************************************************************************
 * Global Type definitions
 *************************************************************************/


/*************************************************************************
 * Extern Variables, Structures, etc.
 *************************************************************************/


/*************************************************************************
 * Extern functions
 *************************************************************************/
extern void copyDbMac(DB_MAC_t dest, const AX_INT8 * src);
extern void copyDbSnmpCommunity(DB_SNMP_COMMUNITY_t dest, const AX_INT8 * src);
extern void copyDbFqdn(DB_FQDN_t dest, const AX_INT8 * src);
extern void copyDbIpAddress(DB_IP_ADDRESS_t dest, const AX_INT8 * src);
extern void copyDbCmtsName(DB_CMTS_NAME_t dest, const AX_INT8 * src);
extern void copyDbHfcName(DB_HFC_NAME_t dest, const AX_INT8 * src);
extern void copyDbChannelName(DB_CHANNEL_NAME_t dest, const AX_INT8 * src);




#endif /* #ifndef _axDbUtils_h_ */
