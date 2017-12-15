
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axAbstractCAAlarm_hpp_
#define _axAbstractCAAlarm_hpp_

//********************************************************************
// include files
//********************************************************************
#include <sys/time.h>
#include "axObject.hpp"
#include "axInternalDsTypes.hpp"
#include "axAbstractLock.hpp"


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
 * file/class: axAbstractCAAlarm.hpp
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

class axAbstractCAAlarm : public axObject
{
public:

  /// destructor
  virtual ~axAbstractCAAlarm();

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

  ///
  virtual bool addAlarm(void)=0;

  ///
  virtual bool postSoakCheckEscalate(void)=0;

  ///
  virtual AX_UINT8 getAlarmType(void)=0;
  virtual AX_UINT8 getAlarmSubType(void)=0;

  /*
   *
   */
  void       setAlarmDetectionTime(struct timeval &);
  void       setAlarmDetectionTime(time_t);
  void       setAlarmDetectionTimeUSec(time_t);
  time_t     getAlarmDetectionTime(void);
  time_t     getAlarmDetectionTimeUSec(void);
  void       setAlarmSoakWindow(AX_UINT16);
  AX_UINT16  getAlarmSoakWindow(void);

  ///
  void      setInternalAlarmId(AX_UINT32);
  AX_UINT32 getInternalAlarmId(void);

  ///
  void           setAlarmId(AX_UINT64);
  AX_UINT64      getAlarmId(void);

  ///
  void           setResId(INTDS_RESID_t);
  INTDS_RESID_t  getResId(void);

  //
  axAbstractLock * getLock(void);

protected:

  /**
   * IN: Lock, Resource ID of resource under alarm
   */
  axAbstractCAAlarm(axAbstractLock *, INTDS_RESID_t);

  /**
   * IN: Lock, Resource ID of resource under alarm
   */
  axAbstractCAAlarm(INTDS_RESID_t);

  /// default constructor
  axAbstractCAAlarm();

  ///
  virtual bool addAlarmToDb(void);

  ///
  bool updateDbBasicAlarm(AX_INT8);
  bool addDbAlarmHistory(AX_INT8);

  ///
  bool clearAlarm(void);

  ///
  bool ticketAlarm(void);

private:

  /// copy constructor
  axAbstractCAAlarm(const axAbstractCAAlarm &);

  ///
  void instantiationInit(void);

  // This is not the same as alarm_id field in the db table
  // This is the unique alarm id generated valid only while
  // this application is up and running. When restarted the
  // the numbers get reassigned from 1
  AX_UINT32      m_intAlarmId;

  // the following mimic the db fields. Replicated only for
  // speedy lookup
  AX_UINT64      m_alarmId;
  INTDS_RESID_t  m_alarmResId;

  /*
   *
   */
  struct timeval m_alarmDetectionTime;
  AX_UINT16      m_alarmSoakWindow;

  //
  axAbstractLock * m_lock;
};

#endif // _axAbstractCAAlarm_hpp_
