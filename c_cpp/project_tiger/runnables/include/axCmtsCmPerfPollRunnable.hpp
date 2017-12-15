
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmtsCmPerfPollRunnable_hpp_
#define _axCmtsCmPerfPollRunnable_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCARunnable.hpp"
#include "axInternalCmts.hpp"

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
 * file/class: axCmtsCmPerfPollRunnable.hpp
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

class axCmtsCmPerfPollRunnable : public axAbstractCARunnable
{
public:

  /// data constructor
  axCmtsCmPerfPollRunnable(axAbstractCARunnableCollection *, axInternalCmts *);

  /// destructor
  virtual ~axCmtsCmPerfPollRunnable();

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
  virtual void addSubject(axObject *);

protected:

  /// default constructor
  axCmtsCmPerfPollRunnable();

private:

  axCmtsCmPerfPollRunnable(const axCmtsCmPerfPollRunnable &);

  axInternalCmts * m_intCmts;
};

#endif // _axCmtsCmPerfPollRunnable_hpp_
