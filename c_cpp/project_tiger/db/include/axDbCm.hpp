
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbCm_hpp_
#define _axDbCm_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractDbObject.hpp"
#include "axListBase.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
class axInternalCm;

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axDbCm.hpp
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

class axDbCm : public axAbstractDbObject
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * BasicCmQuery;
  static AX_INT8 * CmtsCmQuery;
  static AX_INT8 * CmtsCmQueryStartup;
  static AX_INT8 * UpstreamChannelCmQuery;
  static AX_INT8 * DownstreamChannelCmQuery;
  static AX_INT8 * HfcCmQuery;
  static AX_INT8 * BasicCmUpdate;
  static AX_INT8 * BasicCmInsert;

  /// default constructor
  axDbCm();

  /**
   * IN: CM Res ID
   */
  axDbCm(DB_RESID_t);

  /**
   * IN: CM Mac Address, CMTS Res ID
   */
  axDbCm(DB_MAC_t, DB_RESID_t);

  /**
   * IN: Internal CM, CMTS Res ID
   */
  axDbCm(axInternalCm *, DB_RESID_t);

  // Copy needed
  axDbCm(const axDbCm &);

  /// destructor
  virtual ~axDbCm();

  /// assignment operator
  axDbCm & operator= (const axDbCm &);

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
  DB_OBJ_AUTO_INC_ID_t m_id;

  //
  DB_RESID_t m_cmResId;

  //
  DB_RESID_t m_cmtsResId;

  DB_RESID_t m_hfcResId;
  std::string m_hfcName;

  DB_RESID_t m_upstreamChannelResId;
  AX_UINT32  m_upstreamChannelIndex;
  std::string m_upstreamChannelName;

  DB_RESID_t m_downstreamChannelResId;
  AX_UINT32  m_downstreamChannelIndex;
  std::string m_downstreamChannelName;

  //
  DB_CM_INDEX_t  m_modemIndex;

#if 0 // moved to cm_current_status
  //
  AX_UINT32 m_onlineOfflineChangeTime;
#endif

  //
  DB_UPDATE_TIME_t   m_lastUpdated;

  //
  DB_MAC_t  m_cmMac;

  // can store fqdn, IPv(4/6) address, etc
  DB_FQDN_t  m_fqdn;

  // IPv(4/6) address, etc
  DB_IP_ADDRESS_t  m_ipAddress;

  //
  AX_UINT8  m_docsisState;

  //
  AX_UINT8  m_enduserDeviceType;

  //
  AX_UINT8  m_deleted;

  //
  AX_UINT8  m_isOnline;

  //
  AX_UINT8  m_ipAddressType;

  //
  AX_UINT8  m_alertLevel;

  /*
   * Performance fields
   */
  AX_UINT16 m_downstreamSNR;
  AX_UINT16 m_downstreamPower;
  AX_UINT16 m_upstreamPower;
  AX_UINT16 m_upstreamSNRatCmts;
  AX_UINT16 m_upstreamPowerAtCmts;
  AX_UINT32 m_t1Timeouts;
  AX_UINT32 m_t2Timeouts;
  AX_UINT32 m_t3Timeouts;
  AX_UINT32 m_t4Timeouts;

  // ***********************************
  // data members end ...
  // ***********************************

  // virtual bool queryObj(void);

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
  bool getPerformance(void);

  /**
   *
   */
  bool isEmta(void);

protected:

private:

  void instantiationInit(void);

};

#endif // _axDbCm_hpp_
