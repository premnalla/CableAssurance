
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axTraceableWorker_hpp_
#define _axTraceableWorker_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axWorker.hpp"
#include "ax_trace.h"

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
 * file/class: axTraceableWorker.hpp
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

class axTraceableWorker : public axWorker
{
public:


  /// 
  axTraceableWorker(axControllerProxy *, int);


  /// 
  virtual ~axTraceableWorker();


protected:


private:

  ///
  int    m_traceInstance;


  /// 
  axTraceableWorker();


  // copy not allowed
  axTraceableWorker(const axTraceableWorker &);

};

#endif // _axTraceableWorker_hpp_
