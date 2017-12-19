
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axWorker_hpp_
#define _axWorker_hpp_

//********************************************************************
// include files
//********************************************************************
#ifdef __LYNXOS__
#define __LYNXOS
#include <signal.h>
#undef __LYNXOS
#else
#include <signal.h>
#endif

#include <pthread.h>
#include "axObject.hpp"
#include "axControllerProxy.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
// class axTraceHandler;


/**
 * This class is used to ...
 *
 *
 * file/class: axWorker.hpp
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

class axWorker : public axObject
{
public:

  /// 
  static AX_INT32 m_signalList[];

  /// 
  virtual ~axWorker();

  ///
  void setContentionScope(AX_INT32);
  ///
  void setDetachState(AX_INT32);
  ///
  void setPriority(struct sched_param &);
  ///
  void setPolicy(AX_INT32);
  ///
  void setInheritSchedule(AX_INT32);


  /**
   * Call this from your code. 
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
  virtual AX_INT32 start(void);


  /**
   * Specialize this method in sub-classes .... !!! Don't call it
   * from your code. The "start" method calls it for you.
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
  virtual AX_INT32 run(void);


  /**
   * Specialize this method in sub-classes if you wish to add your own
   * set of signals to block withing your thread/worker. Don't call this
   * method (however) !!!
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
  virtual void setSignalsToBlock(void);


  ///
  // void setTraceHandler(axTraceHandler *);


  ///
  pthread_t getWorkerId(void) const;


protected:

  struct threadSettableAttributes {
    AX_INT32             contentionScope;
    AX_INT32             detachState;
    struct sched_param   priority;
    AX_INT32             policy;
    AX_INT32             inheritSchedule;
  };

  /// 
  axWorker();

  /// 
  axWorker(axControllerProxy *);

  /// 
  axControllerProxy * getControllerProxy(void) const;

  ///
  // axTraceHandler * getTraceHandler(void);

  ///
  void setWorkerId(pthread_t);

  ///
  threadSettableAttributes * getThreadAttribuites(void);

private:

  pthread_t           m_workerId;

  pthread_attr_t      m_workerProperties;

  axControllerProxy * m_controllerProxy;

  struct threadSettableAttributes m_threadSettableAttrs;

  // axTraceHandler * m_traceHandler;

  /// 
  void setThreadProperties(void);

  ///
  void InitAtInstantiation(void);

  // copy not allowed
  axWorker(const axWorker &);
};

#endif // _axWorker_hpp_
