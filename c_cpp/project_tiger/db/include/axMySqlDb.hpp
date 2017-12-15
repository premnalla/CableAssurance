
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMySqlDb_hpp_
#define _axMySqlDb_hpp_

//********************************************************************
// include files
//********************************************************************
#ifdef __cplusplus
extern "C" {
#endif

#include "mysql.h"
#include "mysql_time.h"

#ifdef __cplusplus
}
#endif

/*************************************************************************
 * Global definitions/macros
 *************************************************************************/

//
typedef MYSQL_TIME DB_UPDATE_TIME_t;

//
typedef unsigned long * MYSQL_ROWCOL_LEN_t;


/*************************************************************************
 * Global Type definitions
 *************************************************************************/


/*************************************************************************
 * Extern Variables, Structures, etc.
 *************************************************************************/


/*************************************************************************
 * Extern functions
 *************************************************************************/
// extern void copyMySqlTime(DB_UPDATE_TIME_t dest, DB_UPDATE_TIME_t src);
extern void createMySqlTime(DB_UPDATE_TIME_t dest, const char * src);


#endif // _axMySqlDb_hpp_
