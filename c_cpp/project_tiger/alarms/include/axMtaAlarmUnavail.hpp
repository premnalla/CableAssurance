
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMtaAlarmUnavail_hpp_
#define _axMtaAlarmUnavail_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axMtaAlarm.hpp"

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
 * file/class: axMtaAlarmUnavail.hpp
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

class axMtaAlarmUnavail : public axMtaAlarm
{
public:

  /**
   * In: CMTS ResID, In-Memory MTA
   */
  axMtaAlarmUnavail(INTDS_RESID_t, axInternalGenMta *);

  /**
   * In: CMTS ResID, MTA mac
   */
  axMtaAlarmUnavail(INTDS_RESID_t, AX_INT8 *);

  /// destructor
  virtual ~axMtaAlarmUnavail();

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
  virtual AX_UINT8 getAlarmSubType(void);

protected:

  /// default constructor
  axMtaAlarmUnavail();

#if 0
  /**
   *
   */
  virtual bool addAlarmToDb(void);
#endif

private:

  axMtaAlarmUnavail(const axMtaAlarmUnavail &);

};

#endif // _axMtaAlarmUnavail_hpp_
