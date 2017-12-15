
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axGenericTimerQ_hpp_
#define _axGenericTimerQ_hpp_

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
 * file/class: axGenericTimerQ.hpp
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

class axGenericTimerQ : public axConsumerSupplierQueue
{
public:

  /// destructor
  virtual ~axGenericTimerQ();

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
   * Override from axObject
   *
   * @return
   * @see
   */
  virtual axObject * add(axObject *);


protected:

  /// default constructor
  axGenericTimerQ();

private:

  axGenericTimerQ(const axGenericTimerQ &);

};

#endif // _axGenericTimerQ_hpp_
