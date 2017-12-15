
//********************************************************************
// OBSOLETED !!!
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractDbConnection_hpp_
#define _axAbstractDbConnection_hpp_

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
 * file/class: axAbstractDbConnection.hpp
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

class axAbstractDbConnection : public axAbstractConnection
{
public:

  /// destructor
  virtual ~axAbstractDbConnection();

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
  axAbstractDbConnection();


private:

  axAbstractDbConnection(const axAbstractDbConnection &);

};

#endif // _axAbstractDbConnection_hpp_
