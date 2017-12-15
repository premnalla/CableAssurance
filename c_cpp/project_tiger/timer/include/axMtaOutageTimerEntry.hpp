
//********************************************************************
// OBSOLETED
//********************************************************************

//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axMtaOutageTimerEntry_hpp_
#define _axMtaOutageTimerEntry_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axOutageTimerEntry.hpp"
#include "axMtaOutage.hpp"

//********************************************************************
// definitions/macros
//********************************************************************

//********************************************************************
// forward declerations
//********************************************************************

/** 
 * This class is uses as entry in the Soak Timer Q to allow for soaking
 * of MTA outages. Once the soaking period ends, the callback with be
 * will be invoked and we can do whatever we want at that time.
 * 
 * 
 * file/class: axMtaOutageTimerEntry.hpp
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

class axMtaOutageTimerEntry : public axOutageTimerEntry
{
public:

  /// data constructor
  axMtaOutageTimerEntry(axMtaOutage *, time_t &);

  /// destructor
  virtual ~axMtaOutageTimerEntry();

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
  axMtaOutageTimerEntry();

private:

  axMtaOutageTimerEntry(const axMtaOutageTimerEntry &);

  INTDS_RESID_t  m_cmtsResId;
  INTDS_MAC_t    m_mtaMacAddress;
};

#endif // _axMtaOutageTimerEntry_hpp_
