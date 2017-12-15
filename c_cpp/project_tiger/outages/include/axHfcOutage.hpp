
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcOutage_hpp_
#define _axHfcOutage_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCAOutage.hpp"
// #include "axInternalDsTypes.hpp"

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
 * file/class: axHfcOutage.hpp
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

class axHfcOutage : public axAbstractCAOutage
{
public:

  /// destructor
  virtual ~axHfcOutage();

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
  AX_INT8 *     getHfcName(void);

  /**
   * 
   */
  virtual AX_INT32 keyCompare(axObject *);

protected:

  /**
   * In: CMTS ResID, HFC Name
   */
  axHfcOutage(INTDS_RESID_t, AX_INT8 *);

private:

  /// default constructor
  axHfcOutage();

  axHfcOutage(const axHfcOutage &);

  INTDS_RESID_t     m_cmtsResId;
  INTDS_HFC_NAME_t  m_hfcName;
};

#endif // _axHfcOutage_hpp_
