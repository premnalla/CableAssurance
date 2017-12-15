
//********************************************************************
// Copyright (c) 2007 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbAbstractTopoContainer_hpp_
#define _axDbAbstractTopoContainer_hpp_

//********************************************************************
// include files
//********************************************************************
#include <string>
#include "axAbstractDbObject.hpp"

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
 * file/class: axDbAbstractTopoContainer.hpp
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

class axDbAbstractTopoContainer : public axAbstractDbObject
{
public:

  /// default constructor
  axDbAbstractTopoContainer();

  /// destructor
  virtual ~axDbAbstractTopoContainer();

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
  AX_UINT16    m_id;

  //
  AX_UINT16    m_ipAddressType;

  //
  std::string  m_name;

  //
  std::string  m_host;

  //
  AX_UINT8     m_isDeleted;

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

  virtual bool getQuerySql(AX_INT8 *, size_t)=0;

private:

  axDbAbstractTopoContainer(const axDbAbstractTopoContainer &);

};

#endif // _axDbAbstractTopoContainer_hpp_
