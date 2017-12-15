
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCountsSubSystem_hpp_
#define _axCountsSubSystem_hpp_

//********************************************************************
// include files
//********************************************************************
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
 * file/class: axCountsSubSystem.hpp
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

class axCountsSubSystem : public axAbstractSubSystem
{
public:

  /// destructor
  virtual ~axCountsSubSystem();

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
  static axCountsSubSystem * getInstance(void);


protected:

  /// default constructor
  axCountsSubSystem();


private:

  axCountsSubSystem(const axCountsSubSystem &);

  static axCountsSubSystem * m_instance;

};

#endif // _axCountsSubSystem_hpp_
