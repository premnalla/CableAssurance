/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axSnmpUtils.hpp
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

#ifndef _axSnmpUtils_hpp_
#define _axSnmpUtils_hpp_

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
#include "axSnmpDataTypes.hpp"


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
extern void copyOid(AX_INT8 * dest, const AX_INT8 * src, const int destLen);
extern void snmpMacToString(AX_INT8 *, netsnmp_variable_list *);
extern void snmpIPv4ToString(AX_INT8 *, netsnmp_variable_list *);
extern void snmpIfDescriptionToString(AX_INT8 *, netsnmp_variable_list *);
extern void getNetSnmpIpAddress(AX_INT8 *, AX_INT8 *, AX_UINT8);



#endif /* #ifndef _axSnmpUtils_hpp_ */
