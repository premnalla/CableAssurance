
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSoakTimerQProcessor_hpp_
#define _axSoakTimerQProcessor_hpp_

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
 * file/class: axSoakTimerQProcessor.hpp
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

class axSoakTimerQProcessor : public axAceWorker
{
public:

  /// default constructor
  axSoakTimerQProcessor();

  /// destructor
  virtual ~axSoakTimerQProcessor();

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

  axSoakTimerQProcessor(const axSoakTimerQProcessor &);

};

#endif // _axSoakTimerQProcessor_hpp_
