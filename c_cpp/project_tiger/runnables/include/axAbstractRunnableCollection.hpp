
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractRunnableCollection_hpp_
#define _axAbstractRunnableCollection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractRunnable.hpp"

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
 * file/class: axAbstractRunnableCollection.hpp
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

class axAbstractRunnableCollection
{
public:

  /// destructor
  virtual ~axAbstractRunnableCollection();

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
  virtual bool scheduleRunnables(void)=0;
  // virtual bool scheduleFirstBatchOfRunnables(void)=0;
  // virtual bool scheduleNextBatchOfRunnables(void)=0;

  /**
   *
   */
  virtual void processRunnableComplete(axAbstractRunnable *)=0;

protected:

  /// default constructor
  axAbstractRunnableCollection();

  /**
   *
   */
  virtual bool canScheduleRunnables(void)=0;

  /**
   *
   */
  virtual void postponeTask(void)=0;

private:

  axAbstractRunnableCollection(const axAbstractRunnableCollection &);

};

#endif // _axAbstractRunnableCollection_hpp_
