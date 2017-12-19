
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axCommonDefs_hpp_
#define _axCommonDefs_hpp_

//********************************************************************
// include files
//********************************************************************
#include <pthread.h>
#include "axObject.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/**
 * This class is used as a Collection Interface.
 *
 *
 * file/class: axCommonDefs.hpp
 *
 * Design Document:
 *
 * System:
 *
 * Sub-system:
 *
 * History:
 *
 * @version 1.0
 * @author Prem Nallasivampillai
 * @see
 *
 */

class axCommonDefs
{
public:


  /**
   * Describe here ...
   *
   */
  typedef struct syncBoolStateS {
    bool               state;
    pthread_mutex_t    stateLock;
  } syncBoolStateS_t;


  /**
   * Describe here ...
   *
   */
  typedef struct syncBoolMultiStateS {
    syncBoolStateS_t   state;
    bool               initState;
    bool               initializing;
    bool               reInitRequested;
    bool               reInitializing;
  } syncBoolMultiStateS_t;


protected:


private:


};

#endif // _axCommonDefs_hpp_
