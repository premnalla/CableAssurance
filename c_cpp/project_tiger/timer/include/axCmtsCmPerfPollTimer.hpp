
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmtsCmPerfPollTimer_hpp_
#define _axCmtsCmPerfPollTimer_hpp_

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
 * file/class: axCmtsCmPerfPollTimer.hpp
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

class axCmtsCmPerfPollTimer : public axAbstractTimerObject
{
public:

  /// data constructor
  axCmtsCmPerfPollTimer(axInternalCmts *, time_t &);

  /// destructor
  virtual ~axCmtsCmPerfPollTimer();

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
  axCmtsCmPerfPollTimer();

private:

  axCmtsCmPerfPollTimer(const axCmtsCmPerfPollTimer &);

  axInternalCmts * m_intCmts;
};

#endif // _axCmtsCmPerfPollTimer_hpp_
