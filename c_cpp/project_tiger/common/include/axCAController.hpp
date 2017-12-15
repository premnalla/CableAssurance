
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCAController_hpp_
#define _axCAController_hpp_

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
 * file/class: axCAController.hpp
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

class axCAController 
{
public:

  /// destructor
  virtual ~axCAController();

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
  static axCAController * getInstance(void);

  /**
   * 
   */
  void setExit(void);
  bool isExit(void);

  /**
   * 
   */
  void setRereadConfig(bool);
  bool isRereadConfig(void);

protected:

  /**
   * default constructor
   */
  axCAController();

private:

  /// not allowed
  axCAController(const axCAController &);

  ///
  static axCAController  * m_instance;

  ///
  bool               m_isExit;
  bool               m_isRereadConfig;
};

#endif // _axCAController_hpp_
