
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcOutageTimerEntry_hpp_
#define _axHfcOutageTimerEntry_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axOutageTimerEntry.hpp"
#include "axHfcOutage.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is uses as entry in the Soak Timer Q to allow for soaking
 * of HFC outages. Once the soaking period ends, the callback with be
 * will be invoked and we can do whatever we want at that time.
 * 
 * file/class: axHfcOutageTimerEntry.hpp
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

class axHfcOutageTimerEntry : public axOutageTimerEntry
{
public:

  /// data constructor
  axHfcOutageTimerEntry(axHfcOutage *, time_t &);

  /// destructor
  virtual ~axHfcOutageTimerEntry();

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
  axHfcOutageTimerEntry();

private:

  axHfcOutageTimerEntry(const axHfcOutageTimerEntry &);

  INTDS_RESID_t     m_cmtsResId;
  INTDS_HFC_NAME_t  m_hfcName;
  AX_UINT8          m_alarmType;
  AX_UINT8          m_alarmSubType;
};

#endif // _axHfcOutageTimerEntry_hpp_
