
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcAlarmTimerEntry_hpp_
#define _axHfcAlarmTimerEntry_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAlarmTimerEntry.hpp"
#include "axHfcAlarm.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is uses as entry in the Soak Timer Q to allow for soaking
 * of HFC alarms. Once the soaking period ends, the callback with be
 * will be invoked and we can do whatever we want at that time.
 * 
 * file/class: axHfcAlarmTimerEntry.hpp
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

class axHfcAlarmTimerEntry : public axAlarmTimerEntry
{
public:

  /// data constructor
  axHfcAlarmTimerEntry(axHfcAlarm *, time_t &);

  /// destructor
  virtual ~axHfcAlarmTimerEntry();

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
   * Override from axAbstractTimerCallback
   *
   * @return
   * @see
   */
  virtual bool timerCallback(void);

#if 0
  /**
   * Override from axObject
   *
   * @return
   * @see
   */
  virtual bool isKeyEqual(void);

  /**
   *
   */
  INTDS_RESID_t getCmtsResId(void);
  AX_INT8 *     getHfcName(void);
#endif

protected:

  /// default constructor
  axHfcAlarmTimerEntry();

private:

  axHfcAlarmTimerEntry(const axHfcAlarmTimerEntry &);

  INTDS_RESID_t     m_cmtsResId;
  INTDS_HFC_NAME_t  m_hfcName;
  AX_UINT8          m_alarmType;
  AX_UINT8          m_alarmSubType;

  ///
  axInternalHfc   * m_intHfc;
};

#endif // _axHfcAlarmTimerEntry_hpp_
