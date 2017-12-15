
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractCARunnableCollection_hpp_
#define _axAbstractCARunnableCollection_hpp_

//********************************************************************
// include files
//********************************************************************
#include <sys/time.h>
#include "axAll.h"
#include "axAbstractRunnableCollection.hpp"
#include "axReaderWriterLock.hpp"
#include "axRunnableDataTypes.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
class axAbstractCARunnable;

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axAbstractCARunnableCollection.hpp
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

class axAbstractCARunnableCollection : public axAbstractRunnableCollection
{
public:

  struct axAbstractCARunnableCollection_s {
    axAbstractCARunnableCollection_s() :
      lock(new axReaderWriterLock()),
      kickoffTime(0), workStartTime(0), workEndTime(0),
      batchNumber(0), inProgress(false), nextCycleScheduled(false)
    {
      // lock = new axReaderWriterLock();
    }
    virtual ~axAbstractCARunnableCollection_s()
    {
      delete lock;
    }
    axAbstractLock *  lock;
    time_t            kickoffTime;
    time_t            workStartTime;
    time_t            workEndTime;
    AX_INT32          batchNumber;
    bool              inProgress;
    bool              nextCycleScheduled;
  };
  
  /// destructor
  virtual ~axAbstractCARunnableCollection();

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
   * 
   */
  time_t           getKickoffTime(void);
  void             setKickoffTime(time_t);
  time_t           getWorkStartTime(void);
  void             setWorkStartTime(time_t);
  time_t           getWorkEndTime(void);
  void             setWorkEndTime(time_t);
  bool             isInProgress(void);
  void             setInProgress(void);
  void             clearInProgress(void);
  bool             isNextCycleScheduled(void);
  void             setNextCycleScheduled(void);
  void             clearNextCycleScheduled(void);
  axAbstractLock * getLock(void);

  /**
   *
   */
  virtual axAbstractCARunnable * createNewRunnable(void)=0;

protected:

  /// default constructor
  axAbstractCARunnableCollection();

  /**
   * Local Data getter(s) and setter(s). 
   * For now, only to be visible to sub-classes.
   */
  AX_INT32         getBatchNumber(void);
  void             setBatchNumber(AX_INT32);

  /**
   *
   */
  virtual axAbstractCARunnable * getNextRunnable(axAbstractCARunnable *)=0;

private:

  axAbstractCARunnableCollection(const axAbstractCARunnableCollection &);

  axAbstractCARunnableCollection_s  m_myData;
};

#endif // _axAbstractCARunnableCollection_hpp_
