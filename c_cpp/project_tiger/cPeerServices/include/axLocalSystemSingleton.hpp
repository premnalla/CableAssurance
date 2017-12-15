
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axLocalSystemSingleton_hpp_
#define _axLocalSystemSingleton_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axAbstractCPeerServices.hpp"

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
 * file/class: axLocalSystemSingleton.hpp
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

class axLocalSystemSingleton 
{
public:

  /// destructor
  virtual ~axLocalSystemSingleton();

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
  static axLocalSystemSingleton * getInstance(void);

  /**
   * 
   */
  axAbstractCPeerServices * getCPeerServices(void);

protected:

  /**
   * default constructor
   */
  axLocalSystemSingleton();


private:

  /// not allowed
  axLocalSystemSingleton(const axLocalSystemSingleton &);

  static axLocalSystemSingleton * m_instance;

  AX_UINT32 m_locSysType;

};

#endif // _axLocalSystemSingleton_hpp_
