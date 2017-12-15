
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDailySummaryTimer_hpp_
#define _axDailySummaryTimer_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractTimerObject.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to kickoff the Daily Summary process.
 * 
 * 
 * file/class: axDailySummaryTimer.hpp
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

class axDailySummaryTimer : public axAbstractTimerObject
{
public:

  /// data constructor
  axDailySummaryTimer(time_t &);

  /// destructor
  virtual ~axDailySummaryTimer();

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
  axDailySummaryTimer();

private:

  axDailySummaryTimer(const axDailySummaryTimer &);

};

#endif // _axDailySummaryTimer_hpp_
