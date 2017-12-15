
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbMySqlConnFactory_hpp_
#define _axDbMySqlConnFactory_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbConnectionFactory.hpp"

//********************************************************************
// definitions/macros
//********************************************************************
#define AX_DB_MAX_READ_CONNS  100
#define AX_DB_MAX_WRITE_CONNS 100

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axDbMySqlConnFactory.hpp
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

class axDbMySqlConnFactory : public axDbConnectionFactory
{
public:

  /// destructor
  virtual ~axDbMySqlConnFactory();

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

   static axDbMySqlConnFactory * getInstance(void);

   virtual void initialize(void);

   // virtual axDbConnection * getConnection(axDbConnectionType_e);

protected:

  /// default constructor
  axDbMySqlConnFactory();

private:

  axDbMySqlConnFactory(const axDbMySqlConnFactory &);

  static axDbMySqlConnFactory * m_instance;

  static bool m_initialized;

};

#endif // _axDbMySqlConnFactory_hpp_
