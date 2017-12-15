
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbXResource_hpp_
#define _axDbXResource_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbDataTypes.hpp"
#include "axAbstractDbObject.hpp"
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
 * file/class: axDbXResource.hpp
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

class axDbXResource : public axAbstractDbObject
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * BasicQuery;
  static AX_INT8 * BasicUpdate;
  static AX_INT8 * BasicInsert;


  /// default constructor
  axDbXResource();

  /// data constructor
  axDbXResource(DB_RESID_t, DB_RESTYPE_t);

  /// data constructor
  axDbXResource(DB_RESID_t);

  /// destructor
  virtual ~axDbXResource();

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
  DB_RESID_t         m_resId;

  //
  AX_UINT8           m_resType;

  // ***********************************
  // data members end ...
  // ***********************************

  /**
   *
   */
  virtual bool getRow(void);
  virtual bool insertRow(void);
  virtual bool insertRow(axDbConnection *);
  virtual bool updateRow(void);
  virtual bool deleteRow(void);

protected:

private:

  axDbXResource(const axDbXResource &);

  // DB_RESOURCE_FIELDS m_resData;

};

#endif // _axDbXResource_hpp_
