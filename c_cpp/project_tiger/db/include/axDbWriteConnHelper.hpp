
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbWriteConnHelper_hpp_
#define _axDbWriteConnHelper_hpp_

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
 * file/class: axDbWriteConnHelper.hpp
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

class axDbWriteConnHelper : public axDbConnectionHelper
{
public:

  /// data constructor
  axDbWriteConnHelper(axDbConnectionFactory *);

  /// destructor
  virtual ~axDbWriteConnHelper();

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
  axDbWriteConnHelper();


private:

  axDbWriteConnHelper(const axDbWriteConnHelper &);

};

#endif // _axDbWriteConnHelper_hpp_
