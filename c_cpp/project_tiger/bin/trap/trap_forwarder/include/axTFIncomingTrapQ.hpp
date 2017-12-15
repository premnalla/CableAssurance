
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axTFIncomingTrapQ_hpp_
#define _axTFIncomingTrapQ_hpp_

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
 * file/class: axTFIncomingTrapQ.hpp
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

class axTFIncomingTrapQ : public axConsumerSupplierQueue
{
public:

  /// destructor
  virtual ~axTFIncomingTrapQ();

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

   static axTFIncomingTrapQ * getInstance(void);

protected:

  /// default constructor
  axTFIncomingTrapQ();

private:

  axTFIncomingTrapQ(const axTFIncomingTrapQ &);

  static axTFIncomingTrapQ * m_instance;
};

#endif // _axTFIncomingTrapQ_hpp_
