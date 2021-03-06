/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axAlarmDataTypes.hpp      
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
 *    09/14/06    Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

#ifndef _axAlarmDataTypes_hpp_
#define _axAlarmDataTypes_hpp_

/*************************************************************************
 * include files
 *************************************************************************/


/*************************************************************************
 * Forward Declerations
 *************************************************************************/

/*************************************************************************
 * Global definitions/macros
 *************************************************************************/

/************************************************************************/
/***************************** Alarm Types *****************************/
/************************************************************************/

#define AX_ALARM_TYPE_NULL                                           0x00
#define AX_ALARM_TYPE_HFC                                            0x01
#define AX_ALARM_TYPE_MTA                                            0x02
#define AX_ALARM_TYPE_CMTS                                           0x03
#define AX_ALARM_TYPE_CMS                                            0x04
#define AX_ALARM_TYPE_LAST                                           0x04


/************************************************************************/
/*************************** HFC Alarm Sub Types ***********************/
/************************************************************************/

#define AX_HFC_ALARM_NULL                                            0x00
#define AX_HFC_ALARM_POWER                                           0x01
#define AX_HFC_ALARM_PERCENT                                         0x02
#define AX_HFC_ALARM_MTA_COUNT                                       0x03
#define AX_HFC_ALARM_LAST                                            0x03

/************************************************************************/
/**************************** MTA Alarm Sub Types ***********************/
/************************************************************************/

#define AX_MTA_ALARM_NULL                                            0x00
#define AX_MTA_ALARM_UNAVAILABLE                                     0x01
#define AX_MTA_ALARM_ON_BATTERY                                      0x02
#define AX_MTA_ALARM_BATTERY_MISSING                                 0x03
#define AX_MTA_ALARM_LAST                                            0x03


/************************************************************************/
/*************************** Correlated Alarm Codes *********************/
/************************************************************************/

#define AX_CORRELATED_ALARM_NULL                                      0x00
#define AX_CORRELATED_ALARM_POWER                                     0x01
#define AX_CORRELATED_ALARM_PERCENT                                   0x02
#define AX_CORRELATED_ALARM_COUNT                                     0x03
#define AX_CORRELATED_ALARM_LAST                                      0x03


/*************************************************************************
 * Global Type definitions
 *************************************************************************/


/*************************************************************************
 * Extern Variables, Structures, etc.
 *************************************************************************/


/*************************************************************************
 * Extern functions
 *************************************************************************/


#endif /* #ifndef _axAlarmDataTypes_hpp_ */
