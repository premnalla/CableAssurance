
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbConnection_cpp_
#define _axDbConnection_cpp_

//********************************************************************
// include files
//********************************************************************
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
 * file/class: axDbConnection.hpp
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

class axDbConnection : public axAbstractConnection
{
public:

  /// destructor
  virtual ~axDbConnection();

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

#if 0
   virtual void initialize(void);
   virtual bool openConnection(void);
   virtual bool closeConnection(void);
   virtual bool testConnection(void);
#endif

protected:

  /// default constructor
  axDbConnection();


private:

  axDbConnection(const axDbConnection &);

};

#endif // _axDbConnection_cpp_
