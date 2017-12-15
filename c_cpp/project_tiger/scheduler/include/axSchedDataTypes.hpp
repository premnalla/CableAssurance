/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axSchedDataTypes.hpp      
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
 *    08/7/06     Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

#ifndef _axSchedDataTypes_hpp_
#define _axSchedDataTypes_hpp_

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
/******************************** Priorites *****************************/
/************************************************************************/
#define SCHED_NUM_PRIORITIES 5
#define SCHED_MIN_PRIORITY 0  /* HIGHEST PRIORITY; 0 - Reserved */
#define SCHED_MAX_PRIORITY 4  /* LOWEST PRIORITY */

#define CMTS_POLL_PRIORITY         1
#define HIGHRATE_MTA_POLL_PRIORITY 1
#define HIGHRATE_MTA_PING_PRIORITY 1
#define CM_PERF_POLL_PRIORITY      2
#define HFC_OUTAGE_DETECT_PRIORITY 1
#define DAILY_SUMMARY_PRIORITY     4

//
#define SCHED_MAX_NUM_TASK_EXECUTORS 1000
// #define SCHED_MAX_NUM_TASK_EXECUTORS 10


/*************************************************************************
 * Global Type definitions
 *************************************************************************/


/*************************************************************************
 * Extern Variables, Structures, etc.
 *************************************************************************/


/*************************************************************************
 * Extern functions
 *************************************************************************/


#endif /* #ifndef _axSchedDataTypes_hpp_ */
