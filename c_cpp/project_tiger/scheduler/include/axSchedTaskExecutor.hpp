
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSchedTaskExecutor_hpp_
#define _axSchedTaskExecutor_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAceWorker.hpp"
// #include "axConsumerSupplierQueue.hpp"
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
 * file/class: axSchedTaskExecutor.hpp
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

class axSchedTaskExecutor : public axAceWorker
{
public:

  /// default constructor
  axSchedTaskExecutor();

  /// destructor
  virtual ~axSchedTaskExecutor();

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

   virtual AX_INT32 run(void);

   // virtual void enQWorkItem(axAbstractRunnable *);

protected:


private:

  axSchedTaskExecutor(const axSchedTaskExecutor &);

  // axConsumerSupplierQueue  m_workItemQ;

};

#endif // _axSchedTaskExecutor_hpp_
