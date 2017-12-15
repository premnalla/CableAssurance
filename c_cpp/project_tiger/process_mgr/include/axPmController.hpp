
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axPmController_hpp_
#define _axPmController_hpp_

//********************************************************************
// include files
//********************************************************************
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
 * file/class: axPmController.hpp
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

class axPmController 
{
public:

  /// destructor
  virtual ~axPmController();

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
  static axPmController * getInstance(void);

  /**
   * 
   */
  void setExit(void);
  bool isExit(void);

protected:

  /**
   * default constructor
   */
  axPmController();

private:

  /// not allowed
  axPmController(const axPmController &);

  ///
  static axPmController  * m_instance;

  ///
  bool               m_isExit;
};

#endif // _axPmController_hpp_
