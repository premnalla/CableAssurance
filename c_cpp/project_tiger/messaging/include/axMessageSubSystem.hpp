
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMessageSubSystem_hpp_
#define _axMessageSubSystem_hpp_

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
 * file/class: axMessageSubSystem.hpp
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

class axMessageSubSystem : public axAbstractSubSystem
{
public:

  /// destructor
  virtual ~axMessageSubSystem();

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
  static axMessageSubSystem * getInstance(void);

  ///
  AX_UINT32 getNewMessageId(void);

protected:

  /// default constructor
  axMessageSubSystem();


private:

  axMessageSubSystem(const axMessageSubSystem &);

  static axMessageSubSystem * m_instance;

  AX_UINT32                  m_nextId;
  pthread_mutex_t            m_lock;
};

#endif // _axMessageSubSystem_hpp_
