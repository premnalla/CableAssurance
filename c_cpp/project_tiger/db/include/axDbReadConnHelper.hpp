
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbReadConnHelper_hpp_
#define _axDbReadConnHelper_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbConnectionHelper.hpp"
#include "axDbConnectionFactory.hpp"

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
 * file/class: axDbReadConnHelper.hpp
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

class axDbReadConnHelper : public axDbConnectionHelper
{
public:

  /// data constructor
  axDbReadConnHelper(axDbConnectionFactory *);

  /// destructor
  virtual ~axDbReadConnHelper();

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
  axDbReadConnHelper();


private:

  axDbReadConnHelper(const axDbReadConnHelper &);

};

#endif // _axDbReadConnHelper_hpp_
