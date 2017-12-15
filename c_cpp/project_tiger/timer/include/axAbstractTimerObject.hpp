
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractTimerObject_hpp_
#define _axAbstractTimerObject_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractTimerCallback.hpp"
#include "axAbstractTimerData.hpp"

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
 * file/class: axAbstractTimerObject.hpp
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

class axAbstractTimerObject : 
  public axAbstractTimerData, public axAbstractTimerCallback
{
public:

  /// data constructor
  axAbstractTimerObject(time_t);

  /// destructor
  virtual ~axAbstractTimerObject();

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


protected:

  /// default constructor
  axAbstractTimerObject();

private:

  axAbstractTimerObject(const axAbstractTimerObject &);

};

#endif // _axAbstractTimerObject_hpp_
