
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcAlarmPower_hpp_
#define _axHfcAlarmPower_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axHfcAlarm.hpp"

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
 * file/class: axHfcAlarmPower.hpp
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

class axHfcAlarmPower : public axHfcAlarm
{
public:

  /**
   * In: CMTS ResID, In-Memory HFC
   */
  axHfcAlarmPower(INTDS_RESID_t, axInternalHfc *);

  /**
   * In: CMTS ResID, HFC Name
   */
  axHfcAlarmPower(INTDS_RESID_t, AX_INT8 *);

  /// destructor
  virtual ~axHfcAlarmPower();

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

#if 0
  /**
   * In: CMTS, HFC
   */
  virtual bool postSoakCheckAndUpdate(axInternalCmts *, axInternalHfc *);
#endif

private:

  /// default constructor
  axHfcAlarmPower();

  /// copy constructor
  axHfcAlarmPower(const axHfcAlarmPower &);

};

#endif // _axHfcAlarmPower_hpp_
