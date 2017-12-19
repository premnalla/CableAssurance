
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axAvlTree_mts_hpp_
#define _axAvlTree_mts_hpp_

//********************************************************************
// include files
//********************************************************************
#include <pthread.h>
#include "axAvlTreeLockingCapable.hpp"

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
 * file/class: axAvlTree_mts.hpp
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

class axAvlTree_mts : public axAvlTreeLockingCapable
{
public:

  /// default constructor
  axAvlTree_mts();

  /// destructor
  virtual ~axAvlTree_mts();

protected:

  ///
  virtual void lock(void);

  ///
  virtual void unlock(void);

private:

  ///
  pthread_mutex_t   m_collectionLock;

  axAvlTree_mts(const axAvlTree_mts &);

};

#endif // _axAvlTree_mts_hpp_
