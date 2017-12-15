
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMtaPinger_hpp_
#define _axMtaPinger_hpp_

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
 * file/class: axMtaPinger.hpp
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

class axMtaPinger : 
  public axAbstractPoller, public axAbstractSubSystem, public axListExtLock
{
public:

  /// destructor
  virtual ~axMtaPinger();

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
  static axMtaPinger * getInstance(void);

  /**
   *
   */
  virtual AX_INT32 initialize(void);

protected:

  /// default constructor
  axMtaPinger();

private:

  axMtaPinger(const axMtaPinger &);

  bool loadRunnableCollection(void);

  static axMtaPinger * m_instance;

  static bool m_initialized;
};

#endif // _axMtaPinger_hpp_
