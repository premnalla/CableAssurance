
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbConnectionHelper_hpp_
#define _axDbConnectionHelper_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractConnectionHelper.hpp"
#include "axDbConnection.hpp"
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
 * file/class: axDbConnectionHelper.hpp
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

class axDbConnectionHelper : public axAbstractConnectionHelper
{
public:

  /// default constructor
  axDbConnectionHelper();

  /// destructor
  virtual ~axDbConnectionHelper();

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

  virtual axDbConnection * getConnection(void);

protected:

  void setConnection(axDbConnection *);

  void setConnectionFactory(axDbConnectionFactory *);
  axDbConnectionFactory * getConnectionFactory(void);

private:

  axDbConnectionHelper(const axDbConnectionHelper &);

  axDbConnection * m_conn;
  axDbConnectionFactory * m_connFactory;

};

#endif // _axDbConnectionHelper_hpp_
