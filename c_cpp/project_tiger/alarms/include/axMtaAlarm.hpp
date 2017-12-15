
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMtaAlarm_hpp_
#define _axMtaAlarm_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCAAlarm.hpp"
#include "axInternalDsTypes.hpp"
#include "axInternalGenMta.hpp"
#include "axDbHfc.hpp"

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
 * file/class: axMtaAlarm.hpp
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

class axMtaAlarm : public axAbstractCAAlarm
{
public:

  /// destructor
  virtual ~axMtaAlarm();

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
  virtual bool addAlarm(void);

  /**
   *
   */
  virtual bool postSoakCheckEscalate(void);

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

  /**
   *
   */
  axInternalGenMta * getInternalMta(void);

protected:

  /**
   * In: CMTS ResID, In-Memory MTA
   */
  axMtaAlarm(INTDS_RESID_t, axInternalGenMta *);

  /**
   * In: CMTS ResID, MTA Mac
   */
  axMtaAlarm(INTDS_RESID_t, AX_INT8 *);

  /**
   *
   */
  virtual bool addAlarmToDb(void);

  /**
   *
   */
  void createSoakTimerEntry(void);

  /**
   *
   */
  bool findDbHfc(axDbHfc &);

private:

  /// default constructor
  axMtaAlarm();

  /// copy constructor
  axMtaAlarm(const axMtaAlarm &);

  INTDS_RESID_t  m_cmtsResId;
  INTDS_MAC_t    m_mtaMacAddress;

  axInternalGenMta * m_intMta;
};

#endif // _axMtaAlarm_hpp_
