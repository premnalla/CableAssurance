
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSoakTimerQ_hpp_
#define _axSoakTimerQ_hpp_

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
 * file/class: axSoakTimerQ.hpp
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

class axSoakTimerQ : public axGenericTimerQ
{
public:

  /// destructor
  virtual ~axSoakTimerQ();

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

   static axSoakTimerQ * getInstance(void);

protected:

  /// default constructor
  axSoakTimerQ();

private:

  axSoakTimerQ(const axSoakTimerQ &);

  static axSoakTimerQ * m_instance;
};

#endif // _axSoakTimerQ_hpp_
