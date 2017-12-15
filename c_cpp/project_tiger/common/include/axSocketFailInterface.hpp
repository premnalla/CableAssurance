
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSocketFailInterface_hpp_
#define _axSocketFailInterface_hpp_

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
 * file/class: axSocketFailInterface.hpp
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

class axSocketFailInterface 
{
public:

  /// destructor
  virtual ~axSocketFailInterface();

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
  virtual void handleReadFailure(AX_INT32)=0;

  /**
   * 
   */
  virtual void handleWriteFailure(AX_INT32)=0;

protected:

  /**
   * default constructor
   */
  axSocketFailInterface();


private:

  /// not allowed
  axSocketFailInterface(const axSocketFailInterface &);

};

#endif // _axSocketFailInterface_hpp_
