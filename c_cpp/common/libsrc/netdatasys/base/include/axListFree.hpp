
//********************************************************************
// Copyright (c) 2006 by Prem Nallasivampillai. All rights reserved.
//********************************************************************

#ifndef _axListFree_hpp_
#define _axListFree_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axListBase.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is the same as axListBase except for one Major difference
 * being, when this List is freed, it WILL also Free all the elements in
 * the list.
 * 
 * file/class: axListFree.hpp
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

class axListFree : public axListBase
{
public:

  /// default constructor
  axListFree();

  /// destructor
  virtual ~axListFree();

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

private:

  ///
  axListFree(const axListFree &);

};

#endif // _axListFree_hpp_
