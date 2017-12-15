
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDailySummaryRunnable_hpp_
#define _axDailySummaryRunnable_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCARunnable.hpp"

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
 * file/class: axDailySummaryRunnable.hpp
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

class axDailySummaryRunnable : public axAbstractCARunnable
{
public:

  /// data constructor
  axDailySummaryRunnable(axAbstractCARunnableCollection *);

  /// destructor
  virtual ~axDailySummaryRunnable();

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
  axDailySummaryRunnable();

private:

  axDailySummaryRunnable(const axDailySummaryRunnable &);

};

#endif // _axDailySummaryRunnable_hpp_
