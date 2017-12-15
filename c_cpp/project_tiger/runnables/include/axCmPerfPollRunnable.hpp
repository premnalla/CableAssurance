
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmPerfPollRunnable_hpp_
#define _axCmPerfPollRunnable_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCARunnable.hpp"
#include "axInternalCmts.hpp"
#include "axListBase.hpp"

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
 * file/class: axCmPerfPollRunnable.hpp
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

class axCmPerfPollRunnable : public axAbstractCARunnable
{
public:

  /// data constructor
  axCmPerfPollRunnable(axAbstractCARunnableCollection *, axInternalCmts *);

  /// destructor
  virtual ~axCmPerfPollRunnable();

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

  /**
   *
   */
  virtual AX_INT32 run(void);

  /**
   *
   */
  virtual void nextAction(void);

  /**
   *
   */
  virtual size_t size(void);

  /**
   *
   */
  // void addCm(axObject *);
  virtual void addSubject(axObject *);

protected:

  /// default constructor
  axCmPerfPollRunnable();

private:

  axCmPerfPollRunnable(const axCmPerfPollRunnable &);

  axInternalCmts * m_intCmts;

  axListBase       m_cmList;
};

#endif // _axCmPerfPollRunnable_hpp_
