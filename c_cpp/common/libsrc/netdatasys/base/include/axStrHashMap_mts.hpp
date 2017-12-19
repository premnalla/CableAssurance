
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axStrHashMap_mts_hpp_
#define _axStrHashMap_mts_hpp_

//********************************************************************
// include files
//********************************************************************
#include <pthread.h>
#include "axStrHashMap.hpp"

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
 * file/class: axStrHashMap_mts.hpp
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

class axStrHashMap_mts : public axStrHashMap
{
public:

  /// default constructor
  axStrHashMap_mts();

  /// destructor
  virtual ~axStrHashMap_mts();

protected:

  ///
  virtual void lock(void);

  ///
  virtual void unlock(void);

private:

  ///
  pthread_mutex_t   m_collectionLock;

  axStrHashMap_mts(const axStrHashMap_mts &);

};

#endif // _axStrHashMap_mts_hpp_
