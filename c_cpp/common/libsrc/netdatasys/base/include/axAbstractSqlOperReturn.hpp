
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractSqlOperReturn_hpp_
#define _axAbstractSqlOperReturn_hpp_

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
 * file/class: axAbstractSqlOperReturn.hpp
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

class axAbstractSqlOperReturn
{
public:

  /// destructor
  virtual ~axAbstractSqlOperReturn();

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
  axAbstractSqlOperReturn(axAbstractConnection *);

  axAbstractConnection * getConnection(void);

private:

  /// default constructor
  axAbstractSqlOperReturn();

  axAbstractSqlOperReturn(const axAbstractSqlOperReturn &);

  axAbstractConnection * m_conn;

};

#endif // _axAbstractSqlOperReturn_hpp_
