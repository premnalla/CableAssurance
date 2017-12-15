
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmPerfPollTimer_hpp_
#define _axCmPerfPollTimer_hpp_

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
 * file/class: axCmPerfPollTimer.hpp
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

class axCmPerfPollTimer : public axAbstractTimerObject
{
public:

  /// data constructor
  axCmPerfPollTimer(axInternalCmts *, time_t &);

  /// destructor
  virtual ~axCmPerfPollTimer();

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
  axCmPerfPollTimer();

private:

  axCmPerfPollTimer(const axCmPerfPollTimer &);

  axInternalCmts * m_intCmts;
};

#endif // _axCmPerfPollTimer_hpp_
