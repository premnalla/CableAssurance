/*********************************************************************
 * Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
 *********************************************************************
 *
 * axTimerDataTypes.hpp      
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

#ifndef _axTimerDataTypes_hpp_
#define _axTimerDataTypes_hpp_

/*************************************************************************
 * include files
 *************************************************************************/
#include <sys/time.h>
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
struct axTimerHeaderBase_s {
  time_t     timerPop;   /* number of seconds since Jan 1, 1970 */

  axTimerHeaderBase_s() : timerPop((time_t)0)
  {
  }

  axTimerHeaderBase_s(time_t pt) : timerPop(pt)
  {
  }
};


struct axTimerHeader_s : public axTimerHeaderBase_s {
  AX_UINT32  timerId;    /* a unique correlator */

  axTimerHeader_s() : timerId((AX_UINT32)0) 
  { 
  }

  axTimerHeader_s(time_t pt) : axTimerHeaderBase_s(pt), timerId((AX_UINT32)0) 
  { 
  }

};


/*************************************************************************
 * Extern Variables, Structures, etc.
 *************************************************************************/


/*************************************************************************
 * Extern functions
 *************************************************************************/


#endif /* #ifndef _axTimerDataTypes_hpp_ */
