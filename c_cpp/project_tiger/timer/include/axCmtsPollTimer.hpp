
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmtsPollTimer_hpp_
#define _axCmtsPollTimer_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractTimerObject.hpp"
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
 * file/class: axCmtsPollTimer.hpp
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

class axCmtsPollTimer : public axAbstractTimerObject
{
public:

  /// data constructor
  axCmtsPollTimer(axInternalCmts *, time_t &);

  /// destructor
  virtual ~axCmtsPollTimer();

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

  virtual bool timerCallback(void);

protected:

  /// default constructor
  axCmtsPollTimer();

private:

  axCmtsPollTimer(const axCmtsPollTimer &);

  axInternalCmts * m_intCmts;
};

#endif // _axCmtsPollTimer_hpp_
