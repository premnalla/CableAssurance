
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCmtsPoller_hpp_
#define _axCmtsPoller_hpp_

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
 * file/class: axCmtsPoller.hpp
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

class axCmtsPoller : public axAbstractPoller, public axAbstractSubSystem, public axListExtLock
{
public:

  /// destructor
  virtual ~axCmtsPoller();

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
  static axCmtsPoller * getInstance(void);

  /**
   *
   */
  virtual AX_INT32 initialize(void);


protected:

  /// default constructor
  axCmtsPoller();


private:

  axCmtsPoller(const axCmtsPoller &);

  bool loadRunnableCollection(void);

  static axCmtsPoller * m_instance;

  static bool m_initialized;

};

#endif // _axCmtsPoller_hpp_
