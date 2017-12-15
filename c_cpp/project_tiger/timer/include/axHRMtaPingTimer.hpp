
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHRMtaPingTimer_hpp_
#define _axHRMtaPingTimer_hpp_

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
 * file/class: axHRMtaPingTimer.hpp
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

class axHRMtaPingTimer : public axAbstractTimerObject
{
public:

  /// data constructor
  axHRMtaPingTimer(axInternalCmts *, time_t &);

  /// destructor
  virtual ~axHRMtaPingTimer();

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
  axHRMtaPingTimer();

private:

  axHRMtaPingTimer(const axHRMtaPingTimer &);

  axInternalCmts * m_intCmts;
};

#endif // _axHRMtaPingTimer_hpp_
