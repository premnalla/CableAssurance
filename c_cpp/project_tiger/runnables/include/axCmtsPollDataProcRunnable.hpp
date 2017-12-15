
//********************************************************************
// ******************** OBSOLETED ************************************
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmtsPollDataProcRunnable_hpp_
#define _axCmtsPollDataProcRunnable_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractRunnable.hpp"
#include "axSnmpDataTypes.hpp"
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
 * file/class: axCmtsPollDataProcRunnable.hpp
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

class axCmtsPollDataProcRunnable : public axAbstractRunnable
{
public:

  /// data constructor
  axCmtsPollDataProcRunnable(axSnmpCmtsCmResultValues_s *, axInternalCmts *);

  /// destructor
  virtual ~axCmtsPollDataProcRunnable();

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
  virtual AX_INT32 preRun(void);
  virtual AX_INT32 run(void);
  virtual AX_INT32 postRun(void);
  virtual void     nextAction(void);

protected:

  /// default constructor
  axCmtsPollDataProcRunnable();

private:

  axCmtsPollDataProcRunnable(const axCmtsPollDataProcRunnable &);

  axSnmpCmtsCmResultValues_s  m_pollData;
  axInternalCmts          * m_intCmts;
};

#endif // _axCmtsPollDataProcRunnable_hpp_
