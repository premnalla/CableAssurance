
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axSqlQueryOperation_hpp_
#define _axSqlQueryOperation_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractSqlOperation.hpp"
#include "axSqlResultSet.hpp"

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
 * file/class: axSqlQueryOperation.hpp
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

class axSqlQueryOperation : public axAbstractSqlOperation
{
public:

  /// destructor
  virtual ~axSqlQueryOperation();

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


  ///
  axSqlResultSet * getResultSet(void);

protected:

  /// data constructor
  axSqlQueryOperation(axAbstractConnection *);

  ///
  void setResultSet(axSqlResultSet *);

private:

  /// default constructor
  axSqlQueryOperation();

  axSqlQueryOperation(const axSqlQueryOperation &);

  axSqlResultSet * m_resultSet;

};

#endif // _axSqlQueryOperation_hpp_
