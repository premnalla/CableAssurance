/************************************************************************* 
 * Copyright (c) 2006 by Premraj Nallasivampillai. All rights reserved.
 *************************************************************************
 *
 * axSocketUtils.cpp
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
#include "axCblAssureConstants.hpp"
#include "axSocketUtils.hpp"

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
AX_UINT8
sockAddressTypeToAddressFamily(AX_UINT32 snmpAddrTyp)
{
  AX_UINT8 ret;

  switch (snmpAddrTyp) {
    case AX_IP_ADDR_TYPE_IPv4:
      ret = AF_INET;
      break;

    case AX_IP_ADDR_TYPE_IPv6:
      ret = AF_INET6;
      break;

    default:
      ret = 0;
      break;
  }

  return (ret);
}

