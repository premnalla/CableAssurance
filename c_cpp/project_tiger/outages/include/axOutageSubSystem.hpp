
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axOutageSubSystem_hpp_
#define _axOutageSubSystem_hpp_

//********************************************************************
// include files
//********************************************************************
#include <pthread.h>
#include "axAbstractSubSystem.hpp"

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
 * file/class: axOutageSubSystem.hpp
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

class axOutageSubSystem : public axAbstractSubSystem
{
public:

  /// destructor
  virtual ~axOutageSubSystem();

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

  ///
  static axOutageSubSystem * getInstance(void);

  ///
  AX_UINT32 getNextOutageId(void);

protected:

  /// default constructor
  axOutageSubSystem();


private:

  axOutageSubSystem(const axOutageSubSystem &);

  static axOutageSubSystem * m_instance;

  AX_UINT32                  m_nextId;
  pthread_mutex_t            m_lock;
};

#endif // _axOutageSubSystem_hpp_
