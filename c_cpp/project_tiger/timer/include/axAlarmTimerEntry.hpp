
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAlarmTimerEntry_hpp_
#define _axAlarmTimerEntry_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractTimerObject.hpp"
#include "axAbstractCAAlarm.hpp"

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
 * file/class: axAlarmTimerEntry.hpp
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

class axAlarmTimerEntry : public axAbstractTimerObject
{
public:

  /// destructor
  virtual ~axAlarmTimerEntry();

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
   * Override from axObject
   *
   * @return
   * @see
   */
  virtual AX_INT64 hashCode(void);


  ///
  AX_UINT32 getInternalAlarmId(void);

protected:

  /// data constructor
  axAlarmTimerEntry(axAbstractCAAlarm *, time_t &);

  /// default constructor
  axAlarmTimerEntry();

private:

  axAlarmTimerEntry(const axAlarmTimerEntry &);

  AX_UINT32 m_intAlarmId;
};

#endif // _axAlarmTimerEntry_hpp_
