/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axCALog.hpp
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
 *    10/26/06    Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

#ifndef _axCALog_hpp_
#define _axCALog_hpp_

/*************************************************************************
 * include files
 *************************************************************************/
#include "ace/Log_Msg.h"


#ifdef __cplusplus
extern "C"
{
#endif


/*************************************************************************
 * Global definitions/macros
 *************************************************************************/
#define DEBUG_PREFIX       ACE_TEXT ("DEBUG%I")
#define INFO_PREFIX        ACE_TEXT ("INFO%I")
#define NOTICE_PREFIX      ACE_TEXT ("NOTICE%I")
#define WARNING_PREFIX     ACE_TEXT ("WARNING%I")
#define ERROR_PREFIX       ACE_TEXT ("ERROR%I")
#define CRITICAL_PREFIX    ACE_TEXT ("CRITICAL%I")
#define ALERT_PREFIX       ACE_TEXT ("ALERT%I")
#define EMERGENCY_PREFIX   ACE_TEXT ("EMERGENCY%I")
#define LOG_DEBUG(FMT, ...) ACE_DEBUG(( LM_DEBUG,DEBUG_PREFIX FMT __VA_ARGS__))
// #define LOG_INFO(FMT, ...)  ACE_DEBUG(( LM_INFO, INFO_PREFIX FMT __VA_ARGS__))
#define LOG_INFO(X)  ACE_DEBUG(( LM_INFO, X))
#define LOG_NOTICE(FMT, ...) ACE_DEBUG(( LM_NOTICE,NOTICE_PREFIX FMT __VA_ARGS__))
#define LOG_WARNING(FMT, ...) ACE_DEBUG(( LM_WARNING,WARNING_PREFIX FMT __VA_ARGS__))
#define LOG_ERROR(FMT, ...) ACE_DEBUG(( LM_ERROR,ERROR_PREFIX FMT __VA_ARGS__))
#define LOG_CRITICAL(FMT, ...) ACE_DEBUG(( LM_CRITICAL,CRITICAL_PREFIX FMT __VA_ARGS__))
#define LOG_ALERT(FMT, ...) ACE_DEBUG(( LM_ALERT,ALERT_PREFIX FMT __VA_ARGS__))
#define LOG_EMERGENCY(FMT, ...) ACE_DEBUG(( LM_EMERGENCY,EMERGENCY_PREFIX FMT __VA_ARGS__))

#define LOG_DB_DEBUG(FMT, ...) ACE_DEBUG(( LM_DB_DEBUG,DEBUG_PREFIX FMT __VA_ARGS__))
#define LOG_MISC_DEBUG(FMT, ...) ACE_DEBUG(( LM_MISC_DEBUG,DEBUG_PREFIX FMT __VA_ARGS__))


#if 0 // alternatives to above iff compile doesn't support above

#define MY_DEBUG      LM_DEBUG,     ACE_TEXT ("DEBUG%I")
#define MY_INFO       LM_INFO,      ACE_TEXT ("INFO%I")
#define MY_NOTICE     LM_NOTICE,    ACE_TEXT ("NOTICE%I")
#define MY_WARNING    LM_WARNING,   ACE_TEXT ("WARNING%I")
#define MY_ERROR      LM_ERROR,     ACE_TEXT ("ERROR%I")
#define MY_CRITICAL   LM_CRITICAL,  ACE_TEXT ("CRITICAL%I")
#define MY_ALERT      LM_ALERT,     ACE_TEXT ("ALERT%I")
#define MY_EMERGENCY  LM_EMERGENCY, ACE_TEXT ("EMERGENCY%I")

#endif


/*************************************************************************
 * Global Type definitions
 *************************************************************************/


/*************************************************************************
 * Extern Variables, Structures, etc.
 *************************************************************************/


/*************************************************************************
 * Extern functions
 *************************************************************************/




#ifdef __cplusplus
}
#endif

#endif /* #ifndef _axCALog_hpp_ */
