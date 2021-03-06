/************************************************************************* 
 * Copyright (c) 2006 by Premraj Nallasivampillai. All rights reserved.
 *************************************************************************
 *
 * axDbUtils.cpp
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
 *    08/4/06     Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

/*************************************************************************
 * include files
 *************************************************************************/
#include <string.h>
#include "axDbUtils.hpp"
#include "axDbDataTypes.hpp"

/*************************************************************************
 * definitions/macros
 *************************************************************************/


/*************************************************************************
 * constants
 *************************************************************************/

/*************************************************************************
 * static member initialization
 *************************************************************************/


/*************************************************************************
 * forward declerations
 *************************************************************************/


/*************************************************************************
 * functions
 *************************************************************************/


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
void copyDbMac(DB_MAC_t dest, const AX_INT8 * src)
{
  copyString(dest, src, MAX_MAC_ADDRESS_CHARS);
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
void copyDbSnmpCommunity(DB_SNMP_COMMUNITY_t dest, const AX_INT8 * src)
{
  copyString(dest, src, DB_MAX_SNMP_COMMUNITY_SIZE);
}



/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
void copyDbFqdn(DB_FQDN_t dest, const AX_INT8 * src)
{
  copyString(dest, src, DB_FQDN_SIZE);
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
void copyDbIpAddress(DB_IP_ADDRESS_t dest, const AX_INT8 * src)
{
  copyString(dest, src, DB_IP_ADDRESS_SIZE);
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
void copyDbCmtsName(DB_IP_ADDRESS_t dest, const AX_INT8 * src)
{
  copyString(dest, src, DB_CMTS_NAME_SIZE);
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
void copyDbHfcName(DB_IP_ADDRESS_t dest, const AX_INT8 * src)
{
  copyString(dest, src, DB_CHANNEL_NAME_SIZE);
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
void copyDbChannelName(DB_IP_ADDRESS_t dest, const AX_INT8 * src)
{
  copyString(dest, src, DB_HFC_NAME_SIZE);
}



