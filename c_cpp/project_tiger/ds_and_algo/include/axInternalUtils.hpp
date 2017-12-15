/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axInternalUtils.hpp
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

#ifndef _axInternalUtils_h_
#define _axInternalUtils_h_

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
#include "axAll.h"


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
extern void copyIntMac(AX_INT8 * dest, const AX_INT8 * src);
extern void copyIntSnmpCommunity(AX_INT8 * dest, const AX_INT8 * src);
extern void copyIntFqdn(AX_INT8 * dest, const AX_INT8 * src);
extern void copyIntIpAddress(AX_INT8 * dest, const AX_INT8 * src);
extern void copyIntCmtsName(AX_INT8 * dest, const AX_INT8 * src);
extern void copyIntHfcName(AX_INT8 * dest, const AX_INT8 * src);
extern void copyIntChannelName(AX_INT8 * dest, const AX_INT8 * src);




#endif /* #ifndef _axInternalUtils_h_ */
