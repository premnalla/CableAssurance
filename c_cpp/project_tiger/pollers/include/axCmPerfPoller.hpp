
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmPerfPoller_hpp_
#define _axCmPerfPoller_hpp_

//********************************************************************
// include files
//********************************************************************
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
 * file/class: axCmPerfPoller.hpp
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

class axCmPerfPoller : 
  public axAbstractPoller, public axAbstractSubSystem, public axListExtLock
{
public:

  /// destructor
  virtual ~axCmPerfPoller();

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
  static axCmPerfPoller * getInstance(void);

  /**
   *
   */
  virtual AX_INT32 initialize(void);

protected:

  /// default constructor
  axCmPerfPoller();

private:

  axCmPerfPoller(const axCmPerfPoller &);

  bool loadRunnableCollection(void);

  static axCmPerfPoller * m_instance;

  static bool m_initialized;
};

#endif // _axCmPerfPoller_hpp_
