
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMtaOutage_hpp_
#define _axMtaOutage_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCAOutage.hpp"
#include "axInternalDsTypes.hpp"

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
 * file/class: axMtaOutage.hpp
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

class axMtaOutage : public axAbstractCAOutage
{
public:

  /// destructor
  virtual ~axMtaOutage();

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
   *
   */
  virtual AX_UINT8 getAlarmType(void);

  /**
   * 
   */
  INTDS_RESID_t getCmtsResId(void);
  AX_INT8 *     getMtaMacAddress(void);

  /**
   * 
   */
  virtual AX_INT32 keyCompare(axObject *);

protected:

  /**
   * In: CMTS ResID, MTA mac
   */
  axMtaOutage(INTDS_RESID_t, AX_INT8 *);

private:

  /// default constructor
  axMtaOutage();

  axMtaOutage(const axMtaOutage &);

  INTDS_RESID_t  m_cmtsResId;
  INTDS_MAC_t    m_mtaMacAddress;
};

#endif // _axMtaOutage_hpp_
