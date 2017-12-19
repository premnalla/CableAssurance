
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axVector_mts_hpp_
#define _axVector_mts_hpp_

//********************************************************************
// include files
//********************************************************************
#include <pthread.h>
#include "axVector.hpp"

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
 * file/class: axVector_mts.hpp
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

class axVector_mts : public axVector
{
public:

  /// default constructor
  axVector_mts();

  /// destructor
  virtual ~axVector_mts();

protected:

  ///
  virtual void lock(void);

  ///
  virtual void unlock(void);

private:

  ///
  pthread_mutex_t   m_collectionLock;

  axVector_mts(const axVector_mts &);

};

#endif // _axVector_mts_hpp_
