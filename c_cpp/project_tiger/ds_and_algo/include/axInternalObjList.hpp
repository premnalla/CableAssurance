
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axInternalObjList_hpp_
#define _axInternalObjList_hpp_

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
 * This class is used to ...
 * 
 * 
 * file/class: axInternalObjList.hpp
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

class axInternalObjList : public axListBase
{
public:

  /// default constructor
  axInternalObjList();

  /// destructor
  virtual ~axInternalObjList();

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
   * Overloaded from base class
   */
  virtual axObject * find(axObject *);

  /**
   * Overloaded from base class
   */
  virtual axObject * remove(axObject *);

protected:


private:

  axInternalObjList(const axInternalObjList &);

};

#endif // _axInternalObjList_hpp_
