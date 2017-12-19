
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractSqlOperation_hpp_
#define _axAbstractSqlOperation_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAll.h"
#include "axAbstractConnection.hpp"

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
 * file/class: axAbstractSqlOperation.hpp
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

class axAbstractSqlOperation
{
public:

  /// destructor
  virtual ~axAbstractSqlOperation();

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

   // virtual void fooXXX(void);

protected:

  /// default constructor
  axAbstractSqlOperation(axAbstractConnection *);

  axAbstractConnection * getConnection(void);

private:

  /// default constructor
  axAbstractSqlOperation();

  axAbstractSqlOperation(const axAbstractSqlOperation &);

  axAbstractConnection * m_conn;

};

#endif // _axAbstractSqlOperation_hpp_
