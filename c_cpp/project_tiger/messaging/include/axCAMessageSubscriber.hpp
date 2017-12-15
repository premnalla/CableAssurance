
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axCAMessageSubscriber_hpp_
#define _axCAMessageSubscriber_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractMessageSubscriber.hpp"

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
 * file/class: axCAMessageSubscriber.hpp
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

class axCAMessageSubscriber : public axAbstractMessageSubscriber
{
public:

  /// destructor
  virtual ~axCAMessageSubscriber();

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
  axCAMessageSubscriber();


private:

  axCAMessageSubscriber(const axCAMessageSubscriber &);

};

#endif // _axCAMessageSubscriber_hpp_
