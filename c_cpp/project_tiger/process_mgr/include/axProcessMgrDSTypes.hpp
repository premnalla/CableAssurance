/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axProcessMgrDSTypes.hpp      
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
 *    10/28/06     Prem N.,             Created
 *
 *    mm/dd/yy    Subsequent major milestones.
 *************************************************************************
 */

#ifndef _axProcessMgrDSTypes_hpp_
#define _axProcessMgrDSTypes_hpp_

/*************************************************************************
 * include files
 *************************************************************************/
#include <string.h>
#include <sys/types.h>
#include "axAll.h"


/*************************************************************************
 * Forward Declerations
 *************************************************************************/


/*************************************************************************
 * Global definitions/macros
 *************************************************************************/


/*************************************************************************
 * Global Type definitions
 *************************************************************************/
#define AX_CAPM_MAX_INITTAB_ENTRIES                                     10
#define AX_CAPM_NUM_INITTAB_COLUMNS                                      4

#define AX_CAPM_MAX_ID_LEN                                              64
#define AX_CAPM_MAX_RL_LEN                                              64
#define AX_CAPM_MAX_ACT_LEN                                             64
#define AX_CAPM_MAX_PATH_LEN                                           512 
#define AX_CAPM_MAX_ARGV                                                10 

#define AX_CAPM_ACT_RESPAWN                                      "respawn"

struct axPmInittabEntry_s {
  pid_t     pid;
  AX_INT8   id[AX_CAPM_MAX_ID_LEN];
  AX_INT8   runLevel[AX_CAPM_MAX_RL_LEN];
  AX_INT8   action[AX_CAPM_MAX_ACT_LEN];
  AX_INT8   path[AX_CAPM_MAX_PATH_LEN];
  AX_INT8 * argv[AX_CAPM_MAX_ARGV];

  axPmInittabEntry_s() : 
    pid(0)
  {
    id[0]='\0';
    runLevel[0]='\0';
    action[0]='\0';
    path[0]='\0';
    memset(argv, 0, sizeof(argv));
  }
};


struct axPmInittabEntries_s {
  AX_UINT8          maxEntries;
  AX_UINT8          numEntries;
  AX_UINT8          numColumns;
  AX_UINT8          unused1;
  axPmInittabEntry_s entries[AX_CAPM_MAX_INITTAB_ENTRIES];

  axPmInittabEntries_s() : 
    maxEntries(AX_CAPM_MAX_INITTAB_ENTRIES),
    numEntries(0),
    numColumns(AX_CAPM_NUM_INITTAB_COLUMNS)
  {
  }
};


/*************************************************************************
 * Extern Variables, Structures, etc.
 *************************************************************************/


/*************************************************************************
 * Extern functions
 *************************************************************************/


#endif /* #ifndef _axProcessMgrDSTypes_hpp_ */
