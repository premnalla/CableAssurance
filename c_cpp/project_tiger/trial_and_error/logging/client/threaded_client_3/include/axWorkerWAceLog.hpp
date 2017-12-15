
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axWorkerWAceLog_hpp_
#define _axWorkerWAceLog_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axWorker.hpp"

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
 * file/class: axWorkerWAceLog.hpp
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

class axWorkerWAceLog : public axWorker
{
public:

  /// default constructor
  axWorkerWAceLog();

  /// destructor
  virtual ~axWorkerWAceLog();

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

  virtual AX_INT32 start(void);
  virtual AX_INT32 run(void);
  // virtual AX_INT32 init(void);

protected:


private:

  axWorkerWAceLog(const axWorkerWAceLog &);

};

#endif // _axWorkerWAceLog_hpp_
