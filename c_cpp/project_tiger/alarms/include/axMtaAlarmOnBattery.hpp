
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMtaAlarmOnBattery_hpp_
#define _axMtaAlarmOnBattery_hpp_

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
 * file/class: axMtaAlarmOnBattery.hpp
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

class axMtaAlarmOnBattery : public axMtaAlarm
{
public:

  /**
   * In: CMTS ResID, In-Memory MTA
   */
  axMtaAlarmOnBattery(INTDS_RESID_t, axInternalGenMta *);

  /**
   * In: CMTS ResID, MTA mac
   */
  axMtaAlarmOnBattery(INTDS_RESID_t, AX_INT8 *);

  /// destructor
  virtual ~axMtaAlarmOnBattery();

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
  axMtaAlarmOnBattery();

private:

  axMtaAlarmOnBattery(const axMtaAlarmOnBattery &);

};

#endif // _axMtaAlarmOnBattery_hpp_
