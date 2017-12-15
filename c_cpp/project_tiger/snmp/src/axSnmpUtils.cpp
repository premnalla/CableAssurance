/************************************************************************* 
 * Copyright (c) 2006 by Premraj Nallasivampillai. All rights reserved.
 *************************************************************************
 *
 * axSnmpUtils.cpp
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
#include <net-snmp/net-snmp-config.h>
#include <net-snmp/net-snmp-includes.h>
#include "axSnmpUtils.hpp"

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
void copyOid(AX_INT8 * dest, const AX_INT8 * src, const int destLen)
{
  memcpy(dest, src, destLen);
  dest[destLen-1] = '\0';
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
void snmpMacToString(AX_INT8 * dest, netsnmp_variable_list * var)
{
#if 0
  static const int  maxOctets=6;
  AX_UINT8        * cp;
  int               currOctet;
  bool              done;

  if (var->type != ASN_OCTET_STR) {
    goto EXIT_LABEL;
  }

  cp = var->val.string;

  done = false;
  currOctet = 1;
  while (!done) {
    if (currOctet < maxOctets) {
      if (currOctet == 1) {
        sprintf(dest,"%x:", *cp++);
      } else {
        sprintf(dest,"%s%x:", dest, *cp++);
      }
    } else {
      sprintf(dest,"%s%x", dest, *cp++);
    }
    currOctet++;
    if (currOctet > maxOctets) {
      done = true;
    }
  }
#else

  AX_UINT8  * cp;
  AX_UINT8  * ecp;

  if (var->type != ASN_OCTET_STR) {
    goto EXIT_LABEL;
  }

  cp = var->val.string;
  ecp = cp + var->val_len;

  while (cp != ecp) {
    sprintf(dest, "%.2x", *cp++);
    dest += 2;
  }

#endif

EXIT_LABEL:
  return;
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
void snmpIPv4ToString(AX_INT8 * dest, netsnmp_variable_list * var)
{
  static const int maxOctets=4;
  AX_UINT8       * cp;
  int              currOctet;
  bool             done;

  if (var->type != ASN_OCTET_STR) {
    goto EXIT_LABEL;
  }

  cp = var->val.string;

  done = false;
  currOctet = 1;
  while (!done) {
    if (currOctet < maxOctets) {
      if (currOctet == 1) {
        sprintf(dest,"%d.", *cp++);
      } else {
        sprintf(dest,"%s%d.", dest, *cp++);
      }
    } else {
      sprintf(dest,"%s%x", dest, *cp++);
    }
    currOctet++;
    if (currOctet > maxOctets) {
      done = true;
    }
  }

EXIT_LABEL:
  return;
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
void snmpIfDescriptionToString(AX_INT8 * dest, netsnmp_variable_list * var)
{
  AX_UINT8       * cp, *ecp;

  if (var->type != ASN_OCTET_STR) {
    goto EXIT_LABEL;
  }

  cp = var->val.string;
  ecp = cp + var->val_len;

  while (cp != ecp) {
    *dest++ = *cp++;
  }

  *dest = '\0';

EXIT_LABEL:
  return;
}


/*************************************************************************
 * function :
 * description :
 * in :
 * out :
 * in/out :
 * side effects :
 *************************************************************************/
void getNetSnmpIpAddress(AX_INT8 * outAddr, AX_INT8 * inAddr, AX_UINT8 type)
{
  switch (type) {
    case AX_SNMP_IPADDR_TYPE_IPv4:
      strcpy(outAddr, inAddr);
      break;

    case AX_SNMP_IPADDR_TYPE_IPv6:
      sprintf(outAddr, "udp6:[%s]", inAddr);
      break;

    default:
      outAddr[0] = '\0';
      break;
  }
}


