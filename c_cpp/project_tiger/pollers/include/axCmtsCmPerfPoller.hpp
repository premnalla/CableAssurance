
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmtsCmPerfPoller_hpp_
#define _axCmtsCmPerfPoller_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axAbstractPoller.hpp"
#include "axAbstractSubSystem.hpp"
#include "axListExtLock.hpp"

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
 * file/class: axCmtsCmPerfPoller.hpp
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

class axCmtsCmPerfPoller : 
  public axAbstractPoller, public axAbstractSubSystem, public axListExtLock
{
public:

  /// destructor
  virtual ~axCmtsCmPerfPoller();

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
   *
   */
  static axCmtsCmPerfPoller * getInstance(void);

  /**
   *
   */
  virtual AX_INT32 initialize(void);


protected:

  /// default constructor
  axCmtsCmPerfPoller();


private:

  axCmtsCmPerfPoller(const axCmtsCmPerfPoller &);

  bool loadRunnableCollection(void);

  static axCmtsCmPerfPoller * m_instance;

  static bool m_initialized;

};

#endif // _axCmtsCmPerfPoller_hpp_
