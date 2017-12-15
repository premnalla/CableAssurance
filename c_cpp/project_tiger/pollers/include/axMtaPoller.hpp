
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMtaPoller_hpp_
#define _axMtaPoller_hpp_

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
 * file/class: axMtaPoller.hpp
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

class axMtaPoller : 
  public axAbstractPoller, public axAbstractSubSystem, public axListExtLock
{
public:

  /// destructor
  virtual ~axMtaPoller();

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
  static axMtaPoller * getInstance(void);

  /**
   *
   */
  virtual AX_INT32 initialize(void);

protected:

  /// default constructor
  axMtaPoller();

private:

  axMtaPoller(const axMtaPoller &);

  bool loadRunnableCollection(void);

  static axMtaPoller * m_instance;

  static bool m_initialized;
};

#endif // _axMtaPoller_hpp_
