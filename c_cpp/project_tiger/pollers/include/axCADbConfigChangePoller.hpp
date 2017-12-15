
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCADbConfigChangePoller_hpp_
#define _axCADbConfigChangePoller_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axAceWorker.hpp"
#include "axListBase.hpp"

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
 * file/class: axCADbConfigChangePoller.hpp
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

class axCADbConfigChangePoller : public axAceWorker
{
public:

  /// default constructor
  axCADbConfigChangePoller();

  /// destructor
  virtual ~axCADbConfigChangePoller();

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

  axCADbConfigChangePoller(const axCADbConfigChangePoller &);

  void checkForCmtsChanges(void);
  void checkDbBasedConfigChanges(void);
  void preMarkAllCmts(void);
  void deleteUnreconciledCmts(void);
  void addAllNewCmts(axListBase &);
};

#endif // _axCADbConfigChangePoller_hpp_
