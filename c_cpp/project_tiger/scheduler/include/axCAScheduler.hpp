
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCAScheduler_hpp_
#define _axCAScheduler_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractScheduler.hpp"
#include "axSchedTaskExecutor.hpp"
#include "axSchedDataTypes.hpp"
#include "axSchedPriRunnableQ.hpp"

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
 * file/class: axCAScheduler.hpp
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

class axCAScheduler : public axAbstractScheduler
{
public:

  /// destructor
  virtual ~axCAScheduler();

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

  static axCAScheduler * getInstance(void);
  virtual void initialize(void);

  virtual bool enQRunnable(axAbstractRunnable *);
  
  virtual axAbstractRunnable * deQNextRunnable(void);

protected:

  /// default constructor
  axCAScheduler();

private:

  axCAScheduler(const axCAScheduler &);

  static axCAScheduler * m_instance;
  bool                   m_initialized;

  axSchedPriRunnableQ    m_queue;
};

#endif // _axCAScheduler_hpp_
