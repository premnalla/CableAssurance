
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractSnmpSingleGetWork_hpp_
#define _axAbstractSnmpSingleGetWork_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbsSnmpAsyncPollWork.hpp"

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
 * file/class: axAbstractSnmpSingleGetWork.hpp
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

class axAbstractSnmpSingleGetWork : public axAbsSnmpAsyncPollWork
{
public:

  /// destructor
  virtual ~axAbstractSnmpSingleGetWork();

  /**
   * Describe here ...
   *
   * @param p1 in parameter
   * @param p2 in-out parameter
   * @param p3 out parameter
   * @return 
   *   \begin{itemize}
   *     \item AX_SUCCESS successfully executed 
   *     \item AX_FAILED  unsuccessfully executed 
   *   \end{itemize}
   * @see
   */

protected:

  /// default constructor
  axAbstractSnmpSingleGetWork();

private:

  axAbstractSnmpSingleGetWork(const axAbstractSnmpSingleGetWork &);

};

#endif // _axAbstractSnmpSingleGetWork_hpp_
