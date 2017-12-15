
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbLocalSystem_hpp_
#define _axDbLocalSystem_hpp_

//********************************************************************
// include files
//********************************************************************
// #include "axDbDataTypes.hpp"
// #include "axDbConnection.hpp"
#include <string>
#include "axAbstractDbObject.hpp"

//********************************************************************
// definitions/macros
//********************************************************************
#define DB_LOCAL_SYSTEM_UNKNOWN                                      0
#define DB_LOCAL_SYSTEM_ENTERPRISE                                   1
#define DB_LOCAL_SYSTEM_REGION                                       2
#define DB_LOCAL_SYSTEM_MARKET                                       3
#define DB_LOCAL_SYSTEM_BLADE                                        4

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axDbLocalSystem.hpp
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

class axDbLocalSystem : public axAbstractDbObject
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * Update;
  static AX_INT8 * Insert;

  /// default constructor
  axDbLocalSystem();

  /// destructor
  virtual ~axDbLocalSystem();

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

  // ***********************************
  // data members begin ...
  // ***********************************

  //
  AX_UINT32    m_systemType;

  //
  std::string  m_systemName;

  //
  std::string  m_parentHost;

  //
  AX_UINT16    m_parentIpType;

  // ***********************************
  // data members end ...
  // ***********************************

  /**
   *
   */
  virtual bool getRow(void);
  virtual bool insertRow(void);
  virtual bool updateRow(void);
  virtual bool deleteRow(void);

protected:

private:

  axDbLocalSystem(const axDbLocalSystem &);

};

#endif // _axDbLocalSystem_hpp_
