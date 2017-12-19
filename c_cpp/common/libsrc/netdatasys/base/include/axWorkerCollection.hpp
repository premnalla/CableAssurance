
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axWorkerCollection_hpp_
#define _axWorkerCollection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axList_mts.hpp"

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
 * file/class: axWorkerCollection.hpp
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

class axWorkerCollection : public axList_mts
{
public:

  /// 
  axWorkerCollection();

  /// 
  virtual ~axWorkerCollection();

protected:

private:

  // copy not allowed
  axWorkerCollection(const axWorkerCollection &);

};

#endif // _axWorkerCollection_hpp_
