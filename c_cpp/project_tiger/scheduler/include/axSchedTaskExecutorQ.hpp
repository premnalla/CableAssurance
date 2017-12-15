
//********************************************************************
// OBSOLETE
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSchedTaskExecutorQ_hpp_
#define _axSchedTaskExecutorQ_hpp_

//********************************************************************
// include files
//********************************************************************

//********************************************************************
// definitions/macros
//********************************************************************
#include "axConsumerSupplierQueue.hpp"

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axSchedTaskExecutorQ.hpp
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

class axSchedTaskExecutorQ : public axConsumerSupplierQueue
{
public:

  /// destructor
  virtual ~axSchedTaskExecutorQ();

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

  ///
  static axSchedTaskExecutorQ * getInstance(void);

protected:

  /// default constructor
  axSchedTaskExecutorQ();


private:

  axSchedTaskExecutorQ(const axSchedTaskExecutorQ &);

  static axSchedTaskExecutorQ    * m_instance;
};

#endif // _axSchedTaskExecutorQ_hpp_
