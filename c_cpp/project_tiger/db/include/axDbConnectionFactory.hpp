
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbConnectionFactory_hpp_
#define _axDbConnectionFactory_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axObject.hpp"
#include "axAbstractFactory.hpp"
#include "axDbConnection.hpp"
#include "axList_mts.hpp"

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
 * file/class: axDbConnectionFactory.hpp
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

class axDbConnectionFactory : public axObject, public axAbstractFactory
{
public:

  /// destructor
  virtual ~axDbConnectionFactory();

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

   virtual void initialize(void)=0;

   typedef enum axDbConnectionType_e {
     UNKNOWN,
     READ,
     WRITE,
     LAST
   } axDbConnectionType_e;

   virtual axDbConnection * getReadOnlyConnection();
   virtual void releaseReadOnlyConnection(axDbConnection *);

   virtual axDbConnection * getWriteConnection();
   virtual void releaseWriteConnection(axDbConnection *);

protected:

  /// default constructor
  axDbConnectionFactory();

  ///
  void addReadOnlyConnection(axDbConnection *);
  void addWriteConnection(axDbConnection *);

private:

  axDbConnectionFactory(const axDbConnectionFactory &);

  axList_mts  m_readOnlyConns;
  axList_mts  m_readWriteConns;

};

#endif // _axDbConnectionFactory_hpp_
