
//********************************************************************
// Copyright (c) 2006 Premraj Nallasivmapillai. All rights reserved.
//********************************************************************

#ifndef _axHfcAlarm_hpp_
#define _axHfcAlarm_hpp_

//********************************************************************
// include files
//********************************************************************
#include "axAbstractCAAlarm.hpp"
#include "axInternalObjList.hpp"
#include "axInternalHfc.hpp"
// #include "axInternalCmts.hpp"
// #include "axAbstractLock.hpp"
// #include "axInternalDsTypes.hpp"

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
 * file/class: axHfcAlarm.hpp
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

class axHfcAlarm : public axAbstractCAAlarm
{
public:

  /// destructor
  virtual ~axHfcAlarm();

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
  virtual AX_UINT8 getAlarmType(void);

  /**
   * 
   */
  INTDS_RESID_t getCmtsResId(void);
  AX_INT8 *     getHfcName(void);

  /**
   * 
   */
  virtual AX_INT32 keyCompare(axObject *);

  /*
   * Pre Soak children
   */
  void         preSoakAddChildren(axListBase *);
  bool         preSoakHasChild(axObject *);
  size_t       preSoakNumChildren(void);
  axListBase * preSoakGetChildren(void);
  void         preSoakAddCoincidentals(axListBase *);
  bool         preSoakHasCoincidental(axObject *);
  size_t       preSoakNumCoincidentals(void);
  axListBase * preSoakGetCoincidentals(void);

  /*
   * Post Soak children
   */
  void         postSoakAddChildren(axListBase *);
  bool         postSoakHasChild(axObject *);
  size_t       postSoakNumChildren(void);
  axListBase * postSoakGetChildren(void);
  void         postSoakAddCoincidentals(axListBase *);
  bool         postSoakHasCoincidental(axObject *);
  size_t       postSoakNumCoincidentals(void);
  axListBase * postSoakGetCoincidentals(void);

  /*
   *
   */
  void         setAlarmThreshold(AX_UINT16);
  AX_UINT16    getAlarmThreshold(void);
  void         setAlarmDetectionWindow(time_t);
  time_t       getAlarmDetectionWindow(void);

  void         setPreSoakDeviceTotal(AX_UINT16);
  AX_UINT16    getPreSoakDeviceTotal(void);
  void         setPreSoakDeviceChange(AX_UINT16);
  AX_UINT16    getPreSoakDeviceChange(void);
  void         setPostSoakDeviceTotal(AX_UINT16);
  AX_UINT16    getPostSoakDeviceTotal(void);
  void         setPostSoakDeviceChange(AX_UINT16);
  AX_UINT16    getPostSoakDeviceChange(void);

  /**
   *
   */
  void preSoakCheckAndAddCoincidentals(axListBase *);
  void preSoakCheckAllAndAddCoincidentals(axListBase *);

  ///
  axInternalHfc * getInternalHfc(void);

protected:

  /**
   * In: CMTS ResID, In-Memory HFC
   */
  axHfcAlarm(INTDS_RESID_t, axInternalHfc *);

  /**
   * In: CMTS ResID, HFC Name
   */
  axHfcAlarm(INTDS_RESID_t, AX_INT8 *);

  /// default constructor
  axHfcAlarm();

  /**
   * 
   */
  virtual bool addAlarmToDb(void);

  ///
  void movePercentAlarmStartTime(void);
  void movePowerAlarmStartTime(void);
  void createSoakTimerEntry(void);

  ///
  bool updatePostSoakDevices(axListBase &);
  bool updatePostSoakCoincidentals(axListBase &);
  bool updateAlarmSecondary(AX_UINT32, AX_UINT32);

  ///
  bool generatePercentCoincidentalAlarms(axListBase &);
  bool generateCountCoincidentalAlarms(axListBase &);
  bool generatePowerCoincidentalAlarms(axListBase &);

  /**
   * IN : param-1: internal obj list
   * OUT: param-2: coincidentals that are still bad
   */
  bool determinePostSoakBadCoincidentals(
                                axListBase *, axListBase &);

  /**
   * IN : param-1: internal obj list
   * OUT: param-2: coincidentals that are still bad
   */
  bool ticketAlarm(axListBase &, axListBase &);

private:

  axHfcAlarm(const axHfcAlarm &);

  INTDS_RESID_t     m_cmtsResId;
  INTDS_HFC_NAME_t  m_hfcName;

  /**
   * Threshold: 30% online CM's going offline, 3 MTA's, etc
   */
  AX_UINT16    m_alarmThreshold;

  /**
   * Window for threshold: 10 minutes, 15 minutes, etc
   * UNITS: seconds
   */
  time_t      m_alarmDetectionWindow;

  /*
   * Pre Soak
   */
  axInternalObjList m_preSoakList;
  axInternalObjList m_preSoakCoincidentalList;

  /*
   * Post Soak
   */
  axInternalObjList m_postSoakList;
  axInternalObjList m_postSoakCoincidentalList;

  /*
   * Pre Soak
   */
  AX_UINT16         m_preSoakDeviceTotal;
  AX_UINT16         m_preSoakDeviceChange;

  /*
   * Post Soak
   */
  AX_UINT16         m_postSoakDeviceTotal;
  AX_UINT16         m_postSoakDeviceChange;

  ///
  axInternalHfc   * m_intHfc;
};

#endif // _axHfcAlarm_hpp_
