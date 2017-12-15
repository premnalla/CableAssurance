
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbMySqlConnection_hpp_
#define _axDbMySqlConnection_hpp_

//********************************************************************
// include files
//********************************************************************
#include <mysql.h>
#include "axDbConnection.hpp"

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
 * file/class: axDbMySqlConnection.hpp
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

class axDbMySqlConnection : public axDbConnection
{
public:

  /// default constructor
  axDbMySqlConnection();

  /// destructor
  virtual ~axDbMySqlConnection();

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

   virtual void initialize(void);
   virtual bool openConnection(void);
   virtual bool closeConnection(void);
   virtual bool testConnection(void);
   virtual bool isConnected(void);

   virtual void * getConnectionHandle(void);

   bool setAutoCommitOn(void);
   bool setAutoCommitOff(void);
   // bool isAutoCommitOn(void);

protected:


private:

  axDbMySqlConnection(const axDbMySqlConnection &);

  MYSQL m_dbHandle;
  bool  m_connected;

};

#endif // _axDbMySqlConnection_hpp_
