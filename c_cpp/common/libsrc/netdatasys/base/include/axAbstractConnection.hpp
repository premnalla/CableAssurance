
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractConnection_hpp_
#define _axAbstractConnection_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"

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
 * file/class: axAbstractConnection.hpp
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

class axAbstractConnection : public axObject
{
public:

  /// destructor
  virtual ~axAbstractConnection();

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

   virtual bool openConnection(void)=0;
   virtual bool closeConnection(void)=0;
   virtual bool testConnection(void)=0;
   virtual bool isConnected(void)=0;

   virtual void * getConnectionHandle(void)=0;

protected:

  /// default constructor
  axAbstractConnection();


private:

  axAbstractConnection(const axAbstractConnection &);

};

#endif // _axAbstractConnection_hpp_
