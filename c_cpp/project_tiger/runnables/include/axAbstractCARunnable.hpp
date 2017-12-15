
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractCARunnable_hpp_
#define _axAbstractCARunnable_hpp_

//********************************************************************
// include files
//********************************************************************
#include <sys/time.h>
#include "axAbstractRunnable.hpp"
#include "axAbstractCARunnableCollection.hpp"
#include "axReaderWriterLock.hpp"
#include "axRunnableDataTypes.hpp"

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
 * file/class: axAbstractCARunnable.hpp
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

class axAbstractCARunnable : public axAbstractRunnable
{
public:

  struct axAbstractCARunnable_s {
    axAbstractCARunnable_s() :
      workStartTime(0), workEndTime(0), running(false)
    {
      lock = new axReaderWriterLock();
    }
    virtual ~axAbstractCARunnable_s()
    {
      delete lock;
    }
    axAbstractLock * lock;
    time_t           workStartTime;
    time_t           workEndTime;
    bool             running;
  };

  /// destructor
  virtual ~axAbstractCARunnable();

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
  axAbstractLock * getLock(void);
  bool             isRunning(void);
  void             setRunning(void);
  void             clearRunning(void);
  time_t           getWorkStartTime(void);
  void             setWorkStartTime(time_t);
  time_t           getWorkEndTime(void);
  void             setWorkEndTime(time_t);

  /**
   *
   */
  virtual AX_INT32 preRun(void);
  virtual AX_INT32 postRun(void);

  /**
   *
   */
  virtual size_t size(void);

  /**
   *
   */
  axAbstractCARunnableCollection * getRunnableCollection(void);

  /**
   *
   */
  virtual void addSubject(axObject *)=0;

protected:

  /// data constructor
  axAbstractCARunnable(axAbstractCARunnableCollection *);

private:

  /// default constructor
  axAbstractCARunnable();

  axAbstractCARunnable(const axAbstractCARunnable &);

  axAbstractCARunnableCollection * m_rc;

  axAbstractCARunnable_s  m_myData;
};

#endif // _axAbstractCARunnable_hpp_
