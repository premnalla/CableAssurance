
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axLongHashMap_mts_hpp_
#define _axLongHashMap_mts_hpp_

//********************************************************************
// include files
//********************************************************************
#include <pthread.h>
#include "axLongHashMap.hpp"

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
 * file/class: axLongHashMap_mts.hpp
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

class axLongHashMap_mts : public axLongHashMap
{
public:

  /// default constructor
  axLongHashMap_mts();

  /// destructor
  virtual ~axLongHashMap_mts();

protected:

  ///
  virtual void lock();

  ///
  virtual void unlock();

private:

  ///
  pthread_mutex_t   m_collectionLock;

  axLongHashMap_mts(const axLongHashMap_mts &);

};

#endif // _axLongHashMap_mts_hpp_
