
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSqlModifyOperation_hpp_
#define _axSqlModifyOperation_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSqlOperation.hpp"

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
 * file/class: axSqlModifyOperation.hpp
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

class axSqlModifyOperation : public axAbstractSqlOperation
{
public:

  /// destructor
  virtual ~axSqlModifyOperation();

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

  /// data constructor
  axSqlModifyOperation(axAbstractConnection *);

private:

  /// default constructor
  axSqlModifyOperation();

  axSqlModifyOperation(const axSqlModifyOperation &);

};

#endif // _axSqlModifyOperation_hpp_
