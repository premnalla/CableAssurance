
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAlarmSubSystem_hpp_
#define _axAlarmSubSystem_hpp_

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
 * file/class: axAlarmSubSystem.hpp
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

class axAlarmSubSystem : public axAbstractSubSystem
{
public:

  /// destructor
  virtual ~axAlarmSubSystem();

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
  static axAlarmSubSystem * getInstance(void);

  ///
  AX_UINT32 getNextAlarmId(void);

protected:

  /// default constructor
  axAlarmSubSystem();


private:

  axAlarmSubSystem(const axAlarmSubSystem &);

  static axAlarmSubSystem * m_instance;

  AX_UINT32                  m_nextId;
  pthread_mutex_t            m_lock;
};

#endif // _axAlarmSubSystem_hpp_
