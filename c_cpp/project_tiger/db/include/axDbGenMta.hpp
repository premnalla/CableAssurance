
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbGenMta_hpp_
#define _axDbGenMta_hpp_

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
class axInternalGenMta;

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axDbGenMta.hpp
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

class axDbGenMta : public axAbstractDbObject
{
public:

  /// destructor
  virtual ~axDbGenMta();

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
  DB_RESID_t m_mtaResId;

  //
  DB_RESID_t m_cmsResId;

#if 0 // moved to mta_current_avail table
  //
  AX_UINT32  m_availUnavailChangeTime;
#endif

  //
  DB_UPDATE_TIME_t   m_lastUpdated;

  // can store fqdn, IPv(4/6) address, etc
  DB_FQDN_t  m_fqdn;

  // IPv(4/6) address, etc
  DB_IP_ADDRESS_t  m_ipAddress;

  //
  DB_MAC_t  m_mtaMac;

  //
  AX_UINT8  m_provState;
  AX_UINT8  m_isProvStatePass;

  //
  AX_UINT8  m_pingState;
  AX_UINT8  m_isPingFailure;

  //
  AX_UINT8  m_available;

  //
  AX_UINT8  m_deleted;

  //
  AX_UINT8  m_ipAddressType;

  //
  AX_UINT8  m_alertLevel;

protected:

  /// default constructor
  axDbGenMta();

  /**
   * IN: MTA Res ID
   */
  axDbGenMta(DB_RESID_t);


#if 0 // don't need this anymore. just makes it hard to get things right in the head
  /**
   * IN: MTA mac, CMTS Res Id
   */
  axDbGenMta(DB_MAC_t, DB_RESID_t);
#endif

  /**
   * IN: Internal Generic MTA
   */
  axDbGenMta(axInternalGenMta *);


  /**
   * Copy Constructor
   */
  axDbGenMta(const axDbGenMta &);

private:

  void initAtInstantiation(void);

  // DB_GEN_MTA_FIELDS m_genMtaData;

};

#endif // _axDbGenMta_hpp_
