
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSnmpSubSystem_hpp_
#define _axSnmpSubSystem_hpp_

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
 * file/class: axSnmpSubSystem.hpp
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

class axSnmpSubSystem : public axAbstractSubSystem
{
public:

  /// destructor
  virtual ~axSnmpSubSystem();

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

  static axSnmpSubSystem * getInstance(void);

  bool initialize(void);

protected:

  /// default constructor
  axSnmpSubSystem();


private:

  axSnmpSubSystem(const axSnmpSubSystem &);

  static axSnmpSubSystem * m_instance;
  static bool              m_initialized;

};

#endif // _axSnmpSubSystem_hpp_
