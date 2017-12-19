
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axWriteLockOperator_hpp_
#define _axWriteLockOperator_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractLock.hpp"
#include "axAbstractLockOperator.hpp"

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
 * file/class: axWriteLockOperator.hpp
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

class axWriteLockOperator : public axAbstractLockOperator
{
public:

  /// date constructor
  axWriteLockOperator(axAbstractLock *);

  /// destructor
  virtual ~axWriteLockOperator();

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
  axWriteLockOperator();


private:

  axWriteLockOperator(const axWriteLockOperator &);

  axAbstractLock * m_lock;

};

#endif // _axWriteLockOperator_hpp_
