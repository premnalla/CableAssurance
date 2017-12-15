
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractSnmpMultipleGetWork_hpp_
#define _axAbstractSnmpMultipleGetWork_hpp_

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
 * file/class: axAbstractSnmpMultipleGetWork.hpp
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

class axAbstractSnmpMultipleGetWork : public axAbsSnmpAsyncPollWork
{
public:

  /// destructor
  virtual ~axAbstractSnmpMultipleGetWork();

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
  axAbstractSnmpMultipleGetWork();

private:

  axAbstractSnmpMultipleGetWork(const axAbstractSnmpMultipleGetWork &);

};

#endif // _axAbstractSnmpMultipleGetWork_hpp_
