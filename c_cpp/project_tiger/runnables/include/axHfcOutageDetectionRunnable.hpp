
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcOutageDetectionRunnable_hpp_
#define _axHfcOutageDetectionRunnable_hpp_

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
 * file/class: axHfcOutageDetectionRunnable.hpp
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

class axHfcOutageDetectionRunnable : public axAbstractCARunnable
{
public:

  /// data constructor
  axHfcOutageDetectionRunnable(axAbstractCARunnableCollection *, axInternalCmts *);

  /// destructor
  virtual ~axHfcOutageDetectionRunnable();

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

protected:

  /// default constructor
  axHfcOutageDetectionRunnable();

private:

  axHfcOutageDetectionRunnable(const axHfcOutageDetectionRunnable &);

  axInternalCmts * m_intCmts;
};

#endif // _axHfcOutageDetectionRunnable_hpp_
