
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axDbEmta_hpp_
#define _axDbEmta_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axDbCm.hpp"
#include "axDbGenMta.hpp"
#include "axListBase.hpp"
// #include "axInternalEmta.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************
class axInternalEmta;

/** 
 * This class is used to ...
 * 
 * 
 * file/class: axDbEmta.hpp
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

class axDbEmta : public axDbGenMta
{
public:

  /**
   * static query sql statements
   */
  static AX_INT8 * BasicEmtaQuery;
  static AX_INT8 * CmtsEmtaQuery;
  static AX_INT8 * CmtsEmtaQueryStartup;
  static AX_INT8 * UpstreamChannelEmtaQuery;
  static AX_INT8 * DownstreamChannelEmtaQuery;
  static AX_INT8 * HfcEmtaQuery;
  static AX_INT8 * BasicEmtaUpdate;
  static AX_INT8 * BasicEmtaInsert;

  /// default constructor
  axDbEmta();

  /**
   * IN: MTA Res ID
   */
  axDbEmta(DB_RESID_t);

  /**
   * IN: CM mac, MTA mac, CMTS Res Id
   */
  axDbEmta(DB_MAC_t, DB_MAC_t, DB_RESID_t);

  /**
   * IN: MTA mac, CMTS Res Id
   */
  axDbEmta(DB_MAC_t, DB_RESID_t);

#if 0
  /**
   * IN: Internal eMTA, CMTS Res Id
   */
  axDbEmta(axInternalEmta *, DB_RESID_t);
#endif

  /**
   * Copy constructor
   */
  axDbEmta(const axDbEmta &);

  /// destructor
  virtual ~axDbEmta();

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
  // DB_RESID_t  m_cmResId;
  // DB_MAC_t    m_cmMac;

  // eMTA 'Has A' CM
  axDbCm  m_cm;

  //
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

protected:

private:

  void initAtInstantiation(void);

  // DB_EMTA_FIELDS  m_emtaData;

};

#endif // _axDbEmta_hpp_
