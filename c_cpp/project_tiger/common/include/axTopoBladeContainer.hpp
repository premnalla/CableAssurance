
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axTopoBladeContainer_hpp_
#define _axTopoBladeContainer_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractTopoContainerObj.hpp"

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
 * file/class: axTopoBladeContainer.hpp
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

class axTopoBladeContainer : public axAbstractTopoContainerObj
{
public:

  /**
   * data constructor
   */
  axTopoBladeContainer(AX_INT32, AX_UINT16, AX_INT8 *, AX_INT8 *);

  /**
   * data constructor
   */
  axTopoBladeContainer(AX_INT32, axDbAbstractTopoContainer &);

  /**
   * data constructor
   */
  axTopoBladeContainer(AX_UINT16);

  /// destructor
  virtual ~axTopoBladeContainer();

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

  /**
   * default constructor
   */
  axTopoBladeContainer();


private:

  /// not allowed
  axTopoBladeContainer(const axTopoBladeContainer &);

  AX_INT32 m_containerFd;
};

#endif // _axTopoBladeContainer_hpp_
