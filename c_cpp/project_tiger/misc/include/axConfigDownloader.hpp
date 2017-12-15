
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axConfigDownloader_hpp_
#define _axConfigDownloader_hpp_

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
 * file/class: axConfigDownloader.hpp
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

class axConfigDownloader 
{
public:

  /**
   * default constructor
   */
  axConfigDownloader();

  /// destructor
  virtual ~axConfigDownloader();

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
  void downloadConfig(void);

protected:


private:

  /// not allowed
  axConfigDownloader(const axConfigDownloader &);

};

#endif // _axConfigDownloader_hpp_
