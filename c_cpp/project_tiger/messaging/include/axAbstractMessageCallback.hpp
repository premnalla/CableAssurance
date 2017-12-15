
//********************************************************************
// !!! OBSOLETED !!!
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractMessageCallback_hpp_
#define _axAbstractMessageCallback_hpp_

//********************************************************************
// include files
//********************************************************************

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
 * file/class: axAbstractMessageCallback.hpp
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

class axAbstractMessageCallback 
{
public:

  /// destructor
  virtual ~axAbstractMessageCallback();

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

  virtual bool timerCallback(void)=0;

protected:

  /// default constructor
  axAbstractMessageCallback();


private:

  axAbstractMessageCallback(const axAbstractMessageCallback &);

};

#endif // _axAbstractMessageCallback_hpp_
