
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHighRateMtaPollRunnable_hpp_
#define _axHighRateMtaPollRunnable_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"
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
 * file/class: axHighRateMtaPollRunnable.hpp
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

class axHighRateMtaPollRunnable : public axAbstractCARunnable
{
public:

  /// data constructor
  axHighRateMtaPollRunnable(axAbstractCARunnableCollection *, axInternalCmts *);

  /// destructor
  virtual ~axHighRateMtaPollRunnable();

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
  // void addMta(axObject *);
  virtual void addSubject(axObject *);

protected:

  /// default constructor
  axHighRateMtaPollRunnable();

private:

  axHighRateMtaPollRunnable(const axHighRateMtaPollRunnable &);

  axInternalCmts * m_intCmts;

  axListBase       m_mtaList;
};

#endif // _axHighRateMtaPollRunnable_hpp_
