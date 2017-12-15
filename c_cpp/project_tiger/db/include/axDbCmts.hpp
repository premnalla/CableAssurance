
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbCmts_hpp_
#define _axDbCmts_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractDbObject.hpp"
#include "axAbsDbSnmpVerAttrs.hpp"

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
 * file/class: axDbCmts.hpp
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

class axDbCmts : public axAbstractDbObject
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * Query;
  static AX_INT8 * QueryAll;
  static AX_INT8 * QueryAllStartup;
  static AX_INT8 * Insert;
  static AX_INT8 * Update;
  static AX_INT8 * Delete;

  /// default constructor
  axDbCmts();

  // copy constructor
  axDbCmts(const axDbCmts &);

  /// destructor
  virtual ~axDbCmts();

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


  /**
   * data members...
   */

  //
  DB_OBJ_AUTO_INC_ID_t m_id;

  //
  DB_RESID_t m_cmtsResId;

  //
  // DB_RESID_t m_bladeResId;

  //
  DB_UPDATE_TIME_t   m_lastUpdated;

  //
  DB_CMTS_NAME_t m_cmtsName;

  // can store fqdn, IPv(4/6) address, etc
  DB_FQDN_t  m_fqdn;

  //
  DB_IP_ADDRESS_t  m_ipAddress;

  //
  AX_UINT8  m_cmtsSnmpVersion;
  AX_UINT8  m_cmSnmpVersion;
  AX_UINT8  m_mtaSnmpVersion;

  //
  AX_UINT8  m_onlineState;

  //
  AX_UINT8  m_deleted;

  //
  AX_UINT8  m_ipAddressType;

  //
  AX_UINT8  m_alertLevel;

  /**
   *
   */
  virtual AX_UINT8 getResourceType(void);

  /**
   *
   */
  virtual bool getRow(void);
  virtual bool getRow(AX_INT8 *);
  virtual bool getRows(axListBase &);
  virtual bool getRows(axListBase &, AX_INT8 *);
  virtual bool insertRow(void);
  virtual bool updateRow(void);
  virtual bool deleteRow(void);

  /**
   *
   */
  axAbsDbSnmpVerAttrs * getCmtsSnmpVersionAttrs(void);
  axAbsDbSnmpVerAttrs * getCmSnmpVersionAttrs(void);
  axAbsDbSnmpVerAttrs * getMtaSnmpVersionAttrs(void);

protected:

private:

  void initAtInstantiation(void);

  // DB_CMTS_FIELDS m_cmtsData;

  axAbsDbSnmpVerAttrs * commonGetCmtsSnmpV2CAttrs(char *);
};

#endif // _axDbCmts_hpp_
