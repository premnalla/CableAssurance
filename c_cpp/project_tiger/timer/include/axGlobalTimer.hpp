
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axGlobalTimer_hpp_
#define _axGlobalTimer_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axGenericTimerQ.hpp"

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
 * file/class: axGlobalTimer.hpp
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

class axGlobalTimer : public axGenericTimerQ
{
public:

  /// destructor
  virtual ~axGlobalTimer();

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

   static axGlobalTimer * getInstance(void);

protected:

  /// default constructor
  axGlobalTimer();

private:

  axGlobalTimer(const axGlobalTimer &);

  static axGlobalTimer * m_instance;
};

#endif // _axGlobalTimer_hpp_
