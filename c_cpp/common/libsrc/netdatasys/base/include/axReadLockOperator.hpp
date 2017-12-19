
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axReadLockOperator_hpp_
#define _axReadLockOperator_hpp_

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
// class axAbstractLock;

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axReadLockOperator.hpp
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

class axReadLockOperator : public axAbstractLockOperator
{
public:

  /// date constructor
  axReadLockOperator(axAbstractLock *);

  /// destructor
  virtual ~axReadLockOperator();

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
  axReadLockOperator();


private:

  axReadLockOperator(const axReadLockOperator &);

  axAbstractLock * m_lock;

};

#endif // _axReadLockOperator_hpp_
