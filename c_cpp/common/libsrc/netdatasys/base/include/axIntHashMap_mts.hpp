
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axIntHashMap_mts_hpp_
#define _axIntHashMap_mts_hpp_

//********************************************************************
// include files
//********************************************************************
#include <pthread.h>
#include "axIntHashMap.hpp"

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
 * file/class: axIntHashMap_mts.hpp
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

class axIntHashMap_mts : public axIntHashMap
{
public:

  /// default constructor
  axIntHashMap_mts();

  /// destructor
  virtual ~axIntHashMap_mts();

protected:

  ///
  virtual void lock(void);

  ///
  virtual void unlock(void);

private:

  ///
  pthread_mutex_t   m_collectionLock;

  axIntHashMap_mts(const axIntHashMap_mts &);

};

#endif // _axIntHashMap_mts_hpp_
