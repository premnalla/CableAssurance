
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axList_mts_hpp_
#define _axList_mts_hpp_

//********************************************************************
// include files
//********************************************************************
#include <pthread.h>
#include "axList.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axList_mts.hpp
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

class axList_mts : public axList
{
public:

  /// default constructor
  axList_mts();

  /// destructor
  virtual ~axList_mts();

protected:

  ///
  virtual void lock(void);

  ///
  virtual void unlock(void);

private:

  ///
  pthread_mutex_t   m_collectionLock;

  axList_mts(const axList_mts &);

};

#endif // _axList_mts_hpp_
