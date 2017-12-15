
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axPoppedTimerProcessor_hpp_
#define _axPoppedTimerProcessor_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axAceWorker.hpp"

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
 * file/class: axPoppedTimerProcessor.hpp
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

class axPoppedTimerProcessor : public axAceWorker
{
public:

  /// default constructor
  axPoppedTimerProcessor();

  /// destructor
  virtual ~axPoppedTimerProcessor();

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

protected:

private:

  axPoppedTimerProcessor(const axPoppedTimerProcessor &);

};

#endif // _axPoppedTimerProcessor_hpp_
