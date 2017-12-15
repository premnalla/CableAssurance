
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axPoppedTimerQ_hpp_
#define _axPoppedTimerQ_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axConsumerSupplierQueue.hpp"

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
 * file/class: axPoppedTimerQ.hpp
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

class axPoppedTimerQ : public axConsumerSupplierQueue
{
public:

  /// destructor
  virtual ~axPoppedTimerQ();

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

   static axPoppedTimerQ * getInstance(void);

protected:

  /// default constructor
  axPoppedTimerQ();

private:

  axPoppedTimerQ(const axPoppedTimerQ &);

  static axPoppedTimerQ * m_instance;
};

#endif // _axPoppedTimerQ_hpp_
