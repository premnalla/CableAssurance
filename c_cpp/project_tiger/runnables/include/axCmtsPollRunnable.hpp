
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmtsPollRunnable_hpp_
#define _axCmtsPollRunnable_hpp_

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
 * file/class: axCmtsPollRunnable.hpp
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

class axCmtsPollRunnable : public axAbstractCARunnable
{
public:

  /// data constructor
  axCmtsPollRunnable(axAbstractCARunnableCollection *, axInternalCmts *);

  /// destructor
  virtual ~axCmtsPollRunnable();

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
  axCmtsPollRunnable();

private:

  axCmtsPollRunnable(const axCmtsPollRunnable &);

  axInternalCmts * m_intCmts;
};

#endif // _axCmtsPollRunnable_hpp_
