
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbHfc_hpp_
#define _axDbHfc_hpp_

//********************************************************************
// include files
//********************************************************************
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
 * file/class: axDbHfc.hpp
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

class axDbHfc : public axAbstractDbObject
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * BasicHfcQuery;
  static AX_INT8 * BasicHfcUpdate;
  static AX_INT8 * CmtsHfcQuery;
  static AX_INT8 * CmtsHfcQueryStartup;
  static AX_INT8 * BasicHfcInsert;

  /// default constructor
  axDbHfc();

  /**
   * IN: HFC Res ID
   */
  axDbHfc(DB_RESID_t);

  /**
   * IN: HFC Name, CMTS Res ID
   */
  axDbHfc(DB_RESID_t, AX_INT8 *);

  /// destructor
  virtual ~axDbHfc();

  // ***********************************
  // data members begin ...
  // ***********************************

  //
  DB_OBJ_AUTO_INC_ID_t m_id;

  //
  DB_RESID_t m_hfcResId;

  //
  DB_RESID_t m_cmtsResId;

  //
  DB_UPDATE_TIME_t   m_lastUpdated;

  //
  DB_CHANNEL_NAME_t  m_hfcName;

  //
  AX_UINT8  m_alertLevel;

  //
  AX_UINT8  m_deleted;

  // ***********************************
  // data members end ...
  // ***********************************

  /**
   *
   */
  virtual AX_UINT8 getResourceType(void);

  /**
   *
   */
  virtual bool getRow(void);
  virtual bool insertRow(void);
  virtual bool updateRow(void);

#if 0
  virtual bool insertOrUpdateRow(void);
#endif

  virtual bool deleteRow(void);

protected:

private:

  axDbHfc(const axDbHfc &);

  void instantiationInit(void);

};

#endif // _axDbHfc_hpp_
