
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcAlarmMtaCount_hpp_
#define _axHfcAlarmMtaCount_hpp_

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
 * file/class: axHfcAlarmMtaCount.hpp
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

class axHfcAlarmMtaCount : public axHfcAlarm
{
public:

  /**
   * In: CMTS ResID, In-Memory HFC
   */
  axHfcAlarmMtaCount(INTDS_RESID_t, axInternalHfc *);

  /**
   * In: CMTS ResID, HFC Name
   */
  axHfcAlarmMtaCount(INTDS_RESID_t, AX_INT8 *);

  /// destructor
  virtual ~axHfcAlarmMtaCount();

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
  axHfcAlarmMtaCount();

  /// copy constructor
  axHfcAlarmMtaCount(const axHfcAlarmMtaCount &);

};

#endif // _axHfcAlarmMtaCount_hpp_
