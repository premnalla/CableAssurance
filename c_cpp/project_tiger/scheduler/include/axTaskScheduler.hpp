
//********************************************************************
// OBSOLETE
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axTaskScheduler_hpp_
#define _axTaskScheduler_hpp_

//********************************************************************
// include files
//********************************************************************
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
 * file/class: axTaskScheduler.hpp
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

class axTaskScheduler : public axAceWorker
{
public:

  /// default constructor
  axTaskScheduler();

  /// destructor
  virtual ~axTaskScheduler();

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

  axTaskScheduler(const axTaskScheduler &);

};

#endif // _axTaskScheduler_hpp_