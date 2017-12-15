
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCACfgLoader_hpp_
#define _axCACfgLoader_hpp_

//********************************************************************
// include files
//********************************************************************
#include <string>
#include "axAll.h"

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
 * file/class: axCACfgLoader.hpp
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

class axCACfgLoader 
{
public:

  /// 
  axCACfgLoader(AX_INT8 *);

  /// destructor
  virtual ~axCACfgLoader();

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
  bool loadConfig(void);

protected:

  /// default constructor
  axCACfgLoader();

private:

  // not allowed
  axCACfgLoader(const axCACfgLoader &);

  std::string    m_fileName;
};

#endif // _axCACfgLoader_hpp_
