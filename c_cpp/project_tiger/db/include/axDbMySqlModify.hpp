
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbMySqlModify_hpp_
#define _axDbMySqlModify_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axSqlModifyOperation.hpp"

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
 * file/class: axDbMySqlModify.hpp
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

class axDbMySqlModify : public axSqlModifyOperation
{
public:

  /// data constructor
  axDbMySqlModify(axAbstractConnection *);

  /// destructor
  virtual ~axDbMySqlModify();

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
  axDbMySqlModify();

private:

  axDbMySqlModify(const axDbMySqlModify &);

};

#endif // _axDbMySqlModify_hpp_
